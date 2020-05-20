package common.db.entity;

import common.UtilsClass;
import common.db.ConfigurationUtils;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class DatabaseBKBean {

  private Configuration configuration;
  private String url;
  private String user;
  private String password;
  private String fileName;
  private String path;
  private boolean isDropAll;

  public String[] getParamH2DB() {
    List<String> list = new ArrayList<String>();
    url = ConfigurationUtils.getUrl(configuration);
    user = ConfigurationUtils.getUserName(configuration);
    password = ConfigurationUtils.getPassword(configuration);
    if (UtilsClass.isNOTNullEmpty(url)) {
      list.add("-url");
      list.add(url);
    }
    if (UtilsClass.isNOTNullEmpty(user)) {
      list.add("-user");
      list.add(user);
    }
    if (UtilsClass.isNOTNullEmpty(password)) {
      list.add("-password");
      list.add(password);
    }
    String fullPath = getFullPath();
    if (UtilsClass.isNOTNullEmpty(fullPath)) {
      list.add("-script");
      list.add(fullPath);
    }
    list.add("-options");
    list.add("compression");
    list.add("zip");
    return list.toArray(new String[list.size()]);
  }

  public Configuration getConfiguration() {
    return configuration;
  }

  public void setConfiguration(Configuration configuration) {
    this.configuration = configuration;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getFullPath() {
    if (UtilsClass.isNullOrEmpty(fileName))
      return null;
    StringBuilder builder = new StringBuilder();
    if (UtilsClass.isNOTNullEmpty(path)) {
      builder.append(path);
    }
    builder.append("/").append(fileName).append(".zip");
    return builder.toString();
  }

  public boolean isDropAll() {
    return isDropAll;
  }

  public void setDropAll(boolean isDropAll) {
    this.isDropAll = isDropAll;
  }

}
