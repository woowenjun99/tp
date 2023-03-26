package seedu.duke.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Store {
    private final String FILE_NAME = "store.json";
    private final File file = new File(FILE_NAME);
    private final Logger logger = Logger.getLogger("logger");

    private void createFileIfNotExist () throws IOException {
        if (file.createNewFile()) {
            FileWriter writer = new FileWriter(FILE_NAME);
            // Add in an empty array to prevent error from being thrown.
            writer.write("[]");
            writer.close();
        }
    }

    private void getFromStore () {
        try {
            createFileIfNotExist();
        } catch (IOException e) {
            logger.log(Level.WARNING, "An unexpected error occured when creating a file");
        }
    }
}
