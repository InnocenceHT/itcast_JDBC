package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/*
    account添加一条记录 insert语句
 */
public class JDBCDemo02 {

    public static void main(String[] args) {

        Statement stmt = null;
        Connection conn = null;
        try {
            //1.注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            //2.定义sql
            String sql = "insert into account values(null,'王五',3000)";

            //3.获取Connection对象
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db1?serverTimezone=UTC&useSSL=true", "root", "admin");

            //4.获取执行sql的对象Statement
            stmt = conn.createStatement();

            //5.执行sql
            int count = stmt.executeUpdate(sql);//影响的行数

            //6.处理结果
            System.out.println(count);
            if (count > 0) {
                System.out.println("添加成功！");
            }else {
                System.out.println("添加失败！");
            }


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
//            stmt.close();
            //7.释放资源
            //避免空指针异常
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            if (stmt != null) {
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}
