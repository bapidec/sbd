package dialogs;

import entity.ClientEntity;
import entity.ProductEntity;
import entityFactory.DefaultEntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import screen.ClientsScreen;
import screen.ProductsScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;

public class ProductDialog extends JDialog implements EntityDialog {

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

    JButton confirmButton = new JButton("Confirm");
    JButton cancelButton = new JButton("Cancel");
    JButton addButton;
    JPanel formPanel = new JPanel(new GridLayout(0, 2));
    JPanel keywordsPanel = new JPanel(new GridLayout(0, 1));
    JPanel imagesPanel = new JPanel(new GridLayout(0, 1));
    JPanel buttonsPanel = new JPanel();
    EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();

    public ProductDialog(JFrame frame, String title, ProductsScreen productsScreen) {
        super(frame, title);
        super.setVisible(true);
        super.setModal(true);
        super.setAlwaysOnTop(true);
        super.setModalityType(ModalityType.APPLICATION_MODAL);
        super.setPreferredSize(new Dimension(400, 300));
        super.setLocationRelativeTo(null);
        super.setLayout(new BorderLayout());

        this.addButton = productsScreen.getAddButton();

        addGenres();

        this.confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct(productsScreen);
            }
        });

        this.cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });

        JPanel formPanel = new JPanel(new GridLayout(0, 2));
        JPanel buttonsPanel = new JPanel();

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


        buttonsPanel.add(confirmButton);
        buttonsPanel.add(cancelButton);
        this.add(formPanel);
        this.add(buttonsPanel, BorderLayout.SOUTH);
        this.pack();


    }

    private void addGenres() {
        TypedQuery<Integer> genreIds = entityManager.createNamedQuery("ProductGenreEntity.ids", Integer.class);
        for(Integer i : genreIds.getResultList()) {
            this.genreBox.addItem(i);
        }

    }

    private void close() {
        addButton.setEnabled(true);
        ProductDialog.super.dispose();
    }

    private void addProduct(ProductsScreen productsScreen) {

        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            ProductEntity newProduct = new ProductEntity();
            newProduct.setName(this.productNameField.getText());
            newProduct.setType(this.typeField.getText());

            newProduct.setDateOfProduction(Timestamp.valueOf(this.dateOfProductionField.getText() + " 00:00:00"));
            newProduct.setPrice(Double.parseDouble(this.priceField.getText()));
            newProduct.setDescription(this.descriptionArea.getText());
            newProduct.setDiscountDiscountId(1);

            entityManager.persist(newProduct);
            transaction.commit();

            productsScreen.refreshTable();

        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
        }

        close();
    }
}
