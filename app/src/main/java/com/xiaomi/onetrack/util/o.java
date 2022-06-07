package com.xiaomi.onetrack.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import com.xiaomi.onetrack.e.a;
import java.util.UUID;

/* loaded from: classes4.dex */
public class o {
    private static final String a = "o";
    private static final String b = "content://com.miui.analytics.OneTrackProvider/insId";
    private static final String c = "insId";
    private static final String d = "pkg";
    private static final String e = "sign";
    private static volatile o f;
    private static String g;
    private static String i;
    private boolean j = false;
    private final Context h = a.a();

    public static o a() {
        if (f == null) {
            synchronized (o.class) {
                if (f == null) {
                    f = new o();
                }
            }
        }
        return f;
    }

    private o() {
        i = a.d();
    }

    public void a(Boolean bool) {
        this.j = bool.booleanValue();
    }

    public void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            g = str;
            if (this.j) {
                b(g);
            }
            aa.e(g);
        }
    }

    public String b() {
        String str;
        if (!TextUtils.isEmpty(g)) {
            return g;
        }
        if (this.j) {
            str = c();
            String d2 = d();
            if (TextUtils.isEmpty(str) && !TextUtils.isEmpty(d2)) {
                b(d2);
                str = d2;
            } else if (!TextUtils.isEmpty(str) && TextUtils.isEmpty(d2)) {
                aa.e(str);
            }
        } else {
            str = d();
        }
        if (TextUtils.isEmpty(str)) {
            g = UUID.randomUUID().toString();
            if (this.j) {
                b(g);
            }
            aa.e(g);
        } else {
            g = str;
        }
        return g;
    }

    private String c() {
        String str = null;
        try {
            Uri.Builder buildUpon = Uri.parse(b).buildUpon();
            buildUpon.appendQueryParameter("pkg", i);
            buildUpon.appendQueryParameter(e, com.xiaomi.onetrack.c.a.a(c + i));
            Cursor query = this.h.getContentResolver().query(buildUpon.build(), null, null, null, null);
            if (query != null) {
                while (query.moveToNext()) {
                    str = query.getString(0);
                }
                query.close();
            }
        } catch (Exception e2) {
            p.a(a, "getRemoteCacheInstanceId e", e2);
        }
        return str;
    }

    private void b(String str) {
        try {
            if (!TextUtils.isEmpty(str)) {
                Uri parse = Uri.parse(b);
                ContentValues contentValues = new ContentValues();
                contentValues.put(i, str);
                this.h.getContentResolver().insert(parse, contentValues);
            }
        } catch (Exception e2) {
            aa.e(str);
            p.a(a, "setRemoteCacheInstanceId e", e2);
        }
    }

    private String d() {
        String a2 = aa.a(this.h);
        if (TextUtils.isEmpty(a2)) {
            return aa.m();
        }
        aa.e(a2);
        return a2;
    }
}
