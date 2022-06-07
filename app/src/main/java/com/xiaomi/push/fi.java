package com.xiaomi.push;

import android.os.Build;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.push.ef;
import com.xiaomi.push.service.au;
import com.xiaomi.push.service.ba;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Locale;
import java.util.TimeZone;
import java.util.zip.Adler32;

/* loaded from: classes4.dex */
public class fi {
    ByteBuffer a = ByteBuffer.allocate(2048);
    private ByteBuffer b = ByteBuffer.allocate(4);
    private Adler32 c = new Adler32();
    private fl d;
    private OutputStream e;
    private int f;
    private int g;
    private byte[] h;

    /* JADX INFO: Access modifiers changed from: package-private */
    public fi(OutputStream outputStream, fl flVar) {
        this.e = new BufferedOutputStream(outputStream);
        this.d = flVar;
        TimeZone timeZone = TimeZone.getDefault();
        this.f = timeZone.getRawOffset() / 3600000;
        this.g = timeZone.useDaylightTime() ? 1 : 0;
    }

    public int a(fg fgVar) {
        int c = fgVar.c();
        if (c > 32768) {
            b.m149a("Blob size=" + c + " should be less than 32768 Drop blob chid=" + fgVar.a() + " id=" + fgVar.e());
            return 0;
        }
        this.a.clear();
        int i = c + 8 + 4;
        if (i > this.a.capacity() || this.a.capacity() > 4096) {
            this.a = ByteBuffer.allocate(i);
        }
        this.a.putShort((short) -15618);
        this.a.putShort((short) 5);
        this.a.putInt(c);
        int position = this.a.position();
        this.a = fgVar.a(this.a);
        if (!"CONN".equals(fgVar.m907a())) {
            if (this.h == null) {
                this.h = this.d.h();
            }
            au.a(this.h, this.a.array(), true, position, c);
        }
        this.c.reset();
        this.c.update(this.a.array(), 0, this.a.position());
        this.b.putInt(0, (int) this.c.getValue());
        this.e.write(this.a.array(), 0, this.a.position());
        this.e.write(this.b.array(), 0, 4);
        this.e.flush();
        int position2 = this.a.position() + 4;
        b.c("[Slim] Wrote {cmd=" + fgVar.m907a() + ";chid=" + fgVar.a() + ";len=" + position2 + "}");
        return position2;
    }

    public void a() {
        ef.e eVar = new ef.e();
        eVar.a(106);
        eVar.a(Build.MODEL);
        eVar.b(u.m1171a());
        eVar.c(ba.m1145a());
        eVar.b(38);
        eVar.d(this.d.b());
        eVar.e(this.d.mo927a());
        eVar.f(Locale.getDefault().toString());
        eVar.c(Build.VERSION.SDK_INT);
        byte[] a = this.d.mo927a().m925a();
        if (a != null) {
            eVar.a(ef.b.a(a));
        }
        fg fgVar = new fg();
        fgVar.a(0);
        fgVar.a("CONN", (String) null);
        fgVar.a(0L, "xiaomi.com", null);
        fgVar.a(eVar.mo888a(), (String) null);
        a(fgVar);
        b.m149a("[slim] open conn: andver=" + Build.VERSION.SDK_INT + " sdk=38 hash=" + ba.m1145a() + " tz=" + this.f + Constants.COLON_SEPARATOR + this.g + " Model=" + Build.MODEL + " os=" + Build.VERSION.INCREMENTAL);
    }

    public void b() {
        fg fgVar = new fg();
        fgVar.a("CLOSE", (String) null);
        a(fgVar);
        this.e.close();
    }
}
