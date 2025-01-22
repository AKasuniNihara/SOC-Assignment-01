import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket(args[0], Integer.parseInt(args[1]));
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        String message;
        while (true) {
            message = console.readLine();
            output.println(message);
            if (message.equals("terminate")) break;
        }
        socket.close();
    }
}

