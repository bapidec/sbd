package entity;

import jakarta.persistence.*;

import java.lang.annotation.Annotation;
import java.sql.Timestamp;

@Entity
@NamedQuery(name = "OrderEntity.all", query = "FROM OrderEntity c")
@NamedQuery(name = "OrderEntity.byId", query = "FROM OrderEntity c WHERE c.orderId = :orderId")
@Table(name = "order", schema = "sbd", catalog = "")
public class OrderEntity implements Entity{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "order_id")
    private int orderId;
    @Basic
    @Column(name = "start_date")
    private Timestamp startDate;
    @Basic
    @Column(name = "end_date")
    private Timestamp endDate;
    @Basic
    @Column(name = "client_client_id")
    private int clientClientId;
    @Basic
    @Column(name = "address")
    private String address;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    public int getClientClientId() {
        return clientClientId;
    }

    public void setClientClientId(int clientClientId) {
        this.clientClientId = clientClientId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderEntity that = (OrderEntity) o;

        if (orderId != that.orderId) return false;
        if (clientClientId != that.clientClientId) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderId;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + clientClientId;
        result = 31 * result + (address != null ? address.hashCode() : 0);
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
