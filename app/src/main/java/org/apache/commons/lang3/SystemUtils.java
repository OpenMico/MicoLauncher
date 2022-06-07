package org.apache.commons.lang3;

import com.xiaomi.mi_soundbox_command_sdk.Commands;
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
    public static final boolean IS_JAVA_1_8;
    @Deprecated
    public static final boolean IS_JAVA_1_9;
    public static final boolean IS_JAVA_9;
    public static final boolean IS_OS_400;
    public static final boolean IS_OS_AIX;
    public static final boolean IS_OS_FREE_BSD;
    public static final boolean IS_OS_HP_UX;
    public static final boolean IS_OS_IRIX;
    public static final boolean IS_OS_LINUX;
    public static final boolean IS_OS_MAC;
    public static final boolean IS_OS_MAC_OSX;
    public static final boolean IS_OS_MAC_OSX_CHEETAH;
    public static final boolean IS_OS_MAC_OSX_EL_CAPITAN;
    public static final boolean IS_OS_MAC_OSX_JAGUAR;
    public static final boolean IS_OS_MAC_OSX_LEOPARD;
    public static final boolean IS_OS_MAC_OSX_LION;
    public static final boolean IS_OS_MAC_OSX_MAVERICKS;
    public static final boolean IS_OS_MAC_OSX_MOUNTAIN_LION;
    public static final boolean IS_OS_MAC_OSX_PANTHER;
    public static final boolean IS_OS_MAC_OSX_PUMA;
    public static final boolean IS_OS_MAC_OSX_SNOW_LEOPARD;
    public static final boolean IS_OS_MAC_OSX_TIGER;
    public static final boolean IS_OS_MAC_OSX_YOSEMITE;
    public static final boolean IS_OS_NET_BSD;
    public static final boolean IS_OS_OPEN_BSD;
    public static final boolean IS_OS_OS2;
    public static final boolean IS_OS_SOLARIS;
    public static final boolean IS_OS_SUN_OS;
    public static final boolean IS_OS_UNIX;
    public static final boolean IS_OS_WINDOWS;
    public static final boolean IS_OS_WINDOWS_10;
    public static final boolean IS_OS_WINDOWS_2000;
    public static final boolean IS_OS_WINDOWS_2003;
    public static final boolean IS_OS_WINDOWS_2008;
    public static final boolean IS_OS_WINDOWS_2012;
    public static final boolean IS_OS_WINDOWS_7;
    public static final boolean IS_OS_WINDOWS_8;
    public static final boolean IS_OS_WINDOWS_95;
    public static final boolean IS_OS_WINDOWS_98;
    public static final boolean IS_OS_WINDOWS_ME;
    public static final boolean IS_OS_WINDOWS_NT;
    public static final boolean IS_OS_WINDOWS_VISTA;
    public static final boolean IS_OS_WINDOWS_XP;
    public static final boolean IS_OS_ZOS;
    public static final String USER_COUNTRY;
    public static final String USER_DIR;
    public static final String USER_HOME;
    public static final String USER_LANGUAGE;
    public static final String USER_NAME;
    public static final String USER_TIMEZONE;
    public static final String AWT_TOOLKIT = c("awt.toolkit");
    public static final String FILE_ENCODING = c("file.encoding");
    @Deprecated
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
    private static final JavaVersion a = JavaVersion.a(JAVA_SPECIFICATION_VERSION);
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
    @Deprecated
    public static final String PATH_SEPARATOR = c("path.separator");

    static {
        USER_COUNTRY = c(c("user.country") == null ? "user.region" : "user.country");
        USER_DIR = c("user.dir");
        USER_HOME = c("user.home");
        USER_LANGUAGE = c("user.language");
        USER_NAME = c("user.name");
        USER_TIMEZONE = c("user.timezone");
        IS_JAVA_1_1 = a("1.1");
        IS_JAVA_1_2 = a(BuildConfig.VERSION_NAME);
        IS_JAVA_1_3 = a("1.3");
        IS_JAVA_1_4 = a("1.4");
        IS_JAVA_1_5 = a("1.5");
        IS_JAVA_1_6 = a("1.6");
        IS_JAVA_1_7 = a("1.7");
        IS_JAVA_1_8 = a(com.mijiasdk.bleserver.BuildConfig.VERSION_NAME);
        IS_JAVA_1_9 = a(Commands.ResolutionValues.BITSTREAM_4K);
        IS_JAVA_9 = a(Commands.ResolutionValues.BITSTREAM_4K);
        IS_OS_AIX = b("AIX");
        IS_OS_HP_UX = b("HP-UX");
        IS_OS_400 = b("OS/400");
        IS_OS_IRIX = b("Irix");
        boolean z = false;
        IS_OS_LINUX = b("Linux") || b("LINUX");
        IS_OS_MAC = b("Mac");
        IS_OS_MAC_OSX = b("Mac OS X");
        IS_OS_MAC_OSX_CHEETAH = d("Mac OS X", "10.0");
        IS_OS_MAC_OSX_PUMA = d("Mac OS X", "10.1");
        IS_OS_MAC_OSX_JAGUAR = d("Mac OS X", "10.2");
        IS_OS_MAC_OSX_PANTHER = d("Mac OS X", "10.3");
        IS_OS_MAC_OSX_TIGER = d("Mac OS X", "10.4");
        IS_OS_MAC_OSX_LEOPARD = d("Mac OS X", "10.5");
        IS_OS_MAC_OSX_SNOW_LEOPARD = d("Mac OS X", "10.6");
        IS_OS_MAC_OSX_LION = d("Mac OS X", "10.7");
        IS_OS_MAC_OSX_MOUNTAIN_LION = d("Mac OS X", "10.8");
        IS_OS_MAC_OSX_MAVERICKS = d("Mac OS X", "10.9");
        IS_OS_MAC_OSX_YOSEMITE = d("Mac OS X", "10.10");
        IS_OS_MAC_OSX_EL_CAPITAN = d("Mac OS X", "10.11");
        IS_OS_FREE_BSD = b("FreeBSD");
        IS_OS_OPEN_BSD = b("OpenBSD");
        IS_OS_NET_BSD = b("NetBSD");
        IS_OS_OS2 = b("OS/2");
        IS_OS_SOLARIS = b("Solaris");
        IS_OS_SUN_OS = b("SunOS");
        if (IS_OS_AIX || IS_OS_HP_UX || IS_OS_IRIX || IS_OS_LINUX || IS_OS_MAC_OSX || IS_OS_SOLARIS || IS_OS_SUN_OS || IS_OS_FREE_BSD || IS_OS_OPEN_BSD || IS_OS_NET_BSD) {
            z = true;
        }
        IS_OS_UNIX = z;
        IS_OS_WINDOWS = b("Windows");
        IS_OS_WINDOWS_2000 = b("Windows 2000");
        IS_OS_WINDOWS_2003 = b("Windows 2003");
        IS_OS_WINDOWS_2008 = b("Windows Server 2008");
        IS_OS_WINDOWS_2012 = b("Windows Server 2012");
        IS_OS_WINDOWS_95 = b("Windows 95");
        IS_OS_WINDOWS_98 = b("Windows 98");
        IS_OS_WINDOWS_ME = b("Windows Me");
        IS_OS_WINDOWS_NT = b("Windows NT");
        IS_OS_WINDOWS_XP = b("Windows XP");
        IS_OS_WINDOWS_VISTA = b("Windows Vista");
        IS_OS_WINDOWS_7 = b("Windows 7");
        IS_OS_WINDOWS_8 = b("Windows 8");
        IS_OS_WINDOWS_10 = b("Windows 10");
        IS_OS_ZOS = b("z/OS");
    }

    public static File getJavaHome() {
        return new File(System.getProperty("java.home"));
    }

    public static File getJavaIoTmpDir() {
        return new File(System.getProperty("java.io.tmpdir"));
    }

    private static boolean a(String str) {
        return a(JAVA_SPECIFICATION_VERSION, str);
    }

    private static boolean d(String str, String str2) {
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
            printStream.println("Caught a SecurityException reading the system property '" + str + "'; the SystemUtils property value will default to null.");
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
        return Boolean.TRUE.toString().equals(JAVA_AWT_HEADLESS);
    }

    public static boolean isJavaVersionAtLeast(JavaVersion javaVersion) {
        return a.atLeast(javaVersion);
    }

    static boolean a(String str, String str2) {
        if (str == null) {
            return false;
        }
        return str.startsWith(str2);
    }

    static boolean a(String str, String str2, String str3, String str4) {
        return str != null && str2 != null && b(str, str3) && c(str2, str4);
    }

    static boolean b(String str, String str2) {
        if (str == null) {
            return false;
        }
        return str.startsWith(str2);
    }

    static boolean c(String str, String str2) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        String[] split = str2.split("\\.");
        String[] split2 = str.split("\\.");
        for (int i = 0; i < Math.min(split.length, split2.length); i++) {
            if (!split[i].equals(split2[i])) {
                return false;
            }
        }
        return true;
    }
}
