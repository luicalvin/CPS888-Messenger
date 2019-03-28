/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import controller.*;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.IOException;

/**
 *
 * @author James
 */
public class ui extends Application{
    // UI Variables
    private Stage app_window;
    
    
    @Override
    // Configure GUI applicaiton start action
    public void start(Stage stage) throws Exception {
        
        app_window = stage;   //Set main app stage as primary stage
        // Load login screen scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLLogin.fxml"));
        Parent root = (Parent)loader.load();
        Scene login_scene = new Scene (root);
        // Setup login controller
        FXMLLoginController login_controller = (FXMLLoginController) loader.getController();
        // Set application for login controller
        login_controller.setApp(this);
        // Set window title and content
        stage.setTitle("RCCH Login");
        // Set login screne content
        stage.setScene(login_scene);
        stage.show();
        
    }
    
    /**
     * Method: openChatRoom()
     * @param
     * Usage: Update UI window to transition to chat room interface
     */
    public void openChatRoom(){
        try{
            // Load chatroom fxml as new root node
            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("FXMLMessenger.fxml"));
            Parent root2 = (Parent)loader2.load();
            FXMLMessengerController msg_controller = (FXMLMessengerController) loader2.getController();
            // Get new chatroom scene for gui
            Scene chatroom_scene = new Scene(root2);
            // Initialize chatroom chat area
            msg_controller.chatAreaSetup();
            // Set new chatroom scene and update stage title
            app_window.setScene(chatroom_scene);
            app_window.setTitle("RCCH Chat Room");
            app_window.show();
        }
        catch(IOException e){
            System.out.println("Unable to load messenger fxml");
        }
    }
     
    public static void main(String[] args){
        launch(args);
    }
    
}
