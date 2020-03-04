package site.yan.httpclient.trace;

public class TSHttpClient {

//    private Record record;
//
//
//    public String doGet(String url) {
//        return "";
//    }
//
//
//    private Record init() {
//        RecordState state = new RecordState();
//        Record serverRecord = state.getCurrentServerRecord();
//        String parentId = state.getServerRecordId();
//        String traceId = serverRecord.getTraceId();
//        Record clientRecord = state.getCurrentClientRecord();
//        clientRecord.setTraceId(traceId)
//                .setId(IdGeneratorHelper.idLen32Generat())
//                .setParentId(parentId)
//                .setName("GET")
//                .setStartTimeStamp(TimeStamp.stamp())
//                .setServerName(serverRecord.getServerName())
//                .setStage(serverRecord.getStage());
//        this.record = clientRecord;
//        return clientRecord;
//    }
//
//    private Record after() {
//        record.setDurationTime(TimeStamp.minusLastStamp(record.getStartTimeStamp()));
////        NotePairGen();
////        additionalPairGen();
//        TraceCache.put(record);
//        return record;
//
//    }

//    private void NotePairGen() {
//        Host host = new Host(properties.getServerName(), adapter.getRemoteAddress(), adapter.getRemotePort());
//        Note note = new Note();
//        note.setNoteName(NoteType.SERVER_RECEIVE.text());
//        note.setHost(host);
//        this.record.getNotePair().add(note);
//
//        Host host2 = new Host(properties.getServerName(), adapter.getLocalAddress(), adapter.getLocalPort());
//        Note note2 = new Note();
//        note2.setNoteName(NoteType.SERVER_RESPONSE.text());
//        note2.setHost(host2);
//        this.record.getNotePair().add(note2);
//    }
//
//    private void additionalPairGen() {
//        Map<String, String> additonalPair = this.record.getAdditionalPair();
//        additonalPair.put("path", adapter.getPath());
//        additonalPair.put("code", String.valueOf(((HttpServletResponse) this.response).getStatus()));
//        additonalPair.put("responseSize", String.valueOf(((HttpServletResponse) this.response).getBufferSize()));
//    }
//


}
