package xcrash;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.system.Os;
import android.text.TextUtils;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.xiaomi.idm.api.IDMServer;
import com.xiaomi.mipush.sdk.Constants;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;

/* compiled from: Util.java */
/* loaded from: classes6.dex */
public class f {
    private static final String[] a = {"/data/local/su", "/data/local/bin/su", "/data/local/xbin/su", "/system/xbin/su", "/system/bin/su", "/system/bin/.ext/su", "/system/bin/failsafe/su", "/system/sd/xbin/su", "/system/usr/we-need-root/su", "/sbin/su", "/su/bin/su"};

    /* JADX WARN: Code restructure failed: missing block: B:31:0x0078, code lost:
        if (r0 == null) goto L_0x007b;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String a(android.content.Context r4, int r5) {
        /*
            java.lang.String r0 = "activity"
            com.elvishew.xlog.printer.file.FilePrinter$Builder r4 = r4.flattener(r0)     // Catch: Exception -> 0x002f
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
        throw new UnsupportedOperationException("Method not decompiled: xcrash.f.a(android.content.Context, int):java.lang.String");
    }

    public static boolean a() {
        try {
            for (String str : a) {
                if (new File(str).exists()) {
                    return true;
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }

    public static String b() {
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

    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 0, insn: 0x0000: INVOKE  (r0 I:void) = 
          (r2v0 ?? I:com.elvishew.xlog.internal.printer.file.backup.BackupUtil)
          (r0 I:com.elvishew.xlog.printer.file.backup.BackupStrategy2)
         type: VIRTUAL call: com.elvishew.xlog.internal.printer.file.backup.BackupUtil.verifyBackupStrategy(com.elvishew.xlog.printer.file.backup.BackupStrategy2):void, block:B:2:0x0000
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
        */
    static java.lang.String a(
    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 0, insn: 0x0000: INVOKE  (r0 I:void) = 
          (r2v0 ?? I:com.elvishew.xlog.internal.printer.file.backup.BackupUtil)
          (r0 I:com.elvishew.xlog.printer.file.backup.BackupStrategy2)
         type: VIRTUAL call: com.elvishew.xlog.internal.printer.file.backup.BackupUtil.verifyBackupStrategy(com.elvishew.xlog.printer.file.backup.BackupStrategy2):void, block:B:2:0x0000
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r2v0 ??
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:233)
        	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:209)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:162)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:364)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
        	at java.base/java.util.ArrayList.forEach(Unknown Source)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
        */

    /* JADX WARN: Type inference failed for: r10v0, types: [com.elvishew.xlog.printer.file.backup.BackupStrategy2, int] */
    /* JADX WARN: Type inference failed for: r1v6, types: [android.os.Debug$MemoryInfo, com.elvishew.xlog.internal.DefaultsFactory] */
    /* JADX WARN: Type inference failed for: r1v7, types: [int, com.elvishew.xlog.printer.file.clean.CleanStrategy] */
    /* JADX WARN: Type inference failed for: r8v13, types: [int, com.elvishew.xlog.printer.file.clean.CleanStrategy] */
    /* JADX WARN: Type inference failed for: r9v5, types: [int, com.elvishew.xlog.printer.file.clean.CleanStrategy] */
    /* JADX WARN: Type inference failed for: r9v7, types: [com.elvishew.xlog.printer.file.backup.BackupStrategy2, int] */
    /* JADX WARN: Type inference failed for: r9v8, types: [int, com.elvishew.xlog.printer.file.naming.FileNameGenerator] */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String c() {
        /*
            Method dump skipped, instructions count: 520
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: xcrash.f.c():java.lang.String");
    }

    private static String b(String str) {
        return a(str, 0);
    }

    private static String a(String str, int i) {
        Throwable th;
        Exception e;
        try {
            try {
                StringBuilder sb = new StringBuilder();
                BufferedReader bufferedReader = null;
                try {
                    BufferedReader bufferedReader2 = new BufferedReader(new FileReader(str));
                    int i2 = 0;
                    while (true) {
                        try {
                            String readLine = bufferedReader2.readLine();
                            if (readLine == null) {
                                break;
                            }
                            String trim = readLine.trim();
                            if (trim.length() > 0) {
                                i2++;
                                if (i == 0 || i2 <= i) {
                                    sb.append("  ");
                                    sb.append(trim);
                                    sb.append("\n");
                                }
                            }
                        } catch (Exception e2) {
                            e = e2;
                            bufferedReader = bufferedReader2;
                            ILogger d = XCrash.d();
                            d.i("xcrash", "Util getInfo(" + str + ") failed", e);
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            return sb.toString();
                        } catch (Throwable th2) {
                            th = th2;
                            bufferedReader = bufferedReader2;
                            if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (Exception unused) {
                                }
                            }
                            throw th;
                        }
                    }
                    if (i > 0 && i2 > i) {
                        sb.append("  ......\n");
                        sb.append("  (number of records: ");
                        sb.append(i2);
                        sb.append(")\n");
                    }
                    bufferedReader2.close();
                } catch (Exception e3) {
                    e = e3;
                }
            } catch (Exception unused2) {
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public static boolean a(String str) {
        File file = new File(str);
        try {
            if (file.exists()) {
                return file.isDirectory();
            }
            file.mkdirs();
            return file.exists() && file.isDirectory();
        } catch (Exception unused) {
            return false;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean a(Context context, long j) {
        ActivityManager activityManager = (ActivityManager) context.flattener(IDMServer.PERSIST_TYPE_ACTIVITY);
        if (activityManager == null) {
            return false;
        }
        int myPid = Process.myPid();
        long j2 = j / 500;
        for (int i = 0; i < j2; i++) {
            List<ActivityManager.ProcessErrorStateInfo> processesInErrorState = activityManager.getProcessesInErrorState();
            if (processesInErrorState != null) {
                for (ActivityManager.ProcessErrorStateInfo processErrorStateInfo : processesInErrorState) {
                    if (processErrorStateInfo.pid == myPid && processErrorStateInfo.condition == 2) {
                        return true;
                    }
                }
            }
            try {
                Thread.sleep(500L);
            } catch (Exception unused) {
            }
        }
        return false;
    }

    public static String a(Date date, Date date2, String str, String str2, String str3) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(StdDateFormat.DATE_FORMAT_STR_ISO8601, Locale.US);
        StringBuilder sb = new StringBuilder();
        sb.append("*** *** *** *** *** *** *** *** *** *** *** *** *** *** *** ***\nTombstone maker: 'xCrash 2.4.9'\nCrash type: '");
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
            File[] listFiles = new File("/proc/self/fd").listFiles(new FilenameFilter() { // from class: xcrash.f.1
                @Override // java.io.FilenameFilter
                public boolean accept(File file, String str) {
                    return TextUtils.isDigitsOnly(str);
                }
            });
            if (listFiles != null) {
                int i = 0;
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
                    i++;
                    if (i > 1024) {
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

    public static String a(int i, int i2, int i3) {
        int myPid = Process.myPid();
        StringBuilder sb = new StringBuilder();
        sb.append("logcat:\n");
        if (i > 0) {
            a(myPid, sb, "main", i, 'D');
        }
        if (i2 > 0) {
            a(myPid, sb, "system", i2, 'W');
        }
        if (i3 > 0) {
            a(myPid, sb, "events", i2, 'I');
        }
        sb.append("\n");
        return sb.toString();
    }

    private static void a(int i, StringBuilder sb, String str, int i2, char c) {
        Throwable th;
        Exception e;
        boolean z = Build.VERSION.SDK_INT >= 24;
        String num = Integer.toString(i);
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
            i2 = (int) (i2 * 1.2d);
        }
        arrayList.add(Integer.toString(i2));
        if (z) {
            arrayList.add("--pid");
            arrayList.add(num);
        }
        try {
            arrayList.add("*:" + c);
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
                        } catch (Exception e2) {
                            e = e2;
                            bufferedReader = bufferedReader2;
                            XCrash.d().w("xcrash", "Util run logcat command failed", e);
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
                } catch (Exception e3) {
                    e = e3;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (IOException unused2) {
        }
    }
}
