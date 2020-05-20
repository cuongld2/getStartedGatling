package common.db.blog;

import common.db.Base;

import java.util.List;
import java.util.Map;

public class BlogSimple extends Blog {

    private Base base = new Base();

    public List<Map<String, Object>> getAllUserInfo() throws Exception {

        String query = "select * from user_info";
        return base.executeQuery(blogDbConnect.getSession(), query);
    }

    public void clearUserInfoByUsername(String username) {

        String query = String.format("delete from user_info where username = '%s'", username);
        base.queryNoReturn(blogDbConnect.getSession(), query);
    }

    public void clearBlogByTitle(String title){

        String query = String.format("delete from blog where title = '%s'", title);
        base.queryNoReturn(blogDbConnect.getSession(), query);

    }

}
