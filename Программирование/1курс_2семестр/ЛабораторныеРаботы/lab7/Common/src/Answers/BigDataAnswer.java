package Answers;

import javax.sound.midi.Receiver;
import java.io.Serializable;
import java.util.logging.Logger;

public class BigDataAnswer extends Answer implements Serializable {
    public static final Logger logger = Logger.getLogger(Receiver.class.getName());
    public BigDataAnswer() { super("BigData"); }

    @Override
    public void logAnswer() {
        logger.info(answer);
    }

    @Override
    public void printAnswer() {
        System.err.println(answer);
    }
}
