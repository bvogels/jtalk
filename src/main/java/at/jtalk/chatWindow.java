package at.jtalk;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class chatWindow{

    @FXML
    private TextField messageField;
    @FXML
    private Button sendButton;
    @FXML
    private TextArea chatWindowField;
    @FXML
    private TextField groupNameField;


    public void sendText(ActionEvent actionEvent) {

        String message = "user: ";
        message += messageField.getText();
        messageField.clear();
        chatWindowField.appendText(message+ " \n");

    }
}
