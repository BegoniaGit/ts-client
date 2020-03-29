package site.yan.core.delayed;

import site.yan.core.data.Record;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class RecordDelayed implements Delayed {

    private String id;
    private Record record;

    private long executeTime;


    public RecordDelayed(Record record) {
        this.id = record.getId();
        this.record = record;
        this.executeTime = TimeUnit.NANOSECONDS.convert(2000, TimeUnit.MILLISECONDS) + System.nanoTime();
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.executeTime - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return 0;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }


}
