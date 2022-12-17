package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "discount", schema = "sbd", catalog = "")
public class DiscountEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "discount_id")
    private int discountId;
    @Basic
    @Column(name = "discount_percentage")
    private int discountPercentage;
    @Basic
    @Column(name = "product_product_id")
    private int productProductId;

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public int getProductProductId() {
        return productProductId;
    }

    public void setProductProductId(int productProductId) {
        this.productProductId = productProductId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DiscountEntity that = (DiscountEntity) o;

        if (discountId != that.discountId) return false;
        if (discountPercentage != that.discountPercentage) return false;
        if (productProductId != that.productProductId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = discountId;
        result = 31 * result + discountPercentage;
        result = 31 * result + productProductId;
        return result;
    }
}
