import java.io.*;
import java.net.*;

public class testClient {
    public static void main(String[] args) throws IOException {
        Socket clientSocket = new Socket("localhost", 5000);

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        System.out.println("Enter height: ");
        double height = Double.parseDouble(in.readLine());
        System.out.println("Enter radius: ");
        double radius = Double.parseDouble(in.readLine());

        out.println(height);
        out.println(radius);

        BufferedReader serverResponse = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        System.out.println("Result from server:");
        System.out.println(serverResponse.readLine());

        in.close();
        out.close();
        serverResponse.close();
        clientSocket.close();
    }
}
