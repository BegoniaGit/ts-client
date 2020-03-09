package site.yan.core.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.logging.log4j.util.Strings;
import site.yan.core.helper.RecordContextHolder;
import site.yan.core.utils.IdGeneratorHelper;
import site.yan.core.utils.TimeStamp;

import java.util.*;

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

    public Record() {
        init();
    }

    /**
     * clone
     *
     * @param record
     */
    public Record(Record record) {
        this.traceId = record.traceId;
        this.id = record.id;
        this.parentId = record.parentId;
        this.startTimeStamp = record.startTimeStamp;
        this.durationTime = record.durationTime;
        this.name = record.name;
        this.serverName = record.serverName;
        this.stage = record.stage;
        this.notePair = new ArrayList(record.notePair);
        this.additionalPair = new HashMap(record.additionalPair);
    }

    public static Record createClientRecord() {
        Record record = new Record();
        record.setTraceId(RecordContextHolder.getTraceId())
                .setServerName(RecordContextHolder.getServerName())
                .setStage(RecordContextHolder.getStage());
        return record;
    }

    public void clear() {
        this.traceId = null;
        this.parentId = null;
        this.startTimeStamp = null;
        this.durationTime = null;
        this.name = null;
        this.serverName = null;
        this.stage = null;
        this.id = IdGeneratorHelper.idLen32Generat();
        this.notePair = new ArrayList(2);
        this.additionalPair = new HashMap(16);
    }

    /**
     * 初始化 开始时间，id
     */
    private void init() {
        this.startTimeStamp = TimeStamp.stamp();
        this.id = IdGeneratorHelper.idLen32Generat();
        this.notePair = new ArrayList(2);
        this.additionalPair = new HashMap(16);
    }

    public String getTraceId() {
        return traceId;
    }

    public Record setTraceId(String traceId) {
        this.traceId = traceId;
        return this;
    }

    public String getId() {
        return id;
    }

    public Record setId(String id) {
        this.id = id;
        return this;
    }

    public String getParentId() {
        return parentId;
    }

    public Record setParentId(String parentId) {
        if (Strings.isNotBlank(parentId)) {
            this.parentId = parentId;
        }
        return this;
    }

    public Long getStartTimeStamp() {
        return startTimeStamp;
    }

    public Record setStartTimeStamp(Long startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
        return this;
    }

    public Long getDurationTime() {
        return durationTime;
    }

    public Record setDurationTime(Long durationTime) {
        this.durationTime = durationTime;
        return this;
    }

    public String getName() {
        return name;
    }

    public Record setName(String name) {
        this.name = name;
        return this;
    }

    public String getServerName() {
        return serverName;
    }

    public Record setServerName(String serverName) {
        this.serverName = serverName;
        return this;
    }

    public String getStage() {
        return stage;
    }

    public Record setStage(String stage) {
        this.stage = stage;
        return this;
    }

    public List<Note> getNotePair() {
        return notePair;
    }

    public Record setNotePair(List<Note> notePair) {
        this.notePair = notePair;
        return this;
    }

    public Map<String, String> getAdditionalPair() {
        return additionalPair;
    }

    public Record setAdditionalPair(Map<String, String> additionalPair) {
        this.additionalPair = additionalPair;
        return this;
    }

    public Record putAdditionalPair(String k, String v) {
        this.additionalPair.put(k, v);
        return this;
    }

    public Record addNotePair(Note... v) {
        this.notePair.addAll(Arrays.asList(v));
        return this;
    }
}
