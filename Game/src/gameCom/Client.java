package gameCom;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Objects;

public class Client extends Thread {
    private String id;
    private String ipToConnectTo = "localhost";
    private String dataToSend = "";
    private String dataFromServer = "";
    private boolean disconnected;

    public Client(String id) {
        this.id = id;
    }

    public Client(String id, String ipToConnectTo) {
        this.id = id;
        this.ipToConnectTo = ipToConnectTo;
        this.disconnected = false;
    }

    public void run() {
        try {
            InetSocketAddress hA = new InetSocketAddress(ipToConnectTo, 5000); // Used to be 5000 but is problematic
            SocketChannel client;
            try{
                client = SocketChannel.open(hA);
            } catch (ConnectException e){
                disconnected = true;
                return;
            }
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
                Thread.sleep(15);
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

    public boolean isDisconnected(){
        return disconnected;
    }
}
