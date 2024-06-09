/*
 * Giannakopoulos Paraskevas
 * 321/2020040
 */

import javax.swing.*;
import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ClientGui extends JFrame {

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private JTextField searchField;
    private JButton searchButton;
    private JTextField insertFirstNameField;
    private JTextField insertLastNameField;
    private JTextField insertPhoneNumberField;
    private JTextField insertAddressField;
    private JTextField insertProfessionField;
    private JButton insertButton;

    public ClientGui(ObjectInputStream inputStream, ObjectOutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        createGUI();
    }

    //Sunartisi gia to sxediasmo tou grafikou
    private void createGUI() {
        setTitle("Phone Directory Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Search Last Name:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        searchField = new JTextField(20);
        mainPanel.add(searchField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchContact();
            }
        });
        mainPanel.add(searchButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Insert First Name:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        insertFirstNameField = new JTextField(20);
        mainPanel.add(insertFirstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Insert Last Name:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        insertLastNameField = new JTextField(20);
        mainPanel.add(insertLastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(new JLabel("Insert Phone Number:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        insertPhoneNumberField = new JTextField(20);
        mainPanel.add(insertPhoneNumberField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(new JLabel("Insert Address:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        insertAddressField = new JTextField(20);
        mainPanel.add(insertAddressField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(new JLabel("Insert Profession:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        insertProfessionField = new JTextField(20);
        mainPanel.add(insertProfessionField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        insertButton = new JButton("Insert");
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertContact();
            }
        });
        mainPanel.add(insertButton, gbc);

        add(mainPanel);
        setVisible(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                closeConnection(); //Kalw ti sunartisi na kleisei ti sundesi
            }
        });
    }

    //Sunartisi gia anazitisi eggrafis
    private void searchContact() {
        try {
            String lastName = searchField.getText();
            Message searchMessage = new Message(MessageType.SEARCH, lastName);
            outputStream.writeObject(searchMessage);
            outputStream.flush();

            Message response = (Message) inputStream.readObject();
            if (response.getType() == MessageType.RECORD) {
                displayResult(response.getContact().toString());
            } else {
                displayResult("No record found.");
            }

            searchField.setText("");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    //Sunartisi gia eisagwgi neas eggrafis
    private void insertContact() {
        try {
            String firstName = insertFirstNameField.getText();
            String lastName = insertLastNameField.getText();
            String phoneNumber = insertPhoneNumberField.getText();
            String address = insertAddressField.getText();
            String profession = insertProfessionField.getText();

            Contact contact = new Contact(firstName, lastName, phoneNumber, address, profession);
            Message insertMessage = new Message(MessageType.INSERT, contact);
            outputStream.writeObject(insertMessage);
            outputStream.flush();

            Message response = (Message) inputStream.readObject();
            if (response.getType() == MessageType.OK) {
                displayResult("Contact inserted successfully.");
            } else {
                displayResult("Error inserting contact.");
            }

            insertFirstNameField.setText("");
            insertLastNameField.setText("");
            insertPhoneNumberField.setText("");
            insertAddressField.setText("");
            insertProfessionField.setText("");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    //Sunartisi gia kleisimo tis sundesis
    private void closeConnection() {
        try {
            Message endMessage = new Message(MessageType.END, null);
            outputStream.writeObject(endMessage);
            outputStream.flush();
            System.out.println("Connection Closing...");
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Sunartisi gia emfanisi apotelesmatwn
    private void displayResult(String response) {
        JFrame resultFrame = new JFrame("Search Result");
        resultFrame.setSize(300, 100);
        resultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(1, 1));
        JTextArea textArea = new JTextArea();
        textArea.setText(response);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea); // Prosthiki JScrollPane gia to keimeno
        panel.add(scrollPane);

        resultFrame.add(panel);
        resultFrame.setLocationRelativeTo(null);
        resultFrame.setVisible(true);
    }

}
