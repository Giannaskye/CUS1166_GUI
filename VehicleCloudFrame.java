import javax.swing.*;           
import java.awt.FlowLayout;      
import java.awt.GridLayout;     
import java.awt.CardLayout;      
import java.awt.BorderLayout;    
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
public class VehicleCloudFrame extends JFrame{


private static JFrame frame;
private static JButton ownerHomeButton;
private static JButton clientHomeButton;
private static JButton enterButton;
private static JButton clearButton;
private static JRadioButton ownerButton;
private static JRadioButton clientButton;
private static JTextField idField;
private static JPanel cards;

public VehicleCloudFrame (){
    
    setupFrame();        // TO:DO - shanti
    createComponents();  // TO:DO - hawa
    attachListeners();   // TO:DO - gianna

    frame.setVisible(true);
}

//TO:DO - SHANTI - frame + design 
private static void setupFrame(){
    //start code here
	JFrame frame = new JFrame();
	 frame.setSize(500,600);
	 frame.setTitle("Vehicular Cloud Real Time System");
	 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setBackground(java.awt.Color.PINK);
    frame.setLocationRelativeTo(null); 
    
}




//TO:DO - HAWA - panels, buttons, textfields 
private static void createComponents(){

// Creating the titile section 
JLabel titleLabel = new JLabel("Vehicular Cloud Console", JLabel.CENTER); // want to center the title in the center of the frame
frame.add(titleLabel, BorderLayout.NORTH); // you want to add the title to frame and put the title at the top of the frame 

// Main Panel for the three panels  
cards = new JPanel(new CardLayout());

 // --- Home Panel ---
    JPanel homePanel = new JPanel();
    homePanel.setLayout(null);  // not using any 

    JLabel questionLabel = new JLabel("What type of user are you?");
    questionLabel.setBounds(120, 20, 200, 30);
    homePanel.add(questionLabel);
 
    // Owner/Client buttons
    ownerButton = new JRadioButton("Owner");
    ownerButton.setBounds(100, 70, 100, 30);
    clientButton = new JRadioButton("Client");
    clientButton.setBounds(220, 70, 100, 30);

    // Group them so only one can be selected
    ButtonGroup group = new ButtonGroup();
    group.add(ownerButton);
    group.add(clientButton);

    // adding owner and client button to the home panel 
    homePanel.add(ownerButton);
    homePanel.add(clientButton);


 // --Owner Panel---
     JPanel ownerPanel = new JPanel(new GridLayout(5, 1, 0, 10));

     // Title 
    
     JLabel ownerTitle = new JLabel("Owner");
     ownerPanel.add(ownerTitle);

     // row 1 
    JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    row1.add(new JLabel("Owner ID:"));
    row1.add(new JTextField(15));
    row1.add(new JButton("Enter"));
    ownerPanel.add(row1);

    //row 2
    JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    row2.add(new JLabel("Vehicle Info:"));
    row2.add(new JTextField(15));
    row2.add(new JButton("Enter"));
    ownerPanel.add(row2);

    //row 3 

    JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    row3.add(new JLabel("Approx. Residency Time:"));
    row3.add(new JTextField(15));
    row3.add(new JButton("Enter"));
    ownerPanel.add(row3);


    // back home button from owner 
    JPanel ownerHome= new JPanel(new FlowLayout(FlowLayout.LEFT));
    JButton ownerHomeButton = new JButton("Home");
    ownerHome.add(ownerHomeButton);
    ownerPanel.add(ownerHome);



// --Client Panel--
     JPanel clientPanel = new JPanel(new GridLayout(5, 1, 0, 10));
     
     
     // Titile 
     JLabel clientTitle = new JLabel("Client");
     clientPanel.add(clientTitle);

     //row 1 (Client ID): 
    JPanel clientID = new JPanel(new FlowLayout(FlowLayout.LEFT));
    clientID.add(new JLabel("Client ID:"));
    clientID.add(new JTextField(15));
    clientID.add(new JButton("Enter"));
    clientPanel.add(clientID);

    //row 2( Appromximate job Duration)
    JPanel Approximatejobduration = new JPanel(new FlowLayout(FlowLayout.LEFT));
    Approximatejobduration.add(new JLabel("Approximate job duration:"));
    Approximatejobduration.add(new JTextField(15));
    Approximatejobduration.add(new JButton("Enter"));
    clientPanel.add(Approximatejobduration);

    // row 3(Job deadline)
    JPanel jobDeadline = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jobDeadline.add(new JLabel("Job Deadline:"));
    jobDeadline.add(new JTextField(15));
    jobDeadline.add(new JButton("Enter"));
    clientPanel.add(jobDeadline);
 

    // home button from client 
    JPanel clientHome= new JPanel(new FlowLayout(FlowLayout.LEFT));
    JButton clientHomeButton = new JButton("Home");
    clientHome.add(clientHomeButton);
    clientPanel.add(clientHome);


    
cards.add(homePanel, "Home");
cards.add(ownerPanel, "Owner");
cards.add(clientPanel, "Client");
frame.add(cards, BorderLayout.CENTER); // Add the CardLayout container to the frame







}




//TO:DO- GIANNA - action listenrs
    private void attachListeners(){
    //home screen buttons 
    clientButton.addActionListener(e-> cardLayout.show(cards, "Client"));
    ownerButton.addActionListener(e-> cardLayout.show(cards,"Owner"));
    //owner panel buttons 
    ownerHomeButton.addActionListener(e-> goHome());
    ownerSubmitButton.addActionListener(e-> handleOwnerSubmit());
    // client panel buttons 
    clientHomeButton.addActionListener(e-> goHome());
    clientSubmitButton.addActionListener(e->  handleClientSubmit());

}
private void handleClear(){
    idField.setText("");
    modelField.setText("");
    yearField.setText("");
    restTimeField.setText("");
    durationField.setText("");
    deadlineField.setText("");
    durationField.setText("");
    
}
//owner button clicked
private void handleOwnerSubmit(){
        try{
            String vehicleID = idField.getText();
	        String vehicleModel = modelField.getText();
	        String vehicleMake = makeField.getText();
	        int vehicleYear = Integer.parseInt(yearField.getText());
	        String residencyTime = resTimeField.getText();
			Owner owner = new Owner(vehicleID,vehicleMake,vehicleModel,vehicleYear,residencyTime);
        JOptionPane.showMessageDialog(this, "Owner Registered: " + vehicleID);
    }
    catch (NumberFormatException e ){
        JOptionPane.showMessageDialog(this,
        "Please enter valid numeric values.");
    
    } catch(Exception e ){
        JOptionPane.showMessageDialog(this, "Invalid input check fileds");
    }
}
 //client button clicked 
private void handleClientSubmit(){ 
    try{
        String clientId = idField.getText();
        int jobDurationMinutes = Integer.parseInt(durationField.getText());
        LocalDateTime jobDeadline = LocalDateTime.parse(deadlineField.getText());

        Client client = new Client(clientId, jobDurationMinutes, jobDeadline);
        JOptionPane.showMessageDialog(this,"Client Registered: "
        + clientId);
    
    }catch (NumberFormatException e ){
    JOptionPane.showMessageDialog(this,
    "Job duration must be a vaild numebr ");

} catch(Exception e ){
    JOptionPane.showMessageDialog(this, "Invalid deadline format.");
}
}

private void goHome(){
    handleClear();
    cardLayout.show(cards, "Home");

}

}
}