package swing.tasktypes;

/**
 * Deadline class has additional field "by"
 */
public class Deadline extends Task {

    protected String by;

    /**
     * @param description is the deadline descriptor
     * @param by is when the deadline should be completed by
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * @return deadline as a string for printing and storage
     */
    @Override
    public String toString() {
        return "[D]" + getBasicTaskInfo() + " (by: " + by + ")";
    }
}
