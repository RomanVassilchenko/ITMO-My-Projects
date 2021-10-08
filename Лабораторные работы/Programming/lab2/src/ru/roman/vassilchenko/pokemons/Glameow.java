package pokemons;
import attacks.*;
import ru.ifmo.se.pokemon.*;

public class Glameow extends Pokemon {
    public Glameow(String name, int level){
        super(name,level);
        setStats(49,55,42,42,37,85);
        setType(Type.NORMAL);
        setMove(new Confide(), new Swagger(), new DoubleTeam());
    }
}
