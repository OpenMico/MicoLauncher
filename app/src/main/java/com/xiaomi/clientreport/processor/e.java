package com.xiaomi.clientreport.processor;

import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.clientreport.data.PerfClientReport;
import com.xiaomi.clientreport.data.a;
import com.xiaomi.push.z;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.HashMap;

/* loaded from: classes3.dex */
public class e {
    private static PerfClientReport a(PerfClientReport perfClientReport, String str) {
        long[] a;
        if (perfClientReport == null || (a = a(str)) == null) {
            return null;
        }
        perfClientReport.perfCounts = a[0];
        perfClientReport.perfLatencies = a[1];
        return perfClientReport;
    }

    public static String a(PerfClientReport perfClientReport) {
        return perfClientReport.production + "#" + perfClientReport.clientInterfaceId + "#" + perfClientReport.reportType + "#" + perfClientReport.code;
    }

    /* JADX WARN: Code restructure failed: missing block: B:58:0x00d3, code lost:
        if (r1 != null) goto L_0x00d5;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00f4  */
    /* JADX WARN: Type inference failed for: r2v0, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r2v1, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v11, types: [java.io.RandomAccessFile, java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r2v12 */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.List<java.lang.String> a(android.content.Context r7, java.lang.String r8) {
        /*
            Method dump skipped, instructions count: 249
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.clientreport.processor.e.a(android.content.Context, java.lang.String):java.util.List");
    }

    private static void a(String str, HashMap<String, String> hashMap) {
        Throwable th;
        BufferedWriter bufferedWriter;
        Exception e;
        if (!(TextUtils.isEmpty(str) || hashMap == null || hashMap.size() == 0)) {
            File file = new File(str);
            if (file.exists()) {
                file.delete();
            }
            try {
                bufferedWriter = new BufferedWriter(new FileWriter(file));
                try {
                    try {
                        for (String str2 : hashMap.keySet()) {
                            bufferedWriter.write(str2 + "%%%" + hashMap.get(str2));
                            bufferedWriter.newLine();
                        }
                    } catch (Exception e2) {
                        e = e2;
                        b.a(e);
                        z.a(bufferedWriter);
                    }
                } catch (Throwable th2) {
                    th = th2;
                    z.a(bufferedWriter);
                    throw th;
                }
            } catch (Exception e3) {
                e = e3;
                bufferedWriter = null;
            } catch (Throwable th3) {
                th = th3;
                bufferedWriter = null;
                z.a(bufferedWriter);
                throw th;
            }
            z.a(bufferedWriter);
        }
    }

    public static void a(String str, a[] aVarArr) {
        Throwable th;
        RandomAccessFile randomAccessFile;
        IOException e;
        if (!(aVarArr == null || aVarArr.length <= 0 || TextUtils.isEmpty(str))) {
            FileLock fileLock = null;
            try {
                File file = new File(str + ".lock");
                z.m1176a(file);
                randomAccessFile = new RandomAccessFile(file, "rw");
                try {
                    try {
                        fileLock = randomAccessFile.getChannel().lock();
                        HashMap<String, String> b = b(str);
                        for (a aVar : aVarArr) {
                            if (aVar != null) {
                                String a = a((PerfClientReport) aVar);
                                long j = ((PerfClientReport) aVar).perfCounts;
                                long j2 = ((PerfClientReport) aVar).perfLatencies;
                                if (!TextUtils.isEmpty(a) && j > 0 && j2 >= 0) {
                                    a(b, a, j, j2);
                                }
                            }
                        }
                        a(str, b);
                        if (fileLock != null && fileLock.isValid()) {
                            try {
                                fileLock.release();
                            } catch (IOException e2) {
                                e = e2;
                                b.a(e);
                                z.a(randomAccessFile);
                            }
                        }
                    } catch (Throwable unused) {
                        b.c("failed to write perf to file ");
                        if (fileLock != null && fileLock.isValid()) {
                            try {
                                fileLock.release();
                            } catch (IOException e3) {
                                e = e3;
                                b.a(e);
                                z.a(randomAccessFile);
                            }
                        }
                        z.a(randomAccessFile);
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (fileLock != null && fileLock.isValid()) {
                        try {
                            fileLock.release();
                        } catch (IOException e4) {
                            b.a(e4);
                        }
                    }
                    z.a(randomAccessFile);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                randomAccessFile = null;
                if (fileLock != null) {
                    fileLock.release();
                }
                z.a(randomAccessFile);
                throw th;
            }
            z.a(randomAccessFile);
        }
    }

    private static void a(HashMap<String, String> hashMap, String str, long j, long j2) {
        StringBuilder sb;
        String str2 = hashMap.get(str);
        if (TextUtils.isEmpty(str2)) {
            sb = new StringBuilder();
        } else {
            long[] a = a(str2);
            if (a == null || a[0] <= 0 || a[1] < 0) {
                sb = new StringBuilder();
            } else {
                j += a[0];
                j2 += a[1];
                sb = new StringBuilder();
            }
        }
        sb.append(j);
        sb.append("#");
        sb.append(j2);
        hashMap.put(str, sb.toString());
    }

    protected static long[] a(String str) {
        long[] jArr = new long[2];
        try {
            String[] split = str.split("#");
            if (split.length >= 2) {
                jArr[0] = Long.parseLong(split[0].trim());
                jArr[1] = Long.parseLong(split[1].trim());
            }
            return jArr;
        } catch (Exception e) {
            b.a(e);
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v10, types: [int] */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v13, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v15 */
    /* JADX WARN: Type inference failed for: r1v16 */
    /* JADX WARN: Type inference failed for: r1v17 */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r2v0, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v2, types: [java.io.Closeable, java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r3v2, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r4v0, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r5v9, types: [java.lang.Object] */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.util.HashMap<java.lang.String, java.lang.String> b(java.lang.String r5) {
        /*
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            boolean r1 = android.text.TextUtils.isEmpty(r5)
            if (r1 != 0) goto L_0x0064
            java.io.File r1 = new java.io.File
            r1.<init>(r5)
            boolean r1 = r1.exists()
            if (r1 != 0) goto L_0x0017
            goto L_0x0064
        L_0x0017:
            r1 = 0
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch: Exception -> 0x0058, all -> 0x0055
            java.io.FileReader r3 = new java.io.FileReader     // Catch: Exception -> 0x0058, all -> 0x0055
            r3.<init>(r5)     // Catch: Exception -> 0x0058, all -> 0x0055
            r2.<init>(r3)     // Catch: Exception -> 0x0058, all -> 0x0055
        L_0x0022:
            java.lang.String r5 = r2.readLine()     // Catch: Exception -> 0x0052, all -> 0x0050
            if (r5 == 0) goto L_0x004c
            java.lang.String r1 = "%%%"
            java.lang.String[] r5 = r5.split(r1)     // Catch: Exception -> 0x0052, all -> 0x0050
            int r1 = r5.length     // Catch: Exception -> 0x0052, all -> 0x0050
            r3 = 2
            if (r1 < r3) goto L_0x0022
            r1 = 0
            r3 = r5[r1]     // Catch: Exception -> 0x0052, all -> 0x0050
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch: Exception -> 0x0052, all -> 0x0050
            if (r3 != 0) goto L_0x0022
            r3 = 1
            r4 = r5[r3]     // Catch: Exception -> 0x0052, all -> 0x0050
            boolean r4 = android.text.TextUtils.isEmpty(r4)     // Catch: Exception -> 0x0052, all -> 0x0050
            if (r4 != 0) goto L_0x0022
            r1 = r5[r1]     // Catch: Exception -> 0x0052, all -> 0x0050
            r5 = r5[r3]     // Catch: Exception -> 0x0052, all -> 0x0050
            r0.put(r1, r5)     // Catch: Exception -> 0x0052, all -> 0x0050
            goto L_0x0022
        L_0x004c:
            com.xiaomi.push.z.a(r2)
            goto L_0x005f
        L_0x0050:
            r5 = move-exception
            goto L_0x0060
        L_0x0052:
            r5 = move-exception
            r1 = r2
            goto L_0x0059
        L_0x0055:
            r5 = move-exception
            r2 = r1
            goto L_0x0060
        L_0x0058:
            r5 = move-exception
        L_0x0059:
            com.xiaomi.channel.commonutils.logger.b.a(r5)     // Catch: all -> 0x0055
            com.xiaomi.push.z.a(r1)
        L_0x005f:
            return r0
        L_0x0060:
            com.xiaomi.push.z.a(r2)
            throw r5
        L_0x0064:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.clientreport.processor.e.b(java.lang.String):java.util.HashMap");
    }

    private static String[] c(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return str.split("#");
    }

    private static PerfClientReport d(String str) {
        PerfClientReport perfClientReport = null;
        try {
            String[] c = c(str);
            if (c == null || c.length < 4 || TextUtils.isEmpty(c[0]) || TextUtils.isEmpty(c[1]) || TextUtils.isEmpty(c[2]) || TextUtils.isEmpty(c[3])) {
                return null;
            }
            perfClientReport = PerfClientReport.getBlankInstance();
            perfClientReport.production = Integer.parseInt(c[0]);
            perfClientReport.clientInterfaceId = c[1];
            perfClientReport.reportType = Integer.parseInt(c[2]);
            perfClientReport.code = Integer.parseInt(c[3]);
            return perfClientReport;
        } catch (Exception unused) {
            b.c("parse per key error");
            return perfClientReport;
        }
    }
}
