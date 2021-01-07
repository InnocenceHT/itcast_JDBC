package jdbc;

import domain.Account;
import domain.Jobs;
import util.JDBCUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
    定义一个方法，查询jobs表的数据将其封装为对象，然后装载集合，返回
 */
public class JDBCDemo08 {

    public static void main(String[] args) {
        List<Account> list = new JDBCDemo08().finsAll2();
        System.out.println(list);
        System.out.println(list.size());
    }

    /*
            查询所有的jobs对象
         */
    public List<Jobs> finsAll() {
        ResultSet rs = null;
        Statement stmt = null;
        Connection conn = null;
        List<Jobs> list = null;
        try {
            //1.注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2.获取连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/myemployees?serverTimezone=UTC&useSSL=true", "root", "admin");
            //3.定义sql
            String sql = "select * from jobs";
            //4.获取执行sql的对象
            stmt = conn.createStatement();
            //5.执行sql
            rs = stmt.executeQuery(sql);
            //6.遍历结果集，封装对象，装载集合
            Jobs jobs = null;
            list = new ArrayList<Jobs>();
            while(rs.next()) {
                //获取数据
                String job_id = rs.getString("job_id");
                String job_title = rs.getString("job_title");
                int min_salary = rs.getInt("min_salary");
                int max_salary = rs.getInt("max_salary");

                //创建jobs对象，并赋值
                jobs = new Jobs();
                jobs.setJob_id(job_id);
                jobs.setJob_title(job_title);
                jobs.setMin_salary(min_salary);
                jobs.setMax_salary(max_salary);

                //装载集合
                list.add(jobs);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
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

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

        return list;
    }
    /*
        演示JDBC的工具类
     */

    public List<Account> finsAll2() {
        ResultSet rs = null;
        Statement stmt = null;
        Connection conn = null;
        List<Account> list = null;
        try {
            /*//1.注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2.获取连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db1?serverTimezone=UTC&useSSL=true", "root", "admin");*/
            conn = JDBCUtils.getConnection();

            //3.定义sql
            String sql = "select * from account";
            //4.获取执行sql的对象
            stmt = conn.createStatement();
            //5.执行sql
            rs = stmt.executeQuery(sql);
            //6.遍历结果集，封装对象，装载集合
            Account acc = null;
            list = new ArrayList<Account>();
            while(rs.next()) {
                //获取数据
                int id = rs.getInt("id");
                String name = rs.getString("NAME");
                int balance = rs.getInt("balance");

                //创建jobs对象，并赋值
                acc = new Account();
                acc.setId(id);
                acc.setName(name);
                acc.setBalance(balance);

                //装载集合
                list.add(acc);
            }

        } catch ( SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(rs,stmt,conn);
        }

        return list;
    }
}
