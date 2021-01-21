package at.jtalk.connection;


import at.jtalk.gui.LoginWindow;
import at.jtalk.gui.userProfile;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import java.time.LocalTime;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;

public class Client extends Send {
    private String username;
    private String password;
    private Socket socket;
    private static TextArea outputfield;
    private static LocalTime time = LocalTime.now();
    private static DateTimeFormatter timef = DateTimeFormatter.ofPattern("h:mm a");

    public Client(String username, String password) {
        this.username = username;
        this.password = password;

    }
    //assigning chat window textfield to "outputfield" -> static variable
    public static void setOutputField(TextArea chatWindowField) {
        outputfield = chatWindowField;
    }
    //getter Method for Socket
    public Socket getSocket(){
        return socket;
    }

    /*
    public static void setOutputfield(TextArea outputfield){
        this.outputfield = outputfield;
    }
*/

    /*This method creates a thread which establishes the connection between server and client in the background.
     *It sets the Parameter "clientorserver" as "client"
     */
    public void connectServer(String ipaddress, int port) {
        try {
            socket = new Socket(ipaddress, port);
            System.out.println(socket);
            Thread t = new Connection(socket, "Client");
            t.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*stores the login string with username and password and sends it to the server*/
    public void login() throws IOException {

            OutputStreamWriter oswriter = new OutputStreamWriter(socket.getOutputStream());
            PrintWriter pwriter = new PrintWriter(oswriter);
            pwriter.println("login:::::" + username + ":"  + password);
            pwriter.flush();

        //return 1;
    }
    /*if the username and the password is valid the variable "message" will contain the string "loginsuccessful"
     which allows the access to the chat area.
     else if will transition to the sign up window, where a new user could enter his name, password and ip address
     in order to log in the next time.
     else it splits the message and appends the first part to messagearray[0] which represents the username
     and messagearray[1] which represents the message
     */

    public synchronized static void readMessage(String message) throws IOException {
        if (message.equals("loginsuccessful")) {
            LoginWindow.loginAllowed = true;
        }
        else if (message.equals("newSignIn")) {
            FXMLLoader.load(Client.class.getResource("/userProfile.fxml"));;
        }
        else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    //Control if message is (Logon allowed or disallowed)
                    String[] messagearray = message.split("<:::>");

                    outputfield.appendText(messagearray[0] + "(" + timef.format(time) + "):" + "\n");
                    outputfield.appendText(messagearray[1] + "\n\n");
                }
            });
        }

    }


    public String getUsername() {
        return username;
    }
}