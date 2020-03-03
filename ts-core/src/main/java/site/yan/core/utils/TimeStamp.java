package site.yan.core.utils;

public class TimeStamp {

    public static long stamp() {
        return System.currentTimeMillis();
    }

    public static long minusLastStamp(long lastStamp) {
        long currentStamp = System.currentTimeMillis();
        return lastStamp < currentStamp ? currentStamp - lastStamp : -1;
    }
}
