package site.yan.core.utils;

import java.util.Random;
import java.util.UUID;

public class IdGeneratorHelper {

    public static String idLen32Generat(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String getTraceIdByGenerate() {
        return String.valueOf(new Random().nextDouble() * 100000);
    }
}
