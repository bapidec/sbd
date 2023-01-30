package screen;

import controller.ClientController;
import controller.EmployeeController;
import controller.EntityController;
import dialogs.*;
import entity.ClientEntity;
import entity.EmployeeEntity;
import entityFactory.DefaultEntityManagerFactory;
import iterator.ClientFilteredDataList;
import iterator.EmployeeFilteredDataList;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import view.ClientView;
import view.EmployeeView;
import view.EntityView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class EmployeeScreen extends Screen {


    public EmployeeScreen() {
        super();

        this.tablePanel.setBorder(BorderFactory.createTitledBorder("Employees table"));
        this.detailsPanel.setBorder(BorderFactory.createTitledBorder("Employees details"));


    }

    private static void noEmployeeSelectedWarning(JFrame frame) {
        JOptionPane.showMessageDialog(frame, "Please select employee first", "No employee selected", JOptionPane.WARNING_MESSAGE);
    }

    @Override
    protected void addAdditionalButtons(JPanel buttonsPanel) {
    }

    @Override
    protected EmployeeEntity getSelectedEntity() {
        int cId= Integer.valueOf(table.getValueAt(table.getSelectedRow(), 0).toString());
        String eName = (String) table.getValueAt(table.getSelectedRow(), 2);

        EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
        TypedQuery<EmployeeEntity> employeeById = entityManager.createNamedQuery("EmployeeEntity.byId", EmployeeEntity.class);

        employeeById.setParameter("employeeId", cId);

        return employeeById.getSingleResult();
    }

    @Override
    protected Object[][] fetchDataFromDatabase(int columnsNr) {
        List<String[]> rows = new ArrayList<>();

        EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
        TypedQuery<EmployeeEntity> allEmployees = entityManager.createNamedQuery("EmployeeEntity.all", EmployeeEntity.class);



        EmployeeFilteredDataList filteredResults = new EmployeeFilteredDataList(allEmployees.getResultList(), "name", "surname");
//        List filteredResults = allContracts.getResultList();

        for (Object c: filteredResults) {
            c = (EmployeeEntity) c;
            int id = ((EmployeeEntity) c).getEmployeeId();
            String name = String.valueOf(((EmployeeEntity) c).getName());
            String surname= ((EmployeeEntity) c).getSurname();
            rows.add(new String[]{String.valueOf(id), name, surname});
        }

        Object[][] data = new Object[rows.size()][columnsNr];

        for(int i = 0; i < rows.size(); i++) {
            data[i] = rows.get(i);
        }

        return data;
    }

    @Override
    protected String[] createColumnNames() {
        return new String[]{"Id", "First Name", "Last Name"};
    }

    @Override
    protected void deleteSelectedEntity() {
        EmployeeEntity selectedEmployee = (EmployeeEntity) selectedEntity;
        EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            Query deleteClientQuery = entityManager.createQuery("DELETE FROM EmployeeEntity c WHERE c.employeeId = :employeeId");
            deleteClientQuery.setParameter("employeeId", selectedEmployee.getEmployeeId());
            deleteClientQuery.executeUpdate();

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
        return new EmployeeView();
    }

    @Override
    protected EntityController createController() {
        return new EmployeeController();
    }

    @Override
    protected EntityDialog createAddDialog() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(EmployeeScreen.this);
        return new EmployeeDialog(frame, "Add employee", EmployeeScreen.this);
    }

    @Override
    protected EntityDialog createEditDialog() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(EmployeeScreen.this);
        return new EmployeeEditDialog(frame, "Edit employee", EmployeeScreen.this, (EmployeeEntity) this.selectedEntity);
    }
    @Override
    public void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
        TypedQuery<EmployeeEntity> allEmployees = entityManager.createNamedQuery("EmployeeEntity.all", EmployeeEntity.class);

        for (EmployeeEntity c: allEmployees.getResultList()) {
            int id = c.getEmployeeId();
            String name = String.valueOf(c.getName());
            String surname = String.valueOf(c.getSurname());

            model.addRow(new String[]{String.valueOf(id), name, surname});
        }
        entityManager.close();
    }



}


