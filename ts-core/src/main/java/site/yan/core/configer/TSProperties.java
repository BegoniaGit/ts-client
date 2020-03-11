package site.yan.core.configer;

public class TSProperties {

    private static boolean enable;

    private static String serverName;

    private static String stage;

    static void setValues(boolean enable, String serverName, String stage) {
        TSProperties.enable = enable;
        TSProperties.serverName = serverName;
        TSProperties.stage = stage;
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
}
