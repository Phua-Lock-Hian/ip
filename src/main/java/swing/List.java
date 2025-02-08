package swing;

import java.util.Scanner;

import swing.tasktypes.Deadline;
import swing.tasktypes.Event;
import swing.tasktypes.Task;

public class List {
    private final Task[] list;
    private int len;
    private static final int MAX_LIST_LEN = 100;

    public List() {
        list = new Task[MAX_LIST_LEN];
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
        return false;
    }

    private void errorMessage() {
        System.out.println("uh oh, something went wrong meow. try again~");
    }

    private void processListCommand() {
        System.out.println("Here are the tasks in your list meow:");
        for (int i = 0; i < len; i++) {
            System.out.println((i + 1) + "." + list[i]);
        }
    }

    private void processEventCommand(String[] eventArgParts, String eventDesc) {
        String start = eventArgParts[1].split("from", 2)[1];
        String end = eventArgParts[2].split("to", 2)[1];
        list[len] = new Event(eventDesc, start, end);
        len++;
        addedToList();
    }

    private void processDeadlineCommand(String[] parts) throws SwingException {
        if (!isValidCommand(parts, 2) || !parts[1].startsWith("/by")) {
            throw new SwingException();
        }
        String[] deadlineArgParts = parts[1].split("/by", 2);
        String deadlineDesc = deadlineArgParts[0];
        String by = deadlineArgParts[1].split(" ", 2)[1];
        list[len] = new Deadline(deadlineDesc, by);
        len++;
        addedToList();
    }

    private void processTaskStatus(String[] parts, boolean isDone) throws SwingException {
        if (!isValidCommand(parts, 2)) {
            throw new SwingException();
        }
        try {
            int index = Integer.parseInt(parts[1]);
            if (index <= 0 || index > len) {
                errorMessage();
                return;
            }
            list[index - 1].setStatusIcon(isDone);
            if (isDone) {
                System.out.println("Nice! I've marked this task as done meow:");
            } else {
                System.out.println("Aw man, I guess you're not done yet. Do it soon meow:");
            }
            System.out.println(list[index - 1]);
        } catch (NumberFormatException e) {
            System.out.println("I can't mark/unmark something that isn't a number meow :(");
            errorMessage();
        }
    }

    private void processMarkCommand(int toMark) {
        list[toMark - 1].setStatusIcon(true);
        System.out.println("Nice! I've marked this task as done meow:");
        System.out.println(list[toMark - 1].toString());
    }

    public void start() {
        String line;
        Scanner in = new Scanner(System.in);
        do {
            System.out.println("--");
            line = in.nextLine().trim();
            String[] parts = line.split(" ", 2);
            String command = parts[0].toLowerCase(); //make the command case-insensitive
            System.out.println("--");
            switch (command) {
            case "list":
                //validation is done outside so that i do not have to pass parts into the process command function
                if (!isValidCommand(parts, 1)) {
                    errorMessage();
                    break;
                }
                processListCommand();
                break;

            case "mark":
                try {
                    processTaskStatus(parts, true);
                } catch (SwingException e) {
                    errorMessage();
                }
                break;

            case "unmark":
                try {
                    processTaskStatus(parts, false);
                } catch (SwingException e) {
                    errorMessage();
                }
                break;

            case "deadline":
                try {
                    processDeadlineCommand(parts);
                } catch (SwingException e) {
                    errorMessage();
                }
                break;

            case "event":
                //event command requires more validation than the rest
                //have not thought of a good way to abstract this better but it is functional this way still
                if (!isValidCommand(parts, 2)) {
                    errorMessage();
                    break;
                }

                String[] eventArgParts = parts[1].split("/", 3);

                if (!isValidCommand(eventArgParts, 3)) {
                    errorMessage();
                    break;
                }

                String eventDesc = eventArgParts[0];

                if (!eventArgParts[1].startsWith("from") || !eventArgParts[2].startsWith("to")) {
                    errorMessage();
                    break;
                }

                processEventCommand(eventArgParts, eventDesc);
                break;
            case "todo":
                //instruction assumes no more than 100 tasks
                //hence no need to check for index out of range
                if (!isValidCommand(parts, 2)) {
                    errorMessage();
                    break;
                }
                list[len] = new Task(parts[1]);
                len++;
                addedToList();
            case "bye":
                break;

            default:
                //default case is invalid command
                errorMessage();
                break;
            }
        } while (!line.equals("bye"));

        //loop exits when you type bye
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("--");
        in.close();
    }
}
