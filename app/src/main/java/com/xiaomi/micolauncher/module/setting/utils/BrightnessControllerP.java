package com.xiaomi.micolauncher.module.setting.utils;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.provider.Settings;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.common.L;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;

/* loaded from: classes3.dex */
public class BrightnessControllerP extends BaseBrightnessController {
    private Context f;
    private final DisplayManager g;

    @Override // com.xiaomi.micolauncher.module.setting.utils.BaseBrightnessController
    public void onChange(Uri uri) {
    }

    @Override // com.xiaomi.micolauncher.module.setting.utils.BaseBrightnessController
    public /* bridge */ /* synthetic */ void release() {
        super.release();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BrightnessControllerP(Context context) {
        super(context);
        this.f = context;
        this.g = (DisplayManager) context.getSystemService(DisplayManager.class);
    }

    @Override // com.xiaomi.micolauncher.module.setting.utils.BaseBrightnessController
    public int getBrightness() {
        return BrightnessUtils.convertLinearToGamma(Settings.System.getIntForUser(this.f.getContentResolver(), "screen_brightness", this.c, -2), this.a, this.b);
    }

    @Override // com.xiaomi.micolauncher.module.setting.utils.BaseBrightnessController
    public void saveBrightness(int i) {
        final int convertGammaToLinear = BrightnessUtils.convertGammaToLinear(i, this.a, this.b);
        ThreadUtil.getIoThreadPool().submit(new Callable() { // from class: com.xiaomi.micolauncher.module.setting.utils.-$$Lambda$BrightnessControllerP$LnzfMpwIllBYUZSP1DhKCoKaTiI
            @Override // java.util.concurrent.Callable
            public final Object call() {
                Boolean d;
                d = BrightnessControllerP.this.d(convertGammaToLinear);
                return d;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean d(int i) throws Exception {
        return Boolean.valueOf(Settings.System.putIntForUser(this.f.getContentResolver(), "screen_brightness", i, -2));
    }

    @Override // com.xiaomi.micolauncher.module.setting.utils.BaseBrightnessController
    public void setTemporaryBrightness(int i) {
        try {
            this.g.getClass().getMethod("setTemporaryBrightness", Integer.TYPE).invoke(this.g, Integer.valueOf(BrightnessUtils.convertGammaToLinear(i, this.a, this.b)));
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            L.base.e("%s setTemporaryBrightness.exception=%s", "[BrightnessControllerP]: ", e);
        }
    }
}
