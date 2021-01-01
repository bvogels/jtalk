package at.jtalk.connection;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private int port;
    private String ipaddress;

    public Client () {
        this.port = port;
        this.ipaddress = ipaddress;
    }

    public void connectServer() {

        try {
            Socket s = new Socket(ipaddress, port);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
//            OutputStreamWriter oswriter = new OutputStreamWriter(s.getOutputStream());
//            PrintWriter pwriter = new PrintWriter(oswriter);
//            pwriter.println("Guten Tach!");
//            pwriter.flush();