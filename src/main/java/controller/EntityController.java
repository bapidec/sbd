package controller;

import jakarta.persistence.Entity;
import view.EntityView;

public interface EntityController {
    public void updateView();
    public void setEntity(Entity entity);
    public void setView(EntityView view);
}
