package site.yan.httpclient.client;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import site.yan.core.cache.TraceCache;
import site.yan.core.data.Host;
import site.yan.core.data.Note;
import site.yan.core.data.Record;
import site.yan.core.enumeration.HeaderType;
import site.yan.core.enumeration.HttpMethodType;
import site.yan.core.enumeration.NoteType;
import site.yan.core.helper.RecordContextHolder;
import site.yan.core.utils.TimeUtil;
import site.yan.httpclient.builder.TargetLocationType;
import site.yan.httpclient.constant.HttpClientPairType;
import site.yan.httpclient.model.ClientResp;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TsHttpClient extends AbstractHttpClient {

    private String traceId;
    private String parentId;
    private String serverName;
    private URL remoteUrl;
    private List headers = new ArrayList(16);
    private TargetLocationType targetLocationType;

    public TsHttpClient setTargetLocationType(TargetLocationType targetLocationType) {
        this.targetLocationType = targetLocationType;
        return this;
    }

    @Override
    protected List<Header> headerConfig() {
        this.headers.add(new BasicHeader(HeaderType.TS_TRACE_ID.text(), this.traceId));
        this.headers.add(new BasicHeader(HeaderType.TS_ID.text(), this.parentId));
        this.headers.add(new BasicHeader(HeaderType.TS_SERVER_NAME.text(), this.serverName));
        return this.headers;
    }

    @Override
    public String doGet(String httpUrl) {
        Record record = before(httpUrl, HttpMethodType.GET);
        ClientResp resp = sendHttpGet(httpUrl);
        after(resp, record);
        return resp.getRespStr();
    }

    @Override
    public String doPost(String httpUrl) {
        Record record = before(httpUrl, HttpMethodType.POST);
        ClientResp resp = sendHttpPost(httpUrl);
        after(resp, record);
        return resp.getRespStr();
    }

    @Override
    public String doPost(String httpUrl, String body) {
        Record record = before(httpUrl, HttpMethodType.POST);
        ClientResp resp = sendHttpPost(httpUrl, body);
        after(resp, record);
        return resp.getRespStr();
    }

    @Override
    public String doPost(String httpUrl, Map<String, String> maps) {
        Record record = before(httpUrl, HttpMethodType.POST);
        ClientResp resp = sendHttpPost(httpUrl, maps);
        after(resp, record);
        return resp.getRespStr();
    }

    private Record before(String httpUrl, HttpMethodType type) {
        if (!RecordContextHolder.getCurrentOpenState()) {
            this.headers.add(new BasicHeader(HeaderType.TS_TRACE_OPEN.text(), "false"));
            return null;
        }
        this.headers.add(new BasicHeader(HeaderType.TS_TRACE_OPEN.text(), "true"));
        this.traceId = RecordContextHolder.getTraceId();
        this.parentId = RecordContextHolder.getServiceId();
        this.serverName = RecordContextHolder.getServerName();
        parasUrl(httpUrl);
        Record record = Record.createClientRecord();
        record.setParentId(RecordContextHolder.getServiceId())
                .setName("client." + type.text());
        return record;
    }


    private void after(ClientResp clientResp, Record record) {
        if (!RecordContextHolder.getCurrentOpenState()) {
            return;
        }
        if (this.targetLocationType.equals(TargetLocationType.INTERNAL) && Objects.isNull(clientResp.getException())) {
            RecordContextHolder.lastIdGetAndSet(clientResp.getId());
            return;
        }

        if (Objects.nonNull(clientResp.getException())) {
            record.setError(true);
            record.putAdditionalPair(HttpClientPairType.EXCEPTION.text(), clientResp.getException().getMessage());
        }

        long currentStamp = TimeUtil.stamp();
        record.setDurationTime(currentStamp - record.getStartTimeStamp());

        Note startNote = new Note(NoteType.CLIENT_SEND.text(), record.getStartTimeStamp(), RecordContextHolder.getHost());
        Host remoteHost = new Host(remoteUrl.getHost(), remoteUrl.getHost(), remoteUrl.getDefaultPort());
        Note endNote = new Note(NoteType.CLIENT_RECEIVE.text(), currentStamp, remoteHost);
        record.addNotePair(startNote, endNote);

        record.putAdditionalPair(HttpClientPairType.REMOTE_SERVER.text(), remoteHost.getServerName());
        record.putAdditionalPair(HttpClientPairType.PATH.text(), "".equals(remoteUrl.getPath()) ? "/" : remoteUrl.getPath());
        record.putAdditionalPair(HttpClientPairType.STATUS_CODE.text(), String.valueOf(clientResp.getCode()));
        record.putAdditionalPair(HttpClientPairType.CONTENT_SIZE.text(), String.valueOf(clientResp.getContentSize()));

        TraceCache.put(record);
    }

    private void parasUrl(String urlString) {
        try {
            this.remoteUrl = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

}
