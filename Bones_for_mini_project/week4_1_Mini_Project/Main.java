package week4_1_Mini_Project;

import javax.swing.JFrame;

public class Main { //frame setup
    public static void main(String[] args){
        CardLayoutFrame frame = new CardLayoutFrame();
        frame.setTitle("Trivia Game by Kevyn");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
}
