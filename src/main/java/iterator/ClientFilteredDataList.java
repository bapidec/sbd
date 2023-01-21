package iterator;

import entity.ClientEntity;
import entity.ContractEntity;

import java.util.Iterator;
import java.util.List;

public class ClientFilteredDataList implements Iterable {

    List<ClientEntity> entities;
    String key;
    Object value;
    public ClientFilteredDataList(List<ClientEntity> entities, String key, Object value) {
        this.entities = entities;
        this.key = key;
        this.value = value;
    }
    @Override
    public Iterator iterator() {
        return new ClientIteratorWithFiltering(entities, key, value);
    }
}
