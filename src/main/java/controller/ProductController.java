package controller;

import entity.ClientEntity;
import entity.ProductEntity;
import jakarta.persistence.Entity;
import view.ClientView;
import view.EntityView;
import view.ProductsView;

public class ProductController implements EntityController{
    private ProductEntity productEntity;
    private ProductsView productView;
    private String productName;

    public ProductController() {
        this.productEntity = null;
        this.productView = null;
    }



    public void updateView() {
        if(this.productView == null || this.productEntity == null) {
            return;
        }

        productView.showProductDetails(
                String.valueOf(productEntity.getName()),
                String.valueOf(productEntity.getType()),
                String.valueOf(productEntity.getPrice()),
                String.valueOf(productEntity.getDescription()),
                String.valueOf(productEntity.getDateOfProduction()),
                String.valueOf(productEntity.getDiscountDiscountId()),
                String.valueOf(productEntity.getGenre()),
                String.valueOf(productEntity.getKeywords())

        );
    }

    @Override
    public void setEntity(Entity entity) {this.productEntity = (ProductEntity) entity;
    }

    @Override
    public void setView(EntityView view) {
        this.productView = (ProductsView) view;
    }

}
