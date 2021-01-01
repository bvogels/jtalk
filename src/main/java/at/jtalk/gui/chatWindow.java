package at.jtalk.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

public class chatWindow{

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




    public void sendText(ActionEvent actionEvent) {

        String message = "";
        message +=
        message += messageField.getText();
        messageField.clear();
        chatWindowField.appendText(message+ " \n");

    }
}
