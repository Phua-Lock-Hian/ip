import java.util.Scanner;

public class List {
    private String[] list;
    private int len;
    public List() {
        list = new String[100];
        len = 0;
    }
    public void start() {
        String line;
        Scanner in = new Scanner(System.in);
        do {
            line = in.nextLine();

            if (line.equals("list")) {
                for (int i = 0; i < len; i++) {
                    System.out.println( (i + 1)  + ". " + list[i]);
                    System.out.println("--");
                }
                continue;
            }
            System.out.println("added: " + line); //add
            System.out.println("--");

            //instruction assumes no more than 100 tasks
            //hence no need to check for index out of range
            list[len] = line;
            len++;
        } while (!line.equals("bye"));

        //loop exits when you type bye
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("--");
        in.close();
    }
}
