package gameCom;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Objects;

public class Client extends Thread {
    private int id;
    private String dataToSend = "";
    private String dataFromServer = "";

    public Client(int id) {
        this.id = id;
    }

    public void run() {
        try {
            InetSocketAddress hA = new InetSocketAddress("localhost", 5000);
            SocketChannel client = SocketChannel.open(hA);
            System.out.println("The Client is sending messages to server...");
            // Sending messages to the server
            while (!Objects.equals(dataToSend, "KK")){
                byte[] message = ("{" + dataToSend + "###}").getBytes();
                ByteBuffer buffer = ByteBuffer.wrap(message);
                client.write(buffer);
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                client.read(readBuffer);
                dataFromServer = new String(readBuffer.array()).trim();
                buffer.clear();
                Thread.sleep(1);
            }
            byte[] message = "C".getBytes();
            ByteBuffer buffer = ByteBuffer.wrap(message);
            client.write(buffer);
            client.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setDataToSend(String dataToSend) {
        this.dataToSend = dataToSend;
    }

    public String getDataFromServer() {
        return dataFromServer;
    }

    public void resetDataFromServer(){
        dataFromServer = "";
    }
}
