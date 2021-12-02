public class Main {
    public static void main(String[] args) {
        Scooperfield scooperfield = new Scooperfield();
        Fog fog;
        try{
            fog = new Fog("туман");
        } catch (NameError e){
            System.out.println(e.getMessage());
            fog = new Fog("Туман");
        }

        Grass grass = new Grass("Рыхлая земля");

        scooperfield.touch("", "земля");
        try{
            scooperfield.go("противоположный склон оврага", false);
        }
        catch (WalkOnMissingSpaceException e){
            System.out.println(e.getMessage());
        }
        scooperfield.climb(Direction.Up);

        scooperfield.climb(Direction.Down);
        scooperfield.climb(Direction.Up);
        scooperfield.climb(Direction.Down);
        scooperfield.climb(Direction.Up);

        try{
            scooperfield.go("", false);
        } catch (WalkOnMissingSpaceException e){
            System.out.println(e.getMessage());
        }

        try{
            scooperfield.go("поверхности", false);
        } catch (WalkOnMissingSpaceException e){
            System.out.println(e.getMessage());
        }

        System.out.println(fog.dissipate("прозрачнее").getDescriptionOfState());
        try{
            scooperfield.go("рыхлая земля", false);
        } catch (WalkOnMissingSpaceException e){
            System.out.println(e.getMessage());
        }
        System.out.println(fog.dissipate("развеялся").getDescriptionOfState());
        Grass.Bush[] bushes = new Grass.Bush[13];
        bushes[2] = new Grass.Bush("темно-зеленый, ломкий кустик");
        grass.filledBy(bushes[2]);

        Potato potato = new Potato();

        bushes[2].rootsCoveredBy(potato.getName());
        scooperfield.remember(potato.getName(), "жареный", "вареный");
        scooperfield.think(potato.getName(), "растет на деревьях", true);

        scooperfield.bite(scooperfield.getName(), potato.getName());
        scooperfield.think(potato.rawPotato().getName(), potato.rawPotato().taste(), false);
        scooperfield.think("никто", "не будет выращивать бесполезные плоды", true);
        try {
            scooperfield.go("дальше", true);
        } catch (WalkOnMissingSpaceException e) {
            System.out.println(e.getMessage());
        }
    }
}
