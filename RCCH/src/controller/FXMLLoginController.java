/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.text.Text;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import javafx.scene.control.Button;
//import javafx.fxml.Initializable;
import ui.ui;

/**
 *
 * @author James
 */
public class FXMLLoginController {
    
    /**
     * Login window controller fields
     */
    @FXML
    private JFXTextField tf_Username;   // Username text field reference
    private ui application;   //

    /**
     * Method: LogIn()
     * @param event 
     * Usage: Event handle for login current user with provided user name
     */
    @FXML
    public void LogIn(ActionEvent event){
        // Get full username
        String username = tf_Username.getText();
        System.out.println(username);
        // Check if current user name is valid
        boolean valid_user = true;
        if (valid_user){
            // Initialize new RCCH user client
            application.openChatRoom(username);
        }
    }
    
    /**
     * 
     */
    public void setApp(ui app){
        this.application = app;
        System.out.println(app);
    }
    
}