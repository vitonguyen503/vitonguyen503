import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    private Socket socket = null;
    private DataInputStream dataInputStream = null;
    private DataOutputStream dataOutputStream = null;
    public Client(int port, String IPAddress){
        try{
            socket = new Socket(IPAddress, port);
            System.out.println("Conected!");

            dataInputStream = new DataInputStream(System.in);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (UnknownHostException ex){
            ex.printStackTrace();
        } catch (IOException ex){
            ex.printStackTrace();
        }

        String tmp = "";
        while(!tmp.equals("over")){
            try{
                tmp = dataInputStream.readLine();
                dataOutputStream.writeUTF(tmp);
            } catch (IOException exception){
                exception.printStackTrace();
            }
        }
        System.out.println("Close connection!");
        try{
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client(5000, "127.0.0.1" );
    }
}
