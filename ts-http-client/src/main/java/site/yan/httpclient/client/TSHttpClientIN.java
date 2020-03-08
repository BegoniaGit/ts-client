package site.yan.httpclient.client;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import site.yan.core.enumeration.HeaderType;
import site.yan.core.helper.RecordContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 这是一个分布式服务内部请求的封装类，其主要功能是在HttpClient请求头中加入
 * traceId 和 parentId 用作被调用方开始一段新的server record记录。而在本应用中
 * 无需记录此record。
 */
public class TSHttpClientIN extends AbstractHttpClient {

    private String traceId;
    private String parentId;
    private String serverName;

    @Override
    protected List<Header> headerConfig() {
        List headers = new ArrayList(16);
        headers.add(new BasicHeader(HeaderType.TS_TRACE_ID.text(), this.traceId));
        headers.add(new BasicHeader(HeaderType.TS_ID.text(), this.parentId));
        headers.add(new BasicHeader(HeaderType.TS_SERVER_NAME.text(), this.serverName));
        return headers;
    }

    @Override
    public String doGet(String url) {
        before();
        String resp = sendHttpGet(url).getRespStr();
        return resp;
    }

    @Override
    public String doPost(String httpUrl) {
        before();
        String resp = sendHttpPost(httpUrl).getRespStr();
        return resp;
    }

    @Override
    public String doPost(String httpUrl, String params) {
        before();
        String resp = sendHttpPost(httpUrl, params).getRespStr();
        return resp;
    }

    @Override
    public String doPost(String httpUrl, Map<String, String> maps) {
        before();
        String resp = sendHttpPost(httpUrl, maps).getRespStr();
        return resp;
    }

    private String before() {
        this.traceId = RecordContextHolder.getTraceId();
        this.parentId = RecordContextHolder.getServiceId();
        this.serverName = RecordContextHolder.getServerName();
        return this.traceId;
    }
}