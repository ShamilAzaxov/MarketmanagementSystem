package config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
public abstract class MarketConfig {

    private static EntityManagerFactory factory;

    public static EntityManager manager(){
        EntityManager manager;
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory("market_management");
        }
        manager = factory.createEntityManager();
       return manager;
    }
}