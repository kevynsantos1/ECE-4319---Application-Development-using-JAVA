package week_8_1_Networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final int portNumber;
    private final int queueLength;

    public Server(int portNumber, int queueLength){
        this.portNumber = portNumber; //means the assigned portnumber = our global portnumber (white is local) & pink is global
        this.queueLength = queueLength;
    }

    public void receiveConnection() {
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber, queueLength);

            System.out.println("Server is up...");

            Socket socket = serverSocket.accept();
            System.out.println("Connection received from: " + socket.getInetAddress().getHostName());

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            //ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            outputStream.flush();

            outputStream.writeObject("Server: Hello Client!");
            outputStream.flush();

            socket.close();

        }
        catch (IOException exception) {
            System.out.println(("An IOException happened when creating a serverSocket on port: " + portNumber));
        }
    }
}
