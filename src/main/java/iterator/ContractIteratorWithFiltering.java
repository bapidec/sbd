package iterator;

import entity.ContractEntity;

import java.util.Iterator;
import java.util.List;

public class ContractIteratorWithFiltering implements Iterator {

    private List<ContractEntity> entities;
    private ContractEntity current;
    private String key;
    private Object value;
    private int count = 0;

    public ContractIteratorWithFiltering(List<ContractEntity> entities, String key, Object value) {
        this.entities = entities;
        this.key = key;
        this.value = value;
        this.current = null;
    }

    private boolean checkMatching(ContractEntity contract) {
        switch (this.key) {
            case "type":
                if(contract.getType().equals((String) this.value))
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
