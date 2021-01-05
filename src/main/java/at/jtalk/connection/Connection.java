package at.jtalk.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Connection extends Thread {
    private Socket socket;
    private final String clientorserver;

    public Connection(Socket socket, String clientorserver) {
        this.clientorserver = clientorserver;
        this.socket = socket;
        Server.addconnection(this);

    }

    public Socket getSocket(){
        return socket;
    }

    @Override
    public void run() {
        while(true)
        {
            try {
                InputStreamReader istreamreader = new InputStreamReader(socket.getInputStream());
                BufferedReader bf = new BufferedReader(istreamreader);
                String get = bf.readLine();
                if(clientorserver.equals("Server")){
                    Server.readMessage(get);
                }
                else if (clientorserver.equals("Client")) {
                    Client.readMessage(get);
                }

            }
            catch (IOException e) {
                Server.deleteconnection(this);
                break;
            }
        }
    }
}
