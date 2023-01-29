package dialogs;

import entity.ContractEntity;
import entityFactory.DefaultEntityManagerFactory;
import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import screen.ContractScreen;

import javax.swing.*;
import java.sql.Timestamp;

public class ContractEditDialog extends ContractDialog {

    private ContractEntity contract;
    public ContractEditDialog(JFrame frame, String title, ContractScreen contractScreen, ContractEntity contract){
        super(frame, title, contractScreen);

        this.screenButton = contractScreen.getEditButton();
        this.contract = contract;

        fillData();

    }

    private void fillData() {
        String startDateText = String.valueOf(contract.getDateStart());
        String endDateText = String.valueOf(contract.getDateEnd());
        startDate.setText(startDateText.substring(0, 10));
        endDate.setText(endDateText.substring(0, 10));
        paymentAmount.setText(String.valueOf(contract.getPaymentAmount()));
        type.setText(contract.getType());
        employee.setSelectedItem(contract.getEmployeeEmployeeId());
        place.setSelectedItem(contract.getContractId());
    }

    @Override
    protected void close() {
        screenButton.setEnabled(true);
        ContractEditDialog.super.dispose();
    }

    @Override
    protected void confirmAction(ContractScreen contractScreen) {

        Session session = entityManager.unwrap(Session.class);
        Transaction transaction = null;

        boolean toUpdate = false;

        try {
            if(!contract.getDateStart().equals(Timestamp.valueOf(this.startDate.getText()+" 00:00:00"))) {
                contract.setDateStart(Timestamp.valueOf(this.startDate.getText()+" 00:00:00"));//wazne, aby wpisywac w formacie yyyy-MM-dd, np 2022-01-02
                toUpdate = true;
            }
            if(!contract.getDateEnd().equals(Timestamp.valueOf(this.endDate.getText()+" 00:00:00"))) {
                contract.setDateEnd(Timestamp.valueOf(this.endDate.getText()+" 00:00:00"));//wazne, aby wpisywac w formacie yyyy-MM-dd, np 2022-01-02
                toUpdate = true;
            }
            if(contract.getEmployeeEmployeeId() != (int) employee.getSelectedItem()) {
                contract.setEmployeeEmployeeId(Integer.valueOf(employee.getSelectedItem().toString()));
                toUpdate = true;
            }
            if(contract.getPlacePlaceId() != (int) place.getSelectedItem()) {
                contract.setPlacePlaceId(Integer.valueOf(place.getSelectedItem().toString()));
                toUpdate = true;
            }
            if(contract.getPaymentAmount() != Double.parseDouble(this.paymentAmount.getText())) {
                contract.setPaymentAmount(Double.parseDouble(this.paymentAmount.getText()));
                toUpdate = true;
            }
            if(!contract.getType().equals(this.type.getText())) {
                contract.setType(this.type.getText());
                toUpdate = true;
            }
            if(toUpdate) {
                transaction = session.beginTransaction();
                session.update(contract);
                transaction.commit();
                contractScreen.refreshTable();
            }


        } finally {
            if(transaction != null) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
            }
            session.close();
        }

        contractScreen.setSelectedEntity(this.contract);
        close();
    }

}


