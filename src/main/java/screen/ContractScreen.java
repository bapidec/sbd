package screen;

import contractBuilder.ContractBuilderHTML;
import contractBuilder.ContractBuilderPdf;
import controller.ContractController;
import controller.EntityController;
import dialogs.ContractDialog;
import dialogs.EntityDialog;
import entity.ContractEntity;
import entityFactory.DefaultEntityManagerFactory;
import iterator.ContractFilteredDataList;
import jakarta.persistence.*;
import view.ContractView;
import view.EntityView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContractScreen extends Screen {
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
                if(selectedEntity == null) {
                    noContractSelectedWarning(frame);
                    return;
                }
                ContractBuilderHTML contractBuilder = new ContractBuilderHTML(frame, (ContractEntity) selectedEntity);
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
                if(selectedEntity == null) {
                    noContractSelectedWarning(frame);
                    return;
                }

                ContractBuilderPdf contractBuilder = new ContractBuilderPdf(frame, (ContractEntity) selectedEntity);
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
    protected ContractEntity getSelectedEntity() {
        int cId= Integer.valueOf(table.getValueAt(table.getSelectedRow(), 0).toString());
        String eName = (String) table.getValueAt(table.getSelectedRow(), 2);

        EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
        TypedQuery<ContractEntity> contractById = entityManager.createNamedQuery("ContractEntity.byId", ContractEntity.class);

        contractById.setParameter("contractId", cId);

        return contractById.getSingleResult();
    }

    @Override
    protected Object[][] fetchDataFromDatabase(int columnsNr) {
        List<String[]> rows = new ArrayList<>();

        EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
        TypedQuery<ContractEntity> allContracts = entityManager.createNamedQuery("ContractEntity.all", ContractEntity.class);



        ContractFilteredDataList filteredResults = new ContractFilteredDataList(allContracts.getResultList(), "type", "job");
//        List filteredResults = allContracts.getResultList();

        for (Object c: filteredResults) {
            c = (ContractEntity) c;
            int id = ((ContractEntity) c).getContractId();
            String date_start = String.valueOf(((ContractEntity) c).getDateStart());
            int employee_id = ((ContractEntity) c).getEmployeeEmployeeId();
            String employeeName = ((ContractEntity) c).getEmployeeName(entityManager);
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
        ContractEntity selectedContract = (ContractEntity) selectedEntity;
        EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            Query deleteContractQuery = entityManager.createQuery("DELETE FROM ContractEntity c WHERE c.contractId = :contractId");
            deleteContractQuery.setParameter("contractId", selectedContract.getContractId());
            deleteContractQuery.executeUpdate();

            entityTransaction.commit();

            entityManager.close();

            table.clearSelection();
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
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ContractScreen.this);
        return new ContractDialog(frame, "Add contract", ContractScreen.this);
    }

    @Override
    public void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
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

