package ru.rossilman.weblab3.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Coordinates {

    double x, y;
    int r = 1;

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + r + ")";
    }
}
