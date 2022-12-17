package entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

public class PlaceProductEntityPK implements Serializable {
    @Column(name = "product_product_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productProductId;
    @Column(name = "place_place_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

        PlaceProductEntityPK that = (PlaceProductEntityPK) o;

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
