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
                String trim = line.substring(3); //remove the [tasktype] first
                String [] parts = trim.split(" ]");
                String desc = parts[1]; //regardless of task type
                if (line.startsWith("[T]")) {
                    taskList.list.add(new Task(desc));
                }
                else if (line.startsWith("[D]")) {
                    String by = parts[3].replace(")", ""); //remove the closing bracket
                    taskList.list.add(new Deadline(desc, by));
                }
                else if (line.startsWith("[E]")) {
                    String from = parts[3];
                    String to = parts[5].replace(")","");
                    taskList.list.add(new Event(desc, from, to));
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
