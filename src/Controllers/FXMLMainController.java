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
 * @author Calvin-Lui
 */
public class FXMLMainController {
    @FXML private FXMLLoginController LoginController;
    @FXML private FXMLMessengerController MessengerController;
    
    @FXML private void initialize(){
        LoginController.injectMainController(this);
        MessengerController.injectMainController(this);
    }
}
