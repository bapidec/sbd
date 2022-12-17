package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "place", schema = "sbd", catalog = "")
public class PlaceEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "place_id")
    private int placeId;
    @Basic
    @Column(name = "product_limit")
    private Integer productLimit;
    @Basic
    @Column(name = "employee_limit")
    private Integer employeeLimit;
    @Basic
    @Column(name = "location")
    private String location;
    @Basic
    @Column(name = "maintenance_cost")
    private Integer maintenanceCost;
    @Basic
    @Column(name = "supplier_supplier_id")
    private Integer supplierSupplierId;

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public Integer getProductLimit() {
        return productLimit;
    }

    public void setProductLimit(Integer productLimit) {
        this.productLimit = productLimit;
    }

    public Integer getEmployeeLimit() {
        return employeeLimit;
    }

    public void setEmployeeLimit(Integer employeeLimit) {
        this.employeeLimit = employeeLimit;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getMaintenanceCost() {
        return maintenanceCost;
    }

    public void setMaintenanceCost(Integer maintenanceCost) {
        this.maintenanceCost = maintenanceCost;
    }

    public Integer getSupplierSupplierId() {
        return supplierSupplierId;
    }

    public void setSupplierSupplierId(Integer supplierSupplierId) {
        this.supplierSupplierId = supplierSupplierId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlaceEntity that = (PlaceEntity) o;

        if (placeId != that.placeId) return false;
        if (productLimit != null ? !productLimit.equals(that.productLimit) : that.productLimit != null) return false;
        if (employeeLimit != null ? !employeeLimit.equals(that.employeeLimit) : that.employeeLimit != null)
            return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (maintenanceCost != null ? !maintenanceCost.equals(that.maintenanceCost) : that.maintenanceCost != null)
            return false;
        if (supplierSupplierId != null ? !supplierSupplierId.equals(that.supplierSupplierId) : that.supplierSupplierId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = placeId;
        result = 31 * result + (productLimit != null ? productLimit.hashCode() : 0);
        result = 31 * result + (employeeLimit != null ? employeeLimit.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (maintenanceCost != null ? maintenanceCost.hashCode() : 0);
        result = 31 * result + (supplierSupplierId != null ? supplierSupplierId.hashCode() : 0);
        return result;
    }
}
