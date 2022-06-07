package com.xiaomi.micolauncher.module.setting.utils;

import android.content.Context;
import com.xiaomi.micolauncher.UserBehaviorProxy;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.utils.BuildHelper;
import com.xiaomi.micolauncher.module.setting.utils.BaseBrightnessController;

/* loaded from: classes3.dex */
public class BrightnessControllerProxy implements BaseBrightnessController.Listener {
    private static volatile BrightnessControllerProxy a;
    private final BaseBrightnessController b;
    private Listener c;

    /* loaded from: classes3.dex */
    public interface Listener {
        void onChange(int i);

        void onRealChange(int i);
    }

    private BrightnessControllerProxy(Context context) {
        if (BuildHelper.isAndroidP()) {
            this.b = new BrightnessControllerP(context);
        } else if (BuildHelper.isAndroidO()) {
            this.b = new BrightnessControllerO(context);
        } else {
            throw new IllegalArgumentException("un-support platform!");
        }
        this.b.a(this);
        this.c = null;
    }

    private void c() {
        this.b.release();
    }

    public static BrightnessControllerProxy getInstance() {
        if (a == null) {
            synchronized (BrightnessControllerProxy.class) {
                if (a == null) {
                    a = new BrightnessControllerProxy(MicoApplication.getGlobalContext());
                }
            }
        }
        return a;
    }

    public static void destroy() {
        if (a != null) {
            a.c();
            a = null;
        }
    }

    public void setBrightnessMode(boolean z) {
        this.b.a(z);
    }

    public boolean isAutoMode() {
        return this.b.a();
    }

    public void setBrightness(int i, boolean z) {
        this.b.setTemporaryBrightness(i);
        if (z) {
            this.b.saveBrightness(i);
        }
    }

    public int getBrightness() {
        return this.b.getBrightness();
    }

    public void setBrightnessPercent(int i) {
        this.b.c(i);
    }

    public void upBrightness(int i) {
        this.b.a(i);
    }

    public void downBrightness(int i) {
        this.b.b(i);
    }

    public void a() {
        L.base.d("%s enterSleep", "[BrightnessControllerProxy]: ");
    }

    public void b() {
        L.base.d("%s exitSleep", "[BrightnessControllerProxy]: ");
    }

    public void setListener(Listener listener) {
        this.c = listener;
    }

    @Override // com.xiaomi.micolauncher.module.setting.utils.BaseBrightnessController.Listener
    public void onChange(int i) {
        UserBehaviorProxy.setUserBehavior(this.b.e, "brightnessChange");
        Listener listener = this.c;
        if (listener != null) {
            listener.onChange(i);
        }
    }
}
