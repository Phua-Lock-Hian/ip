package swing.tasktypes;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void setStatusIcon(boolean isDone) {
        this.isDone = isDone;
    }

    protected String getBasicTaskInfo() {
        return "[" + getStatusIcon() + "] " + description;
    }

    public String toString() {
        return "[T]" + getBasicTaskInfo();
    }
}

