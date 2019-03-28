/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXScrollPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.control.TextArea;

/**

/**
 *
 * @author James
 */
public class FXMLMessagerController {
  
    /**
     * Messenger window controller fields
     */
    @FXML
    private JFXTextField tf_chatInput;   // Message input textfield reference
    @FXML
    private ScrollPane chat_room;   // Chat room message area
    private TextArea chat_area;
    
    /**
     * Method: Send()
     * @param event 
     * Usage: Event handle to send message to chat room members
     */
    @FXML
    public void Send(KeyEvent event){
        if (event.getCode().equals(KeyCode.ENTER)){
            // Display input text at system out for debug
            System.out.println(tf_chatInput.getText());
            String msg = tf_chatInput.getText();
            // Clear Input text field
            tf_chatInput.setText("");
            // Send msg content to messenger application server
            updateChat(msg);
        }
    }
    
    @FXML
    /**
     * Method: chatAreaSetup
     * Usage: FXML action reference
     */
    public void chatAreaSetup(){
        // Create new text area
        chat_area = new TextArea();
        // Configure text area to disable inline editing
        chat_area.setEditable(false);
        // Configure chatroom scroll feature (may not need)
        chat_room.setContent(chat_area);
        chat_room.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        chat_room.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
    }
    
    @FXML
    /**
     * Method updateChat(s)
     * @param s
     * Usage: Update chat area of chat room window with provided string
     */
    public void updateChat(String s){
        // Update chat room message area
        chat_area.appendText(s+"\n");
    }
}
