package dialogs;

import javax.swing.*;
import java.awt.*;

public class ProductGenreDialog extends JDialog {
    JTextField name = new JTextField();
    JTextArea desc = new JTextArea();
    JButton confirmButton = new JButton("Confirm");
    JButton cancelButton = new JButton("Cancel");

    public ProductGenreDialog(JFrame frame, String title){
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

        formPanel.add(new JLabel("Description: "));
        formPanel.add(desc);

        buttonsPanel.add(confirmButton);
        buttonsPanel.add(cancelButton);
        this.add(formPanel);
        this.add(buttonsPanel, BorderLayout.SOUTH);
        this.pack();


    }
}



