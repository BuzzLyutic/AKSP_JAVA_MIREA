import java.io.*;
import java.net.*;

public class BroadcastClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    private Socket socket;
    private BufferedReader consoleIn;
    private PrintWriter out;

    public static void main(String[] args) {
        new BroadcastClient().startClient();
    }

    public void startClient() {
        try {
            // Connect to the server
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            System.out.println("Connected to the server.");

            // Set up I/O streams
            consoleIn = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Start a thread to listen for messages from the server
            new ServerListener(socketIn).start();

            // Send user input to the server
            String input;
            while ((input = consoleIn.readLine()) != null) {
                out.println(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) { }
        }
    }

    // Thread to listen for messages from the server
    private class ServerListener extends Thread {
        private BufferedReader in;

        public ServerListener(BufferedReader in) {
            this.in = in;
        }

        public void run() {
            try {
                // Read messages from the server and print them
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("\nBroadcast message:\n" + message);
                    System.out.print("> "); // Prompt for user input
                }
            } catch (IOException e) {
                System.out.println("Disconnected from server.");
            }
        }
    }
}
