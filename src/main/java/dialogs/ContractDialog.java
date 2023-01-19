package dialogs;

import entity.ContractEntity;
import entityFactory.DefaultEntityManagerFactory;
import jakarta.persistence.*;
import screen.ContractScreen;
import screen.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;

public class ContractDialog extends JDialog implements EntityDialog {
    JTextField startDate = new JTextField();
    JTextField endDate = new JTextField();
    JTextField paymentAmount = new JTextField();
    JTextField type = new JTextField();
    JComboBox employee = new JComboBox<>();
    JComboBox place = new JComboBox<>();
    JButton confirmButton = new JButton("Confirm");
    JButton cancelButton = new JButton("Cancel");
    JButton addButton;
    ContractScreen contractScreen;

    EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();

    public ContractDialog(JFrame frame, String title, ContractScreen contractScreen){
        super(frame);
        super.setTitle("aaa");
        super.setVisible(true);
        super.setModal(true);
        super.setAlwaysOnTop(true);
        super.setModalityType(ModalityType.APPLICATION_MODAL);
        super.setPreferredSize(new Dimension(400,300));
        super.setLocationRelativeTo(null);
        super.setLayout(new BorderLayout());

        this.addButton = contractScreen.getAddButton();

        addEmployees();
        addPlaces();

        this.confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addContract(contractScreen);
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

        formPanel.add(new JLabel("Start date: "));
        formPanel.add(startDate);

        formPanel.add(new JLabel("End date: "));
        formPanel.add(endDate);

        formPanel.add(new JLabel("Payment amount: "));
        formPanel.add(paymentAmount);

        formPanel.add(new JLabel("Type: "));
        formPanel.add(type);

        formPanel.add(new JLabel("Employee: "));
        formPanel.add(employee);

        formPanel.add(new JLabel("Place: "));
        formPanel.add(place);


        buttonsPanel.add(confirmButton);
        buttonsPanel.add(cancelButton);
        this.add(formPanel);
        this.add(buttonsPanel, BorderLayout.SOUTH);
        this.pack();



    }

    private void addEmployees() {
        TypedQuery<Integer> employeeIds = entityManager.createNamedQuery("EmployeeEntity.ids", Integer.class);
        for(Integer i : employeeIds.getResultList()) {
            this.employee.addItem(i);
        }
    }

    private void addPlaces() {
        TypedQuery<Integer> placeIds = entityManager.createNamedQuery("PlaceEntity.ids", Integer.class);
        for(Integer i : placeIds.getResultList()) {
            this.place.addItem(i);
        }
    }

    private void close() {
        addButton.setEnabled(true);
        ContractDialog.super.dispose();
    }

    private void addContract(ContractScreen contractScreen) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            ContractEntity newContract = new ContractEntity();
            newContract.setEmployeeEmployeeId((Integer) this.employee.getSelectedItem());
            newContract.setPlacePlaceId((Integer) this.place.getSelectedItem());
            newContract.setPaymentAmount(Double.parseDouble(this.paymentAmount.getText()));
            newContract.setType(this.type.getText());
            newContract.setDateStart(Timestamp.valueOf(this.startDate.getText()+" 00:00:00"));
            newContract.setDateEnd(Timestamp.valueOf(this.endDate.getText()+" 00:00:00"));

            entityManager.persist(newContract);
            transaction.commit();

            contractScreen.refreshTable();

        } finally {
            if(transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
        }

        close();
    }

    @Override
    public void setFrame(JFrame frame) {

    }

    @Override
    public void setScreen(Screen screen) {

    }
}


