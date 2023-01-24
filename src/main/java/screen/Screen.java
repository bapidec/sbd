package screen;

import controller.EntityController;
import dialogs.EntityDialog;
import jakarta.persistence.Entity;
import view.EntityView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class Screen extends JPanel {

    protected JPanel tablePanel;
    protected JTable table;
    protected Entity selectedEntity;
    protected EntityView view;
    protected JPanel detailsPanel;
    protected JButton addButton;
    protected JButton deleteButton;
    protected JButton editButton;
    protected JComboBox filtersKeyBox;
    protected JComboBox filterValueBox;
    protected JPanel filtersPanel;
    protected JButton clearFiltersButton;

    public Screen() {
        super(new GridLayout(0,2));

        createDetails();
        createTable();
        createFilters();
        refreshTable();
        this.add(tablePanel);
        this.add(this.detailsPanel);


        this.addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(Screen.this);
                addButton.setEnabled(false);
                EntityDialog addDialog = createDialog();
            }
        });

        this.deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectedEntity == null)
                    return;
                deleteSelectedEntity();
            }
        });

        this.editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(Screen.this);
                editButton.setEnabled(false);
                //edit dialog
            }
        });

        editButton.setEnabled(false);

    }

    public JButton getAddButton() {
        return addButton;
    }
    protected void createDetails() {
        this.view = this.createView();
        this.detailsPanel = new JPanel(new BorderLayout());
        this.detailsPanel.add((JPanel) this.view);

        JPanel buttonsPanel = new JPanel();
        this.addButton = new JButton("Add");
        this.deleteButton = new JButton("Delete");
        this.editButton = new JButton("Edit");
        buttonsPanel.add(this.addButton);
        buttonsPanel.add(this.deleteButton);
        buttonsPanel.add(this.editButton);

        this.addAdditionalButtons(buttonsPanel);

        this.detailsPanel.add(buttonsPanel, BorderLayout.SOUTH);
    }

    protected void createTable() {
        this.tablePanel = new JPanel(new BorderLayout());
        this.tablePanel.setBorder(BorderFactory.createTitledBorder("Contracts table"));

        String[] columnNames = createColumnNames();
        Object[][] data = fetchDataFromDatabase(columnNames.length);
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.table = new JTable(model);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                if(table.getSelectedRow() == -1)
                {
                    view.clear();
                    return;
                }

                selectedEntity = getSelectedEntity();

                EntityController controller = createController();
                controller.setEntity(selectedEntity);
                controller.setView(view);
                controller.updateView();
            }
        });

        table.setFillsViewportHeight(true);
        table.setCellSelectionEnabled(false);
        table.setRowSelectionAllowed(true);

        JScrollPane tablePane = new JScrollPane(this.table);
        this.tablePanel.add(tablePane);

        this.add(tablePanel);
    }

    protected void createFilters() {
        this.filtersPanel = new JPanel(new BorderLayout());
        this.clearFiltersButton = new JButton("Clear");
        this.filtersKeyBox = new JComboBox<>();
        this.filterValueBox = new JComboBox<>();

        JPanel filterByOrdersPanel = new JPanel(new GridLayout(0, 2));
        filterByOrdersPanel.add(this.filtersKeyBox);
        filterByOrdersPanel.add(this.filterValueBox);

        this.filtersPanel.add(filterByOrdersPanel);
        this.filtersPanel.add(clearFiltersButton, BorderLayout.SOUTH);

        this.filtersPanel.setBorder(BorderFactory.createTitledBorder("Filter"));
        this.tablePanel.add(filtersPanel, BorderLayout.NORTH);
    }

    protected abstract void refreshTable();

    // metody szablonowe
    protected abstract void addAdditionalButtons(JPanel buttonsPanel);
    protected abstract Entity getSelectedEntity();
    protected abstract Object[][] fetchDataFromDatabase(int length);
    protected abstract String[] createColumnNames();
    protected abstract void deleteSelectedEntity();

    // metody fabrykujące
    protected abstract EntityView createView();
    protected abstract EntityController createController();
    protected abstract EntityDialog createDialog();


}
