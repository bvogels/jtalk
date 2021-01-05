package at.jtalk.gui;

import at.jtalk.connection.Client;
import at.jtalk.connection.Send;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

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

    private static boolean connected;

    public static void setConnected() {
        connected = true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Client.setOutputField(chatWindowField);
        setConnectionGui();

    }

    public void setConnectionGui(){
        if(connected){
            connectionLabel.setText("Connected");
            connectionLabel.setTextFill(Color.GREEN);
            connectionCircle.setFill(Color.GREEN);
        }
    }


    public static void setClient(Client client) {
        chatclient = client;
    }


    public void sendText(ActionEvent actionEvent) {
       String messagetosend = "sendall:::::" +chatclient.getUsername() + "<:::>" + messageField.getText();
       Send.send(chatclient.getSocket(), messagetosend);
       messageField.clear();

    }

}