package seedu.duke.ui;

import java.util.Scanner;

/**
 * The UI Class is used to display the messages to the user.
 */
public class Ui {
    /**
     * The getUserInput reads in the user input as a string
     * and returns the input for the parser.
     *
     * @return The user input.
     */
    public String getUserInput() {
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        in.close();
        return input;
    }

    private void println(Object message) {
        System.out.println(message);
    }
}
