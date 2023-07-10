package ru.rossilman.weblab3.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.rossilman.weblab3.util.LocalizationUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Rossilman on 02/10/2022
 */
@Entity
@Table(name = "results")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Embedded
    Coordinates coordinates;
    @Transient
    boolean successful;
    long time;

    public Result(double x, double y, int r, boolean successful, long time) {
        this.coordinates = new Coordinates(x, y, r);
        this.successful = successful;
        this.time = time;
    }

    public String getSuccessString() {
        return LocalizationUtil.getMessage(successful ? "yes" : "no");
    }

    public String getFormattedTime() {
        return new SimpleDateFormat("dd.MM.yy HH:mm:ss")
                .format(new Date(time));
    }
}
