package common.db.entity;

import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.type.StandardBasicTypes;

import java.sql.Types;

public class Oracle10gEditionDialect extends Oracle10gDialect {

  public Oracle10gEditionDialect() {
    super();
    registerHibernateType(Types.NCHAR, StandardBasicTypes.CHARACTER.getName());
    registerHibernateType(Types.NCHAR, 1, StandardBasicTypes.CHARACTER.getName());
    registerHibernateType(Types.NCHAR, 255, StandardBasicTypes.STRING.getName());
    registerHibernateType(Types.NVARCHAR, StandardBasicTypes.STRING.getName());
    registerHibernateType(Types.LONGNVARCHAR, StandardBasicTypes.TEXT.getName());
    registerHibernateType(Types.NCLOB, StandardBasicTypes.CLOB.getName());

  }

}
