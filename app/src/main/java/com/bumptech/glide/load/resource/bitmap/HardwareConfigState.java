package com.bumptech.glide.load.resource.bitmap;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.util.Util;
import java.io.File;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public final class HardwareConfigState {
    public static final boolean BLOCK_HARDWARE_BITMAPS_WHEN_GL_CONTEXT_MIGHT_NOT_BE_INITIALIZED;
    public static final boolean HARDWARE_BITMAPS_SUPPORTED;
    public static final int NO_MAX_FD_COUNT = -1;
    private static final File a;
    private static volatile HardwareConfigState b;
    private static volatile int c;
    private final int e;
    private final int f;
    @GuardedBy("this")
    private int g;
    @GuardedBy("this")
    private boolean h = true;
    private final AtomicBoolean i = new AtomicBoolean(false);
    private final boolean d = b();

    static {
        boolean z = true;
        BLOCK_HARDWARE_BITMAPS_WHEN_GL_CONTEXT_MIGHT_NOT_BE_INITIALIZED = Build.VERSION.SDK_INT < 29;
        if (Build.VERSION.SDK_INT < 26) {
            z = false;
        }
        HARDWARE_BITMAPS_SUPPORTED = z;
        a = new File("/proc/self/fd");
        c = -1;
    }

    public static HardwareConfigState getInstance() {
        if (b == null) {
            synchronized (HardwareConfigState.class) {
                if (b == null) {
                    b = new HardwareConfigState();
                }
            }
        }
        return b;
    }

    @VisibleForTesting
    HardwareConfigState() {
        if (Build.VERSION.SDK_INT >= 28) {
            this.e = 20000;
            this.f = 0;
            return;
        }
        this.e = 700;
        this.f = 128;
    }

    public boolean areHardwareBitmapsBlocked() {
        Util.assertMainThread();
        return !this.i.get();
    }

    public void blockHardwareBitmaps() {
        Util.assertMainThread();
        this.i.set(false);
    }

    public void unblockHardwareBitmaps() {
        Util.assertMainThread();
        this.i.set(true);
    }

    public boolean isHardwareConfigAllowed(int i, int i2, boolean z, boolean z2) {
        if (!z) {
            if (Log.isLoggable("HardwareConfig", 2)) {
                Log.v("HardwareConfig", "Hardware config disallowed by caller");
            }
            return false;
        } else if (!this.d) {
            if (Log.isLoggable("HardwareConfig", 2)) {
                Log.v("HardwareConfig", "Hardware config disallowed by device model");
            }
            return false;
        } else if (!HARDWARE_BITMAPS_SUPPORTED) {
            if (Log.isLoggable("HardwareConfig", 2)) {
                Log.v("HardwareConfig", "Hardware config disallowed by sdk");
            }
            return false;
        } else if (a()) {
            if (Log.isLoggable("HardwareConfig", 2)) {
                Log.v("HardwareConfig", "Hardware config disallowed by app state");
            }
            return false;
        } else if (z2) {
            if (Log.isLoggable("HardwareConfig", 2)) {
                Log.v("HardwareConfig", "Hardware config disallowed because exif orientation is required");
            }
            return false;
        } else {
            int i3 = this.f;
            if (i < i3) {
                if (Log.isLoggable("HardwareConfig", 2)) {
                    Log.v("HardwareConfig", "Hardware config disallowed because width is too small");
                }
                return false;
            } else if (i2 < i3) {
                if (Log.isLoggable("HardwareConfig", 2)) {
                    Log.v("HardwareConfig", "Hardware config disallowed because height is too small");
                }
                return false;
            } else if (f()) {
                return true;
            } else {
                if (Log.isLoggable("HardwareConfig", 2)) {
                    Log.v("HardwareConfig", "Hardware config disallowed because there are insufficient FDs");
                }
                return false;
            }
        }
    }

    private boolean a() {
        return BLOCK_HARDWARE_BITMAPS_WHEN_GL_CONTEXT_MIGHT_NOT_BE_INITIALIZED && !this.i.get();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @TargetApi(26)
    public boolean a(int i, int i2, BitmapFactory.Options options, boolean z, boolean z2) {
        boolean isHardwareConfigAllowed = isHardwareConfigAllowed(i, i2, z, z2);
        if (isHardwareConfigAllowed) {
            options.inPreferredConfig = Bitmap.Config.HARDWARE;
            options.inMutable = false;
        }
        return isHardwareConfigAllowed;
    }

    private static boolean b() {
        return !d() && !c();
    }

    private static boolean c() {
        if (Build.VERSION.SDK_INT != 27) {
            return false;
        }
        return Arrays.asList("LG-M250", "LG-M320", "LG-Q710AL", "LG-Q710PL", "LGM-K121K", "LGM-K121L", "LGM-K121S", "LGM-X320K", "LGM-X320L", "LGM-X320S", "LGM-X401L", "LGM-X401S", "LM-Q610.FG", "LM-Q610.FGN", "LM-Q617.FG", "LM-Q617.FGN", "LM-Q710.FG", "LM-Q710.FGN", "LM-X220PM", "LM-X220QMA", "LM-X410PM").contains(Build.MODEL);
    }

    private static boolean d() {
        if (Build.VERSION.SDK_INT != 26) {
            return false;
        }
        for (String str : Arrays.asList("SC-04J", "SM-N935", "SM-J720", "SM-G570F", "SM-G570M", "SM-G960", "SM-G965", "SM-G935", "SM-G930", "SM-A520", "SM-A720F", "moto e5", "moto e5 play", "moto e5 plus", "moto e5 cruise", "moto g(6) forge", "moto g(6) play")) {
            if (Build.MODEL.startsWith(str)) {
                return true;
            }
        }
        return false;
    }

    private int e() {
        if (c != -1) {
            return c;
        }
        return this.e;
    }

    private synchronized boolean f() {
        int i = this.g + 1;
        this.g = i;
        if (i >= 50) {
            boolean z = false;
            this.g = 0;
            int length = a.list().length;
            long e = e();
            if (length < e) {
                z = true;
            }
            this.h = z;
            if (!this.h && Log.isLoggable("Downsampler", 5)) {
                Log.w("Downsampler", "Excluding HARDWARE bitmap config because we're over the file descriptor limit, file descriptors " + length + ", limit " + e);
            }
        }
        return this.h;
    }
}
