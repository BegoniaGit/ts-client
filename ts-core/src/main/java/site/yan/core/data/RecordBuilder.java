package site.yan.core.data;

public class RecordBuilder {

    private Record record;

    public RecordBuilder() {
        this.record = new Record();
    }

    public RecordBuilder setTraceId(String traceId) {
        this.record.setTraceId(traceId);
        return this;
    }

    public RecordBuilder setId(String id) {
        this.record.setId(id);
        return this;
    }

    public RecordBuilder setParentId(String parentId) {
        this.record.setParentId(parentId);
        return this;
    }

    public RecordBuilder setStartTimeStamp(Long startTimeStamp) {
        this.record.setStartTimeStamp(startTimeStamp);
        return this;
    }

    public RecordBuilder setDurationTime(Long durationTime) {
        this.record.setDurationTime(durationTime);
        return this;
    }

    public RecordBuilder setName(String name) {
        this.record.setName(name);
        return this;
    }

    public RecordBuilder setServerName(String serverName) {
        this.record.setServerName(serverName);
        return this;
    }

    public RecordBuilder setStage(String stage) {
        this.record.setStage(stage);
        return this;
    }

    public Record build() {
        return this.record;
    }
}