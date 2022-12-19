package screen;

import dialogs.ClientDialog;
import view.ImageView;
import view.PlaceView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ImagesScreen extends JPanel {
    JTable imagesTable;
    ImageView imageView = new ImageView();
    JComboBox filtersBox = new JComboBox<>();
    JComboBox filterValueBox = new JComboBox<>();
    JTextField filterValueField = new JTextField();
    JButton clearFiltersButton = new JButton("Clear");
    JButton addButton = new JButton("Add");
    JButton deleteButton = new JButton("Delete");

    JPanel tablePanel = new JPanel(new BorderLayout());
    JPanel filtersPanel = new JPanel(new BorderLayout());
    JPanel detailsPanel = new JPanel(new BorderLayout());
    public ImagesScreen() {
        super(new GridLayout(0,2));

        createTable();
        createFilters();
        createDetails();
        this.tablePanel.setBorder(BorderFactory.createTitledBorder("Images table"));
        this.detailsPanel.setBorder(BorderFactory.createTitledBorder("Image details"));
        this.add(tablePanel);
        this.add(detailsPanel);

        this.addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ImagesScreen.this);
                addButton.setEnabled(false);
                ClientDialog clientDialog = new ClientDialog(frame, addButton, "Add client");
            }
        });

    }

    private void createDetails() {
        this.detailsPanel.add(this.imageView);
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(this.addButton);
        buttonsPanel.add(this.deleteButton);
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
        this.imagesTable = new JTable(model);

        imagesTable.setFillsViewportHeight(true);
        imagesTable.setCellSelectionEnabled(false);
        imagesTable.setRowSelectionAllowed(true);

        JScrollPane tablePane = new JScrollPane(this.imagesTable);
        this.tablePanel.add(tablePane);
    }
}


