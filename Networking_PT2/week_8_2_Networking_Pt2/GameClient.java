package week_8_2_Networking_Pt2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class GameClient {
    private static final String SERVER = "localhost";
    private static final int PORT = 8081;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER, PORT);
            ObjectInputStream inputStream =
                    new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputStream =
                    new ObjectOutputStream(socket.getOutputStream());

            String line;
            while ((line = (String) inputStream.readObject()) != null) {
                if (line.startsWith("Your answer is: ")) {
                    System.out.println(line);
                    Scanner userInput = new Scanner(System.in);
                    String clientAnswer = userInput.nextLine();

                    //send userInput back to server
                    outputStream.writeObject(clientAnswer);
                    outputStream.flush();
                } else {
                    System.out.println(line);
                }
            }
            socket.close();

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
