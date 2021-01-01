package at.jtalk.gui;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {

    @FXML
    private javafx.scene.control.Button loginButton;
    @FXML
    private javafx.scene.control.Button signUpButton;
    @FXML
    private javafx.scene.control.Label userLabel;
    @FXML
    private javafx.scene.control.Label passwordLabel;
    @FXML
    private javafx.scene.control.TextField passwordField;
    @FXML
    private javafx.scene.control.TextField textFieldLogin;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void loginAccept(javafx.event.ActionEvent actionEvent) throws IOException {

        Stage stage = (Stage)loginButton.getScene().getWindow();

        Parent root = FXMLLoader.load(getClass().getResource("/chatWindow.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void signUp(javafx.event.ActionEvent actionEvent)throws IOException{

        Stage stage = (Stage)signUpButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/userProfile.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void checkBox(ActionEvent actionEvent) {
        userLabel.setText("Port");
        userLabel.setVisible(true);
        passwordField.setVisible(false);
        passwordLabel.setVisible(false);
        loginButton.setText("Start");
        textFieldLogin.setPromptText("Portnumber");

    }
}
