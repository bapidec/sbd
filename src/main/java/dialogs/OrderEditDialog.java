package dialogs;

import entity.ContractEntity;
import entity.OrderEntity;
import entityFactory.DefaultEntityManagerFactory;
import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import screen.ContractScreen;
import screen.OrdersScreen;

import javax.swing.*;
import java.sql.Timestamp;

public class OrderEditDialog extends OrderDialog {

    private OrderEntity order;
    public OrderEditDialog(JFrame frame, String title, OrdersScreen ordersScreen, OrderEntity order){
        super(frame, title, ordersScreen);

        this.addButton = ordersScreen.getEditButton();
        this.order = order;


        fillData();

    }

    private void fillData() {
        String startDateText = String.valueOf(order.getStartDate());
        String endDateText = String.valueOf(order.getEndDate());
        startDate.setText(startDateText.substring(0, 10));
        endDate.setText(endDateText.substring(0, 10));
        address.setText(order.getAddress());
        client.setSelectedItem(order.getClientClientId());

    }

    @Override
    protected void close() {
        addButton.setEnabled(true);
        OrderEditDialog.super.dispose();
    }

    @Override
    protected void confirmAction(OrdersScreen ordersScreen) {

        Session session = entityManager.unwrap(Session.class);
        Transaction transaction = null;

        boolean toUpdate = false;

        try {
            if(!order.getStartDate().equals(Timestamp.valueOf(this.startDate.getText()+" 00:00:00"))) {
               order.setStartDate(Timestamp.valueOf(this.startDate.getText()+" 00:00:00"));//wazne, aby wpisywac w formacie yyyy-MM-dd, np 2022-01-02
                toUpdate = true;
            }
            if(!order.getEndDate().equals(Timestamp.valueOf(this.endDate.getText()+" 00:00:00"))) {
                order.setEndDate(Timestamp.valueOf(this.endDate.getText()+" 00:00:00"));//wazne, aby wpisywac w formacie yyyy-MM-dd, np 2022-01-02
                toUpdate = true;
            }
            if(order.getClientClientId() != (int) this.client.getSelectedItem()) {
                order.setClientClientId(Integer.valueOf(this.client.getSelectedItem().toString()));
                toUpdate = true;
            }

            if(!order.getAddress().equals(this.address.getText())) {
                order.setAddress(this.address.getText());
                toUpdate = true;
            }

            if(toUpdate) {
                transaction = session.beginTransaction();
                session.update(order);
                transaction.commit();
                ordersScreen.refreshTable();
            }


        } finally {
            if(transaction != null) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
            }
            session.close();
        }

        ordersScreen.setSelectedEntity(this.order);
        close();
    }

}


