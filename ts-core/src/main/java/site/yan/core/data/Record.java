package site.yan.core.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Record {

    private String traceId;

    private String id;

    private String parentId;

    private Long startTimeStamp;

    private Long durationTime;

    // such as: http.get
    private String name;

    // such as: service-user
    private String serverName;

    // such as: dev
    private String stage;

    private List<Note> notePair;

    private Map<String, String> additionalPair;

    private void init() {
        this.notePair = new ArrayList(2);
        this.additionalPair = new HashMap(16);
    }

    public Record() {
        init();
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Long getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(Long startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public Long getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(Long durationTime) {
        this.durationTime = durationTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public List<Note> getNotePair() {
        return notePair;
    }

    public void setNotePair(List<Note> notePair) {
        this.notePair = notePair;
    }

    public Map<String, String> getAdditionalPair() {
        return additionalPair;
    }

    public void setAdditionalPair(Map<String, String> additionalPair) {
        this.additionalPair = additionalPair;
    }
}
