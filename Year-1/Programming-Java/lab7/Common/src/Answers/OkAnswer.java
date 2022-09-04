package Answers;

import javax.sound.midi.Receiver;
import java.io.Serializable;
import java.util.logging.Logger;

public class OkAnswer extends Answer implements Serializable {
    public static final Logger logger = Logger.getLogger(Receiver.class.getName());

    public OkAnswer(String answer) {
        super(answer);
    }

    @Override
    public void logAnswer() {
        logger.info(answer);
    }

    @Override
    public void printAnswer() {
        System.out.println(answer);
    }
}
