package ru.roman.vassilchenko;

import ru.ifmo.se.pokemon.*;
import ru.roman.vassilchenko.pokemons.*;

public class Lab2 {

    public static void main(String[] args) {
        Battle b = new Battle();
        Glameow p1 = new Glameow("Кот",1);
        Purugly p2 = new Purugly("Большой кот",1);
        Scatterbug p3 = new Scatterbug("Букашка",1);
        Spewpa p4 = new Spewpa("Большая букашка",1);
        Tornadus p5 = new Tornadus("Какой-то Джин", 1);
        Vivillon p6 = new Vivillon("Бабочка",1);

        b.addAlly(p1);
        b.addAlly(p2);
        b.addAlly(p3);
        b.addFoe(p4);
        b.addFoe(p5);
        b.addFoe(p6);
        b.go();
    }
}
