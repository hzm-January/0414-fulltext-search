package houzm.accumulation.lucene.helloworld;

import java.io.IOException;
import java.nio.file.Paths;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
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
        index();
        docInfo();
        //查询
        //删除document
        delectDoc();
        docInfo();
    }

    private static void delectDoc() throws IOException {
        FSDirectory indexDir = FSDirectory.open(Paths.get(INDEX_DIR_PATH));
        StandardAnalyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig indexWriterConfigure = new IndexWriterConfig(analyzer);
//        indexWriterConfigure.setOpenMode(IndexWriterConfig.OpenMode.CREATE); a//如果加上这一行，将不会进行条件筛选，并全部删除
        IndexWriter indexWriter = new IndexWriter(indexDir, indexWriterConfigure);
        indexWriter.deleteDocuments(new Term("name", "Jack"));
        indexWriter.close();
    }


    private static void docInfo() throws IOException {
        // 1. 目录
        IndexReader indexReader = DirectoryReader.open(FSDirectory.open(Paths.get(INDEX_DIR_PATH)));
//        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
//        indexSearcher.search(query, n);
        System.out.println("numDocs: " + indexReader.numDocs());
        System.out.println("maxDocs: " + indexReader.maxDoc());
        System.out.println("maxDocs: " + indexReader.numDeletedDocs());
    }

    private static void index() throws IOException {
        //1. 目录
        FSDirectory indexDir = FSDirectory.open(Paths.get(INDEX_DIR_PATH));
        //2. writer
        StandardAnalyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig indexWriterConfigure = new IndexWriterConfig(analyzer);
        indexWriterConfigure.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        IndexWriter indexWriter = new IndexWriter(indexDir, indexWriterConfigure);
        //3. field & document

        Users users = new Users();
        Document document = null;
        for (User user : users.getUsersList()) {
            document = new Document();
            document.add(new StringField("id", user.getId(), Field.Store.YES));
            document.add(new StringField("addr", user.getAddr(), Field.Store.YES));
            document.add(new StringField("phone", user.getPhone(), Field.Store.YES));
            document.add(new StringField("name", user.getName(), Field.Store.YES));
            indexWriter.addDocument(document);
        }
        indexWriter.close();
    }
}
