package entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@NamedQuery(name = "ContractEntity.all", query = "FROM ContractEntity c")
@NamedQuery(name = "ContractEntity.byId", query = "FROM ContractEntity c WHERE c.contractId = :contractId")
@Table(name = "contract", schema = "sbd", catalog = "")
public class ContractEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "contract_id")
    private int contractId;
    @Basic
    @Column(name = "payment_amount")
    private double paymentAmount;
    @Basic
    @Column(name = "date_start")
    private Timestamp dateStart;
    @Basic
    @Column(name = "date_end")
    private Timestamp dateEnd;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "place_place_id")
    private Integer placePlaceId;
    @Basic
    @Column(name = "employee_employee_id")
    private int employeeEmployeeId;

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Timestamp getDateStart() {
        return dateStart;
    }

    public void setDateStart(Timestamp dateStart) {
        this.dateStart = dateStart;
    }

    public Timestamp getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Timestamp dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPlacePlaceId() {
        return placePlaceId;
    }

    public void setPlacePlaceId(Integer placePlaceId) {
        this.placePlaceId = placePlaceId;
    }

    public int getEmployeeEmployeeId() {
        return employeeEmployeeId;
    }

    public void setEmployeeEmployeeId(int employeeEmployeeId) {
        this.employeeEmployeeId = employeeEmployeeId;
    }

    public String getEmployeeName(EntityManager entityManager) {
        TypedQuery<String> contractEmployeeQuery = entityManager.createNamedQuery("EmployeeEntity.nameById", String.class);
        contractEmployeeQuery.setParameter("employee_id", this.getEmployeeEmployeeId());
        String employeeName = String.valueOf(contractEmployeeQuery.getSingleResult());
        return employeeName;
    }

    public String getEmployeeSurname(EntityManager entityManager) {
        TypedQuery<String> contractEmployeeQuery = entityManager.createNamedQuery("EmployeeEntity.surnameById", String.class);
        contractEmployeeQuery.setParameter("employee_id", this.getEmployeeEmployeeId());
        String employeeName = String.valueOf(contractEmployeeQuery.getSingleResult());
        return employeeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContractEntity that = (ContractEntity) o;

        if (contractId != that.contractId) return false;
        if (Double.compare(that.paymentAmount, paymentAmount) != 0) return false;
        if (employeeEmployeeId != that.employeeEmployeeId) return false;
        if (dateStart != null ? !dateStart.equals(that.dateStart) : that.dateStart != null) return false;
        if (dateEnd != null ? !dateEnd.equals(that.dateEnd) : that.dateEnd != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (placePlaceId != null ? !placePlaceId.equals(that.placePlaceId) : that.placePlaceId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = contractId;
        temp = Double.doubleToLongBits(paymentAmount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (dateStart != null ? dateStart.hashCode() : 0);
        result = 31 * result + (dateEnd != null ? dateEnd.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (placePlaceId != null ? placePlaceId.hashCode() : 0);
        result = 31 * result + employeeEmployeeId;
        return result;
    }
}
