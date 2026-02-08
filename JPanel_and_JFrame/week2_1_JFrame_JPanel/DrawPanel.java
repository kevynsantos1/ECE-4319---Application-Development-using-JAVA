package week2_1_JFrame_JPanel;

import javax.swing.JPanel;
import java.awt.*;

public class DrawPanel extends JPanel {

    //polymorphism: allows the child class to customize behavior inherited from its parents
    //this is called overriding. A child class overrides a parent method by declaring a method with the same signature.
    //Inside the overriding method, you can call the parent's implementation using the super keyword, which then calls the parent class instance.


    public void paintComponent(Graphics g){ //calls paint component to ensure the panel displays properly. This line belongs to draw panel
        super.paintComponent(g);//to make sure paint component in parent class is doing the same in child class.
                                //without this the components background may not be cleared and can show incorrect visuals

        int width = getWidth(); //gets the Width from Jpanel
        int height = getHeight();//gets the height from JPanel

        g.setColor(Color.RED);
        g.drawLine(0,0,width,height); //method in graphics class. It has 4 parameters, x1 and y1 which are the starting point and x2 and y2 which are the ending point
        g.drawLine(0, height, width, 0);

    }
}
