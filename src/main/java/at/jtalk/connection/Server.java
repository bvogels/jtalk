package at.jtalk.connection;


import at.jtalk.gui.userProfileController;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/* The implemented class Runnable enables multithreading.
The variables of the class declaration are used as follows:
The PORT variable stores the port number of the server. It is final.
The CONNECTIONS list uses the generics T type, storing the connection information of the server. It is then
specified as an ArrayList.
Label is a JavaFx variable, indicating the connection status.
The ArrayList DETAILS stores user information. It is populated on startup, once "Start" is clicked when running as server.
 */

public class Server implements Runnable {
    private final int PORT;
    private static final List<Connection> CONNECTIONS = new ArrayList<>();
    private static final List<String[]> DETAILS = new ArrayList<>();
    private static Label connectionlabel;
    public static List<String> usernames = new ArrayList<>();

    /* This is the constructor, initializing a server object with a port. */

    public Server(int PORT) {
        this.PORT = PORT;
    }

/* A couple of methods to delete or add Connections to the CONNECTIONS list. */
    public static void deleteConnection(Connection c) {
        CONNECTIONS.remove(c);
    }

    public static void addConnection(Connection c) {
        CONNECTIONS.add(c);
    }

    public static void setConnectionLabel(Label connectionlabel){
        Server.connectionlabel = connectionlabel;
    }

/* The following method read the incoming messages. It is called by the Connection.java class. It takes a
string as input in the format command:::::data:data:data:. In this method, the string is split at the five colons.
The resulting array messageArray then consists of two fields. If the first field is sendall, it is a message
that is sent to all clients in the connections lists, through which the case switch iterates.
If the field is Sign In, a unknown user is requesting login credentials. If the field is login, a known users
attempts to log in, and a method to verify his or her credentials is run.

 */

    public static void readMessage(String message, Connection connection) {

        String[] messageArray = message.split(":::::");
        switch (messageArray[0]) {
            case "sendall":
                for (Connection c : CONNECTIONS) {
                    Send.send(c.getSOCKET(), messageArray[1]);
                }
                break;
            case "Sign In":
                signIn(messageArray[1]);

                break;
            case "login":
                if (checkIfUserExists(messageArray[1])) {
                    Send.send(connection.getSOCKET(), "loginsuccessful");
                    StringBuilder users = new StringBuilder();
                    users.append("ALLUSERS ");
                    for(String username : usernames){
                        users.append(username);
                        users.append(" ");
                    }
                    Send.send(connection.getSOCKET(), users.toString());

                } else {
                    Send.send(connection.getSOCKET(), "loginfailed");
                    Server.deleteConnection(connection);
                };
                break;
        }
    }

/* The data of the file users.txt is read into an ArrayList, where each element of the ArrayList is a list with three fields, containing name
password, and ip address. Thus, the validity of the credentials are checked not on the file directly but in the ArrayList.
The method is called from the LoginWindow class as soon as the server starts and shall speed up the login process (if there are millions of users).
 */

    public static void populateUserDataList() {
        try {
            FileInputStream userData = new FileInputStream("users.txt");
            Scanner data = new Scanner(userData);
            while (data.hasNextLine()) {
                String[] singleDetail = data.nextLine().split(":");
                DETAILS.add(singleDetail);
            }
            data.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/*  This method iterates over the list of user details, which is static in the server class. It sifts through the whole array until it finds a
pair of username/password that fits the details entered by the user.
For future improvement: user/password should be a key-value-map. In its current state, nobody stops a user from entering the same values multiple
time on sign up.
 */

    public static boolean checkIfUserExists(String credentials) {
        String[] details = credentials.split(":");
        String user = details[0];
        String password = details[1];
        for (String[] users : DETAILS) {
            if (users[0].toLowerCase().equals(user.toLowerCase()) && users[1].equals(password)) {
                usernames.add(user);
                return true;

            }
        }
        return false;
    }



/* The signIn method lets a new user subscribe to JTalk. It evaluates only the second field of the string
received in the readMessage method. The data entered by the prospective user is separated by single colons and
assigned to the appropriate variables. The routine looks for a file users.txt and creates it, if necessary. The
user information is stored in there in the same format.

 */

    public static void signIn(String message) {

        String[] contents = message.split(":");
        String user = contents[0];
        String password = contents[1];
        String ipaddress = contents[2];
//        Client client = new Client(user, password);
        boolean userexists = false;
        for (String[] users : DETAILS) {
            if (users[0].equals(user)) {
                userexists = true;

            }
        }
        if (!userexists){
            try {

                File userFile = new File("users.txt");
                if (userFile.createNewFile()) {
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
        else if(userexists) {
            userProfileController.usernameTaken.setVisible(true);
        }

    }

/* A new ServerSocket object is created with the Port passed as an attribute. A thread t is also created and
a Connection object with a socket is passed to it, if it is a Server.
 */

    @Override
    public void run() {
        try {
            ServerSocket ServerSocket = new ServerSocket(PORT);
            //serverHasStarted = true;
            //queue for befehle, falls thread gerade nicht an der reihe ist
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    connectionlabel.setVisible(true);
                    connectionlabel.setText("Server started");
                    connectionlabel.setTextFill(Color.GREEN);

                }
            });
            while (true) {
                try {
                    Socket socket = ServerSocket.accept();
                    Thread t = new Connection(socket, "Server");
                    t.start();
/*
*/

                } catch (Exception e) {
                    System.out.println(e);
                    break;
                }
            }
        } catch (Exception e) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    connectionlabel.setVisible(true);
                    connectionlabel.setText("Server can't start");
                    connectionlabel.setTextFill(Color.RED);
                }
            });
        }
    }
}
