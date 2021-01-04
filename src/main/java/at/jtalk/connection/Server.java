package at.jtalk.connection;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Send {
    private final int PORT;
    private Socket socket;
    private static List<Socket> sockets = new ArrayList<>() {
    };

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

        String[] messagearray = message.split(":::::");
        if(messagearray[0].equals("sendall")){
            for(Socket socket : sockets){
                send(socket, message);
            }
        }else if(messagearray[0].equals("Sign In")){
            signin(messagearray[1]);
        }else if (messagearray[0].equals("logon")){
                //look in users.txt
                send(socket, "LOGONALLOWED");
        }
    }

    public void signin(String nachricht){

        String[] inhalt = nachricht.split(":");
        String user = inhalt[0];
        String password = inhalt[1];
        String ipaddress = inhalt[2];

        try {

            File users = new File("users.txt");
            if (users.createNewFile()) {
                System.out.println("Created new file of user names with name users.txt");
            } else {
                System.out.println("File already exists. Appending.");
            }
        } catch (IOException error) {
            System.out.println("Bad luck. File could not be created.");
            error.printStackTrace();
        }
        try {
            FileWriter saveUserDetails = new FileWriter("users.txt", true);
            saveUserDetails.write(user + ":" + password + ":" + ipaddress + "\n");
            saveUserDetails.close();
        } catch (IOException error) {
            System.out.println("Bad luck. Data could not be written.");
            error.printStackTrace();
        }
    }
}

//    InputStreamReader isreader = new InputStreamReader(s.getInputStream());
//    BufferedReader bfreader = new BufferedReader(isreader);
//            System.out.println(bfreader.readLine());