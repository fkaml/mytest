package com.itheima.test;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class SearchTest {
    //搜索实现
    @Test
    public void testSearch() throws Exception {
        // 索引库在哪
        Directory directory = FSDirectory.open(new File("d:/index").toPath());
        // 读取索引库
        IndexReader reader = DirectoryReader.open(directory);
        // 查询
        IndexSearcher searcher = new IndexSearcher(reader);

        // 查询条件, name中有java select * from book where name like '%java%'
        // f  the default field for query terms. 默认查询时使用哪个列，域，字段
        // a   used to find terms in the query text.  分词器
        Analyzer analyzer = new StandardAnalyzer();// 注意，构建时使用哪个分词器，查询时也要使用相同的分词器
        QueryParser queryParser = new QueryParser("name",analyzer);
        // 查询条件
        Query query = queryParser.parse("name:java OR desc:java");//queryParser.parse("desc:java");
        // n: 获取多少条记录
        TopDocs topDocs = searcher.search(query,5);
        // 从索引库查询得到 符合条件的文档 下标
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            // 文档的下标
            int doc = scoreDoc.doc;
            // 通过下标 获取文档数据
            Document document = searcher.doc(doc);
            // String.format格式化输出%s站位符
            System.out.println(String.format("id=%s,name=%s,price=%s",document.get("id"), document.get("name"), document.get("price")));
        }
        reader.close();
    }


    private void search(Query  query) throws Exception {
        // 索引库在哪
        Directory directory = FSDirectory.open(new File("d:/index").toPath());
        // 读取索引库
        IndexReader reader = DirectoryReader.open(directory);
        // 查询
        IndexSearcher searcher = new IndexSearcher(reader);

        // 查询条件, name中有java select * from book where name like '%java%'
        // f  the default field for query terms. 默认查询时使用哪个列，域，字段
        // a   used to find terms in the query text.  分词器
        //Analyzer analyzer = new StandardAnalyzer();// 注意，构建时使用哪个分词器，查询时也要使用相同的分词器
        // n: 获取多少条记录
        TopDocs topDocs = searcher.search(query,5);
        // 从索引库查询得到 符合条件的文档 下标
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            // 文档的下标
            int doc = scoreDoc.doc;
            // 通过下标 获取文档数据
            Document document = searcher.doc(doc);
            // String.format格式化输出%s站位符
            System.out.println(String.format("id=%s,name=%s,price=%s",document.get("id"), document.get("name"), document.get("price")));
        }
        reader.close();
    }

    // TermQuery 词想查询
    @Test
    public void testTermQuery() throws Exception{
        TermQuery termQuery = new TermQuery(new Term("name", "java"));
        search(termQuery);
    }

    // 搜索逻辑抽取
    @Test
    public void testNumericRangeQuery() throws Exception {
        NumericRangeQuery<Float> query = NumericRangeQuery.newFloatRange("price", 55f, 66f, true, true);
        search(query);
    }

    @Test
    public void testBooleanQuery() throws Exception {
        // 1. 价格在55-66之间书籍
        Query query = new TermQuery(new Term("desc", "lucene"));

        NumericRangeQuery<Float> numericRangeQuery = NumericRangeQuery.newFloatRange("price", 55f, 66f, true, true);

        // 2. 集合查询
        BooleanQuery.Builder builder = new BooleanQuery.Builder();//内部内

        //Occur.MUST表示该查询约束必须满足
        builder.add(query,BooleanClause.Occur.MUST_NOT);
        builder.add(numericRangeQuery,BooleanClause.Occur.MUST_NOT);

        //4. 执行查询
        search(builder.build());

    }
}
