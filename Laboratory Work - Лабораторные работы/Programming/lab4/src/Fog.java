import java.util.Objects;

public class Fog extends Essence implements Dissipatable {
    public Fog(String name) throws NameError {
        super((Objects.equals(name, "")) ? "Туман" : name);
        if(name != null && !name.equals(""))
        {
        System.out.println("На улице туман");}
        else{
            throw new NameError("Объект не может быть создан без имени!");
        }
    }
    @Override
    public int hashCode() {
        return super.hashCode() + this.getName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj.hashCode() == this.hashCode();
    }

    @Override
    public String toString() {
        return "Имя: " + getName();
    }

    public interface FogState{
        String getDescriptionOfState();
    }

    @Override
    public FogState dissipate(String state) {
        class FogDissipateState implements FogState {
            final String fogState = "Туман стал " + state;

            @Override
            public String getDescriptionOfState() {
                return this.fogState;
            }
        }
        return new FogDissipateState();
    }
}
