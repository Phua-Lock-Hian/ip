package swing;

import swing.tasktypes.*;

/**
 * Class to parse and execute user commands
 */
public class Parser {

    private boolean isInvalidCommand(String[] parts, int expectedLen) {
        return parts.length != expectedLen;
    }

    private boolean isInvalidIndex(int index, TaskList tasks, Ui ui) {
        if (index <= 0 || index > tasks.size()) {
            errorMessage(ui);
            return true;
        }
        return false;
    }

    private void errorMessage(Ui ui) {
        ui.showMessage("uh oh, something went wrong meow. try again~");
    }

    private void addedToList(TaskList tasks, Ui ui) {
        ui.showMessage("added: " + tasks.get(tasks.size() - 1).toString()); //to offset zero indexing
        ui.showMessage("Now you have " + tasks.size() + " tasks in list.");
    }

    private void processListCommand(TaskList tasks, Ui ui) {
        ui.showMessage("Here are the tasks in your list meow:");
        for (int i = 0; i < tasks.size(); i++) {
            ui.showMessage((i + 1) + "." + tasks.get(i));
        }
    }

    private void processEventCommand(String[] eventArgParts, String eventDesc, TaskList tasks, Ui ui) throws SwingException {
        String start = eventArgParts[1].split("from ", 2)[1].trim(); //otherwise there is an extra space after start param
        String end = eventArgParts[2].split("to ", 2)[1];
        if (start.isEmpty() || end.isEmpty()) {
            throw new SwingException();
        }
        tasks.add(new Event(eventDesc, start, end));
        addedToList(tasks, ui);
    }

    private void processDeadlineCommand(String[] parts, TaskList tasks, Ui ui) throws SwingException {
        if (isInvalidCommand(parts, 2) || !parts[1].contains("/by")) {
            throw new SwingException();
        }
        String[] deadlineArgParts = parts[1].split(" /by");
        String deadlineDesc = deadlineArgParts[0];
        if (deadlineArgParts[1].isEmpty() || deadlineArgParts.length > 2) {
            throw new SwingException();
        }
        String by = deadlineArgParts[1].split(" ", 2)[1];
        if (by.isEmpty()) {
            throw new SwingException();
        }
        tasks.add(new Deadline(deadlineDesc, by));
        addedToList(tasks, ui);
    }

    private void processTaskStatus(String[] parts, boolean isDone, TaskList tasks, Ui ui) throws SwingException {
        if (isInvalidCommand(parts, 2)) {
            throw new SwingException();
        }
        int index = Integer.parseInt(parts[1]);
        if (isInvalidIndex(index, tasks, ui)) return;

        tasks.get(index - 1).setStatusIcon(isDone);
        if (isDone) {
            ui.showMessage("Nice! I've marked this task as done meow:");
        } else {
            ui.showMessage("Aw man, I guess you're not done yet. Do it soon meow:");
        }
        ui.showMessage(tasks.get(index - 1).toString());
    }

    private void processDeleteCommand(String[] parts, TaskList tasks, Ui ui) throws SwingException {
        if (isInvalidCommand(parts, 2)) {
            throw new SwingException();
        }

        try {
            int index = Integer.parseInt(parts[1]);
            if (isInvalidIndex(index, tasks, ui)) return;

            ui.showMessage("Okay, I've removed this task meow:");
            ui.showMessage(tasks.get(index - 1).toString()); //account for zero indexing
            tasks.remove(index - 1);
            ui.showMessage("Now you have " + tasks.size() + " tasks in list.");
        } catch (NumberFormatException e) {
            ui.showMessage("I can't help you without the item number meow :(");
        }
    }

    /**
     * @param line contains user input
     * @param tasks contains the list of tasks
     * @param ui handles user interface
     */
    public void parseAndExecute(String line, TaskList tasks, Ui ui) {
        String[] parts = line.split(" ", 2);
        String command = parts[0].toLowerCase(); //make the command case-insensitive
        switch (command) {
        case "list":
            //validation is done outside so that I do not have to pass parts into the process command function
            if (isInvalidCommand(parts, 1)) { //in case user types list followed by some extra text
                errorMessage(ui);
                break;
            }
            processListCommand(tasks, ui);
            break;
        case "delete":
            try {
                processDeleteCommand(parts, tasks, ui);
            } catch (SwingException e) {
                errorMessage(ui);
            }
            break;
        case "mark":
            try {
                processTaskStatus(parts, true, tasks, ui);
            } catch (SwingException e) {
                errorMessage(ui);
            }
            break;
        case "unmark":
            try {
                processTaskStatus(parts, false, tasks, ui);
            } catch (SwingException e) {
                errorMessage(ui);
            }
            break;
        case "deadline":
            try {
                processDeadlineCommand(parts, tasks, ui);
            } catch (SwingException e) {
                errorMessage(ui);
            }
            break;
        case "event":
            //event command requires more validation than the rest
            //have not thought of a good way to abstract this better, but it is functional this way still
            if (isInvalidCommand(parts, 2)) {
                errorMessage(ui);
                break;
            }

            String[] eventArgParts = parts[1].split("/");

            if (isInvalidCommand(eventArgParts, 3)) {
                errorMessage(ui);
                break;
            }

            String eventDesc = eventArgParts[0];

            if (!eventArgParts[1].startsWith("from") || !eventArgParts[2].startsWith("to")) {
                errorMessage(ui);
                break;
            }
            try {
                processEventCommand(eventArgParts, eventDesc, tasks, ui);
            } catch (SwingException e) {
                errorMessage(ui);
            }
            break;
        case "todo":
            //instruction assumes no more than 100 tasks
            //hence no need to check for index out of range
            if (isInvalidCommand(parts, 2)) {
                errorMessage(ui);
                break;
            }
            tasks.add(new Task(parts[1]));
            addedToList(tasks, ui);
        case "bye":
            break;
        default:
            //default case is invalid command
            errorMessage(ui);
            break;
        }
    }
}
