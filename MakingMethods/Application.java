package week1_2;
import javax.swing.JOptionPane;

//import static week1_2.CountPrime.countPrimesBasic;
//import static week1_2.CountPrime.countWithArray;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        CountPrime counter = new CountPrime(); //since we made it nonstatic we need to create an object first before using the methods

        Scanner input = new Scanner(System.in);

        //JOptionPane.showMessageDialog(null, "Welcome to Java", "Welcome Message", JOptionPane.WARNING_MESSAGE);

        String dialoginput = JOptionPane.showInputDialog("Please enter input n of the CountPrime problem: n = ");
        int n = Integer.parseInt(dialoginput);

        String message = String.format("there are %d numbers from 0 to %d", counter.countPrimesBasic(n), n);

        JOptionPane.showMessageDialog(null, message, "Output", JOptionPane.INFORMATION_MESSAGE);

        /*System.out.println("Please input a number");
        String inputString = input.nextLine();//scans till it hits a \n

        int n = Integer.parseInt(inputString);

        System.out.println("Solution 1: total count " + counter.countPrimesBasic(n));
        System.out.println("Solution 2: total count " + counter.countWithArray(n));*/

//        int ans = countPrimesBasic(n);
//        System.out.println(ans);
//        System.out.println(countWithArray(n));

    }
}
