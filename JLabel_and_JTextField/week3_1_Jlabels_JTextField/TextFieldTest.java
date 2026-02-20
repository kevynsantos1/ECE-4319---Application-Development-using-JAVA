package week3_1_Jlabels_JTextField;

import javax.swing.*;

public class TextFieldTest {
    public static void main (String[] args) {
        TextFieldFrame frame = new TextFieldFrame();
        frame.setSize(600,800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);

        //actionlistener is not a class it is an interface
        //extends is basically inheritting
        //implements is like an empty class that we define what is done
    }
}
