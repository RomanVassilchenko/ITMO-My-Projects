public class Fog extends Essence implements Dissipatable {
    public Fog(String name) {
        super(name);
        System.out.println("На улице туман");
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

    @Override
    public void dissipate(String state) {
        System.out.println("Туман стал " + state);
    }
}
