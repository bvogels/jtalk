package at.jtalk.gui;

import at.jtalk.connection.Client;
import at.jtalk.connection.Server;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {

    @FXML
    private CheckBox runAsServer;
    @FXML
    private TextField ServerPort;
    @FXML
    private TextField ServerIpField;
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
    @FXML
    private javafx.scene.control.Label connectionLabel;
    @FXML
    private javafx.scene.shape.Circle connectionCircle;
    @FXML
    private Label labelConnection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        labelConnection.setVisible(false);
    }

    @FXML
    public void loginAccept(javafx.event.ActionEvent actionEvent) throws IOException {
        /*


*/
   //     if(logonisallowed == 1){

        if(!runAsServer.isSelected()){
            //Connection
            String serveripaddress = ServerIpField.getText();
            int serverport = Integer.parseInt(ServerPort.getText());
            String username = textFieldLogin.getText();
            String password = passwordField.getText();
            Client client = new Client(username, password);
            client.connectServer(serveripaddress,serverport);
            chatWindow.setClient(client);
            client.Logon();

            //GUI
            Stage stage = (Stage)loginButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/chatWindow.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else{
            int port = Integer.parseInt(textFieldLogin.getText());
            Server s = new Server(port);
            s.listen();
        }

     //   }


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
        textFieldLogin.setText("4876");

    }
}
