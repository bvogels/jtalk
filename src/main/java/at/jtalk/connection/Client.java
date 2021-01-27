package at.jtalk.connection;


import at.jtalk.gui.loginWindowController;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

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
    private static Circle outputConnectionCircle;
    private static Label outputconnectionLabel;
    private static Label outputUserList;



    public Client(String username, String password) {
        this.username = username;
        this.password = password;

    }
    //assigning chat window textfield to "outputfield" -> static variable
    public static void setOutputFields(TextArea chatWindowField, Circle connectionCircle, Label connectionLabel, Label userList) {
        outputfield = chatWindowField;
        outputConnectionCircle = connectionCircle;
        outputconnectionLabel = connectionLabel;
        outputUserList = userList;
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
            if (socket.isConnected()) {
                loginWindowController.serverHasStarted = true;
                System.out.println(socket);
                Thread t = new Connection(socket, "Client");
                t.setDaemon(true);
                t.start();
            }

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

    public synchronized static void readMessage(String message) {
        try{
        if (message.equals("loginsuccessful")) {
            loginWindowController.loginAllowed = true;
        }
        else if (message.startsWith("Username")) {
            String[] messagearray = message.split(" ");
            StringBuilder soutputnames = new StringBuilder();
            for(String username : messagearray){
                if(!username.equals("null")) {
                    soutputnames.append(username + "\n");
                }
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    outputUserList.setText(soutputnames.toString());
                }
            });

        }
        else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    //Control if message is (Logon allowed or disallowed)
                    String[] messagearray = message.split("<:::>");
                    LocalTime time = LocalTime.now();
                    DateTimeFormatter timef = DateTimeFormatter.ofPattern("h:mm a");
                    outputfield.appendText(messagearray[0] + " (" + timef.format(time) + "):" + "\n");
                    outputfield.appendText(messagearray[1] + "\n\n");
                }
            });
        }
        } catch (Exception e){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    outputconnectionLabel.setText("disconnected \n restart \n program");
                    outputconnectionLabel.setTextFill(Color.RED);
                    outputConnectionCircle.setFill(Color.RED);
                }
            });
        }

    }


    public String getUsername() {
        return username;
    }
    
}