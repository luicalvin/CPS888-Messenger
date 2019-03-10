/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rcchserver;

//Utility Imports

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.util.Base64;

//Input Output Imports
import java.io.PrintWriter;
import java.io.IOException;

//Net Imports
import java.net.ServerSocket;
import java.net.Socket;

//Security Imports
import java.security.PrivateKey;
import java.security.PublicKey;




/**
 *
 * @author Hassan Khan
 */
public class RCCHServer {

    /**
     * @param args the command line arguments
     */
    //set of all names and writers currently connected to the server
    //Used to multicast messages to all clients and keep track of name duplicates
    private static Set<String> names = new HashSet<>();
    private static Set<PrintWriter> writers = new HashSet<>();
    
    //Thread dealing with each client
    private static class Handler implements Runnable {
    
        private String name;
        private Socket socket;
        private Scanner in;
        private PrintWriter out;
        private PublicKey publicKey;
        private PrivateKey privateKey;
        
        //Constructor
        public Handler(Socket socket, PrivateKey privateKey, PublicKey publicKey){
        
            this.socket = socket;
            this.privateKey = privateKey;
            this.publicKey = publicKey;
            
        }
        
        public void run(){
            //try incase of failure to securly closing thread
            try{
                System.out.println("in Run");
                //in and out streams for message transmission
                in = new Scanner(socket.getInputStream());
                out = new PrintWriter(socket.getOutputStream(), true);
                
                //Generate random number between 1-99 to act as a Nonce
                Random rand = new Random();
                int n = rand.nextInt(100);
                
                while(true){
                
                    //Initiate first message - to recieve name
                    String N1 = Integer.toString(n);
                    String outMsg = N1 + "//" + "SUBMITNAME";
                    String encrypted = RSA.rsaEncrypt(outMsg, publicKey);
                    out.println(encrypted);
                    
                    //decrypt Name message
                    String initial = in.nextLine();
                    String Decrypted = RSA.rsaDecrypt(Base64.getDecoder().decode(initial), privateKey);
                    String[] inMessage = Decrypted.split("//");
                    
                    name = inMessage[0];
                    String N1back = inMessage[1];
                    
                    //If the name value retrieved is empty ask for name again
                    if (name == null){
                        return;
                    }
                    
                    //Ensure that names variable is synchronzed between threads
                    synchronized(names){
                    
                        //If name isnt empty and isnt already used
                        if(!name.isEmpty() && !names.contains(name)){
                        
                            //add the name to our names list
                            names.add(name);
                            break;
                            
                        }
                    }
                }
                
                //Name Accepted and ready to chat
                String outMsg = "NAMEACCEPTED" + "//" + name;
                String encrypted = RSA.rsaEncrypt(outMsg, publicKey);
                out.println(encrypted);
                
                /*loop through all writers connected and send a message about 
                new joining client*/
                for (PrintWriter writer : writers){
                
                    String Message = "MESSAGE" + "//" + name + " has joined";
                    Message = RSA.rsaEncrypt(Message, publicKey);
                    writer.println(Message);
                }
                
                //add the new writer to the list of writers
                writers.add(out);
                
                //Chat Processing
                /*TODO might have to remove encryption because of issues in encryption decryption
                causing crashes if the the message is over 62 bytes or so*/
                while(true){
                
                    String input = in.nextLine();
                    
                    input = RSA.rsaDecrypt(Base64.getDecoder().decode(input), privateKey);
                    
                    //break chat loop if client sends a quit message
                    if(input.toLowerCase().startsWith("/quit")){
                        return;
                    }
                    
                    //print recieved message to all clients, encrypted
                    for(PrintWriter writer : writers){
                    
                        String Message = "MESSAGE" + "//" + name + ": " + input;
                        Message = RSA.rsaEncrypt(Message, publicKey);
                        writer.println(Message);
                    }
                }
            }
            //catch any exceptions occured during message processing
            catch(Exception e){
            
                System.out.println(e);
            }
            finally{
            
                //if the printwriter is closed remove it from the list
                if(out != null){
                
                    writers.remove(out);
                
                }
                
                //if the name was added, send a message to all others about departure
                if(name != null){
                
                    System.out.println(name + " is leaving the chat");
                    names.remove(name);
                    //Send all client that the handled client has left
                    for(PrintWriter writer : writers){
                    
                        writer.println("MESSAGE" + "//"+ name + " has left");
                    
                    }
                
                }
                //attempt to close the socket
                try{
                    socket.close();
                }
                catch(IOException e){
                    System.out.println(e);
                }
            }
        
        }
        
    }
    
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        System.out.println("Chat is running...");
        
        //Pool of possible threads to handle clients
        ExecutorService pool = Executors.newFixedThreadPool(500);
        
        //Location of "Passwords" folder
        //Storage location for Encryption Passwords
        String path = System.getProperty("user.dir");
        path = path + "\\passwords";
        System.out.println("passwords dir = " + path);
        
        //Loads keys from the passoword folder
        ArrayList keys = loadRSAKeys.serverKey(path);
        PrivateKey mainPrivateKey = (PrivateKey) keys.get(0);
        PublicKey mainPublicKey = (PublicKey) keys.get(1);
        
        //Try to use socket 59001 to listen for incoming client connections
        //TODO set server socket value here
        try (ServerSocket listener = new ServerSocket(59001)){
            //create an new Handler instance on a new Thread to handle client
            while(true){
                
                pool.execute(new Handler(listener.accept(), mainPrivateKey, mainPublicKey));
            
            }
        
        }
        
    }
    
}
