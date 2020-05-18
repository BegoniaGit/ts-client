package site.yan.local.advice;

import site.yan.core.cache.TraceCache;
import site.yan.core.data.Note;
import site.yan.core.data.Record;
import site.yan.core.enumeration.NoteType;
import site.yan.core.helper.RecordContextHolder;
import site.yan.core.utils.TimeUtil;
import site.yan.local.constant.LocalPairType;

public class RowTrace {

    private static ThreadLocal<Record> CURRENT_ROW_RECORD;

    public static void start(String name, String description) {
        if (!RecordContextHolder.getCurrentOpenState()) {
            return;
        }
        CURRENT_ROW_RECORD = new ThreadLocal<Record>() {
            @Override
            protected Record initialValue() {
                return Record.createClientRecord();
            }
        };
        Record record = CURRENT_ROW_RECORD.get();
        String parentId = RecordContextHolder.getServiceId();
        record.setName("row." + name)
                .setParentId(parentId)
                .putAdditionalPair(LocalPairType.DESCRIPTION.text(), description);
    }

    public static void end() {
        if (!RecordContextHolder.getCurrentOpenState()) {
            return;
        }
        Record record = CURRENT_ROW_RECORD.get();
        long currentStamp = TimeUtil.stamp();
        record.setDurationTime(currentStamp - record.getStartTimeStamp());
        Note startNote = new Note(NoteType.LOCAL_START.text(), record.getStartTimeStamp(), RecordContextHolder.getHost());
        Note endNote = new Note(NoteType.LOCAL_END.text(), currentStamp, RecordContextHolder.getHost());
        record.addNotePair(startNote, endNote);
        TraceCache.put(new Record(record));
        record.clear();
    }
}
