package week3_1_Jlabels_JTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextFieldFrame extends JFrame {
    private JTextField textField1; //default user types
    private JTextField textField2; //pre filled text
    private JTextField textField3; //uneditable text
    private JPasswordField passwordField;

    public TextFieldFrame() {
        super("Testing JTextField and JPasswordField");
        setLayout(new FlowLayout());

        textField1 = new JTextField(10); //10 is the length of the textbox, fits 10 characters
        add(textField1);

        textField2 = new JTextField("Enter text here");
        add(textField2);

        textField3 = new JTextField("Un-editable text field",21 );
        textField3.setEnabled(false);
        add(textField3);

        passwordField = new JPasswordField("Hidden text");
        add(passwordField);

        //register event handlers
        TextFieldHandler handler = new TextFieldHandler();

        textField1.addActionListener(handler);
        textField2.addActionListener(handler);
        textField3.addActionListener(handler);
        passwordField.addActionListener(handler);
    }

    private class TextFieldHandler implements ActionListener{ //this must happen because Actionlistener is an interface


        @Override
        public void actionPerformed(ActionEvent event) {
            String string = "";
            if(event.getSource() == textField1) { //event.getSource() gets the java variable name of the text field
                string = String.format("textField1: %s", event.getActionCommand()); //event.getActionCommand() gets what the user typed in at run time

            }
            else if (event.getSource() == textField2){
                string = String.format("textField2: %s", event.getActionCommand());
            }
            else if (event.getSource() == textField3){
                string = String.format("textField3: %s", event.getActionCommand());
            }
            else if (event.getSource() == passwordField){
                string = String.format("passwordField: %s", event.getActionCommand());
            }
            else{
                string = "ALL DONE NOW";
            }


            System.out.println(string);

            JOptionPane.showMessageDialog(null, string);

        }


    }

}



//using CardLayout is a layout manager and will allow us to stack multiple panels on top of each other and show one at a time.