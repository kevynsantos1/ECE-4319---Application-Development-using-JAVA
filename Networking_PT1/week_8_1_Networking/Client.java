package week_8_1_Networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Client {
    private static final String SERVER = "localhost";
    private final int port;

    public Client(int port){
        this.port = port;
    }

    public void receiveMessageFromServer(){
        try {
            Socket socket = new Socket(SERVER, port);

            System.out.println("Client is connected to server: " + socket.getInetAddress().getHostName());

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            String message = (String) inputStream.readObject();
            if (!message.isEmpty()){
                System.out.println("Message from server: " + message);
            }

            socket.close();
        }
        catch (IOException e){
            System.out.println("IOException when connecting to server: " + SERVER);
            throw new RuntimeException(e);
        }
        catch (ClassNotFoundException e){
            System.out.println("Class not found when read from inputStream");
            throw new RuntimeException(e);
        }
    }

}
