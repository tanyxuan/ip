package task;
/**
 * Represents a task with a description and completion status.
 */
public class Task {
    private final String name;
    private boolean isDone;
    private String type;
    /**
     * Creates a task with the given description.
     *
     * @param name Description of the task.
     */
    public Task(String name) {
        this.name = name;
        this.isDone = false;
    }
    /**
     * Returns whether this task has been completed.
     *
     * @return True if the task is done, otherwise false.
     */
    public boolean isDone() {
        return isDone;
    }
    /**
     * Marks this task as completed.
     *
     * @return Message describing the updated task.
     */
    public String markDone() {
        isDone = true;
        return "Nice! I've marked this task as done:\n"
                + this.toString()
                + "\n";
    }
    /**
     * Marks this task as not completed.
     *
     * @return Message describing the updated task.
     */
    public String unmark() {
        isDone = false;
        return "OK, I've marked this task as not done yet:\n"
                + this.toString()
                + "\n";
    }
    /**
     * Returns the description of this task.
     *
     * @return Task description.
     */
    public String getName() {
        return name;
    }
    /**
     * Returns a formatted string representation of this task.
     *
     * @return Formatted task string.
     */
    @Override
    public String toString() {
        return (isDone ? "[X] " : "[ ] ") + name;
    }
}
