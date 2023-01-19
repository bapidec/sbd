package controller;

import entity.ContractEntity;
import entity.EmployeeEntity;
import jakarta.persistence.Entity;
import view.ContractView;
import view.EntityView;

public class ContractController implements EntityController{
    private ContractEntity contractEntity;
    private ContractView contractView;
    private String employeeName;

    public ContractController() {
        this.contractEntity = null;
        this.contractView = null;
    }

    private String findEmployeeName() {
        return "Twoja stara";
    }

    public void updateView() {
        if(this.contractView == null || this.contractEntity == null) {
            return;
        }
        String employeeName = findEmployeeName();
        contractView.showDetails(
                String.valueOf(contractEntity.getPaymentAmount()),
                String.valueOf(contractEntity.getDateStart()),
                String.valueOf(contractEntity.getDateEnd()),
                contractEntity.getType(),
                employeeName
        );
    }

    @Override
    public void setEntity(Entity entity) {
        this.contractEntity = (ContractEntity) entity;
    }

    @Override
    public void setView(EntityView view) {
        this.contractView = (ContractView) view;
    }

}
