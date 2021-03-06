package gameCom;

import serverConsole.MainServerConsole;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class Server extends Thread {

    private static MainServerConsole console;

    public void run(){
        try {
            Selector selector = Selector.open();
            ServerSocketChannel SS = ServerSocketChannel.open();
            InetSocketAddress hostAddress = new InetSocketAddress("localhost", 5000);

            SS.bind(hostAddress);
            SS.configureBlocking(false);
            int ops = SS.validOps();
            SS.register(selector, ops, null);
            HashMap<String, String> lastDataFrom = new HashMap<>();
            promptConsole("Server is now open.");
            for (;;) {
                int noOfKeys = selector.select(5000);
                if (noOfKeys == 0) break;
                Set selectedKeys = selector.selectedKeys();
                Iterator itr = selectedKeys.iterator();
                while (itr.hasNext()) {
                    SelectionKey ky = (SelectionKey) itr.next();
                    if (ky.isAcceptable()) {
                        SocketChannel client = SS.accept();
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);
                        promptConsole("Client connected.");
                    }
                    else if (ky.isReadable()) {
                        // Data is read from the client
                        SocketChannel client = (SocketChannel) ky.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        try{
                            client.read(buffer);
                        } catch (IOException e){
                            client.close();
                            promptConsole("Client disconnected.");
                            itr.remove();
                            continue;
                        }
                        String output = new String(buffer.array()).trim();
                        if (!Objects.equals(output, "") && output.charAt(0) == '{' && output.charAt(output.length() - 1) == '}'){ // Previous was without first condition.
                            output = output.substring(1, output.length() - 1);
                            if (Objects.equals(output, "C")) {
                                client.close();
                                promptConsole("Client disconnected.");
                            }
                            else{
                                String toSendKey;
                                try{
                                    toSendKey = output.split("_")[22].split("###")[0];
                                } catch (IndexOutOfBoundsException e){
                                    toSendKey = output.split("###")[0].substring(1);
                                }

                                lastDataFrom.put(toSendKey, output);
                                String toSend = "{";
                                try{
                                    for (String sendFromKey : lastDataFrom.keySet()) {
                                        if (!Objects.equals(sendFromKey, toSendKey)){
                                            toSend += lastDataFrom.get(sendFromKey);
                                        }
                                    }
                                } catch (IndexOutOfBoundsException ignored){}
                                if (!Objects.equals(toSend, "{")){
                                    byte[] toSendBytes = (toSend + "}").getBytes();
                                    ByteBuffer toSendBuffer = ByteBuffer.wrap(toSendBytes);
                                    client.write(toSendBuffer);
                                }
                                else{
                                    byte[] toSendBytes = "X".getBytes();
                                    ByteBuffer toSendBuffer = ByteBuffer.wrap(toSendBytes);
                                    client.write(toSendBuffer);
                                }
                            }
                        } else{
                            byte[] toSendBytes = "X".getBytes();
                            ByteBuffer toSendBuffer = ByteBuffer.wrap(toSendBytes);
                            try{
                                client.write(toSendBuffer);
                            } catch (IOException ignored){}
                        }
                    }
                    itr.remove();
                } // end of while loop
            } // end of for loop
            selector.close();
        } catch (IOException e){
            e.printStackTrace();
            promptConsole("An error occurred.");
        }
        promptConsole("Server closed.");
    }

    public static void setConsole(MainServerConsole console){
        Server.console = console;
    }

    private static void promptConsole(String text){
        System.out.println(text);
        Server.console.promptConsole(text);
    }

    public static void runServer(){
        Server s = new Server();
        s.start();
    }
}