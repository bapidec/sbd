package dialogs;

import javax.swing.*;
import java.awt.*;

public class SupplierDialog extends JDialog{
    JTextField name = new JTextField();
    JTextField vehicleNr = new JTextField();
    JTextField startDate = new JTextField();
    JTextField endDate = new JTextField();
    JButton confirmButton = new JButton("Confirm");
    JButton cancelButton = new JButton("Cancel");

    public SupplierDialog(JFrame frame, String title){
        super(frame, title);
        super.setVisible(true);
        super.setModal(true);
        super.setAlwaysOnTop(true);
        super.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        super.setPreferredSize(new Dimension(400,300));
        super.setLocationRelativeTo(null);
        super.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(0, 2));
        JPanel buttonsPanel = new JPanel();

        formPanel.add(new JLabel("Name: "));
        formPanel.add(name);

        formPanel.add(new JLabel("Vehicle number: "));
        formPanel.add(vehicleNr);

        formPanel.add(new JLabel("Start date: "));
        formPanel.add(startDate);

        formPanel.add(new JLabel("End date: "));
        formPanel.add(endDate);

        buttonsPanel.add(confirmButton);
        buttonsPanel.add(cancelButton);
        this.add(formPanel);
        this.add(buttonsPanel, BorderLayout.SOUTH);
        this.pack();


    }
}



