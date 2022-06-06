package ru.roman.vassilchenko.attacks;
import ru.ifmo.se.pokemon.*;

public class FireBlast extends SpecialMove {
    public FireBlast(){
        super(Type.FIRE,110,85);
    }
    private boolean isBurned = false;
    @Override
    protected void applyOppEffects(Pokemon p){
        if(Math.random() <= 0.1){
            isBurned = true;
            Effect.burn(p);
        }
    }

    @Override
    protected String describe() {
        return "Использует Fire Blast " + ((isBurned) ? "и burn цель" : "");
    }
}
