package at.jtalk.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Listen extends Thread {
    private Socket clientsocket;
    private final Object clientorserver;

    public Listen(Object clientorserver) {
        this.clientorserver = clientorserver;
        if(clientorserver instanceof Server){
            clientsocket = ((Server) clientorserver).getSocket();
        }
        else if (clientorserver instanceof Client){
            clientsocket = ((Client) clientorserver).getSocket();
        }

    }

    @Override
    public void run() {

        while(true)
        {
            try {
                InputStreamReader istreamreader = new InputStreamReader(clientsocket.getInputStream());
                BufferedReader bf = new BufferedReader(istreamreader);
                String get = bf.readLine();
                if(clientorserver instanceof Server){
                    ((Server) clientorserver).readMessage(get);
                }
                else if (clientorserver instanceof Client) {
                    ((Client) clientorserver).readMessage(get);
                }

            }
            catch (IOException e) {
                Server.deletesocket(clientsocket);
                break;
            }
        }
    }
}
