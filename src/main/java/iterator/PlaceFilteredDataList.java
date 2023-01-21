package iterator;

import entity.PlaceEntity;

import java.util.Iterator;
import java.util.List;

public class PlaceFilteredDataList implements Iterable {

    List<PlaceEntity> entities;
    String key;
    Object value;
    public PlaceFilteredDataList(List<PlaceEntity> entities, String key, Object value) {
        this.entities = entities;
        this.key = key;
        this.value = value;
    }
    @Override
    public Iterator iterator() {
        return new PlaceIteratorWithFiltering(entities, key, value);
    }
}
