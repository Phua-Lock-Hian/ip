package swing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import swing.tasktypes.*;

public class Storage {
    public void createFile(){
        try {
            //create file to store data if it does not exist
            File f = new File("data.txt");
            if (f.createNewFile()) { //only creates file if not existing
                System.out.println("Setup: Data file created");
            } else { //data file exists, read it
                System.out.println("Setup: Data found, reading existing data");
            }
        } catch (IOException e) {
            System.out.println("Error creating data file");
        }
    }

    public void saveFile(List list, File file) {
        try {
            FileWriter fw = new FileWriter(file);
            for (Task t : list.list) {
                fw.write(t.toString() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Error in saving data");
        }
    }

    public List loadFile() {
        List taskList = new List();
        try {
            File file = new File("data.txt");
            Scanner scan = new Scanner(file);
            while (scan.hasNext()) {
                String line = scan.nextLine();
                if (line.startsWith("[T]")) {
                    taskList.
                }

            }
            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println("Data file not found");
        }
    }
}
