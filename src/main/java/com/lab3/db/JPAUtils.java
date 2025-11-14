package com.lab3.db;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.IOException;
import java.util.Properties;

public class JPAUtils {
    private static final EntityManagerFactory factory;

    static {
        try {
            Properties info = new Properties();
            // Загружаем конфигурацию из файла db.cfg
            info.load(JPAUtils.class.getClassLoader().getResourceAsStream("/db.cfg"));
            factory = Persistence.createEntityManagerFactory("default", info);
        } catch (IOException ex) {
            System.err.println("Error loading properties: " + ex);
            throw new ExceptionInInitializerError(ex);
        } catch (Throwable ex) {
            System.err.println("Something went wrong during initializing EclipseLink: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManagerFactory getFactory() {
        return factory;
    }
}
