package screen;

import controller.ContractController;
import controller.EntityController;
import dialogs.ContractDialog;
import dialogs.ContractEditDialog;
import dialogs.EntityDialog;
import entity.ContractEntity;
import entityFactory.DefaultEntityManagerFactory;
import iterator.ContractFilteredDataList;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import view.ContractView;
import view.EntityView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

public class ContractScreen extends Screen {

    public ContractScreen() {
        super();

        this.tablePanel.setBorder(BorderFactory.createTitledBorder("Contracts table"));
        this.detailsPanel.setBorder(BorderFactory.createTitledBorder("Contract details"));

        fillFilters();

    }

    private void fillFilters() {
        filtersKeyBox.addItem("Type");
        filtersKeyBox.addItem("Place ID");
        filtersKeyBox.setSelectedItem(null);

        filtersKeyBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(filtersKeyBox.getSelectedItem() != null) {
                    if (filtersKeyBox.getSelectedItem().equals("Place ID") && filterValueBox instanceof JTextField) {
                        valueBoxToComboBox();
                        addPlaces();
                    }
                    if (filtersKeyBox.getSelectedItem().equals("Type") && filterValueBox instanceof JComboBox) {
                        valueBoxToTextField();
                    }
                }
            }
        });

    }

    private void valueBoxToComboBox() {
        filterKeyValuePanel.remove(filterValueBox);
        filterValueBox = new JComboBox<>();
        if (filterValueBox instanceof JComboBox) {
            ((JComboBox<?>) filterValueBox).setSelectedItem(null);
        }
        filterKeyValuePanel.add(filterValueBox);
        filterKeyValuePanel.revalidate();
    }
    private void valueBoxToTextField() {
        filterKeyValuePanel.remove(filterValueBox);
        filterValueBox = new JTextField();
        if (filterValueBox instanceof JTextField) {
            ((JTextField) filterValueBox).setText("");
        }
        filterKeyValuePanel.add(filterValueBox);
        filterKeyValuePanel.revalidate();
    }

    private void addPlaces() {
        if(filterValueBox instanceof JComboBox) {
            EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
            TypedQuery<Integer> placeIds = entityManager.createNamedQuery("PlaceEntity.ids", Integer.class);
            for (Integer i : placeIds.getResultList()) {
                System.out.println(i);
                ((JComboBox)filterValueBox).addItem(i);
            }
            ((JComboBox<?>) filterValueBox).setSelectedItem(null);
        }
    }
    private static void noContractSelectedWarning(JFrame frame) {
        JOptionPane.showMessageDialog(frame, "Please select contract first", "No contract selected", JOptionPane.WARNING_MESSAGE);
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
        List resultList = allContracts.getResultList();

        for (Object c: resultList) {
            c = (ContractEntity) c;
            int id = ((ContractEntity) c).getContractId();
            String date_start = String.valueOf(((ContractEntity) c).getDateStart());
            int employee_id = ((ContractEntity) c).getEmployeeEmployeeId();
            String employeeName = ((ContractEntity) c).getEmployeeName(entityManager);
            rows.add(new String[]{String.valueOf(id), date_start.substring(0, 10), employeeName});
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
    protected EntityDialog createAddDialog() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ContractScreen.this);
        return new ContractDialog(frame, "Add contract", ContractScreen.this);
    }

    @Override
    protected EntityDialog createEditDialog() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(ContractScreen.this);
        return new ContractEditDialog(frame, "Add contract", ContractScreen.this, (ContractEntity) this.selectedEntity);
    }

    @Override
    public void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
        List resultList = null;

        if(filtersKeyBox.getSelectedItem() == null) {
            TypedQuery<ContractEntity> allContracts = entityManager.createNamedQuery("ContractEntity.all", ContractEntity.class);
            resultList = allContracts.getResultList();
        } else if (filtersKeyBox.getSelectedItem() != null) {
            if(filterValueBox instanceof JTextField && !((JTextField) filterValueBox).getText().equals("")) {
                TypedQuery<ContractEntity> contractsByType = entityManager.createNamedQuery("ContractEntity.byType", ContractEntity.class);
                contractsByType.setParameter("type", ((JTextField) filterValueBox).getText());
                resultList = contractsByType.getResultList();
            } else if (filterValueBox instanceof JComboBox && ((JComboBox<?>) filterValueBox).getSelectedItem() != null) {
                TypedQuery<ContractEntity> contractsByPlaceId = entityManager.createNamedQuery("ContractEntity.byPlaceId", ContractEntity.class);
                contractsByPlaceId.setParameter("place_id", ((JComboBox<?>) filterValueBox).getSelectedItem());
                resultList = contractsByPlaceId.getResultList();
            } else {
                TypedQuery<ContractEntity> allContracts = entityManager.createNamedQuery("ContractEntity.all", ContractEntity.class);
                resultList = allContracts.getResultList();
            }
        }

        for (Object c: resultList) {
            c = (ContractEntity) c;
            int id = ((ContractEntity) c).getContractId();
            String date_start = String.valueOf(((ContractEntity) c).getDateStart());
            int employee_id = ((ContractEntity) c).getEmployeeEmployeeId();
            String employeeName = ((ContractEntity) c).getEmployeeName(entityManager);
            model.addRow(new String[]{String.valueOf(id), date_start.substring(0, 10), employeeName});
        }

        entityManager.close();
    }



}

