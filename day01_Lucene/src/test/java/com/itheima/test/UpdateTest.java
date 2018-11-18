package com.itheima.test;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;


import java.io.File;

public class UpdateTest {

    @Test
    public void testIndexUpdate() throws Exception {
        // 创建分词器
        Analyzer analyzer = new IKAnalyzer();
        // 创建Directory流对象
        Directory directory = FSDirectory.open(new File("D:/index").toPath());
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        // 创建写入对象
        IndexWriter indexWriter = new IndexWriter(directory, config);

        // 创建Document
        Document document = new Document();
        document.add(new TextField("id","1002", Field.Store.YES));
        document.add(new TextField("name","lucene测试test 002", Field.Store.YES));

        // 执行更新,会把所有符合条件的Document删除,再新增.
        indexWriter.updateDocument(new Term("name","test"),document);

        //释放资源
        indexWriter.close();
    }

    @Test
    public void IndexUpdate() throws Exception {
        // 创建分词器
        Analyzer analyzer = new IKAnalyzer();
        // 创建Directory流对象
        Directory directory = FSDirectory.open(new File("D:/index").toPath());
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        // 创建写入对象
        IndexWriter indexWriter = new IndexWriter(directory, config);

        // 创建Document
        Document document = new Document();
        document.add(new TextField("id","1111", Field.Store.YES));
        document.add(new TextField("name","lucene测试test 003", Field.Store.YES));

        // 执行更新,会把所有符合条件的Document删除,再新增.
        indexWriter.updateDocument(new Term("name","传智播客"),document);

        //释放资源
        indexWriter.close();
    }
}
