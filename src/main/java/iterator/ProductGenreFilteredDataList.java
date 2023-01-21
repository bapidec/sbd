package iterator;

import entity.ClientEntity;
import entity.ContractEntity;
import entity.ProductGenreEntity;

import java.util.Iterator;
import java.util.List;

public class ProductGenreFilteredDataList implements Iterable {

    List<ProductGenreEntity> entities;
    String key;
    Object value;
    public ProductGenreFilteredDataList(List<ProductGenreEntity> entities, String key, Object value) {
        this.entities = entities;
        this.key = key;
        this.value = value;
    }
    @Override
    public Iterator iterator() {
        return new ProductGenreIteratorWithFiltering(entities, key, value);
    }
}
