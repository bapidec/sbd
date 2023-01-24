package view;

import javax.swing.*;
import java.awt.*;

public class OrderView extends JPanel implements EntityView {
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

    public void showOrderDetails(String clientId, String address, String startDate, String endDate, String orderId) {
        startDateField.setText(clientId);
        endDateField.setText(address);
        addressField.setText(startDate);
        clientNameField.setText(endDate);
        clientNameField.setText(orderId);
    }

    @Override
    public void clear() {
        startDateField.setText("");
        endDateField.setText("");
        addressField.setText("");
        clientNameField.setText("");
        clientNameField.setText("");
    }
}

