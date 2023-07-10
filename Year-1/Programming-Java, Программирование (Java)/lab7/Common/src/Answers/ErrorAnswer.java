package Answers;

import javax.sound.midi.Receiver;
import java.io.Serializable;
import java.util.logging.Logger;

public class ErrorAnswer extends Answer implements Serializable {
    public static final Logger logger = Logger.getLogger(Receiver.class.getName());
    public ErrorAnswer(String answer) {
        super(answer);
    }

    @Override
    public void logAnswer() {
        logger.severe(answer);
    }

    @Override
    public void printAnswer() {
        System.err.println(answer);
    }
}
