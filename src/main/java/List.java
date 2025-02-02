import java.util.Scanner;

public class List {
    private final Task[] list;
    private int len;

    public List() {
        list = new Task[100];
        len = 0;
    }

    private void addedToList() {
        System.out.println("added: " + list[len - 1].toString()); //to offset zero indexing
        System.out.println("Now you have " + len + " tasks in list.");
    }

    private boolean isValidCommand(String[] parts, int expectedLen) {
        if (parts.length == expectedLen) {
            return true;
        }
        errorMessage();
        return false;
    }

    private void errorMessage() {
        System.out.println("uh oh, something went wrong meow. try again~");
        System.out.println("--");
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
                if (!isValidCommand(parts, 1)) break;

                System.out.println("Here are the tasks in your list meow:");
                for (int i = 0; i < len; i++) {
                    System.out.println((i + 1) + "." + list[i].toString());
                }
                break;

            case "mark":
                if (!isValidCommand(parts, 2)) break;

                int toMark = Integer.parseInt(parts[1]);
                list[toMark - 1].setStatusIcon(true);
                System.out.println("Nice! I've marked this task as done meow:");
                System.out.println(list[toMark - 1].toString());
                break;

            case "unmark":
                if (!isValidCommand(parts, 2)) break;

                int toUnmark = Integer.parseInt(parts[1]);
                list[toUnmark - 1].setStatusIcon(false);
                System.out.println("Aw man, I guess you're not done yet. Do it soon meow:");
                System.out.println(list[toUnmark - 1].toString());
                break;

            case "deadline":
                if (!isValidCommand(parts, 2)) break;

                String[] deadlineArgParts = parts[1].split("/by", 2);
                String deadlineDesc = deadlineArgParts[0];
                String by = deadlineArgParts[1].split(" ", 2)[1];
                list[len] = new Deadline(deadlineDesc, by);
                len++;
                addedToList();
                break;

            case "event":
                if (!isValidCommand(parts, 2)) break;

                String[] eventArgParts = parts[1].split("/", 3);

                if (!isValidCommand(eventArgParts, 3)) break;
                String eventDesc = eventArgParts[0];
                String start = eventArgParts[1].split("from", 2)[1];
                String end = eventArgParts[2].split("to", 2)[1];
                list[len] = new Event(eventDesc, start, end);
                len++;
                addedToList();
                break;
            case "bye":
                break;
            default:
                //default case is to add task
                //instruction assumes no more than 100 tasks
                //hence no need to check for index out of range
                list[len] = new Task(line);
                len++;
                addedToList();
                break;
            }
        } while (!line.equals("bye"));

        //loop exits when you type bye
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("--");
        in.close();
    }
}
