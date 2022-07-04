import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Scooperfield scooperfield = new Scooperfield();
        Fog fog = new Fog("туман");
        Grass grass = new Grass("Рыхлая земля");
        Random random = new Random();

        scooperfield.touch("трость", "земля");
        scooperfield.go("противоположный склон оврага");
        scooperfield.climb(Direction.Up);

        scooperfield.climb(Direction.Down);
        scooperfield.climb(Direction.Up);
        scooperfield.climb(Direction.Down);
        scooperfield.climb(Direction.Up);

        scooperfield.go("поверхности");
        fog.dissipate("прозрачнее");
        scooperfield.go("рыхлая земля");
        fog.dissipate("рассеялся");
        grass.filledBy(" темно-зеленыме, ломкие кустики");
        Bush[] bushes = new Bush[13];
        bushes[2] = new Bush("темно-зеленый, ломкий кустик");
        bushes[2].rootsCoveredBy("картофель");
        scooperfield.remember("картофель", "жареный", "вареный");
        scooperfield.think("картофель", "растет на деревьях");
    }
}
