package ru.roman.vassilchenko.pokemons;
import ru.roman.vassilchenko.attacks.*;
import ru.ifmo.se.pokemon.*;

public class Spewpa extends Pokemon {
    public Spewpa(String name, int level){
        super(name,level);
        setStats(45,22,60,27,30,29);
        setType(Type.BUG);
        setMove(new Swagger(), new Rest(), new Sludge());
    }
}
