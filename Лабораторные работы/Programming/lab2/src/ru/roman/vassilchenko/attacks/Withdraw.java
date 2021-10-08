package attacks;
import ru.ifmo.se.pokemon.*;

public class Withdraw extends StatusMove {
    public Withdraw(){
        super(Type.WATER,0,0);
    }
    @Override
    protected void applySelfEffects(Pokemon p) {
        p.setMod(Stat.DEFENSE,1);
    }

    @Override
    protected String describe() {
        return "использует Withdraw ";
    }
}
