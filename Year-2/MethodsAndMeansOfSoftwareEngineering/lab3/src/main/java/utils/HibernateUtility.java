package utils;

import data.Result;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtility {
    private static SessionFactory sf;

    public static SessionFactory getSessionFactory() {
        if (sf == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Result.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties());
                sf = configuration.buildSessionFactory(builder.build());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Session Factory Fail");
            }
        }
        return sf;
    }
}
