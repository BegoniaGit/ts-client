package site.yan.httpclient.client;

import org.apache.http.Header;
import site.yan.core.cache.TraceCache;
import site.yan.core.data.Host;
import site.yan.core.data.Note;
import site.yan.core.data.Record;
import site.yan.core.enumeration.HttpMethodType;
import site.yan.core.enumeration.NoteType;
import site.yan.core.helper.RecordContextHolder;
import site.yan.core.utils.TimeStamp;
import site.yan.httpclient.constant.HttpClientPairType;
import site.yan.httpclient.model.ClientResp;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class TSHttpClientOUT extends AbstractHttpClient {

    private URL remoteUrl;

    @Override
    protected List<Header> headerConfig() {
        return null;
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
    public String doPost(String httpUrl, String params) {
        Record record = before(httpUrl, HttpMethodType.POST);
        ClientResp resp = sendHttpPost(httpUrl, params);
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
        parasUrl(httpUrl);
        Record record = Record.createClientRecord();
        record.setParentId(RecordContextHolder.getServiceId())
                .setName("client." + type.text());
        return record;
    }

    private void parasUrl(String urlString) {
        try {
            this.remoteUrl = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    private void after(ClientResp clientResp, Record record) {
        long currentStamp = TimeStamp.stamp();
        record.setDurationTime(currentStamp - record.getStartTimeStamp());

        Note startNote = new Note(NoteType.CLIENT_SEND.text(), record.getStartTimeStamp(), RecordContextHolder.getHost());
        Host remoteHost = new Host(remoteUrl.getHost(), remoteUrl.getHost(), remoteUrl.getDefaultPort());
        Note endNote = new Note(NoteType.CLIENT_RECEIVE.text(), currentStamp, remoteHost);
        record.addNotePair(startNote, endNote);

        record.putAdditionalPair(HttpClientPairType.PATH.text(), remoteUrl.getPath());
        record.putAdditionalPair(HttpClientPairType.STATUS_CODE.text(), String.valueOf(clientResp.getCode()));
        record.putAdditionalPair(HttpClientPairType.CONTENT_SIZE.text(), String.valueOf(clientResp.getContentSize()));

        TraceCache.put(record);
    }
}