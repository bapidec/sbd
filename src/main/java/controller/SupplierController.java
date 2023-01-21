package controller;

import entity.SupplierEntity;
import jakarta.persistence.Entity;
import view.EntityView;
import view.SupplierView;

public class SupplierController implements EntityController{
    private SupplierEntity supplierEntity;
    private SupplierView supplierView;
    private String supplierName;

    public SupplierController() {
        this.supplierEntity = null;
        this.supplierView = null;
    }



    public void updateView() {
        if(this.supplierView == null || this.supplierEntity == null) {
            return;
        }

       supplierView.showSuppliersDetails(
                String.valueOf(supplierEntity.getName()),
                String.valueOf(supplierEntity.getVehicleNumber()),
                String.valueOf(supplierEntity.getStartDate()),
                String.valueOf(supplierEntity.getEndDate())

        );
    }

    @Override
    public void setEntity(Entity entity) {
        this.supplierEntity = (SupplierEntity) entity;
    }

    @Override
    public void setView(EntityView view) {
        this.supplierView = (SupplierView) view;
    }

}
