public class Bush extends Essence{
    public Bush(String name) {
        super(name);
    }
    public Bush(){
        super("темно-зеленымй, ломкий кустик");
    }
    public void rootsCoveredBy(String objects){
        System.out.println("Корни покрыты " + objects);
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
