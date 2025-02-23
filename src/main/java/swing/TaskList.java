package swing;

import java.util.ArrayList;
import swing.tasktypes.*;

/**
 * Class to contain tasks as list
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructor
     */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * @param task to add to list
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * @param index specify index of task to get from list
     * @return the task at specified index
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * @param index specify index of task to be removed
     */
    public void remove(int index) {
        tasks.remove(index);
    }

    /**
     * @return length of task list
     */
    public int size() {
        return tasks.size();
    }

    /**
     * @return list of tasks in ArrayList
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
