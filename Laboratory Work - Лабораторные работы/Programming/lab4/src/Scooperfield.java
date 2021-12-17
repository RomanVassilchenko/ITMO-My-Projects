public class Scooperfield extends Essence implements Touchable, Goable, Climbable, Rememberable, Thinkable, Biteable {
    final Stick stick;
    public Scooperfield(){
        super("Скуперфильд");
         stick = new Stick();
        stick.setStickName("Трость");
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

    private class Stick{
        private String stickName;
        public void setStickName(String name){
            stickName = name;
        }
        public String getStickName(){
            return stickName;
        }
        @Override
        public String toString() {
            return getStickName();
        }
    }

    @Override
    public void touch(String byObject, String toObject) {
        if(byObject.isEmpty()) byObject = stick.toString();
        System.out.println(getName() + " ощупывая " + toObject + " при помощи " + byObject);
    }

    @Override
    public void go(String toObject, boolean isGoing) throws WalkOnMissingSpaceException {
        if(!toObject.isEmpty()){System.out.println(getName() + (isGoing ? " отправился " : " добрался до ") + toObject);}
        else {
            throw new WalkOnMissingSpaceException(getName(), " не может идти по пустоте! Проверьте, куда он идет");
        }
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
        for (String state : states) System.out.print(state + ", ");
        System.out.println(object);
    }


    @Override
    public void think(String object, String action, boolean isPastAction) {
        System.out.println(getName() + ((isPastAction) ? (" думал") : (" думает")) + ", что " + object + " " + action);
    }

    @Override
    public void bite(String byObject, String toObject) {
        System.out.println(byObject + " откусил кусочек " + toObject);
    }
}
