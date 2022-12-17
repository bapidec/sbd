package entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

public class ProductKeywordEntityPK implements Serializable {
    @Column(name = "product_product_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productProductId;
    @Column(name = "keyword_keyword_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int keywordKeywordId;

    public int getProductProductId() {
        return productProductId;
    }

    public void setProductProductId(int productProductId) {
        this.productProductId = productProductId;
    }

    public int getKeywordKeywordId() {
        return keywordKeywordId;
    }

    public void setKeywordKeywordId(int keywordKeywordId) {
        this.keywordKeywordId = keywordKeywordId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductKeywordEntityPK that = (ProductKeywordEntityPK) o;

        if (productProductId != that.productProductId) return false;
        if (keywordKeywordId != that.keywordKeywordId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = productProductId;
        result = 31 * result + keywordKeywordId;
        return result;
    }
}
