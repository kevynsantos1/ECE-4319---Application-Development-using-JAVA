import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        int totalPrime = 0;
        int count = 0;

        System.out.printf("Hello and welcome to the prime number counter!\nPlease input a number\n");

        do {
            Scanner input = new Scanner(System.in); //System.in is the input stream of class System. We don't use this because it reads bytes, so we would have to manually translate and handle everything
            //Scanner is a high-level input utility that converts the bytes to characters, then tokenizes the inputs, then converts tokens into requested types

            if (input.hasNextInt()) { //hasNextInt() checks if there is an integer in the input. If so it returns true, if not it returns false

                count = input.nextInt(); //if there is an integer, we set count to the user-defined integer

                if (count < 2) { //if they typed an integer < 2 we loop back from the do while
                    System.out.printf("INVALID INPUT. INTEGERS LESS THAN 2 HAVE 0 PRIME NUMBERS\n");
                } else { //else it is a valid input
                    System.out.printf("Valid Input. Moving Forward...\n");
                }
            }
            else{ //in the case hasNextInt() does not find an int.
                count=0; //we set count to 0 so that the do while loop stays looping
                System.out.printf("INVALID INPUT\n");
            }

        }while(count<2); //condition for the do while to keep looping

        boolean[] prime = new boolean[count]; //creating the list of all the numbers from 0 to count (strictly less than n, which is why no + 1)
        Arrays.fill(prime, true); //Arrays is a utility class that helps us manipulate arrays. here we are setting all the values to true

        prime[0] = false; //we know 0 and 1 are not prime
        prime[1] = false;


        //sieves of eratosthenes
        for (int i = 2; i * i < count; i++) { //this loop stops at the first value of i that ends up being bigger than our integer because we don't care if numbers past our integer are prime or not
            if (prime[i]) { //everything in array was set to true, so if it is false we already know it is not prime.
                for (int j = i * i; j < count; j += i) { //any number that is i^2 is not prime because it can always be divided by 1, i, and itself.
                    prime[j] = false;      //it also adds the same i to our value because we know any multiple of it will not be prime
                }
            }
        }

        for (int i = 2; i < count; i++) { //this for loop increments the totalPrime variable, for each value that is true in the boolean array
            if (prime[i]) ++totalPrime;
        }

        System.out.printf("Input: " + count + "\nPrime number count: " + totalPrime);

    }
}