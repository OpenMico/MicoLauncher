package com.xiaomi.miplay.mylibrary.mirror;

import android.graphics.Rect;
import android.os.Build;
import android.os.IBinder;
import android.view.InputEvent;
import com.xiaomi.miplay.mylibrary.mirror.wrappers.ServiceManager;
import com.xiaomi.miplay.mylibrary.mirror.wrappers.SurfaceControl;
import com.xiaomi.miplay.mylibrary.mirror.wrappers.WindowManager;
import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes4.dex */
public final class Device {
    public static final int POWER_MODE_NORMAL = 2;
    public static final int POWER_MODE_OFF = 0;
    private final ServiceManager a = new ServiceManager();
    private ScreenInfo b;
    private RotationListener c;

    /* loaded from: classes4.dex */
    public interface RotationListener {
        void onRotationChanged(int i);
    }

    public Device(Options options) {
        this.b = a(options.getCrop(), options.getMaxSize(), options.getVideoSize());
    }

    public synchronized ScreenInfo getScreenInfo() {
        return this.b;
    }

    private ScreenInfo a(Rect rect, int i, Size size) {
        DisplayInfo displayInfo = this.a.getDisplayManager().getDisplayInfo();
        boolean z = true;
        if ((displayInfo.getRotation() & 1) == 0) {
            z = false;
        }
        Size size2 = displayInfo.getSize();
        Rect rect2 = new Rect(0, 0, size2.getWidth(), size2.getHeight());
        if (rect != null) {
            if (z) {
                rect = a(rect);
            }
            if (!rect2.intersect(rect)) {
                Ln.w("Crop rectangle (" + b(rect) + ") does not intersect device screen (" + b(size2.toRect()) + ")");
                rect2 = new Rect();
            }
        }
        if (size.getWidth() <= 0 && size.getHeight() <= 0) {
            size = a(rect2.width(), rect2.height(), i);
        }
        return new ScreenInfo(rect2, size, z);
    }

    private static String b(Rect rect) {
        return rect.width() + Constants.COLON_SEPARATOR + rect.height() + Constants.COLON_SEPARATOR + rect.left + Constants.COLON_SEPARATOR + rect.top;
    }

    private static Size a(int i, int i2, int i3) {
        int i4 = i & (-8);
        i4 = i2 & (-8);
        if (i3 > 0) {
            if (i3 % 8 == 0) {
                boolean z = i4 > i4;
                int i5 = z ? i4 : i4;
                if (!z) {
                    i4 = i4;
                }
                if (i5 > i3) {
                    i4 = (((i4 * i3) / i5) + 4) & (-8);
                } else {
                    i3 = i5;
                }
                i4 = z ? i4 : i3;
                if (z) {
                    i4 = i3;
                }
            } else {
                throw new AssertionError("Max size must be a multiple of 8");
            }
        }
        return new Size(i4, i4);
    }

    public Point getPhysicalPoint(Position position) {
        ScreenInfo screenInfo = getScreenInfo();
        Size videoSize = screenInfo.getVideoSize();
        if (!videoSize.equals(position.getScreenSize())) {
            return null;
        }
        Rect contentRect = screenInfo.getContentRect();
        Point point = position.getPoint();
        return new Point(contentRect.left + ((point.getX() * contentRect.width()) / videoSize.getWidth()), contentRect.top + ((point.getY() * contentRect.height()) / videoSize.getHeight()));
    }

    public static String getDeviceName() {
        return Build.MODEL;
    }

    public boolean injectInputEvent(InputEvent inputEvent, int i) {
        return this.a.getInputManager().injectInputEvent(inputEvent, i);
    }

    public boolean isScreenOn() {
        return this.a.getPowerManager().isScreenOn();
    }

    public synchronized void setRotationListener(RotationListener rotationListener) {
        this.c = rotationListener;
    }

    public void expandNotificationPanel() {
        this.a.getStatusBarManager().expandNotificationsPanel();
    }

    public void collapsePanels() {
        this.a.getStatusBarManager().collapsePanels();
    }

    public String getClipboardText() {
        CharSequence text = this.a.getClipboardManager().getText();
        if (text == null) {
            return null;
        }
        return text.toString();
    }

    public void setClipboardText(String str) {
        this.a.getClipboardManager().setText(str);
        Ln.i("Device clipboard set");
    }

    public void setScreenPowerMode(int i) {
        IBinder builtInDisplay = SurfaceControl.getBuiltInDisplay();
        if (builtInDisplay == null) {
            Ln.e("Could not get built-in display");
            return;
        }
        SurfaceControl.setDisplayPowerMode(builtInDisplay, i);
        StringBuilder sb = new StringBuilder();
        sb.append("Device screen turned ");
        sb.append(i == 0 ? "off" : "on");
        Ln.i(sb.toString());
    }

    public void rotateDevice() {
        WindowManager windowManager = this.a.getWindowManager();
        boolean z = !windowManager.isRotationFrozen();
        int rotation = (windowManager.getRotation() & 1) ^ 1;
        Ln.i("Device rotation requested: " + (rotation == 0 ? "portrait" : "landscape"));
        windowManager.freezeRotation(rotation);
        if (z) {
            windowManager.thawRotation();
        }
    }

    public static Rect a(Rect rect) {
        return new Rect(rect.top, rect.left, rect.bottom, rect.right);
    }
}
