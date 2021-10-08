package pokemons;
import attacks.*;
import ru.ifmo.se.pokemon.*;

public class Tornadus extends Pokemon {
    public Tornadus(String name, int level){
        super(name, level);
        setStats(79,115,70,125,80,111);
        setType(Type.FLYING);
        setMove(new Withdraw(), new SweetScent(), new RockSlide(), new Venoshock());
    }
}
