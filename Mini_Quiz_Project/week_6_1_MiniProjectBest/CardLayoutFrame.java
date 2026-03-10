package week_6_1_MiniProjectBest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CardLayoutFrame extends JFrame {
    //all JPanel Variables
    private JPanel cardPanel;
    private JPanel welcomePanel;
    private JPanel usernamePanel;
    private JPanel topicPanel;
    private JPanel gamePanel;
    private JPanel resultPanel;

    //all JButton Variables
    private JButton startButton;
    private JButton usernameButton;
    private JButton forwardButton;
    private JButton restartButton;
    private JButton[] optionButtons = new JButton[4];
    private JButton[] topicButtons = new JButton[4];

    //CardLayout Variable - allows us to switch between "cards"/panels
    private CardLayout cardLayout;

    //Username variables - one is just to create a textfield and naother is to store username.
    private JTextField inputField;
    private String username = new String();

    //filePath we use this variable to switch the src file for questions
    private String filePath = "";

    //questionlabel is used to change the questions text when switching questions
    private JLabel questionLabel;

    //Question variables. index is used to increment the questions properly and correctcount is used to keep track of score
    private int currentQuestionIndex;
    private int correctCount = 0;

    //List variables. question pair
    private List<List<String>> questionPair; //we can use methods like arraylist.add. this is a 2d table
    private List<Integer> correctAnswers; //this is a 1d list since we only want to know the correct answer for each question

    private Timer timer; //timer
    private int timeLeft = 10; //used to know how many loops are done before our actionhandler skips the question
    private JLabel timerLabel; //global label so that it is updated from outside the constructor its in

    public CardLayoutFrame() {

        currentQuestionIndex = 0; //we start at index 0

        //creating the lists
        questionPair = new ArrayList<>();
        correctAnswers = new ArrayList<>();

        //creating the layout so we can switch panels
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        //Welcome page
        createWelcomePanel();

        //username page
        createUsernamePanel();

        //topic page
        createTopicPanel();

        //Game Page
        createGamePanel();

        //we need to use handler so that when our buttons are pressed the actionlistener can work
        ButtonHandler handler = new ButtonHandler();
        startButton.addActionListener(handler);
        for (int i = 0; i<4; i++){
            topicButtons[i].addActionListener(handler);
        }
        forwardButton.addActionListener(handler);
        usernameButton.addActionListener(handler);

        //add all panels to cardPanel, and label them to use that label to switch
        cardPanel.add(welcomePanel,"W");
        cardPanel.add(usernamePanel,"U");
        cardPanel.add(topicPanel, "T");
        cardPanel.add(gamePanel,"G");
        add(cardPanel);
    }

    //we implement the ActionListener interface
    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == startButton) { //e.getSource is like "event.getSource"
                cardLayout.show(cardPanel, "U"); //if a certain button is pressed then show a certain panel
            } else if (e.getSource() == forwardButton) {
                cardLayout.show(cardPanel, "T");
            } else if (e.getSource() == topicButtons[0]) {
                filePath = "src/week_6_1_MiniProjectBest/questions.txt"; //our loadQuestionsFromFile method uses different txt files to load different questions
                loadQuestionsFromFile(filePath); //we use this method to load different questions based on which topic button is pressed.
                loadNextQuestion(); //this method puts them into the correct label and buttons.
                cardLayout.show(cardPanel, "G");
            } else if (e.getSource() == topicButtons[1]) {
                filePath = "src/week_6_1_MiniProjectBest/games.txt";
                loadQuestionsFromFile(filePath);
                loadNextQuestion();
                cardLayout.show(cardPanel, "G");
            } else if (e.getSource() == topicButtons[2]) {
                filePath = "src/week_6_1_MiniProjectBest/soccer.txt";
                loadQuestionsFromFile(filePath);
                loadNextQuestion();
                cardLayout.show(cardPanel, "G");
            } else if (e.getSource() == topicButtons[3]) {
                filePath = "src/week_6_1_MiniProjectBest/movies.txt";
                loadQuestionsFromFile(filePath);
                loadNextQuestion();
                cardLayout.show(cardPanel, "G");
            }
            else if (e.getSource() == usernameButton){ //if the user presses the button the text inside the inputfield is saved as the username
                username = String.format(inputField.getText());
                JOptionPane.showMessageDialog(null, "Welcome " + username + "!"); //a popup is shown to show we received their username
                System.out.println(username);
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
            timer.stop(); //stop the timer once the user makes a decision

            if (index == (correctAnswers.get(currentQuestionIndex))){ //if the index of the button pressed is the same as the correct answer value
                correctCount++; //gives one point
                JOptionPane.showMessageDialog(null, "Correct! +1 point\n" +
                        "Current Score: " + correctCount + "/" + (currentQuestionIndex + 1)); //shows a popup letting user know they got a point and their current score
            }
            else{
                JOptionPane.showMessageDialog(null, "WRONG! NO POINT FOR YOU!\n" +
                        "Current Score: " + correctCount + "/" + (currentQuestionIndex + 1)); //shows a popup letting user know they got the question incorrect and no point will be given. also gives current score
            }

            currentQuestionIndex++; //increments the index so the correct question and options are shown
            loadNextQuestion(); //applies the correct question and options to their respective labels/buttons
        }
    }

    private void createWelcomePanel(){ //creates welcome panel with the label and button that goes to username page
        welcomePanel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Welcome!", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD,24));
        welcomePanel.add(title,BorderLayout.CENTER);

        JPanel startPanel = new JPanel(new FlowLayout());
        startButton = new JButton("Pick a username");
        startPanel.add(startButton);

        welcomePanel.add(startPanel, BorderLayout.SOUTH);
    }

    private void createUsernamePanel(){ //creates username with label, textfield, and two buttons
        usernamePanel = new JPanel(new BorderLayout());
        JPanel userPanel = new JPanel(new BorderLayout());

        inputField = new JTextField("Enter a Username");
        inputField.setFont(new Font("Arial",Font.PLAIN,24));
        userPanel.add(inputField, BorderLayout.NORTH);

        usernameButton = new JButton("Save name");
        userPanel.add(usernameButton, BorderLayout.CENTER);
        usernamePanel.add(userPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        forwardButton = new JButton("Pick a quiz topic");
        buttonPanel.add(forwardButton);

        usernamePanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void createTopicPanel(){ //this panel is for the user to pick what topic their quiz would be
        topicPanel = new JPanel(new BorderLayout());
        JLabel topicLabel = new JLabel("Please pick a topic.", SwingConstants.CENTER);
        topicLabel.setFont(new Font("Arial", Font.BOLD, 32));
        topicPanel.add(topicLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2,2,10,10) );
        topicButtons[0] = new JButton("Memes");
        topicButtons[1] = new JButton("Video Games");
        topicButtons[2] = new JButton("Soccer");
        topicButtons[3] = new JButton("Movies");
        for (int i = 0; i<4;i++){
            buttonPanel.add(topicButtons[i]);
        }
        topicPanel.add(buttonPanel, BorderLayout.CENTER);
    }

    private void createGamePanel(){ //this creates the question label, the timer, and the 4 options for the question
        gamePanel = new JPanel(new BorderLayout());
        JPanel orgPanel = new JPanel(new BorderLayout());
        questionLabel = new JLabel("Question #1", SwingConstants.CENTER);
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
                if (timeLeft <= 0) {
                    timer.stop();
                    JOptionPane.showMessageDialog(null, "Time's up! \nNo point for you!");
                    currentQuestionIndex++;
                    loadNextQuestion();
                }
            }
        });
    }

    //creates the label that shows results, the score, and a button to go back to topic screen
    private void createResultPanel() {
        resultPanel = new JPanel(new BorderLayout());

        JLabel resultLabel = new JLabel(username + "'s Results:", SwingConstants.CENTER); //to use username we put createResultPanel in the loadnextquestions method. we did this bc we need username to be filled before creating the results panel to use it here
        resultLabel.setFont(new Font("Arial", Font.BOLD, 32));
        resultPanel.add(resultLabel, BorderLayout.NORTH);

        String correctLabel = correctCount + "/10 or " + ((correctCount/10.0)*100) + "%"; //before or show how many out of the 10 questions they got right, second shows percentage

        JLabel resultLabel2 = new JLabel(correctLabel,SwingConstants.CENTER);
        resultLabel2.setFont(new Font("Arial",Font.BOLD, 24));

        resultPanel.add(resultLabel2, BorderLayout.CENTER);

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
                cardLayout.show(cardPanel, "T"); //goes back to topic
            }
        });
    }

    private void loadNextQuestion(){ //this method actually loads the questions into the labels and buttons
        if (questionPair == null || currentQuestionIndex > questionPair.size() - 1){ //if there are no more questions
            createResultPanel(); //creates result panel after we have no more questions so that any variables used are instantiated
            cardLayout.show(cardPanel, "R");
            return;
        }
        questionLabel.setText(
                (questionPair.get(currentQuestionIndex).get(0)));
        for (int i = 0; i<4; i++){
            optionButtons[i].setText(
                    questionPair.get(currentQuestionIndex).get(i+1)
            );
        }

        timeLeft = 10; //resets timeLeft variable to 10 after every question is reloaded
        timerLabel.setText("Time left: 10"); //resets text for safety
        timer.start(); //we start the timer back up once we load a new question
    }

    private void loadQuestionsFromFile(String path) { //this method assigns question and options and correctanswer to the variables
        QuestionLoader loader = new QuestionLoader(); //we dont need loader anywhere else so its local
        HashMap<Integer, ArrayList<String>> loadedQuestions = loader.readQuestions(path); //we use readQuestions with path argument because that path argument is how we change what topic of questions

        questionPair.clear(); //clear questions and answers in case we run a different topic
        correctAnswers.clear();

        for (int i = 1; i <= loadedQuestions.size(); i++) { //we use loop to assign loadedquestions to questionpair and every 5th index we add the correct answer
            ArrayList<String> q = loadedQuestions.get(i);
            questionPair.add(q);
            correctAnswers.add(Integer.parseInt(q.get(5)) - 1);
        }
    }
}