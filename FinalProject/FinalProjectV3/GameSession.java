package FinalProjectV3;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GameSession {
    private ServerFrame frame;
    private List<Question> questions;
    private int currentQuestionIndex;
    private boolean gameStarted;
    private Map<String, Integer> playerScores;
    private List<ClientHandler> players;
    private int questionTimeLimit;
    private boolean correctAnswerShown;

    public GameSession(ServerFrame frame) {
        this.frame = frame;
        this.questions = new ArrayList<>();
        this.currentQuestionIndex = 0;
        this.gameStarted = false;
        this.playerScores = new LinkedHashMap<>();
        this.players = new ArrayList<>();
        this.questionTimeLimit = 10;
        this.correctAnswerShown = false;
    }

    public synchronized void addPlayer(ClientHandler player, String username) {
        players.add(player);
        if (!playerScores.containsKey(username)) {
            playerScores.put(username, 0);
        }

        SwingUtilities.invokeLater(() -> frame.updatePlayersArea(playerScores));
    }

    public synchronized void removePlayer(ClientHandler player, String username) {
        players.remove(player);
        playerScores.remove(username);

        SwingUtilities.invokeLater(() -> {
            frame.updatePlayersArea(playerScores);
            frame.updateScoresArea(playerScores);
        });

        // If someone disconnects and the remaining players have all already answered,
        // reveal the answer immediately.
        maybeRevealCorrectAnswer();
    }

    public synchronized void startGame(String topic) {
        questions = loadTopicQuestions(topic);
        currentQuestionIndex = 0;
        gameStarted = true;
        correctAnswerShown = false;

        for (String username : playerScores.keySet()) {
            playerScores.put(username, 0);
        }

        for (ClientHandler player : players) {
            player.resetAnsweredStatus();
        }

        SwingUtilities.invokeLater(() -> {
            frame.displayCurrentQuestion(getCurrentQuestion());
            frame.updateScoresArea(playerScores);
        });
    }

    public synchronized boolean hasGameStarted() {
        return gameStarted;
    }

    public synchronized Question getCurrentQuestion() {
        if (questions == null || currentQuestionIndex >= questions.size()) {
            return null;
        }
        return questions.get(currentQuestionIndex);
    }

    public synchronized int getCurrentQuestionNumber() {
        return currentQuestionIndex + 1;
    }

    public synchronized int submitAnswer(String username, int selectedIndex) {
        Question current = getCurrentQuestion();
        if (current == null) {
            return -1;
        }

        int correctIndex = current.getCorrectIndex();

        if (selectedIndex == correctIndex) {
            int currentScore = playerScores.getOrDefault(username, 0);
            playerScores.put(username, currentScore + 1);
        }

        SwingUtilities.invokeLater(() -> frame.updateScoresArea(playerScores));

        maybeRevealCorrectAnswer();

        return correctIndex;
    }

    private synchronized boolean haveAllPlayersAnsweredCurrentQuestion() {
        if (players.isEmpty()) {
            return false;
        }

        for (ClientHandler player : players) {
            if (!player.hasAnsweredCurrentQuestion()) {
                return false;
            }
        }

        return true;
    }

    private synchronized void maybeRevealCorrectAnswer() {
        if (correctAnswerShown) {
            return;
        }

        Question current = getCurrentQuestion();
        if (current == null) {
            return;
        }

        if (!haveAllPlayersAnsweredCurrentQuestion()) {
            return;
        }

        correctAnswerShown = true;
        int correctIndex = current.getCorrectIndex();

        SwingUtilities.invokeLater(() -> frame.highlightCorrectAnswer(correctIndex));
    }

    public synchronized int getPlayerScore(String username) {
        return playerScores.getOrDefault(username, 0);
    }

    public synchronized void advanceQuestion() {
        currentQuestionIndex++;
        correctAnswerShown = false;

        if (currentQuestionIndex < questions.size()) {
            for (ClientHandler player : players) {
                player.resetAnsweredStatus();
            }

            SwingUtilities.invokeLater(() -> frame.displayCurrentQuestion(getCurrentQuestion()));
        } else {
            SwingUtilities.invokeLater(frame::showResultsScreen);
        }
    }

    public synchronized boolean isGameOver() {
        return questions != null && currentQuestionIndex >= questions.size();
    }

    private List<Question> loadTopicQuestions(String topic) {
        QuestionLoader loader = new QuestionLoader();
        String filePath = "";

        if (topic.equals("Memes")) {
            filePath = "src/FinalProjectV3/questions.txt";
        } else if (topic.equals("Video Games")) {
            filePath = "src/FinalProjectV3/games.txt";
        } else if (topic.equals("Soccer")) {
            filePath = "src/FinalProjectV3/soccer.txt";
        } else if (topic.equals("Movies")) {
            filePath = "src/FinalProjectV3/movies.txt";
        }

        return loader.readQuestionObjects(filePath);
    }

    public synchronized int getQuestionTimeLimit() {
        return questionTimeLimit;
    }

    public synchronized void setQuestionTimeLimit(int questionTimeLimit) {
        this.questionTimeLimit = questionTimeLimit;
    }
}