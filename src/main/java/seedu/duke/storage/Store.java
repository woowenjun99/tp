package seedu.duke.storage;

import com.google.gson.Gson;
import seedu.duke.AccountList;
import seedu.duke.Currency;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Store {
    private static final String FILE_NAME = "store.json";
    private static final File file = new File(FILE_NAME);
    private static final Gson gson = new Gson();
    private static final Logger logger = Logger.getLogger("logger");

    private static void createFileIfNotExist () throws IOException {
        if (file.createNewFile()) {
            FileWriter writer = new FileWriter(FILE_NAME);
            // Add in an empty array to prevent error from being thrown.
            writer.write("[]");
            writer.close();
        }
    }

    public static void getFromStore (AccountList accounts) {
        try {
            createFileIfNotExist();
            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
            Storage[] store = gson.fromJson(br, Storage[].class);
            for (Storage account: store) {
                Currency currency = Currency.valueOf(account.getCurrency());
                long value = account.getValue();
                accounts.addAccount(currency, value);
            }
            // If the 2 lengths do not match, there is a problem.
            assert accounts.getAllAccounts().size() == store.length;
        } catch (Exception e) {
            logger.log(Level.WARNING, "An unexpected error occurred!");
        }
    }
}
