package view;

import javax.swing.*;
import java.awt.*;

public class SupplierView extends JPanel {
    JTextField nameField = new JTextField();
    JTextField vehicleNumberField = new JTextField();
    JTextField startDateField = new JTextField();
    JTextField endDateField = new JTextField();

    public SupplierView() {
        super();
        GridLayout gridLayout = new GridLayout(0, 2);
        gridLayout.setVgap(1);
        super.setLayout(gridLayout);

        nameField.setEditable(false);
        vehicleNumberField.setEditable(false);
        startDateField.setEditable(false);
        endDateField.setEditable(false);

        super.add(new JLabel("Name: "));
        super.add(nameField);

        super.add(new JLabel("Vehicle number: "));
        super.add(vehicleNumberField);

        super.add(new JLabel("Start date: "));
        super.add(startDateField);

        super.add(new JLabel("End date: "));
        super.add(endDateField);
    }

    public void showClientsDetails(String name, String vehicleNumber, String startDate, String endDate) {
        nameField.setText(name);
        vehicleNumberField.setText(vehicleNumber);
        startDateField.setText(startDate);
        endDateField.setText(endDate);
    }

}
