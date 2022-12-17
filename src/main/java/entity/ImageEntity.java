package entity;

import jakarta.persistence.*;

import java.util.Arrays;

@Entity
@Table(name = "image", schema = "sbd", catalog = "")
public class ImageEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "image_id")
    private int imageId;
    @Basic
    @Column(name = "is_default")
    private String isDefault;
    @Basic
    @Column(name = "picture")
    private byte[] picture;
    @Basic
    @Column(name = "product_product_id")
    private int productProductId;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
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

        ImageEntity that = (ImageEntity) o;

        if (imageId != that.imageId) return false;
        if (productProductId != that.productProductId) return false;
        if (isDefault != null ? !isDefault.equals(that.isDefault) : that.isDefault != null) return false;
        if (!Arrays.equals(picture, that.picture)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = imageId;
        result = 31 * result + (isDefault != null ? isDefault.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(picture);
        result = 31 * result + productProductId;
        return result;
    }
}
