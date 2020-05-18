package site.yan.core.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import site.yan.core.api.delayed.CloseDelayed;
import site.yan.core.api.delayed.CloseDelayedModel;
import site.yan.core.api.filter.FileIOProcessFilter;
import site.yan.core.api.server.HttpServer;

import java.util.concurrent.*;

/**
 * The {@code Boot} class is a entrance.
 *
 * @author zhao xubin
 * @date 2020-3-28
 */
public class TraceApiBoot {
    private ExecutorService executor = new ThreadPoolExecutor(1, 1,
            60L, TimeUnit.SECONDS,
            new ArrayBlockingQueue(200000),
            r -> {
                Thread thread = new Thread(r);
                thread.setName("http-server-thread");
                return thread;
            });
    private static Logger logger = LoggerFactory.getLogger(TraceApiBoot.class);

    public static void start(int port) throws Exception {
        logger.info("Http Server boot...");

        TraceApiBoot boot = new TraceApiBoot();

        // ChannelCloseDelayed start at first must.
        DelayQueue<CloseDelayedModel> channelCloseQueue = CloseDelayed.start(boot.executor);
        HttpServer httpServer = new HttpServer(boot.executor, channelCloseQueue);

        // Full implementation of HttpProcessFilter can be added to handle more personalized calculations.
        httpServer.addProcess(new FileIOProcessFilter());

        // Start server.
        httpServer.start(port);
    }
}
