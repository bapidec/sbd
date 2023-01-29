package screen;

import controller.EntityController;
import controller.SupplierController;
import dialogs.EntityDialog;
import dialogs.SupplierDialog;
import entity.SupplierEntity;
import entityFactory.DefaultEntityManagerFactory;
import iterator.SupplierFilteredDataList;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import view.EntityView;
import view.SupplierView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class SuppliersScreen extends Screen {


    public SuppliersScreen() {
        super();

        this.tablePanel.setBorder(BorderFactory.createTitledBorder("Suppliers table"));
        this.detailsPanel.setBorder(BorderFactory.createTitledBorder("Suppliers details"));


    }

    private static void noSupplierSelectedWarning(JFrame frame) {
        JOptionPane.showMessageDialog(frame, "Please select supplier first", "No supplier selected", JOptionPane.WARNING_MESSAGE);
    }

    @Override
    protected void addAdditionalButtons(JPanel buttonsPanel) {
    }

    @Override
    protected SupplierEntity getSelectedEntity() {
        int cId= Integer.valueOf(table.getValueAt(table.getSelectedRow(), 0).toString());
        String eName = (String) table.getValueAt(table.getSelectedRow(), 1);

        EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
        TypedQuery<SupplierEntity> supplierById = entityManager.createNamedQuery("SupplierEntity.byId", SupplierEntity.class);

        supplierById.setParameter("supplierId", cId);

        return supplierById.getSingleResult();
    }

    @Override
    protected Object[][] fetchDataFromDatabase(int columnsNr) {
        List<String[]> rows = new ArrayList<>();

        EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
        TypedQuery<SupplierEntity> allSuppliers = entityManager.createNamedQuery("SupplierEntity.all", SupplierEntity.class);



        SupplierFilteredDataList filteredResults = new SupplierFilteredDataList(allSuppliers.getResultList(), "name", "vehicle_number");
//        List filteredResults = allContracts.getResultList();

        for (Object c: filteredResults) {
            c = (SupplierEntity) c;
            int id = ((SupplierEntity) c).getSupplierId();
            String name = String.valueOf(((SupplierEntity) c).getName());
            rows.add(new String[]{String.valueOf(id), name});
        }

        Object[][] data = new Object[rows.size()][columnsNr];

        for(int i = 0; i < rows.size(); i++) {
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
        SupplierEntity selectedSupplier = (SupplierEntity) selectedEntity;
        EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            Query deleteClientQuery = entityManager.createQuery("DELETE FROM SupplierEntity c WHERE c.supplierId = :supplierId");
            deleteClientQuery.setParameter("supplierId", selectedSupplier.getSupplierId());
            deleteClientQuery.executeUpdate();

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
        return new SupplierView();
    }

    @Override
    protected EntityController createController() {
        return new SupplierController();
    }

    @Override
    protected EntityDialog createAddDialog() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(SuppliersScreen.this);
        return new SupplierDialog(frame, "Add supplier", SuppliersScreen.this);
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
        TypedQuery<SupplierEntity> allSuppliers = entityManager.createNamedQuery("SupplierEntity.all", SupplierEntity.class);

        for (SupplierEntity c: allSuppliers.getResultList()) {
            int id = c.getSupplierId();
            String name = String.valueOf(c.getName());


            model.addRow(new String[]{String.valueOf(id), name});
        }
        entityManager.close();
    }



}


