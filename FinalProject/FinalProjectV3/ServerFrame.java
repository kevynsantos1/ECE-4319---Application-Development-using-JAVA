package FinalProjectV3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class ServerFrame extends JFrame {

    private JLabel questionLabel;
    private JButton[] optionButtons = new JButton[4];
    private JTextArea playersArea;
    private JTextArea scoresArea;
    private JPanel mainPanel;

    private JButton memesButton;
    private JButton gamesButton;
    private JButton soccerButton;
    private JButton moviesButton;
    private JButton startButton;
    private JButton nextButton;
    private String selectedTopic;

    private JTextField timerField;
    private JButton applyTimerButton;

    private GameSession gameSession;

    public ServerFrame() {

        selectedTopic = "";

        mainPanel = new JPanel(new BorderLayout());

        questionLabel = new JLabel("Host Dashboard - Waiting to start", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(questionLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JButton("Option " + (i + 1));
            centerPanel.add(optionButtons[i]);
        }
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel sidePanel = new JPanel(new GridLayout(2, 1));

        playersArea = new JTextArea();
        playersArea.setEditable(false);
        playersArea.setBorder(BorderFactory.createTitledBorder("Players"));

        scoresArea = new JTextArea();
        scoresArea.setEditable(false);
        scoresArea.setBorder(BorderFactory.createTitledBorder("Scores"));

        sidePanel.add(new JScrollPane(playersArea));
        sidePanel.add(new JScrollPane(scoresArea));

        mainPanel.add(sidePanel, BorderLayout.EAST);

        JPanel bottomPanel = new JPanel(new GridLayout(2,1));

        JPanel topicPanel = new JPanel(new GridLayout(1,4,10,10));
        memesButton = new JButton("Memes");
        gamesButton = new JButton("Video Games");
        soccerButton = new JButton("Soccer");
        moviesButton = new JButton("Movies");

        topicPanel.add(memesButton);
        topicPanel.add(gamesButton);
        topicPanel.add(soccerButton);
        topicPanel.add(moviesButton);

        JPanel controlPanel = new JPanel(new FlowLayout());
        startButton = new JButton("Start Game");
        nextButton = new JButton("Next Question");

        controlPanel.add(startButton);
        controlPanel.add(nextButton);

        timerField = new JTextField("10", 5);
        applyTimerButton = new JButton("Set Timer");

        controlPanel.add(new JLabel("Seconds: "));
        controlPanel.add(timerField);
        controlPanel.add(applyTimerButton);

        bottomPanel.add(topicPanel);
        bottomPanel.add(controlPanel);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        ButtonHandler handler = new ButtonHandler();
        memesButton.addActionListener(handler);
        gamesButton.addActionListener(handler);
        soccerButton.addActionListener(handler);
        moviesButton.addActionListener(handler);
        startButton.addActionListener(handler);
        nextButton.addActionListener(handler);
        applyTimerButton.addActionListener(handler);

        add(mainPanel);
    }

    private class ButtonHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource()==memesButton){
                selectedTopic = "Memes";
                questionLabel.setText("Selected Topic: Memes");
            }
            else if (e.getSource()==gamesButton){
                selectedTopic = "Video Games";
                questionLabel.setText("Selected Topic: Video Games");
            }
            else if (e.getSource()==soccerButton){
                selectedTopic = "Soccer";
                questionLabel.setText("Selected Topic: Soccer");
            }
            else if (e.getSource()==moviesButton){
                selectedTopic = "Movies";
                questionLabel.setText("Selected Topic: Movies");
            }
            else if (e.getSource() == startButton){
                if (gameSession == null){
                    JOptionPane.showMessageDialog(null, "Game session not ready.");
                    return;
                }
                if (selectedTopic.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please choose a topic first.");
                    return;
                }
                gameSession.startGame(selectedTopic);
            }
            else if (e.getSource()==nextButton){
                if (gameSession == null){
                    JOptionPane.showMessageDialog(null, "Game session not ready.");
                    return;
                }

                gameSession.advanceQuestion();
            }
            else if (e.getSource() == applyTimerButton) {
                if (gameSession == null) {
                    JOptionPane.showMessageDialog(null, "Game session not ready.");
                    return;
                }

                try {
                    int seconds = Integer.parseInt(timerField.getText().trim());
                    if (seconds <= 0) {
                        JOptionPane.showMessageDialog(null, "Timer must be greater than 0.");
                        return;
                    }

                    gameSession.setQuestionTimeLimit(seconds);
                    JOptionPane.showMessageDialog(null, "Timer set to " + seconds + " seconds.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Enter a valid number.");
                }
            }
        }
    }

    public void displayCurrentQuestion(Question q) {
        if (q == null) {
            questionLabel.setText("No more questions.");
            return;
        }

        questionLabel.setText(q.getQuestion());

        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(q.getOptions()[i]);
            optionButtons[i].setBackground(null);
            optionButtons[i].setOpaque(true);
        }
    }

    public void highlightCorrectAnswer(int correctIndex) {
        for (int i = 0; i < 4; i++) {
            if (i == correctIndex) {
                optionButtons[i].setBackground(Color.GREEN);
            } else {
                optionButtons[i].setBackground(null);
            }
            optionButtons[i].setOpaque(true);
        }
    }

    public void updatePlayersArea(Map<String, Integer> players) {
        StringBuilder sb = new StringBuilder();
        for (String username : players.keySet()) {
            sb.append(username).append("\n");
        }
        playersArea.setText(sb.toString());
    }

    public void updateScoresArea(Map<String, Integer> scores) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        scoresArea.setText(sb.toString());
    }

    public void showResultsScreen() {
        questionLabel.setText("Game Over");
    }

    public void setGameSession(GameSession gameSession) {
        this.gameSession = gameSession;
    }
}