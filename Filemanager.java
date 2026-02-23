import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileManager {

    private static final String FILE_NAME = "vehicular_cloud_log.txt";

    /**
     * Appends a User (Owner or Client) entry to the log file.
     * Each entry is written on its own line using the user's fileText() method.
     *
     * @param user the Owner or Client object to save
     */
    public static void saveUser(User user) {
        // true = append mode so multiple entries are preserved
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME, true))) {
            writer.println(user.fileText());
            writer.println(); // blank line between entries for readability
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
