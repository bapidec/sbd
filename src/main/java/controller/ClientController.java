package controller;

import entity.ClientEntity;
import entity.ContractEntity;
import entity.EmployeeEntity;
import entityFactory.DefaultEntityManagerFactory;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import view.ClientView;
import view.ContractView;
import view.EntityView;

public class ClientController implements EntityController{
    private ClientEntity clientEntity;
    private ClientView clientView;
    private String employeeName;

    public ClientController() {
        this.clientEntity = null;
        this.clientView = null;
    }



    public void updateView() {
        if(this.clientView == null || this.clientEntity == null) {
            return;
        }

        clientView.showClientsDetails(
                String.valueOf(clientEntity.getName()),
                String.valueOf(clientEntity.getSurname()),
                String.valueOf(clientEntity.getPhoneNr()),
                String.valueOf(clientEntity.getEmail()),
                String.valueOf(clientEntity.getAddress())

        );
    }

    @Override
    public void setEntity(Entity entity) {
        this.clientEntity = (ClientEntity) entity;
    }

    @Override
    public void setView(EntityView view) {
        this.clientView = (ClientView) view;
    }

}