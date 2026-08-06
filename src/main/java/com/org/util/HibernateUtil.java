package com.org.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration().configure();

            String host = System.getenv("MYSQLHOST");
            String port = System.getenv("MYSQLPORT");
            String db   = System.getenv("MYSQL_DATABASE");
            String user = System.getenv("MYSQLUSER");
            String pass = System.getenv("MYSQL_ROOT_PASSWORD");

            // If Railway env vars are present, override the XML config.
            // Otherwise, fall back to whatever is in hibernate.cfg.xml (local dev).
            if (host != null && port != null && db != null) {
                String url = "jdbc:mysql://" + host + ":" + port + "/" + db;
                configuration.setProperty("hibernate.connection.url", url);
                configuration.setProperty("hibernate.connection.username", user);
                configuration.setProperty("hibernate.connection.password", pass);
            }

            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Session Factory creation failed: " + e.getMessage());
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}