package screen;

import dialogs.EmployeeDialog;
import view.EmployeeView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class EmployeeScreen extends JPanel {
    JTable employeeTable;
    EmployeeView employeeView = new EmployeeView();
    JComboBox filtersBox = new JComboBox<>();
    JComboBox filterValueBox = new JComboBox<>();
    JTextField filterValueField = new JTextField();
    JButton clearFiltersButton = new JButton("Clear");
    JButton addButton = new JButton("Add");
    JButton deleteButton = new JButton("Delete");
    JButton editButton = new JButton("Edit");
    JButton contractButton = new JButton("Fill contract");

    JPanel tablePanel = new JPanel(new BorderLayout());
    JPanel filtersPanel = new JPanel(new BorderLayout());
    JPanel detailsPanel = new JPanel(new BorderLayout());

    public EmployeeScreen(){
        super(new GridLayout(0, 2));

        createTable();
        createFilters();
        createDetails();
        this.tablePanel.setBorder(BorderFactory.createTitledBorder("Employee table"));
        this.detailsPanel.setBorder(BorderFactory.createTitledBorder("Employee details"));
        this.add(tablePanel);
        this.add(detailsPanel);

        this.contractButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addButton.setEnabled(false);
            }
        });
        this.addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(EmployeeScreen.this);
                addButton.setEnabled(false);
                EmployeeDialog employeeDialog = new EmployeeDialog(frame, addButton, "Add employee");

            }
        });
        this.editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(EmployeeScreen.this);
                editButton.setEnabled(false);
                EmployeeDialog employeeDialog = new EmployeeDialog(frame, editButton, "Edit employee");
            }
        });
    }
    public void createFilters(){
        JPanel filterByOrdersPanel = new JPanel(new GridLayout(0, 2));
        filterByOrdersPanel.add(this.filtersBox);
        filterByOrdersPanel.add(this.filterValueBox);

        this.filtersPanel.add(filterByOrdersPanel);
        this.filtersPanel.add(clearFiltersButton, BorderLayout.SOUTH);

        this.filtersPanel.setBorder(BorderFactory.createTitledBorder("Filter"));
        this.tablePanel.add(filtersPanel, BorderLayout.NORTH);
    }
    public void createDetails(){
        this.detailsPanel.add(this.employeeView);
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(this.addButton);
        buttonsPanel.add(this.deleteButton);
        buttonsPanel.add(this.editButton);
        buttonsPanel.add(this.contractButton);
        this.detailsPanel.add(buttonsPanel, BorderLayout.SOUTH);
    }
    public void createTable(){
        String[] columnNames = {"Id", "First name", "Last name"};//, "Sex", "Email", "Phone number", "Date of birth"};
        List<String[]> rows = new ArrayList<>();

        rows.add(new String[]{String.valueOf(1), "Marcin", "Lapinski"});//, "Male", "siemka", "372902", "06-34-2002"});

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
        this.employeeTable = new JTable(model);

        employeeTable.setFillsViewportHeight(true);
        employeeTable.setCellSelectionEnabled(false);
        employeeTable.setRowSelectionAllowed(true);

        JScrollPane tablePane = new JScrollPane(this.employeeTable);
        this.tablePanel.add(tablePane);
    }
}
