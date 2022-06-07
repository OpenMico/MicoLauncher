package com.efs.sdk.base.a.f;

import com.efs.sdk.base.a.h.d;
import com.efs.sdk.base.protocol.ILogProtocol;
import java.io.File;

/* loaded from: classes.dex */
public final class b {
    public a a;
    public c b = new c();
    public byte[] c;
    public File d;

    public b(String str, byte b) {
        this.a = new a(str, b);
    }

    public final long a() {
        e();
        return this.a.f;
    }

    public final boolean b() {
        return !"none".equals(this.a.d);
    }

    public final void a(String str) {
        this.a.d = str;
    }

    public final boolean c() {
        return 1 != this.a.e;
    }

    public final void a(int i) {
        this.a.e = i;
        e();
    }

    public final void b(int i) {
        this.a.c = i;
    }

    public final void a(byte[] bArr) {
        this.c = bArr;
        e();
    }

    public final void d() {
        this.b.a = true;
    }

    public static b a(ILogProtocol iLogProtocol) {
        b bVar;
        Exception e;
        try {
            bVar = new b(iLogProtocol.getLogType(), iLogProtocol.getLogProtocol());
        } catch (Exception e2) {
            e = e2;
            bVar = null;
        }
        try {
            switch (iLogProtocol.getBodyType()) {
                case 0:
                    bVar.b(0);
                    bVar.a(iLogProtocol.generate());
                    break;
                case 1:
                    bVar.b(1);
                    bVar.d = new File(iLogProtocol.getFilePath());
                    break;
                default:
                    d.a("efs.base", "Can not support body type: " + iLogProtocol.getBodyType(), null);
                    break;
            }
        } catch (Exception e3) {
            e = e3;
            d.b("efs.base", "log send error", e);
            return bVar;
        }
        return bVar;
    }

    private void e() {
        byte[] bArr;
        if (this.a.c == 0 && (bArr = this.c) != null) {
            this.a.f = bArr.length;
        } else if (this.a.c == 1 && this.d.exists()) {
            this.a.f = this.d.length();
        }
    }
}
