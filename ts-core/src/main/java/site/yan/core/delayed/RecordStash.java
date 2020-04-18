package site.yan.core.delayed;

import com.google.gson.Gson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import site.yan.core.cache.TraceCache;
import site.yan.core.configer.TSProperties;
import site.yan.core.data.Record;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.DelayQueue;

/**
 * 延时队列作为 record 暂存区
 */
public class RecordStash {
    private static volatile Map<String, Record> recordMap = new HashMap<>(64);
    private static volatile DelayQueue<RecordDelayed> recordQueue = new DelayQueue();

    private static Logger logger = LoggerFactory.getLogger(RecordStash.class);

    public static Optional<Record> getById(String id) {
        Record record = null;
        try {
            record = recordMap.get(id);
            logger.error("Get stash record success");
        } catch (NullPointerException exc1) {
            logger.error("Get stash record error,because it has null");
        } finally {
            return Optional.ofNullable(record);
        }
    }

    public static void putRecord(Record record) {
        recordMap.put(record.getId(), record);
        recordQueue.offer(new RecordDelayed(record));
    }

    public static void receive() {
        new Thread(() -> {
            logger.info("record stash receive thread start");
            while (true) {
                try {
                    RecordDelayed recordDelayed = recordQueue.take();
                    logger.info("stash push a record");
                    TraceCache.put(recordDelayed.getRecord());
                    recordMap.remove(recordDelayed.getId());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void send() {
        new Thread(() -> {
            logger.info("record stash send thread start");
            final String url = TSProperties.getAutoReportUrl();
            while (true) {
                List recordList = TraceCache.getTraceRecord();
                if (recordList.size() != 0) {
                    CloseableHttpClient httpClient = HttpClients.createDefault();
                    HttpPost httpPost = new HttpPost(url);
                    String body = new Gson().toJson(recordList);
                    System.out.println(body);
                    StringEntity stringEntity = new StringEntity(body, "UTF-8");
                    httpPost.setEntity(stringEntity);
                    CloseableHttpResponse response = null;
                    try {
                        logger.info("will report...");
                        response = httpClient.execute(httpPost);
                        logger.info("report 200");
                    } catch (IOException e) {
                        logger.info("report error");
                        e.printStackTrace();
                    }
                }

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
