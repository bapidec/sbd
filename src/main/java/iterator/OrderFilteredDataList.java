package iterator;



import entity.OrderEntity;

import java.util.Iterator;
import java.util.List;

public class OrderFilteredDataList implements Iterable {

    List<OrderEntity> entities;
    String key;
    Object value;
    public OrderFilteredDataList(List<OrderEntity> entities, String key, Object value) {
        this.entities = entities;
        this.key = key;
        this.value = value;

    }
    @Override
    public Iterator iterator() {
        return new OrderIteratorWithFiltering(entities, key, value);
    }
}
