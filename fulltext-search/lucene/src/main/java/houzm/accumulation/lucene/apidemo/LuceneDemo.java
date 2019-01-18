package houzm.accumulation.lucene.apidemo;

import houzm.accumulation.lucene.data.Datas;
import houzm.accumulation.lucene.data.User;

import java.io.IOException;
import java.nio.file.Paths;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;

/**
 * author: hzm_dream@163.com
 * date: 2019/1/15 13:11
 * description: 创建索引
 */
public class LuceneDemo {
    private static final String INDEX_DIR_PATH = "E:\\data-space\\full-text-search-data-space\\lucene\\index";

    public static void main(String[] args) throws IOException {
        //索引
        index();
        index();
        index();
        index();
        //查询
        docInfo();
        //更新--先删除再添加
//        updateDoc();
//        docInfo();
        //删除document--放入回收站--还可回复
        delectDoc();
        docInfo();
        //恢复回收站--暂时没找到当前版本的方法

        //清空回收站
//        forceDelete();
//        docInfo();
        //merge合并优化
        //不建议使用，会消耗大量资源，超过一定数量，lucene会自动优化
        //forcemerge会自动清空回收站已经删除的document
//        forceMerge();
//        docInfo();

    }

    private static void updateDoc() throws IOException {
        IndexWriter indexWriter = getIndexWriter();
        Document document = new Document();
        document.add(new StringField("id", "121", Field.Store.YES));
        document.add(new StringField("addr", "Ireland", Field.Store.YES));
        document.add(new StringField("phone", "12345678909", Field.Store.YES));
        document.add(new StringField("name", "Doroles", Field.Store.YES));
//        indexWriter.updateDocument(new Term("name", "tom"), document); //Tom 写为tom 执行结果是添加一条document，并没有删除
        indexWriter.updateDocument(new Term("name", "Tom"), document);
        indexWriter.close();
    }

    private static void forceMerge() throws IOException {
        IndexWriter indexWriter = getIndexWriter();
        indexWriter.forceMerge(2);
        indexWriter.close();
    }

    private static void forceDelete() throws IOException {
        IndexWriter indexWriter = getIndexWriter();
        indexWriter.forceMergeDeletes();
        indexWriter.close();
    }

    private static IndexWriter getIndexWriter() throws IOException {
        IndexWriterConfig indexWriterConfigure = new IndexWriterConfig(new StandardAnalyzer());
//        indexWriterConfigure.setOpenMode(IndexWriterConfig.OpenMode.CREATE); //如果加上这一行，将不会进行条件筛选，并全部删除
        return new IndexWriter(FSDirectory.open(Paths.get(INDEX_DIR_PATH)), indexWriterConfigure);
    }

    private static void delectDoc() throws IOException {
        FSDirectory indexDir = FSDirectory.open(Paths.get(INDEX_DIR_PATH));
        StandardAnalyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig indexWriterConfigure = new IndexWriterConfig(analyzer);
//        indexWriterConfigure.setOpenMode(IndexWriterConfig.OpenMode.CREATE); //如果加上这一行，将不会进行条件筛选，并全部删除
        IndexWriter indexWriter = new IndexWriter(indexDir, indexWriterConfigure);
        indexWriter.deleteDocuments(new Term("name", "Jack"));
//        indexWriter.flush();
//        indexWriter.commit();
        indexWriter.close();
    }


    private static void docInfo() throws IOException {
        System.out.println("----------------------------------------");
        // 1. 目录
        IndexReader indexReader = DirectoryReader.open(FSDirectory.open(Paths.get(INDEX_DIR_PATH)));
//        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
//        indexSearcher.search(query, n);
        System.out.println("numDocs: " + indexReader.numDocs());
        System.out.println("maxDocs: " + indexReader.maxDoc());
        System.out.println("numDeletedDocs: " + indexReader.numDeletedDocs());
    }

    private static void index() throws IOException {
        //1. 目录
        FSDirectory indexDir = FSDirectory.open(Paths.get(INDEX_DIR_PATH));
        //2. writer
        StandardAnalyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig indexWriterConfigure = new IndexWriterConfig(analyzer);
        indexWriterConfigure.setOpenMode(IndexWriterConfig.OpenMode.APPEND); //append追加
//        indexWriterConfigure.setOpenMode(IndexWriterConfig.OpenMode.CREATE); //create创建(创建会先清空索引目录中的内容)
        IndexWriter indexWriter = new IndexWriter(indexDir, indexWriterConfigure);
        //3. field & document
//        indexWriter.deleteAll(); //在写入前，清空索引目录中的内容
        Datas datas = new Datas();
        Document document = null;
        for (User user : datas.getUsersList()) {
            document = new Document();
            document.add(new StringField("id", user.getId(), Field.Store.YES));
            document.add(new StringField("addr", user.getAddr(), Field.Store.YES));
            document.add(new LongPoint("phone", user.getPhone()));
            document.add(new StringField("name", user.getName(), Field.Store.YES));
            document.add(new StringField("descript", user.getDescript(), Field.Store.NO));
            indexWriter.addDocument(document);
        }
        indexWriter.close();
    }
}
