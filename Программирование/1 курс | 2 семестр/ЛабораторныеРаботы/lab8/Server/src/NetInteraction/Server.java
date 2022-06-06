package NetInteraction;

import java.io.*;
import java.sql.SQLException;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerEvents serverEvents = new ServerEvents();
        serverEvents.run();
    }
}
