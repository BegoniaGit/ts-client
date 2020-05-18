package site.yan.core.utils;

import site.yan.core.configer.TSProperties;

public class TimeUtil {

    public static long stamp() {
        return System.currentTimeMillis();
    }

    public static long minusLastStamp(long lastStamp) {
        long currentStamp = System.currentTimeMillis();
        return lastStamp < currentStamp ? currentStamp - lastStamp : -1;
    }

    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
