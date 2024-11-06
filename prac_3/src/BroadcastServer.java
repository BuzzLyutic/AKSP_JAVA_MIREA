import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class BroadcastServer {
    private static final int PORT = 12345;
    private static final int BROADCAST_INTERVAL = 5000; // 5 seconds

    private ServerSocket serverSocket;
    private Set<ClientHandler> clientHandlers = ConcurrentHashMap.newKeySet();
    private List<String> messageQueue = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        new BroadcastServer().startServer();
    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Broadcast server started on port " + PORT);

            new BroadcastThread().start();

            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(socket);
                clientHandlers.add(handler);
                handler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Thread to broadcast messages every 5 seconds
    private class BroadcastThread extends Thread {
        public void run() {
            while (true) {
                try {
                    Thread.sleep(BROADCAST_INTERVAL);

                    if (!messageQueue.isEmpty()) {
                        String broadcastMessage = String.join("\n", messageQueue);
                        messageQueue.clear();

                        for (ClientHandler handler : clientHandlers) {
                            handler.sendMessage(broadcastMessage);
                        }
                        System.out.println("Broadcasted messages to clients.");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Inner class to handle each client
    private class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private String clientName;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void sendMessage(String message) {
            out.println(message);
            out.flush();
        }

        public void run() {
            try {
                // Set up I/O streams
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                out.println("Enter your name:");
                clientName = in.readLine();
                System.out.println(clientName + " has connected.");

                String message;
                while ((message = in.readLine()) != null) {
                    String formattedMessage = clientName + ": " + message;
                    messageQueue.add(formattedMessage);
                    System.out.println("Received from " + clientName + ": " + message);
                }
            } catch (IOException e) {
                System.out.println("Client " + clientName + " disconnected.");
            } finally {
                try {
                    socket.close();
                } catch (IOException e) { }
                clientHandlers.remove(this);
            }
        }
    }
}
