package site.yan.core.api.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import site.yan.core.api.delayed.CloseDelayedModel;
import site.yan.core.api.filter.HttpProcessFilter;
import site.yan.core.configer.TSProperties;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;

/**
 * @author zhao xubin
 * @date 2020-3-28
 */
public class HttpServer {

    private Selector selector;

    private ExecutorService executor;
    private List<HttpProcessFilter> processFilters;
    private DelayQueue<CloseDelayedModel> channelCloseQueue;

    private Logger logger = LoggerFactory.getLogger(HttpServer.class);

    public HttpServer(ExecutorService executor, DelayQueue<CloseDelayedModel> channelCloseQueue) {
        this.executor = executor;
        this.processFilters = new LinkedList();
        this.channelCloseQueue = channelCloseQueue;
    }

    public List<HttpProcessFilter> addProcess(HttpProcessFilter httpProcessFilter) {
        this.processFilters.add(httpProcessFilter);
        return this.processFilters;
    }

    public void start(int port) throws IOException {
        this.selector = Selector.open();
        ServerSocketChannel listenChannel = ServerSocketChannel.open();
        listenChannel.socket().bind(new InetSocketAddress(port));
        listenChannel.configureBlocking(false);
        listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        logger.info("Http server started! listen port:" + port);
        while (true) {
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            ArrayList<SelectionKey> list = new ArrayList(selectedKeys);
            selectedKeys.clear();
            this.executor.execute(new ServerHandler(list, this.selector, processFilters, channelCloseQueue));
        }
    }
}
