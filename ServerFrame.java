import java.awt.*;
import java.awt.font.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.io.*;
import java.net.*;

public class ServerFrame {
    public static int thread_count=0;
    public static void main(String args[]) {
        try {
            JTextField theTextField=new JTextField(); 
            Font the_font = new Font("SansSerif", Font.BOLD, 20);
            theTextField.setFont(the_font);
            JFrame frame = new JFrame("Chat Server Frame");
            JButton button = new JButton("Request Port");
            button.addActionListener((ev)->{try_port(theTextField);});
            Container contentPane = frame.getContentPane();
            contentPane.add(theTextField, BorderLayout.CENTER);
            contentPane.add(button, BorderLayout.SOUTH);
            frame.setSize(500, 200);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); ;
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    public static void try_port(JTextField aTextField){
        int portnumber=Integer.parseInt(aTextField.getText().trim());
        try {
            ServerSocket server = new ServerSocket(portnumber); 
            ChatThread chat = new ChatThread(server); //one per port
            Thread t = new Thread(chat); 
            t.start(); 
            aTextField.setText("Port "+aTextField.getText().trim()+ " is open and listening.");
            aTextField.repaint();
        } catch (Exception e) {
            aTextField.setText("Port "+aTextField.getText().trim()+ " is not available.");
            aTextField.repaint();
        }
    }
}
