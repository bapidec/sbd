package entity;

import jakarta.persistence.*;

import java.lang.annotation.Annotation;

@Entity
@NamedQuery(name = "ProductGenreEntity.all", query = "FROM ProductGenreEntity c")
@NamedQuery(name = "ProductGenreEntity.byId", query = "FROM ProductGenreEntity c WHERE c.genreId = :genreId")
@Table(name = "product_genre", schema = "sbd", catalog = "")
public class ProductGenreEntity implements Entity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "genre_id")
    private int genreId;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "product_product_id")
    private Integer productProductId;

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getProductProductId() {
        return productProductId;
    }

    public void setProductProductId(Integer productProductId) {
        this.productProductId = productProductId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductGenreEntity that = (ProductGenreEntity) o;

        if (genreId != that.genreId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (productProductId != null ? !productProductId.equals(that.productProductId) : that.productProductId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = genreId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (productProductId != null ? productProductId.hashCode() : 0);
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
