package common.db;

import common.UtilsClass;
import org.hibernate.cfg.Configuration;

public class ConfigurationUtils {

  public static String getDriverClass(Configuration configuration) {
    return getValue(configuration, DatabaseConstants.CONFIGURATION_NAME.DRIVER_CLASS.getValue());
  }

  public static String getUrl(Configuration configuration) {
    return getValue(configuration, DatabaseConstants.CONFIGURATION_NAME.URL.getValue());
  }

  public static String getUserName(Configuration configuration) {
    return getValue(configuration, DatabaseConstants.CONFIGURATION_NAME.USER_NAME.getValue());
  }

  public static String getPassword(Configuration configuration) {
    return getValue(configuration, DatabaseConstants.CONFIGURATION_NAME.PASSWORD.getValue());
  }

  public static String getDialect(Configuration configuration) {
    return getValue(configuration, DatabaseConstants.CONFIGURATION_NAME.DIALECT.getValue());
  }

  public static String getValue(Configuration configuration, String key) {
    if (configuration != null && UtilsClass.isNOTNullEmpty(key)) {
      return configuration.getProperty(key);
    }
    return null;
  }

}
