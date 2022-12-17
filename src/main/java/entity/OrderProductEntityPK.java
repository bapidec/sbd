package entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

public class OrderProductEntityPK implements Serializable {
    @Column(name = "product_product_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productProductId;
    @Column(name = "order_order_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderOrderId;

    public int getProductProductId() {
        return productProductId;
    }

    public void setProductProductId(int productProductId) {
        this.productProductId = productProductId;
    }

    public int getOrderOrderId() {
        return orderOrderId;
    }

    public void setOrderOrderId(int orderOrderId) {
        this.orderOrderId = orderOrderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderProductEntityPK that = (OrderProductEntityPK) o;

        if (productProductId != that.productProductId) return false;
        if (orderOrderId != that.orderOrderId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = productProductId;
        result = 31 * result + orderOrderId;
        return result;
    }
}
