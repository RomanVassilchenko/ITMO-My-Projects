package org.example.tools;

import org.example.models.Point;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;

public class DBCommunicator implements Serializable {

    private static volatile DBCommunicator instance;

    public static DBCommunicator getInstance() {
        DBCommunicator localInstance = instance;
        if (localInstance == null) {
            synchronized (DBCommunicator.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DBCommunicator();
                }
            }
        }
        return localInstance;
    }



    private EntityManagerFactory managerFactory;
    @PersistenceContext
    private EntityManager manager;
    private EntityTransaction transaction;

    public DBCommunicator() {
        managerFactory = Persistence.createEntityManagerFactory("default");
        manager = managerFactory.createEntityManager();
        transaction = manager.getTransaction();
    }

    public void sendOne(Point point) {
        try {
            transaction.begin();
            manager.persist(point);
            transaction.commit();
        }
        catch (RuntimeException e) {
            if (transaction.isActive()) transaction.rollback();
        }
    }

    public ArrayList<Point> getAll() {
        try {
            transaction.begin();

            ArrayList<Point> res = new ArrayList<>(
                manager.createQuery("select e from org.example.models.Point e", Point.class).getResultList()
            );

            transaction.commit();
            return res;
        }
        catch (RuntimeException e) {
            if (transaction.isActive()) transaction.rollback();
            return new ArrayList<Point>();
        }
    }

    public void clearAll() {
        transaction.begin();
        manager.createQuery("delete from org.example.models.Point").executeUpdate();
        transaction.commit();
    }

}
