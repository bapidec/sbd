package dialogs;

import entity.ClientEntity;
import entity.EmployeeEntity;
import entity.PlaceEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import screen.ClientsScreen;
import screen.ContractScreen;
import screen.EmployeeScreen;
import screen.PlacesScreen;

import javax.swing.*;
import java.sql.Timestamp;

public class PlacesEditDialog extends PlacesDialog{
    private PlaceEntity place;
    public PlacesEditDialog(JFrame frame, String title, PlacesScreen placesScreen, PlaceEntity place) {
        super(frame, title, placesScreen);
        this.addButton = placesScreen.getEditButton();
        this.place = place;

        fillData();

    }

    private void fillData() {


        locationField.setText(String.valueOf(place.getLocation()));
        productLimitField.setText(String.valueOf(place.getProductLimit()));
        employeeLimitField.setText(String.valueOf(place.getEmployeeLimit()));
        maintenanceCostField.setText(String.valueOf(place.getMaintenanceCost()));
        supplier.setSelectedItem(place.getSupplierSupplierId());

    }

    @Override
    protected void close() {
        addButton.setEnabled(true);
        PlacesEditDialog.super.dispose();
    }

    @Override
    protected void confirmAction(PlacesScreen placesScreen) {

        Session session = entityManager.unwrap(Session.class);
        Transaction transaction = null;

        boolean toUpdate = false;

        try {
            if(!place.getLocation().equals(this.locationField.getText())) {
                place.setLocation(this.locationField.getText());
                toUpdate = true;
            }
            if(!place.getProductLimit().equals(Integer.valueOf(this.productLimitField.getText()))){
                place.setProductLimit(Integer.valueOf(this.productLimitField.getText()));
                toUpdate = true;
            }
            if(!place.getEmployeeLimit().equals(Integer.valueOf(this.employeeLimitField.getText()))){
                place.setEmployeeLimit(Integer.valueOf(this.employeeLimitField.getText()));
                toUpdate = true;
            }
            if(!place.getMaintenanceCost().equals(Integer.valueOf(this.maintenanceCostField.getText()))){
                place.setMaintenanceCost(Integer.valueOf(this.maintenanceCostField.getText()));
                toUpdate = true;
            }
            if(place.getSupplierSupplierId() != (int) supplier.getSelectedItem()) {
                place.setSupplierSupplierId(Integer.valueOf(supplier.getSelectedItem().toString()));
                toUpdate = true;
            }


            if(toUpdate) {
                transaction = session.beginTransaction();
                session.update(place);
                transaction.commit();
                placesScreen.refreshTable();
            }


        } finally {
            if(transaction != null) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
            }
            session.close();
        }

        placesScreen.setSelectedEntity(this.place);
        close();
    }


}
