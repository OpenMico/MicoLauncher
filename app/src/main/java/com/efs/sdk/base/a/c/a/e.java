package com.efs.sdk.base.a.c.a;

import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import com.efs.sdk.base.a.c.a;
import com.efs.sdk.base.a.e.f;
import com.efs.sdk.base.a.h.b;
import com.efs.sdk.base.a.h.d;
import com.efs.sdk.base.newsharedpreferences.SharedPreferencesUtils;
import java.io.File;
import java.util.Map;

/* loaded from: classes.dex */
public final class e implements SharedPreferences.OnSharedPreferenceChangeListener {
    volatile SharedPreferences a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean a(@NonNull b bVar) {
        c();
        if (this.a == null) {
            return false;
        }
        SharedPreferences.Editor edit = this.a.edit();
        edit.clear();
        edit.putInt("cver", bVar.a);
        edit.putLong("last_refresh_time", System.currentTimeMillis());
        for (Map.Entry<String, String> entry : bVar.e.entrySet()) {
            edit.putString(entry.getKey(), entry.getValue());
        }
        edit.apply();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a() {
        a a = com.efs.sdk.base.a.d.a.a();
        File b = com.efs.sdk.base.a.h.a.b(a.c, a.a);
        if (!b.exists()) {
            return false;
        }
        b.b(b);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void b() {
        File a = com.efs.sdk.base.a.h.a.a(com.efs.sdk.base.a.d.a.a().c, com.efs.sdk.base.a.d.a.a().a);
        if (a.exists()) {
            a.delete();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void c() {
        try {
            d();
        } catch (Throwable th) {
            d.b("efs.config", "init sharedpreferences error", th);
        }
    }

    private void d() {
        if (this.a == null) {
            synchronized (com.efs.sdk.base.a.e.b.class) {
                if (this.a == null) {
                    String str = com.efs.sdk.base.a.d.a.a().a;
                    this.a = SharedPreferencesUtils.getSharedPreferences(com.efs.sdk.base.a.d.a.a().c, com.efs.sdk.base.a.h.c.b.a(("config_" + str.toLowerCase()).getBytes()));
                    this.a.registerOnSharedPreferenceChangeListener(this);
                }
            }
        }
    }

    @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
    public final void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String str) {
        f fVar;
        fVar = f.a.a;
        if (!fVar.a()) {
            c.a().b();
        }
    }
}
