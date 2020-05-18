package site.yan.core.configer;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ts", ignoreUnknownFields = true)
public class Properties {

    private boolean enable = false;

    private int tracePort =6060;

    // 采样率 0-1 之间
    private double samplingRate = 1.0;

    private boolean autoReport = false;

    private String autoReportUrl = "";

    private long timePeriod = 5000L;

    // rabbitmq or native
    private String mode = "native";

    private String queueName = "trace-data-queue";

    private String serverName = "Unknown server";

    private String stage = "prod";

    private String[] traceIgnoreUrls = {};


    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public double getSamplingRate() {
        return samplingRate;
    }

    public void setSamplingRate(double samplingRate) {
        this.samplingRate = samplingRate;
    }

    public boolean isAutoReport() {
        return autoReport;
    }

    public void setAutoReport(boolean autoReport) {
        this.autoReport = autoReport;
    }

    public long getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(long timePeriod) {
        this.timePeriod = timePeriod;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
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

    public int getTracePort() {
        return tracePort;
    }

    public void setTracePort(int tracePort) {
        this.tracePort = tracePort;
    }
}
