package at.jtalk.connection;

import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Send {
    private String username;
    private String password;
    private Socket socket;
    private static TextArea outputfield;

    public Client(String username, String password) {
        this.username = username;
        this.password = password;

    }

    public static void setOutputField(TextArea chatWindowField) {
        outputfield = chatWindowField;
    }

    public Socket getSocket(){
        return socket;
    }

    /*
    public static void setOutputfield(TextArea outputfield){
        this.outputfield = outputfield;
    }
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

    public void Login() throws IOException {
            OutputStreamWriter oswriter = new OutputStreamWriter(socket.getOutputStream());
            PrintWriter pwriter = new PrintWriter(oswriter);
            pwriter.println("login:::::" + username + ":"  + password);
            pwriter.flush();

        //return 1;
    }

    public static void readMessage(String message) {
        //Controll if message is (Logon allowed or disallowed)
        String[] messagearray = message.split("<:::>");
        outputfield.appendText(messagearray[0] + ":" + "\n");
        outputfield.appendText(messagearray[1] +"\n\n");

    }


    public String getUsername() {
        return username;
    }
}