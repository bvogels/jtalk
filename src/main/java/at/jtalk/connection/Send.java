package at.jtalk.connection;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/*Method for sending messages on socket and gets written to the output stream and printed out on gui*/
public class Send {
    public static void send(Socket socket, String message) {
        try {
            OutputStreamWriter oswriter = new OutputStreamWriter(socket.getOutputStream());
            PrintWriter pwriter = new PrintWriter(oswriter);
            pwriter.println(message);
            pwriter.flush();
        } catch (IOException e){
            System.out.println(e);
        }
    }
}
