import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket(args[0], Integer.parseInt(args[1]));
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

        output.println(args[2]);

        if ("PUBLISHER".equals(args[2])) {
            String message;
            while (true) {
                message = console.readLine();
                output.println(message);
                if (message.equals("terminate")) break;
            }
        } else if ("SUBSCRIBER".equals(args[2])) {
            String message;
            while ((message = input.readLine()) != null) {
                System.out.println("Received: " + message);
            }
        }

        socket.close();
    }
}

