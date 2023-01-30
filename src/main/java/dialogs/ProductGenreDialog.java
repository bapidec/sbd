package dialogs;

import entity.ProductGenreEntity;
import entityFactory.DefaultEntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import screen.ProductGenresScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductGenreDialog extends JDialog implements EntityDialog {
    JTextField name = new JTextField();
    JTextArea desc = new JTextArea();
    JButton confirmButton = new JButton("Confirm");
    JButton cancelButton = new JButton("Cancel");

    JButton addButton;
    EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();

    public ProductGenreDialog(JFrame frame, String title, ProductGenresScreen productGenresScreen){
        super(frame, title);
        super.setVisible(true);
        super.setModal(true);
        super.setAlwaysOnTop(true);
        super.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        super.setPreferredSize(new Dimension(400,300));
        super.setLocationRelativeTo(null);
        super.setLayout(new BorderLayout());

        this.addButton = productGenresScreen.getAddButton();

        this.confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmAction(productGenresScreen);
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
        formPanel.add(name);

        formPanel.add(new JLabel("Description: "));
        formPanel.add(desc);

        buttonsPanel.add(confirmButton);
        buttonsPanel.add(cancelButton);
        this.add(formPanel);
        this.add(buttonsPanel, BorderLayout.SOUTH);
        this.pack();


    }
    protected void close() {
        addButton.setEnabled(true);
        ProductGenreDialog.super.dispose();
    }


    protected void confirmAction(ProductGenresScreen productGenresScreen) {
        EntityTransaction transaction = entityManager.getTransaction();



        try {
            transaction.begin();
            ProductGenreEntity newProductGenre = new ProductGenreEntity();
            newProductGenre.setName(this.name.getText());
            newProductGenre.setDescription(this.desc.getText());


            entityManager.persist(newProductGenre);
            transaction.commit();

            productGenresScreen.refreshTable();

        } finally {
            if(transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
        }

        close();
    }

}



