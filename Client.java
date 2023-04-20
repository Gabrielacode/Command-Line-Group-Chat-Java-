// The Client class is where the user send and receives messages.
// The Client class has two different threads; one for sending messages while one for receiving messages
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread {
    Socket socket ;
    BufferedReader br;
    BufferedReader bre;
    BufferedWriter bw;
    Thread  read;
    Client( int port){
        try {
            socket = new Socket("localhost", port);
            br = new BufferedReader(new InputStreamReader(System.in));
            bre = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println("Type in your username");
            String username = br.readLine();
            bw.write(username);
            bw.newLine();
            bw.flush();
              read = new Thread(() -> {
                  while (socket.isConnected()){
                try {
                    readMessage();
                } catch (IOException e) {
                   e.printStackTrace();
                }
                  }
            });
        }catch ( IOException e){
            System.out.println("This error is from the new Socket in Client");
            e.printStackTrace();
        }

    }
    public void writeMessage(String mess){
        try {
            bw.write(mess);
            bw.newLine();
            bw.flush();
        }catch (IOException e){

        }

    }
    public void readMessage()throws IOException{

        System.out.println( bre.readLine());

    }

    @Override
    public void run() {
        while (true){
            try {
                String message = br.readLine();
                writeMessage(message);
            }catch (IOException e){
                e.printStackTrace();
            }


        }
    }
    public void closeEverything(Client handler){
        try {
            if (handler.socket.isClosed()) {
                if (handler.br != null) {
                    handler.br.close();
                }
                if (handler.bw != null) {
                    handler.bw.close();
                }
                if (handler.bre != null) {
                    handler.bre.close();
                }
                if (handler.socket != null) {
                    handler.socket.close();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client me = new Client(4000);
        me.read.start();
        me.start();


    }
}
