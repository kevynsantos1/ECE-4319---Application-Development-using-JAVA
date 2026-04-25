package week_11_SQLMY_Java;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class GameClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8081;

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine();

            String action;

            if (choice.equals("1")) {
                action = "register";
            } else if (choice.equals("2")) {
                action = "login";
            } else if (choice.equals("3")) {
                System.out.println("Goodbye.");
                break;
            } else {
                System.out.println("Invalid choice.");
                continue;
            }

            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            System.out.print("Enter password: ");
            String password = scanner.nextLine();


            try {
                Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

                outputStream.writeObject(action);
                outputStream.writeObject(username);
                outputStream.writeObject(password);


                String message = (String) inputStream.readObject();
                System.out.println("Server says: " + message);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
