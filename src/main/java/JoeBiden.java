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
        while (true) {

            String input = scanner.nextLine();

            String[] parts = input.split(" ", 2);
            String command = parts[0].toLowerCase();
            if(!(parts.length > 1)) {

            }
            switch(command){
                case "bye":
                    System.out.println(getGoodbyeBanner());
                    scanner.close();
                    return;
                case "list":
                    String output = "Here are the tasks in your list:\n";
                    for (int i = 0; i < list.size(); i++) {
                        output += (i + 1) + ". " + list.get(i) + "\n";
                    }
                    echo(output);
                    break;
                case "mark":
                    if(parts.length > 1) {
                        int number = Integer.parseInt(parts[1]);
                        Task task = list.get(number - 1);

                        System.out.println(LINE + "\n"
                                + task.markDone()
                                + LINE);
                    }
                    break;
                case "unmark":
                    if(parts.length > 1) {
                        int number = Integer.parseInt(parts[1]);
                        Task task = list.get(number - 1);
                        System.out.println(LINE + "\n"
                                + task.unmark()
                                + LINE);
                    }
                    break;

                case "todo":
                    if (parts.length > 1) {
                        Task task = new Todo(parts[1]);
                        list.add(task);
                        echo("Got it. I've added this task:\n"
                                + task.toString()
                                + "\nNow you have " + list.size() + " tasks in the list.");
                    }
                    break;

                case "deadline":
                    if (parts.length > 1) {
                        String[] deadlineParts = parts[1].split(" /by ", 2);

                        String description = deadlineParts[0];
                        String by = deadlineParts[1];

                        Task task = new Deadline(description, by);
                        list.add(task);

                        echo("Got it. I've added this task:\n"
                                + task
                                + "\nNow you have " + list.size() + " tasks in the list.");
                    }
                    break;

                case "event":
                    if (parts.length > 1) {
                        String[] fromParts = parts[1].split(" /from ", 2);

                        String description = fromParts[0];

                        String[] toParts = fromParts[1].split(" /to ", 2);

                        String from = toParts[0];
                        String to = toParts[1];

                        Task task = new Event(description, from, to);
                        list.add(task);

                        echo("Got it. I've added this task:\n"
                                + task
                                + "\nNow you have " + list.size() + " tasks in the list.");
                    }
                    break;

                default:
                    if(parts.length > 1){
                        Task task = new Task(parts[1]);
                        list.add(task);
                        echo("added: " + parts[1]);
                    }
                    break;
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
