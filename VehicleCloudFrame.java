import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class VehicleCloudFrame extends JFrame {

    // ── Shanti: frame-level layout reference
    private CardLayout cardLayout;
    private JPanel cards;

    // ── Hawa: radio buttons on home screen
    private JRadioButton ownerButton;
    private JRadioButton clientButton;
    
    //--Hawa: Start Button on Home page
    private JButton startButton;

    // ── Hawa: Owner panel fields (declared at class level so listeners can read them)
    private JTextField ownerIDField;
    private JTextField vehicleIDField;

    private JComboBox<String> vehicleModelBox; //javonda
    private JComboBox<String> vehicleYearBox;
    private JComboBox<String> vehicleMakeBox;

    private JComboBox<String> arrivalHourBox;
    private JComboBox<String> arrivalMinuteBox;
    private JComboBox<String> arrivalAmPmBox;

    private JComboBox<String> departureHourBox;
    private JComboBox<String> departureMinuteBox;
    private JComboBox<String> departureAmPmBox;

    // ── Hawa: Client panel fields
    private JTextField clientIDField;
    private JComboBox<String> jobDurationBox;
    private JTextField jobDeadlineField;

    // ── Hawa: submit buttons (declared at class level so listeners can reference them)
    private JButton ownerSubmitButton;
    private JButton clientSubmitButton;
    private JButton ownerHomeButton;
    private JButton clientHomeButton;
    
 // ── Hawa: Clear buttons
    private JButton ownerClearButton;
    private JButton clientClearButton;

    private java.util.Map<String, String[]> makeModelMap;

    public VehicleCloudFrame() {
        setupFrame();       // Shanti
        createComponents(); // Hawa
        attachListeners();  // Gianna
        setVisible(true);
    }

    // ── Shanti: frame setup
    // FIX: was re-declaring 'frame' as a local variable, shadowing the class field
    private void setupFrame() {
        setSize(550, 520);
        setTitle("Vehicular Cloud Real Time System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(255, 182, 193));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    // ── Hawa: panels, buttons, text fields
    // FIX: all JTextFields now stored as named instance variables so listeners can read them
    // FIX: submit/home buttons declared at class level instead of locally
    private void createComponents() {

        String[] durations = new String[61];
        durations[0] = "Select Minutes";
        for (int i = 1; i <= 60; i++) {
            durations[i] = String.valueOf(i);
        }
        jobDurationBox = new JComboBox<>(durations);

        // Title
        JLabel titleLabel = new JLabel("Vehicular Cloud Console", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

    //Welcome Panel
    JPanel welcomePanel = new JPanel(new BorderLayout());
    welcomePanel.setBackground(new Color(255, 220, 230));

     // Title at the top
     JLabel welcomeTitle = new JLabel("Welcome to VCRTS", JLabel.CENTER);
     welcomeTitle.setFont(new Font("Arial", Font.BOLD, 24));
     welcomeTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // spacing
     welcomePanel.add(welcomeTitle, BorderLayout.NORTH);

   // Description text in the center
     JLabel description = new JLabel(
    "<html><div style='text-align: center; width: 350px;'>"
    + "<b>Vehicular Cloud Real-Time System (VCRTS)</b><br><br>"
    + "This system allows vehicle owners to share computing resources "
    + "and clients to submit computational jobs.<br><br>"
    + "The controller assigns jobs efficiently based on timing "
    + "and system availability."
    + "</div></html>",
    JLabel.CENTER
    );
    description.setFont(new Font("Arial", Font.PLAIN, 16));

        // Wrap description in a GridBagLayout panel to center it vertically and horizontally
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setBackground(new Color(255, 220, 230));
        centerWrapper.add(description);
        welcomePanel.add(centerWrapper, BorderLayout.CENTER);

        // Start button at the bottom, centered
        startButton = new JButton("Start");
        JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonWrapper.setBackground(new Color(255, 220, 230));
        buttonWrapper.add(startButton);
        welcomePanel.add(buttonWrapper, BorderLayout.SOUTH);

        
        
        // Home Panel 
        JPanel homePanel = new JPanel(new GridBagLayout());
        homePanel.setBackground(new Color(255, 220, 230));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel questionLabel = new JLabel("What type of user are you?");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        homePanel.add(questionLabel, gbc);

        ownerButton = new JRadioButton("Owner");
        ownerButton.setBackground(new Color(255, 220, 230));

        clientButton = new JRadioButton("Client");
        clientButton.setBackground(new Color(255, 220, 230));

        ButtonGroup group = new ButtonGroup();
        group.add(ownerButton);
        group.add(clientButton);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridx = 0;
        homePanel.add(ownerButton, gbc);

        gbc.gridx = 1;
        homePanel.add(clientButton, gbc);

        // Owner Panel Edited Javonda
        JPanel ownerPanel = new JPanel(new GridLayout(9, 1, 0, 5));
        ownerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Text fields for user input
        ownerIDField = new JTextField(15);
        vehicleIDField = new JTextField(15);

        // Map that connects vehicle MAKE - possible MODELS
        makeModelMap = new java.util.HashMap<>();
        makeModelMap.put("Toyota", new String[]{"Camry", "Corolla", "RAV4", "Highlander"});
        makeModelMap.put("BMW", new String[]{"3 Series", "5 Series", "X3", "X5"});
        makeModelMap.put("Honda", new String[]{"Civic", "Accord", "CR-V", "Pilot"});
        makeModelMap.put("Tesla", new String[]{"Model 3", "Model S", "Model X", "Model Y"});
        makeModelMap.put("Nissan", new String[]{"Altima", "Sentra", "Rogue", "Maxima"});
        makeModelMap.put("Ford", new String[]{"Fusion", "Escape", "Explorer", "Mustang"});

        // Dropdown for selecting vehicle make (brand)
        vehicleMakeBox = new JComboBox<>(new String[]{
            "Select Make", "Toyota", "BMW", "Honda", "Tesla", "Nissan", "Ford"
        });

        // Dropdown for selecting vehicle model (depends on make)
       // Initially disabled until a make is selected
        vehicleModelBox = new JComboBox<>(new String[]{
            "Select Model"
        });
        vehicleModelBox.setEnabled(false);

        // Create dropdown for vehicle years (1995 → 2026)
        String[] years = new String[2026 - 1995 + 2];
        years[0] = "Select Year";
        int index = 1;
        for (int y = 1995; y <= 2026; y++) {
            years[index++] = String.valueOf(y);
        }

        // Year dropdown
        vehicleYearBox = new JComboBox<>(years);

        // Time dropdowns (for arrival/departure)
        // Hours (12-hour format)

        String[] hours = {
            "HH", "01", "02", "03", "04", "05", "06",
            "07", "08", "09", "10", "11", "12"
        };

        // Minutes (00–59)
        String[] minutes = new String[61];
        minutes[0] = "MM";
        for (int i = 0; i < 60; i++) {
            minutes[i + 1] = String.format("%02d", i);
        }

        // AM / PM selection
        String[] ampm = {"AM/PM", "AM", "PM"};

        // Arrival time dropdowns
        arrivalHourBox = new JComboBox<>(hours);
        arrivalMinuteBox = new JComboBox<>(minutes);
        arrivalAmPmBox = new JComboBox<>(ampm);

        // Departure time dropdowns
        departureHourBox = new JComboBox<>(hours);
        departureMinuteBox = new JComboBox<>(minutes);
        departureAmPmBox = new JComboBox<>(ampm);

        // Add components to panel
        ownerPanel.add(makeLabel("Owner Registration"));
        ownerPanel.add(makeRow("Owner ID:", ownerIDField));// Owner ID input
        ownerPanel.add(makeRow("Vehicle ID:", vehicleIDField));// Vehicle ID input

        ownerPanel.add(makeRow("Vehicle Make:", vehicleMakeBox)); // Make dropdown
        ownerPanel.add(makeRow("Vehicle Model:", vehicleModelBox)); // Model dropdown
        ownerPanel.add(makeRow("Vehicle Year:", vehicleYearBox)); // Year dropdown

        // Custom row for time input (hour + minute + AM/PM)
        ownerPanel.add(makeTimeRow("Arrival Time:", arrivalHourBox, arrivalMinuteBox, arrivalAmPmBox));
        ownerPanel.add(makeTimeRow("Departure Time:", departureHourBox, departureMinuteBox, departureAmPmBox));

        // Panel for buttons (Submit, Clear, Home)
        JPanel ownerButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));

        ownerSubmitButton = new JButton("Submit"); // sends data to server
        ownerHomeButton = new JButton("Home"); // go back to main screen
        ownerClearButton = new JButton("Clear"); // reset fields

        // Add buttons to panel
        ownerButtons.add(ownerSubmitButton);
        ownerButtons.add(ownerClearButton);
        ownerButtons.add(ownerHomeButton);

        // Add button panel to main owner panel
        ownerPanel.add(ownerButtons);

        // Client Panel
        JPanel clientPanel = new JPanel(new GridLayout(5, 1, 0, 5));
        clientPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add the title at the top of the client form
        clientPanel.add(makeLabel("Client Registration"));

        // Add a row for Client ID input using a text field
        clientPanel.add(makeRow("Client ID:",             clientIDField   = new JTextField(15)));
        // Add a row for Job Duration input using a combo box
        clientPanel.add(makeRow("Job Duration (min):", jobDurationBox));
        // Add a row for Job Deadline input using a text field
        clientPanel.add(makeRow("Job Deadline (yyyy-MM-ddTHH:mm):", jobDeadlineField = new JTextField(15)));

        // Panel for buttons (Submit, Clear, Home)
        JPanel clientButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        clientSubmitButton = new JButton("Submit");
        clientHomeButton   = new JButton("Home");
        clientClearButton = new JButton ("Clear");

        // Add buttons to panel
        clientButtons.add(clientSubmitButton);
        clientButtons.add(clientClearButton);
        clientButtons.add(clientHomeButton);
        clientPanel.add(clientButtons);

        // Add all panels to card layout
        cards.add(welcomePanel, "Welcome");
        cards.add(homePanel,   "Home");
        cards.add(ownerPanel,  "Owner");
        cards.add(clientPanel, "Client");

        // Add card panel to frame
        add(cards, BorderLayout.CENTER);
        //make sure the welcome page shows first
        cardLayout.show(cards, "Welcome"); // FIX: was "welcome" (lowercase), CardLayout is case-sensitive
    }

    // Hawa: helper to build a labeled row 
    private JPanel makeRow(String labelText, JTextField field) {
    JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
    JLabel label = new JLabel(labelText); // Create label with provided text
    label.setPreferredSize(new Dimension(180, 25));
    // Set a fixed size for the text field for better alignment
    row.add(label);
    row.add(field);
    return row;
}

    // Overloaded helper to build a labeled row with a combo box instead of text field
    private JPanel makeRow(String labelText, JComboBox<String> comboBox) {
    JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel label = new JLabel(labelText); // Create label with provided text
    label.setPreferredSize(new Dimension(180, 25));
    comboBox.setPreferredSize(new Dimension(165, 25));
    // Set a fixed size for the combo box for better alignment
    row.add(label);
    row.add(comboBox);
    return row;

}
    // Helper to build a row for time input (hour + minute + AM/PM)
    private JPanel makeTimeRow(String labelText, JComboBox<String> hourBox,
                           JComboBox<String> minuteBox, JComboBox<String> amPmBox) { // FIX: added amPmBox to parameters so we can build the full time input row
    JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Align components to the left
    JLabel label = new JLabel(labelText); // Create label with provided text
    label.setPreferredSize(new Dimension(180, 25));

    // Set fixed sizes for the time dropdowns for better alignment
    hourBox.setPreferredSize(new Dimension(60, 25));
    minuteBox.setPreferredSize(new Dimension(60, 25));
    amPmBox.setPreferredSize(new Dimension(80, 25));

     // Add components to the row in order: label, hour dropdown, ":", minute dropdown, AM/PM dropdown
    row.add(label);
    row.add(hourBox);
    row.add(new JLabel(":"));
    row.add(minuteBox);
    row.add(amPmBox);

    return row;
    }

    // Helper to create styled section labels (like form titles)
    private JLabel makeLabel(String text) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        return label;
    }

    // Gianna: action listeners
    // FIX: cardLayout and all buttons now properly in scope as instance variables
    private void attachListeners() {
        ownerButton.addActionListener(e -> cardLayout.show(cards, "Owner"));
        clientButton.addActionListener(e -> cardLayout.show(cards, "Client"));
        startButton.addActionListener(e -> cardLayout.show(cards, "Home"));
        //home buttons
        ownerHomeButton.addActionListener(e -> goHome());
        clientHomeButton.addActionListener(e -> goHome());
       //submit buttons
        ownerSubmitButton.addActionListener(e -> handleOwnerSubmit());
        clientSubmitButton.addActionListener(e -> handleClientSubmit());
        //clear buttons
        ownerClearButton.addActionListener(e -> handleClear());
        clientClearButton.addActionListener(e -> handleClear());

         vehicleMakeBox.addActionListener(e -> {
        String selectedMake = (String) vehicleMakeBox.getSelectedItem();

        vehicleModelBox.removeAllItems();
        vehicleModelBox.addItem("Select Model");

        if (selectedMake != null && makeModelMap.containsKey(selectedMake)) {
            for (String model : makeModelMap.get(selectedMake)) {
                vehicleModelBox.addItem(model);
            }
            vehicleModelBox.setEnabled(true);
        } else {
            vehicleModelBox.setEnabled(false);
        }
    });
    }

    // Gianna: clear all fields
    
    private void handleClear() {
        ownerIDField.setText("");
        vehicleIDField.setText("");

        // Reset make/model/year dropdowns
        vehicleMakeBox.setSelectedIndex(0);
        vehicleModelBox.removeAllItems();
        vehicleModelBox.addItem("Select Model");
        vehicleModelBox.setEnabled(false);
        // Reset year dropdown
        vehicleYearBox.setSelectedIndex(0);
        // Reset time dropdowns
        arrivalHourBox.setSelectedIndex(0);
        arrivalMinuteBox.setSelectedIndex(0);
        arrivalAmPmBox.setSelectedIndex(0);
        // Reset departure time dropdowns
        departureHourBox.setSelectedIndex(0);
        departureMinuteBox.setSelectedIndex(0);
        departureAmPmBox.setSelectedIndex(0);
        // Reset client fields
        clientIDField.setText("");
        jobDurationBox.setSelectedIndex(0);
        jobDeadlineField.setText("");
    }
    

    // Gianna: owner submit handler (EDITED & PATCHED BY MEHMET)
     private void handleOwnerSubmit() {
        try {
        // Read all input values from the form
        String ownerID = ownerIDField.getText().trim();
        String vehicleID = vehicleIDField.getText().trim();
        String vehicleMake = (String) vehicleMakeBox.getSelectedItem();
        String vehicleModel = (String) vehicleModelBox.getSelectedItem();
        String yearText = (String) vehicleYearBox.getSelectedItem();
        // Arrival time inputs
        String arrivalHour = (String) arrivalHourBox.getSelectedItem();
        String arrivalMinute = (String) arrivalMinuteBox.getSelectedItem();
        String arrivalAmPm = (String) arrivalAmPmBox.getSelectedItem();
        // Departure time inputs
        String departureHour = (String) departureHourBox.getSelectedItem();
        String departureMinute = (String) departureMinuteBox.getSelectedItem();
        String departureAmPm = (String) departureAmPmBox.getSelectedItem();
            if (ownerID.isEmpty() || vehicleID.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Owner ID and Vehicle ID are required.");
                return;
            }
            // Check that all dropdowns have a valid selection (not the default index 0)
            if (vehicleMakeBox.getSelectedIndex() == 0 ||
                vehicleModelBox.getSelectedIndex() == 0 ||
                vehicleYearBox.getSelectedIndex() == 0 ||
                arrivalHourBox.getSelectedIndex() == 0 ||
                arrivalMinuteBox.getSelectedIndex() == 0 ||
                arrivalAmPmBox.getSelectedIndex() == 0 ||
                departureHourBox.getSelectedIndex() == 0 ||
                departureMinuteBox.getSelectedIndex() == 0 ||
                departureAmPmBox.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(this, "Please complete all dropdown selections.");
                return;
            }
            // Validate that year is a number
            int vehicleYear = Integer.parseInt(yearText);

            // Convert arrival and departure times to a consistent format (e.g. "HH:mm AM/PM")
            String arrivalTime = arrivalHour + ":" + arrivalMinute + " " + arrivalAmPm;
            String departureTime = departureHour + ":" + departureMinute + " " + departureAmPm;

            // Create Owner object with the collected data
            Owner owner = new Owner(ownerID, vehicleID, vehicleModel, vehicleMake,
                                    vehicleYear, arrivalTime, departureTime);
 
            // Send to VC Controller server and receive decision  ← SOCKET PATH
            // (mirrors handleClientSubmit — server now owns the file write)


        // Javonda (EDITED): run owner request in background so GUI does not freeze
        // Create a new thread so the GUI does not freeze while sending data to server
        new Thread(() -> {

        // Send vehicle/owner info to server (localhost, port 5050)
        // Server will respond with "ACCEPTED", "REJECTED", or error
        String result = owner.sendVehicleInfo("localhost", 5050);

        // Switch back to Swing UI thread to safely update the interface
        SwingUtilities.invokeLater(() -> {

        // If server accepted the vehicle registration
        if ("ACCEPTED".equals(result)) {
            JOptionPane.showMessageDialog(this,
                "Vehicle Registered Successfully!\nOwner ID: " + ownerID +
                "\nData saved to vehicular_cloud_log.txt");

         // If server rejected the registration (e.g. due to pending request or invalid data)
        } else if ("REJECTED".equals(result)) {
            JOptionPane.showMessageDialog(this,
                "Registration Rejected by VC Controller.\nData was NOT saved.");
        // If something went wrong (connection issue, null response, etc.)
        } else {
            JOptionPane.showMessageDialog(this, "Server Communication Error.");
        }
     });
        }).start(); // Start the thread so everything runs without freezing the GUI

    //the Owner class's sendVehicleInfo method- handles the socket communication, including connecting to the server, sending the data, and returning the server's response as a string.
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vehicle year must be a valid number.");
        } catch (Exception e) { // catch any other unexpected exceptions to prevent crashes and show a user-friendly message
            JOptionPane.showMessageDialog(this, "Invalid input. Please check all fields.");
        }
     }
     
    // Gianna: client submit handler
    private void handleClientSubmit() {
        try {
            String clientID          = clientIDField.getText().trim();
           if (jobDurationBox.getSelectedIndex() == 0) {
    JOptionPane.showMessageDialog(this, "Please select a job duration.");
    return;
    }

        int jobDurationMinutes = Integer.parseInt((String) jobDurationBox.getSelectedItem());
            LocalDateTime jobDeadline = LocalDateTime.parse(
                jobDeadlineField.getText().trim(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
            );

            if (clientID.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Client ID is required.");
                return;
            }

            Client client = new Client(clientID, jobDurationMinutes, jobDeadline);

        // milestone 5 - redirected data to socket in Client class by calling jobrequest, returns message based on response from server 
        // Javonda (EDITED): run client request in background so GUI does not freeze
        new Thread(() -> {
            String result = client.jobRequest("localhost", 5050);

        // Switch back to the Swing UI thread (IMPORTANT for updating GUI safely)
            SwingUtilities.invokeLater(() -> {

        // If server accepted the job
        if ("ACCEPTED".equals(result)) {
            JOptionPane.showMessageDialog(this, "Job Accepted by Server");

         // If server rejected the job
        } else if ("REJECTED".equals(result)) {
            JOptionPane.showMessageDialog(this, "Job Rejected by Server");

        // If something went wrong (no response or error)
        } else {
            JOptionPane.showMessageDialog(this, "Server Communication Error"); // FIX: added message for null/invalid response from server to improve user feedback in case of connection issues or server errors
        }
            });
        }).start();// Start the thread actually runs the code inside it 

        } catch (NumberFormatException e) {// catch invalid number format for job duration
            JOptionPane.showMessageDialog(this, "Job duration must be a valid number.");

        } catch (DateTimeParseException e) { // catch invalid date format for job deadline
            JOptionPane.showMessageDialog(this, "Deadline must be in format: yyyy-MM-ddTHH:mm");

        } catch (Exception e) { // catch any other unexpected exceptions to prevent crashes and show a user-friendly message
            JOptionPane.showMessageDialog(this, "Invalid input. Please check all fields."); // FIX: added generic catch for any other exceptions to improve robustness and user feedback in case of unforeseen errors
        }
    }  

    // Gianna: go back home
    private void goHome() {
        handleClear();
        cardLayout.show(cards, "Home");
    }
}
    




