package ru.roman.vassilchenko.pokemons;
import ru.roman.vassilchenko.attacks.*;
import ru.ifmo.se.pokemon.*;

public class Vivillon extends Pokemon {
    public Vivillon(String name, int level){
        super(name,level);
        setStats(80,52,50,90,50,89);
        setType(Type.BUG);
        setMove(new Swagger(), new Rest(), new Sludge(), new DoubleTeam());
    }
}
