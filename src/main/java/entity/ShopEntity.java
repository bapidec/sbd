package entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "shop", schema = "sbd", catalog = "")
public class ShopEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "shop_id")
    private int shopId;
    @Basic
    @Column(name = "opening_hours")
    private Timestamp openingHours;
    @Basic
    @Column(name = "closing_hours")
    private Timestamp closingHours;
    @Basic
    @Column(name = "does_refunds")
    private String doesRefunds;

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public Timestamp getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(Timestamp openingHours) {
        this.openingHours = openingHours;
    }

    public Timestamp getClosingHours() {
        return closingHours;
    }

    public void setClosingHours(Timestamp closingHours) {
        this.closingHours = closingHours;
    }

    public String getDoesRefunds() {
        return doesRefunds;
    }

    public void setDoesRefunds(String doesRefunds) {
        this.doesRefunds = doesRefunds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShopEntity that = (ShopEntity) o;

        if (shopId != that.shopId) return false;
        if (openingHours != null ? !openingHours.equals(that.openingHours) : that.openingHours != null) return false;
        if (closingHours != null ? !closingHours.equals(that.closingHours) : that.closingHours != null) return false;
        if (doesRefunds != null ? !doesRefunds.equals(that.doesRefunds) : that.doesRefunds != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = shopId;
        result = 31 * result + (openingHours != null ? openingHours.hashCode() : 0);
        result = 31 * result + (closingHours != null ? closingHours.hashCode() : 0);
        result = 31 * result + (doesRefunds != null ? doesRefunds.hashCode() : 0);
        return result;
    }
}
