package view;

import javax.swing.*;
import java.awt.*;

public class PlaceView extends JPanel implements EntityView {
    JTextField locationField = new JTextField();
    JTextField productLimitField = new JTextField();
    JTextField employeeLimitField = new JTextField();
    JTextField maintenanceCostField = new JTextField();
    JTextField typeField = new JTextField();

    public PlaceView() {
        super();
        GridLayout gridLayout = new GridLayout(0, 2);
        gridLayout.setVgap(1);
        super.setLayout(gridLayout);

        locationField.setEditable(false);
        productLimitField.setEditable(false);
        employeeLimitField.setEditable(false);
        maintenanceCostField.setEditable(false);
        typeField.setEditable(false);

        super.add(new JLabel("Location: "));
        super.add(locationField);

        super.add(new JLabel("Product limit: "));
        super.add(productLimitField);

        super.add(new JLabel("Employee limit: "));
        super.add(employeeLimitField);

        super.add(new JLabel("Supplier id: "));
        super.add(maintenanceCostField);

        super.add(new JLabel("Type: "));
        super.add(typeField);
    }

    public void showPlacesDetails(String location, String productLimit, String employeeLimit, String maintenanceCost, String type) {
        locationField.setText(location);
        productLimitField.setText(productLimit);
        employeeLimitField.setText(employeeLimit);
        maintenanceCostField.setText(maintenanceCost);
        maintenanceCostField.setText(type);
        typeField.setText("Shop");
    }

    @Override
    public void clear() {
        locationField.setText("");
        productLimitField.setText("");
        employeeLimitField.setText("");
        maintenanceCostField.setText("");
        maintenanceCostField.setText("");
        typeField.setText("");
    }
}
