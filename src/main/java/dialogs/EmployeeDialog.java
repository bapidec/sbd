package dialogs;

import entity.EmployeeEntity;
import entity.SupplierEntity;
import entityFactory.DefaultEntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.w3c.dom.Entity;
import screen.EmployeeScreen;
import screen.SuppliersScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;

public class EmployeeDialog extends JDialog implements EntityDialog {
    JTextField firstNameField = new JTextField();
    JTextField lastNameField = new JTextField();
    JTextField sexField = new JTextField();
    JTextField emailField = new JTextField();
    JTextField phoneNumberField = new JTextField();
    JTextField dateOfBirthField = new JTextField();

    JButton confirmButton = new JButton("Confirm");
    JButton cancelButton = new JButton("Cancel");
    JButton addButton;

    EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
    public EmployeeDialog(JFrame frame, String title, EmployeeScreen employeeScreen){
        super(frame, title);
        super.setVisible(true);
        super.setModal(true);
        super.setAlwaysOnTop(true);
        super.setModalityType(ModalityType.APPLICATION_MODAL);
        super.setPreferredSize(new Dimension(400,300));
        super.setLocationRelativeTo(null);
        super.setLayout(new BorderLayout());

        this.addButton = employeeScreen.getAddButton();

        this.confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEmployee(employeeScreen);
            }
        });
        this.cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });


        JPanel formPanel = new JPanel(new GridLayout(0 ,2));
        JPanel buttonsPanel = new JPanel();

        formPanel.add(new JLabel("First name: "));
        formPanel.add(firstNameField);

        formPanel.add(new JLabel("Last name: "));
        formPanel.add(lastNameField);

        formPanel.add(new JLabel("Sex: "));
        formPanel.add(sexField);

        formPanel.add(new JLabel("Email: "));
        formPanel.add(emailField);

        formPanel.add(new JLabel("Phone number: "));
        formPanel.add(phoneNumberField);

        formPanel.add(new JLabel("Date of birth: "));
        formPanel.add(dateOfBirthField);



        buttonsPanel.add(confirmButton);
        buttonsPanel.add(cancelButton);

        this.add(formPanel);
        this.add(buttonsPanel, BorderLayout.SOUTH);
        this.pack();

    }

    private void close() {
        addButton.setEnabled(true);
        EmployeeDialog.super.dispose();
    }

    private void addEmployee(EmployeeScreen employeeScreen) {
        EntityTransaction transaction = entityManager.getTransaction();



        try {
            transaction.begin();
            EmployeeEntity newEmployee = new EmployeeEntity();
            newEmployee.setName(this.firstNameField.getText());
            newEmployee.setSurname(this.lastNameField.getText());
            newEmployee.setSex(this.sexField.getText());
            newEmployee.setEmail(this.emailField.getText());
            newEmployee.setPhoneNr(this.phoneNumberField.getText());
            newEmployee.setDateOfBirth(Timestamp.valueOf(this.dateOfBirthField.getText()+" 00:00:00"));



            entityManager.persist(newEmployee);
            transaction.commit();

            employeeScreen.refreshTable();

        } finally {
            if(transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
        }

        close();
    }
}
