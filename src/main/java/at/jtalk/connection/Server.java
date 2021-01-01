package at.jtalk.connection;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server extends Listen {
    private final int PORT;
    private static List<Socket> sockets;

    public Server(int PORT) {
        this.PORT = PORT;
    }

    public void addSocketToList(Socket s){
        sockets.add(s);
    }

    public void listen() {
        try {
            ServerSocket ServerSocket = new ServerSocket(PORT);
            Socket s = ServerSocket.accept();
            sockets.add(s);
            checkusernamepassword(s);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void checkusernamepassword(Socket s) throws IOException {
        InputStreamReader isreader = new InputStreamReader(s.getInputStream());
        BufferedReader bfreader = new BufferedReader(isreader);
        System.out.println(bfreader.readLine());

    }
    public void send(){

    }
    public void signin(){

    }
}

//    InputStreamReader isreader = new InputStreamReader(s.getInputStream());
//    BufferedReader bfreader = new BufferedReader(isreader);
//            System.out.println(bfreader.readLine());