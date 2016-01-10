package com.rp.util.db;

import com.rp.p2p.model.OrderConfirmation;
import com.rp.util.ApplicationProperties;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class HibernateUtil {

    private static final ConcurrentMap<String,SessionFactory> sessionFactoryMap = new ConcurrentHashMap<String, SessionFactory>();
    public enum DbId{P2P}

    private static SessionFactory buildSessionFactory(String db) {
        try {
            Configuration configuration = new Configuration();
            configuration.setProperty("hibernate.connection.driver_class","com.mysql.jdbc.Driver");
            configuration.setProperty("hibernate.connection.url",DbFactory.getInstance().getUrlWithoutCredentials(db));
            configuration.setProperty("hibernate.connection.username",ApplicationProperties.getInstance().getProperty(db+".RDS_USERNAME"));
            configuration.setProperty("hibernate.connection.password",ApplicationProperties.getInstance().getProperty(db+".RDS_PASSWORD"));
            // Create the SessionFactory from hibernate.cfg.xml
            return configuration.configure().
                    buildSessionFactory();
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory(String db) {
        synchronized (sessionFactoryMap) {
            SessionFactory sessionFactory =sessionFactoryMap.get(db);
            if (sessionFactory == null) {
                sessionFactory = buildSessionFactory(db);
                sessionFactoryMap.put(db,sessionFactory);
            }
            return sessionFactory;
        }
    }

    public static SessionFactory getSessionFactory(DbId dbId)
    {
        return getSessionFactory(dbId.name());
    }

    public static void shutdown(String db) {
        // Close caches and connection pools
        getSessionFactory(db).close();
    }

    public static void shutdown(DbId db) {
        // Close caches and connection pools
        shutdown(db.name());
    }

    public static final void main(String args[])
    {
        DbId db = DbId.P2P;

        SessionFactory sessionFactory = getSessionFactory(db);
        shutdown(db);
    }
}
