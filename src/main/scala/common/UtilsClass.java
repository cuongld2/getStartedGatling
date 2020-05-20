package common;

import org.json.JSONArray;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class UtilsClass {

  public UtilsClass() {}

  public static final String COMMA = ",";
  public static final String DOT = ".";
  private static final String TRUE = "TRUE";
  // private static final String FALSE = "FALSE";
  private static final String NUMBER_1 = "1";
  // private static final String NUMBER_0 = "0";
  private static final String FORMAT_DATE_TIME_CUSTOM = "yyyyMMddHHmmss";

  public static List<String> stringToList(String value, boolean cutBracketFirst,
      boolean cutBracketEnd) {
    List<String> values = new ArrayList<String>();
    if (isNOTNullEmpty(value)) {
      value = value.trim();
      if (cutBracketFirst) {
        value = cutBracketMark(value, true, false);
      }
      if (cutBracketEnd) {
        value = cutBracketMark(value, false, true);
      }
      String[] arr = splitByCharacter(value, COMMA);
      values = arrayToList(arr, true);
    }
    return values;
  }

  public static String cutBracketMark(String value, boolean isFirst, boolean isEnd) {
    return StringUtils.cutBracketMark(value, isFirst, isEnd);
  }

  public static String addBracketMark(String value, boolean isFirst, boolean isEnd) {
    return StringUtils.addBracketMark(value, isFirst, isEnd);
  }

  /**
   * chia tach chuoi theo character
   * 
   * @param value
   * @param character : ky tu de chia tach
   * @return: mang gom nhung chu da duoc chia tach
   */
  public static String[] splitByCharacter(String value, String character) {
    String[] arr = null;
    if (isNOTNullEmpty(value) && character != null) {
      arr = value.split(character);
    }
    return arr;
  }

  public static List<String> arrayToList(String[] arr, boolean isTrim) {
    List<String> values = new ArrayList<String>();
    if (isNOTNullEmpty(arr)) {
      if (isTrim) {
        arr = trimElementArray(arr);
      }
      for (int i = 0; i < arr.length; i++) {
        values.add(arr[i]);
      }
    }
    return values;
  }

  public static String[] trimElementArray(String[] arr) {
    if (isNOTNullEmpty(arr)) {
      for (int i = 0; i < arr.length; i++) {
        arr[i] = arr[i] == null ? arr[i] : arr[i].trim();
      }
    }
    return arr;
  }

  public static String trim(String value) {
    return StringUtils.trim(value);
  }

  public static List<?> addElementToList(List<?> values, Object element) {
    List<Object> objects = null;
    if (isNOTNullEmpty(values)) {
      objects = new ArrayList<Object>(values);
      objects.add(element);
    }
    return objects;
  }

  public static List<?> addElementToListNotCheck(List<?> values, Object element) {
    List<Object> objects = null;
    if (isNOTNullEmpty(values)) {
      objects = new ArrayList<Object>(values);
    } else {
      objects = new ArrayList<Object>();
    }
    objects.add(element);
    return objects;
  }

  public static List<?> addAllListToList(List<?> fromList, List<?> toList, boolean duplicate) {
    List<Object> objects = new ArrayList<Object>();
    if (toList != null) {
      objects = new ArrayList<Object>(toList);
      if (isNOTNullEmpty(fromList)) {
        if (duplicate) {
          objects.addAll(fromList);
        } else {
          for (Object objFrom : fromList) {
            boolean isDuplicate = false;
            for (Object objTo : toList) {
              if (objFrom.equals(objTo)) {
                isDuplicate = true;
                break;
              }
            }
            if (!isDuplicate) {
              objects.add(objFrom);
            }
            isDuplicate = false;
          }
        }
      }
    }
    return objects;
  }

  /**
   * Kiem tra Null va Empty
   * 
   * @param value
   * @return: true => value!=null && value != empty; false => con lai
   */
  public static boolean isNOTNullEmpty(String value) {
    if (value != null && !value.trim().isEmpty()) {
      return true;
    }
    return false;
  }

  public static boolean isNullOrEmpty(String value) {
    if (value == null || value.trim().isEmpty()) {
      return true;
    }
    return false;
  }

  /**
   * Kiem tra Null va Empty
   * 
   * @param values
   * @return: true => values!=null && values != empty; false => con lai
   */
  public static boolean isNOTNullEmpty(List<?> values) {
    if (values != null && !values.isEmpty()) {
      return true;
    }
    return false;
  }

  /**
   * Kiem tra Null va Empty
   * 
   * @param o
   * @return
   */
  public static boolean isNOTNullEmpty(Object o) {
    if (o != null) {
      if (o instanceof String) {
        return !((String) o).trim().isEmpty();
      }
      return true;
    }
    return false;
  }

  /**
   * Kiem tra Null va Empty
   * 
   * @param values
   * @return: true => values!=null && values.length > 0; false => con lai
   */
  public static boolean isNOTNullEmpty(Object[] values) {
    if (values != null && values.length > 0) {
      return true;
    }
    return false;
  }

  /**
   * Kiem tra Null va Empty va check them dieu kien phan tu dau tien != null
   * 
   * @param values
   * @return: true => values!=null && values.length > 0 && values[0] !=null; false => con lai
   */
  public static boolean isNOTNullEmptyCheckFirstElement(Object[] values) {
    if (values != null && values.length > 0 && values[0] != null) {
      return true;
    }
    return false;
  }

  /**
   * Kiem tra Null va Empty
   * 
   * @param map
   * @return: true => value!=null && value != empty; false => con lai
   */
  public static boolean isNOTNullEmpty(Map<?, ?> map) {
    if (map != null && !map.isEmpty()) {
      return true;
    }
    return false;
  }

  /**
   * Kiem tra Null va Empty cua cac truong (level 1 nhung truong nam truc tiep trong object, khong
   * gom nhung truong nam trong class con) String trong 1 object
   * 
   * @param object
   * @return
   * @throws Exception
   */
  public static Boolean isNOTNullEmptyAllElement(Object object) throws Exception {
    if (object == null)
      return false;
    Class<? extends Object> c = object.getClass();
    try {
      for (PropertyDescriptor propertyDescriptor : Introspector.getBeanInfo(c, Object.class)
          .getPropertyDescriptors()) {
        Method method = propertyDescriptor.getReadMethod();
        if (method.getReturnType().equals(String.class)) {
          if (isNOTNullEmpty((String) method.invoke(object)))
            return true;
        }
      }

    } catch (Exception e) {
      throw new Exception("Error isNOTNullEmptyAllElement : " + e, e);
    }
    return false;
  }

  public static boolean isExistsIn(Map<?, ?> map, Object value, boolean compareToKey,
      boolean compareToValue) {
    boolean hasKey = false;
    boolean hasValue = false;
    if (isNOTNullEmpty(map) && value != null) {
      hasKey = compareToKey ? isExistsInKey(map, value) : false;
      hasValue = compareToValue ? isExistsInValue(map, value) : false;
      if (compareToKey && compareToValue) {
        return (hasKey & hasValue);
      } else if (compareToKey) {
        return hasKey;
      } else if (compareToValue) {
        return hasValue;
      }
    }
    return false;
  }

  public static boolean isExistsInKey(Map<?, ?> map, Object value) {
    if (isNOTNullEmpty(map) && value != null) {
      for (Map.Entry<?, ?> entry : map.entrySet()) {
        if (value.equals(entry.getKey())) {
          return true;
        }
      }
      return false;
    }
    return false;
  }

  public static boolean isExistsInValue(Map<?, ?> map, Object value) {
    if (isNOTNullEmpty(map) && value != null) {
      for (Map.Entry<?, ?> entry : map.entrySet()) {
        if (value.equals(entry.getValue())) {
          return true;
        }
      }
      return false;
    }
    return false;
  }

  public static String getValueByKey(Map<?, ?> map, Object key) {
    return StringUtils.getValueByKey(map, key);
  }

  public static <T> List<T> getValuesByKey(Map<?, List<T>> map, Object key) {
    if (isNOTNullEmpty(map)) {
      for (Map.Entry<?, List<T>> entry : map.entrySet()) {
        if (entry.getKey().equals(key)) {
          return (List<T>) entry.getValue();
        }
      }
    }
    return null;
  }

  /**
   * parse tu string ve kieu boolean
   * 
   * @param value
   * @return: true => value.upper = TRUE || value = 1; false => con lai
   */
  public static boolean stringToBoolean(String value) {
    if (isNOTNullEmpty(value)) {
      if (TRUE.equalsIgnoreCase(value) || NUMBER_1.equals(value)) {
        return true;
      }
      return false;
    }
    return false;
  }

  public static boolean isString(Object object) {
    if (object instanceof String) {
      return true;
    }
    return false;
  }

  public static boolean isNumber(String value) {
    return StringUtils.isNumber(value);
  }

  public static boolean isCurrency(String value) {
    return StringUtils.isCurrency(value);
  }

  public static BigDecimal currencyToBigDecimal(String value) {
    if (isCurrency(value)) {
      return new BigDecimal(value.replace(COMMA, ""));
    }
    return new BigDecimal("0");
  }

  public static String currencyToString(String value) {
    return StringUtils.currencyToString(value);
  }

  public static String formatCurrencyEdittion(String value) {
    return StringUtils.formatCurrencyEdittion(value);
  }

  public static String formatCurrencyVN(BigDecimal value) {
    return StringUtils.formatCurrencyVN(value);
  }

  public static String formatCurrencyVN(String value) {
    return StringUtils.formatCurrencyVN(value);
  }

  public static String formatCurrency(BigDecimal value) {
    return StringUtils.formatCurrency(value);
  }

  public static String formatCurrency(String value) {
    return StringUtils.formatCurrency(value);
  }

  public static BigDecimal stringToBigDecimal(String value) {
    if (isNumber(value)) {
      return new BigDecimal(value);
    }
    return new BigDecimal("0");
  }

  public static BigDecimal add(BigDecimal value1, String value2) {
    return value1.add(stringToBigDecimal(value2));
  }

  public static void sortByField(List<?> list, String fieldName, final boolean isDesc,
      final String dateTimeFormatOrigin) {
    sort(list, fieldName, isDesc, dateTimeFormatOrigin);
  }

  public static void sortByField(Object[] arr, String fieldName, final boolean isDesc,
      final String dateTimeFormatOrigin) {
    List<?> list = null;
    if (isNOTNullEmpty(arr)) {
      list = Arrays.asList(arr);
    }
    sort(list, fieldName, isDesc, dateTimeFormatOrigin);
  }

  public static void sort(List<?> list, String fieldName, final boolean isDesc,
      final String dateTimeFormatOrigin) {
    if (isNOTNullEmpty(list) && isNOTNullEmpty(fieldName)) {
      final String getter = getGetterByFieldName(fieldName);
      final boolean isCompareDateString = (isNOTNullEmpty(dateTimeFormatOrigin)) ? true : false;
      final SimpleDateFormat dateFormatCustom = new SimpleDateFormat(FORMAT_DATE_TIME_CUSTOM);
      Collections.sort(list, new Comparator<Object>() {

        @SuppressWarnings("unchecked")
        public int compare(Object object1, Object object2) {
          try {
            if (object1 != null && object2 != null) {
              object1 =
                  object1.getClass().getMethod(getter, new Class[0]).invoke(object1, new Object[0]);
              object2 =
                  object2.getClass().getMethod(getter, new Class[0]).invoke(object2, new Object[0]);
              if (isCompareDateString && object1 != null && object2 != null) {
                try {
                  SimpleDateFormat dateFormat = new SimpleDateFormat(dateTimeFormatOrigin);
                  object1 = dateFormatCustom.format(dateFormat.parse(object1.toString()));
                  object2 = dateFormatCustom.format(dateFormat.parse(object2.toString()));
                } catch (ParseException e1) {
                  throw new RuntimeException("Format date time error: " + object1 + " and "
                      + object2 + " :" + e1);
                }
              }

            }
            return ((object1 == null) ? -1 : ((object2 == null) ? 1
                : (isDesc ? (-1 * ((Comparable<Object>) object1).compareTo(object2))
                    : ((Comparable<Object>) object1).compareTo(object2))));
          } catch (Exception e) {
            throw new RuntimeException("Cannot compare " + object1 + " with " + object2 + " on "
                + getter + " :" + e, e);
          }
        }
      });
    }
  }

  public static String getGetterByFieldName(String fieldName) {
    return StringUtils.getGetterByFieldName(fieldName);
  }

  public static boolean isDuplicateInList(List<?> list, Object object) {
    if (isNOTNullEmpty(list) && object != null) {
      for (Object obj : list) {
        if (obj.equals(object)) {
          return true;
        }
      }
    }
    return false;
  }

  public static <T> List<T> asList(T[] source) {
    List<T> objects = null;
    if (isNOTNullEmpty(source)) {
      objects = new LinkedList<T>(Arrays.asList(source));
    }
    return objects;
  }

  public static <T> List<T> asList(T[] source, boolean checkFirstElement) {
    List<T> objects = new LinkedList<T>();
    if (isNOTNullEmpty(source)) {
      if (checkFirstElement) {
        if (source[0] != null)
          objects = new LinkedList<T>(Arrays.asList(source));
      } else {
        objects = new LinkedList<T>(Arrays.asList(source));
      }
    }
    return objects;
  }

  public static String appendStringByChar(String str1, String str2, String charC) {
    return StringUtils.appendStringByChar(str1, str2, charC);
  }

  public static Double StringToDouble(String value) {
    if (isNumber(value)) {
      return Double.valueOf(value);
    }
    return null;
  }

  public static boolean existCharAtLast(String value, char c) {
    if (isNOTNullEmpty(value)) {
      if (value != null && value.charAt(value.length() - 1) == c) {
        return true;
      }
    }
    return false;
  }

  /**
   * Parse the String output to a org.w3c.dom.Document and be able to reach every node with the
   * org.w3c.dom API
   * 
   * @param xml
   * @return
   * @throws Exception
   */
  public static Document parseXmlToDocument(String xml) throws Exception {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbf.newDocumentBuilder();
    InputSource is = new InputSource(new StringReader(xml));
    return db.parse(is);
  }

  public static JSONArray appendArray(JSONArray arr1, JSONArray arr2) {
    JSONArray jsonArray = new JSONArray();
    appendArrayToSource(jsonArray, arr1);
    appendArrayToSource(jsonArray, arr2);
    return jsonArray;
  }

  public static JSONArray appendArrayToSource(JSONArray source, JSONArray extra) {
    if (source != null && extra != null) {
      for (Object o : extra) {
        source.put(o);
      }
    }
    return source;
  }

  public static org.jsoup.nodes.Document parseStringToDocument(String html) throws Exception {
    org.jsoup.nodes.Document document = null;
    if (UtilsClass.isNOTNullEmpty(html)) {
      document = (org.jsoup.nodes.Document) Jsoup.parse(html);
    }
    return document;
  }

}
