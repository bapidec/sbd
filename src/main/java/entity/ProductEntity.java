package entity;

import jakarta.persistence.*;

import java.lang.annotation.Annotation;
import java.sql.Timestamp;

@Entity
@NamedQuery(name = "ProductEntity.all", query = "FROM ProductEntity c")
@NamedQuery(name = "ProductEntity.byId", query = "FROM ProductEntity c WHERE c.productId = :productId")
@Table(name = "product", schema = "sbd", catalog = "")
public class ProductEntity implements Entity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "product_id")
    private int productId;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "price")
    private double price;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "date_of_production")
    private Timestamp dateOfProduction;
    @Basic
    @Column(name = "discount_discount_id")
    private int discountDiscountId;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDateOfProduction() {
        return dateOfProduction;
    }

    public void setDateOfProduction(Timestamp dateOfProduction) {
        this.dateOfProduction = dateOfProduction;
    }

    public int getDiscountDiscountId() {
        return discountDiscountId;
    }

    public void setDiscountDiscountId(int discountDiscountId) {
        this.discountDiscountId = discountDiscountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductEntity that = (ProductEntity) o;

        if (productId != that.productId) return false;
        if (Double.compare(that.price, price) != 0) return false;
        if (discountDiscountId != that.discountDiscountId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (dateOfProduction != null ? !dateOfProduction.equals(that.dateOfProduction) : that.dateOfProduction != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = productId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (dateOfProduction != null ? dateOfProduction.hashCode() : 0);
        result = 31 * result + discountDiscountId;
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

    public String getGenre() {
        return "Gatunek Domyślny";
    }

    public String getKeywords() {
        return "Domyślne Słowa Kluczowe";
    }
}
