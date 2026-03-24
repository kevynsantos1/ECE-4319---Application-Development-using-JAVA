package week_8_1_Networking;

public class ServerMain {
    private static int portNumber = 8080;
    private static int queueLength = 10;

    public static void main (String[] args) {
        Server server = new Server(portNumber, queueLength);
        server.receiveConnection();
    }
}
