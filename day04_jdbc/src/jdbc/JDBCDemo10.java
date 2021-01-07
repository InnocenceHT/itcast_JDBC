package jdbc;

import util.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
    JDBC控制事务：
        1.事务：一个包含多个步骤的业务操作。如果这个业务操作被事务管理，则这多个步骤要么同时成功，要么同时失败
        2.操作：
            1.开启事务
            2.提交事务
            3.回滚事务
        3.使用Connection对象来管理事务
            * 开启事务：setAutoCommit(boolean autoCommit):调用该方法设置参数为false，既开启事务
                *在执行sql之前开启事务
            * commit()
                *当所有sql都执行完成提交事务
            * rollback()
                *在catch中回滚事务
 */
public class JDBCDemo10 {

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;

        try {
            //1.获取连接
            conn = JDBCUtils.getConnection();
            //开启事务
            conn.setAutoCommit(false);

            //2.定义sql
            //2.1张三 - 500
            String sql1 = "update account set balance = balance - ? where id = ?";
            //2.2李四 + 500
            String sql2 = "update account set balance = balance + ? where id = ?";
            //3.获取执行sql对象
            pstmt1 = conn.prepareStatement(sql1);
            pstmt2 = conn.prepareStatement(sql2);
            //4.设置参数
            pstmt1.setDouble(1,500);
            pstmt1.setInt(2,1);

            pstmt2.setDouble(1,500);
            pstmt2.setInt(2,2);

            //5.执行sql
            pstmt1.executeUpdate();
            //手动制造异常
            int i = 3/0;

            pstmt2.executeUpdate();

        } catch (Exception e) {
            //事务回滚
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException exception) {
                e.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            //6.释放资源
            JDBCUtils.close(pstmt1,conn);
            JDBCUtils.close(pstmt2,null);
        }
    }
}
