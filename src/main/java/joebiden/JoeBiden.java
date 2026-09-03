package joebiden;

import joebiden.exception.JoeBidenException;
import joebiden.parser.Parser;
import joebiden.storage.Storage;
import joebiden.task.Task;
import joebiden.task.TaskList;
import joebiden.task.Todo;
import joebiden.ui.Ui;

/**
 * Runs the Joe Biden chatbot.
 */
public class JoeBiden {

    public static final String NAME = "Joe Biden";

    private final Ui ui;
    private TaskList tasks;
    private String startupError;

    /**
     * Creates a Joe Biden chatbot and loads saved tasks.
     */
    public JoeBiden() {
        ui = new Ui();

        try {
            tasks = new TaskList(Storage.loadList());
        } catch (JoeBidenException e) {
            tasks = new TaskList();
            startupError = "ERROR! " + e.getMessage();
        }
    }

    /**
     * Starts the text-based version of the chatbot.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        JoeBiden joeBiden = new JoeBiden();
        joeBiden.runCli();
    }

    /**
     * Runs the command-line interface until the user exits.
     */
    public void runCli() {
        if (startupError != null) {
            ui.showMessage(startupError);
        }

        System.out.println(getWelcomeBanner());

        while (true) {
            String input = ui.readInput();
            String response = getResponse(input);

            ui.showMessage(response);

            try {
                String command = Parser.getCommand(input);
                String arguments = Parser.getArguments(input);

                if (command.equals("bye")) {
                    Parser.validateNoArguments(command, arguments);
                    ui.close();
                    return;
                }
            } catch (JoeBidenException e) {
                // Error has already been handled by getResponse().
            }
        }
    }

    /**
     * Generates a response to the user's input.
     *
     * @param input User input.
     * @return Response to the user.
     */
    public String getResponse(String input) {
        try {
            String command = Parser.getCommand(input);
            String arguments = Parser.getArguments(input);

            switch (command) {
                case "bye":
                    Parser.validateNoArguments(command, arguments);
                    return getGoodbyeBanner();

                case "list":
                    Parser.validateNoArguments(command, arguments);
                    return tasks.listTasks();

                case "mark": {
                    int number = Parser.getTaskNumber(arguments);
                    String response = tasks.markTask(number);

                    Storage.saveList(tasks.getList());

                    return response;
                }

                case "unmark": {
                    int number = Parser.getTaskNumber(arguments);
                    String response = tasks.unmarkTask(number);

                    Storage.saveList(tasks.getList());

                    return response;
                }

                case "delete": {
                    int number = Parser.getTaskNumber(arguments);

                    Task removedTask = tasks.deleteTask(number);

                    Storage.saveList(tasks.getList());

                    return "Noted. I've removed this task:\n"
                            + removedTask
                            + "\nNow you have "
                            + tasks.size()
                            + " tasks in the list.";
                }

                case "todo": {
                    String description =
                            Parser.parseDescription(arguments);

                    Task task = new Todo(description);
                    tasks.addTask(task);

                    Storage.saveList(tasks.getList());

                    return "Got it. I've added this task:\n"
                            + task
                            + "\nNow you have "
                            + tasks.size()
                            + " tasks in the list.";
                }

                case "deadline": {
                    Task task = Parser.parseDeadline(arguments);

                    tasks.addTask(task);

                    Storage.saveList(tasks.getList());

                    return "Got it. I've added this task:\n"
                            + task
                            + "\nNow you have "
                            + tasks.size()
                            + " tasks in the list.";
                }

                case "event": {
                    Task task = Parser.parseEvent(arguments);

                    tasks.addTask(task);

                    Storage.saveList(tasks.getList());

                    return "Got it. I've added this task:\n"
                            + task
                            + "\nNow you have "
                            + tasks.size()
                            + " tasks in the list.";
                }

                case "find": {
                    String keyword =
                            Parser.parseFindKeyword(arguments);

                    return tasks.findTasks(keyword);
                }

                default:
                    throw new JoeBidenException(
                            "Invalid input try again"
                    );
            }

        } catch (JoeBidenException e) {
            return "ERROR! " + e.getMessage();
        }
    }

    /**
     * Displays a message surrounded by separator lines.
     *
     * @param input Message to display.
     */
    public static void echo(String input) {
        System.out.println(input);
    }

    /**
     * Returns the welcome message displayed when the chatbot starts.
     *
     * @return Welcome message.
     */
    public static String getWelcomeBanner() {
        return "Hello! I'm " + NAME + ".\n"
                + "What can I do for you?";
    }

    /**
     * Returns the goodbye message displayed when the chatbot exits.
     *
     * @return Goodbye message.
     */
    public static String getGoodbyeBanner() {
        return "Bye. Hope to see you again soon!\n";
    }
}
