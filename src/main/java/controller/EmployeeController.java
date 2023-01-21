package controller;

import entity.EmployeeEntity;
import entityFactory.DefaultEntityManagerFactory;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import view.ContractView;
import view.EmployeeView;
import view.EntityView;

public class EmployeeController implements EntityController{
    private EmployeeEntity employeeEntity;
    private EmployeeView employeeView;
    private String employeeName;

    public EmployeeController() {
        this.employeeEntity = null;
        this.employeeView = null;
    }



    public void updateView() {
        if(this.employeeView == null || this.employeeEntity == null) {
            return;
        }
        employeeView.showEmployeeDetails(
                String.valueOf(employeeEntity.getName()),
                String.valueOf(employeeEntity.getSurname()),
                String.valueOf(employeeEntity.getSex()),
                String.valueOf(employeeEntity.getEmail()),
                String.valueOf(employeeEntity.getPhoneNr()),
                String.valueOf(employeeEntity.getDateOfBirth())
        );
    }

    @Override
    public void setEntity(Entity entity) {
        this.employeeEntity = (EmployeeEntity) entity;
    }

    @Override
    public void setView(EntityView view) {
        this.employeeView = (EmployeeView) view;
    }

}
