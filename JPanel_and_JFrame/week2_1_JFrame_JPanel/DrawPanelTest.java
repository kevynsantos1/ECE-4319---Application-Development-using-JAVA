package week2_1_JFrame_JPanel;
import javax.swing.JFrame;
import java.awt.Graphics;

public class DrawPanelTest {
    public static void main (String[] args) {
        JFrame application = new JFrame();
        DrawPanel panel = new DrawPanel();

        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //This line sets how to close the Jframe. It means that clicking the upper right X will close the frame.
        application.add(panel);
        application.setSize(500,500); //sets the size width x height in pixels
        application.setVisible(true);//This line sets the application to be visible

        //note for myself: extends keyword is used to establish an inheritance relationship between classes.
        //in this case, DrawPanel is a user defined class inheriting from JPanel



    }


}
