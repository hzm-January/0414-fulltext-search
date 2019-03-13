package houzm.accumulation.lucene.apidemo;

import houzm.accumulation.lucene.data.Datas;
import houzm.accumulation.lucene.data.User;
import houzm.accumulation.lucene.util.ReaderUtil;
import houzm.accumulation.lucene.util.WriterUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;

/**
 * author: hzm_dream@163.com
 * date: 2019/1/18 14:46
 * description:
 */
public class ScoreDemo {

    private static Map<String, Float> scoreMap = new HashMap<>(16);

    static {
        scoreMap.put("tom", 2.0f);
        scoreMap.put("jack", 1.5f);
    }

    public static void main(String[] args) throws IOException {
        //1. 添加score
        IndexWriter indexWriter = WriterUtil.writer();
        Document document = null;
        for (User user : Datas.usersList) {
            document = new Document();
            document.add(new StringField("id", user.getId(), Field.Store.YES));
            document.add(new StringField("addr", user.getAddr(), Field.Store.YES));
            document.add(new LongPoint("phone", Long.valueOf(user.getPhone())));
            document.add(new StringField("name", user.getName(), Field.Store.YES));
            if (scoreMap.containsKey(user.getName())) {
//                document.setBoost(); //该方法已被删除
            }
            indexWriter.addDocument(document);
        }
        //2. 查找
        IndexReader reader = ReaderUtil.reader();
        IndexSearcher searcher = new IndexSearcher(reader);
        Query query = new TermQuery(new Term("name", "Tom"));
        TopDocs topDocs = searcher.search(query, 10);
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            Document doc = searcher.doc(scoreDoc.doc);
            String name = doc.get("name");
            String addr = doc.get("addr");
            String id = doc.get("id");
            System.out.println("id: "+ id +"name: " + name + "; addr: " + addr);
        }
    }
}
