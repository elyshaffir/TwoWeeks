package gameCom;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client extends Thread {
    private int id;

    Client(int id) {
        this.id = id;
    }

    public void run() {
        try {
            InetSocketAddress hA = new InetSocketAddress("localhost", 5000);
            SocketChannel client = SocketChannel.open(hA);
            System.out.println("The Client is sending messages to server...");
            // Sending messages to the server
            String[] msg = new String[]{"A" + id, "B" + id, "C"};
            for (String aMsg : msg) {
                byte[] message = aMsg.getBytes();
                ByteBuffer buffer = ByteBuffer.wrap(message);
                client.write(buffer);
                ByteBuffer readBuffer = ByteBuffer.allocate(256);
                client.read(readBuffer);
                String fromServer = new String(readBuffer.array()).trim();
                System.out.println(fromServer + ":: " + id);
                buffer.clear();
                Thread.sleep(3000);
            }
            client.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
