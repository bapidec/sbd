package screen;

import controller.EntityController;
import controller.ProductGenreController;
import controller.SupplierController;
import dialogs.ClientDialog;
import dialogs.EntityDialog;
import dialogs.ProductGenreDialog;
import dialogs.SupplierDialog;
import entity.ProductGenreEntity;
import entity.SupplierEntity;
import entityFactory.DefaultEntityManagerFactory;
import iterator.ProductGenreFilteredDataList;
import iterator.SupplierFilteredDataList;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import view.ClientView;
import view.EntityView;
import view.ProductGenreView;
import view.SupplierView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ProductGenresScreen extends Screen {

    public ProductGenresScreen() {
        super();

        this.tablePanel.setBorder(BorderFactory.createTitledBorder("Genre table"));
        this.detailsPanel.setBorder(BorderFactory.createTitledBorder("Genre details"));


    }



    private static void noProductGenreSelectedWarning(JFrame frame) {
        JOptionPane.showMessageDialog(frame, "Please select genre first", "No genre selected", JOptionPane.WARNING_MESSAGE);
    }

    @Override
    protected void addAdditionalButtons(JPanel buttonsPanel) {
    }

    @Override
    protected ProductGenreEntity getSelectedEntity() {
        int cId = Integer.valueOf(table.getValueAt(table.getSelectedRow(), 0).toString());
        String eName = (String) table.getValueAt(table.getSelectedRow(), 1);

        EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
        TypedQuery<ProductGenreEntity> productGenreById = entityManager.createNamedQuery("ProductGenreEntity.byId", ProductGenreEntity.class);

        productGenreById.setParameter("genreId", cId);

        return productGenreById.getSingleResult();
    }

    @Override
    protected Object[][] fetchDataFromDatabase(int columnsNr) {
        List<String[]> rows = new ArrayList<>();

        EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
        TypedQuery<ProductGenreEntity> allProductGenres = entityManager.createNamedQuery("ProductGenreEntity.all", ProductGenreEntity.class);


        ProductGenreFilteredDataList filteredResults = new ProductGenreFilteredDataList(allProductGenres.getResultList(), "name", "desc");
//        List filteredResults = allContracts.getResultList();

        for (Object c : filteredResults) {
            c = (ProductGenreEntity) c;
            int id = ((ProductGenreEntity) c).getGenreId();
            String name = String.valueOf(((ProductGenreEntity) c).getName());
            rows.add(new String[]{String.valueOf(id), name});
        }

        Object[][] data = new Object[rows.size()][columnsNr];

        for (int i = 0; i < rows.size(); i++) {
            data[i] = rows.get(i);
        }

        return data;
    }

    @Override
    protected String[] createColumnNames() {
        return new String[]{"Id", "Name"};
    }

    @Override
    protected void deleteSelectedEntity() {
        ProductGenreEntity selectedProductGenre = (ProductGenreEntity) selectedEntity;
        EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            Query deleteProductGenreQuery = entityManager.createQuery("DELETE FROM ProductGenreEntity c WHERE c.genreId = :genreId");
            deleteProductGenreQuery.setParameter("genreId", selectedProductGenre.getGenreId());
            deleteProductGenreQuery.executeUpdate();

            entityTransaction.commit();

            entityManager.close();

            table.clearSelection();
            refreshTable();
        } finally {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            entityManager.close();
        }
    }

    @Override
    protected EntityView createView() {
        return new ProductGenreView();
    }

    @Override
    protected EntityController createController() {
        return new ProductGenreController();
    }

    @Override
    protected EntityDialog createDialog() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ProductGenresScreen.this);
        return new ProductGenreDialog(frame, "Add genre", ProductGenresScreen.this);
    }
    @Override
    public void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
        TypedQuery<ProductGenreEntity> allProductGenres = entityManager.createNamedQuery("ProductGenreEntity.all", ProductGenreEntity.class);

        for (ProductGenreEntity c: allProductGenres.getResultList()) {
            int id = c.getGenreId();
            String name = String.valueOf(c.getName());


            model.addRow(new String[]{String.valueOf(id), name});
        }
        entityManager.close();
    }



}
