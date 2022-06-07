package io.netty.util.internal;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public final class SystemPropertyUtil {
    private static boolean c;
    private static final InternalLogger b = InternalLoggerFactory.getInstance(SystemPropertyUtil.class);
    private static boolean a = true;
    private static final Pattern d = Pattern.compile("-?[0-9]+");

    public static boolean contains(String str) {
        return get(str) != null;
    }

    public static String get(String str) {
        return get(str, null);
    }

    public static String get(final String str, String str2) {
        if (str == null) {
            throw new NullPointerException("key");
        } else if (!str.isEmpty()) {
            String str3 = null;
            try {
                if (System.getSecurityManager() == null) {
                    str = System.getProperty(str);
                    str3 = str;
                } else {
                    str3 = (String) AccessController.doPrivileged(new PrivilegedAction<String>() { // from class: io.netty.util.internal.SystemPropertyUtil.1
                        /* renamed from: a */
                        public String run() {
                            return System.getProperty(str);
                        }
                    });
                }
            } catch (Exception e) {
                if (!c) {
                    a("Unable to retrieve a system property '" + str + "'; default values will be used.", e);
                    c = true;
                }
            }
            return str3 == null ? str2 : str3;
        } else {
            throw new IllegalArgumentException("key must not be empty.");
        }
    }

    public static boolean getBoolean(String str, boolean z) {
        String str2 = get(str);
        if (str2 == null) {
            return z;
        }
        String lowerCase = str2.trim().toLowerCase();
        if (lowerCase.isEmpty() || "true".equals(lowerCase) || "yes".equals(lowerCase) || "1".equals(lowerCase)) {
            return true;
        }
        if ("false".equals(lowerCase) || "no".equals(lowerCase) || "0".equals(lowerCase)) {
            return false;
        }
        a("Unable to parse the boolean system property '" + str + "':" + lowerCase + " - using the default value: " + z);
        return z;
    }

    public static int getInt(String str, int i) {
        String str2 = get(str);
        if (str2 == null) {
            return i;
        }
        String lowerCase = str2.trim().toLowerCase();
        if (d.matcher(lowerCase).matches()) {
            try {
                return Integer.parseInt(lowerCase);
            } catch (Exception unused) {
            }
        }
        a("Unable to parse the integer system property '" + str + "':" + lowerCase + " - using the default value: " + i);
        return i;
    }

    public static long getLong(String str, long j) {
        String str2 = get(str);
        if (str2 == null) {
            return j;
        }
        String lowerCase = str2.trim().toLowerCase();
        if (d.matcher(lowerCase).matches()) {
            try {
                return Long.parseLong(lowerCase);
            } catch (Exception unused) {
            }
        }
        a("Unable to parse the long integer system property '" + str + "':" + lowerCase + " - using the default value: " + j);
        return j;
    }

    private static void a(String str) {
        if (a) {
            b.warn(str);
        } else {
            Logger.getLogger(SystemPropertyUtil.class.getName()).log(Level.WARNING, str);
        }
    }

    private static void a(String str, Exception exc) {
        if (a) {
            b.warn(str, (Throwable) exc);
        } else {
            Logger.getLogger(SystemPropertyUtil.class.getName()).log(Level.WARNING, str, (Throwable) exc);
        }
    }

    private SystemPropertyUtil() {
    }
}
