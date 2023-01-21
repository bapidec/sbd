package iterator;

import entity.ClientEntity;
import entity.ContractEntity;

import javax.swing.text.html.parser.Entity;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class ClientIteratorWithFiltering implements Iterator {

    private List<ClientEntity> entities;
    private ClientEntity current;
    private String key;
    private Object value;
    private int count = 0;

    public ClientIteratorWithFiltering(List<ClientEntity> entities, String key, Object value) {
        this.entities = entities;
        this.key = key;
        this.value = value;
        this.current = null;
    }

    private boolean checkMatching(ClientEntity client) {
        switch (this.key) {
            case "name":
                if(client.getName().equals((String) this.value))
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
