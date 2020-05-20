package common.db.blog;

import common.db.Base;
import common.db.HibernateEdition;


public abstract class Blog {

  private Base base = new Base();

  protected static HibernateEdition blogDbConnect = new HibernateEdition(
      "db/hibernate-blogdb.cfg.xml");

}
