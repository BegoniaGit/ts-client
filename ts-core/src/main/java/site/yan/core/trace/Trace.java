package site.yan.core.trace;

import site.yan.core.data.Record;

public class Trace {

    private Record record;


    public Trace() {
        this.record = new Record(true);
    }

    public static Trace getTrace() {
        Trace trace = new Trace();
        return new Trace();
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }
}
