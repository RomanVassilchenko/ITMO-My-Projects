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
    double area = 0.0;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object unused) {
        results.addAll(storage.getAllResults());
        area = -1;
        MBeanRegistryUtil.registerBean(this, "area");
    }

    public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object unused) {
        MBeanRegistryUtil.unregisterBean(this);
    }


    public void computeArea(ResultBean resultBean){
        current = resultBean.getCurrent();
        boolean successful = PlotUtil.isOnPlot(current.getX(), current.getY(), current.getR());
        Result result = new Result(
                current.getX(),
                current.getY(),
                current.getR(),
                successful,
                System.currentTimeMillis()
        );
        results.add(result);
        storage.addResult(result);
    }
    @Override
    public double getArea() {
        if (results.size() < 3) {
            return area;
        }

        Coordinates r1 = results.get(results.size() - 1).getCoordinates();
        Coordinates r2 = results.get(results.size() - 2).getCoordinates();
        Coordinates r3 = results.get(results.size() - 3).getCoordinates();

        double x1 = r1.getX(), y1 = r1.getY();
        double x2 = r2.getX(), y2 = r2.getY();
        double x3 = r3.getX(), y3 = r3.getY();

        double a = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        double b = Math.sqrt(Math.pow(x3 - x2, 2) + Math.pow(y3 - y2, 2));
        double c = Math.sqrt(Math.pow(x1 - x3, 2) + Math.pow(y1 - y3, 2));

        double s = (a + b + c) / 2;

        area = Math.sqrt(s * (s - a) * (s - b) * (s - c));
        return area;
    }
}

