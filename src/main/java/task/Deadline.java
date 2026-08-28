package task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    private final LocalDateTime by;

    public Deadline(String name, LocalDateTime by) {
        super(name);
        this.by = by;
    }

    public LocalDateTime getBy() {
        return by;
    }

    @Override
    public String toString() {
        DateTimeFormatter outputFormat =
                DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");

        return "[D]" + super.toString()
                + " (by: " + by.format(outputFormat) + ")";
    }
}