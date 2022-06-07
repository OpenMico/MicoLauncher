package com.xiaomi.push;

import android.content.Context;
import android.content.SharedPreferences;
import com.xiaomi.channel.commonutils.logger.b;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class hd {
    private static boolean a = false;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static class a implements Runnable {
        private Context a;
        private hg b;

        public a(Context context, hg hgVar) {
            this.b = hgVar;
            this.a = context;
        }

        @Override // java.lang.Runnable
        public void run() {
            hd.c(this.a, this.b);
        }
    }

    private static void a(Context context) {
        File file = new File(context.getFilesDir() + "/tdReadTemp");
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static void a(Context context, hg hgVar) {
        aj.a(context).a(new a(context, hgVar));
    }

    private static void a(Context context, hg hgVar, File file, byte[] bArr) {
        Throwable th;
        BufferedInputStream bufferedInputStream;
        BufferedInputStream bufferedInputStream2;
        Exception e;
        String str;
        int a2;
        try {
            ArrayList arrayList = new ArrayList();
            byte[] bArr2 = new byte[4];
            bufferedInputStream2 = null;
            try {
                bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                int i = 0;
                loop0: while (true) {
                    int i2 = 0;
                    while (true) {
                        try {
                            int read = bufferedInputStream.read(bArr2);
                            if (read != -1) {
                                if (read == 4) {
                                    a2 = ad.a(bArr2);
                                    if (a2 < 1 || a2 > 10240) {
                                        break loop0;
                                    }
                                    byte[] bArr3 = new byte[a2];
                                    int read2 = bufferedInputStream.read(bArr3);
                                    if (read2 != a2) {
                                        str = "TinyData read from cache file failed cause buffer size not equal length. size:" + read2 + "__length:" + a2;
                                        break loop0;
                                    }
                                    byte[] a3 = h.a(bArr, bArr3);
                                    if (!(a3 == null || a3.length == 0)) {
                                        hl hlVar = new hl();
                                        ir.a(hlVar, a3);
                                        arrayList.add(hlVar);
                                        i++;
                                        i2 += a3.length;
                                        if (i >= 8 || i2 >= 10240) {
                                        }
                                    }
                                    b.d("TinyData read from cache file failed cause decrypt fail");
                                } else {
                                    str = "TinyData read from cache file failed cause lengthBuffer error. size:" + read;
                                    break loop0;
                                }
                            } else {
                                break loop0;
                            }
                        } catch (Exception e2) {
                            e = e2;
                            bufferedInputStream2 = bufferedInputStream;
                            b.a(e);
                            z.a(bufferedInputStream2);
                            return;
                        } catch (Throwable th2) {
                            th = th2;
                            z.a(bufferedInputStream);
                            throw th;
                        }
                    }
                    b.m149a("TinyData readTinyDataFromFile upload tiny data by part. items:" + arrayList.size() + " ts:" + System.currentTimeMillis());
                    he.a(context, hgVar, arrayList);
                    arrayList = new ArrayList();
                }
                str = "TinyData read from cache file failed cause lengthBuffer < 1 || too big. length:" + a2;
                b.d(str);
                he.a(context, hgVar, arrayList);
                b.m149a("TinyData readTinyDataFromFile upload tiny data at last. items:" + arrayList.size() + " ts:" + System.currentTimeMillis());
                if (file != null && file.exists() && !file.delete()) {
                    b.m149a("TinyData delete reading temp file failed");
                }
                z.a(bufferedInputStream);
            } catch (Exception e3) {
                e = e3;
            }
        } catch (Throwable th3) {
            th = th3;
            bufferedInputStream = bufferedInputStream2;
        }
    }

    private static void b(Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences("mipush_extra", 4).edit();
        edit.putLong("last_tiny_data_upload_timestamp", System.currentTimeMillis() / 1000);
        edit.commit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00bf  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00c5  */
    /* JADX WARN: Type inference failed for: r2v13 */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v15 */
    /* JADX WARN: Type inference failed for: r2v16 */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v18 */
    /* JADX WARN: Type inference failed for: r2v4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void c(android.content.Context r7, com.xiaomi.push.hg r8) {
        /*
            boolean r0 = com.xiaomi.push.hd.a
            if (r0 != 0) goto L_0x00e7
            r0 = 1
            com.xiaomi.push.hd.a = r0
            java.io.File r0 = new java.io.File
            java.io.File r1 = r7.getFilesDir()
            java.lang.String r2 = "tiny_data.data"
            r0.<init>(r1, r2)
            boolean r1 = r0.exists()
            if (r1 != 0) goto L_0x001e
            java.lang.String r7 = "TinyData no ready file to get data."
            com.xiaomi.channel.commonutils.logger.b.m149a(r7)
            return
        L_0x001e:
            a(r7)
            byte[] r1 = com.xiaomi.push.service.bf.a(r7)
            r2 = 0
            java.io.File r3 = new java.io.File     // Catch: Exception -> 0x007d, all -> 0x007a
            java.io.File r4 = r7.getFilesDir()     // Catch: Exception -> 0x007d, all -> 0x007a
            java.lang.String r5 = "tiny_data.lock"
            r3.<init>(r4, r5)     // Catch: Exception -> 0x007d, all -> 0x007a
            com.xiaomi.push.z.m1176a(r3)     // Catch: Exception -> 0x007d, all -> 0x007a
            java.io.RandomAccessFile r4 = new java.io.RandomAccessFile     // Catch: Exception -> 0x007d, all -> 0x007a
            java.lang.String r5 = "rw"
            r4.<init>(r3, r5)     // Catch: Exception -> 0x007d, all -> 0x007a
            java.nio.channels.FileChannel r3 = r4.getChannel()     // Catch: Exception -> 0x0078, all -> 0x00d2
            java.nio.channels.FileLock r2 = r3.lock()     // Catch: Exception -> 0x0078, all -> 0x00d2
            java.io.File r3 = new java.io.File     // Catch: Exception -> 0x0078, all -> 0x00d2
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: Exception -> 0x0078, all -> 0x00d2
            r5.<init>()     // Catch: Exception -> 0x0078, all -> 0x00d2
            java.io.File r6 = r7.getFilesDir()     // Catch: Exception -> 0x0078, all -> 0x00d2
            r5.append(r6)     // Catch: Exception -> 0x0078, all -> 0x00d2
            java.lang.String r6 = "/tdReadTemp"
            r5.append(r6)     // Catch: Exception -> 0x0078, all -> 0x00d2
            java.lang.String r6 = "/"
            r5.append(r6)     // Catch: Exception -> 0x0078, all -> 0x00d2
            java.lang.String r6 = "tiny_data.data"
            r5.append(r6)     // Catch: Exception -> 0x0078, all -> 0x00d2
            java.lang.String r5 = r5.toString()     // Catch: Exception -> 0x0078, all -> 0x00d2
            r3.<init>(r5)     // Catch: Exception -> 0x0078, all -> 0x00d2
            r0.renameTo(r3)     // Catch: Exception -> 0x0078, all -> 0x00d2
            if (r2 == 0) goto L_0x0092
            boolean r0 = r2.isValid()
            if (r0 == 0) goto L_0x0092
            r2.release()     // Catch: IOException -> 0x0076
            goto L_0x0092
        L_0x0076:
            r0 = move-exception
            goto L_0x008f
        L_0x0078:
            r0 = move-exception
            goto L_0x007f
        L_0x007a:
            r7 = move-exception
            r4 = r2
            goto L_0x00d3
        L_0x007d:
            r0 = move-exception
            r4 = r2
        L_0x007f:
            com.xiaomi.channel.commonutils.logger.b.a(r0)     // Catch: all -> 0x00d2
            if (r2 == 0) goto L_0x0092
            boolean r0 = r2.isValid()
            if (r0 == 0) goto L_0x0092
            r2.release()     // Catch: IOException -> 0x008e
            goto L_0x0092
        L_0x008e:
            r0 = move-exception
        L_0x008f:
            com.xiaomi.channel.commonutils.logger.b.a(r0)
        L_0x0092:
            com.xiaomi.push.z.a(r4)
            java.io.File r0 = new java.io.File
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.io.File r3 = r7.getFilesDir()
            r2.append(r3)
            java.lang.String r3 = "/tdReadTemp"
            r2.append(r3)
            java.lang.String r3 = "/"
            r2.append(r3)
            java.lang.String r3 = "tiny_data.data"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r0.<init>(r2)
            boolean r2 = r0.exists()
            if (r2 != 0) goto L_0x00c5
            java.lang.String r7 = "TinyData no ready file to get data."
            com.xiaomi.channel.commonutils.logger.b.m149a(r7)
            return
        L_0x00c5:
            a(r7, r8, r0, r1)
            r8 = 0
            com.xiaomi.push.hc.a(r8)
            b(r7)
            com.xiaomi.push.hd.a = r8
            return
        L_0x00d2:
            r7 = move-exception
        L_0x00d3:
            if (r2 == 0) goto L_0x00e3
            boolean r8 = r2.isValid()
            if (r8 == 0) goto L_0x00e3
            r2.release()     // Catch: IOException -> 0x00df
            goto L_0x00e3
        L_0x00df:
            r8 = move-exception
            com.xiaomi.channel.commonutils.logger.b.a(r8)
        L_0x00e3:
            com.xiaomi.push.z.a(r4)
            throw r7
        L_0x00e7:
            java.lang.String r7 = "TinyData extractTinyData is running"
            com.xiaomi.channel.commonutils.logger.b.m149a(r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.hd.c(android.content.Context, com.xiaomi.push.hg):void");
    }
}
