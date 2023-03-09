package seedu.duke.constants;

/**
 * Contains the messages that will be printed by the ui chatbot
 */
public enum Message {

    GOODBYE("Thank you for using MoneyMoover! We hope to see you again soon :)"),
    WELCOME("Welcome to MoneyMoover!");
    private String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
