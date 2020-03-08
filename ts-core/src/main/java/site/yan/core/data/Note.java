package site.yan.core.data;

public class Note {

    private String noteName;
    private Long timeStamp;
    private Host host;

    public Note() {
    }

    public Note(String noteName, Long timeStamp, Host host) {
        this.noteName = noteName;
        this.timeStamp = timeStamp;
        this.host = host;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }
}
