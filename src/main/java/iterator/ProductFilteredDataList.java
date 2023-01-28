package iterator;

import entity.ClientEntity;
import entity.ContractEntity;
import entity.ProductEntity;

import java.util.Iterator;
import java.util.List;

public class ProductFilteredDataList implements Iterable {

    List<ProductEntity> entities;
    String key;
    Object value;
    public ProductFilteredDataList(List<ProductEntity> entities, String key, Object value) {
        this.entities = entities;
        this.key = key;
        this.value = value;
    }
    @Override
    public Iterator iterator() {
        return new ProductIteratorWithFiltering(entities, key, value);
    }
}
