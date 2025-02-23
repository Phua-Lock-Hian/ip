package swing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import swing.tasktypes.*;

public class Storage {
    public static void createFile(){
        try {
            //create file to store data if it does not exist
            File f = new File("./data.txt");
            if (f.createNewFile()) { //only creates file if not existing
                System.out.println("Data file created");
            } else { //data file exists, read it
                System.out.println("Data found, reading existing data");
            }
        } catch (IOException e) {
            System.out.println("Error creating data file");
        }
    }

    public static void saveFile(List list, File file) {
        try {
            FileWriter fw = new FileWriter(file);
            for (Task t : list.list) {
                fw.write(t.toString() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Error in saving data");
        }
        System.out.println("File saved");
    }

    public static List loadFile() {
        List taskList = new List();
        try {
            File file = new File("./data.txt");
            Scanner scan = new Scanner(file);
            while (scan.hasNext()) {
                String line = scan.nextLine();
                String trim = line.substring(3); //remove the [task type] first
                String [] parts = trim.split("] ");
                //parts[0] contains [X or [ depending on if task is marked as done, parts[1] contains desc and params to be further processed
                String desc; //needed for all task types but need to trim accordingly
                boolean isDone = parts[0].contains("[X");
                if (line.startsWith("[T]")) {
                    desc = parts[1];
                    Task newTask = new Task(desc);
                    taskList.list.add(newTask);
                    newTask.setStatusIcon(isDone);
                }
                else if (line.startsWith("[D]")) {
                    String [] params = parts[1].split(" ");
                    desc = params[0];
                    String by = params[2].replace(")", ""); //remove the closing bracket
                    Deadline newDeadline = new Deadline(desc, by);
                    taskList.list.add(newDeadline);
                    newDeadline.setStatusIcon(isDone);
                }
                else if (line.startsWith("[E]")) {
                    String [] params = parts[1].split(" ");
                    desc = params[0];
                    String from = params[2];
                    String to = params[4].replace(")","");
                    Event newEvent = new Event(desc, from, to);
                    taskList.list.add(newEvent);
                    newEvent.setStatusIcon(isDone);
                }
                else {
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
