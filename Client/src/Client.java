import java.io.*;
import java.net.*;

public class Client {
    // initialize socket and input output streams
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream out = null;

    // constructor to put ip address and port
    public Client(String address, int port)
    {
        // establish a connection
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");

            // takes input from terminal
            input = new DataInputStream(System.in);

            // sends output to the socket
            out = new DataOutputStream(
                    socket.getOutputStream());
        }
        catch (UnknownHostException u) {
            System.out.println(u);
            return;
        }
        catch (IOException i) {
            System.out.println(i);
            return;
        }

        // string to read message from input
        String line = "";

        try {
            // Send START message
            out.writeUTF("START");
            // Receive WAITING message
            System.out.println("Server: " + input.readUTF());

            // Send REQUEST_INSERT message
            out.writeUTF("REQUEST_INSERT");

            // Create and send contact
            PhoneDirectory.Contact contact = new PhoneDirectory.Contact("Kostas", "Papadopoulos", "6987456123", "Aristotelous 15", "Engineer");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(contact);
            byte[] contactBytes = baos.toByteArray();
            out.writeInt(contactBytes.length);
            out.write(contactBytes);

            // Receive OK message
            System.out.println("Server: " + input.readUTF());

            // Send END message
            out.writeUTF("END");
        } catch (IOException i) {
            System.out.println(i);
        }

        // close the connection
        try {
            input.close();
            out.close();
            socket.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String args[])
    {
        Client client = new Client("127.0.0.1", 2324);
    }
}
