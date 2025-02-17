package swing;

import java.util.ArrayList;
import java.util.Scanner;

import swing.tasktypes.*;

public class List {
    public ArrayList<Task> list;

    public List() {
        list = new ArrayList<>();
    }

    private void addedToList() {
        System.out.println("added: " + list.get(list.size() - 1).toString()); //to offset zero indexing
        System.out.println("Now you have " + list.size() + " tasks in list.");
    }

    private boolean isInvalidCommand(String[] parts, int expectedLen) {
        return parts.length != expectedLen;
    }

    private boolean isInvalidIndex(int index) {
        if (index <= 0 || index > list.size()) {
            errorMessage();
            return true;
        }
        return false;
    }

    private void errorMessage() {
        System.out.println("uh oh, something went wrong meow. try again~");
    }

    private void processListCommand() {
        System.out.println("Here are the tasks in your list meow:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + "." + list.get(i));
        }
    }

    private void processEventCommand(String[] eventArgParts, String eventDesc) throws SwingException {
        String start = eventArgParts[1].split("from", 2)[1];
        String end = eventArgParts[2].split("to", 2)[1];
        if (start.isEmpty() || end.isEmpty()) {
            throw new SwingException();
        }
        list.add(new Event(eventDesc, start, end));
        addedToList();
    }

    private void processDeadlineCommand(String[] parts) throws SwingException {
        if (isInvalidCommand(parts, 2) || !parts[1].contains("/by")) {
            throw new SwingException();
        }
        String[] deadlineArgParts = parts[1].split("/by", 2);
        String deadlineDesc = deadlineArgParts[0];
        if (deadlineArgParts[1].isEmpty()) {
            throw new SwingException();
        }
        String by = deadlineArgParts[1].split(" ", 2)[1];
        if (by.isEmpty()) {
            throw new SwingException();
        }
        list.add(new Deadline(deadlineDesc, by));
        addedToList();
    }

    private void processTaskStatus(String[] parts, boolean isDone) throws SwingException {
        if (isInvalidCommand(parts, 2)) {
            throw new SwingException();
        }
        int index = Integer.parseInt(parts[1]);
        if (isInvalidIndex(index)) return;

        list.get(index - 1).setStatusIcon(isDone);
        if (isDone) {
            System.out.println("Nice! I've marked this task as done meow:");
        } else {
            System.out.println("Aw man, I guess you're not done yet. Do it soon meow:");
        }
        System.out.println(list.get(index - 1));
    }

    private void processDeleteCommand(String[] parts) throws SwingException {
        if (isInvalidCommand(parts, 2)) {
            throw new SwingException();
        }

        try {
            int index = Integer.parseInt(parts[1]);
            if (isInvalidIndex(index)) return;

            System.out.println("Okay, I've removed this task meow:");
            System.out.println(list.get(index - 1)); //account for zero indexing
            list.remove(index - 1);
            System.out.println("Now you have " + list.size() + " tasks in list.");
        } catch (NumberFormatException e) {
            System.out.println("I can't help you without the item number meow :(");
        }
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
                //validation is done outside so that I do not have to pass parts into the process command function
                if (isInvalidCommand(parts, 1)) { //in case user types list followed by some extra text
                    errorMessage();
                    break;
                }
                processListCommand();
                break;
            case "delete":
                try {
                    processDeleteCommand(parts);
                } catch (SwingException e) {
                    errorMessage();
                }
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
                //have not thought of a good way to abstract this better, but it is functional this way still
                if (isInvalidCommand(parts, 2)) {
                    errorMessage();
                    break;
                }

                String[] eventArgParts = parts[1].split("/", 3);

                if (isInvalidCommand(eventArgParts, 3)) {
                    errorMessage();
                    break;
                }

                String eventDesc = eventArgParts[0];

                if (!eventArgParts[1].startsWith("from") || !eventArgParts[2].startsWith("to")) {
                    errorMessage();
                    break;
                }
                try {
                    processEventCommand(eventArgParts, eventDesc);
                } catch (SwingException e) {
                    errorMessage();
                }
                break;
            case "todo":
                //instruction assumes no more than 100 tasks
                //hence no need to check for index out of range
                if (isInvalidCommand(parts, 2)) {
                    errorMessage();
                    break;
                }
                list.add(new Task(parts[1]));
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
