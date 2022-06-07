package com.xiaomi.push;

import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.push.ef;
import com.xiaomi.push.service.al;
import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.zip.Adler32;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class es {
    private ByteBuffer a = ByteBuffer.allocate(2048);
    private ByteBuffer b = ByteBuffer.allocate(4);
    private Adler32 c = new Adler32();
    private fj d = new fj();
    private InputStream e;
    private fl f;
    private volatile boolean g;
    private byte[] h;

    /* JADX INFO: Access modifiers changed from: package-private */
    public es(InputStream inputStream, fl flVar) {
        this.e = new BufferedInputStream(inputStream);
        this.f = flVar;
    }

    private void a(ByteBuffer byteBuffer, int i) {
        int position = byteBuffer.position();
        do {
            int read = this.e.read(byteBuffer.array(), position, i);
            if (read != -1) {
                i -= read;
                position += read;
            } else {
                throw new EOFException();
            }
        } while (i > 0);
        byteBuffer.position(position);
    }

    private void d() {
        StringBuilder sb;
        String str;
        boolean z = false;
        this.g = false;
        fg c = c();
        if ("CONN".equals(c.m907a())) {
            ef.f a = ef.f.a(c.m910a());
            if (a.mo888a()) {
                this.f.a(a.mo888a());
                z = true;
            }
            if (a.c()) {
                ef.b a2 = a.mo888a();
                fg fgVar = new fg();
                fgVar.a("SYNC", "CONF");
                fgVar.a(a2.mo888a(), (String) null);
                this.f.a(fgVar);
            }
            b.m149a("[Slim] CONN: host = " + a.mo890b());
        }
        if (z) {
            this.h = this.f.h();
            while (!this.g) {
                fg c2 = c();
                this.f.c();
                switch (c2.m908a()) {
                    case 1:
                        this.f.a(c2);
                        break;
                    case 2:
                        if ("SECMSG".equals(c2.m907a()) && ((c2.a() == 2 || c2.a() == 3) && TextUtils.isEmpty(c2.m912b()))) {
                            try {
                                this.f.b(this.d.a(c2.m911a(al.a().a(Integer.valueOf(c2.a()).toString(), c2.g()).h), this.f));
                            } catch (Exception e) {
                                e = e;
                                sb = new StringBuilder();
                                sb.append("[Slim] Parse packet from Blob chid=");
                                sb.append(c2.a());
                                sb.append("; Id=");
                                sb.append(c2.e());
                                sb.append(" failure:");
                                sb.append(e.getMessage());
                                str = sb.toString();
                                b.m149a(str);
                            }
                        }
                        this.f.a(c2);
                        break;
                    case 3:
                        try {
                            this.f.b(this.d.a(c2.m910a(), this.f));
                        } catch (Exception e2) {
                            e = e2;
                            sb = new StringBuilder();
                            sb.append("[Slim] Parse packet from Blob chid=");
                            sb.append(c2.a());
                            sb.append("; Id=");
                            sb.append(c2.e());
                            sb.append(" failure:");
                            sb.append(e.getMessage());
                            str = sb.toString();
                            b.m149a(str);
                        }
                    default:
                        str = "[Slim] unknow blob type " + ((int) c2.m908a());
                        b.m149a(str);
                        break;
                }
            }
            return;
        }
        b.m149a("[Slim] Invalid CONN");
        throw new IOException("Invalid Connection");
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0068, code lost:
        if (r0 < 2048) goto L_0x003f;
     */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00b5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.nio.ByteBuffer e() {
        /*
            r8 = this;
            java.nio.ByteBuffer r0 = r8.a
            r0.clear()
            java.nio.ByteBuffer r0 = r8.a
            r1 = 8
            r8.a(r0, r1)
            java.nio.ByteBuffer r0 = r8.a
            r1 = 0
            short r0 = r0.getShort(r1)
            java.nio.ByteBuffer r2 = r8.a
            r3 = 2
            short r2 = r2.getShort(r3)
            r3 = -15618(0xffffffffffffc2fe, float:NaN)
            if (r0 != r3) goto L_0x00e8
            r0 = 5
            if (r2 != r0) goto L_0x00e8
            java.nio.ByteBuffer r0 = r8.a
            r2 = 4
            int r0 = r0.getInt(r2)
            java.nio.ByteBuffer r3 = r8.a
            int r3 = r3.position()
            r4 = 32768(0x8000, float:4.5918E-41)
            if (r0 > r4) goto L_0x00e0
            int r4 = r0 + 4
            java.nio.ByteBuffer r5 = r8.a
            int r5 = r5.remaining()
            if (r4 <= r5) goto L_0x005c
            int r4 = r0 + 2048
        L_0x003f:
            java.nio.ByteBuffer r4 = java.nio.ByteBuffer.allocate(r4)
            java.nio.ByteBuffer r5 = r8.a
            byte[] r5 = r5.array()
            java.nio.ByteBuffer r6 = r8.a
            int r6 = r6.arrayOffset()
            java.nio.ByteBuffer r7 = r8.a
            int r7 = r7.position()
            int r6 = r6 + r7
            r4.put(r5, r1, r6)
            r8.a = r4
            goto L_0x006b
        L_0x005c:
            java.nio.ByteBuffer r4 = r8.a
            int r4 = r4.capacity()
            r5 = 4096(0x1000, float:5.74E-42)
            if (r4 <= r5) goto L_0x006b
            r4 = 2048(0x800, float:2.87E-42)
            if (r0 >= r4) goto L_0x006b
            goto L_0x003f
        L_0x006b:
            java.nio.ByteBuffer r4 = r8.a
            r8.a(r4, r0)
            java.nio.ByteBuffer r4 = r8.b
            r4.clear()
            java.nio.ByteBuffer r4 = r8.b
            r8.a(r4, r2)
            java.nio.ByteBuffer r2 = r8.b
            r2.position(r1)
            java.nio.ByteBuffer r2 = r8.b
            int r2 = r2.getInt()
            java.util.zip.Adler32 r4 = r8.c
            r4.reset()
            java.util.zip.Adler32 r4 = r8.c
            java.nio.ByteBuffer r5 = r8.a
            byte[] r5 = r5.array()
            java.nio.ByteBuffer r6 = r8.a
            int r6 = r6.position()
            r4.update(r5, r1, r6)
            java.util.zip.Adler32 r1 = r8.c
            long r4 = r1.getValue()
            int r1 = (int) r4
            if (r2 != r1) goto L_0x00b5
            byte[] r1 = r8.h
            if (r1 == 0) goto L_0x00b2
            java.nio.ByteBuffer r2 = r8.a
            byte[] r2 = r2.array()
            r4 = 1
            com.xiaomi.push.service.au.a(r1, r2, r4, r3, r0)
        L_0x00b2:
            java.nio.ByteBuffer r0 = r8.a
            return r0
        L_0x00b5:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "CRC = "
            r0.append(r1)
            java.util.zip.Adler32 r1 = r8.c
            long r3 = r1.getValue()
            int r1 = (int) r3
            r0.append(r1)
            java.lang.String r1 = " and "
            r0.append(r1)
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            com.xiaomi.channel.commonutils.logger.b.m149a(r0)
            java.io.IOException r0 = new java.io.IOException
            java.lang.String r1 = "Corrupted Blob bad CRC"
            r0.<init>(r1)
            throw r0
        L_0x00e0:
            java.io.IOException r0 = new java.io.IOException
            java.lang.String r1 = "Blob size too large"
            r0.<init>(r1)
            throw r0
        L_0x00e8:
            java.io.IOException r0 = new java.io.IOException
            java.lang.String r1 = "Malformed Input"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.es.e():java.nio.ByteBuffer");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a() {
        try {
            d();
        } catch (IOException e) {
            if (!this.g) {
                throw e;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b() {
        this.g = true;
    }

    fg c() {
        int i;
        IOException e;
        ByteBuffer e2;
        try {
            e2 = e();
            i = e2.position();
        } catch (IOException e3) {
            e = e3;
            i = 0;
        }
        try {
            e2.flip();
            e2.position(8);
            fg fkVar = i == 8 ? new fk() : fg.b(e2.slice());
            b.c("[Slim] Read {cmd=" + fkVar.m907a() + ";chid=" + fkVar.a() + ";len=" + i + "}");
            return fkVar;
        } catch (IOException e4) {
            e = e4;
            if (i == 0) {
                i = this.a.position();
            }
            StringBuilder sb = new StringBuilder();
            sb.append("[Slim] read Blob [");
            byte[] array = this.a.array();
            if (i > 128) {
                i = 128;
            }
            sb.append(ag.a(array, 0, i));
            sb.append("] Err:");
            sb.append(e.getMessage());
            b.m149a(sb.toString());
            throw e;
        }
    }
}
