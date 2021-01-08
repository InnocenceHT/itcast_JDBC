package jdbc;

import util.JDBCUtils;
import java.sql.*;
import java.util.Scanner;

/*
    练习：
        需求：
            1.通过键盘录入用户名和密码
            2.判断用户是否登录成功
 */


public class JDBCDemo09 {

    public static void main(String[] args) {
        //1.键盘录入，接受用户名和密码
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String username = sc.nextLine();
        System.out.println("请输入密码：");
        String password = sc.nextLine();

        //2.调用方法
        boolean flag = new JDBCDemo09().login2(username, password);

        //3.判断结果输出不同语句
        if (flag) {
            //登录成功
            System.out.println("登录成功！");
        }else {
            System.out.println("用户名或密码错误！");
        }

    }

    /*
        登录方法
        JDBC方法：
            1.注册驱动
            2.获取数据库连接对象
            3.创建Statement对象
            4.定义sql
            5.获取执行sql的对象
            6.释放资源
     */
    /*
        登录方法
     */
    public boolean login(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        //连接数据库判断是否登录成功
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        //1.获取连接
        try {
            conn = JDBCUtils.getConnection();

            //2.定义sql
            String sql = "select * from user where username = '"+username+"' and password = '"+password+"' ";

            //3.获取执行sql的对象
            stmt = conn.createStatement();

            //4.执行查询
            rs = stmt.executeQuery(sql);

            //5.判断
            /*if (rs.next()) {//如果有下一行则返回true
                return true;
            }else {
                return false;
            }*/

            return rs.next();//如果有下一行，则返回true



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtils.close(rs,stmt,conn);
        }

        return false;

    }

    /*
        登录方法,使用PreparedStatement实现

        PreparedStatement:执行sql的对象
        1.SQL注入问题：在拼接sql时，有一些sql的特殊关键字参与字符串的拼接。会造成安全性的问题
            1.输入用户随便，输入密码：a' or 'a' = 'a
            2.sql:select * from user where username = 'afjasdjhf' and password = 'a or 'a' = 'a;

        2.解决sql注入问题：使用PrepareStatement对象来解决
        3.预编译的SQL：参数使用?作为占位符
        4.步骤：
            1.导入驱动jar包
            2.注册驱动
            3.获取数据库连接对象Connection
            4.定义sql
                * 注意：sql的参数使用?作为占位符。如：select * from user where username = ? and password = ?;
            5.获取执行sql语句的对象 PrepareStatement Connection.prepareStatement(String sql)
            6.给?赋值：
                * 方法：setXxx(参数1,参数2)
                    *参数1：？的位置编号，从1开始
                    *参数2：？的值

            7.执行sql,接受返回结果，不需要传递sql语句
            8.处理结果
            9.释放资源

        5.后期都会使用PreparedStatement来完成增删改查的所有操作
            1.可以防止sql注入
            2.效率更高
     */

    public boolean login2(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        //连接数据库判断是否登录成功
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        //1.获取连接
        try {
            conn = JDBCUtils.getConnection();

            //2.定义sql
            String sql = "select * from user where username = ? and password = ?";

            //3.获取执行sql的对象
            pstmt = conn.prepareStatement(sql);

            //给?赋值
            pstmt.setString(1,username);
            pstmt.setString(2,password);

            //4.执行查询,不需要传递sql
            rs = pstmt.executeQuery();

            //5.判断
            /*if (rs.next()) {//如果有下一行则返回true
                return true;
            }else {
                return false;
            }*/

            return rs.next();//如果有下一行，则返回true

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtils.close(rs,pstmt,conn);
        }
        return false;
    }

}
