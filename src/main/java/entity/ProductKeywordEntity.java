package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product_keyword", schema = "sbd", catalog = "")
@IdClass(ProductKeywordEntityPK.class)
public class ProductKeywordEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "product_product_id")
    private int productProductId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "keyword_keyword_id")
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

        ProductKeywordEntity that = (ProductKeywordEntity) o;

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
