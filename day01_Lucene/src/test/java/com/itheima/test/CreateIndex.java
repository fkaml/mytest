package com.itheima.test;

import com.itheima.dao.BookDao;
import com.itheima.dao.Impl.BookDaoImpl;
import com.itheima.domain.Book;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;


import java.io.File;
import java.util.List;


public class CreateIndex {
    @Test
    public void testCreateIndex() throws Exception {
        createIndex(new StandardAnalyzer());
    }

    //使用中文分词器IKAnalyzer
    @Test
    public void testCreateIndexWithIK() throws Exception {
        createIndex(new IKAnalyzer());
    }

    private void createIndex(Analyzer analyzer) throws Exception {
        // 构建索引存放的目录
        Directory directory = FSDirectory.open(new File("d:/index").toPath());
        //Directory d, 目录：索引库存放的目录
        // IndexWriterConfig conf  配置信息
        //Analyzer analyzer 分词器, 词汇的提取
        // StandardAnalyzer lucene自带的分词器
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        // 构建索引，操作索引
        IndexWriter indexWriter = new IndexWriter(directory,indexWriterConfig);

        // 查询数据库获取数据
        BookDao bookDao = new BookDaoImpl();
        List<Book> books = bookDao.queryBookList();
        // 添加文档
        Document doc = null;
        for (Book book : books) {
            doc = new Document();
            // name field name 域的名称， 相当jave bean中的属性
            // value string value 值,属性对应的值
            // store Store.YES if the content should also be stored， 是否要存储到索引库中
            // 不分词，不索引，要存储
            doc.add(new StoredField("id",book.getId()));
            // 要分词，要索引，要存储
            doc.add(new TextField("name",book.getName(), Field.Store.YES));
            // 要分词，要索引，要存储
            doc.add(new FloatField("price",book.getPrice(), Field.Store.YES));
            // 不分词，不索引，要存储
            doc.add(new StoredField("pic",book.getPic()));
            // 要分词，要索引，不存储
            doc.add(new TextField("desc",book.getDesc(), Field.Store.YES));
            // 添加到文档域中, 同时就会创建索引域
            indexWriter.addDocument(doc);
        }

        // 释放资源
        indexWriter.close();
    }
}
