package dialogs;

import entity.ClientEntity;
import entity.EmployeeEntity;
import entity.SupplierEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import screen.ClientsScreen;
import screen.ContractScreen;
import screen.EmployeeScreen;
import screen.SuppliersScreen;

import javax.swing.*;
import java.sql.Timestamp;

public class SupplierEditDialog extends SupplierDialog{
    private SupplierEntity supplier;
    public SupplierEditDialog(JFrame frame, String title, SuppliersScreen suppliersScreen, SupplierEntity supplier) {
        super(frame, title, suppliersScreen);
        this.addButton = suppliersScreen.getEditButton();
        this.supplier = supplier;

        fillData();

    }

    private void fillData() {

        name.setText(String.valueOf(supplier.getName()));
        vehicleNr.setText(String.valueOf(supplier.getVehicleNumber()));
        String sStartDate = String.valueOf(supplier.getStartDate());
        String sEndDate = String.valueOf(supplier.getEndDate());
        startDate.setText(sStartDate.substring(0,10));
        endDate.setText(sEndDate.substring(0,10));
    }

    @Override
    protected void close() {
        addButton.setEnabled(true);
        SupplierEditDialog.super.dispose();
    }

    @Override
    protected void confirmAction(SuppliersScreen suppliersScreen) {

        Session session = entityManager.unwrap(Session.class);
        Transaction transaction = null;

        boolean toUpdate = false;

        try {
            if(!supplier.getName().equals(this.name.getText())){
                supplier.setName(this.name.getText());
                toUpdate = true;
            }
            if(!supplier.getVehicleNumber().equals(this.vehicleNr.getText())){
                supplier.setVehicleNumber(Integer.valueOf(this.vehicleNr.getText()));
                toUpdate = true;
            }
            if(!supplier.getStartDate().equals(Timestamp.valueOf(this.startDate.getText()+" 00:00:00"))) {
                supplier.setStartDate(Timestamp.valueOf(this.startDate.getText()+" 00:00:00"));
                toUpdate = true;;
            }
            if(!supplier.getEndDate().equals(Timestamp.valueOf(this.endDate.getText()+" 00:00:00"))) {
                supplier.setEndDate(Timestamp.valueOf(this.endDate.getText()+" 00:00:00"));
                toUpdate = true;;
            }


            if(toUpdate) {
                transaction = session.beginTransaction();
                session.update(supplier);
                transaction.commit();
                suppliersScreen.refreshTable();
            }


        } finally {
            if(transaction != null) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
            }
            session.close();
        }

        suppliersScreen.setSelectedEntity(this.supplier);
        close();
    }


}