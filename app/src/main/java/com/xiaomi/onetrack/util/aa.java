package com.xiaomi.onetrack.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.xiaomi.onetrack.e.a;

/* loaded from: classes4.dex */
public class aa {
    private static final String A = "onetrack_user_id";
    private static final String B = "onetrack_user_type";
    private static final String C = "custom_id";
    private static final String D = "page_end";
    private static final String E = "last_app_version";
    private static final String F = "first_launch_time";
    private static final String G = "pref_custom_privacy_policy_";
    private static final String a = "onetrack";
    private static final String b = "one_track_pref";
    private static SharedPreferences c = null;
    private static SharedPreferences.Editor d = null;
    private static final String e = "last_upload_active_time";
    private static final String f = "last_upload_usage_time";
    private static final String g = "last_collect_crash_time";
    private static final String h = "report_crash_ticket";
    private static final String i = "last_secret_key_time";
    private static final String j = "last_common_cloud_time";
    private static final String k = "next_update_common_conf_time";
    private static final String l = "common_cloud_data";
    private static final String m = "common_config_hash";
    private static final String n = "s_t";
    private static final String o = "l_t";
    private static final String p = "e_t";
    private static final String q = "secret_key_data";
    private static final String r = "region_rul";
    private static final String s = "pref_instance_id";
    private static final String t = "pref_instance_id_last_use_time";
    private static final String u = "last_usage_resume_component";
    private static final String v = "last_usage_resume_time";
    private static final String w = "network_access_enabled";
    private static final String x = "anonymous_enabled";
    private static final String y = "onetrack_first_open";
    private static final String z = "dau_last_time";

    private static void C() {
        if (d == null) {
            synchronized (aa.class) {
                if (d == null) {
                    c = a.a().getSharedPreferences(b, 0);
                    d = c.edit();
                }
            }
        }
    }

    private static String a(String str, String str2) {
        C();
        return c.getString(str, str2);
    }

    private static void b(String str, String str2) {
        C();
        d.putString(str, str2).apply();
    }

    private static long a(String str, long j2) {
        C();
        return c.getLong(str, j2);
    }

    private static void b(String str, long j2) {
        C();
        d.putLong(str, j2).apply();
    }

    private static boolean b(String str, boolean z2) {
        C();
        return c.getBoolean(str, z2);
    }

    private static void c(String str, boolean z2) {
        C();
        d.putBoolean(str, z2).apply();
    }

    private static void a(String str, float f2) {
        C();
        d.putFloat(str, f2).apply();
    }

    private static float b(String str, float f2) {
        C();
        return c.getFloat(str, f2);
    }

    private static void l(String str) {
        C();
        d.remove(str).apply();
    }

    public static long a() {
        return a(e, 0L);
    }

    public static void a(long j2) {
        b(e, j2);
    }

    public static long b(long j2) {
        return a(f, j2);
    }

    public static void c(long j2) {
        b(g, j2);
    }

    public static long b() {
        return a(g, 0L);
    }

    public static void d(long j2) {
        b(h, j2);
    }

    public static long c() {
        return a(h, 0L);
    }

    public static void e(long j2) {
        b(f, j2);
    }

    public static long d() {
        return a(n, 0L);
    }

    public static void f(long j2) {
        b(n, j2);
    }

    public static long e() {
        return a(o, 0L);
    }

    public static void g(long j2) {
        b(o, j2);
    }

    public static long f() {
        return a(p, 0L);
    }

    public static void h(long j2) {
        b(p, j2);
    }

    public static void a(String str) {
        b(q, str);
    }

    public static String g() {
        return a(q, "");
    }

    public static void b(String str) {
        b(r, str);
    }

    public static String h() {
        return a(r, "");
    }

    public static void i(long j2) {
        b(i, j2);
    }

    public static long i() {
        return a(i, 0L);
    }

    public static void j(long j2) {
        b(k, j2);
    }

    public static long j() {
        return a(k, 0L);
    }

    public static void c(String str) {
        b(m, str);
    }

    public static String k() {
        return a(m, "");
    }

    public static void d(String str) {
        b(l, str);
    }

    public static String l() {
        return a(l, "");
    }

    public static String m() {
        return a(s, "");
    }

    public static void e(String str) {
        b(s, str);
        k(ac.a());
    }

    public static long n() {
        return a(t, 0L);
    }

    public static void k(long j2) {
        b(t, j2);
    }

    public static String o() {
        return a(u, "");
    }

    public static void f(String str) {
        b(u, str);
    }

    public static long p() {
        return a(v, 0L);
    }

    public static void l(long j2) {
        b(v, j2);
    }

    public static boolean q() {
        return b(w, true);
    }

    public static void a(boolean z2) {
        c(w, z2);
    }

    public static boolean r() {
        return b(x, false);
    }

    public static void b(boolean z2) {
        c(x, z2);
    }

    public static void c(boolean z2) {
        c(y, z2);
    }

    public static boolean s() {
        return b(y, true);
    }

    public static long t() {
        return a(z, 0L);
    }

    public static void m(long j2) {
        b(z, j2);
    }

    public static void g(String str) {
        b(A, str);
    }

    public static String u() {
        return a(A, "");
    }

    public static void v() {
        l(A);
    }

    public static void h(String str) {
        b(B, str);
    }

    public static String w() {
        return a(B, "");
    }

    public static void x() {
        l(B);
    }

    public static void a(Context context, String str) {
        b(C, str);
    }

    public static String a(Context context) {
        return a(C, "");
    }

    public static long y() {
        return a(w, 0L);
    }

    public static String z() {
        return a(D, "");
    }

    public static void i(String str) {
        b(D, str);
    }

    public static void j(String str) {
        b(E, str);
    }

    public static String A() {
        return a(E, "");
    }

    public static long B() {
        return a(F, 0L);
    }

    public static void n(long j2) {
        b(F, j2);
    }

    public static boolean k(String str) {
        if (TextUtils.isEmpty(str)) {
            return true;
        }
        return b(G + str, true);
    }

    public static void a(String str, boolean z2) {
        if (!TextUtils.isEmpty(str)) {
            c(G + str, z2);
        }
    }
}
