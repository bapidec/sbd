package iterator;

import entity.ContractEntity;
import entity.EmployeeEntity;

import java.util.Iterator;
import java.util.List;

public class EmployeeFilteredDataList implements Iterable {

    List<EmployeeEntity> entities;
    String key;
    Object value;
    public EmployeeFilteredDataList(List<EmployeeEntity> entities, String key, Object value) {
        this.entities = entities;
        this.key = key;
        this.value = value;
    }
    @Override
    public Iterator iterator() {
        return new EmployeeIteratorWithFiltering(entities, key, value);
    }
}
