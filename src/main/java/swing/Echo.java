package swing;

import java.util.Scanner;

public class Echo {
    public void start() {
        String line;
        Scanner in = new Scanner(System.in);
        do {
            line = in.nextLine();
            System.out.println(line); //echo
            System.out.println("--");
        } while (!line.equals("bye"));

        //loop exits when you type bye
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("--");
        in.close();
    }
}
