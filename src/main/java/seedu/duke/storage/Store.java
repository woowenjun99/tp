package seedu.duke.storage;

import com.google.gson.Gson;
import seedu.duke.AccountList;
import seedu.duke.Currency;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Store {
    private static final String FILE_NAME = "data/store.json";
    private static final File file = new File(FILE_NAME);
    private static final Gson gson = new Gson();

    private static void createFileIfNotExist () throws IOException {
        if (file.getParentFile().mkdirs()) {
            FileWriter writer = new FileWriter(FILE_NAME);
            // Add in an empty array to prevent error from being thrown.
            writer.write("[]");
            writer.close();
        }
    }

    /**
     * Populates the account list with the previously stored data in the
     * json file.
     *
     * @param accounts The account list hashmap.
     */
    public static void getFromStore (AccountList accounts) throws Exception {

        createFileIfNotExist();
        BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
        Storage[] store = gson.fromJson(br, Storage[].class);
        for (Storage account : store) {
            Currency currency = Currency.valueOf(account.getCurrency());
            long value = account.getValue();
            accounts.addAccount(currency, value);
        }
        // If the 2 lengths do not match, there is a problem.
        assert accounts.getAllAccounts().size() == store.length;
    }
}
