package site.yan.core.utils;


public class ReflectUtil {
    private static final int MAX_RECORD_REDIS_ARG_LENGTH = 100;

    public ReflectUtil() {
    }

    public static String argToString(Object arg) {
        String argValue;
        if (arg == null) {
            argValue = "null";
        } else {
            StringBuilder argValueBuilder = new StringBuilder();
            if (arg instanceof byte[]) {
                argValueBuilder.append(new String((byte[]) arg));
            } else if (!(arg instanceof Integer) && !(arg instanceof Long) && !(arg instanceof Boolean) && !(arg instanceof String) && !(arg instanceof Short) && !(arg instanceof Float) && !(arg instanceof Double)) {
                argValueBuilder.append(arg.getClass().getName()).append(":").append(arg.toString());
            } else {
                argValueBuilder.append(arg);
            }

            argValue = argValueBuilder.toString();
            if (argValue.length() > 100) {
                argValue = argValue.substring(0, 100) + " ... length " + argValue.length() + " in total";
            }
        }

        return argValue;
    }
}