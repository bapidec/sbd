package screen;

import dialogs.PlacesDialog;
import view.PlaceView;
import view.SupplierView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PlacesScreen extends JPanel{
    JTable placesTable;
    PlaceView placeView = new PlaceView();
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
    public PlacesScreen() {
        super(new GridLayout(0,2));

        createTable();
        createFilters();
        createDetails();
        this.tablePanel.setBorder(BorderFactory.createTitledBorder("Places table"));
        this.detailsPanel.setBorder(BorderFactory.createTitledBorder("Place details"));
        this.add(tablePanel);
        this.add(detailsPanel);

        this.addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(PlacesScreen.this);
                addButton.setEnabled(false);
                PlacesDialog placesDialog = new PlacesDialog(frame, addButton, "Add building");
            }
        });

        this.editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(PlacesScreen.this);
                editButton.setEnabled(false);
                PlacesDialog placesDialog = new PlacesDialog(frame, addButton, "Edit building");
            }
        });

    }

    private void createDetails() {
        this.detailsPanel.add(this.placeView);
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(this.addButton);
        buttonsPanel.add(this.deleteButton);
        buttonsPanel.add(this.editButton);
        this.detailsPanel.add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void createFilters() {
        JPanel filterByOrdersPanel = new JPanel(new GridLayout(0, 2));
        filterByOrdersPanel.add(this.filtersBox);
        filterByOrdersPanel.add(this.filterValueBox);

        this.filtersPanel.add(filterByOrdersPanel);
        this.filtersPanel.add(clearFiltersButton, BorderLayout.SOUTH);

        this.filtersPanel.setBorder(BorderFactory.createTitledBorder("Filter"));
        this.tablePanel.add(filtersPanel, BorderLayout.NORTH);
    }

    private void createTable() {
        String[] columnNames = {"Id", "Location", "Type"};
        List<String[]> rows = new ArrayList<>();

        rows.add(new String[]{String.valueOf(1), "Białystok", "Shop"});

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
        this.placesTable = new JTable(model);

        placesTable.setFillsViewportHeight(true);
        placesTable.setCellSelectionEnabled(false);
        placesTable.setRowSelectionAllowed(true);

        JScrollPane tablePane = new JScrollPane(this.placesTable);
        this.tablePanel.add(tablePane);
    }
}

