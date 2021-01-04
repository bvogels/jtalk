package at.jtalk.connection;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Send {
    private final int PORT;
    private Socket socket;
    private static List<Socket> sockets = new ArrayList<>();

    public Server(int PORT) {
        this.PORT = PORT;
    }

    public Socket getSocket(){
        return socket;
    }
    public static void deletesocket(Socket s){
        sockets.remove(s);
    }

    public void listen() {
        try {
            ServerSocket ServerSocket = new ServerSocket(PORT);

        while(true) {
            try {

                Socket socket = ServerSocket.accept();
                System.out.println(socket);
                this.socket = socket;
                sockets.add(socket);
                Thread t = new Listen(this);
                t.start();

            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void checkusernamepassword(Socket s) throws IOException {
        InputStreamReader isreader = new InputStreamReader(s.getInputStream());
        BufferedReader bfreader = new BufferedReader(isreader);
        System.out.println(bfreader.readLine());


    }
    public void readMessage(String message){
        System.out.println(message);
        String[] messagearray = message.split(":::::");
        if(messagearray[0].equals("sendall")){
            for(Socket socket : sockets){
             //   send(socket, message, chatWindowField);
            }
        }
    }

    public void signin(){

    }
}

//    InputStreamReader isreader = new InputStreamReader(s.getInputStream());
//    BufferedReader bfreader = new BufferedReader(isreader);
//            System.out.println(bfreader.readLine());