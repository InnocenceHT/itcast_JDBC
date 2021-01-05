package jdbc;

import java.sql.*;

/*

 */
public class JDBCDemo06 {
    public static void main(String[] args) {
        Statement stmt = null;
        Connection conn = null;
        ResultSet rs = null;

        try {
            //1.注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            //2.定义sql语句
            String sql = "select * from account";

            //3.获取连接对象
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db1?serverTimezone=UTC&useSSL=true", "root", "admin");

            //4.获取执行sql的对象Statement
            stmt = conn.createStatement();

            //5.执行sql
            rs = stmt.executeQuery(sql);
            //6.处理结果
            //6.1 让游标向下移动一行
            rs.next();
            //6.2 获取数据
            int id = rs.getInt(1);
            String name = rs.getString("name");
            double balance = rs.getDouble(3);

            System.out.println(id + "---" + name + "---" + balance);


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            //7.释放资源
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }


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
