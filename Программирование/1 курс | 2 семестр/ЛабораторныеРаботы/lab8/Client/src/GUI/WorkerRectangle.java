package GUI;

import Dependency.Worker;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class WorkerRectangle extends Rectangle {
    double centerY;
    int centreX;
    int width;
    double height;
    int startX;
    double startY;
    Integer key;
    Worker worker;
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
        int selectWidth = 50;
        switch (worker.getPosition().toString()) {
            case "DIRECTOR":
                selectWidth  = 100;
                break;
            case "LABORER":
                selectWidth = 90;
                break;
            case "HUMAN_RESOURCES":
                selectWidth = 80;
                break;
            case "HEAD_OF_DEPARTMENT":
                selectWidth  = 70;
                break;
            case "MANAGER_OF_CLEANING":
                selectWidth  = 60;
                break;
        }
        return selectWidth;
    }

    public Worker getWorker(){
        return worker;
    }
}
