package iterator;


import entity.ContractEntity;
import entity.OrderEntity;

import javax.swing.text.html.parser.Entity;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class OrderIteratorWithFiltering implements Iterator {

    private List<OrderEntity> entities;
    private OrderEntity current;
    private String key;
    private Object value;
    private int count = 0;

    public OrderIteratorWithFiltering(List<OrderEntity> entities, String key, Object value) {
        this.entities = entities;
        this.key = key;
        this.value = value;
        this.current = null;

    }

    private boolean checkMatching(OrderEntity order) {

        switch (this.key) {
            case "start_date":
                if(order.getStartDate().equals((String) this.value))
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
