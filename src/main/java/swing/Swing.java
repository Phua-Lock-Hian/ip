package swing;

import java.io.File;

public class Swing {
    public static void main(String[] args) {
        System.out.println("Setting up...");
        //create file if it does not exist
        Storage.createFile();
        //load tasks from file
        TaskList tasks = Storage.loadFile();

        Ui ui = new Ui();
        Parser parser = new Parser();

        ui.showMessage("--");
        ui.showMessage("Hello! I'm Swing~ ^_^");
        ui.showMessage("What can I do for you meow?");

        String line;
        do {
            ui.showDivider();
            line = ui.readCommand();
            ui.showDivider();
            parser.parseAndExecute(line, tasks, ui);
        } while (!line.equals("bye"));

        //when bye is typed
        ui.showGoodbye();
        Storage.saveFile(tasks, new File("data.txt"));
        ui.close();
    }
}
