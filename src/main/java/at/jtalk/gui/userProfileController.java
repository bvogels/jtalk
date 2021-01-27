package at.jtalk.gui;

import at.jtalk.connection.Client;
import at.jtalk.connection.Send;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class userProfileController implements Initializable{

    @FXML
    private javafx.scene.control.Button exit;
    @FXML
    private javafx.scene.control.Button save;
    @FXML
    private TextField signUpUser;
    @FXML
    private TextField signUpPassword;
    @FXML
    private TextField ipAddressField;
    @FXML
    public static Label usernameTaken;


    private static Client chatclient;

    public static void setClient(Client client) {
        chatclient = client;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    //Changes window from Sign up to login window
    public void exitUserProfile() throws IOException {

        Stage stage = (Stage)exit.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /*Method saves username, password and ipaddress and concatenates them in a new string where the variables are separated by ":". Furthermore,
    * the input of the string "message" will be written to a file from the server*/
    public void saveUserProfile() throws IOException {

        // When save button is clicked, the details are saved
        String user = signUpUser.getText();
        String password = signUpPassword.getText();
        String ipaddress = ipAddressField.getText();
        String message = "Sign In:::::"+user+":"+password+":"+ipaddress;
        Send.send(chatclient.getSocket(),message);

        exitUserProfile(); // the window is closed


        };

    }


