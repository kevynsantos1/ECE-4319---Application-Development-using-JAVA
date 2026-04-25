package week_11_SQL_Java;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class GameClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8081;

    public static void main(String[] args){
        System.out.println("Connecting to server...");

        try{
            Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your username below: ");
            String username = scanner.nextLine();
            outputStream.writeObject(username);

            String message = (String) inputStream.readObject();
            System.out.println("Server says: "+message);

        }catch(IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
