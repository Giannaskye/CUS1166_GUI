import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class VCServer {

    private static final int PORT = 5000;

    public static void main(String[] args) {
        System.out.println("VC Controller Server starting on port " + PORT + " ...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening. Waiting for connections...");

            // Keep accepting new connections indefinitely
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New connection from: " + clientSocket.getInetAddress());

                // Spin up a new thread for each connection (Note II - multi-threaded)
                Thread handler = new Thread(new RequestHandler(clientSocket));
                handler.setDaemon(true); // dies when main thread exits
                handler.start();
            }

        } catch (Exception e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // -----------------------------------------------------------------------
    // Inner class: handles one client/owner connection
    // -----------------------------------------------------------------------
    static class RequestHandler implements Runnable {

        private final Socket socket;

        RequestHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (DataInputStream  dis = new DataInputStream(socket.getInputStream());
                 DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {

                // 1. Read the incoming data string sent by Owner or Client
                String data = dis.readUTF();
                System.out.println("\n--- Incoming Request ---");
                System.out.println(data);

                // 2. Send acknowledgment immediately (assignment: "acknowledgment should
                //    be sent upon receiving any request")
                dos.writeUTF("Request received by VC Controller");
                dos.flush();

                // 3. Decide: ACCEPT or REJECT
                //    Policy: accept if the data is non-empty and well-formed.
                //    You can replace this logic with any business rule you need.
                String decision = evaluate(data);

                // 4. If accepted, persist to file (server owns the file — clients no longer
                //    call FileManager directly)
                if ("ACCEPTED".equals(decision)) {
                    FileManager.saveRaw(data);   // see FileManager addition below
                    System.out.println("Decision: ACCEPTED — data saved to log.");
                } else {
                    System.out.println("Decision: REJECTED — data NOT saved.");
                }

                // 5. Send the decision back to the caller
                dos.writeUTF(decision);
                dos.flush();

            } catch (Exception e) {
                System.err.println("Handler error: " + e.getMessage());
                e.printStackTrace();
            } finally {
                try { socket.close(); } catch (Exception ignored) {}
            }
        }

        private String evaluate(String data) {
            if (data == null || data.isBlank()) {
                return "REJECTED";
            }
            // Owner entries start with "Owner ID:", client entries with "Request ID:"
            if (data.contains("Owner ID:") || data.contains("Request ID:")) {
                return "ACCEPTED";
            }
            return "REJECTED";
        }
    }
}
