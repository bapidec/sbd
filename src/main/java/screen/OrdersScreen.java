package screen;

import controller.ClientController;
import controller.EntityController;
import controller.OrderController;
import dialogs.ClientDialog;
import dialogs.EntityDialog;
import dialogs.OrderDialog;
import entity.ClientEntity;
import entity.OrderEntity;
import entityFactory.DefaultEntityManagerFactory;
import iterator.ClientFilteredDataList;
import iterator.OrderFilteredDataList;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import view.ClientView;
import view.EntityView;
import view.OrderView;
import view.PlaceView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class OrdersScreen extends Screen {

    public OrdersScreen() {
        super();

        this.tablePanel.setBorder(BorderFactory.createTitledBorder("Orders table"));
        this.detailsPanel.setBorder(BorderFactory.createTitledBorder("Orders details"));


    }
    private static void noOrdersSelectedWarning(JFrame frame) {
        JOptionPane.showMessageDialog(frame, "Please select order first", "No order selected", JOptionPane.WARNING_MESSAGE);
    }

    @Override
    protected void addAdditionalButtons(JPanel buttonsPanel) {

    }

    @Override
    protected OrderEntity getSelectedEntity() {
        int cId= Integer.valueOf(table.getValueAt(table.getSelectedRow(), 0).toString());
        String eName = (String) table.getValueAt(table.getSelectedRow(), 2);

        EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
        TypedQuery<OrderEntity> orderById = entityManager.createNamedQuery("OrderEntity.byId", OrderEntity.class);
        orderById.setParameter("order_Id", cId);

        return orderById.getSingleResult();
    }
    @Override
    protected Object[][] fetchDataFromDatabase(int columnsNr) {
        List<String[]> rows = new ArrayList<>();

        EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
        TypedQuery<OrderEntity> allOrders = entityManager.createNamedQuery("OrderEntity.all", OrderEntity.class);
        OrderFilteredDataList filteredResults = new OrderFilteredDataList(allOrders.getResultList(), "start_date", "2023-01-24 12:39:48");
//        List filteredResults = allContracts.getResultList();

        for (Object c: filteredResults) {

            c = (OrderEntity) c;
            int id = ((OrderEntity) c).getOrderId();
            String startDate = String.valueOf(((OrderEntity) c).getStartDate());
            String address= ((OrderEntity) c).getAddress();
            rows.add(new String[]{String.valueOf(id), startDate, address});
        }

        Object[][] data = new Object[rows.size()][columnsNr];

        for(int i = 0; i < rows.size(); i++) {
            data[i] = rows.get(i);
        }

        return data;
    }

    @Override
    protected String[] createColumnNames() {
        return new String[]{"Id", "Start Date", "Address"};
    }

    @Override
    protected void deleteSelectedEntity() {
        OrderEntity selectedOrder  = (OrderEntity) selectedEntity;
        EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            Query deleteOrderQuery = entityManager.createQuery("DELETE FROM OrderEntity c WHERE c.orderId = :orderId");
            deleteOrderQuery.setParameter("orderId", selectedOrder.getOrderId());
            deleteOrderQuery.executeUpdate();

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
        return new OrderView();
    }

    @Override
    protected EntityController createController() {
        return new OrderController();
    }

    @Override
    protected EntityDialog createAddDialog() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(OrdersScreen.this);
        return new OrderDialog(frame, "Add order", OrdersScreen.this);
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
        TypedQuery<OrderEntity> allOrders = entityManager.createNamedQuery("OrderEntity.all", OrderEntity.class);

        for (OrderEntity c: allOrders.getResultList()) {
            int id = c.getOrderId();
            String startDate = String.valueOf(c.getStartDate());
            String address = String.valueOf(c.getAddress());

            model.addRow(new String[]{String.valueOf(id), startDate, address});
        }
        entityManager.close();
    }
}


