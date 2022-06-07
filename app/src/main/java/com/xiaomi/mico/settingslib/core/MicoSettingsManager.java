package com.xiaomi.mico.settingslib.core;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Handler;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class MicoSettingsManager {
    private static volatile MicoSettingsManager a;
    private MicoSettingsContentObserver b;

    public static MicoSettingsManager getInstance() {
        if (a == null) {
            synchronized (MicoSettingsManager.class) {
                if (a == null) {
                    a = new MicoSettingsManager();
                }
            }
        }
        return a;
    }

    private MicoSettingsManager() {
    }

    public void register(Context context, MicoSettingChangeListener micoSettingChangeListener) {
        if (context == null) {
            throw new IllegalStateException("init first");
        } else if (this.b == null) {
            this.b = new MicoSettingsContentObserver(new Handler());
            final ContentResolver contentResolver = context.getContentResolver();
            MicoSettings.RIGISTER_KEY.forEach(new Consumer() { // from class: com.xiaomi.mico.settingslib.core.-$$Lambda$MicoSettingsManager$qjxFGaMhjoinYjpZTDiKI_lk770
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    MicoSettingsManager.this.a(contentResolver, (String) obj);
                }
            });
            this.b.a(micoSettingChangeListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ContentResolver contentResolver, String str) {
        contentResolver.registerContentObserver(MicoSettings.getUriFor(str), true, this.b);
    }

    public void unRegister(Context context) {
        MicoSettingsContentObserver micoSettingsContentObserver = this.b;
        if (micoSettingsContentObserver != null) {
            micoSettingsContentObserver.a(null);
            if (context != null) {
                context.getContentResolver().unregisterContentObserver(this.b);
            }
            this.b = null;
        }
    }
}
