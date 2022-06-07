package com.xiaomi.push;

import android.content.Context;
import android.content.SharedPreferences;

/* loaded from: classes4.dex */
public class bi {
    private static volatile bi a;
    private Context b;

    private bi(Context context) {
        this.b = context;
    }

    public static bi a(Context context) {
        if (a == null) {
            synchronized (bi.class) {
                if (a == null) {
                    a = new bi(context);
                }
            }
        }
        return a;
    }

    public synchronized long a(String str, String str2, long j) {
        try {
        } catch (Throwable th) {
            throw th;
        }
        return this.b.getSharedPreferences(str, 4).getLong(str2, j);
    }

    public synchronized String a(String str, String str2, String str3) {
        try {
        } catch (Throwable th) {
            throw th;
        }
        return this.b.getSharedPreferences(str, 4).getString(str2, str3);
    }

    /* renamed from: a  reason: collision with other method in class */
    public synchronized void m780a(String str, String str2, long j) {
        SharedPreferences.Editor edit = this.b.getSharedPreferences(str, 4).edit();
        edit.putLong(str2, j);
        edit.commit();
    }

    /* renamed from: a  reason: collision with other method in class */
    public synchronized void m781a(String str, String str2, String str3) {
        SharedPreferences.Editor edit = this.b.getSharedPreferences(str, 4).edit();
        edit.putString(str2, str3);
        edit.commit();
    }
}
