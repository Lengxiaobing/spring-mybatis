package com.mybatis.dao;

import java.sql.*;

/**
 * @Description: 原生Jdbc示例
 * @author: xiang
 * @date: 2019/6/19 16:57
 */
public class JdbcDemo {

    public static void main(String[] args) {
        // 数据库连接
        Connection connection = null;
        // 预编译SQL语句，提高数据库查询性能
        PreparedStatement preparedStatement = null;
        // 结果集
        ResultSet resultSet = null;
        try {
            // 加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 通过驱动管理类获取连接
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mybatis?serverTimezone=UTC&characterEncoding=utf8&useUnicode=true&useSSL=false&allowMultiQueries=true", "root", "root");

            // 定义sql，？表示占位符
            String sql = "SELECT * FROM user_info WHERE name = ?";

            // 预编译，获取Statement
            preparedStatement = connection.prepareStatement(sql);
            // 设置参数
            preparedStatement.setString(1, "小明");
            // 发送查询请求，获取查询结果
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                System.out.println(" id=" + resultSet.getInt("id")
                        + " name=" + resultSet.getString("name")
                        + " birth=" + resultSet.getString("birth")
                        + " sex=" + resultSet.getString("sex")
                        + " mobile=" + resultSet.getString("mobile")
                        + " mail=" + resultSet.getString("mail")
                );
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭结果集
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            // 关闭Statement
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            // 关闭连接
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}