package gameCom;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class Server extends Thread {
    public void run(){
        try {
            // Get the selector
            Selector selector = Selector.open();
            System.out.println("Selector is open for making connection: " + selector.isOpen());
            // Get the server socket channel and register using selector
            ServerSocketChannel SS = ServerSocketChannel.open();
            InetSocketAddress hostAddress = new InetSocketAddress("localhost", 5000);
            SS.bind(hostAddress);
            SS.configureBlocking(false);
            int ops = SS.validOps();
            SS.register(selector, ops, null);
            HashMap<Integer, String> lastDataFrom = new HashMap<>();
            for (;;) {
                System.out.println("Waiting for the select operation...");
                int noOfKeys = selector.select(5000);
                if (noOfKeys == 0) break;
                Set selectedKeys = selector.selectedKeys();
                Iterator itr = selectedKeys.iterator();
                while (itr.hasNext()) {
                    SelectionKey ky = (SelectionKey) itr.next();
                    if (ky.isAcceptable()) {
                        // The new client connection is accepted
                        SocketChannel client = SS.accept();
                        client.configureBlocking(false);
                        // The new connection is added to a selector
                        client.register(selector, SelectionKey.OP_READ);
                    }
                    else if (ky.isReadable()) {
                        // Data is read from the client
                        SocketChannel client = (SocketChannel) ky.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(256);
                        client.read(buffer);
                        String output = new String(buffer.array()).trim();
                        if (Objects.equals(output, "C")) {
                            client.close();
                            System.out.println("The Client messages are complete; close the session.");
                        }
                        else{
                            int toSendKey = Character.getNumericValue(output.charAt(1));
                            lastDataFrom.put(toSendKey, output);
                            String toSend = "";
                            try{
                                for (int sendFromKey : lastDataFrom.keySet()) {
                                    if (sendFromKey != toSendKey){
                                        toSend += lastDataFrom.get(sendFromKey);
                                    }
                                }
                            } catch (IndexOutOfBoundsException ignored){}
                            if (!Objects.equals(toSend, "")){
                                byte[] toSendBytes = toSend.getBytes();
                                ByteBuffer toSendBuffer = ByteBuffer.wrap(toSendBytes);
                                client.write(toSendBuffer);
                                System.out.println("Sending: " + toSend + " to: " + toSendKey);
                            }
                            else{
                                byte[] toSendBytes = "-".getBytes();
                                ByteBuffer toSendBuffer = ByteBuffer.wrap(toSendBytes);
                                client.write(toSendBuffer);
                            }
                        }

                        System.out.println(lastDataFrom.toString());
                    }
                    itr.remove();
                } // end of while loop
            } // end of for loop
            selector.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
