import java.util.Scanner;

public class List {
    private Task[] list;
    private int len;

    public List() {
        list = new Task[100];
        len = 0;
    }

    public void start() {
        String line;
        Scanner in = new Scanner(System.in);
        do {
            System.out.println("--");
            line = in.nextLine().trim();
            String[] parts = line.split(" ", 2);
            String command = parts[0];
            System.out.println("--");
            switch (command) {
            case "list":
                System.out.println("Here are the tasks in your list meow:");
                for (int i = 0; i < len; i++) {
                    System.out.println((i + 1) + ".[" + list[i].getStatusIcon() + "] " + list[i].description);
                }
                break;

            case "mark":
                int toMark = Integer.parseInt(parts[1]);
                list[toMark - 1].markAsDone();
                System.out.println("Nice! I've marked this task as done meow:");
                System.out.println("[X] " + list[toMark - 1].description);
                break;

            case "unmark":
                int toUnmark = Integer.parseInt(parts[1]);
                list[toUnmark - 1].markAsNotDone();
                System.out.println("Aw man, I guess you're not done yet. Do it soon meow:");
                System.out.println("[ ] " + list[toUnmark - 1].description);
                break;
            case "bye":
                break;
            default:
                //default case is to add task
                System.out.println("added: " + line); //add

                //instruction assumes no more than 100 tasks
                //hence no need to check for index out of range
                list[len] = new Task(line);
                len++;
                break;
            }
        } while (!line.equals("bye"));

        //loop exits when you type bye
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("--");
        in.close();
    }
}
