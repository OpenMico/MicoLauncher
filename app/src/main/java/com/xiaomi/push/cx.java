package com.xiaomi.push;

import com.xiaomi.push.de;
import java.io.File;
import java.util.Date;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class cx extends de.b {
    File a;
    final /* synthetic */ int d;
    final /* synthetic */ Date e;
    final /* synthetic */ Date f;
    final /* synthetic */ String g;
    final /* synthetic */ String h;
    final /* synthetic */ boolean i;
    final /* synthetic */ de j;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public cx(de deVar, int i, Date date, Date date2, String str, String str2, boolean z) {
        super();
        this.j = deVar;
        this.d = i;
        this.e = date;
        this.f = date2;
        this.g = str;
        this.h = str2;
        this.i = z;
    }

    @Override // com.xiaomi.push.de.b, com.xiaomi.push.am.b
    public void b() {
        if (ab.d()) {
            try {
                File file = new File(this.j.b.getExternalFilesDir(null) + "/.logcache");
                file.mkdirs();
                if (file.isDirectory()) {
                    cw cwVar = new cw();
                    cwVar.a(this.d);
                    this.a = cwVar.a(this.j.b, this.e, this.f, file);
                }
            } catch (NullPointerException unused) {
            }
        }
    }

    @Override // com.xiaomi.push.am.b
    public void c() {
        File file = this.a;
        if (file != null && file.exists()) {
            this.j.a.add(new de.c(this.g, this.h, this.a, this.i));
        }
        this.j.a(0L);
    }
}
