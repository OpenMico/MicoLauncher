package com.xiaomi.onetrack.b;

import android.os.HandlerThread;
import android.text.TextUtils;
import com.xiaomi.onetrack.c.c;
import com.xiaomi.onetrack.c.f;
import com.xiaomi.onetrack.f.b;
import com.xiaomi.onetrack.util.m;
import com.xiaomi.onetrack.util.p;
import com.xiaomi.onetrack.util.x;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class n {
    private static final String a = "UploaderEngine";
    private static final String b = "code";
    private static final String c = "UTF-8";
    private k d;

    private n() {
        b();
    }

    /* loaded from: classes4.dex */
    public static class a {
        private static final n a = new n();

        private a() {
        }
    }

    public static n a() {
        return a.a;
    }

    public synchronized void a(int i, boolean z) {
        if (this.d != null) {
            this.d.a(i, z);
        } else {
            p.b(a, "*** impossible, upload timer should not be null");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x007c, code lost:
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean a(int r8) {
        /*
            r7 = this;
            java.lang.String r0 = "UploaderEngine"
            java.lang.String r1 = "即将读取数据库并上传数据"
            com.xiaomi.onetrack.util.p.a(r0, r1)
        L_0x0007:
            com.xiaomi.onetrack.b.b r0 = com.xiaomi.onetrack.b.b.a()
            com.xiaomi.onetrack.b.f r0 = r0.a(r8)
            r1 = 1
            if (r0 != 0) goto L_0x0029
            java.lang.String r0 = "UploaderEngine"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "满足条件的记录为空，即将返回, priority="
            r2.append(r3)
            r2.append(r8)
            java.lang.String r8 = r2.toString()
            com.xiaomi.onetrack.util.p.a(r0, r8)
            return r1
        L_0x0029:
            java.util.ArrayList<java.lang.Long> r2 = r0.c
            org.json.JSONArray r3 = r0.a
            boolean r3 = r7.a(r3)
            java.lang.String r4 = "UploaderEngine"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "upload success:"
            r5.append(r6)
            r5.append(r3)
            java.lang.String r5 = r5.toString()
            com.xiaomi.onetrack.util.p.a(r4, r5)
            if (r3 != 0) goto L_0x004b
            r8 = 0
            return r8
        L_0x004b:
            com.xiaomi.onetrack.b.b r3 = com.xiaomi.onetrack.b.b.a()
            int r2 = r3.a(r2)
            if (r2 != 0) goto L_0x0062
            java.lang.String r8 = "UploaderEngine"
            java.lang.String r0 = "delete DB failed!"
            java.lang.Throwable r2 = new java.lang.Throwable
            r2.<init>()
            com.xiaomi.onetrack.util.p.b(r8, r0, r2)
            goto L_0x007c
        L_0x0062:
            boolean r0 = r0.d
            if (r0 == 0) goto L_0x0007
            java.lang.String r0 = "UploaderEngine"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "No more records for prio="
            r2.append(r3)
            r2.append(r8)
            java.lang.String r8 = r2.toString()
            com.xiaomi.onetrack.util.p.a(r0, r8)
        L_0x007c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.onetrack.b.n.a(int):boolean");
    }

    private boolean a(JSONArray jSONArray) {
        try {
            String b2 = x.a().b();
            String jSONArray2 = jSONArray.toString();
            p.a(a, " payload:" + jSONArray2);
            byte[] a2 = a(a(jSONArray2));
            p.a(a, "before zip and encrypt, len=" + jSONArray2.length() + ", after=" + a2.length);
            String a3 = b.a(b2, a2);
            StringBuilder sb = new StringBuilder();
            sb.append("sendDataToServer response: ");
            sb.append(a3);
            p.a(a, sb.toString());
            if (TextUtils.isEmpty(a3)) {
                return false;
            }
            return b(a3);
        } catch (Exception e) {
            p.b(a, "Exception while uploading ", e);
            return false;
        }
    }

    private void b() {
        HandlerThread handlerThread = new HandlerThread("onetrack_uploader_worker");
        handlerThread.start();
        this.d = new k(handlerThread.getLooper());
    }

    private static byte[] a(String str) {
        Throwable th;
        GZIPOutputStream gZIPOutputStream;
        ByteArrayOutputStream byteArrayOutputStream;
        byte[] bArr;
        Exception e;
        try {
            bArr = null;
            try {
                byteArrayOutputStream = new ByteArrayOutputStream(str.getBytes("UTF-8").length);
            } catch (Exception e2) {
                e = e2;
                byteArrayOutputStream = null;
                gZIPOutputStream = null;
            } catch (Throwable th2) {
                th = th2;
                byteArrayOutputStream = null;
                gZIPOutputStream = null;
            }
        } catch (Throwable th3) {
            th = th3;
        }
        try {
            gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            try {
                gZIPOutputStream.write(str.getBytes("UTF-8"));
                gZIPOutputStream.finish();
                bArr = byteArrayOutputStream.toByteArray();
            } catch (Exception e3) {
                e = e3;
                p.b(a, " zipData failed! " + e.toString());
                m.a((OutputStream) byteArrayOutputStream);
                m.a((OutputStream) gZIPOutputStream);
                return bArr;
            }
        } catch (Exception e4) {
            e = e4;
            gZIPOutputStream = null;
        } catch (Throwable th4) {
            th = th4;
            gZIPOutputStream = null;
            m.a((OutputStream) byteArrayOutputStream);
            m.a((OutputStream) gZIPOutputStream);
            throw th;
        }
        m.a((OutputStream) byteArrayOutputStream);
        m.a((OutputStream) gZIPOutputStream);
        return bArr;
    }

    private byte[] a(byte[] bArr) {
        if (bArr != null) {
            return com.xiaomi.onetrack.c.a.a(bArr, c.a(f.a().b()[0]));
        }
        p.b(a, "content is null");
        return null;
    }

    private boolean b(String str) {
        boolean z = false;
        try {
            int optInt = new JSONObject(str).optInt("code");
            if (optInt == 0) {
                p.a(a, "成功发送数据到服务端");
                z = true;
            } else if (optInt == -3) {
                p.b(a, "signature expired, will update");
                f.a().c();
            } else {
                p.b(a, "Error: status code=" + optInt);
            }
        } catch (Exception e) {
            p.b(a, "parseUploadingResult exception ", e);
        }
        return z;
    }
}
