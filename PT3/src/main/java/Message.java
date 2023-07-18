import java.io.Serializable;

public class Message implements Serializable {
    final static public String ready = "ready";
    final static public String readForMessages = "ready for message";
    final static public String finished = "action ended";
    final static public String error = "error";

    private int number;
    private final String content;

    public Message(String content) {
        this.content = content;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public String getContent() {
        return content;
    }
}