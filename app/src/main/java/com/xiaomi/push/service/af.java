package com.xiaomi.push.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import com.xiaomi.push.az;
import com.xiaomi.push.y;
import com.xiaomi.push.z;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes4.dex */
public class af {
    private static long a;

    /* loaded from: classes4.dex */
    public static class a {
        byte[] a;
        int b;

        public a(byte[] bArr, int i) {
            this.a = bArr;
            this.b = i;
        }
    }

    /* loaded from: classes4.dex */
    public static class b {
        public long a;

        /* renamed from: a  reason: collision with other field name */
        public Bitmap f186a;

        public b(Bitmap bitmap, long j) {
            this.f186a = bitmap;
            this.a = j;
        }
    }

    private static int a(Context context, InputStream inputStream) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);
        if (options.outWidth == -1 || options.outHeight == -1) {
            com.xiaomi.channel.commonutils.logger.b.m149a("decode dimension failed for bitmap.");
            return 1;
        }
        int round = Math.round((context.getResources().getDisplayMetrics().densityDpi / 160.0f) * 48.0f);
        if (options.outWidth <= round || options.outHeight <= round) {
            return 1;
        }
        return Math.min(options.outWidth / round, options.outHeight / round);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0, types: [android.content.Context] */
    /* JADX WARN: Type inference failed for: r3v1, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v6 */
    public static Bitmap a(Context context, String str) {
        Throwable th;
        InputStream inputStream;
        Uri parse;
        IOException e;
        InputStream inputStream2;
        try {
            parse = Uri.parse(str);
            try {
                inputStream = context.getContentResolver().openInputStream(parse);
            } catch (IOException e2) {
                e = e2;
                inputStream2 = null;
                inputStream = null;
            } catch (Throwable th2) {
                th = th2;
                context = 0;
                inputStream = null;
            }
        } catch (Throwable th3) {
            th = th3;
        }
        try {
            int a2 = a((Context) context, inputStream);
            inputStream2 = context.getContentResolver().openInputStream(parse);
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = a2;
                Bitmap decodeStream = BitmapFactory.decodeStream(inputStream2, null, options);
                z.a(inputStream2);
                z.a(inputStream);
                return decodeStream;
            } catch (IOException e3) {
                e = e3;
                com.xiaomi.channel.commonutils.logger.b.a(e);
                z.a(inputStream2);
                z.a(inputStream);
                return null;
            }
        } catch (IOException e4) {
            e = e4;
            inputStream2 = null;
        } catch (Throwable th4) {
            th = th4;
            context = 0;
            z.a((Closeable) context);
            z.a(inputStream);
            throw th;
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 2, insn: 0x00f8: MOVE  (r0 I:??[OBJECT, ARRAY]) = (r2 I:??[OBJECT, ARRAY]), block:B:61:0x00f8
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:41)
        */
    private static com.xiaomi.push.service.af.a a(
    /*  JADX ERROR: JadxRuntimeException in pass: SSATransform
        jadx.core.utils.exceptions.JadxRuntimeException: Not initialized variable reg: 2, insn: 0x00f8: MOVE  (r0 I:??[OBJECT, ARRAY]) = (r2 I:??[OBJECT, ARRAY]), block:B:61:0x00f8
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVarsInBlock(SSATransform.java:171)
        	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:143)
        	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:60)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r10v0 ??
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

    public static b a(Context context, String str, boolean z) {
        Throwable th;
        Exception e;
        a a2;
        ByteArrayInputStream byteArrayInputStream = null;
        b bVar = new b(null, 0L);
        Bitmap b2 = b(context, str);
        try {
            if (b2 != null) {
                bVar.f186a = b2;
                return bVar;
            }
            try {
                a2 = a(str, z);
            } catch (Exception e2) {
                e = e2;
            }
            if (a2 == null) {
                z.a((Closeable) null);
                return bVar;
            }
            bVar.a = a2.b;
            byte[] bArr = a2.a;
            if (bArr != null) {
                if (z) {
                    ByteArrayInputStream byteArrayInputStream2 = new ByteArrayInputStream(bArr);
                    try {
                        int a3 = a(context, byteArrayInputStream2);
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = a3;
                        bVar.f186a = BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
                        byteArrayInputStream = byteArrayInputStream2;
                    } catch (Exception e3) {
                        e = e3;
                        byteArrayInputStream = byteArrayInputStream2;
                        com.xiaomi.channel.commonutils.logger.b.a(e);
                        z.a(byteArrayInputStream);
                        return bVar;
                    } catch (Throwable th2) {
                        th = th2;
                        byteArrayInputStream = byteArrayInputStream2;
                        z.a(byteArrayInputStream);
                        throw th;
                    }
                } else {
                    bVar.f186a = BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
                }
            }
            a(context, a2.a, str);
            z.a(byteArrayInputStream);
            return bVar;
        } catch (Throwable th3) {
            th = th3;
        }
    }

    private static void a(Context context) {
        File file = new File(context.getCacheDir().getPath() + File.separator + "mipush_icon");
        if (file.exists()) {
            if (a == 0) {
                a = y.a(file);
            }
            if (a > 15728640) {
                try {
                    File[] listFiles = file.listFiles();
                    for (int i = 0; i < listFiles.length; i++) {
                        if (!listFiles[i].isDirectory() && Math.abs(System.currentTimeMillis() - listFiles[i].lastModified()) > 1209600) {
                            listFiles[i].delete();
                        }
                    }
                } catch (Exception e) {
                    com.xiaomi.channel.commonutils.logger.b.a(e);
                }
                a = 0L;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:29:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:42:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.io.File] */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r0v9, types: [java.io.OutputStream, java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r6v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r6v1, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v12 */
    /* JADX WARN: Type inference failed for: r6v13 */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void a(android.content.Context r4, byte[] r5, java.lang.String r6) {
        /*
            if (r5 != 0) goto L_0x0008
            java.lang.String r4 = "cannot save small icon cause bitmap is null"
            com.xiaomi.channel.commonutils.logger.b.m149a(r4)
            return
        L_0x0008:
            a(r4)
            java.io.File r0 = new java.io.File
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.io.File r2 = r4.getCacheDir()
            java.lang.String r2 = r2.getPath()
            r1.append(r2)
            java.lang.String r2 = java.io.File.separator
            r1.append(r2)
            java.lang.String r2 = "mipush_icon"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            boolean r1 = r0.exists()
            if (r1 != 0) goto L_0x0037
            r0.mkdirs()
        L_0x0037:
            java.io.File r1 = new java.io.File
            java.lang.String r6 = com.xiaomi.push.az.a(r6)
            r1.<init>(r0, r6)
            r6 = 0
            boolean r0 = r1.exists()     // Catch: Exception -> 0x0069, all -> 0x0066
            if (r0 != 0) goto L_0x004a
            r1.createNewFile()     // Catch: Exception -> 0x0069, all -> 0x0066
        L_0x004a:
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch: Exception -> 0x0069, all -> 0x0066
            r0.<init>(r1)     // Catch: Exception -> 0x0069, all -> 0x0066
            java.io.BufferedOutputStream r2 = new java.io.BufferedOutputStream     // Catch: Exception -> 0x0064, all -> 0x00ab
            r2.<init>(r0)     // Catch: Exception -> 0x0064, all -> 0x00ab
            r2.write(r5)     // Catch: Exception -> 0x0061, all -> 0x005e
            r2.flush()     // Catch: Exception -> 0x0061, all -> 0x005e
            com.xiaomi.push.z.a(r2)
            goto L_0x0071
        L_0x005e:
            r4 = move-exception
            r6 = r2
            goto L_0x00ac
        L_0x0061:
            r5 = move-exception
            r6 = r2
            goto L_0x006b
        L_0x0064:
            r5 = move-exception
            goto L_0x006b
        L_0x0066:
            r4 = move-exception
            r0 = r6
            goto L_0x00ac
        L_0x0069:
            r5 = move-exception
            r0 = r6
        L_0x006b:
            com.xiaomi.channel.commonutils.logger.b.a(r5)     // Catch: all -> 0x00ab
            com.xiaomi.push.z.a(r6)
        L_0x0071:
            com.xiaomi.push.z.a(r0)
            long r5 = com.xiaomi.push.service.af.a
            r2 = 0
            int r5 = (r5 > r2 ? 1 : (r5 == r2 ? 0 : -1))
            if (r5 != 0) goto L_0x00aa
            java.io.File r5 = new java.io.File
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.io.File r4 = r4.getCacheDir()
            java.lang.String r4 = r4.getPath()
            r6.append(r4)
            java.lang.String r4 = java.io.File.separator
            r6.append(r4)
            java.lang.String r4 = "mipush_icon"
            r6.append(r4)
            java.lang.String r4 = r6.toString()
            r5.<init>(r4)
            long r4 = com.xiaomi.push.y.a(r5)
            long r0 = r1.length()
            long r4 = r4 + r0
            com.xiaomi.push.service.af.a = r4
        L_0x00aa:
            return
        L_0x00ab:
            r4 = move-exception
        L_0x00ac:
            com.xiaomi.push.z.a(r6)
            com.xiaomi.push.z.a(r0)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.af.a(android.content.Context, byte[], java.lang.String):void");
    }

    private static Bitmap b(Context context, String str) {
        Throwable th;
        Exception e;
        FileInputStream fileInputStream;
        File file = new File(context.getCacheDir().getPath() + File.separator + "mipush_icon", az.a(str));
        FileInputStream fileInputStream2 = null;
        Bitmap bitmap = null;
        fileInputStream2 = null;
        if (!file.exists()) {
            return null;
        }
        try {
            try {
                fileInputStream = new FileInputStream(file);
            } catch (Exception e2) {
                e = e2;
                bitmap = null;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            bitmap = BitmapFactory.decodeStream(fileInputStream);
            file.setLastModified(System.currentTimeMillis());
            z.a(fileInputStream);
            return bitmap;
        } catch (Exception e3) {
            e = e3;
            fileInputStream2 = fileInputStream;
            com.xiaomi.channel.commonutils.logger.b.a(e);
            z.a(fileInputStream2);
            return bitmap;
        } catch (Throwable th3) {
            th = th3;
            fileInputStream2 = fileInputStream;
            z.a(fileInputStream2);
            throw th;
        }
    }
}
