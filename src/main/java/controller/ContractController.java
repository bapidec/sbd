package controller;

import entity.ContractEntity;
import view.ContractView;

public class ContractController implements EntityController{
    private ContractEntity contractEntity;
    private ContractView contractView;
    private String employeeName;

    public ContractController(ContractEntity ce, ContractView cv, String en) {
        this.contractEntity = ce;
        this.contractView = cv;
        this.employeeName = en;
    }

    public void updateView() {
        contractView.showDetails(
                String.valueOf(contractEntity.getPaymentAmount()),
                String.valueOf(contractEntity.getDateStart()),
                String.valueOf(contractEntity.getDateEnd()),
                contractEntity.getType(),
                employeeName
        );
    }

}
