package common;

import org.apache.log4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Logging {

  private Logger logger;

  public Logging() {
    logger = Logger.getLogger(Logging.class);
  }

  public Logging(Class<?> clazz) {
    logger = Logger.getLogger(clazz);
  }

  public static StringWriter getStackMessage(Exception e) {
    StringWriter stack = new StringWriter();
    e.printStackTrace(new PrintWriter(stack));
    return stack;
  }

  public void fatal(String msg) {
    logger.fatal(msg);
  }

  public void error(String msg) {
    logger.error(msg);
  }

  public void error(String msg, Exception e) {
    logger.error(msg, e);
  }

  public void error(Exception e) {
    logger.error(null, e);
  }

  public void warn(String msg) {
    logger.warn(msg);
  }

  public void info(String msg) {
    logger.info(msg);
  }

  public void debug(String msg) {
    logger.debug(msg);
  }

  public void trace(String msg) {
    logger.trace(msg);
  }

}
