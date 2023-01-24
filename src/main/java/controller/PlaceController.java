package controller;

import entity.PlaceEntity;
import jakarta.persistence.Entity;
import view.EntityView;
import view.PlaceView;

public class PlaceController implements EntityController{
    private PlaceEntity placeEntity;
    private PlaceView placeView;
    private String employeeName;

    public PlaceController() {
        this.placeEntity = null;
        this.placeView = null;
    }



    public void updateView() {
        if(this.placeView == null || this.placeEntity == null) {
            return;
        }

        placeView.showPlacesDetails(
                String.valueOf(placeEntity.getLocation()),
                String.valueOf(placeEntity.getProductLimit()),
                String.valueOf(placeEntity.getEmployeeLimit()),
                String.valueOf(placeEntity.getMaintenanceCost()),
                String.valueOf(placeEntity.getSupplierSupplierId())

        );
    }

    @Override
    public void setEntity(Entity entity) {
        this.placeEntity = (PlaceEntity) entity;
    }

    @Override
    public void setView(EntityView view) {
        this.placeView = (PlaceView) view;
    }

}
