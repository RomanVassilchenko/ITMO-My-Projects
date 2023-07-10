package ru.roman.vassilchenko.attacks;
import ru.ifmo.se.pokemon.*;

public class Sludge extends SpecialMove {
    public Sludge(){
        super(Type.POISON, 65,100);
    }

    private boolean isPoisoned = false;
    @Override
    protected void applyOppEffects(Pokemon p) {
        if(Math.random() < 0.3){
            Effect.poison(p);
            isPoisoned = true;
        }
    }

    @Override
    protected String describe() {
        return "использует Sludge " + ((isPoisoned) ? "и poison цель" : "");
    }
}
