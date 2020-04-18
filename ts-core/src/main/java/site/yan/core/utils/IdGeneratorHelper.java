package site.yan.core.utils;

import java.util.UUID;

public class IdGeneratorHelper {

    public static String idLen32Generat() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
