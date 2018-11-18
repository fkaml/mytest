package com.itheima.dao.Impl;

import com.itheima.dao.BookDao;
import com.itheima.domain.Book;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao {

    //查询数据库数据(使用jdbc)
    public List<Book> queryBookList() {
        // 数据库链接
        Connection connection = null;
        // 预编译statement
        PreparedStatement preparedStatement = null;
        // 结果集
        ResultSet resultSet = null;
        //图书列表
        List<Book> list = new ArrayList<Book>();

        try {
            // 加载数据库驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 连接数据库
            connection = DriverManager.getConnection("jdbc:mysql:///day01Lucene","root","123456");
            // sql语句
            String sql = "select * from book";
            // 创建preparedStatement
            preparedStatement = connection.prepareStatement(sql);
            // 获取结果集
            resultSet = preparedStatement.executeQuery();
            // 创建book实例
            Book book = null;
            // 结果集遍历
            while(resultSet.next()){
                book = new Book();
                book.setId(resultSet.getInt(1));
                book.setName(resultSet.getString(2));
                book.setPrice(resultSet.getFloat(3));
                book.setPic(resultSet.getString(4));
                book.setDesc(resultSet.getString(5));
                list.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != resultSet){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (null != preparedStatement){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (null != connection){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return list;
    }
}
