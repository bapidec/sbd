package screen;

import controller.EntityController;
import controller.PlaceController;
import dialogs.EntityDialog;
import dialogs.PlacesDialog;
import entity.PlaceEntity;
import entityFactory.DefaultEntityManagerFactory;
import iterator.PlaceFilteredDataList;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import view.EntityView;
import view.PlaceView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class PlacesScreen extends Screen {


    public PlacesScreen() {
        super();

        this.tablePanel.setBorder(BorderFactory.createTitledBorder("Places table"));
        this.detailsPanel.setBorder(BorderFactory.createTitledBorder("Places details"));


    }

    private static void noPlaceSelectedWarning(JFrame frame) {
        JOptionPane.showMessageDialog(frame, "Please select place first", "No place selected", JOptionPane.WARNING_MESSAGE);
    }

    @Override
    protected void addAdditionalButtons(JPanel buttonsPanel) {
    }

    @Override
    protected PlaceEntity getSelectedEntity() {
        int cId= Integer.valueOf(table.getValueAt(table.getSelectedRow(), 0).toString());
        String eName = (String) table.getValueAt(table.getSelectedRow(), 2);

        EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
        TypedQuery<PlaceEntity> placeById = entityManager.createNamedQuery("PlaceEntity.byId", PlaceEntity.class);

        placeById.setParameter("placeId", cId);

        return placeById.getSingleResult();
    }

    @Override
    protected Object[][] fetchDataFromDatabase(int columnsNr) {
        List<String[]> rows = new ArrayList<>();

        EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
        TypedQuery<PlaceEntity> allPlaces = entityManager.createNamedQuery("PlaceEntity.all", PlaceEntity.class);



        PlaceFilteredDataList filteredResults = new PlaceFilteredDataList(allPlaces.getResultList(), "Location", "Maintenance cost");
//        List filteredResults = allPlaces.getResultList();

        for (Object c: filteredResults) {
            c = (PlaceEntity) c;
            int id = ((PlaceEntity) c).getPlaceId();
            String location = String.valueOf(((PlaceEntity) c).getLocation());
            String maintenanceCost= String.valueOf(((PlaceEntity) c).getMaintenanceCost());
            rows.add(new String[]{String.valueOf(id), location, maintenanceCost});
        }

        Object[][] data = new Object[rows.size()][columnsNr];

        for(int i = 0; i < rows.size(); i++) {
            data[i] = rows.get(i);
        }

        return data;
    }

    @Override
    protected String[] createColumnNames() {
        return new String[]{"Id", "Location", "Maintenance Cost"};
    }

    @Override
    protected void deleteSelectedEntity() {
        PlaceEntity selectedPlace = (PlaceEntity) selectedEntity;
        EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            Query deletePlaceQuery = entityManager.createQuery("DELETE FROM PlaceEntity c WHERE c.placeId = :placeId");
            deletePlaceQuery.setParameter("placeId", selectedPlace.getPlaceId());
            deletePlaceQuery.executeUpdate();

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
        return new PlaceView();
    }

    @Override
    protected EntityController createController() {
        return new PlaceController();
    }

    @Override
    protected EntityDialog createDialog() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(PlacesScreen.this);
        return new PlacesDialog(frame, "Add place", PlacesScreen.this);
    }

    @Override
    public void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
        TypedQuery<PlaceEntity> allPlaces = entityManager.createNamedQuery("PlaceEntity.all", PlaceEntity.class);

        for (PlaceEntity c: allPlaces.getResultList()) {
            int id = c.getPlaceId();
            String location = String.valueOf(c.getLocation());
            String maintenanceCost = String.valueOf(c.getMaintenanceCost());

            model.addRow(new String[]{String.valueOf(id), location, maintenanceCost});
        }
        entityManager.close();
    }



}


