package org.apache.commons.lang;

import com.zlw.main.recorderlib.BuildConfig;
import java.io.File;
import java.io.PrintStream;

/* loaded from: classes5.dex */
public class SystemUtils {
    public static final boolean IS_JAVA_1_1;
    public static final boolean IS_JAVA_1_2;
    public static final boolean IS_JAVA_1_3;
    public static final boolean IS_JAVA_1_4;
    public static final boolean IS_JAVA_1_5;
    public static final boolean IS_JAVA_1_6;
    public static final boolean IS_JAVA_1_7;
    public static final boolean IS_OS_AIX;
    public static final boolean IS_OS_HP_UX;
    public static final boolean IS_OS_IRIX;
    public static final boolean IS_OS_LINUX;
    public static final boolean IS_OS_MAC;
    public static final boolean IS_OS_MAC_OSX;
    public static final boolean IS_OS_OS2;
    public static final boolean IS_OS_SOLARIS;
    public static final boolean IS_OS_SUN_OS;
    public static final boolean IS_OS_UNIX;
    public static final boolean IS_OS_WINDOWS;
    public static final boolean IS_OS_WINDOWS_2000;
    public static final boolean IS_OS_WINDOWS_7;
    public static final boolean IS_OS_WINDOWS_95;
    public static final boolean IS_OS_WINDOWS_98;
    public static final boolean IS_OS_WINDOWS_ME;
    public static final boolean IS_OS_WINDOWS_NT;
    public static final boolean IS_OS_WINDOWS_VISTA;
    public static final boolean IS_OS_WINDOWS_XP;
    public static final float JAVA_VERSION_FLOAT;
    public static final int JAVA_VERSION_INT;
    public static final String JAVA_VERSION_TRIMMED;
    public static final String USER_COUNTRY;
    public static final String USER_DIR;
    public static final String USER_HOME;
    public static final String USER_LANGUAGE;
    public static final String USER_NAME;
    public static final String USER_TIMEZONE;
    public static final String AWT_TOOLKIT = c("awt.toolkit");
    public static final String FILE_ENCODING = c("file.encoding");
    public static final String FILE_SEPARATOR = c("file.separator");
    public static final String JAVA_AWT_FONTS = c("java.awt.fonts");
    public static final String JAVA_AWT_GRAPHICSENV = c("java.awt.graphicsenv");
    public static final String JAVA_AWT_HEADLESS = c("java.awt.headless");
    public static final String JAVA_AWT_PRINTERJOB = c("java.awt.printerjob");
    public static final String JAVA_CLASS_PATH = c("java.class.path");
    public static final String JAVA_CLASS_VERSION = c("java.class.version");
    public static final String JAVA_COMPILER = c("java.compiler");
    public static final String JAVA_ENDORSED_DIRS = c("java.endorsed.dirs");
    public static final String JAVA_EXT_DIRS = c("java.ext.dirs");
    public static final String JAVA_HOME = c("java.home");
    public static final String JAVA_IO_TMPDIR = c("java.io.tmpdir");
    public static final String JAVA_LIBRARY_PATH = c("java.library.path");
    public static final String JAVA_RUNTIME_NAME = c("java.runtime.name");
    public static final String JAVA_RUNTIME_VERSION = c("java.runtime.version");
    public static final String JAVA_SPECIFICATION_NAME = c("java.specification.name");
    public static final String JAVA_SPECIFICATION_VENDOR = c("java.specification.vendor");
    public static final String JAVA_SPECIFICATION_VERSION = c("java.specification.version");
    public static final String JAVA_UTIL_PREFS_PREFERENCES_FACTORY = c("java.util.prefs.PreferencesFactory");
    public static final String JAVA_VENDOR = c("java.vendor");
    public static final String JAVA_VENDOR_URL = c("java.vendor.url");
    public static final String JAVA_VERSION = c("java.version");
    public static final String JAVA_VM_INFO = c("java.vm.info");
    public static final String JAVA_VM_NAME = c("java.vm.name");
    public static final String JAVA_VM_SPECIFICATION_NAME = c("java.vm.specification.name");
    public static final String JAVA_VM_SPECIFICATION_VENDOR = c("java.vm.specification.vendor");
    public static final String JAVA_VM_SPECIFICATION_VERSION = c("java.vm.specification.version");
    public static final String JAVA_VM_VENDOR = c("java.vm.vendor");
    public static final String JAVA_VM_VERSION = c("java.vm.version");
    public static final String LINE_SEPARATOR = c("line.separator");
    public static final String OS_ARCH = c("os.arch");
    public static final String OS_NAME = c("os.name");
    public static final String OS_VERSION = c("os.version");
    public static final String PATH_SEPARATOR = c("path.separator");

    static {
        USER_COUNTRY = c(c("user.country") == null ? "user.region" : "user.country");
        USER_DIR = c("user.dir");
        USER_HOME = c("user.home");
        USER_LANGUAGE = c("user.language");
        USER_NAME = c("user.name");
        USER_TIMEZONE = c("user.timezone");
        JAVA_VERSION_TRIMMED = c();
        JAVA_VERSION_FLOAT = a();
        JAVA_VERSION_INT = b();
        IS_JAVA_1_1 = a("1.1");
        IS_JAVA_1_2 = a(BuildConfig.VERSION_NAME);
        IS_JAVA_1_3 = a("1.3");
        IS_JAVA_1_4 = a("1.4");
        IS_JAVA_1_5 = a("1.5");
        IS_JAVA_1_6 = a("1.6");
        IS_JAVA_1_7 = a("1.7");
        IS_OS_AIX = b("AIX");
        IS_OS_HP_UX = b("HP-UX");
        IS_OS_IRIX = b("Irix");
        boolean z = false;
        IS_OS_LINUX = b("Linux") || b("LINUX");
        IS_OS_MAC = b("Mac");
        IS_OS_MAC_OSX = b("Mac OS X");
        IS_OS_OS2 = b("OS/2");
        IS_OS_SOLARIS = b("Solaris");
        IS_OS_SUN_OS = b("SunOS");
        if (IS_OS_AIX || IS_OS_HP_UX || IS_OS_IRIX || IS_OS_LINUX || IS_OS_MAC_OSX || IS_OS_SOLARIS || IS_OS_SUN_OS) {
            z = true;
        }
        IS_OS_UNIX = z;
        IS_OS_WINDOWS = b("Windows");
        IS_OS_WINDOWS_2000 = c("Windows", "5.0");
        IS_OS_WINDOWS_95 = c("Windows 9", "4.0");
        IS_OS_WINDOWS_98 = c("Windows 9", "4.1");
        IS_OS_WINDOWS_ME = c("Windows", "4.9");
        IS_OS_WINDOWS_NT = b("Windows NT");
        IS_OS_WINDOWS_XP = c("Windows", "5.1");
        IS_OS_WINDOWS_VISTA = c("Windows", "6.0");
        IS_OS_WINDOWS_7 = c("Windows", "6.1");
    }

    public static File getJavaHome() {
        return new File(System.getProperty("java.home"));
    }

    public static File getJavaIoTmpDir() {
        return new File(System.getProperty("java.io.tmpdir"));
    }

    public static float getJavaVersion() {
        return JAVA_VERSION_FLOAT;
    }

    private static float a() {
        return a(a(JAVA_VERSION, 3));
    }

    private static int b() {
        return b(a(JAVA_VERSION, 3));
    }

    private static boolean a(String str) {
        return a(JAVA_VERSION_TRIMMED, str);
    }

    private static String c() {
        if (JAVA_VERSION == null) {
            return null;
        }
        for (int i = 0; i < JAVA_VERSION.length(); i++) {
            char charAt = JAVA_VERSION.charAt(i);
            if (charAt >= '0' && charAt <= '9') {
                return JAVA_VERSION.substring(i);
            }
        }
        return null;
    }

    private static boolean c(String str, String str2) {
        return a(OS_NAME, OS_VERSION, str, str2);
    }

    private static boolean b(String str) {
        return b(OS_NAME, str);
    }

    private static String c(String str) {
        try {
            return System.getProperty(str);
        } catch (SecurityException unused) {
            PrintStream printStream = System.err;
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Caught a SecurityException reading the system property '");
            stringBuffer.append(str);
            stringBuffer.append("'; the SystemUtils property value will default to null.");
            printStream.println(stringBuffer.toString());
            return null;
        }
    }

    public static File getUserDir() {
        return new File(System.getProperty("user.dir"));
    }

    public static File getUserHome() {
        return new File(System.getProperty("user.home"));
    }

    public static boolean isJavaAwtHeadless() {
        String str = JAVA_AWT_HEADLESS;
        if (str != null) {
            return str.equals(Boolean.TRUE.toString());
        }
        return false;
    }

    public static boolean isJavaVersionAtLeast(float f) {
        return JAVA_VERSION_FLOAT >= f;
    }

    public static boolean isJavaVersionAtLeast(int i) {
        return JAVA_VERSION_INT >= i;
    }

    static boolean a(String str, String str2) {
        if (str == null) {
            return false;
        }
        return str.startsWith(str2);
    }

    static boolean a(String str, String str2, String str3, String str4) {
        return str != null && str2 != null && str.startsWith(str3) && str2.startsWith(str4);
    }

    static boolean b(String str, String str2) {
        if (str == null) {
            return false;
        }
        return str.startsWith(str2);
    }

    private static int[] a(String str, int i) {
        if (str == null) {
            return ArrayUtils.EMPTY_INT_ARRAY;
        }
        String[] split = StringUtils.split(str, "._- ");
        int[] iArr = new int[Math.min(i, split.length)];
        int i2 = 0;
        for (int i3 = 0; i3 < split.length && i2 < i; i3++) {
            String str2 = split[i3];
            if (str2.length() > 0) {
                try {
                    iArr[i2] = Integer.parseInt(str2);
                    i2++;
                } catch (Exception unused) {
                }
            }
        }
        if (iArr.length <= i2) {
            return iArr;
        }
        int[] iArr2 = new int[i2];
        System.arraycopy(iArr, 0, iArr2, 0, i2);
        return iArr2;
    }

    private static float a(int[] iArr) {
        if (iArr == null || iArr.length == 0) {
            return 0.0f;
        }
        if (iArr.length == 1) {
            return iArr[0];
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(iArr[0]);
        stringBuffer.append('.');
        for (int i = 1; i < iArr.length; i++) {
            stringBuffer.append(iArr[i]);
        }
        try {
            return Float.parseFloat(stringBuffer.toString());
        } catch (Exception unused) {
            return 0.0f;
        }
    }

    private static int b(int[] iArr) {
        int i = 0;
        if (iArr == null) {
            return 0;
        }
        int length = iArr.length;
        if (length >= 1) {
            i = iArr[0] * 100;
        }
        if (length >= 2) {
            i += iArr[1] * 10;
        }
        return length >= 3 ? i + iArr[2] : i;
    }
}
