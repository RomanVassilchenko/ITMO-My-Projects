package ru.rossilman.weblab3.bean;

import com.google.gson.GsonBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Destroyed;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.rossilman.weblab3.entity.Coordinates;
import ru.rossilman.weblab3.entity.Result;
import ru.rossilman.weblab3.storage.ResultStorage;
import ru.rossilman.weblab3.util.MBeanRegistryUtil;
import ru.rossilman.weblab3.util.PlotUtil;

import javax.management.*;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by rossilman on 31/10/2022
 */
@Named
@ApplicationScoped
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResultAreaBean extends NotificationBroadcasterSupport implements ResultAreaMXBean, Serializable {

    @Inject
    ResultStorage storage;
    final List<Result> results = new CopyOnWriteArrayList<>();
    Coordinates current = new Coordinates();

    public void init(@Observes @Initialized(ApplicationScoped.class) Object unused) {
        results.addAll(storage.getAllResults());
        System.out.println(results);
        MBeanRegistryUtil.registerBean(this, "area");
    }

    public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object unused) {
        MBeanRegistryUtil.unregisterBean(this);
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        String[] types = new String[] { AttributeChangeNotification.ATTRIBUTE_CHANGE };
        String name = AttributeChangeNotification.class.getName();
        String description = "The point is outside of area.";
        MBeanNotificationInfo info = new MBeanNotificationInfo(types, name, description);
        return new MBeanNotificationInfo[] { info };
    }

    public String parseResultsToJson() {
        return new GsonBuilder().create().toJson(results);
    }

    @Override
    public double getArea() {

        if (results.size() < 3) {
            return 0;
        }

        Coordinates r1 = results.get(results.size() - 1).getCoordinates();
        Coordinates r2 = results.get(results.size() - 2).getCoordinates();
        Coordinates r3 = results.get(results.size() - 3).getCoordinates();

        double x1 = r1.getX(), y1 = r1.getY();
        double x2 = r2.getX(), y2 = r2.getY();
        double x3 = r3.getX(), y3 = r3.getY();

        // compute the sides of the triangle
        double a = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        double b = Math.sqrt(Math.pow(x3 - x2, 2) + Math.pow(y3 - y2, 2));
        double c = Math.sqrt(Math.pow(x1 - x3, 2) + Math.pow(y1 - y3, 2));

        // compute the semi-perimeter
        double s = (a + b + c) / 2;

        // compute the area using Heron's formula

        System.out.println(x1 + " " + x2 + " " + x3);

        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }
}

