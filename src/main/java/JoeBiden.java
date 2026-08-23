import java.util.ArrayList;
import java.util.Scanner;
public class JoeBiden {

    public static final String NAME = "Joe Biden";

    private static final ArrayList<String> list = new ArrayList<>();


    private static final String LINE =
            "____________________________________________________________";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println(getWelcomeBanner());
        while (true) {

            String input = scanner.nextLine();

            String[] parts = input.split(" ", 2);
            String command = parts[0].toLowerCase();
            switch(command){
                case "bye":
                    System.out.println(getGoodbyeBanner());
                    scanner.close();
                    return;
                case "list":
                    for (int i = 0; i < list.size(); i++) {
                        System.out.println((i + 1) + ". " + list.get(i));
                    }
                    break;
                default:
                    if(parts.length > 1){
                        list.add(parts[1]);
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
