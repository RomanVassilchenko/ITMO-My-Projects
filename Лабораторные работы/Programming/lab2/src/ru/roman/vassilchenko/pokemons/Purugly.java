package pokemons;
import attacks.*;
import ru.ifmo.se.pokemon.*;

public class Purugly extends Pokemon {
    public Purugly(String name, int level){
        super(name, level);
        setStats(71,82,64,64,59,112);
        setType(Type.NORMAL);
        setMove(new Confide(), new Swagger(),new DoubleTeam(), new FireBlast());
    }
}
