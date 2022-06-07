package com.xiaomi.push;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import java.util.HashMap;

/* loaded from: classes4.dex */
public final class el {
    private static volatile el a;
    private Context b;
    private HashMap<en, eo> c = new HashMap<>();
    private String d;
    private String e;
    private int f;
    private ep g;

    private el(Context context) {
        this.b = context;
        this.c.put(en.SERVICE_ACTION, new ei());
        this.c.put(en.SERVICE_COMPONENT, new ej());
        this.c.put(en.ACTIVITY, new Cdo());
        this.c.put(en.PROVIDER, new du());
    }

    public static el a(Context context) {
        if (a == null) {
            synchronized (el.class) {
                if (a == null) {
                    a = new el(context);
                }
            }
        }
        return a;
    }

    public void a(en enVar, Context context, ek ekVar) {
        this.c.get(enVar).a(context, ekVar);
    }

    public int a() {
        return this.f;
    }

    /* renamed from: a */
    public ep m892a() {
        return this.g;
    }

    /* renamed from: a */
    public String m893a() {
        return this.d;
    }

    public void a(int i) {
        this.f = i;
    }

    public void a(Context context, String str, int i, String str2, String str3) {
        if (context == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            eh.a(context, "" + str, 1008, "A receive a incorrect message");
            return;
        }
        a(i);
        aj.a(this.b).a(new dp(this, str, context, str2, str3));
    }

    public void a(en enVar, Context context, Intent intent, String str) {
        if (enVar != null) {
            this.c.get(enVar).a(context, intent, str);
        } else {
            eh.a(context, "null", 1008, "A receive a incorrect message with empty type");
        }
    }

    public void a(ep epVar) {
        this.g = epVar;
    }

    public void a(String str) {
        this.d = str;
    }

    public void a(String str, String str2, int i, ep epVar) {
        a(str);
        b(str2);
        a(i);
        a(epVar);
    }

    public String b() {
        return this.e;
    }

    public void b(String str) {
        this.e = str;
    }
}
