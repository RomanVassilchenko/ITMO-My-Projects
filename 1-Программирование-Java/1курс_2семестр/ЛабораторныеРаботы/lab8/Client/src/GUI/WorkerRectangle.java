package GUI;

import Dependency.Worker;

import java.awt.*;

public class WorkerRectangle extends Rectangle {
    final double centerY;
    final int centreX;
    final int width;
    final double height;
    final int startX;
    final double startY;
    final Integer key;
    final Worker worker;
    @Override
    public double getX() {
        return this.centreX;
    }

    @Override
    public double getY() {
        return this.centerY;
    }

    @Override
    public double getWidth() {
        return this.width;
    }

    @Override
    public double getHeight() {
        return this.height;
    }

    public WorkerRectangle(Worker worker, int startX, double startY) {
        this.width = selectWidth(worker);
        this.centerY = startY - worker.getCoordinates().getY() - (worker.getPerson().getHeight() / 2);
        this.centreX = startX + worker.getCoordinates().getX()
                - this.width/2;
        this.height = worker.getPerson().getHeight();
        this.startX = startX;
        this.startY = startY;
        this.key = Math.toIntExact(worker.getId());
        this.worker = worker;
    }

    public int selectWidth(Worker worker) {
        return switch (worker.getPosition().toString()) {
            case "DIRECTOR" -> 100;
            case "LABORER" -> 90;
            case "HUMAN_RESOURCES" -> 80;
            case "HEAD_OF_DEPARTMENT" -> 70;
            case "MANAGER_OF_CLEANING" -> 60;
            default -> 50;
        };
    }

    public Worker getWorker(){
        return worker;
    }
}
