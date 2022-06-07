package com.xiaomi.push;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes4.dex */
public class dz extends dy {
    private boolean b;
    private boolean c;
    private boolean d;
    private boolean e;

    public dz(Context context, int i, boolean z, boolean z2, boolean z3, boolean z4) {
        super(context, i);
        this.b = z;
        this.c = z2;
        if (l.d()) {
            this.c = false;
        }
        this.d = z3;
        this.e = z4;
    }

    private String a(Context context) {
        return !this.e ? "off" : "";
    }

    private String c() {
        if (!this.b) {
            return "off";
        }
        try {
            String d = d();
            if (TextUtils.isEmpty(d)) {
                return "";
            }
            return az.a(d) + Constants.ACCEPT_TIME_SEPARATOR_SP + az.b(d);
        } catch (Throwable unused) {
            return "";
        }
    }

    private String d() {
        return "";
    }

    private String e() {
        return !this.c ? "off" : "";
    }

    private String f() {
        return !this.d ? "off" : "";
    }

    @Override // com.xiaomi.push.dy, com.xiaomi.push.aj.a
    /* renamed from: a */
    public int mo834a() {
        return 13;
    }

    @Override // com.xiaomi.push.dy, com.xiaomi.push.aj.a
    /* renamed from: a  reason: collision with other method in class */
    public hj mo834a() {
        return hj.DeviceBaseInfo;
    }

    @Override // com.xiaomi.push.dy, com.xiaomi.push.aj.a
    /* renamed from: a */
    public String mo834a() {
        return c() + "|" + e() + "|" + f() + "|" + a(this.f24a);
    }
}
