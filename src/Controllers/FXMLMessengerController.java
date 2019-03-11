/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


/**
 * FXML Controller class
 *
 * @author Calvin-Lui
 */
//public class FXMLMessengerController implements Initializable {
public class FXMLMessengerController{
    /**
     * Initializes the controller class.
     */
    
    private FXMLMainController mainController;
    public void injectMainController(FXMLMainController mainController) 
    {
        this.mainController = mainController;
    }
    
    public void initialize() {
        // TODO
    }    
    
}
