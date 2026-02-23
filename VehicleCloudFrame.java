import java.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
public class VehicleCloudFrame extends JFrame{


private static JFrame frame;
private static JButton submitButton;
private static JButton clearButton;
private static JRadioButton ownerButton;
private static JRadioButton clientButton;
private static JTextField idField;

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
	 frame.setSize(400,500);
	 frame.setTitle("Vehicular Cloud Real Time System");
	 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setBackground(java.awt.Color.PINK);
    frame.setLocationRelativeTo(null); 
    
}
//TO:DO - HAWA - panels, buttons, textfields 
private static void createComponents(){
    // start code here 
}




//TO:DO- GIANNA - action listenrs
private static void attachListeners(){
    //start code here 

}
}