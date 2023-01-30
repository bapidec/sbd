package dialogs;

import entity.ClientEntity;
import entity.SupplierEntity;
import entityFactory.DefaultEntityManagerFactory;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import screen.ClientsScreen;
import screen.SuppliersScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;

public class SupplierDialog extends JDialog implements EntityDialog {
    JTextField name = new JTextField();
    JTextField vehicleNr = new JTextField();
    JTextField startDate = new JTextField();
    JTextField endDate = new JTextField();
    JButton confirmButton = new JButton("Confirm");
    JButton cancelButton = new JButton("Cancel");

    JButton addButton;

    EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
    public SupplierDialog(JFrame frame, String title, SuppliersScreen supplierScreen){
        super(frame, title);
        super.setVisible(true);
        super.setModal(true);
        super.setAlwaysOnTop(true);
        super.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        super.setPreferredSize(new Dimension(400,300));
        super.setLocationRelativeTo(null);
        super.setLayout(new BorderLayout());

        this.addButton = supplierScreen.getAddButton();

        this.confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmAction(supplierScreen);
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

        formPanel.add(new JLabel("Vehicle number: "));
        formPanel.add(vehicleNr);

        formPanel.add(new JLabel("Start date: "));
        formPanel.add(startDate);

        formPanel.add(new JLabel("End date: "));
        formPanel.add(endDate);

        buttonsPanel.add(confirmButton);
        buttonsPanel.add(cancelButton);
        this.add(formPanel);
        this.add(buttonsPanel, BorderLayout.SOUTH);
        this.pack();


    }
    protected void close() {
        addButton.setEnabled(true);
        SupplierDialog.super.dispose();
    }

    protected void confirmAction(SuppliersScreen supplierScreen) {
        EntityTransaction transaction = entityManager.getTransaction();



        try {
            transaction.begin();
            SupplierEntity newSupplier = new SupplierEntity();
            newSupplier.setName(this.name.getText());
            newSupplier.setVehicleNumber(Integer.valueOf(this.vehicleNr.getText()));
            newSupplier.setStartDate(Timestamp.valueOf(this.startDate.getText()+" 00:00:00"));
            newSupplier.setEndDate(Timestamp.valueOf(this.endDate.getText()+" 00:00:00"));


            entityManager.persist(newSupplier);
            transaction.commit();

            supplierScreen.refreshTable();

        } finally {
            if(transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
        }

        close();
    }

}



