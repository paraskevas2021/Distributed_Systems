import java.io.Serializable;
import java.util.*;

public class PhoneDirectory implements Serializable {
    private TreeMap<String, Contact> contacts;

    public PhoneDirectory() {
        this.contacts = new TreeMap<>();
        addContact(new Contact("Dimitrios", "Papadopoulos", "6987123456", "Panepistimiou 21", "Giatros"));
        addContact(new Contact("Giannis", "Arseniou", "6987123457", "Ploutarxou 21", "Arxitektonas"));
        addContact(new Contact("Orestis", "Dimitriadis", "6901234568", "Ellinwn 45", "Kathigitis"));
        addContact(new Contact("Paraskevas", "Giannakopoulos", "6987989709", "Ploutarxou 1", "Mixanikos"));
        addContact(new Contact("Panagiwtis", "Adamopoulos", "6856234567", "Thrakomakedonwn", "Odigos"));
    }

    public void addContact(Contact contact) {
        contacts.put(contact.getLastName(), contact);
    }

    public Contact searchContact(String lastName) {
        return contacts.get(lastName);
    }

    public TreeMap<String, Contact> getAllContacts() {
        return contacts;
    }

    public static class Contact implements Serializable {
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private String address;
        private String profession;

        public Contact(String firstName, String lastName, String phoneNumber, String address, String profession) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.phoneNumber = phoneNumber;
            this.address = address;
            this.profession = profession;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public String getAddress() {
            return address;
        }

        public String getProfession() {
            return profession;
        }
    }
}
