package at.jtalk.connection;

import at.jtalk.gui.chatWindow;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Send {
    private String username;
    private String password;
    private Socket socket;
    private TextArea outputfield;

    public Client(String username, String password) {
        this.username = username;
        this.password = password;

    }

    public Socket getSocket(){
        return socket;
    }

    public void setOutputfield(TextArea outputfield){
        this.outputfield = outputfield;
    }

    public void connectServer(String ipaddress, int port) {
        try {
            socket = new Socket(ipaddress, port);
            System.out.println(socket);
            Thread t = new Listen(this);
            t.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Logon() throws IOException {
            OutputStreamWriter oswriter = new OutputStreamWriter(socket.getOutputStream());
            PrintWriter pwriter = new PrintWriter(oswriter);
            pwriter.println("logon:::::" + username + " "  + password);
            pwriter.flush();

        //return 1;
    }

    public void readMessage(String message) {
        outputfield.appendText(message);
    }





}
//            OutputStreamWriter oswriter = new OutputStreamWriter(s.getOutputStream());
//            PrintWriter pwriter = new PrintWriter(oswriter);
//            pwriter.println("Guten Tach!");
//            pwriter.flush();