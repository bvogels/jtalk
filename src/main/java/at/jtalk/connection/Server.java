package at.jtalk.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final int PORT;

    public Server(int PORT) {
        this.PORT = PORT;
    }

    public void createServer() {
        try {
            ServerSocket ServerSocket = new ServerSocket(PORT);
            Socket s = ServerSocket.accept();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//    InputStreamReader isreader = new InputStreamReader(s.getInputStream());
//    BufferedReader bfreader = new BufferedReader(isreader);
//            System.out.println(bfreader.readLine());