package at.jtalk.connection;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
/*
    listen()
        if(in nachricht is signin::::::)
            int returnvalue = checkusernamepassword(nachricht)
            schicke zurück(returvale)




    checkusernamepassword()
        for file1
            if username == use

    writetexttouser()

    signin()
*/

}

//    InputStreamReader isreader = new InputStreamReader(s.getInputStream());
//    BufferedReader bfreader = new BufferedReader(isreader);
//            System.out.println(bfreader.readLine());