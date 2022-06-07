package com.xiaomi.clientreport.processor;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.clientreport.data.EventClientReport;
import com.xiaomi.push.az;
import com.xiaomi.push.bf;
import com.xiaomi.push.h;
import com.xiaomi.push.z;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/* loaded from: classes3.dex */
public class a implements IEventProcessor {
    protected Context a;
    private HashMap<String, ArrayList<com.xiaomi.clientreport.data.a>> b;

    public a(Context context) {
        a(context);
    }

    public static String a(com.xiaomi.clientreport.data.a aVar) {
        return String.valueOf(aVar.production);
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x005c, code lost:
        r8 = "eventData read from cache file failed cause lengthBuffer < 1 || lengthBuffer > 4K";
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.util.List<java.lang.String> a(java.lang.String r8) {
        /*
            r7 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 4
            byte[] r2 = new byte[r1]
            byte[] r3 = new byte[r1]
            r4 = 0
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch: Exception -> 0x006b, all -> 0x0068
            java.io.File r6 = new java.io.File     // Catch: Exception -> 0x006b, all -> 0x0068
            r6.<init>(r8)     // Catch: Exception -> 0x006b, all -> 0x0068
            r5.<init>(r6)     // Catch: Exception -> 0x006b, all -> 0x0068
        L_0x0015:
            int r8 = r5.read(r2)     // Catch: Exception -> 0x0065, all -> 0x0063
            r4 = -1
            if (r8 != r4) goto L_0x001d
            goto L_0x005f
        L_0x001d:
            if (r8 == r1) goto L_0x0025
            java.lang.String r8 = "eventData read from cache file failed because magicNumber error"
        L_0x0021:
            com.xiaomi.channel.commonutils.logger.b.d(r8)     // Catch: Exception -> 0x0065, all -> 0x0063
            goto L_0x005f
        L_0x0025:
            int r8 = com.xiaomi.push.ad.a(r2)     // Catch: Exception -> 0x0065, all -> 0x0063
            r6 = -573785174(0xffffffffddccbbaa, float:-1.84407149E18)
            if (r8 == r6) goto L_0x0031
            java.lang.String r8 = "eventData read from cache file failed because magicNumber error"
            goto L_0x0021
        L_0x0031:
            int r8 = r5.read(r3)     // Catch: Exception -> 0x0065, all -> 0x0063
            if (r8 != r4) goto L_0x0038
            goto L_0x005f
        L_0x0038:
            if (r8 == r1) goto L_0x003d
            java.lang.String r8 = "eventData read from cache file failed cause lengthBuffer error"
            goto L_0x0021
        L_0x003d:
            int r8 = com.xiaomi.push.ad.a(r3)     // Catch: Exception -> 0x0065, all -> 0x0063
            r4 = 1
            if (r8 < r4) goto L_0x005c
            r4 = 4096(0x1000, float:5.74E-42)
            if (r8 <= r4) goto L_0x0049
            goto L_0x005c
        L_0x0049:
            byte[] r4 = new byte[r8]     // Catch: Exception -> 0x0065, all -> 0x0063
            int r6 = r5.read(r4)     // Catch: Exception -> 0x0065, all -> 0x0063
            if (r6 == r8) goto L_0x0054
            java.lang.String r8 = "eventData read from cache file failed cause buffer size not equal length"
            goto L_0x0021
        L_0x0054:
            java.lang.String r8 = r7.bytesToString(r4)     // Catch: Exception -> 0x0065, all -> 0x0063
            r0.add(r8)     // Catch: Exception -> 0x0065, all -> 0x0063
            goto L_0x0015
        L_0x005c:
            java.lang.String r8 = "eventData read from cache file failed cause lengthBuffer < 1 || lengthBuffer > 4K"
            goto L_0x0021
        L_0x005f:
            com.xiaomi.push.z.a(r5)
            goto L_0x0072
        L_0x0063:
            r8 = move-exception
            goto L_0x0073
        L_0x0065:
            r8 = move-exception
            r4 = r5
            goto L_0x006c
        L_0x0068:
            r8 = move-exception
            r5 = r4
            goto L_0x0073
        L_0x006b:
            r8 = move-exception
        L_0x006c:
            com.xiaomi.channel.commonutils.logger.b.a(r8)     // Catch: all -> 0x0068
            com.xiaomi.push.z.a(r4)
        L_0x0072:
            return r0
        L_0x0073:
            com.xiaomi.push.z.a(r5)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.clientreport.processor.a.a(java.lang.String):java.util.List");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [boolean] */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v4, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r8v1, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r8v2 */
    /* JADX WARN: Type inference failed for: r8v4, types: [java.io.Closeable, java.io.BufferedOutputStream] */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(com.xiaomi.clientreport.data.a[] r7, java.lang.String r8) {
        /*
            r6 = this;
            if (r7 == 0) goto L_0x0075
            int r0 = r7.length
            if (r0 <= 0) goto L_0x0075
            boolean r0 = android.text.TextUtils.isEmpty(r8)
            if (r0 == 0) goto L_0x000d
            goto L_0x0075
        L_0x000d:
            r0 = 0
            java.io.File r1 = new java.io.File
            r1.<init>(r8)
            java.io.BufferedOutputStream r8 = new java.io.BufferedOutputStream     // Catch: Exception -> 0x0067, all -> 0x0064
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch: Exception -> 0x0067, all -> 0x0064
            r3 = 1
            r2.<init>(r1, r3)     // Catch: Exception -> 0x0067, all -> 0x0064
            r8.<init>(r2)     // Catch: Exception -> 0x0067, all -> 0x0064
            int r0 = r7.length     // Catch: Exception -> 0x0061, all -> 0x005f
            r1 = 0
        L_0x0020:
            if (r1 >= r0) goto L_0x005b
            r2 = r7[r1]     // Catch: Exception -> 0x0061, all -> 0x005f
            if (r2 != 0) goto L_0x0027
            goto L_0x0058
        L_0x0027:
            java.lang.String r2 = r2.toJsonString()     // Catch: Exception -> 0x0061, all -> 0x005f
            byte[] r2 = r6.stringToBytes(r2)     // Catch: Exception -> 0x0061, all -> 0x005f
            if (r2 == 0) goto L_0x0053
            int r4 = r2.length     // Catch: Exception -> 0x0061, all -> 0x005f
            if (r4 < r3) goto L_0x0053
            int r4 = r2.length     // Catch: Exception -> 0x0061, all -> 0x005f
            r5 = 4096(0x1000, float:5.74E-42)
            if (r4 <= r5) goto L_0x003a
            goto L_0x0053
        L_0x003a:
            r4 = -573785174(0xffffffffddccbbaa, float:-1.84407149E18)
            byte[] r4 = com.xiaomi.push.ad.a(r4)     // Catch: Exception -> 0x0061, all -> 0x005f
            r8.write(r4)     // Catch: Exception -> 0x0061, all -> 0x005f
            int r4 = r2.length     // Catch: Exception -> 0x0061, all -> 0x005f
            byte[] r4 = com.xiaomi.push.ad.a(r4)     // Catch: Exception -> 0x0061, all -> 0x005f
            r8.write(r4)     // Catch: Exception -> 0x0061, all -> 0x005f
            r8.write(r2)     // Catch: Exception -> 0x0061, all -> 0x005f
            r8.flush()     // Catch: Exception -> 0x0061, all -> 0x005f
            goto L_0x0058
        L_0x0053:
            java.lang.String r2 = "event data throw a invalid item "
            com.xiaomi.channel.commonutils.logger.b.d(r2)     // Catch: Exception -> 0x0061, all -> 0x005f
        L_0x0058:
            int r1 = r1 + 1
            goto L_0x0020
        L_0x005b:
            com.xiaomi.push.z.a(r8)
            goto L_0x0070
        L_0x005f:
            r7 = move-exception
            goto L_0x0071
        L_0x0061:
            r7 = move-exception
            r0 = r8
            goto L_0x0068
        L_0x0064:
            r7 = move-exception
            r8 = r0
            goto L_0x0071
        L_0x0067:
            r7 = move-exception
        L_0x0068:
            java.lang.String r8 = "event data write to cache file failed cause exception"
            com.xiaomi.channel.commonutils.logger.b.a(r8, r7)     // Catch: all -> 0x0064
            com.xiaomi.push.z.a(r0)
        L_0x0070:
            return
        L_0x0071:
            com.xiaomi.push.z.a(r8)
            throw r7
        L_0x0075:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.clientreport.processor.a.a(com.xiaomi.clientreport.data.a[], java.lang.String):void");
    }

    private String b(com.xiaomi.clientreport.data.a aVar) {
        File externalFilesDir = this.a.getExternalFilesDir("event");
        String a = a(aVar);
        if (externalFilesDir == null) {
            return null;
        }
        String str = externalFilesDir.getAbsolutePath() + File.separator + a;
        for (int i = 0; i < 100; i++) {
            String str2 = str + i;
            if (bf.m778a(this.a, str2)) {
                return str2;
            }
        }
        return null;
    }

    @Override // com.xiaomi.clientreport.processor.c
    public void a() {
        Throwable th;
        Exception e;
        bf.a(this.a, "event", "eventUploading");
        File[] a = bf.m779a(this.a, "eventUploading");
        if (a != null && a.length > 0) {
            FileLock fileLock = null;
            RandomAccessFile randomAccessFile = null;
            File file = null;
            for (File file2 : a) {
                if (file2 == null) {
                    if (fileLock != null && fileLock.isValid()) {
                        try {
                            fileLock.release();
                        } catch (IOException e2) {
                            b.a(e2);
                        }
                    }
                    z.a(randomAccessFile);
                    if (file == null) {
                    }
                    file.delete();
                } else {
                    try {
                        try {
                            String absolutePath = file2.getAbsolutePath();
                            File file3 = new File(absolutePath + ".lock");
                            try {
                                z.m1176a(file3);
                                RandomAccessFile randomAccessFile2 = new RandomAccessFile(file3, "rw");
                                try {
                                    fileLock = randomAccessFile2.getChannel().lock();
                                    a(a(absolutePath));
                                    file2.delete();
                                    if (fileLock != null && fileLock.isValid()) {
                                        try {
                                            fileLock.release();
                                        } catch (IOException e3) {
                                            b.a(e3);
                                        }
                                    }
                                    z.a(randomAccessFile2);
                                    file3.delete();
                                    randomAccessFile = randomAccessFile2;
                                    file = file3;
                                } catch (Exception e4) {
                                    e = e4;
                                    randomAccessFile = randomAccessFile2;
                                    file = file3;
                                    b.a(e);
                                    if (fileLock != null && fileLock.isValid()) {
                                        try {
                                            fileLock.release();
                                        } catch (IOException e5) {
                                            b.a(e5);
                                        }
                                    }
                                    z.a(randomAccessFile);
                                    if (file == null) {
                                    }
                                    file.delete();
                                } catch (Throwable th2) {
                                    th = th2;
                                    randomAccessFile = randomAccessFile2;
                                    file = file3;
                                    if (fileLock != null && fileLock.isValid()) {
                                        try {
                                            fileLock.release();
                                        } catch (IOException e6) {
                                            b.a(e6);
                                        }
                                    }
                                    z.a(randomAccessFile);
                                    if (file != null) {
                                        file.delete();
                                    }
                                    throw th;
                                }
                            } catch (Exception e7) {
                                e = e7;
                            } catch (Throwable th3) {
                                th = th3;
                            }
                        } catch (Exception e8) {
                            e = e8;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                    }
                }
            }
        }
    }

    public void a(Context context) {
        this.a = context;
    }

    @Override // com.xiaomi.clientreport.processor.d
    /* renamed from: a */
    public void mo151a(com.xiaomi.clientreport.data.a aVar) {
        if ((aVar instanceof EventClientReport) && this.b != null) {
            EventClientReport eventClientReport = (EventClientReport) aVar;
            String a = a((com.xiaomi.clientreport.data.a) eventClientReport);
            ArrayList<com.xiaomi.clientreport.data.a> arrayList = this.b.get(a);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
            }
            arrayList.add(eventClientReport);
            this.b.put(a, arrayList);
        }
    }

    public void a(List<String> list) {
        bf.a(this.a, list);
    }

    public void a(com.xiaomi.clientreport.data.a[] aVarArr) {
        Throwable th;
        RandomAccessFile randomAccessFile;
        IOException e;
        Exception e2;
        if (aVarArr != null && aVarArr.length > 0) {
            if (aVarArr[0] != null) {
                String b = b(aVarArr[0]);
                if (!TextUtils.isEmpty(b)) {
                    FileLock fileLock = null;
                    try {
                        File file = new File(b + ".lock");
                        z.m1176a(file);
                        randomAccessFile = new RandomAccessFile(file, "rw");
                        try {
                            try {
                                fileLock = randomAccessFile.getChannel().lock();
                                for (com.xiaomi.clientreport.data.a aVar : aVarArr) {
                                    if (aVar != null) {
                                        a(aVarArr, b);
                                    }
                                }
                                if (fileLock != null && fileLock.isValid()) {
                                    try {
                                        fileLock.release();
                                    } catch (IOException e3) {
                                        e = e3;
                                        b.a(e);
                                        z.a(randomAccessFile);
                                    }
                                }
                            } catch (Exception e4) {
                                e2 = e4;
                                b.a(e2);
                                if (fileLock != null && fileLock.isValid()) {
                                    try {
                                        fileLock.release();
                                    } catch (IOException e5) {
                                        e = e5;
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
                                } catch (IOException e6) {
                                    b.a(e6);
                                }
                            }
                            z.a(randomAccessFile);
                            throw th;
                        }
                    } catch (Exception e7) {
                        e2 = e7;
                        randomAccessFile = null;
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
        }
    }

    @Override // com.xiaomi.clientreport.processor.d
    public void b() {
        HashMap<String, ArrayList<com.xiaomi.clientreport.data.a>> hashMap = this.b;
        if (hashMap != null) {
            if (hashMap.size() > 0) {
                for (String str : this.b.keySet()) {
                    ArrayList<com.xiaomi.clientreport.data.a> arrayList = this.b.get(str);
                    if (arrayList != null && arrayList.size() > 0) {
                        com.xiaomi.clientreport.data.a[] aVarArr = new com.xiaomi.clientreport.data.a[arrayList.size()];
                        arrayList.toArray(aVarArr);
                        a(aVarArr);
                    }
                }
            }
            this.b.clear();
        }
    }

    @Override // com.xiaomi.clientreport.processor.IEventProcessor
    public String bytesToString(byte[] bArr) {
        byte[] a;
        if (bArr != null && bArr.length >= 1) {
            if (!com.xiaomi.clientreport.manager.a.a(this.a).a().isEventEncrypted()) {
                return az.a(bArr);
            }
            String a2 = bf.a(this.a);
            if (!TextUtils.isEmpty(a2) && (a = bf.a(a2)) != null && a.length > 0) {
                try {
                    return az.a(Base64.decode(h.a(a, bArr), 2));
                } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
                    b.a(e);
                }
            }
        }
        return null;
    }

    @Override // com.xiaomi.clientreport.processor.IEventProcessor
    public void setEventMap(HashMap<String, ArrayList<com.xiaomi.clientreport.data.a>> hashMap) {
        this.b = hashMap;
    }

    @Override // com.xiaomi.clientreport.processor.IEventProcessor
    public byte[] stringToBytes(String str) {
        byte[] a;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (!com.xiaomi.clientreport.manager.a.a(this.a).a().isEventEncrypted()) {
            return az.m762a(str);
        }
        String a2 = bf.a(this.a);
        byte[] a3 = az.m762a(str);
        if (!TextUtils.isEmpty(a2) && a3 != null && a3.length > 1 && (a = bf.a(a2)) != null) {
            try {
                if (a.length > 1) {
                    return h.b(a, Base64.encode(a3, 2));
                }
            } catch (Exception e) {
                b.a(e);
            }
        }
        return null;
    }
}
