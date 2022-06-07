package com.xiaomi.mico.settingslib.core;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;

/* loaded from: classes3.dex */
public class MicoSettingsContentObserver extends ContentObserver {
    private MicoSettingChangeListener a;

    @Override // android.database.ContentObserver
    public boolean deliverSelfNotifications() {
        return true;
    }

    public MicoSettingsContentObserver(Handler handler) {
        super(handler);
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z) {
        super.onChange(z);
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z, Uri uri) {
        super.onChange(z, uri);
        MicoSettingChangeListener micoSettingChangeListener = this.a;
        if (micoSettingChangeListener != null) {
            micoSettingChangeListener.onConfigChange(z, uri.toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(MicoSettingChangeListener micoSettingChangeListener) {
        this.a = micoSettingChangeListener;
    }
}
