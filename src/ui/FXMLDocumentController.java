/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.text.Text;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
/**
 *
 * @author Calvin Lui
 */
public class FXMLDocumentController{
    
    @FXML
    // The reference of inputText will be injected by the FXML loader
    private TextField inputText;
     
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
     
    @FXML
    private void initialize() 
    {
    }
     
    @FXML
    private void LogInButton() 
    {
        outputText.setText("You have logged in ");
    }
    
}
