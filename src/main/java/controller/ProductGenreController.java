package controller;

import entity.PlaceEntity;
import entity.ProductGenreEntity;
import jakarta.persistence.Entity;
import view.EntityView;
import view.PlaceView;
import view.ProductGenreView;

public class ProductGenreController implements EntityController{
    private ProductGenreEntity productGenreEntity;
    private ProductGenreView productGenreView;
    private String productGenreName;

    public ProductGenreController() {
        this.productGenreEntity = null;
        this.productGenreView = null;
    }



    public void updateView() {
        if(this.productGenreView == null || this.productGenreEntity == null) {
            return;
        }

        productGenreView.showProductGenreDetails(
                String.valueOf(productGenreEntity.getName()),
                String.valueOf(productGenreEntity.getDescription())
        );
    }

    @Override
    public void setEntity(Entity entity) {
        this.productGenreEntity = (ProductGenreEntity) entity;
    }

    @Override
    public void setView(EntityView view) {
        this.productGenreView = (ProductGenreView) view;
    }

}
