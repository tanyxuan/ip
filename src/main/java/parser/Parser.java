package parser;

import exception.JoeBidenException;
import task.Deadline;
import task.Event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {

    public static String getCommand(String input) {
        String[] parts = input.split(" ", 2);
        return parts[0].toLowerCase();
    }

    public static String getArguments(String input) {
        String[] parts = input.split(" ", 2);

        if (parts.length < 2) {
            return "";
        }

        return parts[1];
    }

    public static void validateNoArguments(String command, String arguments)
            throws JoeBidenException {

        if (!arguments.isBlank()) {
            throw new JoeBidenException(
                    "The " + command + " command does not take any arguments."
            );
        }
    }

    public static int getTaskNumber(String arguments) throws JoeBidenException {
        if (arguments.isBlank()) {
            throw new JoeBidenException(
                    "Please specify a task number."
            );
        }

        try {
            return Integer.parseInt(arguments);
        } catch (NumberFormatException e) {
            throw new JoeBidenException(
                    "Task number must be a number."
            );
        }
    }
    public static Event parseEvent(String arguments)
            throws JoeBidenException {

        if (arguments.isBlank()) {
            throw new JoeBidenException(
                    "The description of an event cannot be empty."
            );
        }

        String[] fromParts = arguments.split(" /from ", 2);

        if (fromParts.length < 2
                || fromParts[0].isBlank()
                || fromParts[1].isBlank()) {
            throw new JoeBidenException(
                    "Event must be in the format: event <task> /from <start> /to <end>"
            );
        }

        String description = fromParts[0];

        String[] toParts = fromParts[1].split(" /to ", 2);

        if (toParts.length < 2
                || toParts[0].isBlank()
                || toParts[1].isBlank()) {
            throw new JoeBidenException(
                    "Event must be in the format: event <task> /from <start> /to <end>"
            );
        }

        DateTimeFormatter inputFormat =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

        try {
            LocalDateTime from =
                    LocalDateTime.parse(toParts[0], inputFormat);

            LocalDateTime to =
                    LocalDateTime.parse(toParts[1], inputFormat);

            return new Event(description, from, to);

        } catch (DateTimeParseException e) {
            throw new JoeBidenException(
                    "Invalid date. Use format: yyyy-MM-dd HHmm"
            );
        }
    }
    public static String parseDescription(String arguments)
            throws JoeBidenException {

        if (arguments.isBlank()) {
            throw new JoeBidenException(
                    "The description cannot be empty."
            );
        }

        return arguments;
    }
    /**
     * Validates and returns the keyword used for finding tasks.
     *
     * @param arguments Keyword entered by the user.
     * @return Validated search keyword.
     * @throws JoeBidenException If the keyword is empty.
     */
    public static String parseFindKeyword(String arguments)
            throws JoeBidenException {
        if (arguments.isBlank()) {
            throw new JoeBidenException(
                    "Please specify a keyword to find."
            );
        }

        return arguments;
    }
    public static Deadline parseDeadline(String arguments)
            throws JoeBidenException {

        if (arguments.isBlank()) {
            throw new JoeBidenException(
                    "The description of a deadline cannot be empty."
            );
        }

        String[] deadlineParts = arguments.split(" /by ", 2);

        if (deadlineParts.length < 2
                || deadlineParts[0].isBlank()
                || deadlineParts[1].isBlank()) {
            throw new JoeBidenException(
                    "Deadline must be in the format: deadline <task> /by <date>"
            );
        }

        String description = deadlineParts[0];

        DateTimeFormatter inputFormat =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

        try {
            LocalDateTime by =
                    LocalDateTime.parse(deadlineParts[1], inputFormat);

            return new Deadline(description, by);

        } catch (DateTimeParseException e) {
            throw new JoeBidenException(
                    "Invalid date. Use format: yyyy-MM-dd HHmm"
            );
        }
    }

}