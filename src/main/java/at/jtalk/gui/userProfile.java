package at.jtalk.gui;

import at.jtalk.connection.Client;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class userProfile implements Initializable{

    @FXML
    private javafx.scene.control.Button exit;
    @FXML
    private javafx.scene.control.Button save;
    @FXML
    TextField signUpUser;
    @FXML
    TextField signUpPassword;
    @FXML
    TextField ipAddressField;

    private static Client chatclient;

    public static void setClient(Client client) {
        chatclient = client;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void exitUserProfile() throws IOException {

        Stage stage = (Stage)exit.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void saveUserProfile() throws IOException {

        // When save button is clicked, the details are saved

        String user = signUpUser.getText();
        String password = signUpPassword.getText();
        String ipaddress = ipAddressField.getText();
        String message = "Sign In:::::"+user+":"+password+":"+ipaddress;
        chatclient.send(chatclient.getSocket(),message);
        EventHandler<ActionEvent> saveProfile; // this is a lambda expression
        saveProfile = event -> {};

        save.setOnAction(saveProfile); // the contents of the variables is stored into the file
        exitUserProfile(); // the window is closed
    }
}

