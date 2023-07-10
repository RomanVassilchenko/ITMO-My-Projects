package data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Entity
@Table(name = "RESULT_TBL")
public class Result implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "RESULT_ID")
    private int id;
    @Column(name = "RESULT_X")
    private Double x;
    @Column(name = "RESULT_Y")
    private Double y;
    @Column(name = "RESULT_R")
    private Double r;
    @Column(name = "RESULT_CUR")
    private String currTime;
    @Column(name = "RESULT_EXE")
    private Double executionTime;
    @Column(name = "RESULT_HIT")
    private Boolean hit;
    @Column(name = "RESULT_CUSTOM_TIME")
    private OffsetDateTime dateTime;

    // 1 ?????? -  ???? ????????? ??????? JSF.
    //(? ???????????) ???????? ???? ? ????? OffsetDateTime ???? ? ???? ? ????? ?? ???????, ?????????, ??? ????? ?????? ???? ? ??????? ????/?????/??? ? ????????? ? ?? ? ????? Timestamp

    public Result() {

    }

    public Result(double x, double y,  double r, OffsetDateTime dateTime) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.dateTime = dateTime;
    }

    public Result(double x, double y,  double r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public String getCurrTime() {
        return currTime;
    }

    public void setCurrTime(String currTime) {
        this.currTime = currTime;
    }

    public double getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(double executionTime) {
        this.executionTime = executionTime;
    }

    public boolean getHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public void setDateTime(OffsetDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public OffsetDateTime getDateTime() {
        return dateTime;
    }

    public boolean checkFirstSector(double x, double y, double r) {
        return x <= 0 && y >= 0 && Math.abs(x) <= r / 2 && y <= r;
    }

    public boolean checkSecondSector(double x, double y, double r) {
        return x <= 0 && y <= 0 && Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) <= r;
    }

    public boolean checkThirdSector(double x, double y, double r) {
        return x >= 0 && y <= 0 && x <= r / 2 && y >= (x - r / 2);
    }

    public void checkHit() {
        setHit(checkFirstSector(x, y, r) || checkSecondSector(x, y, r) || checkThirdSector(x, y, r));
    }

    public boolean testHit() {
        return checkFirstSector(x, y, r) || checkSecondSector(x, y, r) || checkThirdSector(x, y, r);
    }

    @Override
    public String toString() {
        return "Result{" +
                "xValue=" + x +
                ", yValue=" + y +
                ", rValue=" + r +
                ", currTime=" + currTime +
                ", executionTime=" + executionTime +
                ", hitResult=" + hit +
                '}';
    }

    @Override
    public int hashCode() {
        return x.hashCode() + y.hashCode() +
                r.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Result) {
            Result resultObj = (Result) obj;
            return x.equals(resultObj.getX()) &&
                    y.equals(resultObj.getY()) &&
                    r.equals(resultObj.getR()) &&
                    currTime.equals(resultObj.getCurrTime()) &&
                    hit.equals(resultObj.getHit());
        }
        return false;
    }
}
