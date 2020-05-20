package common.db;

public class DatabaseConstants {

  public static enum CONFIGURATION_NAME {
    DRIVER_CLASS("hibernate.connection.driver_class"), URL("hibernate.connection.url"), USER_NAME(
        "hibernate.connection.username"), PASSWORD("hibernate.connection.password"), DIALECT(
        "hibernate.dialect");
    private String value;

    private CONFIGURATION_NAME(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

  }

}
