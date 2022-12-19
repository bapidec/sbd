package screen;

import view.ContractView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ContractScreen extends JPanel {
    JTable contractTable;
    ContractView contractView = new ContractView();
    //JComboBox placesBox = new JComboBox<>();
    JButton clearFiltersButton = new JButton("Clear");
    JButton addButton = new JButton("Add");
    JButton deleteButton = new JButton("Delete");
    JButton editButton = new JButton("Edit");

    JPanel tablePanel = new JPanel(new BorderLayout());
    JPanel filtersPanel = new JPanel(new BorderLayout());
    JPanel detailsPanel = new JPanel(new BorderLayout());

    public ContractScreen(){
        super(new GridLayout(0, 2));

        createDetails();
        createTable();
        this.tablePanel.setBorder(BorderFactory.createTitledBorder("Contract table"));
        this.detailsPanel.setBorder(BorderFactory.createTitledBorder("Contract details"));
        this.add(tablePanel);
        this.add(detailsPanel);
    }
    public void createDetails(){
        this.detailsPanel.add(this.contractView);
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(this.addButton);
        buttonsPanel.add(this.deleteButton);
        buttonsPanel.add(this.editButton);
        this.detailsPanel.add(buttonsPanel, BorderLayout.SOUTH);

    }
    public void createTable(){
        String[] columnNames = {"Contract ID", "Type of job", "Employee ID"};
        List<String[]> rows = new ArrayList<>();

        rows.add(new String[]{String.valueOf(1), "Cashier", "997"});//, "Male", "siemka", "372902", "06-34-2002"});

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
        this.contractTable = new JTable(model);

        contractTable.setFillsViewportHeight(true);
        contractTable.setCellSelectionEnabled(false);
        contractTable.setRowSelectionAllowed(true);

        JScrollPane tablePane = new JScrollPane(this.contractView);
        this.tablePanel.add(tablePane);
    }
}
