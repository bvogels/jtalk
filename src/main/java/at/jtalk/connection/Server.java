package at.jtalk.connection;


import at.jtalk.gui.LoginWindow;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable{
    private final int PORT;
    private static final List<Connection> connections = new ArrayList<>();
    private static Label connectionlabel;

    public Server(int PORT) {
        this.PORT = PORT;
    }

    public static void deleteconnection(Connection c) {
        connections.remove(c);
    }

    public static void addconnection(Connection c) {
        connections.add(c);
    }

    public static void setConnectionlabel(Label connectionlabel){
        Server.connectionlabel = connectionlabel;
    }
    public void checkusernamepassword(Socket s) throws IOException {

    }

    public static void readMessage(String message) {

        String[] messagearray = message.split(":::::");
        if (messagearray[0].equals("sendall")) {
            for (Connection c : connections) {
                Send.send(c.getSocket(), messagearray[1]);
            }
        } else if (messagearray[0].equals("Sign In")) {
            signin(messagearray[1]);
        } else if (messagearray[0].equals("login")) {
            //look in users.txt

        }
    }

    public static void signin(String nachricht) {

        String[] inhalt = nachricht.split(":");
        String user = inhalt[0];
        String password = inhalt[1];
        String ipaddress = inhalt[2];

        try {

            File users = new File("users.txt");
            if (users.createNewFile()) {
                System.out.println("Created new file of user names with name users.txt");
            } else {
                System.out.println("File already exists. Appending.");
            }
        } catch (IOException error) {
            System.out.println("Bad luck. File could not be created.");
            error.printStackTrace();
        }
        try {
            FileWriter saveUserDetails = new FileWriter("users.txt", true);
            saveUserDetails.write(user + ":" + password + ":" + ipaddress + "\n");
            saveUserDetails.close();
        } catch (IOException error) {
            System.out.println("Bad luck. Data could not be written.");
            error.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            ServerSocket ServerSocket = new ServerSocket(PORT);

            while (true) {
                try {
                    Socket socket = ServerSocket.accept();
                    Thread t = new Connection(socket, "Server");
                    t.start();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            connectionlabel.setText("shit");
                            connectionlabel.setVisible(true);
                            connectionlabel.setText("Hallo");
                        }
                    });


                } catch (Exception e) {
                    System.out.println(e);
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            connectionlabel.setVisible(true);
        }
    }
}
