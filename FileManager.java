// MEHMET SOYDAN DATA STORAGE WORK
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileManager {

    // Output log file where all Owner and Client entries are stored
    private static final String FILE_NAME = "vehicular_cloud_log.txt";

    
     //kept for any internal/non-socket use.
     //Formats the user via fileText() and appends to the log.
     
    public static void saveUser(User user) {
        saveRaw(user.fileText());
    }

    
     //NEW — called by VCServer after it decides to ACCEPT a request.
     //Writes the raw data string (already formatted by the sender's fileText())
     //directly to the log. This is the only write path when using sockets,
     //so the file is always written server-side, never client-side.
     
    public static void saveRaw(String data) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME, true))) {
            writer.println(data);   // the formatted line from Owner/Client.fileText()
            writer.println();       // blank line between entries for readability
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
