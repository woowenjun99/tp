package seedu.duke.ui;

import seedu.duke.constants.Message;

import java.util.Scanner;

/**
 * The UI Class is used to display the messages to the user.
 */
public class Ui {
    /**
     * The getUserInput reads in the user input as a string
     * and returns the input for the parser.
     *
     * @return The user input as a string.
     */
    public static String getUserInput() {
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        in.close();
        return input;
    }

    private static void println(Object message) {
        System.out.println(message);
    }

    private static void printf(String format, Object... args) {
        System.out.printf(format, args);
    }

    /**
     * The printWelcomeMessage prints out the welcome message upon
     * the initialisation of the chatbot.
     */
    public static void printWelcomeMessage() {
        println(Message.WELCOME.getMessage());
    }
}
