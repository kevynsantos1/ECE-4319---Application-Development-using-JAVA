package week_8_2_Networking_Pt2;

import javax.swing.JFrame;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static int PORT = 8081;
    private static int queue_Length = 20;
    public static List<Question> questions = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        loadSampleQuestions();

        ServerFrame frame = new ServerFrame();
        frame.setSize(800, 600);
        frame.setTitle("Trivia Game_by Kevyn");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        System.out.println("Game Server started");

        try{
            ServerSocket serverSocket = new ServerSocket(PORT, queue_Length);
            Socket socket = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(frame,socket,questions);
            clientHandler.sendQuestionsAndReceiveAnswers();

        }catch (IOException ex){
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    private static void loadSampleQuestions() {
        questions.add(new Question("What is the capital of France?",
                new String[]{"Paris", "London", "Berlin", "Rome"}, 0));
        questions.add(new Question("Which planet is known as the Red Planet?",
                new String[]{"Earth", "Venus", "Mars", "Jupiter"}, 2));
    }

}

