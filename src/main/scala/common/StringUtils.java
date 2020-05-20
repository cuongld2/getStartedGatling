package common;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class StringUtils {

  private static final String REGEX_NUMBER =
      "^[0-9]$|^\\-[0-9]$|^[1-9][0-9]*$|^\\-[1-9][0-9]*$|^[0-9]\\.[0-9]{1,}$|^\\-[0-9]\\.[0-9]{1,}$|^[1-9][0-9]*\\.[0-9]{1,}$|^\\-[1-9][0-9]*\\.[0-9]{1,}$";
  private static final String REGEX_CURRENCY =
      "^[0-9]$|^[1-9][0-9]+$|^(?!0\\.00)[1-9][0-9]{0,2}(,[0-9]{3})*(\\.[0-9][0-9])?$|^[1-9][0-9]*(\\.[0-9][0-9])?$|^[0-9](\\.[0-9][0-9])?$";
  private static final String BRACKET_OPEN = "[";
  private static final String BRACKET_CLOSE = "]";
  private static final String FORMAT_CURRENCY_VN = "###,###";
  private static final String FORMAT_CURRENCY = "###,###.##";
  private static final String GET = "get";

  public static boolean isNumber(String value) {
    if (UtilsClass.isNOTNullEmpty(value)) {
      return value.matches(REGEX_NUMBER);
    }
    return false;
  }

  public static boolean isInteger(String value) {
    if (UtilsClass.isNOTNullEmpty(value) && value.contains(".")) {
      return false;
    }
    return isNumber(value);
  }

  public static boolean isCurrency(String value) {
    if (UtilsClass.isNOTNullEmpty(value)) {
      return value.matches(REGEX_CURRENCY);
    }
    return false;
  }

  public static String formatDate(Date date, String pattern) {
    if (UtilsClass.isNOTNullEmpty(pattern))
      return new SimpleDateFormat(pattern).format(date);
    return "";
  }

  public static String formatDateExactlyPattern(Date date, String pattern) {
    if (UtilsClass.isNOTNullEmpty(pattern)) {
      DateFormat format = new SimpleDateFormat(pattern);
      format.setLenient(false);
      return format.format(date);
    }
    return "";
  }

  /**
   * cat dau [ va ]
   * 
   * @param value
   * @param isFirst : cat dau [
   * @param isEnd :cat dau ]
   * @return: chuoi string da duoc cat
   */
  public static String cutBracketMark(String value, boolean isFirst, boolean isEnd) {
    if (UtilsClass.isNOTNullEmpty(value)) {
      if (isFirst) {
        if (BRACKET_OPEN.equals(value.substring(0, 1))) {
          value = value.substring(1, value.length());
        }
      }
      if (isEnd) {
        if (BRACKET_CLOSE.equals(value.substring(value.length() - 1))) {
          value = value.substring(0, value.length() - 1);
        }
      }
      return value;
    }
    return null;
  }

  public static String addBracketMark(String value, boolean isFirst, boolean isEnd) {
    if (UtilsClass.isNOTNullEmpty(value)) {
      StringBuilder builder = new StringBuilder(value);
      if (isFirst) {
        builder.insert(0, BRACKET_OPEN);
      }
      if (isEnd) {
        builder.insert(builder.length(), BRACKET_CLOSE);
      }
      return builder.toString();
    }
    return value;
  }

  public static String trim(String value) {
    if (UtilsClass.isNOTNullEmpty(value)) {
      value = value.trim();
    }
    return value;
  }

  public static String getValueByKey(Map<?, ?> map, Object key) {
    if (UtilsClass.isNOTNullEmpty(map)) {
      for (Map.Entry<?, ?> entry : map.entrySet()) {
        if (entry.getKey().equals(key)) {
          return (String) entry.getValue();
        }
      }
    }
    return null;
  }

  public static String currencyToString(String value) {
    if (UtilsClass.isNullOrEmpty(value)) {
      return null;
    }
    if (isCurrency(value)) {
      return value.replace(UtilsClass.COMMA, "");
    }
    return "0";
  }

  public static String formatCurrencyEdittion(String value) {
    if (UtilsClass.isNOTNullEmpty(value)) {
      if (value.contains(UtilsClass.DOT))
        return formatCurrency(value);
      return formatCurrencyVN(value);
    }
    return value;
  }

  public static String formatCurrencyVN(BigDecimal value) {
    String money = "";
    if (value != null) {
      DecimalFormat format = new DecimalFormat(FORMAT_CURRENCY_VN);
      // money = format.format(value).replace(COMMA, DOT);
      money = format.format(value);
    }
    return money;
  }

  public static String formatCurrencyVN(String value) {
    if (isNumber(value)) {
      return formatCurrencyVN(UtilsClass.stringToBigDecimal(value));
    }
    return value;
  }

  public static String formatCurrency(BigDecimal value) {
    String money = "";
    if (value != null) {
      DecimalFormat format = new DecimalFormat(FORMAT_CURRENCY);
      money = format.format(value);
    }
    return money;
  }

  public static String formatCurrency(String value) {
    if (isNumber(value)) {
      return formatCurrency(UtilsClass.stringToBigDecimal(value));
    }
    return value;
  }

  public static String getGetterByFieldName(String fieldName) {
    StringBuilder builder = new StringBuilder();
    if (UtilsClass.isNOTNullEmpty(fieldName)) {
      builder.append(GET).append(fieldName.substring(0, 1).toUpperCase())
          .append(fieldName.substring(1));
    }
    return builder.toString();
  }

  public static String appendStringByChar(String str1, String str2, String charC) {
    StringBuilder builder = new StringBuilder();
    builder.append(str1).append(charC).append(str2);
    return builder.toString();
  }

  /**
   * lay gia tri string trim
   * 
   * @param value
   * @return
   */
  public static String getSafeValue(String value) {
    return UtilsClass.isNullOrEmpty(value) ? "" : value.trim();
  }

}
