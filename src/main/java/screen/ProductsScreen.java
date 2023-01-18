package screen;

import dialogs.DialogFactory;
import dialogs.ShowImagesDialog;
import dialogs.ProductDialog;
import view.ProductsView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ProductsScreen extends JPanel{
    JTable productsTable;
    ProductsView productsView = new ProductsView();
    JComboBox filtersBox = new JComboBox<>();
    JComboBox filterValueBox = new JComboBox<>();
    JTextField filterValueField = new JTextField();
    JButton clearFiltersButton = new JButton("Clear");
    JButton addButton = new JButton("Add");
    JButton deleteButton = new JButton("Delete");
    JButton editButton = new JButton("Edit");
    JButton imagesButton = new JButton("Show images");
    JButton discountButton = new JButton("Set discount");
    JPanel tablePanel = new JPanel(new BorderLayout());
    JPanel filtersPanel = new JPanel(new BorderLayout());
    JPanel detailsPanel = new JPanel(new BorderLayout());

    public ProductsScreen() {
        super(new GridLayout(0,2));

        createTable();
        createFilters();
        createDetails();
        this.tablePanel.setBorder(BorderFactory.createTitledBorder("Products table"));
        this.detailsPanel.setBorder(BorderFactory.createTitledBorder("Product details"));
        this.add(tablePanel);
        this.add(detailsPanel);

        this.addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ProductsScreen.this);
                addButton.setEnabled(false);
                DialogFactory productAddDialog = new DialogFactory();
                productAddDialog.setTitle("New Product");
                productAddDialog.setButton(addButton);
                productAddDialog.setFrame(frame);
                productAddDialog.getDialog("Product");
            }
        });
        this.editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ProductsScreen.this);
                editButton.setEnabled(false);
                ProductDialog productDialog = new ProductDialog(frame, "Edit product");
            }
        });
        this.imagesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ProductsScreen.this);
                editButton.setEnabled(false);
                ShowImagesDialog images = new ShowImagesDialog(frame);
            }
        });
    }

    private void createDetails() {
        this.detailsPanel.add(this.productsView);
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(this.addButton);
        buttonsPanel.add(this.deleteButton);
        buttonsPanel.add(this.editButton);
        buttonsPanel.add(this.imagesButton);
        buttonsPanel.add(this.discountButton);
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
        String[] columnNames = {"Id", "Name", "type", "Price"};
        List<String[]> rows = new ArrayList<>();

        rows.add(new String[]{String.valueOf(1), "Baby Yoda", "Plushie", "99$"});
        rows.add(new String[]{String.valueOf(2), "Uno", "Card Game", "5$"});

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
        this.productsTable = new JTable(model);

        productsTable.setFillsViewportHeight(true);
        productsTable.setCellSelectionEnabled(false);
        productsTable.setRowSelectionAllowed(true);

        JScrollPane tablePane = new JScrollPane(this.productsTable);
        this.tablePanel.add(tablePane);
    }

}
