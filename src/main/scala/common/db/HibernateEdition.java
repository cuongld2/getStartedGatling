package common.db;

import common.Logging;
import common.UtilsClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateEdition {

  private Logging log = new Logging(HibernateEdition.class);
  private SessionFactory sessionFactory;
  private Session session;
  private Configuration configuration;
  private String fileConfig;

  public HibernateEdition(String fileConfig) {
    this.fileConfig = fileConfig;
    sessionFactory = buildSessionFactory(fileConfig);
  }

  public HibernateEdition(Configuration configuration) {
    this.configuration = configuration;
    sessionFactory = buildSessionFactory(configuration);
  }

  private SessionFactory buildSessionFactory(String fileConfig) {
    if (UtilsClass.isNOTNullEmpty(fileConfig)) {
      configuration = new Configuration().configure(fileConfig);
      return buildSessionFactory(configuration);
    }
    return null;
  }

  private SessionFactory buildSessionFactory(Configuration configuration) {
    try {
      if (configuration != null) {
        return configuration.buildSessionFactory();
      }
    } catch (Throwable ex) {
      log.error("Initial SessionFactory creation failed.", new Exception(ex));
      throw new ExceptionInInitializerError(ex);
    }
    return null;
  }

  public void shutdown() {
    // Close caches and connection pools
    getSessionFactory().close();
  }

  public void openSession() {
    if (sessionFactory != null) {
      session = sessionFactory.openSession();
    }
  }

  public void closeSession() {
    if (session != null) {
      session.close();
    }
  }

  public SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  public Session getSession() {
    if (session == null) {
      openSession();
    }
    return session;
  }

  public Configuration getConfiguration() {
    return configuration;
  }

  public String getFileConfig() {
    return fileConfig;
  }

  public void setFileConfig(String fileConfig) {
    this.fileConfig = fileConfig;
  }

}
