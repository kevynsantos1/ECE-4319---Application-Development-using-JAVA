package FinalProjectV3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CardLayoutFrame extends JFrame {
    //all JPanel Variables
    private JPanel cardPanel;
    private JPanel welcomePanel;
    private JPanel usernamePanel;
    private JPanel waitingPanel;
    private JPanel gamePanel;
    private JPanel resultPanel;

    //all JButton Variables
    private JButton startButton;
    private JButton restartButton;
    private JButton[] optionButtons = new JButton[4];

    //CardLayout Variable - allows us to switch between "cards"/panels
    private CardLayout cardLayout;

    //Username variables - one is just to create a textfield and naother is to store username.
    private JTextField inputField;
    private String username = new String();
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton loginButton;
    private boolean answered = false;

    //questionlabel is used to change the questions text when switching questions
    private JLabel questionLabel;

    //Question variables. index is used to increment the questions properly and correctcount is used to keep track of score
    private int currentQuestionIndex;
    private int correctCount = 0;

    //List variables. question pair
    private List<Question> questions;

    private int totalQuestions = 10;

    private Timer timer; //timer
    private int timeLeft = 10; //used to know how many loops are done before our actionhandler skips the question
    private JLabel timerLabel; //global label so that it is updated from outside the constructor its in

    private Thread statusThread;
    private boolean listeningForUpdates = false;
    private int questionTimeLimit = 10;

    private QuizClientConnection connection;

    private List<User> leaderboard;

    public CardLayoutFrame() {

        connection = new QuizClientConnection("localhost", 8081);

        currentQuestionIndex = 0; //we start at index 0

        //creating the lists
        questions = new ArrayList<>();

        leaderboard = new ArrayList<>();

        //creating the layout so we can switch panels
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        //Welcome page
        createWelcomePanel();

        //username page
        createUsernamePanel();

        createWaitingPanel();

        //Game Page
        createGamePanel();

        //we need to use handler so that when our buttons are pressed the actionlistener can work
        ButtonHandler handler = new ButtonHandler();
        startButton.addActionListener(handler);

        registerButton.addActionListener(handler);
        loginButton.addActionListener(handler);

        //add all panels to cardPanel, and label them to use that label to switch
        cardPanel.add(welcomePanel,"W");
        cardPanel.add(usernamePanel,"U");
        cardPanel.add(waitingPanel, "WAIT");
        cardPanel.add(gamePanel,"G");
        add(cardPanel);
    }

    //we implement the ActionListener interface
    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == startButton) { //e.getSource is like "event.getSource"
                cardLayout.show(cardPanel, "U"); //if a certain button is pressed then show a certain panel
            } else if (e.getSource() == registerButton) {
                String enteredUsername = inputField.getText().trim();
                String enteredPassword = new String(passwordField.getPassword());

                if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a username and password.");
                    return;
                }

                String response = connection.registerUser(enteredUsername, enteredPassword);
                JOptionPane.showMessageDialog(null, response);
            }else if (e.getSource() == loginButton) {
                String enteredUsername = inputField.getText().trim();
                String enteredPassword = new String(passwordField.getPassword());

                if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a username and password.");
                    return;
                }

                String response = connection.loginUser(enteredUsername, enteredPassword);
                JOptionPane.showMessageDialog(null, response);

                if (response.equals("Login successful.")) {
                    username = enteredUsername;
                    cardLayout.show(cardPanel, "WAIT");
                    startStatusListener();
                }
            }
        }
    }

    //we implement another actionlistener for our buttons on the questions.
    private class OptionButtonHandler implements ActionListener{

        private int index; //used to take the index of the button pressed at the time

        public OptionButtonHandler(int index){ //Constructor to take button id
            this.index = index; //Assign the index/id to this handler object
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (answered) return;
            answered = true;

            timer.stop(); //stop the timer once the user makes a decision
            setButtonsEnabled(false);


            int[] result = connection.submitAnswer(index);
            int correctIndex = result[0];
            correctCount = result[1];
            if (correctIndex == -2){
                showAutoCloseDialog(
                        "You already answered this question",
                        1500
                );
                return;
            }
            highlightClientAnswers(index, correctIndex);

            if (index == correctIndex) {
                showAutoCloseDialog(
                        "Correct! +1 point\n" +
                                "Current Score: " + correctCount + "/" + (currentQuestionIndex + 1),
                        1200
                ); //if the index of the button pressed is the same as the correct answer value it shows a popup letting user know they got a point and their current score
            } else {
                showAutoCloseDialog(
                        "Wrong!\nCorrect answer was: " +
                                questions.get(0).getOptions()[correctIndex] +
                                "\nCurrent Score: " + correctCount + "/" + (currentQuestionIndex + 1),
                        1500
                ); //shows a popup letting user know they got the question incorrect and no point will be given. also gives current score
            }


        }
    }

    private void createWelcomePanel(){ //creates welcome panel with the label and button that goes to username page
        welcomePanel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Welcome!", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD,24));
        welcomePanel.add(title,BorderLayout.CENTER);

        JPanel startPanel = new JPanel(new FlowLayout());
        startButton = new JButton("Login or Register");
        startPanel.add(startButton);

        welcomePanel.add(startPanel, BorderLayout.SOUTH);
    }

    private void createUsernamePanel(){ //login and password textfield, and two buttons one to register and one to login
        usernamePanel = new JPanel(new BorderLayout());
        JPanel userPanel = new JPanel(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Please enter details below. You must register before logging in.", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial",Font.PLAIN,24));

        JLabel usernameLabel = new JLabel("Enter Username: ");
        usernameLabel.setFont(new Font("Arial",Font.PLAIN,24));

        inputField = new JTextField();
        inputField.setFont(new Font("Arial",Font.PLAIN,24));

        JPanel usernameInfo = new JPanel(new BorderLayout());

        usernameInfo.add(welcomeLabel, BorderLayout.NORTH);
        usernameInfo.add(usernameLabel, BorderLayout.CENTER);
        usernameInfo.add(inputField, BorderLayout.SOUTH);

        userPanel.add(usernameInfo, BorderLayout.NORTH);

        JPanel passwordInfo = new JPanel(new BorderLayout());

        JLabel passwordLabel = new JLabel("Enter Password: ");
        passwordLabel.setFont(new Font("Arial",Font.PLAIN,24));

        passwordInfo.add(passwordLabel, BorderLayout.NORTH);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 24));
        passwordInfo.add(passwordField, BorderLayout.SOUTH);

        userPanel.add(passwordInfo, BorderLayout.CENTER);

        JPanel logregPanel = new JPanel(new FlowLayout());

        registerButton = new JButton("Register");
        loginButton = new JButton("Login");

        logregPanel.add(registerButton);
        logregPanel.add(loginButton);

        userPanel.add(logregPanel, BorderLayout.SOUTH);

        usernamePanel.add(userPanel, BorderLayout.NORTH);

    }

    private void createWaitingPanel() {
        waitingPanel = new JPanel(new BorderLayout());
        JLabel waitingLabel = new JLabel("Waiting for host to start the game...", SwingConstants.CENTER);
        waitingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        waitingPanel.add(waitingLabel, BorderLayout.CENTER);
    }


    private void createGamePanel(){ //this creates the question label, the timer, and the 4 options for the question
        gamePanel = new JPanel(new BorderLayout());
        JPanel orgPanel = new JPanel(new BorderLayout());
        questionLabel = new JLabel("Question 1:", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 24));
        orgPanel.add(questionLabel,BorderLayout.WEST);

        timerLabel = new JLabel("Time: ", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial",Font.BOLD,20));
        orgPanel.add(timerLabel, BorderLayout.EAST);

        gamePanel.add(orgPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2,2,10,10));
        for (int i = 0; i<4;i++){
            optionButtons[i] = new JButton("Option " + (i+1));
            optionButtons[i].addActionListener(new OptionButtonHandler(i)); //Instantiate 4 different handlers with diff ids, and then register
            buttonPanel.add(optionButtons[i]);
        }
        gamePanel.add(buttonPanel, BorderLayout.CENTER);

        //here we create a timer that goes off every second, it decrements from time left (that starts at 10) and replaces the text
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                timerLabel.setText("Time left: " + timeLeft);

                //if timeLeft hits 0 then we stop timer, show the user they ran out of time and wont get any points and then shows next question stuff
                if (timeLeft <= 0&& !answered) {
                    timer.stop();
                    answered = true;

                    int[] result = connection.submitAnswer(-1);
                    int correctIndex = result[0];
                    correctCount = result[1];

                    if (correctIndex == -2) {
                        return;
                    }

                    highlightClientAnswers(-1, correctIndex);

                    showAutoCloseDialog(
                            "Time's up!\nCorrect answer was: " +
                                    questions.get(0).getOptions()[correctIndex] +
                                    "\nCurrent Score: " + correctCount + "/" + (currentQuestionIndex + 1),
                            1500
                    );

                }
            }
        });
    }

    //creates the label that shows results, the score, and a button to go back to topic screen
    private void createResultPanel() {
        if (resultPanel != null) {
            cardPanel.remove(resultPanel);
        }

        resultPanel = new JPanel(new BorderLayout());

        JPanel currentUserStuff = new JPanel(new BorderLayout());

        JLabel resultLabel = new JLabel(username + "'s Results:", SwingConstants.CENTER); //to use username we put createResultPanel in the loadnextquestions method. we did this bc we need username to be filled before creating the results panel to use it here
        resultLabel.setFont(new Font("Arial", Font.BOLD, 32));
        currentUserStuff.add(resultLabel, BorderLayout.NORTH);

        String correctLabel = correctCount + "/" + totalQuestions + " or " +
                ((correctCount / (double) totalQuestions) * 100) + "%"; //before or show how many out of the 10 questions they got right, second shows percentage

        JLabel resultLabel2 = new JLabel(correctLabel,SwingConstants.CENTER);
        resultLabel2.setFont(new Font("Arial",Font.BOLD, 24));
        currentUserStuff.add(resultLabel2, BorderLayout.CENTER);

        resultPanel.add(currentUserStuff, BorderLayout.NORTH);

        JPanel leaderboardPanel = new JPanel();
        leaderboardPanel.setLayout(new BoxLayout(leaderboardPanel, BoxLayout.Y_AXIS));

        JLabel leaderboardTitle = new JLabel("Top 10 Scorers (All-Time)", SwingConstants.CENTER);
        leaderboardTitle.setFont(new Font("Arial", Font.BOLD, 26));
        leaderboardTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        leaderboardPanel.add(leaderboardTitle);
        leaderboardPanel.add(Box.createVerticalStrut(10));

        if (leaderboard != null && !leaderboard.isEmpty()) {
            for (int i = 0; i < leaderboard.size(); i++) {
                User user = leaderboard.get(i);
                JLabel entry = new JLabel((i + 1) + ". " + user.getUsername() + " - " + user.getScore());
                entry.setFont(new Font("Arial", Font.PLAIN, 20));
                entry.setAlignmentX(Component.CENTER_ALIGNMENT);
                leaderboardPanel.add(entry);
            }
        } else {
            JLabel emptyLabel = new JLabel("No leaderboard data available.");
            emptyLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            leaderboardPanel.add(emptyLabel);
        }
        resultPanel.add(leaderboardPanel, BorderLayout.CENTER);

        JPanel restartPanel = new JPanel(new FlowLayout());
        restartButton = new JButton("Start Again");
        restartPanel.add(restartButton);

        resultPanel.add(restartPanel, BorderLayout.SOUTH);

        cardPanel.add(resultPanel,"R");
        cardLayout.show(cardPanel, "R");

        restartButton.addActionListener(new ActionListener() { //we create an actionListener just for restartButton because if we declare it any earlier then we cant use username variable
            @Override
            public void actionPerformed(ActionEvent e) {
                currentQuestionIndex = 0; //clears the question index
                correctCount = 0; //clears the pointCounter
                cardLayout.show(cardPanel, "WAIT"); //goes back to topic
            }
        });
    }

    private void loadNextQuestion(){ //this method actually loads the questions into the labels and buttons
        if (questions == null || questions.isEmpty()) {
            return;
        }
        answered = false;
        setButtonsEnabled(true);

        Question currentQuestion = questions.get(0);
        questionLabel.setText(currentQuestion.getQuestion());

        for (int i = 0; i < 4; i++) {
            optionButtons[i].setBackground(null);
            optionButtons[i].setOpaque(true);
            optionButtons[i].setText(currentQuestion.getOptions()[i]);
        }

        timeLeft = questionTimeLimit; //resets timeLeft variable to 10 after every question is reloaded
        timerLabel.setText("Time left: " + questionTimeLimit); //resets text
        timer.start(); //we start the timer back up once we load a new question
    }

    private void highlightClientAnswers(int selectedIndex, int correctIndex) {
        for (int i = 0; i < 4; i++) {
            if (i == correctIndex) {
                optionButtons[i].setBackground(Color.GREEN);
            } else if (i == selectedIndex) {
                optionButtons[i].setBackground(Color.RED);
            } else {
                optionButtons[i].setBackground(null);
            }
            optionButtons[i].setOpaque(true);
        }
    }

    private void startStatusListener() {
        if (listeningForUpdates) return;
        listeningForUpdates = true;

        statusThread = new Thread(() -> {
            String lastQuestionText = "";
            String lastStatus = "";

            while (listeningForUpdates) {
                try {
                    String status = connection.getGameStatus();

                    if (status.equals("QUESTION")) {
                        Question currentQuestion = connection.getCurrentQuestion();
                        int serverQuestionNumber = connection.getCurrentQuestionNumberFromServer();

                        if (currentQuestion != null && !currentQuestion.getQuestion().equals(lastQuestionText)){
                            int newTimeLimit = connection.getQuestionTimeLimit();

                            boolean isNewGameStart =
                                    lastStatus.equals("") ||
                                            lastStatus.equals("WAIT") ||
                                            lastStatus.equals("RESULTS");

                            boolean hostSkippedQuestion =
                                    !isNewGameStart &&
                                            !answered &&
                                            timeLeft > 0;

                            lastQuestionText = currentQuestion.getQuestion();

                            SwingUtilities.invokeLater(() -> {
                                currentQuestionIndex = serverQuestionNumber - 1;
                                if (hostSkippedQuestion) {
                                    timer.stop();
                                    setButtonsEnabled(false);

                                    showAutoCloseDialog(
                                            "Host skipped this question.\n" +
                                                    "No answer was selected.\n" +
                                                    "Current Score: " + correctCount + "/" + currentQuestionIndex,
                                            1200
                                    );
                                }
                                if (isNewGameStart){
                                    currentQuestionIndex = 0;
                                    correctCount = 0;
                                    answered = false;
                                    leaderboard.clear();
                                    if (resultPanel != null) {
                                        cardPanel.remove(resultPanel);
                                        resultPanel = null;
                                    }
                                }
                                questionTimeLimit = newTimeLimit;
                                questions.clear();
                                questions.add(currentQuestion);
                                loadNextQuestion();
                                cardLayout.show(cardPanel, "G");
                            });
                        }

                    } else if (status.equals("RESULTS") && !lastStatus.equals("RESULTS")) {
                        leaderboard = connection.finishQuizAndGetLeaderboard();

                        SwingUtilities.invokeLater(() -> {
                            createResultPanel();
                            cardLayout.show(cardPanel, "R");
                        });
                    }

                    lastStatus = status;
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                    listeningForUpdates = false;
                }
            }
        });

        statusThread.start();
    }

    private void setButtonsEnabled(boolean enabled) {
        for (JButton button : optionButtons) {
            button.setEnabled(enabled);
        }
    }

    private void showAutoCloseDialog(String message, int millis) {
        final JOptionPane optionPane = new JOptionPane(
                message,
                JOptionPane.INFORMATION_MESSAGE
        );

        final JDialog dialog = optionPane.createDialog(this, "Trivia Update");
        dialog.setModal(false);

        Timer closeTimer = new Timer(millis, e -> dialog.dispose());
        closeTimer.setRepeats(false);
        closeTimer.start();

        dialog.setVisible(true);
    }
}