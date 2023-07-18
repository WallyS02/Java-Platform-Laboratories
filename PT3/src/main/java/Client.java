import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class Client {
    public static int generateInteger() {
        Random random = new Random();
        return random.nextInt(512);
    }

    public void startSession() {
        System.out.println("Making connection.");
        try (Socket socket = new Socket(Server.host, Server.port)) {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Scanner scanner = new Scanner(System.in);
            Message serverMsg;
            boolean exit = false;
            int n = 0;
            while(!exit && (serverMsg = (Message)in.readObject()) != null) {
                System.out.println("Server: " + serverMsg.getContent());
                switch (serverMsg.getContent()) {
                    case Message.ready -> {
                        while (true) {
                            try {
                                System.out.println("Enter integer to generate.");
                                n = scanner.nextInt();
                                Message clientMessage = new Message(Message.ready);
                                clientMessage.setNumber(n);
                                out.writeObject(clientMessage);
                                break;
                            } catch (Exception ex) {
                                System.err.println(ex.getMessage());
                                System.err.println("Enter correct integer value.");
                            }
                        }
                    }
                    case Message.readForMessages -> {
                        for (int i = 0; i < n; i++) {
                            Message message = new Message(Message.readForMessages);
                            message.setNumber(generateInteger());
                            out.writeObject(message);
                        }
                    }
                    case Message.finished -> {
                        System.out.println("Ending connection, messages sent correctly.");
                        Message clientMsg = new Message(Message.finished);
                        out.writeObject(clientMsg);
                        exit = true;
                    }
                    default -> {
                        System.out.println("Ending connection, unknown response received.");
                        exit = true;
                    }
                }
            }
            in.close();
            out.close();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.startSession();
    }
}