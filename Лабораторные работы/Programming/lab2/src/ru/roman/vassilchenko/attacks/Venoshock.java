package ru.roman.vassilchenko.attacks;
import ru.ifmo.se.pokemon.*;

public class Venoshock extends SpecialMove {
    public Venoshock(){
        super(Type.POISON, 65,100);
    }

    private boolean isPoisoned = false;
    @Override
    protected void applyOppDamage(Pokemon p, double v) {
        Status cond = p.getCondition();
        isPoisoned = cond.equals(Status.POISON);
        if(isPoisoned){
            p.setMod(Stat.HP, -2*(int)Math.round(v));
        }
    }

    @Override
    protected String describe() {
        return "использует Venoshock " + ((isPoisoned) ? "и урон x2, так как цель отравлена" : "");
    }
}
