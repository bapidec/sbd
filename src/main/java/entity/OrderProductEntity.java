package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_product", schema = "sbd", catalog = "")
@IdClass(OrderProductEntityPK.class)
public class OrderProductEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "product_product_id")
    private int productProductId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "order_order_id")
    private int orderOrderId;
    @Basic
    @Column(name = "quantity")
    private int quantity;
    @Basic
    @Column(name = "order_product_id")
    private int orderProductId;

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(int orderProductId) {
        this.orderProductId = orderProductId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderProductEntity that = (OrderProductEntity) o;

        if (productProductId != that.productProductId) return false;
        if (orderOrderId != that.orderOrderId) return false;
        if (quantity != that.quantity) return false;
        if (orderProductId != that.orderProductId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = productProductId;
        result = 31 * result + orderOrderId;
        result = 31 * result + quantity;
        result = 31 * result + orderProductId;
        return result;
    }
}
