public class Scooperfield extends Essence implements Touchable, Goable, Climbable, Rememberable, Thinkable {
    public Scooperfield(){
        super("Скуперфильд");
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
    public void touch(String byObject, String toObject) {
        System.out.println(getName() + " ощупывая " + toObject + " при помощи " + byObject);
    }

    @Override
    public void go(String toObject) {
        System.out.println(getName() + " добрался до " + toObject);
    }

    @Override
    public void climb(Direction direction) {
        System.out.print(getName() + " начал ");
        switch (direction){
            case Up:
                System.out.println("карабкаться наверх");
                break;
            case Down:
                System.out.println("срываться и скатываться вниз");
                break;
            case Left:
                System.out.println("карабкаться налево");
                break;
            case Right:
                System.out.println("карабкаться направо");
                break;
            default:
                break;
        }
    }
    @Override
    public void remember(String object, String... states) {
        System.out.print(getName() + " помнит только ");
        for (int i = 0; i < states.length; i++) System.out.print(states[i] + ", ");
        System.out.println(object);
    }


    @Override
    public void think(String object, String action) {
        System.out.println(getName() + "думал, что " + object + " " + action);
    }
}
