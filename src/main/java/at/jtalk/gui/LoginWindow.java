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

    @FXML
    public void loginAccept(javafx.event.ActionEvent actionEvent) {

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
                    Server.setConnectionlabel(labelConnection);
                    startserver.start();
                }
            }
        }catch(IOException ignored){

        }




    }
    @FXML
    public void signUp(javafx.event.ActionEvent actionEvent)throws IOException{
        if(checkIfFilled()) {
            connectToServer();
            chatWindow.setConnected();

            Stage stage = (Stage) signUpButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/userProfile.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }


    }

    @FXML
    public void checkBox(ActionEvent actionEvent) {
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

    public Client connectToServer(){
            String serveripaddress = ServerIpField.getText();
            int serverport = Integer.parseInt(ServerPort.getText());
            String username = usernameportfield.getText();
            String password = passwordField.getText();
            Client client = new Client(username, password);
            client.connectServer(serveripaddress, serverport);
            chatWindow.setClient(client);

            return client;

    }
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
