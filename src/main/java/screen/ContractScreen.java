package screen;

import contractBuilder.ContractBuilderHTML;
import contractBuilder.ContractBuilderPdf;
import controller.ContractController;
import dialogs.ContractDialog;
import dialogs.PlacesDialog;
import entity.ContractEntity;
import entityFactory.DefaultEntityManagerFactory;
import jakarta.persistence.*;
import view.ContractView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ContractScreen extends JPanel {
    JTable contractsTable;
    ContractView contractView = new ContractView();
    JComboBox filtersBox = new JComboBox<>();
    JComboBox filterValueBox = new JComboBox<>();
    JTextField filterValueField = new JTextField();
    JButton clearFiltersButton = new JButton("Clear");
    JButton addButton = new JButton("Add");
    JButton deleteButton = new JButton("Delete");
    JButton editButton = new JButton("Edit");
    JButton generateHTMLButton = new JButton("Generate HTML");
    JButton generatePdfButton = new JButton("Generate Pdf");

    JPanel tablePanel = new JPanel(new BorderLayout());
    JPanel filtersPanel = new JPanel(new BorderLayout());
    JPanel detailsPanel = new JPanel(new BorderLayout());
    private ContractEntity selectedContract;

    public ContractScreen() {
        super(new GridLayout(0,2));

        createTable();
        createFilters();
        createDetails();
        this.tablePanel.setBorder(BorderFactory.createTitledBorder("Contracts table"));
        this.detailsPanel.setBorder(BorderFactory.createTitledBorder("Contract details"));
        this.add(tablePanel);
        this.add(detailsPanel);

        this.addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ContractScreen.this);
                addButton.setEnabled(false);
                ContractDialog contractDialog = new ContractDialog(frame, "Add contract", ContractScreen.this);
            }
        });

        this.deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
                EntityTransaction entityTransaction = entityManager.getTransaction();

                try {
                    entityTransaction.begin();

                    Query deleteContractQuery = entityManager.createQuery("DELETE FROM ContractEntity c WHERE c.contractId = :contractId");
                    deleteContractQuery.setParameter("contractId", selectedContract.getContractId());
                    deleteContractQuery.executeUpdate();

                    entityTransaction.commit();

                    entityManager.close();

                    contractsTable.clearSelection();
                    refreshTable();
                } finally {
                    if(entityTransaction.isActive()) {
                        entityTransaction.rollback();
                    }
                    entityManager.close();
                }
            }
        });

        this.editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ContractScreen.this);
                editButton.setEnabled(false);
                new PlacesDialog(frame, addButton, "Edit building");
            }
        });

        this.generateHTMLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ContractScreen.this);
                if(selectedContract == null) {
                    noContractSelectedWarning(frame);
                    return;
                }
                ContractBuilderHTML contractBuilder = new ContractBuilderHTML(frame, selectedContract);
                contractBuilder.buildTitle();
                contractBuilder.buildContents();
                contractBuilder.buildSignature("Janusz Szef");
                contractBuilder.getHTMLDocument();
            }
        });

        this.generatePdfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ContractScreen.this);
                if(selectedContract == null) {
                    noContractSelectedWarning(frame);
                    return;
                }

                ContractBuilderPdf contractBuilder = new ContractBuilderPdf(frame, selectedContract);
                contractBuilder.buildTitle();
                contractBuilder.buildContents();
                contractBuilder.buildSignature("Janusz Szef");

                contractBuilder.getPdfDocument();
            }
        });

    }

    private static void noContractSelectedWarning(JFrame frame) {
        JOptionPane.showMessageDialog(frame, "Please select contract first", "No contract selected", JOptionPane.WARNING_MESSAGE);
    }

    public JButton getAddButton() {
        return addButton;
    }

    private void createDetails() {
        this.detailsPanel.add(this.contractView);
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(this.addButton);
        buttonsPanel.add(this.deleteButton);
        buttonsPanel.add(this.editButton);
        buttonsPanel.add(this.generateHTMLButton);
        buttonsPanel.add(this.generatePdfButton);
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
        String[] columnNames = {"Id", "Start date", "Employee"};
        List<String[]> rows = new ArrayList<>();

        EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
        TypedQuery<ContractEntity> allContracts = entityManager.createNamedQuery("ContractEntity.all", ContractEntity.class);

        for (ContractEntity c: allContracts.getResultList()) {
            int id = c.getContractId();
            String date_start = String.valueOf(c.getDateStart());
            int employee_id = c.getEmployeeEmployeeId();
            String employeeName = c.getEmployeeName(entityManager);
            rows.add(new String[]{String.valueOf(id), date_start, employeeName});
        }

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
        this.contractsTable = new JTable(model);

        contractsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                if(contractsTable.getSelectedRow() == -1)
                {
                    contractView.clear();
                    return;
                }
                int cId= Integer.valueOf(contractsTable.getValueAt(contractsTable.getSelectedRow(), 0).toString());
                String eName = (String) contractsTable.getValueAt(contractsTable.getSelectedRow(), 2);

                EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
                TypedQuery<ContractEntity> contractById = entityManager.createNamedQuery("ContractEntity.byId", ContractEntity.class);

                contractById.setParameter("contractId", cId);

                selectedContract = contractById.getSingleResult();

                ContractController contractController = new ContractController(selectedContract,contractView, eName);
                contractController.updateContractView();
            }
        });

        contractsTable.setFillsViewportHeight(true);
        contractsTable.setCellSelectionEnabled(false);
        contractsTable.setRowSelectionAllowed(true);

        JScrollPane tablePane = new JScrollPane(this.contractsTable);
        this.tablePanel.add(tablePane);
    }

    public void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) contractsTable.getModel();
        model.setRowCount(0);

        EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
        TypedQuery<ContractEntity> allContracts = entityManager.createNamedQuery("ContractEntity.all", ContractEntity.class);

        for (ContractEntity c: allContracts.getResultList()) {
            int id = c.getContractId();
            String date_start = String.valueOf(c.getDateStart());
            int employee_id = c.getEmployeeEmployeeId();
            TypedQuery<String> contractEmployeeQuery = entityManager.createNamedQuery("EmployeeEntity.nameById", String.class);

            contractEmployeeQuery.setParameter("employee_id", employee_id);

            String employeeName = String.valueOf(contractEmployeeQuery.getSingleResult());
            model.addRow(new String[]{String.valueOf(id), date_start, employeeName});
        }
        entityManager.close();
    }

}

