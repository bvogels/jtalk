package at.jtalk.connection;


import javafx.application.Platform;
import javafx.scene.control.Label;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/* The implemented class Runnable enables multithreading.
The variables of the class declaration are used as follows:
The PORT variable stores the port number of the server. It is final.
The CONNECTIONS list uses the generics T type, storing the connection information of the server. It is then
specified as an ArrayList.
Label is a JavaFx variable, indicating the connection status.
 */

public class Server implements Runnable {
    private final int PORT;
    private static final List<Connection> CONNECTIONS = new ArrayList<>();
    private static Label connectionlabel;

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
    public void checkusernamepassword(Socket s) throws IOException {

    }

/* The following method read the incoming messages. It is called by the Connection.java class. It takes a
string as input in the format command:::::data:data:data:. In this method, the string is split at the five colons.
The resulting array messageArray then consists of two fields. If the first field is sendall, it is a message
that is sent to all clients in the connections lists, through which the case switch iterates.
If the field is Sign In, a unknown user is requesting login credentials. If the field is login, a known users
attempts to log in, and a method to verify his or her credentials is run.

 */

    public static void readMessage(String message) {

        String[] messageArray = message.split(":::::");
        switch (messageArray[0]) {
            case "sendall":
                for (Connection c : CONNECTIONS) {
                    Send.send(c.getSocket(), messageArray[1]);
                }
                break;
            case "Sign In":
//                    Connection.getSocket(), messagearray[1]);
//                    signIn(messagearray[1]);
                break;
            case "login":
                //look in users.txt

                break;
        }
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

/* A new ServerSocket object is created with the Port passed as an attribute. A thread t is also created and
a Connection object with a socket is passed to it, if it is a Server.
 */

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
