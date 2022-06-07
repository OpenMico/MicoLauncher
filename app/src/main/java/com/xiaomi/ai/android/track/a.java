package com.xiaomi.ai.android.track;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.ai.android.core.d;
import com.xiaomi.ai.android.utils.NetworkUtils;
import com.xiaomi.ai.core.AivsConfig;
import com.xiaomi.ai.log.Logger;
import com.xiaomi.ai.track.TrackData;

/* loaded from: classes2.dex */
public class a extends com.xiaomi.ai.track.a {
    private d a;

    public a(d dVar) {
        super(dVar.g());
        this.a = dVar;
        a("sdk.type", "android");
        a("sdk.version", "1.35.1");
        a("android.androidsdk.version", Build.VERSION.SDK_INT);
        a("android.app.package", this.a.a().getPackageName());
        if (this.a.g() != null) {
            a("channel.type", this.a.g().getChannelType());
        }
        String f = f();
        if (f != null) {
            a("android.app.version", f);
        }
        String string = dVar.b().getString(AivsConfig.Track.DEVICE);
        if (TextUtils.isEmpty(string)) {
            a("android.device", Build.MODEL);
        } else {
            a("android.device", string);
        }
    }

    private String f() {
        Context a = this.a.a();
        try {
            return a.getPackageManager().getPackageInfo(a.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Logger.d("AndroidTrackInfo", Log.getStackTraceString(e));
            return null;
        }
    }

    @Override // com.xiaomi.ai.track.a
    public TrackData a() {
        TrackData a = super.a();
        String c = NetworkUtils.c(this.a.a());
        a.set("network", c);
        if ("WIFI".equals(c)) {
            a.set("network.wifi.signal.level", NetworkUtils.d(this.a.a()));
        } else {
            a.set("network.data.carrier.type", NetworkUtils.e(this.a.a()));
        }
        return a;
    }

    public void b() {
        com.xiaomi.ai.core.a g = this.a.g();
        if (g != null) {
            a("channel.type", g.getChannelType());
        }
    }
}
