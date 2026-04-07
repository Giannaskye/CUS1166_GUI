import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class VCControllerFrame extends JFrame {

    private JTextArea requestArea;
    private JButton acceptButton;
    private JButton rejectButton;
    private Timer refreshTimer;

    // Javonda: controller computation components
    private final VCController vc = new VCController("VC-001");
    private JTextArea outputArea;
    private JButton computeCompletion;

    public VCControllerFrame() {
        setTitle("VC Controller Panel");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(700, 100);
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("VC Controller Panel", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        requestArea = new JTextArea();
        requestArea.setEditable(false);
        requestArea.setLineWrap(true);
        requestArea.setWrapStyleWord(true);
        requestArea.setText("No pending request.");

        outputArea = new JTextArea(8, 30);
        outputArea.setEditable(false);

        JSplitPane splitPane = new JSplitPane(
            JSplitPane.VERTICAL_SPLIT,
            new JScrollPane(requestArea),
            new JScrollPane(outputArea)
        );
        splitPane.setResizeWeight(0.6);
        add(splitPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        computeCompletion = new JButton("Compute Completion");
        acceptButton = new JButton("Accept");
        rejectButton = new JButton("Reject");

        buttonPanel.add(computeCompletion);
        buttonPanel.add(acceptButton);
        buttonPanel.add(rejectButton);

        add(buttonPanel, BorderLayout.SOUTH);

        acceptButton.addActionListener(e -> handleDecision("ACCEPTED"));
        rejectButton.addActionListener(e -> handleDecision("REJECTED"));

        computeCompletion.addActionListener(e -> {
             File file = new File("vehicular_cloud_log.txt");
             handleComputation(file);
        });

        startRefresh();

        setVisible(true);
    }

    private void startRefresh() {
        refreshTimer = new Timer(1000, e -> {
            String pending = VCServer.getPendingRequest();

            if (pending != null) {
                requestArea.setText(pending);

                if (!pending.equals(VCServer.getLastDisplayedRequest())) {
                    JOptionPane.showMessageDialog(this,
                        "New Request Received:\n\n" + pending);
                    VCServer.setLastDisplayedRequest(pending);
                }
            } else {
                requestArea.setText("No pending request.");
            }
        });

        refreshTimer.start();
    }

    private void handleDecision(String decision) {
        if (VCServer.getPendingRequest() == null) {
            JOptionPane.showMessageDialog(this, "No pending request.");
            return;
        }

        VCServer.setDecision(decision);
        JOptionPane.showMessageDialog(this, "Request " + decision + " by VC Controller.");
    }

    private void handleComputation(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            List<Job> jobs = new ArrayList<>();
            String line;

            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                if (!line.contains("Client ID:")) continue;

                String[] parts = line.split("\\|");
                if (parts.length < 4) {
                    throw new IllegalArgumentException("Invalid client line: " + line);
                }

                String clientID = "";
                LocalDateTime arrival = null;
                int duration = 0;
                LocalDateTime deadline = null;

                for (String part : parts) {
                    String trimmed = part.trim();

                    if (trimmed.startsWith("Client ID:")) {
                        clientID = trimmed.split(":", 2)[1].trim();
                    } else if (trimmed.startsWith("Timestamp:")) {
                        arrival = LocalDateTime.parse(trimmed.split(":", 2)[1].trim());
                    } else if (trimmed.startsWith("Approx job duration (min):")) {
                        duration = Integer.parseInt(trimmed.split(":", 2)[1].trim());
                    } else if (trimmed.startsWith("Job deadline:")) {
                        deadline = LocalDateTime.parse(trimmed.split(":", 2)[1].trim());
                    }
                }

                if (!clientID.isEmpty() && arrival != null && deadline != null) {
                    Job j = new Job(clientID, clientID, arrival, null, duration, deadline);
                    jobs.add(j);
                }
            }

            if (jobs.isEmpty()) {
                outputArea.setText("No client jobs found in the selected file");
                return;
            }

            jobs.sort(Comparator.comparing(Job::getArrivalTime));

           VCController tempVC = new VCController("VC-001");

            for (Job j : jobs) {
                tempVC.assignJob(j);
            }

            LocalDateTime start = LocalDateTime.now();
            String report = tempVC.completion();

            outputArea.setText("==== Starting at " + start + " ====\n" + report);

        } catch (Exception e) {
            outputArea.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}