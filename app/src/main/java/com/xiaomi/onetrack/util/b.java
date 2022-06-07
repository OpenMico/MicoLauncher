package com.xiaomi.onetrack.util;

import android.content.Context;
import android.os.Build;
import android.os.Debug;
import android.os.Process;
import android.system.Os;
import android.text.TextUtils;
import com.xiaomi.mipush.sdk.Constants;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes4.dex */
public class b {
    static final String a = "1.2.6";
    static final String b = "OneTrack 1.2.6";
    static final String c = "CrashUtil";
    public static final String d = "*** *** *** *** *** *** *** *** *** *** *** *** *** *** *** ***";
    public static final String e = "--- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---";
    public static final String f = "+++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++";
    static final String g = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String h = "java";
    static final String i = "tombstone";
    static final String j = ".java.xcrash";
    private static final String k = "%21s %8s\n";
    private static final String l = "%21s %8s %21s %8s\n";
    private static final String[] m = {"/data/local/su", "/data/local/bin/su", "/data/local/xbin/su", "/system/xbin/su", "/system/bin/su", "/system/bin/.ext/su", "/system/bin/failsafe/su", "/system/sd/xbin/su", "/system/usr/we-need-root/su", "/sbin/su", "/su/bin/su"};

    private b() {
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0078, code lost:
        if (r0 == null) goto L_0x007b;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String a(android.content.Context r4, int r5) {
        /*
            java.lang.String r0 = "activity"
            java.lang.Object r4 = r4.getSystemService(r0)     // Catch: Exception -> 0x002f
            android.app.ActivityManager r4 = (android.app.ActivityManager) r4     // Catch: Exception -> 0x002f
            if (r4 == 0) goto L_0x002f
            java.util.List r4 = r4.getRunningAppProcesses()     // Catch: Exception -> 0x002f
            if (r4 == 0) goto L_0x002f
            java.util.Iterator r4 = r4.iterator()     // Catch: Exception -> 0x002f
        L_0x0014:
            boolean r0 = r4.hasNext()     // Catch: Exception -> 0x002f
            if (r0 == 0) goto L_0x002f
            java.lang.Object r0 = r4.next()     // Catch: Exception -> 0x002f
            android.app.ActivityManager$RunningAppProcessInfo r0 = (android.app.ActivityManager.RunningAppProcessInfo) r0     // Catch: Exception -> 0x002f
            int r1 = r0.pid     // Catch: Exception -> 0x002f
            if (r1 != r5) goto L_0x0014
            java.lang.String r1 = r0.processName     // Catch: Exception -> 0x002f
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch: Exception -> 0x002f
            if (r1 != 0) goto L_0x0014
            java.lang.String r4 = r0.processName     // Catch: Exception -> 0x002f
            return r4
        L_0x002f:
            r4 = 0
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch: Exception -> 0x0077, all -> 0x0070
            java.io.FileReader r1 = new java.io.FileReader     // Catch: Exception -> 0x0077, all -> 0x0070
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: Exception -> 0x0077, all -> 0x0070
            r2.<init>()     // Catch: Exception -> 0x0077, all -> 0x0070
            java.lang.String r3 = "/proc/"
            r2.append(r3)     // Catch: Exception -> 0x0077, all -> 0x0070
            r2.append(r5)     // Catch: Exception -> 0x0077, all -> 0x0070
            java.lang.String r5 = "/cmdline"
            r2.append(r5)     // Catch: Exception -> 0x0077, all -> 0x0070
            java.lang.String r5 = r2.toString()     // Catch: Exception -> 0x0077, all -> 0x0070
            r1.<init>(r5)     // Catch: Exception -> 0x0077, all -> 0x0070
            r0.<init>(r1)     // Catch: Exception -> 0x0077, all -> 0x0070
            java.lang.String r5 = r0.readLine()     // Catch: Exception -> 0x0078, all -> 0x006c
            boolean r1 = android.text.TextUtils.isEmpty(r5)     // Catch: Exception -> 0x0078, all -> 0x006c
            if (r1 != 0) goto L_0x0068
            java.lang.String r5 = r5.trim()     // Catch: Exception -> 0x0078, all -> 0x006c
            boolean r1 = android.text.TextUtils.isEmpty(r5)     // Catch: Exception -> 0x0078, all -> 0x006c
            if (r1 != 0) goto L_0x0068
            r0.close()     // Catch: Exception -> 0x0067
        L_0x0067:
            return r5
        L_0x0068:
            r0.close()     // Catch: Exception -> 0x007b
            goto L_0x007b
        L_0x006c:
            r4 = move-exception
            r5 = r4
            r4 = r0
            goto L_0x0071
        L_0x0070:
            r5 = move-exception
        L_0x0071:
            if (r4 == 0) goto L_0x0076
            r4.close()     // Catch: Exception -> 0x0076
        L_0x0076:
            throw r5
        L_0x0077:
            r0 = r4
        L_0x0078:
            if (r0 == 0) goto L_0x007b
            goto L_0x0068
        L_0x007b:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.onetrack.util.b.a(android.content.Context, int):java.lang.String");
    }

    static boolean a() {
        try {
            for (String str : m) {
                if (new File(str).exists()) {
                    return true;
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }

    static String b() {
        if (Build.VERSION.SDK_INT >= 21) {
            return TextUtils.join(Constants.ACCEPT_TIME_SEPARATOR_SP, Build.SUPPORTED_ABIS);
        }
        String str = Build.CPU_ABI;
        String str2 = Build.CPU_ABI2;
        if (TextUtils.isEmpty(str2)) {
            return str;
        }
        return str + Constants.ACCEPT_TIME_SEPARATOR_SP + str2;
    }

    public static String a(Context context) {
        String str;
        try {
            str = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception unused) {
            str = null;
        }
        return TextUtils.isEmpty(str) ? "unknown" : str;
    }

    static String c() {
        StringBuilder sb = new StringBuilder();
        sb.append(" Process Summary (From: android.os.Debug.MemoryInfo)\n");
        sb.append(String.format(Locale.US, k, "", "Pss(KB)"));
        sb.append(String.format(Locale.US, k, "", "------"));
        try {
            Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
            Debug.getMemoryInfo(memoryInfo);
            if (Build.VERSION.SDK_INT >= 23) {
                sb.append(String.format(Locale.US, k, "Java Heap:", memoryInfo.getMemoryStat("summary.java-heap")));
                sb.append(String.format(Locale.US, k, "Native Heap:", memoryInfo.getMemoryStat("summary.native-heap")));
                sb.append(String.format(Locale.US, k, "Code:", memoryInfo.getMemoryStat("summary.code")));
                sb.append(String.format(Locale.US, k, "Stack:", memoryInfo.getMemoryStat("summary.stack")));
                sb.append(String.format(Locale.US, k, "Graphics:", memoryInfo.getMemoryStat("summary.graphics")));
                sb.append(String.format(Locale.US, k, "Private Other:", memoryInfo.getMemoryStat("summary.private-other")));
                sb.append(String.format(Locale.US, k, "System:", memoryInfo.getMemoryStat("summary.system")));
                sb.append(String.format(Locale.US, l, "TOTAL:", memoryInfo.getMemoryStat("summary.total-pss"), "TOTAL SWAP:", memoryInfo.getMemoryStat("summary.total-swap")));
            } else {
                Locale locale = Locale.US;
                sb.append(String.format(locale, k, "Java Heap:", "~ " + memoryInfo.dalvikPrivateDirty));
                sb.append(String.format(Locale.US, k, "Native Heap:", String.valueOf(memoryInfo.nativePrivateDirty)));
                Locale locale2 = Locale.US;
                sb.append(String.format(locale2, k, "Private Other:", "~ " + memoryInfo.otherPrivateDirty));
                if (Build.VERSION.SDK_INT >= 19) {
                    sb.append(String.format(Locale.US, k, "System:", String.valueOf((memoryInfo.getTotalPss() - memoryInfo.getTotalPrivateDirty()) - memoryInfo.getTotalPrivateClean())));
                } else {
                    Locale locale3 = Locale.US;
                    sb.append(String.format(locale3, k, "System:", "~ " + (memoryInfo.getTotalPss() - memoryInfo.getTotalPrivateDirty())));
                }
                sb.append(String.format(Locale.US, k, "TOTAL:", String.valueOf(memoryInfo.getTotalPss())));
            }
        } catch (Exception e2) {
            p.b(c, "CrashUtil getProcessMemoryInfo failed", e2);
        }
        return sb.toString();
    }

    private static String b(String str) {
        return a(str, 0);
    }

    private static String a(String str, int i2) {
        Throwable th;
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2;
        Exception e2;
        try {
            try {
                StringBuilder sb = new StringBuilder();
                bufferedReader2 = null;
                try {
                    bufferedReader = new BufferedReader(new FileReader(str));
                    int i3 = 0;
                    while (true) {
                        try {
                            String readLine = bufferedReader.readLine();
                            if (readLine == null) {
                                break;
                            }
                            String trim = readLine.trim();
                            if (trim.length() > 0) {
                                i3++;
                                if (i2 == 0 || i3 <= i2) {
                                    sb.append("  ");
                                    sb.append(trim);
                                    sb.append("\n");
                                }
                            }
                        } catch (Exception e3) {
                            e2 = e3;
                            bufferedReader2 = bufferedReader;
                            p.c(c, "CrashUtil getInfo(" + str + ") failed", e2);
                            if (bufferedReader2 != null) {
                                bufferedReader2.close();
                            }
                            return sb.toString();
                        } catch (Throwable th2) {
                            th = th2;
                            if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (Exception unused) {
                                }
                            }
                            throw th;
                        }
                    }
                    if (i2 > 0 && i3 > i2) {
                        sb.append("  ......\n");
                        sb.append("  (number of records: ");
                        sb.append(i3);
                        sb.append(")\n");
                    }
                    bufferedReader.close();
                } catch (Exception e4) {
                    e2 = e4;
                }
            } catch (Throwable th3) {
                th = th3;
                bufferedReader = bufferedReader2;
            }
        } catch (Exception unused2) {
        }
    }

    public static String a(Date date, Date date2, String str, String str2, String str3) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);
        StringBuilder sb = new StringBuilder();
        sb.append("*** *** *** *** *** *** *** *** *** *** *** *** *** *** *** ***\nTombstone maker: 'OneTrack 1.2.6'\nCrash type: '");
        sb.append(str);
        sb.append("'\nStart time: '");
        sb.append(simpleDateFormat.format(date));
        sb.append("'\nCrash time: '");
        sb.append(simpleDateFormat.format(date2));
        sb.append("'\nApp ID: '");
        sb.append(str2);
        sb.append("'\nApp version: '");
        sb.append(str3);
        sb.append("'\nRooted: '");
        sb.append(a() ? "Yes" : "No");
        sb.append("'\nAPI level: '");
        sb.append(Build.VERSION.SDK_INT);
        sb.append("'\nOS version: '");
        sb.append(Build.VERSION.RELEASE);
        sb.append("'\nABI list: '");
        sb.append(b());
        sb.append("'\nManufacturer: '");
        sb.append(Build.MANUFACTURER);
        sb.append("'\nBrand: '");
        sb.append(Build.BRAND);
        sb.append("'\nModel: '");
        sb.append(Build.MODEL);
        sb.append("'\nBuild fingerprint: '");
        sb.append(Build.FINGERPRINT);
        sb.append("'\n");
        return sb.toString();
    }

    public static String d() {
        return "memory info:\n System Summary (From: /proc/meminfo)\n" + b("/proc/meminfo") + "-\n Process Status (From: /proc/PID/status)\n" + b("/proc/self/status") + "-\n Process Limits (From: /proc/PID/limits)\n" + b("/proc/self/limits") + "-\n" + c() + "\n";
    }

    public static String e() {
        if (Build.VERSION.SDK_INT >= 29) {
            return "network info:\nNot supported on Android Q (API level 29) and later.\n\n";
        }
        return "network info:\n TCP over IPv4 (From: /proc/PID/net/tcp)\n" + a("/proc/self/net/tcp", 1024) + "-\n TCP over IPv6 (From: /proc/PID/net/tcp6)\n" + a("/proc/self/net/tcp6", 1024) + "-\n UDP over IPv4 (From: /proc/PID/net/udp)\n" + a("/proc/self/net/udp", 1024) + "-\n UDP over IPv6 (From: /proc/PID/net/udp6)\n" + a("/proc/self/net/udp6", 1024) + "-\n ICMP in IPv4 (From: /proc/PID/net/icmp)\n" + a("/proc/self/net/icmp", 256) + "-\n ICMP in IPv6 (From: /proc/PID/net/icmp6)\n" + a("/proc/self/net/icmp6", 256) + "-\n UNIX domain (From: /proc/PID/net/unix)\n" + a("/proc/self/net/unix", 256) + "\n";
    }

    public static String f() {
        StringBuilder sb = new StringBuilder("open files:\n");
        try {
            File[] listFiles = new File("/proc/self/fd").listFiles(new c());
            if (listFiles != null) {
                int i2 = 0;
                for (File file : listFiles) {
                    String str = null;
                    try {
                        if (Build.VERSION.SDK_INT >= 21) {
                            str = Os.readlink(file.getAbsolutePath());
                        } else {
                            str = file.getCanonicalPath();
                        }
                    } catch (Exception unused) {
                    }
                    sb.append("    fd ");
                    sb.append(file.getName());
                    sb.append(": ");
                    sb.append(TextUtils.isEmpty(str) ? "???" : str.trim());
                    sb.append('\n');
                    i2++;
                    if (i2 > 1024) {
                        break;
                    }
                }
                if (listFiles.length > 1024) {
                    sb.append("    ......\n");
                }
                sb.append("    (number of FDs: ");
                sb.append(listFiles.length);
                sb.append(")\n");
            }
        } catch (Exception unused2) {
        }
        sb.append('\n');
        return sb.toString();
    }

    public static String a(int i2, int i3, int i4) {
        int myPid = Process.myPid();
        StringBuilder sb = new StringBuilder();
        sb.append("logcat:\n");
        if (i2 > 0) {
            a(myPid, sb, "main", i2, 'D');
        }
        if (i3 > 0) {
            a(myPid, sb, "system", i3, 'W');
        }
        if (i4 > 0) {
            a(myPid, sb, "events", i3, 'I');
        }
        sb.append("\n");
        return sb.toString();
    }

    private static void a(int i2, StringBuilder sb, String str, int i3, char c2) {
        Throwable th;
        Exception e2;
        boolean z = Build.VERSION.SDK_INT >= 24;
        String num = Integer.toString(i2);
        String str2 = StringUtils.SPACE + num + StringUtils.SPACE;
        ArrayList arrayList = new ArrayList();
        arrayList.add("/system/bin/logcat");
        arrayList.add("-b");
        arrayList.add(str);
        arrayList.add("-d");
        arrayList.add("-v");
        arrayList.add("threadtime");
        arrayList.add("-t");
        if (!z) {
            i3 = (int) (i3 * 1.2d);
        }
        arrayList.add(Integer.toString(i3));
        if (z) {
            arrayList.add("--pid");
            arrayList.add(num);
        }
        try {
            arrayList.add("*:" + c2);
            Object[] array = arrayList.toArray();
            sb.append("--------- tail end of log ");
            sb.append(str);
            sb.append(" (");
            sb.append(TextUtils.join(StringUtils.SPACE, array));
            sb.append(")\n");
            BufferedReader bufferedReader = null;
            try {
                try {
                    BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(new ProcessBuilder(new String[0]).command(arrayList).start().getInputStream()));
                    while (true) {
                        try {
                            String readLine = bufferedReader2.readLine();
                            if (readLine == null) {
                                break;
                            } else if (z || readLine.contains(str2)) {
                                sb.append(readLine);
                                sb.append("\n");
                            }
                        } catch (Exception e3) {
                            e2 = e3;
                            bufferedReader = bufferedReader2;
                            p.b(c, "CrashUtil run logcat command failed", e2);
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            return;
                        } catch (Throwable th2) {
                            th = th2;
                            bufferedReader = bufferedReader2;
                            if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (IOException unused) {
                                }
                            }
                            throw th;
                        }
                    }
                    bufferedReader2.close();
                } catch (Exception e4) {
                    e2 = e4;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (IOException unused2) {
        }
    }

    public static long a(String str) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US).parse(str).getTime();
        } catch (ParseException e2) {
            e2.printStackTrace();
            return 0L;
        }
    }
}
