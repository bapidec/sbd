package iterator;

import entity.ContractEntity;

import java.util.Iterator;
import java.util.List;

public class ContractFilteredDataList implements Iterable {

    List<ContractEntity> entities;
    String key;
    Object value;
    public ContractFilteredDataList(List<ContractEntity> entities, String key, Object value) {
        this.entities = entities;
        this.key = key;
        this.value = value;
    }
    @Override
    public Iterator iterator() {
        return new ContractIteratorWithFiltering(entities, key, value);
    }
}
