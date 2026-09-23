package com.org.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {

            Configuration configuration = new Configuration().configure();
            Properties environment = loadEnvironment();
            

            String host = environment.getProperty("MYSQLHOST");
            String port = environment.getProperty("MYSQLPORT");
            String db = environment.getProperty("MYSQL_DATABASE");
            String user = environment.getProperty("MYSQLUSER");
            String pass = environment.getProperty("MYSQLPASSWORD");

            if (host != null) {
                configuration.setProperty(
                        "hibernate.connection.url",
                        "jdbc:mysql://" + host + ":" + port + "/" + db +
                                "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
                );

                configuration.setProperty("hibernate.connection.username", user);
                configuration.setProperty("hibernate.connection.password", pass);
            }

            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Session Factory creation failed: " + e.getMessage());
        }
    }

    private static Properties loadEnvironment() throws IOException {
        Properties environment = new Properties();
        Path environmentFile = Path.of("timestore.env");

        if (Files.exists(environmentFile)) {
            try (Reader reader = Files.newBufferedReader(environmentFile)) {
                environment.load(reader);
            }
        }

        System.getenv().forEach(environment::put);
        return environment;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}