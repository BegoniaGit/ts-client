package site.yan.core.configer;

/**
 * 各个组件需要获取配置属性时调用此工具类
 */
public class TSProperties {

    private static boolean enable;

    private static int tracePort;

    private static double samplingRate;

    private static boolean autoReport;

    private static long timePeriod;

    private static String mode;

    private static String queueName;

    private static String autoReportUrl;

    private static String serverName;

    private static String stage;

    private static String[] traceIgnoreUrls;

    static void setValues(Properties properties) {
        TSProperties.enable = properties.isEnable();
        TSProperties.tracePort=properties.getTracePort();
        TSProperties.samplingRate = properties.getSamplingRate();
        TSProperties.autoReport = properties.isAutoReport();
        TSProperties.timePeriod=properties.getTimePeriod();
        TSProperties.mode = properties.getMode();
        TSProperties.queueName = properties.getQueueName();
        TSProperties.autoReportUrl = properties.getAutoReportUrl();
        TSProperties.serverName = properties.getServerName();
        TSProperties.stage = properties.getStage();
        TSProperties.traceIgnoreUrls = properties.getTraceIgnoreUrls();
    }

    public static boolean isAutoReport() {
        return autoReport;
    }

    public static String getAutoReportUrl() {
        return autoReportUrl;
    }

    public static boolean isEnable() {
        return enable;
    }

    public static long getTimePeriod() {
        return timePeriod;
    }

    public static String getMode() {
        return mode;
    }

    public static String getQueueName() {
        return queueName;
    }

    public static double getSamplingRate() {
        return samplingRate;
    }

    public static String getServerName() {
        return serverName;
    }

    public static String getStage() {
        return stage;
    }

    public static String[] getTraceIgnoreUrls() {
        return traceIgnoreUrls;
    }

    public static int getTracePort() {
        return tracePort;
    }
}
