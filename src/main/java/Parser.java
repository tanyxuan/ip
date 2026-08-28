public class Parser {
    public static String getArguments(String input) {
        String[] parts = input.split(" ", 2);

        if (parts.length < 2) {
            return "";
        }

        return parts[1];
    }
    public static String getCommand(String input) {
        String[] parts = input.split(" ", 2);
        return parts[0].toLowerCase();
    }
}
