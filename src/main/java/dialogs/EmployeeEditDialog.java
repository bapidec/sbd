package dialogs;

import entity.ClientEntity;
import entity.EmployeeEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import screen.ClientsScreen;
import screen.ContractScreen;
import screen.EmployeeScreen;

import javax.swing.*;
import java.sql.Timestamp;

public class EmployeeEditDialog extends EmployeeDialog{
    private EmployeeEntity employee;
    public EmployeeEditDialog(JFrame frame, String title, EmployeeScreen employeeScreen, EmployeeEntity employee) {
        super(frame, title, employeeScreen);
        this.addButton = employeeScreen.getEditButton();
        this.employee = employee;

        fillData();

    }

    private void fillData() {

        firstNameField.setText(String.valueOf(employee.getName()));
        lastNameField.setText(String.valueOf(employee.getSurname()));
        sexField.setText(String.valueOf(employee.getSex()));
        emailField.setText(employee.getEmail());
        phoneNumberField.setText(employee.getPhoneNr());
        String birthDate = String.valueOf(employee.getDateOfBirth());
        dateOfBirthField.setText(birthDate.substring(0, 10));
    }

    @Override
    protected void close() {
        addButton.setEnabled(true);
        EmployeeEditDialog.super.dispose();
    }

    @Override
    protected void confirmAction(EmployeeScreen employeeScreen) {

        Session session = entityManager.unwrap(Session.class);
        Transaction transaction = null;

        boolean toUpdate = false;

        try {
            if(!employee.getName().equals(this.firstNameField.getText())){
                employee.setName(this.firstNameField.getText());
            toUpdate = true;
            }
            if(!employee.getSurname().equals(this.lastNameField.getText())){
                employee.setSurname(this.lastNameField.getText());
                toUpdate = true;
            }
            if(!employee.getSex().equals(this.sexField.getText())){
                employee.setSex(this.sexField.getText());
            toUpdate = true;
            }
            if(!employee.getEmail().equals(this.emailField.getText())){
                employee.setEmail(this.emailField.getText());
                toUpdate = true;
            }
            if(!employee.getPhoneNr().equals(this.phoneNumberField.getText())){
                employee.setPhoneNr(this.phoneNumberField.getText());
                toUpdate = true;
            }
            if(!employee.getDateOfBirth().equals(Timestamp.valueOf(this.dateOfBirthField.getText()+" 00:00:00"))) {
                employee.setDateOfBirth(Timestamp.valueOf(this.dateOfBirthField.getText()+" 00:00:00"));
                toUpdate = true;;
            }

            if(toUpdate) {
                transaction = session.beginTransaction();
                session.update(employee);
                transaction.commit();
                employeeScreen.refreshTable();
            }


        } finally {
            if(transaction != null) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
            }
            session.close();
        }

        employeeScreen.setSelectedEntity(this.employee);
        close();
    }


}
