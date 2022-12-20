package dialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderDialog extends JDialog{
    private JButton confirmButton = new JButton("Confirm");
    private JButton cancelButton = new JButton("Cancel");
    JTextField startDate = new JTextField();
    JTextField endDate = new JTextField();
    JTextField address = new JTextField();
    JComboBox client = new JComboBox<>();
    JTextArea products = new JTextArea("");
    JComboBox productsBox = new JComboBox();
    JButton addProduct = new JButton("Add product");

    JPanel formPanel = new JPanel(new GridLayout(0, 2));
    JPanel productsPanel = new JPanel(new GridLayout(0, 1));
    JPanel buttonsPanel = new JPanel();

    public OrderDialog(JFrame frame, String title) {
        super(frame, title);
        super.setVisible(true);
        super.setModal(true);
        super.setAlwaysOnTop(true);
        super.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        super.setPreferredSize(new Dimension(600,500));
        super.setLocationRelativeTo(null);

        this.setLayout(new BorderLayout());

        formPanel.add(new JLabel("Start date: "));
        formPanel.add(this.startDate);

        formPanel.add(new JLabel("End date: "));
        formPanel.add(this.endDate);

        formPanel.add(new JLabel("Address: "));
        formPanel.add(this.address);

        formPanel.add(new JLabel("Client: "));
        formPanel.add(this.client);

        productsPanel.setBorder(BorderFactory.createTitledBorder("Products"));
        productsPanel.add(products);
        productsPanel.add(productsBox);
        productsPanel.add(addProduct);

        buttonsPanel.add(confirmButton);
        buttonsPanel.add(cancelButton);

        JPanel panel = new JPanel(new GridLayout(0, 1));

        panel.add(formPanel);
        panel.add(productsPanel);

        this.add(panel);
        this.add(buttonsPanel, BorderLayout.SOUTH);
        this.pack();


    }
}

