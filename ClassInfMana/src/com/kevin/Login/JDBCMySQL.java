package com.kevin.Login;

import java.sql.*;

/**
 * Created by hcnucai on 2016/12/16.
 */
//数据库的链接
public class JDBCMySQL {
    private Connection conn = null;
    private Statement statement;
    public static final String names = "com.mysql.jdbc.Driver";
    public void getConnection() {

        try {
            Class.forName(names);
            String jdbcPath = "jdbc:mysql://localhost:3306/student";
            String user = "root";
            String pass = "123456";
            try {
                conn = DriverManager.getConnection(jdbcPath, user, pass);
            }catch (SQLException e){
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //查询语句
    public ResultSet query(String sql){

        try {
            statement = conn.createStatement();
            ResultSet res = statement.executeQuery(sql);

            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
    }
//跟新语句
    public  int update(String sql){
        try {
            statement = conn.createStatement();
            int res = statement.executeUpdate(sql);

            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  0;
    }
    //插入或者删除
    public int deleteOrInsert(String sql){
        try {
            statement = conn.createStatement();
            int res = statement.executeUpdate(sql);
            return  res;
        }catch (SQLException e){
            e.printStackTrace();
            return  0;
        }
    }

    //关闭数据库的链接
    public void closeConnection() {
        try {
            statement.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
