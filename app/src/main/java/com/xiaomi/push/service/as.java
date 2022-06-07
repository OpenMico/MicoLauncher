package com.xiaomi.push.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.push.ac;

/* loaded from: classes4.dex */
public class as {
    private static as a;
    private Context b;
    private int c = 0;

    private as(Context context) {
        this.b = context.getApplicationContext();
    }

    public static as a(Context context) {
        if (a == null) {
            a = new as(context);
        }
        return a;
    }

    @SuppressLint({"NewApi"})
    public int a() {
        int i = this.c;
        if (i != 0) {
            return i;
        }
        this.c = Build.VERSION.SDK_INT >= 17 ? Settings.Global.getInt(this.b.getContentResolver(), "device_provisioned", 0) : Settings.Secure.getInt(this.b.getContentResolver(), "device_provisioned", 0);
        return this.c;
    }

    @SuppressLint({"NewApi"})
    /* renamed from: a  reason: collision with other method in class */
    public Uri m1142a() {
        return Build.VERSION.SDK_INT >= 17 ? Settings.Global.getUriFor("device_provisioned") : Settings.Secure.getUriFor("device_provisioned");
    }

    /* renamed from: a  reason: collision with other method in class */
    public boolean m1143a() {
        return ac.a.contains("xmsf") || ac.a.contains("xiaomi") || ac.a.contains(OneTrack.Param.MIUI);
    }
}
