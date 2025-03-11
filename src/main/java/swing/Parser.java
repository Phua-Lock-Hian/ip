package swing;

import java.io.File;

import swing.tasktypes.Task;
import swing.tasktypes.Deadline;
import swing.tasktypes.Event;

/**
 * Class to parse and execute user commands
 */
public class Parser {

    private boolean isInvalidCommand(String[] parts, int expectedLen) {
        return parts.length != expectedLen;
    }

    private boolean isInvalidIndex(int index, TaskList tasks, Ui ui) {
        if (index <= 0 || index > tasks.size()) {
            return true;
        }
        return false;
    }

    private void printErrorMessage(Ui ui) {
        ui.showMessage("uh oh, something went wrong meow. try again~");
    }

    private void addToList(TaskList tasks, Ui ui) {
        ui.showMessage("added: " + tasks.get(tasks.size() - 1).toString()); //to offset zero indexing
        ui.showMessage("Now you have " + tasks.size() + " tasks in list.");
    }

    private void processListCommand(TaskList tasks, Ui ui) {
        for (int i = 0; i < tasks.size(); i++) {
            ui.showMessage((i + 1) + "." + tasks.get(i));
        }
    }

    private void processEventCommand(String[] eventArgParts, String eventDesc, TaskList tasks, Ui ui) throws SwingException {
        String start = eventArgParts[1].split("from ", 2)[1].trim(); //otherwise there is an extra space after start param
        String end = eventArgParts[2].contains("to ") ? eventArgParts[2].split("to ", 2)[1].trim() : ""; //account for case where there is nothing after /to
        if (start.isEmpty() || end.isEmpty()) {
            throw new SwingException();
        }
        tasks.add(new Event(eventDesc, start, end));
        addToList(tasks, ui);
    }

    /**
     * @param tasks contains the list of tasks
     * @param ui    handles user interface
     * @param parts contains user input
     */
    private void processDeadlineCommand(String[] parts, TaskList tasks, Ui ui) throws SwingException {
        if (isInvalidCommand(parts, 2) || !parts[1].contains("/by")) {
            throw new SwingException();
        }
        String[] deadlineArgParts = parts[1].trim().split(" /by");
        String deadlineDesc = deadlineArgParts[0];
        if (deadlineArgParts.length != 2 || deadlineArgParts[1].isEmpty()) {
            throw new SwingException();
        }
        String by = deadlineArgParts[1].split(" ", 2)[1];
        if (by.isEmpty()) {
            throw new SwingException();
        }
        tasks.add(new Deadline(deadlineDesc, by));
        addToList(tasks, ui);
    }

    private void processTaskStatus(String[] parts, boolean isDone, TaskList tasks, Ui ui) throws SwingException {
        if (isInvalidCommand(parts, 2)) {
            throw new SwingException();
        }
        int index = -1; //initialize to an invalid value first in case of error
        try {
            index = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            throw new SwingException();
        }
        if (isInvalidIndex(index, tasks, ui)) {
            throw new SwingException();
        }

        tasks.get(index - 1).setStatusIcon(isDone);
        if (isDone) {
            ui.showMessage("Nice! I've marked this task as done meow:");
        } else {
            ui.showMessage("Aw man, I guess you're not done yet. Do it soon meow:");
        }
        ui.showMessage(tasks.get(index - 1).toString());
    }

    /**
     * @param tasks contains the list of tasks
     * @param ui    handles user interface
     * @param parts contains user input
     */
    private void processDeleteCommand(String[] parts, TaskList tasks, Ui ui) throws SwingException {
        if (isInvalidCommand(parts, 2)) {
            throw new SwingException();
        }

        try {
            int index = Integer.parseInt(parts[1]);
            if (isInvalidIndex(index, tasks, ui)) {
                throw new SwingException();
            }

            ui.showMessage("Okay, I've removed this task meow:");
            ui.showMessage(tasks.get(index - 1).toString()); //account for zero indexing
            tasks.remove(index - 1);
            ui.showMessage("Now you have " + tasks.size() + " tasks in list.");
        } catch (NumberFormatException e) {
            throw new SwingException();
        }
    }

    /**
     * @param tasks contains the list of tasks
     * @param ui    handles user interface
     * @param parts contains user input
     */
    private void processFindCommand(TaskList tasks, Ui ui, String[] parts) {
        String keyword = parts[1];
        TaskList searchResults = new TaskList();
        for (Task i : tasks.getTasks()) {
            if (i.toString().contains(keyword)) {
                searchResults.add(i);
            }
        }
        ui.showMessage("Here are the matching tasks in your list meow:");
        processListCommand(searchResults, ui);
    }
    /**
     * @param tasks contains the list of tasks
     * @param ui    handles user interface
     * @param line contains user input
     */
    public void parseAndExecute(String line, TaskList tasks, Ui ui) {
        String[] parts = line.split(" ", 2);
        String command = parts[0].toLowerCase(); //make command case-insensitive
        boolean shouldSave = false; //track if file should be saved

        switch (command) {
        case "list":
            if (isInvalidCommand(parts, 1)) {
                printErrorMessage(ui);
                return;
            }
            ui.showMessage("Here are the tasks in your list meow:");
            processListCommand(tasks, ui);
            return;

        case "delete":
            try {
                processDeleteCommand(parts, tasks, ui);
                shouldSave = true;
            } catch (SwingException e) {
                printErrorMessage(ui);
                return;
            }
            break;

        case "mark":
            try {
                processTaskStatus(parts, true, tasks, ui);
                shouldSave = true;
            } catch (SwingException e) {
                printErrorMessage(ui);
                return;
            }
            break;

        case "unmark":
            try {
                processTaskStatus(parts, false, tasks, ui);
                shouldSave = true;
            } catch (SwingException e) {
                printErrorMessage(ui);
                return;
            }
            break;

        case "deadline":
            try {
                processDeadlineCommand(parts, tasks, ui);
                shouldSave = true;
            } catch (SwingException e) {
                printErrorMessage(ui);
                return;
            }
            break;

        case "event":
            if (isInvalidCommand(parts, 2)) {
                printErrorMessage(ui);
                return;
            }

            String[] eventArgParts = parts[1].split("/");
            if (isInvalidCommand(eventArgParts, 3)) {
                printErrorMessage(ui);
                return;
            }

            String eventDesc = eventArgParts[0];

            if (!eventArgParts[1].startsWith("from") || !eventArgParts[2].startsWith("to")) {
                printErrorMessage(ui);
                return;
            }

            try {
                processEventCommand(eventArgParts, eventDesc, tasks, ui);
                shouldSave = true;
            } catch (SwingException e) {
                printErrorMessage(ui);
                return;
            }
            break;

        case "todo":
            if (isInvalidCommand(parts, 2)) {
                printErrorMessage(ui);
                return;
            }
            tasks.add(new Task(parts[1]));
            addToList(tasks, ui);
            shouldSave = true;
            break;

        case "find":
            if (isInvalidCommand(parts, 2)) {
                printErrorMessage(ui);
                return;
            }
            processFindCommand(tasks, ui, parts);
            break;

        case "bye":
            return; //auto save on exit

        default:
            printErrorMessage(ui);
            return;
        }

        // Save file only if a change was made
        if (shouldSave) { //not sure why this is saying it is always true?
            Storage.saveFile(tasks, new File("data.txt"));
        }
    }
}
