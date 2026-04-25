package week_12_ThreadsChatroom;

import javax.swing.*;

public class Client1 {

    public static void main (String[] args){
        SwingUtilities.invokeLater(new Runnable() { //Swing components are not thread-safe. This method takes a runnable object as an argument and places it into a Event Dispatch Thread queue which is responsible for GUI tasks. The run method will run after all other pending events in the Queue have been ran.
            @Override
            public void run() {
                new ClientGUIFrame().startClient();
            }
        });
    }

}
