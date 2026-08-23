public class Task {
    private final String name;
    private boolean isDone;
    private String type;
    public Task(String name) {
        this.name = name;
        this.isDone = false;
    }



    public String markDone() {
        isDone = true;
        return "Nice! I've marked this task as done:\n"
                + this.toString()
                + "\n";
    }
    public String unmark() {
        isDone = false;
        return "OK, I've marked this task as not done yet:\n"
                + this.toString()
                + "\n";
    }

    @Override
    public String toString() {
        return (isDone ? "[X] " : "[ ] ") + name;
    }
}
