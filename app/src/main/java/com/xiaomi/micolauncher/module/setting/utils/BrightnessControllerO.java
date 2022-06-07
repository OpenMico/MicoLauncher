package com.xiaomi.micolauncher.module.setting.utils;

import android.content.Context;
import android.net.Uri;
import android.os.IPowerManager;
import android.os.ServiceManager;
import android.provider.Settings;
import androidx.annotation.VisibleForTesting;
import com.xiaomi.micolauncher.common.L;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes3.dex */
public class BrightnessControllerO extends BaseBrightnessController {
    private IPowerManager f = IPowerManager.Stub.asInterface(ServiceManager.getService("power"));
    private float g;

    private static float a(float f, int i, int i2) {
        if (f > i2) {
            return 1.0f;
        }
        float f2 = i;
        if (f < f2) {
            return 0.0f;
        }
        return (f - f2) / (i2 - i);
    }

    @Override // com.xiaomi.micolauncher.module.setting.utils.BaseBrightnessController
    public /* bridge */ /* synthetic */ void release() {
        super.release();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BrightnessControllerO(Context context) {
        super(context);
    }

    @Override // com.xiaomi.micolauncher.module.setting.utils.BaseBrightnessController
    public int getBrightness() {
        if (this.d) {
            return (int) (a(this.g, -1, 1) * this.b);
        }
        return Settings.System.getInt(this.e.getContentResolver(), "screen_brightness", 0);
    }

    @Override // com.xiaomi.micolauncher.module.setting.utils.BaseBrightnessController
    public void saveBrightness(int i) {
        if (this.d) {
            Settings.System.putFloatForUser(this.e.getContentResolver(), "screen_auto_brightness_adj", ((i * 2.0f) / 255.0f) - 1.0f, 0);
            return;
        }
        Settings.System.putInt(this.e.getContentResolver(), "screen_brightness", i);
    }

    private void d(int i) {
        setManualBrightness(this.f, i, false);
    }

    @VisibleForTesting
    public static void setManualBrightness(IPowerManager iPowerManager, int i, boolean z) {
        if (iPowerManager != null) {
            try {
                iPowerManager.getClass().getMethod("setTemporaryScreenBrightnessSettingOverride", Integer.TYPE).invoke(iPowerManager, Integer.valueOf(i));
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                L.base.e("%s setManualBrightness.exception=%s", "[BrightnessControllerO]: ", e);
                if (z) {
                    throw new IllegalStateException(e);
                }
            }
        }
    }

    private void e(int i) {
        setAutoBrightnessAdj(this.f, ((i * 2.0f) / 255.0f) - 1.0f, false);
    }

    @VisibleForTesting
    public static void setAutoBrightnessAdj(IPowerManager iPowerManager, float f, boolean z) {
        if (iPowerManager != null) {
            try {
                iPowerManager.getClass().getMethod("setTemporaryScreenAutoBrightnessAdjustmentSettingOverride", Float.TYPE).invoke(iPowerManager, Float.valueOf(f));
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                L.base.e("%s setAutoBrightnessAdj.exception=%s", "[BrightnessControllerO]: ", e);
                if (z) {
                    throw new IllegalStateException(e);
                }
            }
        }
    }

    @Override // com.xiaomi.micolauncher.module.setting.utils.BaseBrightnessController
    public void setTemporaryBrightness(int i) {
        if (i < this.a) {
            i = this.a;
        } else if (i > this.b) {
            i = this.b;
        }
        if (this.d) {
            e(i);
        } else {
            d(i);
        }
    }

    @Override // com.xiaomi.micolauncher.module.setting.utils.BaseBrightnessController
    public void onChange(Uri uri) {
        if (uri.equals(Settings.System.getUriFor("screen_auto_brightness_adj"))) {
            this.g = Settings.System.getFloat(this.e.getContentResolver(), "screen_auto_brightness_adj", this.c);
        }
    }
}
