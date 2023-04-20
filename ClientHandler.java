// The ClientHandler class is in charge of handling the transfer of messages to and from the  client
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class ClientHandler extends Thread {
    Socket star ;
    String userName;
    BufferedWriter bw ;
    BufferedReader ff ;
    static ArrayList<ClientHandler> stack = new ArrayList<>();
    ClientHandler(Socket st){
        try {
            star = st;
            bw = new BufferedWriter(new OutputStreamWriter(star.getOutputStream()));
            ff = new BufferedReader(new InputStreamReader(star.getInputStream()));
            stack.add(this);


        }catch(IOException e){
           remove();
        }
    }
  public void broadcastMessage(String message){
        try {
            Iterator<ClientHandler> iter = stack.iterator();
            while (iter.hasNext()) {
                ClientHandler cc = iter.next();
                if(cc.userName!= null && !cc.userName.equalsIgnoreCase(this.userName)){
                cc.bw.write("["+ this.userName + "] " +message);
                cc.bw.newLine();
                cc.bw.flush();
            }}

        }catch (IOException e){
            remove();
        }
  }
  public String readMessage(){

        try {
            String message = ff.readLine();
            if (message.equalsIgnoreCase("List")){
                for (ClientHandler ad :stack) {
                    bw.write(ad.userName);
                    bw.newLine();
                    bw.flush();
                }
                return "User asked for the members of this group";
            }else {
            return message;}
        }catch (IOException e){
            remove();
        }
      return " ";
  }
    @Override
    public void run() {
        try {
            userName = ff.readLine();
        }catch (IOException e){
           remove();
        }

        while (true){
     String mess = readMessage();
        broadcastMessage(mess);}
    }


public void closeEverything(ClientHandler handler){
        try {
            if (handler.star.isClosed()) {
                if (handler.ff != null) {
                    handler.ff.close();
                }
                if (handler.bw != null) {
                    handler.bw.close();
                }
                if (handler.star != null) {
                    handler.star.close();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
}
public void remove(){
        if (!star.isConnected()){
            stack.remove(this);
            closeEverything(this);
        }
}
}