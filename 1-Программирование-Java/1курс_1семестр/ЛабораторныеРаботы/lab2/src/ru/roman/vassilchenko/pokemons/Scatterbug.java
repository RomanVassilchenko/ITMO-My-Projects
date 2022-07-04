package ru.roman.vassilchenko.pokemons;
import ru.roman.vassilchenko.attacks.*;
import ru.ifmo.se.pokemon.*;

public class Scatterbug extends Pokemon {
    public Scatterbug(String name, int level){
        super(name,level);
        setStats(38,35,40,27,25,35);
        setType(Type.BUG);
        setMove(new Swagger(), new Rest());
    }
}
