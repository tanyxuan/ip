public class JoeBiden {

    public static final String NAME = "Joe Biden";

    public static void main(String[] args) {
        System.out.println(getWelcomeBanner());
        System.out.println(getGoodbyeBanner());
    }
    public static String getWelcomeBanner() {
        return "____________________________________________________________\n"
                + "Hello! I'm " + NAME + ".\n"
                + "What can I do for you?\n"
                + "____________________________________________________________";
    }

    public static String getGoodbyeBanner() {
        return "Bye. Hope to see you again soon!\n"
                + "____________________________________________________________";
    }
}
