package com.xiaomi.miplay.mylibrary.mirror.wrappers;

import android.annotation.SuppressLint;
import android.os.IBinder;
import android.os.IInterface;
import com.xiaomi.idm.service.iot.InputMethodService;
import java.lang.reflect.Method;

@SuppressLint({"PrivateApi"})
/* loaded from: classes4.dex */
public final class ServiceManager {
    private final Method a;
    private WindowManager b;
    private DisplayManager c;
    private InputManager d;
    private PowerManager e;
    private StatusBarManager f;
    private ClipboardManager g;

    public ServiceManager() {
        try {
            this.a = Class.forName("android.os.ServiceManager").getDeclaredMethod("getService", String.class);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    private IInterface a(String str, String str2) {
        try {
            return (IInterface) Class.forName(str2 + "$Stub").getMethod("asInterface", IBinder.class).invoke(null, (IBinder) this.a.invoke(null, str));
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    public WindowManager getWindowManager() {
        if (this.b == null) {
            this.b = new WindowManager(a("window", "android.view.IWindowManager"));
        }
        return this.b;
    }

    public DisplayManager getDisplayManager() {
        if (this.c == null) {
            this.c = new DisplayManager(a("display", "android.hardware.display.IDisplayManager"));
        }
        return this.c;
    }

    public InputManager getInputManager() {
        if (this.d == null) {
            this.d = new InputManager(a(InputMethodService.InputPropertyCommand.INPUT_SERVICE_DESC, "android.hardware.input.IInputManager"));
        }
        return this.d;
    }

    public PowerManager getPowerManager() {
        if (this.e == null) {
            this.e = new PowerManager(a("power", "android.os.IPowerManager"));
        }
        return this.e;
    }

    public StatusBarManager getStatusBarManager() {
        if (this.f == null) {
            this.f = new StatusBarManager(a("statusbar", "com.android.internal.statusbar.IStatusBarService"));
        }
        return this.f;
    }

    public ClipboardManager getClipboardManager() {
        if (this.g == null) {
            this.g = new ClipboardManager(a("clipboard", "android.content.IClipboard"));
        }
        return this.g;
    }
}
