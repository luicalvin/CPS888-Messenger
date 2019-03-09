/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

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
/**
 *
 * @author Calvin Lui
 */
public class FXMLDocumentController{
    
    @FXML
    // The reference of inputText will be injected by the FXML loader
    //private TextField inputText;
    private JFXTextField tf_Username;  
     
    // The reference of outputText will be injected by the FXML loader
    @FXML
    private TextArea outputText;
     
    // location and resources will be automatically injected by the FXML loader 
    @FXML
    private URL location;
     
    @FXML
    private ResourceBundle resources;
     
    // Add a public no-args constructor
    public void FxFXMLController() 
    {
    }
    
    @FXML private FXMLMessengerController FXMLMessengerController;
     
    @FXML
    private void initialize() 
    {
        //FXMLMessengerController.injectFXMLDocumentController(this);
    }
    
    @FXML
    void LogInButton(ActionEvent event) {
        System.out.println(tf_Username.getText());
    }

    
}