package dialogs;


import entity.PlaceEntity;
import entityFactory.DefaultEntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import screen.PlacesScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlacesDialog extends JDialog implements EntityDialog {

    JTextField locationField = new JTextField();
    JTextField productLimitField = new JTextField();
    JTextField employeeLimitField = new JTextField();
    JTextField maintenanceCostField = new JTextField();
    JPanel typeField = new JPanel();
    JComboBox supplier = new JComboBox();
    JButton confirmButton = new JButton("Confirm");
    JButton cancelButton = new JButton("Cancel");
    JButton addButton;
    EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();


    public PlacesDialog(JFrame frame, String title, PlacesScreen placesScreen){
        super(frame, title);
        super.setVisible(true);
        super.setModal(true);
        super.setAlwaysOnTop(true);
        super.setModalityType(ModalityType.APPLICATION_MODAL);
        super.setPreferredSize(new Dimension(400,300));
        super.setLocationRelativeTo(null);
        super.setLayout(new BorderLayout());

        this.addButton = placesScreen.getAddButton();

        this.confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPlace(placesScreen);
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

        formPanel.add(new JLabel("Location adress: "));
        formPanel.add(locationField);

        formPanel.add(new JLabel("Product limit: "));
        formPanel.add(productLimitField);

        formPanel.add(new JLabel("Employee limit in workplace: "));
        formPanel.add(employeeLimitField);

        formPanel.add(new JLabel("Monthly maintenance cost: "));
        formPanel.add(maintenanceCostField);

        formPanel.add(new JLabel("Supplier: "));
        formPanel.add(supplier);

        formPanel.add(new JLabel("Building role: "));
        typeField.add(new JRadioButton("Warehouse"));
        typeField.add(new JRadioButton("Shop"));
        formPanel.add(typeField);


        buttonsPanel.add(confirmButton);
        buttonsPanel.add(cancelButton);
        this.add(formPanel);
        this.add(buttonsPanel, BorderLayout.SOUTH);
        this.pack();
    }
    private void close() {
        addButton.setEnabled(true);
        PlacesDialog.super.dispose();
    }

    private void addPlace(PlacesScreen placeScreen) {
        EntityTransaction transaction = entityManager.getTransaction();



        try {
            transaction.begin();
            PlaceEntity newPlace = new PlaceEntity();
            newPlace.setLocation(this.locationField.getText());
            newPlace.setProductLimit(Integer.valueOf(this.productLimitField.getText()));
            newPlace.setEmployeeLimit(Integer.valueOf(this.employeeLimitField.getText()));
            newPlace.setMaintenanceCost(Integer.valueOf(this.maintenanceCostField.getText()));
            newPlace.setSupplierSupplierId(1);//dodać sprawdzanie suppliera

            entityManager.persist(newPlace);
            transaction.commit();

            placeScreen.refreshTable();

        } finally {
            if(transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
        }

        close();
    }

}
