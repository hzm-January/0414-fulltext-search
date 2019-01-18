package houzm.accumulation.lucene.util;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Paths;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;

/**
 * author: hzm_dream@163.com
 * date: 2019/1/18 14:47
 * description:
 */
public class WriterUtil implements Closeable {
    private static IndexWriter indexWriter;
    private static final String INDEX_DIR_PATH = "E:\\data-space\\full-text-search-data-space\\lucene\\index";

    public static IndexWriter writer() {
        FSDirectory indexDir = null;
        try {
            indexDir = FSDirectory.open(Paths.get(INDEX_DIR_PATH));
            StandardAnalyzer analyzer = new StandardAnalyzer();
            IndexWriterConfig indexWriterConfigure = new IndexWriterConfig(analyzer);
            //indexWriterConfigure.setOpenMode(IndexWriterConfig.OpenMode.CREATE); //如果加上这一行，将不会进行条件筛选，并全部删除
            indexWriter = new IndexWriter(indexDir, indexWriterConfigure);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return indexWriter;
    }

    @Override
    public void close() throws IOException {
        if (indexWriter != null) {
            indexWriter.close();
        }
    }
}
