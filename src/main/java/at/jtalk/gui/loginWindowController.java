package at.jtalk.gui;

import at.jtalk.connection.Client;
import at.jtalk.connection.Server;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;


public class loginWindowController implements Initializable {

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
    @FXML
    private javafx.scene.shape.Circle loginConCirc;

    public static boolean loginAllowed; //
    public static boolean serverHasStarted = false;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(serverHasStarted) {

            loginConCirc.setFill(Color.GREEN);
        }
        labelConnection.setVisible(false);
    }

/* This is the method to build the login mechanism. If the username and password fields are filled, the client
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
                    if (serverHasStarted) {

                        client.login();
                        TimeUnit.SECONDS.sleep(3);//changed to two instead of 3
                        if (loginAllowed) {

                            //GUI
                            loginConCirc.setFill(Color.GREEN); // sets connection circle on green on login screen
                            chatWindowController.setConnected();
                            Stage stage = (Stage) loginButton.getScene().getWindow();
                            Parent root = FXMLLoader.load(getClass().getResource("/chatWindow.fxml"));
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.show();
                        } else {
                            labelConnection.setTextFill(Color.RED);
                            setLabelConnection("Wrong credentials.");
                        }
                    } else {
                        labelConnection.setVisible(true);
                        labelConnection.setTextFill(Color.RED);
                        labelConnection.setText("Connection to server required");
                    }
                }
            } else {

/* When the software is run as server, the following code is executed. The server is one thread, started with
startserver.start(). It calls the constructor of a server in the Server.java class. If start is clicked then, the available
user details are loaded through the method populateUserDataList().
 */

                if (checkIfFilled()) {

                    int port = Integer.parseInt(usernameportfield.getText());
                    Thread startserver = new Thread(new Server(port));
                    Server.setConnectionLabel(labelConnection);
                    startserver.start();
                    Server.populateUserDataList();
                    if(serverHasStarted) {
                        loginConCirc.setFill(Color.GREEN);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            }
        }

/* If a new user wants to sign up for an account, the username/password fields must not be filled. Therefore, the
*if statement is reversed so the appropriate FXML code can be executed.
*/

    @FXML
    public void signUp() throws IOException{
            if (!checkIfFilled()) {
                Client client = connectToServer();
                if (serverHasStarted) {
                    chatWindowController.setConnected();
                    userProfileController.setClient(client);
                    Stage stage = (Stage) signUpButton.getScene().getWindow();
                    Parent root = FXMLLoader.load(getClass().getResource("/userProfile.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } else {
                    labelConnection.setVisible(true);
                    labelConnection.setTextFill(Color.RED);
                    labelConnection.setText("Connection to server required");
                }
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
*is called by the loginAccept method. The client object itself has the username and the password as parameters.
*/

    public Client connectToServer(){
            String serverIpAddress = ServerIpField.getText();
            int serverPort = Integer.parseInt(ServerPort.getText());
            String username = usernameportfield.getText();
            String password = passwordField.getText();
            Client client = new Client(username, password);
            client.connectServer(serverIpAddress, serverPort);
            chatWindowController.setClient(client);

            return client;
    }

/* The status of the text fields is evaluated. The fields have to be filled for all possibilities except
*   if a new user requests credentials via sign up.
*/


    public boolean checkIfFilled(){
        if((ServerIpField.getText().isEmpty() || ServerPort.getText().isEmpty() || usernameportfield.getText().isEmpty() || passwordField.getText().isEmpty()) && !runAsServer.isSelected()){
            labelConnection.setTextFill(Color.RED);
            setLabelConnection("Please fill in all fields");
            return false;

        }else if(usernameportfield.getText().isEmpty() && runAsServer.isSelected()) {
            labelConnection.setTextFill(Color.RED);
            setLabelConnection("Port field is empty");
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
