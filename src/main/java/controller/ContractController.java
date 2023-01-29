package controller;

import entity.ContractEntity;
import entity.EmployeeEntity;
import entityFactory.DefaultEntityManagerFactory;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
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
        EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();

        TypedQuery<String> contractEmployeeQuery = entityManager.createNamedQuery("EmployeeEntity.nameById", String.class);

        contractEmployeeQuery.setParameter("employee_id", contractEntity.getEmployeeEmployeeId());

        return String.valueOf(contractEmployeeQuery.getSingleResult());

    }

    public void updateView() {
        if(this.contractView == null || this.contractEntity == null) {
            return;
        }
        String employeeName = findEmployeeName();
        String startDate = String.valueOf(contractEntity.getDateStart());
        String endDate = String.valueOf(contractEntity.getDateEnd());
        contractView.showDetails(
                String.valueOf(contractEntity.getPaymentAmount()),
                startDate.substring(0, 10),
                endDate.substring(0, 10),
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
