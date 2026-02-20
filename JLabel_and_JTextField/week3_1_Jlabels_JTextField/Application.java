package week3_1_Jlabels_JTextField;

import javax.swing.*;
import java.awt.*;

public class Application {

    private static Icon bug = new ImageIcon(Application.class.getResource("bug1.png"));

    public static void main (String[] args){
        JLabel label1; //this will be our JLabel with just text
        JLabel label2; //this will be our JLabel constructed with text and icon
        JLabel label3; //this will be our JLabel with added text and icon

        JFrame labelFrame = new JFrame("Testing JLabel"); //backbone of our project
        labelFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        labelFrame.setSize(800,600);

        labelFrame.setLayout(new FlowLayout());

        //label constructor with string argument
        label1 = new JLabel("Label with text"); //instantiates a new JLabel (label1) with the text "Label with text”’
        label1.setToolTipText("This is label1"); //Tool tip is the hint when user hover over the mouse
        labelFrame.add(label1); //add(label); this new label is added to JFrame.

        label2 = new JLabel("Label with text and icon", bug, SwingConstants.LEFT);//last argument is alignment
        label2.setToolTipText("This is label2");
        labelFrame.add(label2);

        label3 = new JLabel();
        label3.setText("Label with icon and text as bottom");
        label3.setIcon(bug);
        label3.setHorizontalTextPosition(SwingConstants.CENTER);
        label3.setVerticalTextPosition(SwingConstants.BOTTOM);
        label3.setToolTipText("This is label3");
        labelFrame.add(label3);

        labelFrame.setVisible(true);

    }
}
