package entityFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


public final class DefaultEntityManagerFactory {
    private static DefaultEntityManagerFactory instance = new DefaultEntityManagerFactory();
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("default");
    private DefaultEntityManagerFactory() {}

    public static DefaultEntityManagerFactory getInstance() {
        return instance;
    }
    public EntityManager createEntityManager() {
        return factory.createEntityManager();
    }

}
