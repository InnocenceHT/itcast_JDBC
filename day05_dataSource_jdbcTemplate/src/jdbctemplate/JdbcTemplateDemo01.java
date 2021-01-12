package jdbctemplate;

import datasource.utils.JDBCUtils;
import org.springframework.jdbc.core.JdbcTemplate;

/*
    JdbcTemplate入门
 */
public class JdbcTemplateDemo01 {

    public static void main(String[] args) {
        //1.导入jar包
        //2.创建JdbcTemplate对象
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDatasource());

        //3.调用方法
        String sql = "update account set balance = 5000 where id = ?";
        int count = template.update(sql, 3);
        System.out.println(count);
    }
}
