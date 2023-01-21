package view;

import javax.swing.*;
import java.awt.*;

public class ClientView extends JPanel implements EntityView {

    JTextField firstNameField = new JTextField();
    JTextField lastNameField = new JTextField();
    JTextField phoneNumberField = new JTextField();
    JTextField emailField = new JTextField();
    JTextField addressField = new JTextField();

    public ClientView() {
        super();
        GridLayout gridLayout = new GridLayout(0, 2);
        gridLayout.setVgap(1);
        super.setLayout(gridLayout);

        firstNameField.setEditable(false);
        lastNameField.setEditable(false);
        phoneNumberField.setEditable(false);
        emailField.setEditable(false);
        addressField.setEditable(false);

        super.add(new JLabel("First name: "));
        super.add(firstNameField);

        super.add(new JLabel("Last name: "));
        super.add(lastNameField);

        super.add(new JLabel("Phone number: "));
        super.add(phoneNumberField);

        super.add(new JLabel("Email: "));
        super.add(emailField);

        super.add(new JLabel("Address: "));
        super.add(addressField);

    }

    public void showClientsDetails(String firstName, String lastName, String phoneNumber, String email, String address) {
        firstNameField.setText(firstName);
        lastNameField.setText(lastName);
        phoneNumberField.setText(phoneNumber);
        emailField.setText(email);
        addressField.setText(address);
    }
    public void clear() {
        firstNameField.setText("");
        lastNameField.setText("");
        phoneNumberField.setText("");
        emailField.setText("");
        addressField.setText("");
    }
}
