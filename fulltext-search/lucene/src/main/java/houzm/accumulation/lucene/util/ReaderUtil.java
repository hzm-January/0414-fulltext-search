package houzm.accumulation.lucene.util;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Paths;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

/**
 * author: hzm_dream@163.com
 * date: 2019/1/18 14:50
 * description:
 */
public class ReaderUtil implements Closeable {
    private static final String INDEX_DIR_PATH = "E:\\data-space\\full-text-search-data-space\\lucene\\index";
    private static IndexReader indexReader;

    public static IndexReader reader() {
        try {
            indexReader = DirectoryReader.open(FSDirectory.open(Paths.get(INDEX_DIR_PATH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return indexReader;
    }

    @Override
    public void close() throws IOException {
        if (indexReader != null) {
            indexReader.close();
        }
    }
}
