import java.awt.*;
import java.awt.font.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.io.*;
import java.net.*;
public class ClientFrame {
    static Socket this_socket;
    static  PrintWriter    out;
    static BufferedReader  in;
    public static void main(String args[]) {
        try {
            JTextField theMessage=new JTextField();
            Font the_font = new Font("SansSerif", Font.BOLD, 20);
            theMessage.setFont(the_font);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            int ChatPort=Integer.parseInt(args[0]);
            InetAddress ip = InetAddress.getByName("localhost");
            try {
                this_socket = new Socket(ip, ChatPort);
                out =new PrintWriter(this_socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(this_socket.getInputStream()));

            } catch (Exception e1) {
                System.out.println("Port "+ChatPort+ " not Available");
                return;
            }
            System.out.println("The Inet address is "+ip+
                               "\n listening on port "+this_socket.getLocalPort()+
                               "\n sending on port "+this_socket.getPort()+"\n\n");
            JFrame frame = new JFrame("Chat Frame");
            JButton button = new JButton("Ask Chat Server");
            button.addActionListener((ev)->{send_receive(theMessage);});
            Container contentPane = frame.getContentPane();
            contentPane.add(theMessage, BorderLayout.CENTER);
            contentPane.add(button, BorderLayout.SOUTH);
            frame.setSize(500, 200);
            frame.setVisible(true);
            //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); ;
            frame.addWindowListener(new WindowAdapter() {
                                        public void windowClosing(WindowEvent e) {
                                            try {
                                                this_socket.close();
                                                System.out.println("Client Closing");
                                                System.exit(0);
                                            } catch (IOException ioe) {
                                                System.out.println("Socket did not close properly.");
                                            }

                                        }
                                    });          
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void send_receive(JTextField the_message){
        ClientFrame.out.println(the_message.getText());
        String the_response="";
        try {
            while ((the_response=ClientFrame.in.readLine())==null) {
                Thread.sleep(500);
            }
            the_message.setText(the_response);
            the_message.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}



