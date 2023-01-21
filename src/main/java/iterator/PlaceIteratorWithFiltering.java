package iterator;

import entity.PlaceEntity;

import java.util.Iterator;
import java.util.List;

public class PlaceIteratorWithFiltering implements Iterator {

    private List<PlaceEntity> entities;
    private PlaceEntity current;
    private String key;
    private Object value;
    private int count = 0;

    public PlaceIteratorWithFiltering(List<PlaceEntity> entities, String key, Object value) {
        this.entities = entities;
        this.key = key;
        this.value = value;
        this.current = null;
    }

    private boolean checkMatching(PlaceEntity place) {
        switch (this.key) {
            case "location":
                if(place.getLocation().equals((String) this.value))
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
