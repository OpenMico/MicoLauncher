package com.blankj.utilcode.util;

import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import com.xiaomi.accountsdk.account.AccountIntent;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/* loaded from: classes.dex */
public final class RomUtils {
    private static final String[] a = {"huawei"};
    private static final String[] b = {"vivo"};
    private static final String[] c = {"xiaomi"};
    private static final String[] d = {"oppo"};
    private static final String[] e = {"leeco", "letv"};
    private static final String[] f = {"360", "qiku"};
    private static final String[] g = {"zte"};
    private static final String[] h = {"oneplus"};
    private static final String[] i = {"nubia"};
    private static final String[] j = {"coolpad", "yulong"};
    private static final String[] k = {"lg", "lge"};
    private static final String[] l = {AccountIntent.GOOGLE_SNS_TYPE};
    private static final String[] m = {"samsung"};
    private static final String[] n = {"meizu"};
    private static final String[] o = {"lenovo"};
    private static final String[] p = {"smartisan", "deltainno"};
    private static final String[] q = {"htc"};
    private static final String[] r = {"sony"};
    private static final String[] s = {"gionee", "amigo"};
    private static final String[] t = {"motorola"};
    private static RomInfo u = null;

    private RomUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean isHuawei() {
        return a[0].equals(getRomInfo().a);
    }

    public static boolean isVivo() {
        return b[0].equals(getRomInfo().a);
    }

    public static boolean isXiaomi() {
        return c[0].equals(getRomInfo().a);
    }

    public static boolean isOppo() {
        return d[0].equals(getRomInfo().a);
    }

    public static boolean isLeeco() {
        return e[0].equals(getRomInfo().a);
    }

    public static boolean is360() {
        return f[0].equals(getRomInfo().a);
    }

    public static boolean isZte() {
        return g[0].equals(getRomInfo().a);
    }

    public static boolean isOneplus() {
        return h[0].equals(getRomInfo().a);
    }

    public static boolean isNubia() {
        return i[0].equals(getRomInfo().a);
    }

    public static boolean isCoolpad() {
        return j[0].equals(getRomInfo().a);
    }

    public static boolean isLg() {
        return k[0].equals(getRomInfo().a);
    }

    public static boolean isGoogle() {
        return l[0].equals(getRomInfo().a);
    }

    public static boolean isSamsung() {
        return m[0].equals(getRomInfo().a);
    }

    public static boolean isMeizu() {
        return n[0].equals(getRomInfo().a);
    }

    public static boolean isLenovo() {
        return o[0].equals(getRomInfo().a);
    }

    public static boolean isSmartisan() {
        return p[0].equals(getRomInfo().a);
    }

    public static boolean isHtc() {
        return q[0].equals(getRomInfo().a);
    }

    public static boolean isSony() {
        return r[0].equals(getRomInfo().a);
    }

    public static boolean isGionee() {
        return s[0].equals(getRomInfo().a);
    }

    public static boolean isMotorola() {
        return t[0].equals(getRomInfo().a);
    }

    public static RomInfo getRomInfo() {
        RomInfo romInfo = u;
        if (romInfo != null) {
            return romInfo;
        }
        u = new RomInfo();
        String b2 = b();
        String a2 = a();
        if (a(b2, a2, a)) {
            u.a = a[0];
            String a3 = a("ro.build.version.emui");
            String[] split = a3.split("_");
            if (split.length > 1) {
                u.b = split[1];
            } else {
                u.b = a3;
            }
            return u;
        } else if (a(b2, a2, b)) {
            u.a = b[0];
            u.b = a("ro.vivo.os.build.display.id");
            return u;
        } else if (a(b2, a2, c)) {
            u.a = c[0];
            u.b = a("ro.build.version.incremental");
            return u;
        } else if (a(b2, a2, d)) {
            u.a = d[0];
            u.b = a("ro.build.version.opporom");
            return u;
        } else if (a(b2, a2, e)) {
            u.a = e[0];
            u.b = a("ro.letv.release.version");
            return u;
        } else if (a(b2, a2, f)) {
            u.a = f[0];
            u.b = a("ro.build.uiversion");
            return u;
        } else if (a(b2, a2, g)) {
            u.a = g[0];
            u.b = a("ro.build.MiFavor_version");
            return u;
        } else if (a(b2, a2, h)) {
            u.a = h[0];
            u.b = a("ro.rom.version");
            return u;
        } else if (a(b2, a2, i)) {
            u.a = i[0];
            u.b = a("ro.build.rom.id");
            return u;
        } else {
            if (a(b2, a2, j)) {
                u.a = j[0];
            } else if (a(b2, a2, k)) {
                u.a = k[0];
            } else if (a(b2, a2, l)) {
                u.a = l[0];
            } else if (a(b2, a2, m)) {
                u.a = m[0];
            } else if (a(b2, a2, n)) {
                u.a = n[0];
            } else if (a(b2, a2, o)) {
                u.a = o[0];
            } else if (a(b2, a2, p)) {
                u.a = p[0];
            } else if (a(b2, a2, q)) {
                u.a = q[0];
            } else if (a(b2, a2, r)) {
                u.a = r[0];
            } else if (a(b2, a2, s)) {
                u.a = s[0];
            } else if (a(b2, a2, t)) {
                u.a = t[0];
            } else {
                u.a = a2;
            }
            u.b = a("");
            return u;
        }
    }

    private static boolean a(String str, String str2, String... strArr) {
        for (String str3 : strArr) {
            if (str.contains(str3) || str2.contains(str3)) {
                return true;
            }
        }
        return false;
    }

    private static String a() {
        try {
            String str = Build.MANUFACTURER;
            return !TextUtils.isEmpty(str) ? str.toLowerCase() : "unknown";
        } catch (Throwable unused) {
            return "unknown";
        }
    }

    private static String b() {
        try {
            String str = Build.BRAND;
            return !TextUtils.isEmpty(str) ? str.toLowerCase() : "unknown";
        } catch (Throwable unused) {
            return "unknown";
        }
    }

    private static String a(String str) {
        String str2 = "";
        if (!TextUtils.isEmpty(str)) {
            str2 = b(str);
        }
        if (TextUtils.isEmpty(str2) || str2.equals("unknown")) {
            try {
                String str3 = Build.DISPLAY;
                if (!TextUtils.isEmpty(str3)) {
                    str2 = str3.toLowerCase();
                }
            } catch (Throwable unused) {
            }
        }
        return TextUtils.isEmpty(str2) ? "unknown" : str2;
    }

    private static String b(String str) {
        String c2 = c(str);
        if (!TextUtils.isEmpty(c2)) {
            return c2;
        }
        String d2 = d(str);
        return (TextUtils.isEmpty(d2) && Build.VERSION.SDK_INT < 28) ? e(str) : d2;
    }

    private static String c(String str) {
        Throwable th;
        BufferedReader bufferedReader;
        String readLine;
        try {
            BufferedReader bufferedReader2 = null;
            try {
                Runtime runtime = Runtime.getRuntime();
                bufferedReader = new BufferedReader(new InputStreamReader(runtime.exec("getprop " + str).getInputStream()), 1024);
            } catch (IOException unused) {
            } catch (Throwable th2) {
                th = th2;
            }
            try {
                readLine = bufferedReader.readLine();
            } catch (IOException unused2) {
                bufferedReader2 = bufferedReader;
                if (bufferedReader2 == null) {
                    return "";
                }
                bufferedReader2.close();
                return "";
            } catch (Throwable th3) {
                th = th3;
                bufferedReader2 = bufferedReader;
                if (bufferedReader2 != null) {
                    try {
                        bufferedReader2.close();
                    } catch (IOException unused3) {
                    }
                }
                throw th;
            }
            if (readLine != null) {
                try {
                    bufferedReader.close();
                } catch (IOException unused4) {
                }
                return readLine;
            }
            bufferedReader.close();
            return "";
        } catch (IOException unused5) {
            return "";
        }
    }

    private static String d(String str) {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
            return properties.getProperty(str, "");
        } catch (Exception unused) {
            return "";
        }
    }

    private static String e(String str) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod(BluetoothConstants.GET, String.class, String.class).invoke(cls, str, "");
        } catch (Exception unused) {
            return "";
        }
    }

    /* loaded from: classes.dex */
    public static class RomInfo {
        private String a;
        private String b;

        public String getName() {
            return this.a;
        }

        public String getVersion() {
            return this.b;
        }

        public String toString() {
            return "RomInfo{name=" + this.a + ", version=" + this.b + "}";
        }
    }
}
