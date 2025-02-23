package swing;

import java.util.Scanner;

/**
 * Class to handle user interface
 */
public class Ui {
    private Scanner in;

    /**
     * Constructor
     */
    public Ui() {
        in = new Scanner(System.in);
    }

    /**
     * @return user input, trailing spaces deleted
     */
    public String readCommand() {
        return in.nextLine().trim();
    }

    /**
     * Print divider
     */
    public void showDivider() {
        System.out.println("--");
    }

    /**
     * @param message equivalent to System.out.println(message)
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Print goodbye message
     */
    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("--");
    }

    /**
     * Close user input scanner
     */
    public void close() {
        in.close();
    }
}
