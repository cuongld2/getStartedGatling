package common.db;

import org.hibernate.Session;

import java.util.List;
import java.util.Map;

public class Base {

    public List<Map<String, Object>> executeQuery(Session session, String query) {
        try {
            return DatabaseHBUtils.getResultMapped(session, query, false);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public void queryNoReturn(Session session, String query) {

        try {
            DatabaseHBUtils.executeUpdateWithTransaction(session, query, false);
        } catch (Exception ex) {

            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }

    }

}
