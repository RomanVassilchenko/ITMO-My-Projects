public class Potato {
    public String taste(){
        return "Нормальный";
    }
    private final String name;

    public String getName(){
        return name;
    }

    public Potato(){
        name = "картофель";
    }

    public Potato rawPotato(){
        return new Potato(){

            @Override
            public String getName() {
                return "Сырой картофель";
            }

            @Override
            public String taste() {
                return "страшно невкусный и противный";
            }
        };
    }
}
