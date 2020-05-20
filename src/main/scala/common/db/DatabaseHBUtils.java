package common.db;

import common.Logging;
import common.UtilsClass;
import common.db.entity.DatabaseBKBean;
import common.db.entity.SupportDBException;
import org.h2.tools.RunScript;
import org.h2.tools.Script;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.OracleDialect;
import org.hibernate.dialect.SQLServerDialect;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.type.StringType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

@SuppressWarnings("deprecation")
public class DatabaseHBUtils {

  private static final String DOUBLE_QUOTE = "\"";
  private static final String COLON = ":";
  private static final char COMMA = ',';
  private static final String CURLY_BRACES_OPEN = "{";
  private static final String CURLY_BRACES_CLOSE = "}";
  private static final String SQUARE_BRACKETS_MARK_OPEN = "[";
  private static final String SQUARE_BRACKETS_MARK_CLOSE = "]";

  private static Logging log = new Logging(DatabaseHBUtils.class);

  /**
   * get query result with object entity mapped to DB table (Dialect mapping for JDBC type)
   * 
   * @param queryStr
   * @return
   */
  public static List<Map<String, Object>> getResultMapped(String queryStr, boolean isCloseSession)
      throws Exception {
    return getResultMapped(HibernateUtil.getSession(), queryStr, isCloseSession);
  }

  /**
   * get query result with object entity mapped to DB table (Dialect mapping for JDBC type)
   * 
   * @param session
   * @param queryStr
   * @return
   * @throws Exception
   */
  @SuppressWarnings("unchecked")
  public static List<Map<String, Object>> getResultMapped(Session session, String queryStr,
                                                          boolean isCloseSession) throws Exception {
    if (UtilsClass.isNOTNullEmpty(queryStr)) {
      try {
        SQLQuery query = session.createSQLQuery(queryStr);
        query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
        List<Map<String, Object>> list = query.list();
        return list;
      } catch (Exception e) {
        log.error(e);
        throw e;
      } finally {
        if (session != null && isCloseSession)
          session.close();
      }
    }
    return null;
  }

  // public static List<?> getResultMapEntity(Session session, String queryStr,
  // Class<?> clazz, boolean isCloseSession)
  // throws Exception {
  // if (UtilsClass.isNOTNullEmpty(queryStr)) {
  // try {
  // SQLQuery query = session.createSQLQuery(queryStr);
  // query.addEntity(clazz);
  // List<?> list = query.list();
  // return list;
  // } catch (Exception e) {
  // log.error(e);
  // throw e;
  // } finally {
  // if (session != null && isCloseSession)
  // session.close();
  // }
  // }
  // return null;
  // }

  /**
   * get list result query with object not mapped to DB table (scalar)
   * 
   * @param queryStr
   * @param columnName
   * @return
   */
  public static String getResultListNotMapped(String queryStr, String[] columnName,
      boolean isCloseSession) throws Exception {
    try {
      return getResultNotMapped(queryStr, columnName, true, isCloseSession);
    } catch (Exception e) {
      log.error(e);
      throw e;
    }
  }

  public static String getResultListNotMapped(Session session, String queryStr,
                                              String[] columnName, boolean isCloseSession) throws Exception {
    try {
      return getResultNotMapped(session, queryStr, columnName, true, isCloseSession);
    } catch (Exception e) {
      log.error(e);
      throw e;
    }
  }

  /**
   * get object result query with object not mapped to DB table (scalar)
   * 
   * @param queryStr
   * @param columnName
   * @return
   */
  public static String getResultNotMapped(String queryStr, String[] columnName,
      boolean isCloseSession) throws Exception {
    try {
      return getResultNotMapped(queryStr, columnName, false, isCloseSession);
    } catch (Exception e) {
      log.error(e);
      throw e;
    }
  }

  /**
   * get object result query with object not mapped to DB table (scalar)
   * 
   * @param session
   * @param queryStr
   * @param columnName
   * @return
   * @throws Exception
   */
  public static String getResultNotMapped(Session session, String queryStr, String[] columnName,
                                          boolean isCloseSession) throws Exception {
    try {
      return getResultNotMapped(session, queryStr, columnName, false, isCloseSession);
    } catch (Exception e) {
      log.error(e);
      throw e;
    }
  }

  /**
   * get list or object result query with object not mapped to DB table (scalar) (json format)
   * 
   * @param queryStr
   * @param columnName
   * @param isList
   * @return
   * @throws Exception
   */
  public static String getResultNotMapped(String queryStr, String[] columnName, boolean isList,
      boolean isCloseSession) throws Exception {
    if (UtilsClass.isNOTNullEmpty(queryStr)) {
      return listToJson(getResultOriginNotMapped(queryStr, columnName, isCloseSession), columnName,
          isList);
    }
    return null;
  }

  /**
   * get list or object result query with object not mapped to DB table (scalar) (json format)
   * 
   * @param session
   * @param queryStr
   * @param columnName
   * @param isList
   * @return
   * @throws Exception
   */
  public static String getResultNotMapped(Session session, String queryStr, String[] columnName,
                                          boolean isList, boolean isCloseSession) throws Exception {
    if (UtilsClass.isNOTNullEmpty(queryStr)) {
      return listToJson(getResultOriginNotMapped(session, queryStr, columnName, isCloseSession),
          columnName, isList);
    }
    return null;
  }

  /**
   * list to json format
   * 
   * @param list
   * @param columnName
   * @param isList
   * @return
   * @throws Exception
   */
  public static String listToJson(List<?> list, String[] columnName, boolean isList)
      throws Exception {
    return listToJsonFromJSONObject(list, columnName, isList);
  }

  /**
   * list to json format (JSONObject)
   * 
   * @param list
   * @param columnName
   * @param isList
   * @return
   * @throws Exception
   */
  private static String listToJsonFromJSONObject(List<?> list, String[] columnName, boolean isList)
      throws Exception {
    if (UtilsClass.isNOTNullEmpty(list)) {
      if (isList) {
        JSONArray jsonArray = new JSONArray();
        for (Object o : list) {
          jsonArray.put(addToJSONObject(o, columnName));
        }
        return jsonArray.toString();
      } else {
        JSONObject jsonObject = addToJSONObject(list.get(0), columnName);
        return jsonObject != null ? jsonObject.toString() : null;
      }
    }
    return null;
  }

  private static JSONObject addToJSONObject(Object o, String[] columnName) {
    JSONObject jsonObject = null;
    if (o != null && UtilsClass.isNOTNullEmpty(columnName)) {
      jsonObject = new JSONObject();
      if (o.getClass().isArray()) {
        Object[] oChild = (Object[]) o;
        for (int i = 0; i < oChild.length; i++) {
          jsonObject.put(columnName[i], oChild[i]);
        }
      } else {
        jsonObject.put(columnName[0], o);
      }
    }
    return jsonObject;
  }

  /**
   * list to json format (StringBuilder)
   * 
   * @param list
   * @param columnName
   * @param isList
   * @return
   * @throws Exception
   */
  private static String listToJsonFromStringBuilder(List<?> list, String[] columnName,
      boolean isList) throws Exception {
    if (UtilsClass.isNOTNullEmpty(list)) {
      StringBuilder result = new StringBuilder();
      StringBuilder temp = null;
      Object[] oChild = null;
      for (Object o : list) {
        if (o == null)
          continue;
        temp = new StringBuilder();
        if (o.getClass().isArray()) {
          oChild = (Object[]) o;
          for (int i = 0; i < oChild.length; i++) {
            if (UtilsClass.isNOTNullEmpty(oChild[i])) {
              temp.append(appendFieldValue(columnName[i], oChild[i]));
            }
          }
          if (temp.length() > 0 && temp.charAt(temp.length() - 1) == COMMA) {
            temp = new StringBuilder(temp.substring(0, temp.length() - 1));
          }
        } else {
          if (UtilsClass.isNOTNullEmpty(o)) {
            temp.append(appendFieldValue(columnName[0], o));
          }
          if (temp.length() > 0 && temp.charAt(temp.length() - 1) == COMMA) {
            temp = new StringBuilder(temp.substring(0, temp.length() - 1));
          }
        }
        temp.insert(0, CURLY_BRACES_OPEN);
        temp.insert(temp.length(), CURLY_BRACES_CLOSE);
        if (result.length() > 0)
          result.append(COMMA);
        result.append(temp);
      }
      if (isList)
        result.insert(0, SQUARE_BRACKETS_MARK_OPEN).insert(result.length(),
            SQUARE_BRACKETS_MARK_CLOSE);
      return result.toString();
    }
    return null;
  }

  /**
   * append field name and value similar json
   * 
   * @param fieldName
   * @param value
   * @return
   */
  private static String appendFieldValue(String fieldName, Object value) {
    StringBuilder builder = new StringBuilder();
    builder.append(DOUBLE_QUOTE).append(fieldName).append(DOUBLE_QUOTE);
    builder.append(COLON);
    builder.append(DOUBLE_QUOTE).append(value).append(DOUBLE_QUOTE);
    builder.append(COMMA);
    return builder.toString();
  }

  public static List<?> getResultOriginNotMapped(String queryStr, String[] columnName,
      boolean isCloseSession) throws Exception {
    return getResultOriginNotMapped(HibernateUtil.getSession(), queryStr, columnName,
        isCloseSession);
  }

  /**
   * get list result query with object not mapped to DB table (scalar)
   * 
   * @param session
   * @param queryStr
   * @param columnName
   * @return
   * @throws Exception
   */
  public static List<?> getResultOriginNotMapped(Session session, String queryStr,
                                                 String[] columnName, boolean isCloseSession) throws Exception {
    if (UtilsClass.isNOTNullEmpty(queryStr)) {
      try {
        SQLQuery query = session.createSQLQuery(queryStr);
        if (columnName != null && columnName.length > 0) {
          for (String o : columnName) {
            query.addScalar(o, StringType.INSTANCE);
          }
        }
        return query.list();
      } catch (Exception e) {
        log.error(e);
        throw e;
      } finally {
        if (session != null && isCloseSession)
          session.close();
      }
    }
    return null;
  }

  /**
   * executeUpdate with transaction (commit, rollback)
   * 
   * @param queryStr
   * @return
   * @throws HibernateException
   */
  public static int executeUpdateWithTransaction(String queryStr, boolean isCloseSession)
      throws Exception {
    return executeUpdateWithTransaction(HibernateUtil.getSession(), queryStr, isCloseSession);
  }

  /**
   * executeUpdate with transaction (commit, rollback)
   * 
   * @param session
   * @param queryStr
   * @return
   * @throws Exception
   */
  public static int executeUpdateWithTransaction(Session session, String queryStr,
                                                 boolean isCloseSession) throws Exception {
    try {
      session.beginTransaction();
      int r = executeUpdateWithoutTransaction(session, queryStr, isCloseSession);
      session.getTransaction().commit();
      return r;
    } catch (HibernateException e) {
      session.getTransaction().rollback();
      log.error(e);
      throw e;
    } finally {
      if (session != null && isCloseSession)
        session.close();
    }
  }

  /**
   * executeUpdate without transaction (commit, rollback)
   * 
   * @param queryStr
   * @return
   * @throws HibernateException
   */
  public static int executeUpdateWithoutTransaction(String queryStr, boolean isCloseSession)
      throws Exception {
    return executeUpdateWithoutTransaction(HibernateUtil.getSession(), queryStr, isCloseSession);
  }

  /**
   * executeUpdate without transaction (commit, rollback)
   * 
   * @param session
   * @param queryStr
   * @return
   * @throws Exception
   */
  public static int executeUpdateWithoutTransaction(Session session, String queryStr,
                                                    boolean isCloseSession) throws Exception {
    try {
      if (UtilsClass.isNOTNullEmpty(queryStr)) {
        return session.createSQLQuery(queryStr).executeUpdate();
      }
    } catch (Exception e) {
      log.error(e);
      throw e;
    } finally {
      if (session != null && isCloseSession)
        session.close();
    }
    return -1;
  }

  public static boolean backup(DatabaseBKBean bkBean) throws Exception {
    if (bkBean != null) {
      try {
        Dialect dialect = Dialect.getDialect(bkBean.getConfiguration().getProperties());
        if (dialect instanceof OracleDialect) {
          throw new SupportDBException("No support this database version");
        } else if (dialect instanceof SQLServerDialect) {
          throw new SupportDBException("No support this database version");
        } else if (dialect instanceof H2Dialect) {
          Script.main(bkBean.getParamH2DB());
        }
        return true;
      } catch (Exception e) {
        log.error(e);
        throw e;
      }
    }
    return false;
  }

  public static boolean restore(DatabaseBKBean bkBean) throws Exception {
    if (bkBean != null) {
      try {
        Dialect dialect = Dialect.getDialect(bkBean.getConfiguration().getProperties());
        if (dialect instanceof OracleDialect) {
          throw new SupportDBException("No support this database version");
        } else if (dialect instanceof SQLServerDialect) {
          throw new SupportDBException("No support this database version");
        } else if (dialect instanceof H2Dialect) {
          if (bkBean.isDropAll()) {
            HibernateEdition edition = new HibernateEdition(bkBean.getConfiguration());
            DatabaseHBUtils.executeUpdateWithoutTransaction(edition.getSession(),
                "DROP ALL OBJECTS", true);
          }
          RunScript.main(bkBean.getParamH2DB());
        }
        return true;
      } catch (Exception e) {
        log.error(e);
        throw e;
      }
    }
    return false;
  }

}
