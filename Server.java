// The Server class is in charge of connecting each client that connects to this particular port 4000
// The Server class makes use of multithreading to carry out both the creation of the ClientHandler class and the acceptanc.e of Clients that want to connect to the network
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
        ServerSocket serversocks ;
        Socket usersocks;
        int port;
        // The Server constructor
        // A ServerSocket is created to create the connection between the server and the client.

        Server(int port){
            try {
                    this.port = port;
                    serversocks = new ServerSocket(port);
                }catch(IOException e){
                    System.out.println("This error is from the Server constructor");
                    e.printStackTrace();
                }
        }
        // The main thread of the class is in charge of the Server constructor while the class thread is in charge of accepting connections,
       // In the run method the ClientHandler thread is started when the ServerSocket accepts a new connection.
    public void run () {

        try {
            while (true){ usersocks = serversocks.accept();
            System.out.println("A new user has joined");
            if (usersocks.isConnected()){
            Thread cc = new ClientHandler(usersocks);
                cc.start();
            }
        }

        }catch (IOException e){
            System.out.println("This error is from the run method in Server");
            e.printStackTrace();
        }

    }

    // The main method starts the Server thread
    public static void main(String[] args) {
        Server sert = new Server(4000);

        sert.start();
            }

}
