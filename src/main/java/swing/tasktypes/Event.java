package swing.tasktypes;

/**
 * Event class has additional fields "start", "end"
 */
public class Event extends Task {

    protected String start;
    protected String end;

    /**
     * @param description is the event descriptor
     * @param start denotes when the event starts
     * @param end denotes when the event ends
     */
    public Event(String description, String start, String end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    /**
     * @return the event as a string for printing and storage
     */
    @Override
    public String toString() {
        return "[E]" + getBasicTaskInfo() + " (from: " + start + " to: " + end + ")";
    }
}
