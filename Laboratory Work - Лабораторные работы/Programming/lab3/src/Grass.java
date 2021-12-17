public class Grass extends Essence{
    public Grass(String name) {
        super(name);
    }
    public void filledBy(String objects){
        System.out.println(getName() + " усаженная " + objects);
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
}
