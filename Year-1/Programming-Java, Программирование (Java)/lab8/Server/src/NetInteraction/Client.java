package NetInteraction;

import java.net.SocketAddress;
import java.nio.ByteBuffer;

public class Client {
    final byte[] b = new byte[128000];
    SocketAddress clientAddress;
    ByteBuffer buffer = ByteBuffer.wrap(b);
}
