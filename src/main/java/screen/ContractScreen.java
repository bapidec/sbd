package screen;

import contractBuilder.ContractBuilderHTML;
import contractBuilder.ContractBuilderPdf;
import controller.ContractController;
import controller.EntityController;
import dialogs.EntityDialog;
import entity.ContractEntity;
import entityFactory.DefaultEntityManagerFactory;
import jakarta.persistence.*;
import view.ContractView;
import view.EntityView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ContractScreen extends Screen {
    JTable contractsTable;
    ContractView contractView = new ContractView();
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
    private ContractEntity selectedContract;
    private JButton generatePdfButton;
    private JButton generateHTMLButton;

    public ContractScreen() {
        super();

        this.tablePanel.setBorder(BorderFactory.createTitledBorder("Contracts table"));
        this.detailsPanel.setBorder(BorderFactory.createTitledBorder("Contract details"));

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

    @Override
    protected void addAdditionalButtons(JPanel buttonsPanel) {
        this.generateHTMLButton = new JButton("Generate HTML");
        this.generatePdfButton = new JButton("Generate Pdf");
    }

    @Override
    protected Entity getSelectedEntity() {
        int cId= Integer.valueOf(contractsTable.getValueAt(contractsTable.getSelectedRow(), 0).toString());
        String eName = (String) contractsTable.getValueAt(contractsTable.getSelectedRow(), 2);

        EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
        TypedQuery<ContractEntity> contractById = entityManager.createNamedQuery("ContractEntity.byId", ContractEntity.class);

        contractById.setParameter("contractId", cId);

        return (Entity) contractById.getSingleResult();
    }

    @Override
    protected Object[][] fetchDataFromDatabase(int columnsNr) {
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

        Object[][] data = new Object[rows.size()][columnsNr];

        for(int i = 0; i < rows.size(); i++) {
            data[i] = rows.get(i);
        }

        return data;
    }

    @Override
    protected String[] createColumnNames() {
        return new String[]{"Id", "Start date", "Employee"};
    }

    @Override
    protected void deleteSelectedEntity() {
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

    @Override
    protected EntityView createView() {

        return new ContractView();
    }

    @Override
    protected EntityController createController() {

        return new ContractController();
    }

    @Override
    protected EntityDialog createDialog() {

        return new ;
    }

    @Override
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

