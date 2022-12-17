package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "place_product", schema = "sbd", catalog = "")
@IdClass(PlaceProductEntityPK.class)
public class PlaceProductEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "product_product_id")
    private int productProductId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "place_place_id")
    private int placePlaceId;

    public int getProductProductId() {
        return productProductId;
    }

    public void setProductProductId(int productProductId) {
        this.productProductId = productProductId;
    }

    public int getPlacePlaceId() {
        return placePlaceId;
    }

    public void setPlacePlaceId(int placePlaceId) {
        this.placePlaceId = placePlaceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlaceProductEntity that = (PlaceProductEntity) o;

        if (productProductId != that.productProductId) return false;
        if (placePlaceId != that.placePlaceId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = productProductId;
        result = 31 * result + placePlaceId;
        return result;
    }
}
