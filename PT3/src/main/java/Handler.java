import java.io.*;
import java.net.Socket;

public class Handler implements Runnable {
    private final Socket socket;

    public Handler(Socket socket) {
        System.out.println("Handler created.");
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            Message initMsg = new Message(Message.ready);
            out.writeObject(initMsg);

            Message clientMsg;
            boolean exit = false;
            while (!exit && (clientMsg = (Message)in.readObject()) != null) {
                switch (clientMsg.getContent()) {
                    case Message.ready -> {
                        System.out.println("Sent number to generate numbers: " + clientMsg.getNumber());
                        Message serverMsg = new Message(Message.readForMessages);
                        out.writeObject(serverMsg);
                    }
                    case Message.readForMessages -> {
                        System.out.println("Sent generated number: " + clientMsg.getNumber());
                        Message serverMsg = new Message(Message.finished);
                        out.writeObject(serverMsg);
                    }
                    case Message.finished -> {
                        System.out.println("Finished! Closing connection.");
                        exit = true;
                    }
                    default -> {
                        System.err.println("Unknown message type, aborting connection.");
                        Message serverMsg = new Message(Message.error);
                        out.writeObject(serverMsg);
                        exit = true;
                    }
                }
            }
            in.close();
            out.close();
            socket.close();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        } finally {
            System.out.println("Handler finished.");
        }
    }
}