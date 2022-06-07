package miuix.animation.utils;

import android.app.Application;
import android.content.Context;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import dalvik.system.PathClassLoader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
public class DeviceUtils {
    public static final int DEVICE_HIGHEND = 2;
    public static final int DEVICE_MIDDLE = 1;
    public static final int DEVICE_PRIMARY = 0;
    public static final int DEVICE_UNKNOWN = -1;
    public static int TYPE_CPU;
    public static int TYPE_GPU;
    public static int TYPE_RAM;
    private static Class h;
    private static PathClassLoader i;
    private static Constructor<Class> j;
    private static Object k;
    private static Method l;
    private static Method m;
    private static Method n;
    private static Application o;
    private static Context p;
    private static int r;
    private static int s;
    private static int t;
    private static int u;
    private static final String[] v;
    static final Pattern a = Pattern.compile("Inc ([A-Z]+)([\\d]+)");
    static final Pattern b = Pattern.compile("MT([\\d]{2})([\\d]+)");
    static int c = -1;
    static int d = -1;
    static int e = -1;
    static int f = -1;
    static int g = Integer.MAX_VALUE;
    public static int DEV_STANDARD_VERSION = 1;
    private static int q = DEV_STANDARD_VERSION;

    static {
        j = null;
        k = null;
        l = null;
        m = null;
        n = null;
        TYPE_RAM = 1;
        TYPE_CPU = 2;
        TYPE_GPU = 3;
        try {
            i = new PathClassLoader("/system/framework/MiuiBooster.jar", ClassLoader.getSystemClassLoader());
            h = i.loadClass("com.miui.performance.DeviceLevelUtils");
            j = h.getConstructor(Context.class);
            l = h.getDeclaredMethod("getDeviceLevel", Integer.TYPE, Integer.TYPE);
            m = h.getDeclaredMethod("getDeviceLevel", Integer.TYPE);
            n = h.getDeclaredMethod("isSupportPrune", new Class[0]);
            TYPE_RAM = ((Integer) a(h, "DEVICE_LEVEL_FOR_RAM", Integer.TYPE)).intValue();
            TYPE_CPU = ((Integer) a(h, "DEVICE_LEVEL_FOR_CPU", Integer.TYPE)).intValue();
            TYPE_GPU = ((Integer) a(h, "DEVICE_LEVEL_FOR_GPU", Integer.TYPE)).intValue();
            r = ((Integer) a(h, "LOW_DEVICE", Integer.TYPE)).intValue();
            s = ((Integer) a(h, "MIDDLE_DEVICE", Integer.TYPE)).intValue();
            t = ((Integer) a(h, "HIGH_DEVICE", Integer.TYPE)).intValue();
            u = ((Integer) a(h, "DEVICE_LEVEL_UNKNOWN", Integer.TYPE)).intValue();
        } catch (Exception e2) {
            Log.e("DeviceUtils", "DeviceLevel(): Load Class Exception:" + e2);
        }
        if (p == null) {
            try {
                o = (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication", new Class[0]).invoke(null, null);
                if (o != null) {
                    p = o.getApplicationContext();
                }
            } catch (Exception e3) {
                Log.e("DeviceUtils", "android.app.ActivityThread Exception:" + e3);
            }
        }
        if (p == null) {
            try {
                o = (Application) Class.forName("android.app.AppGlobals").getMethod("getInitialApplication", new Class[0]).invoke(null, null);
                if (o != null) {
                    p = o.getApplicationContext();
                }
            } catch (Exception e4) {
                Log.e("DeviceUtils", "android.app.AppGlobals Exception:" + e4);
            }
        }
        try {
            if (j != null) {
                k = j.newInstance(p);
            }
        } catch (Exception e5) {
            Log.e("DeviceUtils", "DeviceLevelUtils(): newInstance Exception:" + e5);
            e5.printStackTrace();
        }
        v = new String[]{"cactus", "cereus", "pine", "olive", "ginkgo", "olivelite", "olivewood", "willow", "wayne", "dandelion", "angelica", "angelicain", "whyred", "tulip", "onc", "onclite", "lavender", "lotus", "laurus", "merlinnfc", "merlin", "lancelot", "citrus", "pomelo", "lemon", "shiva", "lime", "cannon", "curtana", "durandal", "excalibur", "joyeuse", "gram", "sunny", "mojito", "rainbow", "cattail", "angelican", "camellia"};
    }

    /* loaded from: classes5.dex */
    public static class CpuInfo {
        int a;
        int b;
        int c;
        int d;
        int e;

        public String toString() {
            return "CpuInfo{id=" + this.a + ", implementor=" + Integer.toHexString(this.b) + ", architecture=" + this.c + ", part=" + Integer.toHexString(this.d) + ", maxFreq=" + this.e + '}';
        }
    }

    /* loaded from: classes5.dex */
    public static class CpuStats {
        int a = -1;
        int b;
        int c;
        int d;

        public String toString() {
            return "CpuStats{level=" + this.a + ", maxFreq=" + this.b + ", bigCoreCount=" + this.c + ", smallCoreCount=" + this.d + '}';
        }
    }

    public static int getTotalRam() {
        if (g == Integer.MAX_VALUE) {
            try {
                g = (int) (((((Long) Class.forName("miui.util.HardwareInfo").getMethod("getTotalPhysicalMemory", new Class[0]).invoke(null, new Object[0])).longValue() / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID);
            } catch (Throwable th) {
                Log.e("DeviceUtils", th.getMessage());
                g = 0;
            }
        }
        return g;
    }

    private static boolean a() {
        try {
            return ((Boolean) Class.forName("miui.os.Build").getDeclaredField("IS_MIUI_LITE_VERSION").get(null)).booleanValue();
        } catch (Throwable th) {
            Log.i("DeviceUtils", "getDeviceLevel failed", th);
            return false;
        }
    }

    public static int getDeviceLevel() {
        return getDeviceLevel(DEV_STANDARD_VERSION);
    }

    public static int getDeviceLevel(int i2) {
        int i3;
        if (q == i2 && (i3 = c) != -1) {
            return i3;
        }
        q = i2;
        c = b(i2);
        int i4 = c;
        return i4 != -1 ? i4 : b();
    }

    public static int getDeviceLevel(int i2, int i3) {
        int i4;
        int i5;
        int i6;
        if (i3 == TYPE_CPU) {
            if (q == i2 && (i6 = d) != -1) {
                return i6;
            }
        } else if (i3 == TYPE_GPU) {
            if (q == i2 && (i5 = e) != -1) {
                return i5;
            }
        } else if (i3 == TYPE_RAM && q == i2 && (i4 = f) != -1) {
            return i4;
        }
        int a2 = a(i2, i3);
        if (a2 != -1) {
            return a(i2, a2, i3);
        }
        return a(i2, a(i3), i3);
    }

    private static int a(int i2, int i3, int i4) {
        q = i2;
        if (i4 == TYPE_CPU) {
            d = i3;
            return d;
        } else if (i4 == TYPE_GPU) {
            e = i3;
            return e;
        } else if (i4 != TYPE_RAM) {
            return -1;
        } else {
            f = i3;
            return f;
        }
    }

    private static int a(int i2) {
        if (i2 == TYPE_RAM) {
            int totalRam = getTotalRam();
            if (totalRam > 6) {
                return 2;
            }
            if (totalRam > 4) {
                return 1;
            }
            return totalRam > 0 ? 0 : -1;
        } else if (i2 == TYPE_CPU) {
            return c();
        } else {
            return -1;
        }
    }

    private static int b() {
        int i2 = c;
        if (i2 != -1) {
            return i2;
        }
        if (a()) {
            c = 0;
        } else {
            c = a(a(TYPE_CPU), a(TYPE_RAM), getDeviceLevel(DEV_STANDARD_VERSION, TYPE_GPU));
        }
        return c;
    }

    private static int a(int... iArr) {
        if (iArr.length == 0) {
            return -1;
        }
        int i2 = iArr[0];
        for (int i3 : iArr) {
            if (i3 > -1 && i3 < i2) {
                i2 = i3;
            }
        }
        return i2;
    }

    private static int c() {
        int i2;
        String d2 = d();
        if (d2.length() <= 0) {
            i2 = -1;
        } else if (d2.contains("Qualcomm")) {
            i2 = getQualcommCpuLevel(d2);
        } else {
            i2 = a(d2);
        }
        return i2 == -1 ? getCpuStats().a : i2;
    }

    private static String d() {
        try {
            Scanner scanner = new Scanner(new File("/proc/cpuinfo"));
            while (scanner.hasNextLine()) {
                String nextLine = scanner.nextLine();
                if (!scanner.hasNextLine()) {
                    String[] split = nextLine.split(": ");
                    if (split.length > 1) {
                        return split[1];
                    }
                }
            }
            return "";
        } catch (Exception e2) {
            Log.e("DeviceUtils", "getChipSetFromCpuInfo failed", e2);
            return "";
        }
    }

    public static int getQualcommCpuLevel(String str) {
        String group;
        String group2;
        Matcher matcher = a.matcher(str);
        if (!matcher.find() || (group = matcher.group(1)) == null || (group2 = matcher.group(2)) == null) {
            return -1;
        }
        String lowerCase = group.toLowerCase(Locale.ENGLISH);
        if (!lowerCase.equals("sm")) {
            return lowerCase.equals("sdm") ? Integer.parseInt(group2.substring(0, 1)) >= 7 ? 1 : 0 : lowerCase.equals("msm") ? 0 : -1;
        }
        int parseInt = Integer.parseInt(group2.substring(0, 1));
        if (parseInt >= 8) {
            return 2;
        }
        return parseInt >= 7 ? 1 : 0;
    }

    private static int a(String str) {
        String group;
        String group2;
        Matcher matcher = b.matcher(str);
        if (!matcher.find() || (group = matcher.group(1)) == null || (group2 = matcher.group(2)) == null) {
            return -1;
        }
        return (Integer.parseInt(group) != 68 || Integer.parseInt(group2) < 73) ? 0 : 1;
    }

    public static CpuStats getCpuStats() {
        List<CpuInfo> cpuInfoList = getCpuInfoList();
        CpuStats cpuStats = new CpuStats();
        if (cpuInfoList.size() < 8) {
            cpuStats.a = 0;
        }
        a(cpuStats, cpuInfoList);
        return cpuStats;
    }

    private static void a(CpuStats cpuStats, List<CpuInfo> list) {
        for (CpuInfo cpuInfo : list) {
            if (cpuInfo.c < 8) {
                cpuStats.a = 0;
            }
            if (cpuInfo.e > cpuStats.b) {
                cpuStats.b = cpuInfo.e;
            }
            if (cpuInfo.e >= 2000000) {
                cpuStats.c++;
            } else {
                cpuStats.d++;
            }
        }
        a(cpuStats);
    }

    private static void a(CpuStats cpuStats) {
        if (cpuStats.a == -1) {
            if (cpuStats.c >= 4) {
                if (cpuStats.b > 2700000) {
                    cpuStats.a = 2;
                } else if (cpuStats.b > 2300000) {
                    cpuStats.a = 1;
                } else {
                    cpuStats.a = 0;
                }
            } else if (cpuStats.b > 2300000) {
                cpuStats.a = 1;
            } else {
                cpuStats.a = 0;
            }
        }
    }

    public static List<CpuInfo> getCpuInfoList() {
        ArrayList arrayList = new ArrayList();
        try {
            Scanner scanner = new Scanner(new File("/proc/cpuinfo"));
            CpuInfo cpuInfo = null;
            while (scanner.hasNextLine()) {
                String[] split = scanner.nextLine().split(": ");
                if (split.length > 1) {
                    cpuInfo = a(split, arrayList, cpuInfo);
                }
            }
        } catch (Exception e2) {
            Log.e("DeviceUtils", "getChipSetFromCpuInfo failed", e2);
        }
        return arrayList;
    }

    private static CpuInfo a(String[] strArr, List<CpuInfo> list, CpuInfo cpuInfo) {
        String trim = strArr[1].trim();
        if (strArr[0].contains("processor") && TextUtils.isDigitsOnly(trim)) {
            CpuInfo b2 = b(trim);
            list.add(b2);
            return b2;
        } else if (cpuInfo == null) {
            return cpuInfo;
        } else {
            a(strArr[0], trim, cpuInfo);
            return cpuInfo;
        }
    }

    private static CpuInfo b(String str) {
        CpuInfo cpuInfo = new CpuInfo();
        cpuInfo.a = Integer.parseInt(str);
        String d2 = d(String.format(Locale.ENGLISH, "/sys/devices/system/cpu/cpu%d/cpufreq/cpuinfo_max_freq", Integer.valueOf(cpuInfo.a)));
        if (d2 != null) {
            cpuInfo.e = Integer.parseInt(d2);
        }
        return cpuInfo;
    }

    private static void a(String str, String str2, CpuInfo cpuInfo) {
        if (str.contains("CPU implementer")) {
            cpuInfo.b = c(str2);
        } else if (str.contains("CPU architecture")) {
            cpuInfo.c = c(str2);
        } else if (str.contains("CPU part")) {
            cpuInfo.d = c(str2);
        }
    }

    private static int c(String str) {
        if (str.startsWith("0x")) {
            return Integer.parseInt(str.substring(2), 16);
        }
        return Integer.parseInt(str);
    }

    private static String d(String str) {
        FileInputStream fileInputStream;
        Throwable th;
        try {
            fileInputStream = new FileInputStream(str);
        } catch (IOException unused) {
            fileInputStream = null;
        } catch (Throwable th2) {
            th = th2;
            fileInputStream = null;
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String readLine = bufferedReader.readLine();
            bufferedReader.close();
            try {
                fileInputStream.close();
            } catch (IOException unused2) {
            }
            return readLine;
        } catch (IOException unused3) {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException unused4) {
                }
            }
            return null;
        } catch (Throwable th3) {
            th = th3;
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException unused5) {
                }
            }
            throw th;
        }
    }

    private static int b(int i2) {
        int i3;
        try {
            i3 = ((Integer) m.invoke(k, Integer.valueOf(i2))).intValue();
        } catch (Exception e2) {
            Log.e("DeviceUtils", "getDeviceLevel failed , e:" + e2.toString());
            i3 = -1;
        }
        return c(i3);
    }

    private static int a(int i2, int i3) {
        int i4;
        try {
            i4 = ((Integer) l.invoke(k, Integer.valueOf(i2), Integer.valueOf(i3))).intValue();
        } catch (Exception e2) {
            Log.e("DeviceUtils", "getDeviceLevel failed , e:" + e2.toString());
            i4 = -1;
        }
        return c(i4);
    }

    public static boolean isSupportPrune() {
        try {
            return ((Boolean) n.invoke(k, new Object[0])).booleanValue();
        } catch (Exception e2) {
            Log.e("DeviceUtils", "isSupportPrune failed , e:" + e2.toString());
            return false;
        }
    }

    private static int c(int i2) {
        if (i2 == r) {
            return 0;
        }
        if (i2 == s) {
            return 1;
        }
        return i2 == t ? 2 : -1;
    }

    private static <T> T a(Class<?> cls, String str, Class<T> cls2) throws Exception {
        Field declaredField = cls.getDeclaredField(str);
        declaredField.setAccessible(true);
        return (T) declaredField.get(null);
    }

    public static String getProductDevice() {
        try {
            return (String) Class.forName("android.os.SystemProperties").getMethod(BluetoothConstants.GET, String.class, String.class).invoke(null, "ro.product.device", "");
        } catch (Exception e2) {
            Log.e("DeviceUtils", "getProductDevice failed , e:" + e2.toString());
            return "";
        }
    }

    public static boolean isStockDevice() {
        String productDevice = getProductDevice();
        if (productDevice == null || productDevice.length() == 0) {
            return false;
        }
        for (String str : v) {
            if (str.equalsIgnoreCase(productDevice)) {
                return true;
            }
        }
        return false;
    }
}
