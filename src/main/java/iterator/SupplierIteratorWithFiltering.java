package iterator;

import entity.PlaceEntity;
import entity.SupplierEntity;

import java.util.Iterator;
import java.util.List;

public class SupplierIteratorWithFiltering implements Iterator {

    private List<SupplierEntity> entities;
    private SupplierEntity current;
    private String key;
    private Object value;
    private int count = 0;

    public SupplierIteratorWithFiltering(List<SupplierEntity> entities, String key, Object value) {
        this.entities = entities;
        this.key = key;
        this.value = value;
        this.current = null;
    }

    private boolean checkMatching(SupplierEntity place) {
        switch (this.key) {
            case "name":
                if(place.getName().equals((String) this.value))
                    return true;
                else return false;
            default:
                return false;
        }
    }
    @Override
    public boolean hasNext() {
        return entities.size() > count;
    }

    @Override
    public Object next() {
        do {
            if(!hasNext())
                break;
            this.current = entities.get(count);
            count++;
        } while (!checkMatching(current));
        return current;
    }
}
