import java.awt.TrayIcon.MessageType;
import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L; // Προστίθεται για να αποφευχθούν πιθανά προβλήματα σε αναβαθμίσεις του κώδικα

    private MessageType type;
    private Object data;

    public Message(MessageType type, Object data) {
        this.type = type;
        this.data = data;
    }

    public MessageType getType() {
        return type;
    }

    public Object getData() {
        return data;
    }
}
