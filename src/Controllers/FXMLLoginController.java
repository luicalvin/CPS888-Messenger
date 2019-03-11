/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

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
public class FXMLLoginController{
    
    @FXML
    // The reference of inputText will be injected by the FXML loader
    //private TextField inputText;
    private JFXTextField tf_Username;  
     
     
    // Add a public no-args constructor
    private FXMLMainController mainController;
    public void injectMainController(FXMLMainController mainController) 
    {
        this.mainController = mainController;
    }
    
    
    @FXML
    void LogInButton(ActionEvent event) {
        System.out.println(tf_Username.getText());
        
    }

    
}
