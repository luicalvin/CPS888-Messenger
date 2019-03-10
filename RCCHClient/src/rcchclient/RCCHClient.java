/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rcchclient;

//Import Output Imports
import java.io.PrintWriter;

//Security Imports
import java.security.PrivateKey;
import java.security.PublicKey;

//Utility Imports
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Base64;

//JFrame Imports
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

//Net Imports
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Hassan Khan
 */
public class RCCHClient {

    /**
     * @param args the command line arguments
     */
    //Server Address
    String serverAddress;
    int serverSocket = 59001;
    //In and out streams for client-server connection
    Scanner in;
    PrintWriter out;
    //Private and Public Keys for encryption
    PrivateKey privateKey;
    PublicKey publicKey;
    
    //Initial framing for Messenger -
    //TODO replace with CALVINs design
    JFrame frame = new JFrame("RCCH Messenger");
    JTextField txtField = new JTextField(50);
    JTextArea msgArea = new JTextArea(20, 50);
    
    //Constructor
    public RCCHClient(String serverAddress, PrivateKey privateKey, PublicKey publicKey) throws Exception{
    
        //instantiate Client variables
        this.serverAddress = serverAddress;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        
        //Initialize Frame settings and features
        //TODO change depending on CALVINS design
        txtField.setEditable(false);
        msgArea.setEditable(false);
        frame.getContentPane().add(txtField, BorderLayout.SOUTH);
        frame.getContentPane().add(new JScrollPane(msgArea), BorderLayout.CENTER);
        frame.pack();
        
        //Send message on Enter, clear txtfield and prepare for next message
        /*TODO might have to remove encryption because of issues in encryption decryption
        causing crashes if the the message is over 62 bytes or so
        */
        txtField.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                String txt = txtField.getText();
                try {
                    String encrypted = RSA.rsaEncrypt(txt, publicKey);
                    out.println(encrypted);
                    txtField.setText("");
                } catch (Exception ex) {
                    System.out.println(e);
                }
            }
        });
    }
    
    //Name Select Pop Up screen
    //TODO replace with CALVINs design
    private String enterName(){
    
        return JOptionPane.showInputDialog(
                frame,
                "Please Enter a Screen Name:",
                "Screne Name Selection",
                JOptionPane.PLAIN_MESSAGE
        );
    }
    
    private void run() throws Exception{
        //Try to connect to server and main message exchange
        try{
            //initialize connection with server and prepare in and out streams
            Socket socket = new Socket(serverAddress, serverSocket);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);

            //Main Message Exchange occurs here
            while(in.hasNextLine()){

                //Retrieve new input, decrypt and seperate into array
                String line = in.nextLine();
                String Decrypted = RSA.rsaDecrypt(Base64.getDecoder().decode(line), privateKey);
                String[] inMessage = Decrypted.split("//");

                //If the server is requesting a User Name
                if("SUBMITNAME".equals(inMessage[1])){

                    //Retrieve a Username from the User
                    String N2 = enterName();

                    //Format and Encrypt Username
                    String outMsg = N2 + "//" + inMessage[0];
                    String encrypted = RSA.rsaEncrypt(outMsg, publicKey);

                    //Send out the Username Encrypted in RSA
                    out.println(encrypted);
                }
                //If the server accepts the submitted UserName
                else if("NAMEACCEPTED".equals(inMessage[0])){

                    //Change Client Window Title and Allow editing in text field
                    //TODO change depending on CALVINs design
                    this.frame.setTitle("RCCH Messenger - " + inMessage[1]);
                    txtField.setEditable(true);
                }
                //If client recieves a message
                else if("MESSAGE".equals(inMessage[0])){

                    //display the message onto the message area
                    msgArea.append(inMessage[1] + "\n");
                }
            }
        }
        //Close the client if fail to connect
        finally{
                frame.setVisible(false);
                frame.dispose();
        }
    
    }
    
    //Initialization of Client, Loading elements
    public static void main(String[] args) throws Exception {
        
        //Define Server Address
        //TODO change depending on server address
        String mainServerAddress = "127.0.0.1";
        //Location of "Passwords" folder
        //Storage location for Encryption Passwords
        String path = System.getProperty("user.dir");
        path = path + "\\passwords";
        System.out.println("passwords dir = " + path);
        
        
        //Loads keys from the passoword folder
        ArrayList keys = loadRSAKeys.clientKey(path);
        PrivateKey mainPrivateKey = (PrivateKey) keys.get(0);
        PublicKey mainPublicKey = (PublicKey) keys.get(1);
        
        //instantiate the client
        RCCHClient client = new RCCHClient(mainServerAddress, mainPrivateKey, mainPublicKey);
        
        //Set close operation and make it visible
        //TODO Change depending on CALVINs design
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setVisible(true);
        
        //Run the client
        client.run();
        
    }
    
}


