package at.jtalk.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/* This class establishes the connection between a client and a server and vice versa.
The variable can CLIENTORSERVER determines if the respective piece of software works as
a server or a client. The information is stored in a Connection object, which itself store
two object: CLIENTORSERVER and SOCKET, which are both final, since they don't change during
runtime.
 */

public class Connection extends Thread {
    private final Socket SOCKET;
    private final String CLIENTORSERVER;
    private String USERNAME;


    public Connection(Socket SOCKET, String CLIENTORSERVER) {
        this.CLIENTORSERVER = CLIENTORSERVER;
        this.SOCKET = SOCKET;
        Server.addConnection(this);
    }

    public void setUSERNAME(String USERNAME){
        this.USERNAME = USERNAME;
    }

    public String getUSERNAME(){
        return USERNAME;
    }

    public Socket getSOCKET() {
        return SOCKET;
    }

/* In this method the server or the client read the incoming messages.

 */
    @Override
    public void run() {
        while(true)
        {
            try {
                InputStreamReader istreamreader = new InputStreamReader(SOCKET.getInputStream());
                BufferedReader bf = new BufferedReader(istreamreader);
                String get = bf.readLine();
                if(CLIENTORSERVER.equals("Server")){
                    Server.readMessage(get, this);
                }
                else if (CLIENTORSERVER.equals("Client")) {
                    Client.readMessage(get);
                }

            }
            catch (IOException e) {
                System.out.println(e);
                Server.deleteConnection(this);
                
                break;
            }
        }
    }
}
