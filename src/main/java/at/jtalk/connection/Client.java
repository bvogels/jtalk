package at.jtalk.connection;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket socket;


    public Client(Socket s) {
        this.socket = socket;
    }

    public static Socket connectServer(String ipaddress, int port) {

        try {
            Socket s = new Socket(ipaddress, port);
            return s;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    public void Logon(String Username, String Password) throws IOException {
            OutputStreamWriter oswriter = new OutputStreamWriter(socket.getOutputStream());
            PrintWriter pwriter = new PrintWriter(oswriter);
            pwriter.println("Guten Tach!");
            pwriter.flush();

        //return 1;
    }


}
//            OutputStreamWriter oswriter = new OutputStreamWriter(s.getOutputStream());
//            PrintWriter pwriter = new PrintWriter(oswriter);
//            pwriter.println("Guten Tach!");
//            pwriter.flush();