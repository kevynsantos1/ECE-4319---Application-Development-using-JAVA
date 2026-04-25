package FinalProjectV3;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler extends Thread {
    private ServerFrame frame;
    private Socket socket;
    private UserCRUD userCRUD;
    private GameSession gameSession;
    private PlayerSession playerSession;

    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket socket, UserCRUD userCRUD, GameSession gameSession, ServerFrame frame) {
        this.socket = socket;
        this.userCRUD = userCRUD;
        this.gameSession = gameSession;
        this.frame = frame;
        this.playerSession = new PlayerSession();
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                String command = in.readLine();
                if (command == null) break;

                switch (command) {

                    case "REGISTER": {
                        String username = in.readLine();
                        String password = in.readLine();

                        boolean success = userCRUD.registerUser(username, password);
                        out.println(success ? "Registration successful." : "Registration failed. Username already taken");
                        break;
                    }

                    case "LOGIN": {
                        String username = in.readLine();
                        String password = in.readLine();

                        boolean success = userCRUD.loginUser(username, password);

                        if (success) {
                            playerSession.setUsername(username);
                            playerSession.setLoggedIn(true);
                            gameSession.addPlayer(this, username);
                            out.println("Login successful.");
                        } else {
                            out.println("Login failed. \nInvalid Username or Password");
                        }
                        break;
                    }

                    case "GET_STATUS": {
                        if (gameSession.hasGameStarted()) {
                            out.println(gameSession.isGameOver() ? "RESULTS" : "QUESTION");
                        } else {
                            out.println("WAIT");
                        }
                        break;
                    }

                    case "GET_QUESTION": {
                        Question q = gameSession.getCurrentQuestion();

                        if (q == null) {
                            out.println("NULL");
                        } else {
                            out.println(gameSession.getCurrentQuestionNumber());
                            out.println(q.getQuestion());
                            for (String opt : q.getOptions()) {
                                out.println(opt);
                            }
                            out.println(q.getCorrectIndex());
                        }
                        break;
                    }

                    case "ANSWER": {
                        int selectedIndex = Integer.parseInt(in.readLine());

                        if (playerSession.hasAnsweredCurrentQuestion()) {
                            out.println(-2);
                            out.println(gameSession.getPlayerScore(playerSession.getUsername()));
                            break;
                        }

                        playerSession.setAnsweredCurrentQuestion(true);

                        int correctIndex = gameSession.submitAnswer(
                                playerSession.getUsername(),
                                selectedIndex
                        );

                        out.println(correctIndex);
                        out.println(gameSession.getPlayerScore(playerSession.getUsername()));
                        break;
                    }

                    case "FINISH": {
                        userCRUD.updateHighScore(
                                playerSession.getUsername(),
                                gameSession.getPlayerScore(playerSession.getUsername())
                        );

                        List<User> leaderboard = userCRUD.getTopUsers(10);

                        out.println(leaderboard.size());
                        for (User u : leaderboard) {
                            out.println(u.getUsername());
                            out.println(u.getScore());
                        }
                        break;
                    }

                    case "GET_TIMER": {
                        out.println(gameSession.getQuestionTimeLimit());
                        break;
                    }

                    case "SET_TIMER": {
                        int seconds = Integer.parseInt(in.readLine());
                        gameSession.setQuestionTimeLimit(seconds);
                        break;
                    }
                }
            }

        } catch (Exception e) {
            if (playerSession.getUsername() != null && !playerSession.getUsername().isEmpty()) {
                gameSession.removePlayer(this, playerSession.getUsername());
            }
        }
    }

    public void resetAnsweredStatus() {
        playerSession.setAnsweredCurrentQuestion(false);
    }

    public boolean hasAnsweredCurrentQuestion() {
        return playerSession.hasAnsweredCurrentQuestion();
    }
}