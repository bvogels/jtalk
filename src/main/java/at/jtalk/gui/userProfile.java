package at.jtalk.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void exitUserProfile(ActionEvent actionEvent) throws IOException {

        Stage stage = (Stage)exit.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void saveUserProfile(ActionEvent actionEvent) throws IOException {
        // obtaining the user details
        String user = signUpUser.getText();
        String password = signUpPassword.getText();
        String ipaddress = ipAddressField.getText();

        // When save button is clicked, the details are saved
        EventHandler<ActionEvent> saveProfile = event -> {
            try {
                File users = new File("users.txt");
                if (users.createNewFile()) {
                    System.out.println("Created new file of user names with name users.txt");
                } else {
                    System.out.println("File already exists. Appending.");
                }
            } catch (IOException error) {
                System.out.println("Bad luck. File could not be created.");
                error.printStackTrace();
            }

            try {
                FileWriter saveUserDetails = new FileWriter("users.txt");
                saveUserDetails.write(user + password + ipaddress);
                saveUserDetails.close();
            } catch (IOException error) {
                System.out.println("Could not save data.");
            }
        };
        save.setOnAction(saveProfile);
    }
}

