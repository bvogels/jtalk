package at.jtalk.connection;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Listen {
    private String username;
    private String password;
    private Socket socket;


    public Client(String username, String password) {
        this.username = username;
        this.password = password;

    }

    public void connectServer(String ipaddress, int port) {
        try {
            socket = new Socket(ipaddress, port);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Logon() throws IOException {
            OutputStreamWriter oswriter = new OutputStreamWriter(socket.getOutputStream());
            PrintWriter pwriter = new PrintWriter(oswriter);
            pwriter.println(username + " "  + password);
            pwriter.flush();

        //return 1;
    }

    public void listen(){

    }


}
//            OutputStreamWriter oswriter = new OutputStreamWriter(s.getOutputStream());
//            PrintWriter pwriter = new PrintWriter(oswriter);
//            pwriter.println("Guten Tach!");
//            pwriter.flush();