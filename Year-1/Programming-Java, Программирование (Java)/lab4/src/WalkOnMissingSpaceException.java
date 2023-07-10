public class WalkOnMissingSpaceException extends Exception{
    private String whoIsWalking;
    private String message;

    public WalkOnMissingSpaceException(String whoIsWalking, String message){
        super(whoIsWalking + message);
        this.whoIsWalking = whoIsWalking;
        this.message = message;
    }

    @Override
    public String getMessage(){
        return "Объект: " + whoIsWalking + message;
    }
}
