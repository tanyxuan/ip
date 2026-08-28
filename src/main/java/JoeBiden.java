public class JoeBiden {

    public static final String NAME = "Joe Biden";

    static Ui ui = new Ui();

    static TaskList tasks;

    private static final String LINE =
            "____________________________________________________________";

    public static void main(String[] args) {
        try {
            tasks = new TaskList(Storage.loadList());
        } catch (JoeBidenException e) {
            ui.showMessage("ERROR! " + e.getMessage());
            tasks = new TaskList();
        }
        System.out.println(getWelcomeBanner());
        while (true) {
            try {
                String input = ui.readInput();
                String command = Parser.getCommand(input);
                String arguments = Parser.getArguments(input);
                switch (command) {
                    case "bye":
                        Parser.validateNoArguments(command, arguments);
                        ui.showMessage(getGoodbyeBanner());
                        ui.close();
                        return;

                    case "list":
                        Parser.validateNoArguments(command, arguments);
                        String output = tasks.listTasks();
                        ui.showMessage(output);
                        break;

                    case "mark": {
                        int number = Parser.getTaskNumber(arguments);

                        ui.showMessage(tasks.markTask(number));

                        Storage.saveList(tasks.getList());
                        break;
                    }

                    case "unmark": {
                        int number = Parser.getTaskNumber(arguments);

                        ui.showMessage(tasks.unmarkTask(number));

                        Storage.saveList(tasks.getList());
                        break;
                    }

                    case "delete": {
                        int number = Parser.getTaskNumber(arguments);

                        Task removedTask = tasks.deleteTask(number);

                        ui.showMessage("Noted. I've removed this task:\n"
                                + removedTask
                                + "\nNow you have " + tasks.size() + " tasks in the list.");

                        Storage.saveList(tasks.getList());
                        break;
                    }

                    case "todo": {
                        String description = Parser.parseDescription(arguments);

                        Task task = new Todo(description);
                        tasks.addTask(task);

                        ui.showMessage("Got it. I've added this task:\n"
                                + task
                                + "\nNow you have " + tasks.size() + " tasks in the list.");

                        Storage.saveList(tasks.getList());
                        break;
                    }
                    case "deadline": {
                        Task task = Parser.parseDeadline(arguments);

                        tasks.addTask(task);

                        ui.showMessage("Got it. I've added this task:\n"
                                + task
                                + "\nNow you have " + tasks.size() + " tasks in the list.");

                        Storage.saveList(tasks.getList());
                        break;
                    }
                    case "event": {
                        Task task = Parser.parseEvent(arguments);

                        tasks.addTask(task);

                        ui.showMessage("Got it. I've added this task:\n"
                                + task
                                + "\nNow you have " + tasks.size() + " tasks in the list.");

                        Storage.saveList(tasks.getList());
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
