package view;

import javax.swing.*;
import java.awt.*;

public class ImageView extends JPanel {

    ImageIcon imageIcon = new ImageIcon();
    JLabel imageLabel = new JLabel(imageIcon);

    public ImageView() {
        super();
        super.add(imageLabel);
    }

//    public void showClientsDetails(String location, String productLimit, String employeeLimit, String maintenanceCost, String type) {
//        startDateField.setText(location);
//        endDateField.setText(productLimit);
//        addressField.setText(employeeLimit);
//        clientNameField.setText(maintenanceCost);
//        clientNameField.setText(type);
//    }

}


