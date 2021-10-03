package attacks;

import ru.ifmo.se.pokemon.*;

public class BubbleBeam extends SpecialMove {
    public BubbleBeam(){
        super(Type.WATER,65,100);
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        super.applySelfEffects(pokemon);
    }
}
