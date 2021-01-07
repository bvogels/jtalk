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
import java.net.URL;
import java.util.ResourceBundle;

public class LoginWindow implements Initializable {

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
    private javafx.scene.control.TextField usernameportfield;
    @FXML
    private Label labelConnection;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        labelConnection.setVisible(false);
    }

/* This is the method to build the login mechanics. If the username and passwort fields are filled, the client
is allowed to connect to the server (via method connectToServer()). This is only executed if the checkbox
runAsServer is not clicked. However, if it is clicked, the else block is executed and the server is started upon
pressing the start button (don't forget to do this.)
 */

    @FXML
    public void loginAccept() {

        try {
            if (!runAsServer.isSelected()) {
                //Connection
                if (checkIfFilled()) {
                    Client client = connectToServer();
                    client.Login();

                    //GUI
                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    Parent root = FXMLLoader.load(getClass().getResource("/chatWindow.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            } else {
                if (checkIfFilled()) {

                    int port = Integer.parseInt(usernameportfield.getText());
                    Thread startserver = new Thread(new Server(port));
                    Server.setConnectionLabel(labelConnection);
                    startserver.start();
                }
            }
        } catch (IOException ignored) {
            }
        }

/* If a new user wants to sign up for an account, the username/password fields must not be filled. Therefore, the
if statement is reversed so the appropriate FXML code can be executed.
 */

    @FXML
    public void signUp() throws IOException{
        if(!checkIfFilled()) {
            connectToServer();
            chatWindow.setConnected();

            Stage stage = (Stage) signUpButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/userProfile.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

/* Just a couple of text fields. It is checked at the beginning if the checkbox runAsServer has been checked. */

    @FXML
    public void checkBox() {
        if(runAsServer.isSelected()){
            userLabel.setText("Port");
            passwordField.setVisible(false);
            passwordLabel.setVisible(false);
            loginButton.setText("Start");
            usernameportfield.setPromptText("Portnumber");
            usernameportfield.setText("4876");
            labelConnection.setVisible(false);
        }
        else{
            userLabel.setText("Username");
            passwordField.setVisible(true);
            passwordLabel.setVisible(true);
            loginButton.setText("Login");
            usernameportfield.setText("");
            usernameportfield.setPromptText("Username");
            labelConnection.setVisible(false);
        }
    }

/* A client object is returned with the user details username, password, ip address and port number. This method
is called by the loginAccept method. The client object itself has the username and the password as parameters.

 */

    public Client connectToServer(){
            String serverIpAddress = ServerIpField.getText();
            int serverPort = Integer.parseInt(ServerPort.getText());
            String username = usernameportfield.getText();
            String password = passwordField.getText();
            Client client = new Client(username, password);
            client.connectServer(serverIpAddress, serverPort);
            chatWindow.setClient(client);

            return client;
    }

/* The status of the text fields is evaluated. The fields have to be filled for all possibilities except
if a new user requests credentials via sign up.
 */


    public boolean checkIfFilled(){
        if((ServerIpField.getText().isEmpty() || ServerPort.getText().isEmpty() || usernameportfield.getText().isEmpty() || passwordField.getText().isEmpty()) && !runAsServer.isSelected()){
            setLabelConnection("Bitte alle Felder ausfuellen");
            return false;

        }else if(usernameportfield.getText().isEmpty() && runAsServer.isSelected()) {
            setLabelConnection("Bitte Port ausfuellen");
            return false;
        }
        else
        {
            return true;
        }
    }

    public void setLabelConnection(String text){
        labelConnection.setVisible(true);
        labelConnection.setText(text);
    }
}
