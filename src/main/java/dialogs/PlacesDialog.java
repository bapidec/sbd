package dialogs;


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PlacesDialog extends JDialog {

    JTextField locationField = new JTextField();
    JTextField productLimitField = new JTextField();
    JTextField employeeLimitField = new JTextField();
    JTextField maintenanceCostField = new JTextField();
    JPanel typeField = new JPanel();
    JButton confirmButton = new JButton("Confirm");
    JButton cancelButton = new JButton("Cancel");

    public PlacesDialog(JFrame frame, JButton button, String title){
        super(frame, title);
        super.setVisible(true);
        super.setModal(true);
        super.setAlwaysOnTop(true);
        super.setModalityType(ModalityType.APPLICATION_MODAL);
        super.setPreferredSize(new Dimension(400,300));
        super.setLocationRelativeTo(null);
        super.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(0, 2));
        JPanel buttonsPanel = new JPanel();

        formPanel.add(new JLabel("Location adress: "));
        formPanel.add(locationField);

        formPanel.add(new JLabel("Product limit: "));
        formPanel.add(productLimitField);

        formPanel.add(new JLabel("Employee limit in workplace: "));
        formPanel.add(employeeLimitField);

        formPanel.add(new JLabel("Monthly maintenance cost: "));
        formPanel.add(maintenanceCostField);

        formPanel.add(new JLabel("Building role: "));
        typeField.add(new JRadioButton("Warehouse"));
        typeField.add(new JRadioButton("Shop"));
        formPanel.add(typeField);


        buttonsPanel.add(confirmButton);
        buttonsPanel.add(cancelButton);
        this.add(formPanel);
        this.add(buttonsPanel, BorderLayout.SOUTH);
        this.pack();

        super.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                button.setEnabled(true);
                PlacesDialog.super.dispose();
            }
        });
    }
}
