package com.xiaomi.micolauncher.module.setting.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings;
import com.xiaomi.micolauncher.common.L;

/* loaded from: classes3.dex */
abstract class BaseBrightnessController {
    final int a;
    final int b;
    final int c;
    volatile boolean d = d();
    final Context e;
    private a f;
    private Listener g;

    /* loaded from: classes3.dex */
    public interface Listener {
        void onChange(int i);
    }

    public abstract int getBrightness();

    public abstract void onChange(Uri uri);

    public abstract void saveBrightness(int i);

    public abstract void setTemporaryBrightness(int i);

    /* JADX INFO: Access modifiers changed from: package-private */
    public BaseBrightnessController(Context context) {
        this.e = context;
        PowerManager powerManager = (PowerManager) context.getSystemService(PowerManager.class);
        this.a = powerManager.getMinimumScreenBrightnessSetting();
        this.b = powerManager.getMaximumScreenBrightnessSetting();
        this.c = powerManager.getDefaultScreenBrightnessSetting();
        b();
    }

    public void release() {
        c();
    }

    private void b() {
        if (this.f == null) {
            this.f = new a(new Handler());
            ContentResolver contentResolver = this.e.getContentResolver();
            contentResolver.registerContentObserver(Settings.System.getUriFor("screen_brightness"), true, this.f);
            contentResolver.registerContentObserver(Settings.System.getUriFor("screen_auto_brightness_adj"), true, this.f);
            contentResolver.registerContentObserver(Settings.System.getUriFor("screen_brightness_mode"), true, this.f);
            L.base.d("%s registerObserver success", "[BaseBrightnessController]: ");
        }
    }

    private void c() {
        if (this.f != null) {
            this.e.getContentResolver().unregisterContentObserver(this.f);
            this.f = null;
            L.base.d("%s unRegisterObserver success", "[BaseBrightnessController]: ");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean d() {
        return Settings.System.getIntForUser(this.e.getContentResolver(), "screen_brightness_mode", 0, -2) != 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(boolean z) {
        if (this.d != z) {
            Settings.System.putInt(this.e.getContentResolver(), "screen_brightness_mode", z ? 1 : 0);
            this.d = z;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a() {
        return this.d;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(int i) {
        int i2 = ((this.b - this.a) * i) / 100;
        if (i2 > 80 || i2 < 15) {
            i2 = 60;
        }
        int brightness = getBrightness() + i2;
        int i3 = this.b;
        if (brightness <= i3) {
            i3 = brightness;
        }
        L.base.d("%s upBrightness.brightness=%d, .delta=%d", "[BaseBrightnessController]: ", Integer.valueOf(i3), Integer.valueOf(i));
        setTemporaryBrightness(i3);
        saveBrightness(i3);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(int i) {
        int i2 = ((this.b - this.a) * i) / 100;
        if (i2 > 80 || i2 < 15) {
            i2 = 60;
        }
        int brightness = getBrightness() - i2;
        int i3 = this.a;
        if (brightness >= i3) {
            i3 = brightness;
        }
        L.base.d("%s upBrightness.brightness=%d, .delta=%d", "[BaseBrightnessController]: ", Integer.valueOf(i3), Integer.valueOf(i));
        setTemporaryBrightness(i3);
        saveBrightness(i3);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(Listener listener) {
        this.g = listener;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void c(int i) {
        int i2 = this.b;
        int i3 = this.a;
        int i4 = (((i2 - i3) * i) / 100) + i3;
        L.base.d("%s setBrightnessPercent.brightness=%d, .percent=%d", "[BaseBrightnessController]: ", Integer.valueOf(i4), Integer.valueOf(i));
        setTemporaryBrightness(i4);
        saveBrightness(i4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public final class a extends ContentObserver {
        @Override // android.database.ContentObserver
        public boolean deliverSelfNotifications() {
            return true;
        }

        private a(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            super.onChange(z);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            L.base.d("%s onChange, selfChange=%s, uri=%s", "[BaseBrightnessController]: ", Boolean.valueOf(z), uri);
            super.onChange(z, uri);
            if (!z) {
                if (uri.equals(Settings.System.getUriFor("screen_brightness_mode"))) {
                    BaseBrightnessController baseBrightnessController = BaseBrightnessController.this;
                    baseBrightnessController.d = baseBrightnessController.d();
                }
                BaseBrightnessController.this.onChange(uri);
                if (BaseBrightnessController.this.g != null) {
                    BaseBrightnessController.this.g.onChange(BaseBrightnessController.this.getBrightness());
                }
            }
        }
    }
}
