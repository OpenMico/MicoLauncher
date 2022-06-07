package com.xiaomi.push;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes4.dex */
public class ea extends dy {
    private boolean b;
    private boolean c;
    private boolean d;
    private boolean e;
    private boolean f;

    public ea(Context context, int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        super(context, i);
        this.b = z;
        this.c = z2;
        this.d = z3;
        this.e = z4;
        this.f = z5;
    }

    private String c() {
        if (!this.b) {
            return "off";
        }
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) this.f24a.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
            return displayMetrics.heightPixels + Constants.ACCEPT_TIME_SEPARATOR_SP + displayMetrics.widthPixels;
        } catch (Throwable unused) {
            return "";
        }
    }

    private String d() {
        if (!this.c) {
            return "off";
        }
        try {
            return Build.VERSION.RELEASE;
        } catch (Throwable unused) {
            return "";
        }
    }

    private String e() {
        if (!this.d) {
            return "off";
        }
        try {
            return String.valueOf(Build.VERSION.SDK_INT);
        } catch (Throwable unused) {
            return "";
        }
    }

    private String f() {
        if (!this.e) {
            return "off";
        }
        try {
            return Settings.Secure.getString(this.f24a.getContentResolver(), "android_id");
        } catch (Throwable unused) {
            return "";
        }
    }

    private String g() {
        if (!this.f) {
            return "off";
        }
        try {
            return ((TelephonyManager) this.f24a.getSystemService("phone")).getSimOperator();
        } catch (Throwable unused) {
            return "";
        }
    }

    @Override // com.xiaomi.push.dy, com.xiaomi.push.aj.a
    /* renamed from: a */
    public int mo834a() {
        return 3;
    }

    @Override // com.xiaomi.push.dy, com.xiaomi.push.aj.a
    /* renamed from: a  reason: collision with other method in class */
    public hj mo834a() {
        return hj.DeviceInfoV2;
    }

    @Override // com.xiaomi.push.dy, com.xiaomi.push.aj.a
    /* renamed from: a */
    public String mo834a() {
        return c() + "|" + d() + "|" + e() + "|" + f() + "|" + g();
    }
}
