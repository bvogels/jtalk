package at.jtalk.connection;

import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Send {
    public static final void send(Socket socket, String message) {
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
