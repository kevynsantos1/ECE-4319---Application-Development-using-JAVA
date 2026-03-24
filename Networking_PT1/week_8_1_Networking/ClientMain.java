package week_8_1_Networking;

import javax.swing.*;
import java.io.IOException;

public class ClientMain {
    private static final int PORT_NUMBER = 8080;

    public static void main(String[] args) throws IOException, ClassNotFoundException{
        Client client = new Client(PORT_NUMBER);
        client.receiveMessageFromServer();
    }
}
