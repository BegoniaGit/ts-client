package site.yan.core.configer;

import java.util.Collections;
import java.util.List;

/**
 * 各个组件需要获取配置属性时调用此工具类
 */
public class TSProperties {

    private static boolean enable;

    private static boolean autoReport = false;

    private static String autoReportUrl = "";

    private static String serverName;

    private static String stage;

    private static String[] traceIgnoreUrls;

    static void setValues(Properties properties) {
        TSProperties.enable = properties.isEnable();
        TSProperties.autoReport = properties.isAutoReport();
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

    public static String getServerName() {
        return serverName;
    }

    public static String getStage() {
        return stage;
    }

    public static String[] getTraceIgnoreUrls() {
        return traceIgnoreUrls;
    }
}
