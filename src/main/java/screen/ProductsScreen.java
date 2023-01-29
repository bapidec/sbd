package screen;

import controller.ClientController;
import controller.EntityController;
import controller.ProductController;
import dialogs.ClientDialog;
import dialogs.EntityDialog;
import dialogs.ShowImagesDialog;
import dialogs.ProductDialog;
import entity.ClientEntity;
import entity.ProductEntity;
import entityFactory.DefaultEntityManagerFactory;
import iterator.ClientFilteredDataList;
import iterator.ProductFilteredDataList;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import view.ClientView;
import view.EntityView;
import view.ProductsView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ProductsScreen extends Screen{

    public ProductsScreen() {
        super();

        this.tablePanel.setBorder(BorderFactory.createTitledBorder("Products table"));
        this.detailsPanel.setBorder(BorderFactory.createTitledBorder("Products details"));


    }
    private static void noProductSelectedWarning(JFrame frame) {
        JOptionPane.showMessageDialog(frame, "Please select product first", "No product selected", JOptionPane.WARNING_MESSAGE);
    }

    @Override
    protected void addAdditionalButtons(JPanel buttonsPanel) {

    }

    @Override
    protected ProductEntity getSelectedEntity() {
        int cId= Integer.valueOf(table.getValueAt(table.getSelectedRow(), 0).toString());
        String eName = (String) table.getValueAt(table.getSelectedRow(), 3);

        EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
        TypedQuery<ProductEntity> productById = entityManager.createNamedQuery("ProductEntity.byId", ProductEntity.class);
        productById.setParameter("productId", cId);

        return productById.getSingleResult();
    }

    @Override
    protected Object[][] fetchDataFromDatabase(int columnsNr) {
        List<String[]> rows = new ArrayList<>();

        EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
        TypedQuery<ProductEntity> allProducts = entityManager.createNamedQuery("ProductEntity.all", ProductEntity.class);



        ProductFilteredDataList filteredResults = new ProductFilteredDataList(allProducts.getResultList(), "name", "Yoda");
//        List filteredResults = allContracts.getResultList();

        for (Object c: filteredResults) {
            c = (ProductEntity) c;
            int id = ((ProductEntity) c).getProductId();
            String name = String.valueOf(((ProductEntity) c).getName());
            String type= ((ProductEntity) c).getType();
            Double price= ((ProductEntity) c).getPrice();

            rows.add(new String[]{String.valueOf(id), name, type, String.valueOf(price)});
        }

        Object[][] data = new Object[rows.size()][columnsNr];

        for(int i = 0; i < rows.size(); i++) {
            data[i] = rows.get(i);
        }

        return data;
    }

    @Override
    protected String[] createColumnNames() {
        return new String[]{"Id", "Name", "Type", "Price"};
    }

    @Override
    protected void deleteSelectedEntity() {
        ProductEntity selectedProduct = (ProductEntity) selectedEntity;
        EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            Query deleteProductQuery = entityManager.createQuery("DELETE FROM ProductEntity c WHERE c.productId = :productId");
            deleteProductQuery.setParameter("productId", selectedProduct.getProductId());
            deleteProductQuery.executeUpdate();

            entityTransaction.commit();

            entityManager.close();

            table.clearSelection();
            refreshTable();
        } finally {
            if(entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            entityManager.close();
        }
    }

    @Override
    protected EntityView createView() {
        return new ProductsView();
    }

    @Override
    protected EntityController createController() {
        return new ProductController();
    }

    @Override
    protected EntityDialog createAddDialog() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ProductsScreen.this);
        return new ProductDialog(frame, "Add product", ProductsScreen.this);
    }

    @Override
    protected EntityDialog createEditDialog() {
        return null;
    }

    @Override
    public void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
        TypedQuery<ProductEntity> allProducts = entityManager.createNamedQuery("ProductEntity.all", ProductEntity.class);

        for (ProductEntity c: allProducts.getResultList()) {
            int id = c.getProductId();
            String name = String.valueOf(((ProductEntity) c).getName());
            String type= ((ProductEntity) c).getType();
            Double price= ((ProductEntity) c).getPrice();

            model.addRow(new String[]{String.valueOf(id), name, type, String.valueOf(price)});
        }
        entityManager.close();
    }


}
