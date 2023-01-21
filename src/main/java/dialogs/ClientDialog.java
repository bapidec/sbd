package dialogs;

import entity.ClientEntity;
import entity.ContractEntity;
import entityFactory.DefaultEntityManagerFactory;
import jakarta.persistence.*;
import screen.ClientsScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;

public class ClientDialog extends JDialog implements EntityDialog {



    JTextField name = new JTextField();
    JTextField surname = new JTextField();
    JTextField phoneNumber = new JTextField();
    JTextField email = new JTextField();
    JTextField address = new JTextField();
    JComboBox place = new JComboBox<>();
    JButton confirmButton = new JButton("Confirm");
    JButton cancelButton = new JButton("Cancel");
    JButton addButton;

    EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();

    public ClientDialog(JFrame frame, String title, ClientsScreen clientScreen){
        super(frame, title);
        super.setVisible(true);
        super.setModal(true);
        super.setAlwaysOnTop(true);
        super.setModalityType(ModalityType.APPLICATION_MODAL);
        super.setPreferredSize(new Dimension(400,300));
        super.setLocationRelativeTo(null);
        super.setLayout(new BorderLayout());

        this.addButton = clientScreen.getAddButton();



        this.confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addClient(clientScreen);
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

        formPanel.add(new JLabel("Client Name: "));
        formPanel.add(name);

        formPanel.add(new JLabel("Client Last Name: "));
        formPanel.add(surname);

        formPanel.add(new JLabel("Client Phone Number: "));
        formPanel.add(phoneNumber);

        formPanel.add(new JLabel("Client Email: "));
        formPanel.add(email);

        formPanel.add(new JLabel("Client Address: "));
        formPanel.add(address);



        buttonsPanel.add(confirmButton);
        buttonsPanel.add(cancelButton);
        this.add(formPanel);
        this.add(buttonsPanel, BorderLayout.SOUTH);
        this.pack();



    }



    private void close() {
        addButton.setEnabled(true);
        ClientDialog.super.dispose();
    }

    private void addClient(ClientsScreen clientScreen) {
        EntityTransaction transaction = entityManager.getTransaction();



        try {
            transaction.begin();
            ClientEntity newClient = new ClientEntity();
            newClient.setName(this.name.getText());
            newClient.setSurname(this.surname.getText());
            newClient.setPhoneNr(this.phoneNumber.getText());
            newClient.setEmail(this.email.getText());
            newClient.setAddress(this.address.getText());

            entityManager.persist(newClient);
            transaction.commit();

            clientScreen.refreshTable();

        } finally {
            if(transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
        }

        close();
    }

}


