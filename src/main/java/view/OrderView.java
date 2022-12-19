package view;

import javax.swing.*;
import java.awt.*;

public class OrderView extends JPanel {
    JTextField startDateField = new JTextField();
    JTextField endDateField = new JTextField();
    JTextField addressField = new JTextField();
    JTextField clientNameField = new JTextField();
    JTextField clientMailField = new JTextField();
    JTextField clientPhoneField = new JTextField();
    JTextArea productsArea = new JTextArea();

    public OrderView() {
        super();
        GridLayout gridLayout = new GridLayout(0, 2);
        gridLayout.setVgap(1);
        super.setLayout(gridLayout);

        startDateField.setEditable(false);
        endDateField.setEditable(false);
        addressField.setEditable(false);
        clientNameField.setEditable(false);
        clientMailField.setEditable(false);
        clientPhoneField.setEditable(false);
        productsArea.setEditable(false);

        super.add(new JLabel("Start date: "));
        super.add(startDateField);

        super.add(new JLabel("End date: "));
        super.add(endDateField);

        super.add(new JLabel("Address: "));
        super.add(addressField);

        super.add(new JLabel("Client name: "));
        super.add(clientNameField);

        super.add(new JLabel("Client email: "));
        super.add(clientMailField);

        super.add(new JLabel("Client phone number: "));
        super.add(clientPhoneField);

        super.add(new JLabel("Ordered products: "));
        super.add(productsArea);
    }

    public void showClientsDetails(String location, String productLimit, String employeeLimit, String maintenanceCost, String type) {
        startDateField.setText(location);
        endDateField.setText(productLimit);
        addressField.setText(employeeLimit);
        clientNameField.setText(maintenanceCost);
        clientNameField.setText(type);
    }

}

