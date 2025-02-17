package swing;

import java.io.File;

public class Swing {

    public static void main(String[] args) {
        System.out.println("Setting up...");
        Storage.createFile(); //already accounts for if file does not exist
        List list = Storage.loadFile();
        System.out.println("--");
        System.out.println("Hello! I'm Swing~ ^_^");
        System.out.println("What can I do for you meow?");
        list.start();
        Storage.saveFile(list, new File("data.txt"));
    }
}
