package site.yan.httpclient.client;

import org.apache.http.Header;
import site.yan.core.cache.TraceCache;
import site.yan.core.data.Host;
import site.yan.core.data.Note;
import site.yan.core.data.Record;
import site.yan.core.enumeration.NoteType;
import site.yan.core.helper.RecordContextHolder;
import site.yan.core.utils.IdGeneratorHelper;
import site.yan.core.utils.TimeStamp;
import site.yan.httpclient.HttpClientCore;
import site.yan.httpclient.adapter.ClientResp;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class TSHttpClientOUT extends HttpClientCore {

    private URL remoteUrl;

    @Override
    protected List<Header> headerConfig() {
        return null;
    }

    public String doGet(String httpUrl) {
        Record record = before(httpUrl);
        ClientResp resp = sendHttpGet(httpUrl);
        after(resp, record);
        return resp.getRespStr();
    }

    public String doPost(String httpUrl) {
        Record record = before(httpUrl);
        ClientResp resp = sendHttpPost(httpUrl);
        after(resp, record);
        return resp.getRespStr();
    }

    public String doPost(String httpUrl, String params) {
        Record record = before(httpUrl);
        ClientResp resp = sendHttpPost(httpUrl, params);
        after(resp, record);
        return resp.getRespStr();
    }

    public String doPost(String httpUrl, Map<String, String> maps) {
        Record record = before(httpUrl);
        ClientResp resp = sendHttpPost(httpUrl, maps);
        after(resp, record);
        return resp.getRespStr();
    }

    private Record before(String httpUrl) {
        parasUrl(httpUrl);
        Record record = new Record();
        record.setTraceId(RecordContextHolder.getTraceId())
                .setParentId(RecordContextHolder.getServiceId())
                .setId(IdGeneratorHelper.idLen32Generat());

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
        record.setDurationTime(TimeStamp.stamp() - record.getStartTimeStamp());
        NotePairGen(record);
        additionalPairGen(clientResp, record);
        TraceCache.put(record);
    }

    private void NotePairGen(Record record) {

        Note note = new Note();
        note.setNoteName(NoteType.CLIENT_SEND.text());
        note.setHost(RecordContextHolder.getHost());
        record.getNotePair().add(note);

        Host remoteHost = new Host(remoteUrl.getHost(), remoteUrl.getHost(), remoteUrl.getDefaultPort());
        Note note2 = new Note();
        note2.setNoteName(NoteType.CLIENT_RECEIVE.text());
        note2.setHost(remoteHost);
        record.getNotePair().add(note2);
    }

    private void additionalPairGen(ClientResp clientResp, Record record) {
        Map<String, String> additonalPair = record.getAdditionalPair();
        additonalPair.put("path", remoteUrl.getPath());
        additonalPair.put("code", String.valueOf(clientResp.getCode()));
        additonalPair.put("content size", String.valueOf(clientResp.getContentSize()));
    }
}