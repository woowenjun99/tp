package seedu.duke.constants;

/**
 * Contains the messages that will be printed by the ui chatbot
 */
public enum Message {
    WELCOME("Welcome");

    Message(String message) {
        this.message = message;
    }

    private String message;

    public String getMessage() {
        return message;
    }
}
