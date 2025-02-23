package swing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import swing.tasktypes.*;

/**
 * Utility class to handle data storage
 */
public class Storage {

    /**
     * Create file for data storage if it does not already exist
     */
    public static void createFile(){
        try {
            // Create file to store data if it does not exist
            File f = new File("./data.txt");
            if (f.createNewFile()) { // Only creates file if not existing
                System.out.println("Data file created");
            } else { // Data file exists, read it
                System.out.println("Data found, reading existing data");
            }
        } catch (IOException e) {
            System.out.println("Error creating data file");
        }
    }

    /**
     * @param tasks a TaskList containing all the tasks/deadlines/events to save
     * @param file to save data in
     */
    public static void saveFile(TaskList tasks, File file) {
        try {
            FileWriter fw = new FileWriter(file);
            for (Task t : tasks.getTasks()) {
                fw.write(t.toString() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Error in saving data");
        }
        System.out.println("File saved");
    }

    /**
     * @return the saved data as a taskList for use in the program
     */
    public static TaskList loadFile() {
        TaskList taskList = new TaskList();
        try {
            File file = new File("./data.txt");
            Scanner scan = new Scanner(file);

            // Define regex patterns to handle descriptions with spaces:
            Pattern todoPattern = Pattern.compile("^\\[T\\]\\[( |X)\\] (.*)$");
            Pattern deadlinePattern = Pattern.compile("^\\[D\\]\\[( |X)\\] (.*?) \\(by: (.*?)\\)$");
            Pattern eventPattern = Pattern.compile("^\\[E\\]\\[( |X)\\] (.*?) \\(from: (.*?) to: (.*?)\\)$");

            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                Matcher matcher;
                if (line.startsWith("[T]")) {
                    matcher = todoPattern.matcher(line);
                    if (matcher.matches()) {
                        boolean isDone = matcher.group(1).trim().equals("X");
                        String desc = matcher.group(2);
                        Task newTask = new Task(desc);
                        newTask.setStatusIcon(isDone);
                        taskList.add(newTask);
                    } else {
                        System.out.println("Error parsing todo: " + line);
                    }
                } else if (line.startsWith("[D]")) {
                    matcher = deadlinePattern.matcher(line);
                    if (matcher.matches()) {
                        boolean isDone = matcher.group(1).trim().equals("X");
                        String desc = matcher.group(2);
                        String by = matcher.group(3);
                        Deadline newDeadline = new Deadline(desc, by);
                        newDeadline.setStatusIcon(isDone);
                        taskList.add(newDeadline);
                    } else {
                        System.out.println("Error parsing deadline: " + line);
                    }
                } else if (line.startsWith("[E]")) {
                    matcher = eventPattern.matcher(line);
                    if (matcher.matches()) {
                        boolean isDone = matcher.group(1).trim().equals("X");
                        String desc = matcher.group(2);
                        String from = matcher.group(3);
                        String to = matcher.group(4);
                        Event newEvent = new Event(desc, from, to);
                        newEvent.setStatusIcon(isDone);
                        taskList.add(newEvent);
                    } else {
                        System.out.println("Error parsing event: " + line);
                    }
                } else {
                    System.out.println("Error in loading line: " + line);
                }
            }
            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println("Data file not found");
        }
        return taskList;
    }
}
