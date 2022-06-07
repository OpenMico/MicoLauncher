package com.xiaomi.push.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.push.az;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class m {
    private static m a;
    private Context b;
    private List<String> c = new ArrayList();
    private final List<String> d = new ArrayList();
    private final List<String> e = new ArrayList();

    private m(Context context) {
        this.b = context.getApplicationContext();
        if (this.b == null) {
            this.b = context;
        }
        SharedPreferences sharedPreferences = this.b.getSharedPreferences("mipush_app_info", 0);
        String[] split = sharedPreferences.getString("unregistered_pkg_names", "").split(Constants.ACCEPT_TIME_SEPARATOR_SP);
        for (String str : split) {
            if (TextUtils.isEmpty(str)) {
                this.c.add(str);
            }
        }
        String[] split2 = sharedPreferences.getString("disable_push_pkg_names", "").split(Constants.ACCEPT_TIME_SEPARATOR_SP);
        for (String str2 : split2) {
            if (!TextUtils.isEmpty(str2)) {
                this.d.add(str2);
            }
        }
        String[] split3 = sharedPreferences.getString("disable_push_pkg_names_cache", "").split(Constants.ACCEPT_TIME_SEPARATOR_SP);
        for (String str3 : split3) {
            if (!TextUtils.isEmpty(str3)) {
                this.e.add(str3);
            }
        }
    }

    public static m a(Context context) {
        if (a == null) {
            a = new m(context);
        }
        return a;
    }

    public void a(String str) {
        synchronized (this.c) {
            if (!this.c.contains(str)) {
                this.c.add(str);
                this.b.getSharedPreferences("mipush_app_info", 0).edit().putString("unregistered_pkg_names", az.a(this.c, Constants.ACCEPT_TIME_SEPARATOR_SP)).commit();
            }
        }
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m1161a(String str) {
        boolean contains;
        synchronized (this.c) {
            contains = this.c.contains(str);
        }
        return contains;
    }

    public void b(String str) {
        synchronized (this.d) {
            if (!this.d.contains(str)) {
                this.d.add(str);
                this.b.getSharedPreferences("mipush_app_info", 0).edit().putString("disable_push_pkg_names", az.a(this.d, Constants.ACCEPT_TIME_SEPARATOR_SP)).commit();
            }
        }
    }

    /* renamed from: b  reason: collision with other method in class */
    public boolean m1162b(String str) {
        boolean contains;
        synchronized (this.d) {
            contains = this.d.contains(str);
        }
        return contains;
    }

    public void c(String str) {
        synchronized (this.e) {
            if (!this.e.contains(str)) {
                this.e.add(str);
                this.b.getSharedPreferences("mipush_app_info", 0).edit().putString("disable_push_pkg_names_cache", az.a(this.e, Constants.ACCEPT_TIME_SEPARATOR_SP)).commit();
            }
        }
    }

    /* renamed from: c  reason: collision with other method in class */
    public boolean m1163c(String str) {
        boolean contains;
        synchronized (this.e) {
            contains = this.e.contains(str);
        }
        return contains;
    }

    public void d(String str) {
        synchronized (this.c) {
            if (this.c.contains(str)) {
                this.c.remove(str);
                this.b.getSharedPreferences("mipush_app_info", 0).edit().putString("unregistered_pkg_names", az.a(this.c, Constants.ACCEPT_TIME_SEPARATOR_SP)).commit();
            }
        }
    }

    public void e(String str) {
        synchronized (this.d) {
            if (this.d.contains(str)) {
                this.d.remove(str);
                this.b.getSharedPreferences("mipush_app_info", 0).edit().putString("disable_push_pkg_names", az.a(this.d, Constants.ACCEPT_TIME_SEPARATOR_SP)).commit();
            }
        }
    }

    public void f(String str) {
        synchronized (this.e) {
            if (this.e.contains(str)) {
                this.e.remove(str);
                this.b.getSharedPreferences("mipush_app_info", 0).edit().putString("disable_push_pkg_names_cache", az.a(this.e, Constants.ACCEPT_TIME_SEPARATOR_SP)).commit();
            }
        }
    }
}
