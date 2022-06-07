package com.umeng.commonsdk.stateless;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import com.umeng.commonsdk.internal.crash.UMCrashManager;
import com.umeng.commonsdk.statistics.common.ULog;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.zip.Deflater;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: UMSLUtils.java */
/* loaded from: classes2.dex */
public class d {
    public static int a;
    private static final byte[] b = {10, 1, 11, 5, 4, 15, 7, 9, 23, 3, 1, 6, 8, 12, 13, 91};
    private static Object c = new Object();

    public static boolean a(long j, long j2) {
        return j > j2;
    }

    public static boolean a(File file) {
        if (file == null) {
            return false;
        }
        if (file.isDirectory()) {
            for (String str : file.list()) {
                if (!a(new File(file, str))) {
                    return false;
                }
            }
        }
        return file.delete();
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0065, code lost:
        if (r1 == null) goto L_0x0078;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0067, code lost:
        r1.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x006f, code lost:
        if (r1 == null) goto L_0x0078;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int a(android.content.Context r6, java.lang.String r7, java.lang.String r8, byte[] r9) {
        /*
            r0 = 101(0x65, float:1.42E-43)
            if (r6 == 0) goto L_0x0078
            r1 = 0
            java.lang.Object r2 = com.umeng.commonsdk.stateless.d.c     // Catch: IOException -> 0x006b, Throwable -> 0x0061, all -> 0x005f
            monitor-enter(r2)     // Catch: IOException -> 0x006b, Throwable -> 0x0061, all -> 0x005f
            java.io.File r3 = new java.io.File     // Catch: all -> 0x005c
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: all -> 0x005c
            r4.<init>()     // Catch: all -> 0x005c
            java.io.File r5 = r6.getFilesDir()     // Catch: all -> 0x005c
            r4.append(r5)     // Catch: all -> 0x005c
            java.lang.String r5 = java.io.File.separator     // Catch: all -> 0x005c
            r4.append(r5)     // Catch: all -> 0x005c
            r4.append(r7)     // Catch: all -> 0x005c
            java.lang.String r7 = r4.toString()     // Catch: all -> 0x005c
            r3.<init>(r7)     // Catch: all -> 0x005c
            boolean r7 = r3.isDirectory()     // Catch: all -> 0x005c
            if (r7 != 0) goto L_0x002e
            r3.mkdir()     // Catch: all -> 0x005c
        L_0x002e:
            java.io.File r7 = new java.io.File     // Catch: all -> 0x005c
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: all -> 0x005c
            r4.<init>()     // Catch: all -> 0x005c
            java.lang.String r3 = r3.getPath()     // Catch: all -> 0x005c
            r4.append(r3)     // Catch: all -> 0x005c
            java.lang.String r3 = java.io.File.separator     // Catch: all -> 0x005c
            r4.append(r3)     // Catch: all -> 0x005c
            r4.append(r8)     // Catch: all -> 0x005c
            java.lang.String r8 = r4.toString()     // Catch: all -> 0x005c
            r7.<init>(r8)     // Catch: all -> 0x005c
            java.io.FileOutputStream r8 = new java.io.FileOutputStream     // Catch: all -> 0x005c
            r8.<init>(r7)     // Catch: all -> 0x005c
            r8.write(r9)     // Catch: all -> 0x0059
            r8.close()     // Catch: all -> 0x0059
            r0 = 0
            monitor-exit(r2)     // Catch: all -> 0x005c
            goto L_0x0078
        L_0x0059:
            r7 = move-exception
            r1 = r8
            goto L_0x005d
        L_0x005c:
            r7 = move-exception
        L_0x005d:
            monitor-exit(r2)     // Catch: all -> 0x005c
            throw r7     // Catch: IOException -> 0x006b, Throwable -> 0x0061, all -> 0x005f
        L_0x005f:
            r6 = move-exception
            goto L_0x0072
        L_0x0061:
            r7 = move-exception
            com.umeng.commonsdk.internal.crash.UMCrashManager.reportCrash(r6, r7)     // Catch: all -> 0x005f
            if (r1 == 0) goto L_0x0078
        L_0x0067:
            r1.close()     // Catch: Throwable -> 0x0078
            goto L_0x0078
        L_0x006b:
            r7 = move-exception
            com.umeng.commonsdk.internal.crash.UMCrashManager.reportCrash(r6, r7)     // Catch: all -> 0x005f
            if (r1 == 0) goto L_0x0078
            goto L_0x0067
        L_0x0072:
            if (r1 == 0) goto L_0x0077
            r1.close()     // Catch: Throwable -> 0x0077
        L_0x0077:
            throw r6
        L_0x0078:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.stateless.d.a(android.content.Context, java.lang.String, java.lang.String, byte[]):int");
    }

    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 2, insn: 0x010a: IF  (r2 I:??[int, boolean, OBJECT, ARRAY, byte, short, char]) == (0 ??[int, boolean, OBJECT, ARRAY, byte, short, char])  -> B:43:0x010f, block:B:41:0x010a
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
        */
    public static boolean b(
    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 2, insn: 0x010a: IF  (r2 I:??[int, boolean, OBJECT, ARRAY, byte, short, char]) == (0 ??[int, boolean, OBJECT, ARRAY, byte, short, char])  -> B:43:0x010f, block:B:41:0x010a
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r8v0 ??
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v5 */
    public static byte[] a(String str) throws IOException {
        Throwable th;
        IOException e;
        byte[] bArr;
        synchronized (c) {
            try {
                str = 0;
            } catch (Throwable th2) {
                th = th2;
            }
            try {
                FileChannel channel = new RandomAccessFile(str, "r").getChannel();
                try {
                    MappedByteBuffer load = channel.map(FileChannel.MapMode.READ_ONLY, 0L, channel.size()).load();
                    bArr = new byte[(int) channel.size()];
                    if (load.remaining() > 0) {
                        load.get(bArr, 0, load.remaining());
                    }
                    if (channel != null) {
                        try {
                            channel.close();
                        } catch (Throwable unused) {
                        }
                    }
                } catch (IOException e2) {
                    e = e2;
                    ULog.i("walle", "[stateless] write envelope, e is " + e.getMessage());
                    throw e;
                }
            } catch (IOException e3) {
                e = e3;
            } catch (Throwable th3) {
                th = th3;
                if (str != 0) {
                    try {
                        str.close();
                    } catch (Throwable unused2) {
                    }
                }
                throw th;
            }
        }
        return bArr;
    }

    public static File a(Context context) {
        Throwable th;
        File[] listFiles;
        File[] listFiles2;
        File file = null;
        try {
            try {
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            UMCrashManager.reportCrash(context, th3);
        }
        synchronized (c) {
            ULog.i("walle", "get last envelope begin, thread is " + Thread.currentThread());
            if (!(context == null || context.getApplicationContext() == null)) {
                String str = context.getApplicationContext().getFilesDir() + File.separator + a.e;
                if (!TextUtils.isEmpty(str)) {
                    File file2 = new File(str);
                    if (file2.isDirectory() && (listFiles = file2.listFiles()) != null && listFiles.length > 0) {
                        File file3 = null;
                        for (File file4 : listFiles) {
                            try {
                                if (file4 != null && file4.isDirectory() && (listFiles2 = file4.listFiles()) != null && listFiles2.length > 0) {
                                    Arrays.sort(listFiles2, new Comparator<File>() { // from class: com.umeng.commonsdk.stateless.d.1
                                        /* renamed from: a */
                                        public int compare(File file5, File file6) {
                                            int i = ((file5.lastModified() - file6.lastModified()) > 0L ? 1 : ((file5.lastModified() - file6.lastModified()) == 0L ? 0 : -1));
                                            if (i > 0) {
                                                return 1;
                                            }
                                            return i == 0 ? 0 : -1;
                                        }
                                    });
                                    File file5 = listFiles2[0];
                                    if (file5 != null && (file3 == null || file3.lastModified() > file5.lastModified())) {
                                        file3 = file5;
                                    }
                                }
                            } catch (Throwable th4) {
                                th = th4;
                                file = file3;
                                throw th;
                            }
                        }
                        file = file3;
                    }
                }
            }
            ULog.i("walle", "get last envelope end, thread is " + Thread.currentThread());
            return file;
        }
    }

    public static File b(Context context) {
        if (context == null) {
            return null;
        }
        try {
            synchronized (c) {
                String str = context.getApplicationContext().getFilesDir() + File.separator + a.f;
                if (TextUtils.isEmpty(str)) {
                    return null;
                }
                File file = new File(str);
                synchronized (c) {
                    File[] listFiles = file.listFiles();
                    if (!(listFiles == null || listFiles.length == 0)) {
                        Arrays.sort(listFiles, new Comparator<File>() { // from class: com.umeng.commonsdk.stateless.d.2
                            /* renamed from: a */
                            public int compare(File file2, File file3) {
                                int i = ((file2.lastModified() - file3.lastModified()) > 0L ? 1 : ((file2.lastModified() - file3.lastModified()) == 0L ? 0 : -1));
                                if (i > 0) {
                                    return 1;
                                }
                                return i == 0 ? 0 : -1;
                            }
                        });
                        return listFiles[0];
                    }
                    return null;
                }
            }
        } catch (Throwable th) {
            UMCrashManager.reportCrash(context, th);
            return null;
        }
    }

    public static String a(Context context, boolean z) {
        String str = null;
        if (context == null) {
            return null;
        }
        try {
            if (z) {
                str = context.getApplicationContext().getFilesDir() + File.separator + a.e;
            } else {
                str = context.getApplicationContext().getFilesDir() + File.separator + a.f;
            }
        } catch (Throwable unused) {
        }
        return str;
    }

    public static File[] c(Context context) {
        if (context == null) {
            return null;
        }
        try {
            synchronized (c) {
                String str = context.getApplicationContext().getFilesDir() + File.separator + a.f;
                if (TextUtils.isEmpty(str)) {
                    return null;
                }
                File file = new File(str);
                synchronized (c) {
                    File[] listFiles = file.listFiles();
                    if (!(listFiles == null || listFiles.length == 0)) {
                        Arrays.sort(listFiles, new Comparator<File>() { // from class: com.umeng.commonsdk.stateless.d.3
                            /* renamed from: a */
                            public int compare(File file2, File file3) {
                                int i = ((file2.lastModified() - file3.lastModified()) > 0L ? 1 : ((file2.lastModified() - file3.lastModified()) == 0L ? 0 : -1));
                                if (i > 0) {
                                    return 1;
                                }
                                return i == 0 ? 0 : -1;
                            }
                        });
                        return listFiles;
                    }
                    return null;
                }
            }
        } catch (Throwable th) {
            UMCrashManager.reportCrash(context, th);
            return null;
        }
    }

    public static void a(Context context, String str, int i) {
        try {
            if (str == null) {
                ULog.i("AmapLBS", "[lbs-build] fileDir not exist, thread is " + Thread.currentThread());
                return;
            }
            File file = new File(str);
            if (!file.isDirectory()) {
                ULog.i("AmapLBS", "[lbs-build] fileDir not exist, thread is " + Thread.currentThread());
                return;
            }
            synchronized (c) {
                File[] listFiles = file.listFiles();
                ULog.i("AmapLBS", "[lbs-build] delete file begin " + listFiles.length + ", thread is " + Thread.currentThread());
                if (listFiles == null || listFiles.length < i) {
                    ULog.i("AmapLBS", "[lbs-build] file size < max");
                } else {
                    ULog.i("AmapLBS", "[lbs-build] file size >= max");
                    ArrayList arrayList = new ArrayList();
                    for (File file2 : listFiles) {
                        if (file2 != null) {
                            arrayList.add(file2);
                        }
                    }
                    if (arrayList.size() >= i) {
                        Collections.sort(arrayList, new Comparator<File>() { // from class: com.umeng.commonsdk.stateless.d.4
                            /* renamed from: a */
                            public int compare(File file3, File file4) {
                                if (file3 == null || file4 == null || file3.lastModified() >= file4.lastModified()) {
                                    return (file3 == null || file4 == null || file3.lastModified() != file4.lastModified()) ? 1 : 0;
                                }
                                return -1;
                            }
                        });
                        if (ULog.DEBUG) {
                            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                                ULog.i("AmapLBS", "[lbs-build] overrun native file is " + ((File) arrayList.get(i2)).getPath());
                            }
                        }
                        for (int i3 = 0; i3 <= arrayList.size() - i; i3++) {
                            if (arrayList.get(i3) != null) {
                                ULog.i("AmapLBS", "[lbs-build] overrun remove file is " + ((File) arrayList.get(i3)).getPath());
                                try {
                                    ((File) arrayList.get(i3)).delete();
                                    arrayList.remove(i3);
                                } catch (Exception unused) {
                                }
                            }
                        }
                    }
                }
                ULog.i("AmapLBS", "[lbs-build] delete file end " + listFiles.length + ", thread is " + Thread.currentThread());
            }
        } catch (Throwable th) {
            UMCrashManager.reportCrash(context, th);
        }
    }

    public static void a(Context context, String str, final String str2, int i) {
        if (str != null) {
            try {
                File file = new File(str);
                if (file.isDirectory()) {
                    synchronized (c) {
                        File[] listFiles = file.listFiles(new FilenameFilter() { // from class: com.umeng.commonsdk.stateless.d.5
                            @Override // java.io.FilenameFilter
                            public boolean accept(File file2, String str3) {
                                return str3.startsWith(str2);
                            }
                        });
                        if (listFiles == null || listFiles.length < i) {
                            ULog.i("AmapLBS", "[lbs-build] file size < max");
                        } else {
                            ULog.i("AmapLBS", "[lbs-build] file size >= max");
                            ArrayList arrayList = new ArrayList();
                            for (File file2 : listFiles) {
                                if (file2 != null) {
                                    arrayList.add(file2);
                                }
                            }
                            if (arrayList.size() >= i) {
                                Collections.sort(arrayList, new Comparator<File>() { // from class: com.umeng.commonsdk.stateless.d.6
                                    /* renamed from: a */
                                    public int compare(File file3, File file4) {
                                        if (file3 == null || file4 == null || file3.lastModified() >= file4.lastModified()) {
                                            return (file3 == null || file4 == null || file3.lastModified() != file4.lastModified()) ? 1 : 0;
                                        }
                                        return -1;
                                    }
                                });
                                if (ULog.DEBUG) {
                                    for (int i2 = 0; i2 < arrayList.size(); i2++) {
                                        ULog.i("AmapLBS", "[lbs-build] overrun native file is " + ((File) arrayList.get(i2)).getPath());
                                    }
                                }
                                for (int i3 = 0; i3 <= arrayList.size() - i; i3++) {
                                    if (arrayList.get(i3) != null) {
                                        ULog.i("AmapLBS", "[lbs-build] overrun remove file is " + ((File) arrayList.get(i3)).getPath());
                                        try {
                                            ((File) arrayList.get(i3)).delete();
                                            arrayList.remove(i3);
                                        } catch (Exception unused) {
                                        }
                                    }
                                }
                            }
                        }
                        ULog.i("AmapLBS", "[lbs-build] delete file end " + listFiles.length + ", thread is " + Thread.currentThread());
                    }
                }
            } catch (Throwable th) {
                UMCrashManager.reportCrash(context, th);
            }
        }
    }

    public static byte[] a(byte[] bArr) throws IOException {
        Throwable th;
        ByteArrayOutputStream byteArrayOutputStream;
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        Deflater deflater = new Deflater();
        deflater.setInput(bArr);
        deflater.finish();
        byte[] bArr2 = new byte[8192];
        a = 0;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            while (!deflater.finished()) {
                try {
                    int deflate = deflater.deflate(bArr2);
                    a += deflate;
                    byteArrayOutputStream.write(bArr2, 0, deflate);
                } catch (Throwable th2) {
                    th = th2;
                    if (byteArrayOutputStream != null) {
                        byteArrayOutputStream.close();
                    }
                    throw th;
                }
            }
            deflater.end();
            byteArrayOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (Throwable th3) {
            th = th3;
            byteArrayOutputStream = null;
        }
    }

    public static byte[] a(byte[] bArr, byte[] bArr2) throws Exception {
        Cipher instance = Cipher.getInstance("AES/CBC/PKCS7Padding");
        instance.init(1, new SecretKeySpec(bArr2, "AES"), new IvParameterSpec(b));
        return instance.doFinal(bArr);
    }

    public static byte[] b(byte[] bArr) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.reset();
            instance.update(bArr);
            return instance.digest();
        } catch (Exception unused) {
            return null;
        }
    }

    public static String c(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < bArr.length; i++) {
            stringBuffer.append(String.format("%02X", Byte.valueOf(bArr[i])));
        }
        return stringBuffer.toString().toLowerCase(Locale.US);
    }

    public static String b(String str) {
        try {
            return Base64.encodeToString(str.getBytes(), 0);
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String c(String str) {
        try {
            return new String(Base64.decode(str, 0));
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String d(String str) {
        int lastIndexOf;
        int lastIndexOf2;
        if (!TextUtils.isEmpty(str) && str.indexOf("envelope") < 0 && (lastIndexOf = str.lastIndexOf("_")) >= 0 && (lastIndexOf2 = str.lastIndexOf(".")) >= 0) {
            return str.substring(lastIndexOf + 1, lastIndexOf2);
        }
        return "";
    }
}
