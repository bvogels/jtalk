package at.jtalk;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class userProfile implements Initializable{

    @FXML
    private javafx.scene.control.Button exit;
    @FXML
    private javafx.scene.control.Button save;

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

    public void saveUserProfile(ActionEvent actionEvent) throws IOException{



    }
}
