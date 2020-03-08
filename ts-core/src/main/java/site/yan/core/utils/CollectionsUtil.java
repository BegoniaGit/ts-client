package site.yan.core.utils;

import java.util.Collection;
import java.util.Objects;

public class CollectionsUtil {


    public static boolean isNotBlank(Collection collection) {
        return !isBlank(collection);
    }

    public static boolean isBlank(Collection collection) {
        return Objects.isNull(collection) || collection.size() == 0;
    }
}
