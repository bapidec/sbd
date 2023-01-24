package dialogs;

import entity.ClientEntity;
import entity.OrderEntity;
import entityFactory.DefaultEntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
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
    JTextArea products = new JTextArea("");
    JComboBox productsBox = new JComboBox();
    JButton addProduct = new JButton("Add product");

    JPanel formPanel = new JPanel(new GridLayout(0, 2));
    JPanel productsPanel = new JPanel(new GridLayout(0, 1));
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

        productsPanel.setBorder(BorderFactory.createTitledBorder("Products"));
        productsPanel.add(products);
        productsPanel.add(productsBox);
        productsPanel.add(addProduct);

        buttonsPanel.add(confirmButton);
        buttonsPanel.add(cancelButton);

        JPanel panel = new JPanel(new GridLayout(0, 1));

        panel.add(formPanel);
        panel.add(productsPanel);

        this.add(panel);
        this.add(buttonsPanel, BorderLayout.SOUTH);
        this.pack();


    }
    private void close() {
        addButton.setEnabled(true);
        OrderDialog.super.dispose();
    }
    private void addOrder(OrdersScreen ordersScreen) {
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

