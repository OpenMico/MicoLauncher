package com.xiaomi.push;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.xiaomi.push.aj;
import com.xiaomi.push.bx;
import com.xiaomi.push.service.ag;
import com.xiaomi.push.service.be;

/* loaded from: classes4.dex */
public class bl {
    private static volatile bl e;
    private Context f;
    private String g;
    private String h;
    private ca i;
    private cb j;
    private final String a = "push_stat_sp";
    private final String b = "upload_time";
    private final String c = "delete_time";
    private final String d = "check_time";
    private aj.a k = new ao(this);
    private aj.a l = new bg(this);
    private aj.a m = new bh(this);

    private bl(Context context) {
        this.f = context;
    }

    public static bl a(Context context) {
        if (e == null) {
            synchronized (bl.class) {
                if (e == null) {
                    e = new bl(context);
                }
            }
        }
        return e;
    }

    public void b(String str) {
        SharedPreferences.Editor edit = this.f.getSharedPreferences("push_stat_sp", 0).edit();
        edit.putLong(str, System.currentTimeMillis());
        s.a(edit);
    }

    private boolean c() {
        return ag.a(this.f).a(hm.StatDataSwitch.a(), true);
    }

    public String d() {
        return this.f.getDatabasePath(bp.f15a).getAbsolutePath();
    }

    public String a() {
        return this.g;
    }

    public void a(bx.a aVar) {
        bx.a(this.f).a(aVar);
    }

    public void a(hl hlVar) {
        if (c() && be.a(hlVar.e())) {
            a(bu.a(this.f, d(), hlVar));
        }
    }

    public void a(String str) {
        if (c() && !TextUtils.isEmpty(str)) {
            a(cc.a(this.f, str));
        }
    }

    public void a(String str, String str2, Boolean bool) {
        if (this.i == null) {
            return;
        }
        if (bool.booleanValue()) {
            this.i.a(this.f, str2, str);
        } else {
            this.i.b(this.f, str2, str);
        }
    }

    public String b() {
        return this.h;
    }
}
