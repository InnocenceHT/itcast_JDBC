package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/*

 */
public class JDBCDemo05 {
    public static void main(String[] args) {
        Statement stmt = null;
        Connection conn = null;

        try {
            //1.注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            //2.定义sql语句
            String sql = "create table student (id int, name varchar(20))";

            //3.获取连接对象
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db1?serverTimezone=UTC&useSSL=true", "root", "admin");

            //4.获取执行sql的对象Statement
            stmt = conn.createStatement();

            //5.执行sql
            int count = stmt.executeUpdate(sql);

            //6.处理结果
            System.out.println(count);


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            //7.释放资源

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}
