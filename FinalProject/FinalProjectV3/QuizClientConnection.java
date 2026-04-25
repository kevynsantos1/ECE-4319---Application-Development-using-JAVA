package FinalProjectV3;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class QuizClientConnection {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private int currentQuestionNumberFromServer = 0;

    public QuizClientConnection(String host, int port) {
        try {
            socket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized String registerUser(String username, String password) {
        try {
            out.println("REGISTER");
            out.println(username);
            out.println(password);
            return in.readLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized String loginUser(String username, String password) {
        try {
            out.println("LOGIN");
            out.println(username);
            out.println(password);
            return in.readLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized String getGameStatus() {
        try {
            out.println("GET_STATUS");
            return in.readLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized Question getCurrentQuestion() {
        try {
            out.println("GET_QUESTION");

            String firstLine = in.readLine();
            if (firstLine.equals("NULL")) return null;

            currentQuestionNumberFromServer = Integer.parseInt(firstLine);

            String questionText = in.readLine();

            String[] options = new String[4];
            for (int i = 0; i < 4; i++) {
                options[i] = in.readLine();
            }

            int correctIndex = Integer.parseInt(in.readLine());

            return new Question(questionText, options, correctIndex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized int getCurrentQuestionNumberFromServer() {
        return currentQuestionNumberFromServer;
    }

    public synchronized int[] submitAnswer(int selectedIndex) {
        try {
            out.println("ANSWER");
            out.println(selectedIndex);

            int correctIndex = Integer.parseInt(in.readLine());
            int score = Integer.parseInt(in.readLine());

            return new int[]{correctIndex, score};
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized List<User> finishQuizAndGetLeaderboard() {
        try {
            out.println("FINISH");

            int size = Integer.parseInt(in.readLine());
            List<User> leaderboard = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                String username = in.readLine();
                int score = Integer.parseInt(in.readLine());
                leaderboard.add(new User(username, score));
            }

            return leaderboard;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized int getQuestionTimeLimit() {
        try {
            out.println("GET_TIMER");
            return Integer.parseInt(in.readLine());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}