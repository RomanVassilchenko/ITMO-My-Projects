package ru.rossilman.weblab3.storage;

import jakarta.enterprise.context.ApplicationScoped;
import ru.rossilman.weblab3.entity.Coordinates;
import ru.rossilman.weblab3.entity.Result;
import ru.rossilman.weblab3.util.PlotUtil;
import ru.rossilman.weblab3.util.TransactionUtil;

import java.util.List;

@ApplicationScoped
public class ResultStorage {

    public List<Result> getAllResults() {
        return TransactionUtil.executeWithCallback(manager -> {
            List<Result> results = manager
                    .createQuery("SELECT result FROM Result result", Result.class)
                    .getResultList();

            results.forEach(result -> {
                Coordinates coordinates = result.getCoordinates();
                boolean successful = PlotUtil.isOnPlot(coordinates.getX(), coordinates.getY(), coordinates.getR());
                result.setSuccessful(successful);
            });

            return results;
        });
    }

    public void addResult(Result result) {
        TransactionUtil.execute(manager -> manager.persist(result));
    }
}
