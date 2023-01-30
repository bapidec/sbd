package dialogs;

import entity.ClientEntity;
import entity.OrderEntity;
import entityFactory.DefaultEntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import screen.ClientsScreen;
import screen.OrdersScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;

public class OrderDialog extends JDialog implements EntityDialog{
    private JButton confirmButton = new JButton("Confirm");
    private JButton cancelButton = new JButton("Cancel");
    JTextField startDate = new JTextField();
    JTextField endDate = new JTextField();
    JTextField address = new JTextField();
    JComboBox client = new JComboBox<>();

    JComboBox productsBox = new JComboBox<>();


    JPanel formPanel = new JPanel(new GridLayout(0, 2));

    JPanel buttonsPanel = new JPanel();
    JButton addButton;
    EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();

    public OrderDialog(JFrame frame, String title, OrdersScreen ordersScreen) {
        super(frame, title);
        super.setVisible(true);
        super.setModal(true);
        super.setAlwaysOnTop(true);
        super.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        super.setPreferredSize(new Dimension(600,500));
        super.setLocationRelativeTo(null);

        this.setLayout(new BorderLayout());

        this.addButton = ordersScreen.getAddButton();

        addClients();
        addProducts();

        this.confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addOrder(ordersScreen);
            }
        });

        this.cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });

        formPanel.add(new JLabel("Start date: "));
        formPanel.add(this.startDate);

        formPanel.add(new JLabel("End date: "));
        formPanel.add(this.endDate);

        formPanel.add(new JLabel("Address: "));
        formPanel.add(this.address);

        formPanel.add(new JLabel("Client: "));
        formPanel.add(this.client);

        formPanel.add(new JLabel("Product: "));
        formPanel.add(this.productsBox);



        buttonsPanel.add(confirmButton);
        buttonsPanel.add(cancelButton);

        JPanel panel = new JPanel(new GridLayout(0, 1));

        panel.add(formPanel);


        this.add(panel);
        this.add(buttonsPanel, BorderLayout.SOUTH);
        this.pack();


    }

    private void addClients() {
        TypedQuery<Integer> clientIds = entityManager.createNamedQuery("ClientEntity.ids", Integer.class);
        for(Integer i : clientIds.getResultList()) {
            this.client.addItem(i);
        }
    }

    private void addProducts(){
        TypedQuery<Integer> productIds = entityManager.createNamedQuery("ProductEntity.ids", Integer.class);
        for(Integer i : productIds.getResultList()) {
            this.productsBox.addItem(i);
        }
    }

    protected void close() {
        addButton.setEnabled(true);
        OrderDialog.super.dispose();
    }
    protected void addOrder(OrdersScreen ordersScreen) {
        EntityTransaction transaction = entityManager.getTransaction();



        try {
            transaction.begin();
            OrderEntity newOrder = new OrderEntity();
            newOrder.setAddress(this.address.getText());
            newOrder.setStartDate(Timestamp.valueOf(this.startDate.getText()+" 00:00:00"));
            newOrder.setEndDate(Timestamp.valueOf(this.endDate.getText()+" 00:00:00"));
            newOrder.setClientClientId(Integer.valueOf(this.client.getSelectedItem().toString()));

            entityManager.persist(newOrder);
            transaction.commit();

            ordersScreen.refreshTable();

        } finally {
            if(transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
        }

        close();
    }

}

