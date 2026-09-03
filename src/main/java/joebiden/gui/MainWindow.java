package joebiden.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import joebiden.JoeBiden;
import joebiden.exception.JoeBidenException;
import joebiden.parser.Parser;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox dialogContainer;

    @FXML
    private TextField userInput;

    @FXML
    private Button sendButton;

    private JoeBiden joeBiden;

    private final Image userImage =
            new Image(getClass().getResourceAsStream(
                    "/images/rubberducky.png"
            ));

    private final Image joeBidenImage =
            new Image(getClass().getResourceAsStream(
                    "/images/bidenblast.png"
            ));

    /**
     * Initializes the GUI and keeps the scroll pane
     * scrolled to the latest dialog.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(
                dialogContainer.heightProperty()
        );
    }

    /**
     * Injects the JoeBiden chatbot instance.
     *
     * @param joeBiden Joe Biden chatbot instance
     */
    public void setJoeBiden(JoeBiden joeBiden) {
        this.joeBiden = joeBiden;

        dialogContainer.getChildren().add(
                DialogBox.getDukeDialog(
                        JoeBiden.getWelcomeBanner(),
                        joeBidenImage
                )
        );
    }

    /**
     * Processes and displays the user's input.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();

        if (input.isBlank()) {
            return;
        }

        String response = joeBiden.getResponse(input);

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, joeBidenImage)
        );

        userInput.clear();

        try {
            String command = Parser.getCommand(input);
            String arguments = Parser.getArguments(input);

            if (command.equals("bye")) {
                Parser.validateNoArguments(command, arguments);

                Platform.exit();
            }
        } catch (JoeBidenException e) {
            // getResponse() already displays the error
        }
    }
}
