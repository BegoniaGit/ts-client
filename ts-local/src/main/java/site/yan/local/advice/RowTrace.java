package site.yan.local.advice;

import site.yan.core.cache.TraceCache;
import site.yan.core.data.Note;
import site.yan.core.data.Record;
import site.yan.core.enumeration.NoteType;
import site.yan.core.helper.RecordContextHolder;
import site.yan.core.utils.TimeStamp;

import java.util.Objects;

public class RowTrace {

    private static ThreadLocal<Record> CURRENT_ROW_RECORD;

    public static void start(String description) {
        CURRENT_ROW_RECORD = new ThreadLocal<Record>() {
            @Override
            protected Record initialValue() {
                return Record.createClientRecord();
            }
        };
        Record record = CURRENT_ROW_RECORD.get();
        String parentId = Objects.isNull(RecordContextHolder.getParentId()) ? RecordContextHolder.getServiceId() : RecordContextHolder.getParentId();
        record.setName("row." + description)
                .setParentId(parentId);
    }

    public static void end(String description) {
        Record record = CURRENT_ROW_RECORD.get();
        long currentStamp = TimeStamp.stamp();
        record.setDurationTime(currentStamp - record.getStartTimeStamp());
        Note startNote = new Note(NoteType.LOCAL_START.text(), record.getStartTimeStamp(), RecordContextHolder.getHost());
        record.addNotePair(startNote);
        record.addNotePair(new Note(NoteType.LOCAL_END.text(), currentStamp, RecordContextHolder.getHost()));
        TraceCache.put(new Record(record));
        record.clear();
    }
}
