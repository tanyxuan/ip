import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class JoeBiden {

    public static final String NAME = "Joe Biden";

    private static final ArrayList<Task> list = new ArrayList<>();


    private static final String LINE =
            "____________________________________________________________";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println(getWelcomeBanner());
        try {
            list.addAll(Storage.loadList());
        } catch (IOException e) {
            echo("Failed to load tasks.");
        }

        while (true) {
            try {
                String input = scanner.nextLine();

                String[] parts = input.split(" ", 2);
                String command = parts[0].toLowerCase();
                switch (command) {
                    case "bye":
                        if (parts.length > 1) {
                            throw new JoeBidenException(
                                    "The bye command does not take any arguments."
                            );
                        }
                        System.out.println(getGoodbyeBanner());
                        scanner.close();
                        return;
                    case "list":
                        if (parts.length > 1) {
                            throw new JoeBidenException(
                                    "The list command does not take any arguments."
                            );
                        }
                        String output = "Here are the tasks in your list:\n";
                        for (int i = 0; i < list.size(); i++) {
                            output += (i + 1) + ". " + list.get(i) + "\n";
                        }
                        echo(output);
                        break;
                    case "mark": {
                        int number;
                        if (parts.length < 2 || parts[1].isBlank()) {
                            throw new JoeBidenException(
                                    "Please specify a task number to mark."
                            );
                        }
                        try {
                            number = Integer.parseInt(parts[1]);
                        } catch (NumberFormatException e) {
                            throw new JoeBidenException(
                                    "Task number must be a number."
                            );
                        }
                        if (number < 1 || number > list.size()) {
                            throw new JoeBidenException(
                                    "That task number does not exist."
                            );
                        }
                        Task task = list.get(number - 1);
                        echo(task.markDone());
                        try {
                            Storage.saveList(list);
                        } catch (IOException e) {
                            throw new JoeBidenException("Failed to save tasks.");
                        }
                        break;
                    }
                    case "unmark": {
                        if (parts.length < 2 || parts[1].isBlank()) {
                            throw new JoeBidenException(
                                    "Please specify a task number to unmark."
                            );
                        }
                        int number;
                        try {
                            number = Integer.parseInt(parts[1]);
                        } catch (NumberFormatException e) {
                            throw new JoeBidenException(
                                    "Task number must be a number."
                            );
                        }
                        if (number < 1 || number > list.size()) {
                            throw new JoeBidenException(
                                    "That task number does not exist."
                            );
                        }
                        Task task = list.get(number - 1);
                        echo(task.unmark());
                        try {
                            Storage.saveList(list);
                        } catch (IOException e) {
                            throw new JoeBidenException("Failed to save tasks.");
                        }
                        break;
                    }
                    case "delete": {
                        if (parts.length < 2 || parts[1].isBlank()) {
                            throw new JoeBidenException(
                                    "Please specify a task number to delete."
                            );
                        }

                        int number;

                        try {
                            number = Integer.parseInt(parts[1]);
                        } catch (NumberFormatException e) {
                            throw new JoeBidenException(
                                    "Task number must be a number."
                            );
                        }

                        if (number < 1 || number > list.size()) {
                            throw new JoeBidenException(
                                    "That task number does not exist."
                            );
                        }

                        Task removedTask = list.remove(number - 1);

                        echo("Noted. I've removed this task:\n"
                                + removedTask
                                + "\nNow you have " + list.size() + " tasks in the list.");
                        try {
                            Storage.saveList(list);
                        } catch (IOException e) {
                            throw new JoeBidenException("Failed to save tasks.");
                        }
                        break;
                    }
                    case "todo": {
                        if (parts.length < 2 || parts[1].isBlank()) {
                            throw new JoeBidenException(
                                    "The description of a todo cannot be empty."
                            );
                        }
                        Task task = new Todo(parts[1]);
                        list.add(task);
                        echo("Got it. I've added this task:\n"
                                + task.toString()
                                + "\nNow you have " + list.size() + " tasks in the list.");
                        try {
                            Storage.saveList(list);
                        } catch (IOException e) {
                            throw new JoeBidenException("Failed to save tasks.");
                        }
                        break;
                    }
                    case "deadline": {
                        if (parts.length < 2 || parts[1].isBlank()) {
                            throw new JoeBidenException(
                                    "The description of a deadline cannot be empty."
                            );
                        }

                        String[] deadlineParts = parts[1].split(" /by ", 2);

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

                        LocalDateTime by;
                        try {
                            by = LocalDateTime.parse(deadlineParts[1], inputFormat);
                        } catch (DateTimeParseException e) {
                            throw new JoeBidenException(
                                    "Invalid date. Use format: yyyy-MM-dd HHmm"
                            );
                        }

                        Task task = new Deadline(description, by);
                        list.add(task);

                        echo("Got it. I've added this task:\n"
                                + task
                                + "\nNow you have " + list.size() + " tasks in the list.");
                        try {
                            Storage.saveList(list);
                        } catch (IOException e) {
                            throw new JoeBidenException("Failed to save tasks.");
                        }
                        break;
                    }
                    case "event": {
                        if (parts.length < 2 || parts[1].isBlank()) {
                            throw new JoeBidenException(
                                    "The description of an event cannot be empty."
                            );
                        }

                        String[] fromParts = parts[1].split(" /from ", 2);

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

                        LocalDateTime from;
                        LocalDateTime to;

                        try {
                            from = LocalDateTime.parse(toParts[0], inputFormat);
                            to = LocalDateTime.parse(toParts[1], inputFormat);
                        } catch (DateTimeParseException e) {
                            throw new JoeBidenException(
                                    "Invalid date. Use format: yyyy-MM-dd HHmm"
                            );
                        }

                        Task task = new Event(description, from, to);
                        list.add(task);

                        echo("Got it. I've added this task:\n"
                                + task
                                + "\nNow you have " + list.size() + " tasks in the list.");
                        try {
                            Storage.saveList(list);
                        } catch (IOException e) {
                            throw new JoeBidenException("Failed to save tasks.");
                        }
                        break;
                    }
                    default:
                        throw new JoeBidenException(
                                "Invalid input try again"
                        );

                }
            }catch (JoeBidenException e){
                echo("ERROR! "+ e.getMessage());
            }finally {
            }


        }
    }

    public static void echo(String input) {
        System.out.println(LINE);
        System.out.println(input);
        System.out.println(LINE);
    }


    public static String getWelcomeBanner() {
        return LINE + "\n"
                + "Hello! I'm " + NAME + ".\n"
                + "What can I do for you?\n"
                + LINE;
    }

    public static String getGoodbyeBanner() {
        return "Bye. Hope to see you again soon!\n"
                + LINE;
    }
}
