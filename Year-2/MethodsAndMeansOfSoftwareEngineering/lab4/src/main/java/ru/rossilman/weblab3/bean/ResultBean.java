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
public class ResultBean extends NotificationBroadcasterSupport implements ResultMXBean, Serializable {

    // Deprecated because of the profiler
//    @Inject
//    ResultStorage storage;
    final List<Result> results = new CopyOnWriteArrayList<>();
    Coordinates current = new Coordinates();
    int sequenceNumber = 1;
    int unsuccessfulStreak = 0;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object unused) {
        // Deprecated because of the profiler
//        results.addAll(storage.getAllResults());
        MBeanRegistryUtil.registerBean(this, "main");
    }

    public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object unused) {
        MBeanRegistryUtil.unregisterBean(this);
    }

    @Override
    public int getSuccessfulResultsNumber() {
        return (int) results.stream()
                .filter(Result::isSuccessful)
                .count();
    }

    @Override
    public int getTotalResultsNumber() {
        return results.size();
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        String[] types = new String[] { AttributeChangeNotification.ATTRIBUTE_CHANGE };
        String name = AttributeChangeNotification.class.getName();
        String description = "The point is outside of area.";
        MBeanNotificationInfo info = new MBeanNotificationInfo(types, name, description);
        return new MBeanNotificationInfo[] { info };
    }

    public void addResult() {
        boolean successful = PlotUtil.isOnPlot(current.getX(), current.getY(), current.getR());
        Result result = new Result(
                current.getX(),
                current.getY(),
                current.getR(),
                successful,
                System.currentTimeMillis()
        );

        results.add(result);
        // Deprecated because of the profiler
//        storage.addResult(result);

        if (!successful) {
            unsuccessfulStreak++;
            if (unsuccessfulStreak >= 3) {
                Notification notification = new Notification(
                        "Three misses in a row",
                        getClass().getSimpleName(),
                        sequenceNumber++,
                        "The user missed three times in a row."
                );
                sendNotification(notification);
                unsuccessfulStreak = 0;  // Reset the streak
            }
        } else {
            unsuccessfulStreak = 0;  // Reset the streak
        }
    }

    public String parseResultsToJson() {
        return new GsonBuilder().create().toJson(results);
    }
}
