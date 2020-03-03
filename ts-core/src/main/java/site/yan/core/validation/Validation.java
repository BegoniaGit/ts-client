package site.yan.core.validation;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import site.yan.core.utils.StringUtil;

public class Validation {
    private static final Logger LOG = LoggerFactory.getLogger(Validation.class);

    public Validation() {
    }

    public static void assertServiceName(String serviceName) {
        if (StringUtil.isBlank(serviceName)) {
            throw new IllegalArgumentException("The service name can't be empty!");
        }
    }

    public static boolean checkHunterHostFormat(String host) {
        return StringUtil.isNotBlank(host) ? host.startsWith("http://") : false;
    }

    public static String md5Length16(String sourceStr) {
        String result = md5Length32(sourceStr);
        return result.length() == 32 ? result.substring(8, 24) : result;
    }

    public static String md5Length32(String sourceStr) {
        String result = "";

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte[] b = md.digest();
            StringBuilder buf = new StringBuilder();

            for (int offset = 0; offset < b.length; ++offset) {
                int i = b[offset];
                if (i < 0) {
                    i += 256;
                }

                if (i < 16) {
                    buf.append("0");
                }

                buf.append(Integer.toHexString(i));
            }

            result = buf.toString();
            return result;
        } catch (NoSuchAlgorithmException var7) {
            LOG.error("md5Length16", var7);
            return result;
        }
    }
}
