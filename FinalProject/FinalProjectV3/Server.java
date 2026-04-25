package FinalProjectV3;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;


public class Server {
    private static int PORT = 8081;
    private static int queue_Length = 20;

    public static void main(String[] args) throws IOException {

        ServerFrame frame = new ServerFrame();
        frame.setSize(800, 600);
        frame.setTitle("Trivia Game_by Kevyn");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Connection connection = null;
        try {
            connection = DBConnectionv2.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        UserCRUD userCRUD = new UserCRUD(connection);

        System.out.println("Game Server started");

        try{
            ServerSocket serverSocket = new ServerSocket(PORT, queue_Length);


            GameSession gameSession = new GameSession(frame);
            frame.setGameSession(gameSession);

            while(true) {

                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket, userCRUD, gameSession, frame);
                clientHandler.start();
            }

        }catch (IOException ex){
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

    }
}

