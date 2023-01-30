package dialogs;

import entity.ClientEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import screen.ClientsScreen;
import screen.ContractScreen;

import javax.swing.*;
import java.sql.Timestamp;

public class ClientEditDialog extends ClientDialog{
    private ClientEntity client;
    public ClientEditDialog(JFrame frame, String title, ClientsScreen clientScreen, ClientEntity client) {
        super(frame, title, clientScreen);
        this.addButton = clientScreen.getEditButton();
        this.client = client;

        fillData();

    }

    private void fillData() {

        name.setText(String.valueOf(client.getName()));
        surname.setText(String.valueOf(client.getSurname()));
        phoneNumber.setText(String.valueOf(client.getPhoneNr()));
        email.setText(client.getEmail());
        address.setText(client.getAddress());
    }

    @Override
    protected void close() {
        addButton.setEnabled(true);
        ClientEditDialog.super.dispose();
    }

    @Override
    protected void confirmAction(ClientsScreen clientsScreen) {

        Session session = entityManager.unwrap(Session.class);
        Transaction transaction = null;

        boolean toUpdate = false;

        try {
            if(!client.getName().equals(this.name.getText())){
                client.setName(this.name.getText());
                toUpdate = true;
            }
            if(!client.getSurname().equals(this.surname.getText())){
                client.setSurname(this.surname.getText());
                    toUpdate = true;
            }
            if(!client.getPhoneNr().equals(this.phoneNumber.getText())){
                client.setPhoneNr(this.phoneNumber.getText());
                    toUpdate = true;
            }
            if(!client.getEmail().equals(this.email.getText())){
                client.setEmail(this.email.getText());
                    toUpdate = true;
            }
            if(!client.getAddress().equals(this.address.getText())){
                client.setAddress(this.address.getText());
                    toUpdate = true;
            }

            if(toUpdate) {
                transaction = session.beginTransaction();
                session.update(client);
                transaction.commit();
                clientsScreen.refreshTable();
            }


        } finally {
            if(transaction != null) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
            }
            session.close();
        }

        clientsScreen.setSelectedEntity(this.client);
        close();
    }


}
