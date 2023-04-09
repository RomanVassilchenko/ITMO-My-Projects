package beans;

import lombok.*;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "results")
public class Result implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "x", nullable = false)
    private double x;
    @Column(name = "y", nullable = false)
    private double y;
    @Column(name = "r", nullable = false)
    private double r;
    @Column(name = "hit", nullable = false)
    private boolean hit;

    @ManyToOne
    @JoinColumn(name = "owner", referencedColumnName = "username")
    private User owner;

    public Result(Double x, Double y, Double r) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = checkHit();
    }

    public JsonObject toJSONObject() {
        return Json.createObjectBuilder()
                .add("x", x)
                .add("y", y)
                .add("r", r)
                .add("hit", hit)
                .build();
    }

    private boolean checkHit() {
        boolean circle = (x <= 0) && (y >= 0) && (x * x + y * y <= (r / 2) * (r / 2));
        boolean triangle = (x <= 0) && (y <= 0) && (y >= -x - r / 2);
        boolean rectangle = (x >= 0) && (y >= 0) && (x <= r) && (y <= r / 2);
        return circle || triangle || rectangle;
    }
}