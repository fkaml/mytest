package com.itheima.test;

import com.itheima.dao.Impl.BookDaoImpl;
import org.junit.Test;

public class TestLucene {
    @Test
    public void testJDBC(){
        BookDaoImpl bookDao = new BookDaoImpl();
        System.out.println(bookDao.queryBookList().toString());
    }
}
