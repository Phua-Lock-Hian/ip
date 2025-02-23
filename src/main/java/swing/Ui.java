package swing;

import java.util.Scanner;

public class Ui {
    private Scanner in;

    public Ui() {
        in = new Scanner(System.in);
    }

    public String readCommand() {
        return in.nextLine().trim();
    }

    public void showDivider() {
        System.out.println("--");
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showGoodbye() {
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("--");
    }

    public void close() {
        in.close();
    }
}
