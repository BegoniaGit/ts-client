package site.yan.core.utils;

import java.util.Objects;

public class ExceptionUtil {

    public static String generateServletExceptionValue(Exception e) {
        if (e == null) {
            return null;
        } else {
            StringBuilder exceptionStackTraceBuilder = (new StringBuilder()).append(e.getClass().getName()).append(";").append(StringUtil.trimNewlineSymbolAndRemoveExtraSpace(e.getMessage())).append(";");
            StackTraceElement[] stackTraceElements = e.getStackTrace();
            StackTraceElement[] var3 = stackTraceElements;
            int var4 = stackTraceElements.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                StackTraceElement element = var3[var5];
                String msg = element.toString();
                if (Objects.nonNull(msg)) {
                    exceptionStackTraceBuilder.append(msg).append(";");
                }
            }

            return exceptionStackTraceBuilder.toString();
        }
    }
}
