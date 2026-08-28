package exception;

/**
 * Represents an exception specific to the JoeBiden chatbot.
 */
public class JoeBidenException extends Exception {

    /**
     * Creates a JoeBidenException with the specified error message.
     *
     * @param message the error message describing the problem
     */
    public JoeBidenException(String message) {
        super(message);
    }
}