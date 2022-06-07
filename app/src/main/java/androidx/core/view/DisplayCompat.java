package androidx.core.view;

import android.annotation.SuppressLint;
import android.app.UiModeManager;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.text.TextUtils;
import android.view.Display;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.util.Preconditions;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;

/* loaded from: classes.dex */
public final class DisplayCompat {
    private DisplayCompat() {
    }

    @NonNull
    public static ModeCompat getMode(@NonNull Context context, @NonNull Display display) {
        if (Build.VERSION.SDK_INT >= 23) {
            return b.a(context, display);
        }
        return new ModeCompat(b(context, display));
    }

    @NonNull
    private static Point b(@NonNull Context context, @NonNull Display display) {
        Point a2 = a(context, display);
        if (a2 != null) {
            return a2;
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= 17) {
            a.a(display, point);
        } else {
            display.getSize(point);
        }
        return point;
    }

    @NonNull
    @SuppressLint({"ArrayReturn"})
    public static ModeCompat[] getSupportedModes(@NonNull Context context, @NonNull Display display) {
        return Build.VERSION.SDK_INT >= 23 ? b.b(context, display) : new ModeCompat[]{getMode(context, display)};
    }

    private static Point a(@NonNull String str) throws NumberFormatException {
        String[] split = str.trim().split("x", -1);
        if (split.length == 2) {
            int parseInt = Integer.parseInt(split[0]);
            int parseInt2 = Integer.parseInt(split[1]);
            if (parseInt > 0 && parseInt2 > 0) {
                return new Point(parseInt, parseInt2);
            }
        }
        throw new NumberFormatException();
    }

    @Nullable
    private static String b(String str) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod(BluetoothConstants.GET, String.class).invoke(cls, str);
        } catch (Exception unused) {
            return null;
        }
    }

    private static boolean a(@NonNull Context context) {
        UiModeManager uiModeManager = (UiModeManager) context.getSystemService("uimode");
        return uiModeManager != null && uiModeManager.getCurrentModeType() == 4;
    }

    @Nullable
    private static Point a(@NonNull String str, @NonNull Display display) {
        if (display.getDisplayId() != 0) {
            return null;
        }
        String b2 = b(str);
        if (TextUtils.isEmpty(b2)) {
            return null;
        }
        try {
            return a(b2);
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    static Point a(@NonNull Context context, @NonNull Display display) {
        Point point;
        if (Build.VERSION.SDK_INT < 28) {
            point = a("sys.display-size", display);
        } else {
            point = a("vendor.display-size", display);
        }
        if (point != null) {
            return point;
        }
        if (!b(context) || !a(display)) {
            return null;
        }
        return new Point(3840, 2160);
    }

    private static boolean b(@NonNull Context context) {
        return a(context) && "Sony".equals(Build.MANUFACTURER) && Build.MODEL.startsWith("BRAVIA") && context.getPackageManager().hasSystemFeature("com.sony.dtv.hardware.panel.qfhd");
    }

    static boolean a(@NonNull Display display) {
        if (Build.VERSION.SDK_INT >= 23) {
            return b.a(display);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @RequiresApi(23)
    /* loaded from: classes.dex */
    public static class b {
        @NonNull
        static ModeCompat a(@NonNull Context context, @NonNull Display display) {
            Display.Mode mode = display.getMode();
            Point a = DisplayCompat.a(context, display);
            if (a == null || a(mode, a)) {
                return new ModeCompat(mode, true);
            }
            return new ModeCompat(mode, a);
        }

        @NonNull
        @SuppressLint({"ArrayReturn"})
        public static ModeCompat[] b(@NonNull Context context, @NonNull Display display) {
            ModeCompat modeCompat;
            Display.Mode[] supportedModes = display.getSupportedModes();
            ModeCompat[] modeCompatArr = new ModeCompat[supportedModes.length];
            Display.Mode mode = display.getMode();
            Point a = DisplayCompat.a(context, display);
            if (a == null || a(mode, a)) {
                for (int i = 0; i < supportedModes.length; i++) {
                    modeCompatArr[i] = new ModeCompat(supportedModes[i], a(supportedModes[i], mode));
                }
            } else {
                for (int i2 = 0; i2 < supportedModes.length; i2++) {
                    if (a(supportedModes[i2], mode)) {
                        modeCompat = new ModeCompat(supportedModes[i2], a);
                    } else {
                        modeCompat = new ModeCompat(supportedModes[i2], false);
                    }
                    modeCompatArr[i2] = modeCompat;
                }
            }
            return modeCompatArr;
        }

        static boolean a(@NonNull Display display) {
            Display.Mode mode = display.getMode();
            Display.Mode[] supportedModes = display.getSupportedModes();
            for (int i = 0; i < supportedModes.length; i++) {
                if (mode.getPhysicalHeight() < supportedModes[i].getPhysicalHeight() || mode.getPhysicalWidth() < supportedModes[i].getPhysicalWidth()) {
                    return false;
                }
            }
            return true;
        }

        static boolean a(Display.Mode mode, Point point) {
            return (mode.getPhysicalWidth() == point.x && mode.getPhysicalHeight() == point.y) || (mode.getPhysicalWidth() == point.y && mode.getPhysicalHeight() == point.x);
        }

        static boolean a(Display.Mode mode, Display.Mode mode2) {
            return mode.getPhysicalWidth() == mode2.getPhysicalWidth() && mode.getPhysicalHeight() == mode2.getPhysicalHeight();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @RequiresApi(17)
    /* loaded from: classes.dex */
    public static class a {
        static void a(Display display, Point point) {
            display.getRealSize(point);
        }
    }

    /* loaded from: classes.dex */
    public static final class ModeCompat {
        private final Display.Mode a;
        private final Point b;
        private final boolean c;

        ModeCompat(@NonNull Point point) {
            Preconditions.checkNotNull(point, "physicalSize == null");
            this.b = point;
            this.a = null;
            this.c = true;
        }

        @RequiresApi(23)
        ModeCompat(@NonNull Display.Mode mode, boolean z) {
            Preconditions.checkNotNull(mode, "mode == null, can't wrap a null reference");
            this.b = new Point(mode.getPhysicalWidth(), mode.getPhysicalHeight());
            this.a = mode;
            this.c = z;
        }

        @RequiresApi(23)
        ModeCompat(@NonNull Display.Mode mode, @NonNull Point point) {
            Preconditions.checkNotNull(mode, "mode == null, can't wrap a null reference");
            Preconditions.checkNotNull(point, "physicalSize == null");
            this.b = point;
            this.a = mode;
            this.c = true;
        }

        public int getPhysicalWidth() {
            return this.b.x;
        }

        public int getPhysicalHeight() {
            return this.b.y;
        }

        @Deprecated
        public boolean isNative() {
            return this.c;
        }

        @Nullable
        @RequiresApi(23)
        public Display.Mode toMode() {
            return this.a;
        }
    }
}
