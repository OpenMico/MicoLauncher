package com.xiaomi.push;

import android.text.TextUtils;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.push.ef;
import com.xiaomi.push.service.au;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/* loaded from: classes4.dex */
public class fg {
    private static String b = gp.a(5) + Constants.ACCEPT_TIME_SEPARATOR_SERVER;
    private static long c = 0;
    private static final byte[] f = new byte[0];
    String a;
    private ef.a d;
    private short e;
    private byte[] g;

    public fg() {
        this.e = (short) 2;
        this.g = f;
        this.a = null;
        this.d = new ef.a();
    }

    fg(ef.a aVar, short s, byte[] bArr) {
        this.e = (short) 2;
        this.g = f;
        this.a = null;
        this.d = aVar;
        this.e = s;
        this.g = bArr;
    }

    @Deprecated
    public static fg a(ge geVar, String str) {
        int i;
        fg fgVar = new fg();
        try {
            i = Integer.parseInt(geVar.k());
        } catch (Exception e) {
            b.m149a("Blob parse chid err " + e.getMessage());
            i = 1;
        }
        fgVar.a(i);
        fgVar.a(geVar.j());
        fgVar.c(geVar.m());
        fgVar.b(geVar.n());
        fgVar.a("XMLMSG", (String) null);
        try {
            fgVar.a(geVar.m939a().getBytes("utf8"), str);
            if (TextUtils.isEmpty(str)) {
                fgVar.a((short) 3);
            } else {
                fgVar.a((short) 2);
                fgVar.a("SECMSG", (String) null);
            }
        } catch (UnsupportedEncodingException e2) {
            b.m149a("Blob setPayload err： " + e2.getMessage());
        }
        return fgVar;
    }

    public static fg b(ByteBuffer byteBuffer) {
        try {
            ByteBuffer slice = byteBuffer.slice();
            short s = slice.getShort(0);
            short s2 = slice.getShort(2);
            int i = slice.getInt(4);
            ef.a aVar = new ef.a();
            aVar.a(slice.array(), slice.arrayOffset() + 8, (int) s2);
            byte[] bArr = new byte[i];
            slice.position(s2 + 8);
            slice.get(bArr, 0, i);
            return new fg(aVar, s, bArr);
        } catch (Exception e) {
            b.m149a("read Blob err :" + e.getMessage());
            throw new IOException("Malformed Input");
        }
    }

    public static synchronized String d() {
        String sb;
        synchronized (fg.class) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(b);
            long j = c;
            c = 1 + j;
            sb2.append(Long.toString(j));
            sb = sb2.toString();
        }
        return sb;
    }

    public int a() {
        return this.d.c();
    }

    /* renamed from: a */
    public String m907a() {
        return this.d.m843c();
    }

    public ByteBuffer a(ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            byteBuffer = ByteBuffer.allocate(c());
        }
        byteBuffer.putShort(this.e);
        byteBuffer.putShort((short) this.d.mo888a());
        byteBuffer.putInt(this.g.length);
        int position = byteBuffer.position();
        this.d.a(byteBuffer.array(), byteBuffer.arrayOffset() + position, this.d.mo888a());
        byteBuffer.position(position + this.d.mo888a());
        byteBuffer.put(this.g);
        return byteBuffer;
    }

    /* renamed from: a */
    public short m908a() {
        return this.e;
    }

    public void a(int i) {
        this.d.a(i);
    }

    public void a(long j, String str, String str2) {
        if (j != 0) {
            this.d.a(j);
        }
        if (!TextUtils.isEmpty(str)) {
            this.d.a(str);
        }
        if (!TextUtils.isEmpty(str2)) {
            this.d.b(str2);
        }
    }

    public void a(String str) {
        this.d.e(str);
    }

    public void a(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            this.d.c(str);
            this.d.mo888a();
            if (!TextUtils.isEmpty(str2)) {
                this.d.d(str2);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("command should not be empty");
    }

    public void a(short s) {
        this.e = s;
    }

    public void a(byte[] bArr, String str) {
        if (!TextUtils.isEmpty(str)) {
            this.d.c(1);
            bArr = au.a(au.a(str, e()), bArr);
        } else {
            this.d.c(0);
        }
        this.g = bArr;
    }

    /* renamed from: a */
    public boolean m909a() {
        return this.d.j();
    }

    /* renamed from: a */
    public byte[] m910a() {
        return this.g;
    }

    /* renamed from: a */
    public byte[] m911a(String str) {
        if (this.d.e() == 1) {
            return au.a(au.a(str, e()), this.g);
        }
        if (this.d.e() == 0) {
            return this.g;
        }
        b.m149a("unknow cipher = " + this.d.e());
        return this.g;
    }

    public int b() {
        return this.d.f();
    }

    /* renamed from: b */
    public String m912b() {
        return this.d.m845d();
    }

    public void b(String str) {
        this.a = str;
    }

    public int c() {
        return this.d.mo890b() + 8 + this.g.length;
    }

    /* renamed from: c */
    public String m913c() {
        return this.d.m849f();
    }

    public void c(String str) {
        if (!TextUtils.isEmpty(str)) {
            int indexOf = str.indexOf("@");
            try {
                long parseLong = Long.parseLong(str.substring(0, indexOf));
                int indexOf2 = str.indexOf("/", indexOf);
                String substring = str.substring(indexOf + 1, indexOf2);
                String substring2 = str.substring(indexOf2 + 1);
                this.d.a(parseLong);
                this.d.a(substring);
                this.d.b(substring2);
            } catch (Exception e) {
                b.m149a("Blob parse user err " + e.getMessage());
            }
        }
    }

    public String e() {
        String e = this.d.m847e();
        if ("ID_NOT_AVAILABLE".equals(e)) {
            return null;
        }
        if (this.d.g()) {
            return e;
        }
        String d = d();
        this.d.e(d);
        return d;
    }

    public String f() {
        return this.a;
    }

    public String g() {
        if (!this.d.mo890b()) {
            return null;
        }
        return Long.toString(this.d.mo888a()) + "@" + this.d.mo888a() + "/" + this.d.mo890b();
    }

    public String toString() {
        return "Blob [chid=" + a() + "; Id=" + e() + "; cmd=" + m907a() + "; type=" + ((int) m908a()) + "; from=" + g() + " ]";
    }
}
