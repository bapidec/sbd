package dialogs;

import screen.ProductsScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductDialog extends JDialog{

    private JButton confirmButton = new JButton("Confirm");
    private JButton cancelButton = new JButton("Cancel");
    JTextField productNameField = new JTextField();
    JTextField typeField = new JTextField();
    JTextField priceField = new JTextField();
    JTextArea descriptionArea = new JTextArea();
    JTextField dateOfProductionField = new JTextField();
    JTextArea keywordsArea = new JTextArea("");
    JTextArea imagesArea = new JTextArea("");
    JComboBox genreBox = new JComboBox<>();
    JComboBox keywordsBox = new JComboBox<>();
    JButton addKeyword = new JButton("Add keyword");
    JButton newKeyword = new JButton("New keyword");
    JButton addImage = new JButton("Add image");

    JPanel formPanel = new JPanel(new GridLayout(0, 2));
    JPanel keywordsPanel = new JPanel(new GridLayout(0, 1));
    JPanel imagesPanel = new JPanel(new GridLayout(0,1));
    JPanel buttonsPanel = new JPanel();

    public ProductDialog(JFrame frame, String title) {
        super(frame, title);
        super.setVisible(true);
        super.setModal(true);
        super.setAlwaysOnTop(true);
        super.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        super.setPreferredSize(new Dimension(600,500));
        super.setLocationRelativeTo(null);

        this.setLayout(new BorderLayout());

        formPanel.add(new JLabel("Name: "));
        formPanel.add(this.productNameField);

        formPanel.add(new JLabel("Type: "));
        formPanel.add(this.typeField);

        formPanel.add(new JLabel("Genre: "));
        formPanel.add(this.genreBox);

        formPanel.add(new JLabel("Date of production: "));
        formPanel.add(this.dateOfProductionField);

        formPanel.add(new JLabel("Price: "));
        formPanel.add(this.priceField);

        formPanel.add(new JLabel("Description: "));
        formPanel.add(this.descriptionArea);

        keywordsPanel.setBorder(BorderFactory.createTitledBorder("Keywords"));
        keywordsPanel.add(keywordsArea);
        keywordsPanel.add(keywordsBox);
        keywordsPanel.add(addKeyword);
        keywordsPanel.add(newKeyword);

        imagesPanel.setBorder(BorderFactory.createTitledBorder("Images"));
        imagesPanel.add(imagesArea);
        imagesPanel.add(addImage);

        buttonsPanel.add(confirmButton);
        buttonsPanel.add(cancelButton);

        JPanel panel = new JPanel(new GridLayout(0, 1));

        panel.add(formPanel);
        panel.add(keywordsPanel);
        panel.add(imagesPanel);
        
        this.add(panel);
        this.add(buttonsPanel, BorderLayout.SOUTH);
        this.pack();

        addImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductAddImageDialog addImageDialog = new ProductAddImageDialog(frame);
            }
        });

    }
}
