import entity.ClientEntity;
import jakarta.persistence.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void reloadData(JTable table, EntityManager entityManager) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        TypedQuery<ClientEntity> allClients = entityManager.createNamedQuery("ClientEntity.all", ClientEntity.class);

        for (ClientEntity c: allClients.getResultList()) {
            int id = c.getClientId();
            String firstName = c.getName();
            String lastName = c.getSurname();
            String email = c.getEmail();
            String phone = c.getPhoneNr();
            String address = c.getAddress();
            model.addRow(new String[]{String.valueOf(id), firstName, lastName, email, phone, address});

        }


    }
    public static void testGUI(EntityManager entityManager, EntityManagerFactory entityManagerFactory) {
        JFrame mainFrame = new JFrame();
        mainFrame.setTitle("FIGURKI");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        JPanel mainPanel = new JPanel(new BorderLayout());

        String[] columnNames = {"Id", "First name", "Last name", "Email", "Phone nr", "Address"};
        List<String[]> rows = new ArrayList<>();

        TypedQuery<ClientEntity> allClients = entityManager.createNamedQuery("ClientEntity.all", ClientEntity.class);

        for (ClientEntity c: allClients.getResultList()) {
            int id = c.getClientId();
            String firstName = c.getName();
            String lastName = c.getSurname();
            String email = c.getEmail();
            String phone = c.getPhoneNr();
            String address = c.getAddress();
            rows.add(new String[]{String.valueOf(id), firstName, lastName, email, phone, address});
        }

        Object[][] data = new Object[rows.size()][columnNames.length];

        for(int i = 0; i < rows.size(); i++) {
            data[i] = rows.get(i);
        }
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable clientsTable = new JTable(model);

        JScrollPane tablePane = new JScrollPane(clientsTable);
        mainPanel.add(tablePane);

        JPanel inputPanel = new JPanel(new BorderLayout());

        GridLayout inputFieldsLayout = new GridLayout(0, 2);
        inputFieldsLayout.setVgap(1);
        JPanel inputFieldsPanel = new JPanel(inputFieldsLayout);

        TextField nameField = new TextField();
        TextField surnameField = new TextField();
        TextField emailField = new TextField();
        TextField phoneField = new TextField();
        TextField addressField = new TextField();

        inputFieldsPanel.add(new JLabel("Name: "));
        inputFieldsPanel.add(nameField);
        inputFieldsPanel.add(new JLabel("Surname: "));
        inputFieldsPanel.add(surnameField);
        inputFieldsPanel.add(new JLabel("Email: "));
        inputFieldsPanel.add(emailField);
        inputFieldsPanel.add(new JLabel("Phone: "));
        inputFieldsPanel.add(phoneField);
        inputFieldsPanel.add(new JLabel("Address: "));
        inputFieldsPanel.add(addressField);

        inputPanel.add(inputFieldsPanel);

        Button addButton = new Button("ADD");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EntityTransaction transaction = entityManager.getTransaction();

                try {
                    transaction.begin();

                    ClientEntity newClient = new ClientEntity();
                    newClient.setName(nameField.getText());
                    newClient.setSurname(surnameField.getText());
                    newClient.setEmail(emailField.getText());
                    newClient.setPhoneNr(phoneField.getText());
                    newClient.setAddress(addressField.getText());

                    entityManager.persist(newClient);

                    reloadData(clientsTable, entityManager);

                    transaction.commit();
                } finally {
                    if(transaction.isActive()) {
                        transaction.rollback();
                    }
                    entityManager.close();
                    entityManagerFactory.close();
                }

            }
        });

        inputPanel.add(addButton, BorderLayout.PAGE_END);

        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        mainFrame.add(mainPanel);
        mainFrame.pack();

    }
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        testGUI(entityManager, entityManagerFactory);

    }
}
