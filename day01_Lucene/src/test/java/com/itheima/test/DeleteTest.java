package com.itheima.test;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;

public class DeleteTest {
    @Test
    public void testDelete() throws Exception {
        // 删除文档
        // 构建索引存放的目录
        Directory directory = FSDirectory.open(new File("d:/index").toPath());
        // Directory d , 目录: 索引库存放的目录
        // IndexWriterConfig conf 配置信息
        // Analyzer analyzer 分词器, 词汇的提取
        // StandardAnalyzer lucene自带的分词器
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(new IKAnalyzer());
        // 构建索引,操作索引
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        //根据条件来删除 delete from book where name = java
        // fld 域, 属性名,text: 值
        Term term = new Term("name", "java");
        // 删除文档
        indexWriter.deleteDocuments(term);
        //indexWriter.deleteAll();//删除所有

        indexWriter.close();
    }
}
