package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "warehouse", schema = "sbd", catalog = "")
public class WarehouseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "warehouse_id")
    private int warehouseId;

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WarehouseEntity that = (WarehouseEntity) o;

        if (warehouseId != that.warehouseId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return warehouseId;
    }
}
