package swing.tasktypes;

/**
 * Basic class for todo tasks without additional @params
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructor method
     * @param description is the task descriptor
     * isDone is false by default
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * @return "X" if the task is marked as done and " " otherwise
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * @param isDone is true if task is marked as done
     */
    public void setStatusIcon(boolean isDone) {
        this.isDone = isDone;
    }

    protected String getBasicTaskInfo() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * @return the task as a string for printing and storage
     */
    public String toString() {
        return "[T]" + getBasicTaskInfo();
    }
}

