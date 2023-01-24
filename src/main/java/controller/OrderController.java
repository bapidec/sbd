package controller;

import entity.EmployeeEntity;
import entity.OrderEntity;
import entityFactory.DefaultEntityManagerFactory;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import view.ContractView;
import view.EmployeeView;
import view.EntityView;
import view.OrderView;

public class OrderController implements EntityController{
    private OrderEntity orderEntity;
    private OrderView orderView;
    private String orderName;

    public OrderController() {
        this.orderEntity = null;
        this.orderView = null;
    }



    public void updateView() {
        if(this.orderView == null || this.orderEntity == null) {
            return;
        }
        orderView.showOrderDetails(
                String.valueOf(orderEntity.getClientClientId()),
                String.valueOf(orderEntity.getAddress()),
                String.valueOf(orderEntity.getStartDate()),
                String.valueOf(orderEntity.getEndDate()),
                String.valueOf(orderEntity.getOrderId())
        );
    }

    @Override
    public void setEntity(Entity entity) {
        this.orderEntity = (OrderEntity) entity;
    }

    @Override
    public void setView(EntityView view) {
        this.orderView = (OrderView) view;
    }

}
