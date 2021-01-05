package at.jtalk.gui;

import at.jtalk.connection.Client;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class chatWindow implements Initializable{

    @FXML
    private TextField messageField;
    @FXML
    private Button sendButton;
    @FXML
    private TextArea chatWindowField;
    @FXML
    private TextField groupNameField;
    @FXML
    private javafx.scene.control.Label connectionLabel;
    @FXML
    private javafx.scene.shape.Circle connectionCircle;

    private static Client chatclient;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chatclient.setOutputfield(chatWindowField);
    }

    public static void setClient(Client client) {
        chatclient = client;
    }


    public void sendText() {
        String messageToSend = "sendall:::::" + messageField.getText();
        chatclient.send(chatclient.getSocket(), messageToSend);

    /*
        String message = "user: ";
        message += messageField.getText();
        messageField.clear();
        chatWindowField.appendText(message+ " \n");
    */

    }

}