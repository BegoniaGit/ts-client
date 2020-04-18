package site.yan.core.configer;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "ts", ignoreUnknownFields = true)
public class Properties {

    private boolean enable = false;

    private boolean autoReport = false;

    private String autoReportUrl = "";

    private String serverName = "Unknown server";

    private String stage = "prod";

    private String[] traceIgnoreUrls = {};


    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isAutoReport() {
        return autoReport;
    }

    public void setAutoReport(boolean autoReport) {
        this.autoReport = autoReport;
    }

    public String getAutoReportUrl() {
        return autoReportUrl;
    }

    public void setAutoReportUrl(String autoReportUrl) {
        this.autoReportUrl = autoReportUrl;
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

    public String[] getTraceIgnoreUrls() {
        return traceIgnoreUrls;
    }

    public void setTraceIgnoreUrls(String[] traceIgnoreUrls) {
        this.traceIgnoreUrls = traceIgnoreUrls;
    }
}
