package site.yan.core.configer;

import org.springframework.stereotype.Component;

@Component
public class TSConfig {

    private boolean enable;

    private String serverName;

    private String stage;

    public TSConfig(boolean enable, String serverName, String stage) {
        this.enable = enable;
        this.serverName = serverName;
        this.stage = stage;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
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
}
