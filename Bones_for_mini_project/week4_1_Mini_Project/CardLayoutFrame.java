package week4_1_Mini_Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CardLayoutFrame extends JFrame {

    JPanel cardPanel;
    JPanel welcomePanel;
    JPanel usernamePanel;
    JPanel gamePanel;
    JPanel resultPanel;

    JButton startButton;
    JButton usernameButton;
    JButton forwardButton;
    JButton nextButton;
    JButton restartButton;

    CardLayout cardLayout;

    JTextField inputField;
    String username = new String();

    public CardLayoutFrame() {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        //Welcome page
        createWelcomePanel();
        /*welcomePanel = new JPanel();
        welcomePanel.add(new JLabel("Welcome Screen"));
        startButton = new JButton("Start Game");
        welcomePanel.add(startButton);*/

        createUsernamePanel();

        //Game Page
        createGamePanel();
        /*gamePanel = new JPanel();
        gamePanel.add(new JLabel("What's 9+10?"));
        nextButton = new JButton("Go to results");
        gamePanel.add(nextButton);*/

        //Results page
        createResultPanel(); //here we used refactor instead of manually making the methods.

        ButtonHandler handler = new ButtonHandler();
        startButton.addActionListener(handler);
        nextButton.addActionListener(handler);
        forwardButton.addActionListener(handler); // important
        usernameButton.addActionListener(handler);
        restartButton.addActionListener(handler);

        //add all panels to cardPanel
        cardPanel.add(welcomePanel,"W");
        cardPanel.add(usernamePanel, "U");
        cardPanel.add(gamePanel,"G");
        cardPanel.add(resultPanel,"R");

        add(cardPanel);

    }

    private class ButtonHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == startButton){
                cardLayout.show(cardPanel, "U");
            }
            else if (e.getSource() == forwardButton){
                cardLayout.show(cardPanel, "G");
            }
            else if (e.getSource() == nextButton) {
                cardLayout.show(cardPanel, "R");
            }
            else if (e.getSource() == restartButton){
                cardLayout.show(cardPanel, "W");
            }
            else if (e.getSource() == usernameButton){
                username = String.format(inputField.getText());
                JOptionPane.showMessageDialog(null, "Welcome " + username + "!");
                System.out.println(username);
            }

        }

    }
    private void createWelcomePanel(){
        welcomePanel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Welcome Screen", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD,24));
        welcomePanel.add(title,BorderLayout.CENTER);

        JPanel startPanel = new JPanel(new FlowLayout());
        startButton = new JButton("Start Game");
        startPanel.add(startButton);

        welcomePanel.add(startPanel, BorderLayout.SOUTH);
    }

    private void createUsernamePanel(){
        usernamePanel = new JPanel(new BorderLayout());
        JPanel userPanel = new JPanel(new BorderLayout());

        inputField = new JTextField("Enter a Username");
        inputField.setFont(new Font("Arial",Font.PLAIN,24));
        userPanel.add(inputField, BorderLayout.NORTH);

        usernameButton = new JButton("Save name");
        userPanel.add(usernameButton, BorderLayout.CENTER);
        usernamePanel.add(userPanel, BorderLayout.NORTH);
        //usernamePanel.add(userPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        forwardButton = new JButton("PLAY");
        buttonPanel.add(forwardButton);


        usernamePanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void createGamePanel(){
        gamePanel = new JPanel();
        gamePanel.add(new JLabel("What's 9+10?"));

        nextButton = new JButton("Go to results");
        gamePanel.add(nextButton);
    }

    private void createResultPanel() {
        resultPanel = new JPanel();
        resultPanel.add(new JLabel("Results Screen"));
        restartButton = new JButton("Go to beginning");
        resultPanel.add(restartButton);
    }



}
