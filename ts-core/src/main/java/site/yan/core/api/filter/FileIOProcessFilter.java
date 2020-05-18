package site.yan.core.api.filter;

import site.yan.core.api.RespModel;
import site.yan.core.cache.TraceCache;
import site.yan.core.api.constant.ContentType;
import site.yan.core.api.protocol.HttpAppResponse;
import site.yan.core.api.protocol.HttpRequest;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Random;

/**
 * @author zhao xubin
 * @date 2020-3-28
 */
public class FileIOProcessFilter implements site.yan.core.api.filter.HttpProcessFilter {

    private Random random = new Random();

    @Override
    public void preProcess(HttpRequest httpRequest, site.yan.core.api.protocol.HttpResponse httpResponse, FilterContext filterContext) {
        final long timeStamp = System.currentTimeMillis();
        String path = httpRequest.getPath();
        HttpAppResponse httpAppResponse = (HttpAppResponse) httpResponse;
        httpAppResponse.setContentType(ContentType.JSON);

        // To return information faster, use simple string concatenation。
        httpAppResponse.addHeader("Connection", "close");
        switch (path) {
            case "/trace": {
                RespModel respModel = null;
                try {
                    respModel = RespModel.ok(TraceCache.getTraceRecord());
                } catch (Exception exc) {
                    respModel = RespModel.error();
                } finally {
                    ByteBuffer byteBuffer = ByteBuffer.wrap(httpAppResponse.createBytes());
                    if (byteBuffer.hasRemaining()) {
                        try {
                            filterContext.getSocketChannel().write(byteBuffer);
                        } catch (IOException exc1) {
                            exc1.printStackTrace();
                        }
                    }
                }

            }
        }
    }

    @Override
    public void postProcess(HttpRequest httpRequest, site.yan.core.api.protocol.HttpResponse httpResponse, FilterContext filterContext) {
    }
}
