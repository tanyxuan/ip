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

    public static int getTaskNumber(String input) throws JoeBidenException {
        String argument = getArguments(input);

        if (argument.isBlank()) {
            throw new JoeBidenException(
                    "Please specify a task number."
            );
        }

        try {
            return Integer.parseInt(argument);
        } catch (NumberFormatException e) {
            throw new JoeBidenException(
                    "Task number must be a number."
            );
        }
    }
}