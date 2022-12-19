package screen;

import view.ClientView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClientsScreen extends JPanel {
    JTable clientsTable;
    ClientView clientView = new ClientView();
    JComboBox ordersList = new JComboBox<>();

    JPanel tablePanel = new JPanel(new BorderLayout());
    JPanel filtersPanel = new JPanel(new GridLayout(0, 2));
    public ClientsScreen() {
        super();

        createTable();
        createFilters();
        this.add(tablePanel);
        this.add(clientView);

    }

    private void createFilters() {
        this.filtersPanel.add(new JLabel("Order"));
        this.filtersPanel.add(this.ordersList);
        this.tablePanel.add(filtersPanel, BorderLayout.SOUTH);
    }

    private void createTable() {
        String[] columnNames = {"Id", "First name", "Last name", "Email", "Phone nr", "Address"};
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
