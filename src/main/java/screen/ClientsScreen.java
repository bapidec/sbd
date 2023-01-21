package screen;

import controller.ClientController;
import controller.EntityController;
import dialogs.ClientDialog;
import dialogs.EntityDialog;
import entity.ClientEntity;
import entityFactory.DefaultEntityManagerFactory;
import iterator.ClientFilteredDataList;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import view.ClientView;
import view.EntityView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class ClientsScreen extends Screen {


    public ClientsScreen() {
        super();

        this.tablePanel.setBorder(BorderFactory.createTitledBorder("Clients table"));
        this.detailsPanel.setBorder(BorderFactory.createTitledBorder("Clients details"));


    }

    private static void noClientSelectedWarning(JFrame frame) {
        JOptionPane.showMessageDialog(frame, "Please select client first", "No client selected", JOptionPane.WARNING_MESSAGE);
    }

    @Override
    protected void addAdditionalButtons(JPanel buttonsPanel) {
    }

    @Override
    protected ClientEntity getSelectedEntity() {
        int cId= Integer.valueOf(table.getValueAt(table.getSelectedRow(), 0).toString());
        String eName = (String) table.getValueAt(table.getSelectedRow(), 2);

        EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
        TypedQuery<ClientEntity> clientById = entityManager.createNamedQuery("ClientEntity.byId", ClientEntity.class);

        clientById.setParameter("clientId", cId);

        return clientById.getSingleResult();
    }

    @Override
    protected Object[][] fetchDataFromDatabase(int columnsNr) {
        List<String[]> rows = new ArrayList<>();

        EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
        TypedQuery<ClientEntity> allClients = entityManager.createNamedQuery("ClientEntity.all", ClientEntity.class);



        ClientFilteredDataList filteredResults = new ClientFilteredDataList(allClients.getResultList(), "type", "job");
//        List filteredResults = allContracts.getResultList();

        for (Object c: filteredResults) {
            c = (ClientEntity) c;
            int id = ((ClientEntity) c).getClientId();
            String name = String.valueOf(((ClientEntity) c).getName());
            String surname= ((ClientEntity) c).getSurname();
            rows.add(new String[]{String.valueOf(id), name, surname});
        }

        Object[][] data = new Object[rows.size()][columnsNr];

        for(int i = 0; i < rows.size(); i++) {
            data[i] = rows.get(i);
        }

        return data;
    }

    @Override
    protected String[] createColumnNames() {
        return new String[]{"Id", "First Name", "Last Name"};
    }

    @Override
    protected void deleteSelectedEntity() {
        ClientEntity selectedClient = (ClientEntity) selectedEntity;
        EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            Query deleteClientQuery = entityManager.createQuery("DELETE FROM ClientEntity c WHERE c.clientId = :clientId");
            deleteClientQuery.setParameter("clientId", selectedClient.getClientId());
            deleteClientQuery.executeUpdate();

            entityTransaction.commit();

            entityManager.close();

            table.clearSelection();
            refreshTable();
        } finally {
            if(entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            entityManager.close();
        }
    }

    @Override
    protected EntityView createView() {
        return new ClientView();
    }

    @Override
    protected EntityController createController() {
        return new ClientController();
    }

    @Override
    protected EntityDialog createDialog() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ClientsScreen.this);
        return new ClientDialog(frame, "Add client", ClientsScreen.this);
    }

    @Override
    public void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
        TypedQuery<ClientEntity> allClients = entityManager.createNamedQuery("ClientEntity.all", ClientEntity.class);

        for (ClientEntity c: allClients.getResultList()) {
            int id = c.getClientId();
            String name = String.valueOf(c.getName());
            String surname = String.valueOf(c.getSurname());

            model.addRow(new String[]{String.valueOf(id), name, surname});
        }
        entityManager.close();
    }



}


