package entity;

import jakarta.persistence.*;

import java.lang.annotation.Annotation;
import java.sql.Timestamp;

@Entity
@NamedQuery(name = "SupplierEntity.all", query = "FROM SupplierEntity c")
@NamedQuery(name = "SupplierEntity.byId", query = "FROM SupplierEntity c WHERE c.supplierId = :supplierId")
@Table(name = "supplier", schema = "sbd", catalog = "")
public class SupplierEntity implements Entity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "supplier_id")
    private int supplierId;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "vehicle_number")
    private Integer vehicleNumber;
    @Basic
    @Column(name = "start_date")
    private Timestamp startDate;
    @Basic
    @Column(name = "end_date")
    private Timestamp endDate;

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(Integer vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SupplierEntity that = (SupplierEntity) o;

        if (supplierId != that.supplierId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (vehicleNumber != null ? !vehicleNumber.equals(that.vehicleNumber) : that.vehicleNumber != null)
            return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = supplierId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (vehicleNumber != null ? vehicleNumber.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        return result;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }

    @Override
    public String name() {
        return null;
    }
}
