package screen;

import dialogs.ClientDialog;
import dialogs.DialogFactory;
import view.ClientView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ClientsScreen extends JPanel {
    JTable clientsTable;
    ClientView clientView = new ClientView();
    JComboBox filtersBox = new JComboBox<>();
    JComboBox filterValueBox = new JComboBox<>();
    JTextField filterValueField = new JTextField();
    JButton clearFiltersButton = new JButton("Clear");
    JButton addButton = new JButton("Add");
    JButton deleteButton = new JButton("Delete");
    JButton editButton = new JButton("Edit");

    JPanel tablePanel = new JPanel(new BorderLayout());
    JPanel filtersPanel = new JPanel(new BorderLayout());
    JPanel detailsPanel = new JPanel(new BorderLayout());
    public ClientsScreen() {
        super(new GridLayout(0,2));

        createTable();
        createFilters();
        createDetails();
        this.tablePanel.setBorder(BorderFactory.createTitledBorder("Clients table"));
        this.detailsPanel.setBorder(BorderFactory.createTitledBorder("Client details"));
        this.add(tablePanel);
        this.add(detailsPanel);

        this.addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ClientsScreen.this);
                addButton.setEnabled(false);
                DialogFactory clientAddDialog = new DialogFactory();
                clientAddDialog.setTitle("New Client");
                clientAddDialog.setButton(addButton);
                clientAddDialog.setFrame(frame);
                clientAddDialog.getDialog("Client");
            }
        });

        this.editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ClientsScreen.this);
                editButton.setEnabled(false);
                ClientDialog clientDialog = new ClientDialog(frame, editButton, "Edit client");
            }
        });

    }

    private void createDetails() {
        this.detailsPanel.add(this.clientView);
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(this.addButton);
        buttonsPanel.add(this.deleteButton);
        buttonsPanel.add(this.editButton);
        this.detailsPanel.add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void createFilters() {
        JPanel filterByOrdersPanel = new JPanel(new GridLayout(0, 2));
        filterByOrdersPanel.add(this.filtersBox);
        filterByOrdersPanel.add(this.filterValueField);

        this.filtersPanel.add(filterByOrdersPanel);
        this.filtersPanel.add(clearFiltersButton, BorderLayout.SOUTH);

        this.filtersPanel.setBorder(BorderFactory.createTitledBorder("Filter"));
        this.tablePanel.add(filtersPanel, BorderLayout.NORTH);
    }

    private void createTable() {
        String[] columnNames = {"Id", "First name", "Last name"};
        List<String[]> rows = new ArrayList<>();

        rows.add(new String[]{String.valueOf(1), "Bartek", "Dec", "gmail", "666666666", "bialystok"});

        Object[][] data = new Object[rows.size()][columnNames.length];

        for(int i = 0; i < rows.size(); i++) {
            data[i] = rows.get(i);
        }
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.clientsTable = new JTable(model);

        clientsTable.setFillsViewportHeight(true);
        clientsTable.setCellSelectionEnabled(false);
        clientsTable.setRowSelectionAllowed(true);

        JScrollPane tablePane = new JScrollPane(this.clientsTable);
        this.tablePanel.add(tablePane);
    }
}
