package week_12_ThreadsChatroom;

import javax.swing.*;

public class Client2 {

    public static void main (String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientGUIFrame().startClient();
            }
        });


    }

}
