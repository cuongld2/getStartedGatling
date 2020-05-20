package common.db;

import common.Logging;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

  private static final SessionFactory sessionFactory = buildSessionFactory();
  private static Logging log = new Logging(HibernateUtil.class);
  private static Session session;
  private static Configuration configuration;

  private static SessionFactory buildSessionFactory() {
    try {
      // Create the SessionFactory from hibernate.cfg.xml
      configuration = new Configuration().configure();
      return configuration.buildSessionFactory();
    } catch (Throwable ex) {
      assert log != null;
      log.error("Initial SessionFactory creation failed.", new Exception(ex));
      throw new ExceptionInInitializerError(ex);
    }
  }

  public static void shutdown() {
    // Close caches and connection pools
    getSessionFactory().close();
  }

  public static void openSession() {
    if (sessionFactory != null) {
      session = sessionFactory.openSession();
    }
  }

  public static void closeSession() {
    if (session != null) {
      session.close();
    }
  }

  public static SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  public static Session getSession() {
    if (session == null) {
      openSession();
    }
    return session;
  }

  public static Configuration getConfiguration() {
    return configuration;
  }

}
