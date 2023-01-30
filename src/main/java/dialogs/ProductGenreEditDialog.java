package dialogs;

import entity.ClientEntity;
import entity.EmployeeEntity;
import entity.ProductGenreEntity;
import entity.SupplierEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import screen.*;

import javax.swing.*;
import java.sql.Timestamp;

public class ProductGenreEditDialog extends ProductGenreDialog {
    private ProductGenreEntity genre;

    public ProductGenreEditDialog(JFrame frame, String title, ProductGenresScreen productGenresScreen, ProductGenreEntity productGenre) {
        super(frame, title, productGenresScreen);
        this.addButton = productGenresScreen.getEditButton();
        this.genre = productGenre;

        fillData();

    }

    private void fillData() {

        name.setText(String.valueOf(genre.getName()));
        desc.setText(String.valueOf(genre.getDescription()));
    }

    @Override
    protected void close() {
        addButton.setEnabled(true);
        ProductGenreEditDialog.super.dispose();
    }

    @Override
    protected void confirmAction(ProductGenresScreen productGenresScreen) {

        Session session = entityManager.unwrap(Session.class);
        Transaction transaction = null;

        boolean toUpdate = false;

        try {
            if (!genre.getName().equals(this.name.getText())) {
                genre.setName(this.name.getText());
                toUpdate = true;
            }
            if (!genre.getDescription().equals(this.desc.getText())) {
                genre.setDescription(this.desc.getText());
                toUpdate = true;
            }


            if (toUpdate) {
                transaction = session.beginTransaction();
                session.update(genre);
                transaction.commit();
                productGenresScreen.refreshTable();
            }


        } finally {
            if (transaction != null) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
            }
            session.close();
        }

        productGenresScreen.setSelectedEntity(this.genre);
        close();
    }
}