import java.net.*;
import java.io.*;

public class Server {
    //initialize socket and input stream
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;
    private PhoneDirectory phoneDirectory;

    // constructor with port
    public Server(int port)
    {
        phoneDirectory = new PhoneDirectory();

        // starts server and waits for a connection
        try
        {
            server = new ServerSocket(port);
            System.out.println("Server started");

            System.out.println("Waiting for a client ...");

            socket = server.accept();
            System.out.println("Client accepted");

            // takes input from the client socket
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new DataOutputStream(socket.getOutputStream());

            String line = "";

            // reads message from client until "END" is sent
            while (!line.equals("END"))
            {
                try
                {
                    line = in.readUTF();
                    System.out.println("Client: " + line);

                    if (line.equals("START")) {
                        // Send WAITING message
                        out.writeUTF("WAITING");
                    } else if (line.equals("REQUEST_INSERT")) {
                        // Receive contact data
                        int length = in.readInt();
                        byte[] contactBytes = new byte[length];
                        in.readFully(contactBytes);
                        ByteArrayInputStream bais = new ByteArrayInputStream(contactBytes);
                        ObjectInputStream ois = new ObjectInputStream(bais);
                        PhoneDirectory.Contact contact = (PhoneDirectory.Contact) ois.readObject();
                        // Add contact to directory
                        phoneDirectory.addContact(contact);
                        // Send OK message
                        out.writeUTF("OK");
                    }
                }
                catch(IOException | ClassNotFoundException i)
                {
                    System.out.println(i);
                }
            }
            System.out.println("Closing connection");

            // close connection
            socket.close();
            in.close();
            out.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

    public static void main(String args[])
    {
        Server server = new Server(2324);
    }
}
