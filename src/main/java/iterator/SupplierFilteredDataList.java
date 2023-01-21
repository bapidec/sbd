package iterator;

import entity.ContractEntity;
import entity.SupplierEntity;

import java.util.Iterator;
import java.util.List;

public class SupplierFilteredDataList implements Iterable {

    List<SupplierEntity> entities;
    String key;
    Object value;
    public SupplierFilteredDataList(List<SupplierEntity> entities, String key, Object value) {
        this.entities = entities;
        this.key = key;
        this.value = value;
    }
    @Override
    public Iterator iterator() {
        return new SupplierIteratorWithFiltering(entities, key, value);
    }
}
