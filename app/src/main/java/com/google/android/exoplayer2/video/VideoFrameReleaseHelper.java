package com.google.android.exoplayer2.video;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.Choreographer;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes2.dex */
public final class VideoFrameReleaseHelper {
    private final a a = new a();
    @Nullable
    private final DisplayHelper b;
    @Nullable
    private final c c;
    private boolean d;
    @Nullable
    private Surface e;
    private float f;
    private float g;
    private float h;
    private float i;
    private long j;
    private long k;
    private long l;
    private long m;
    private long n;
    private long o;
    private long p;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public interface DisplayHelper {

        /* loaded from: classes2.dex */
        public interface Listener {
            void onDefaultDisplayChanged(@Nullable Display display);
        }

        void a();

        void a(Listener listener);
    }

    public VideoFrameReleaseHelper(@Nullable Context context) {
        this.b = a(context);
        this.c = this.b != null ? c.a() : null;
        this.j = C.TIME_UNSET;
        this.k = C.TIME_UNSET;
        this.f = -1.0f;
        this.i = 1.0f;
    }

    public void onEnabled() {
        if (this.b != null) {
            ((c) Assertions.checkNotNull(this.c)).b();
            this.b.a(new DisplayHelper.Listener() { // from class: com.google.android.exoplayer2.video.-$$Lambda$VideoFrameReleaseHelper$m9Ulh1NgC6vriXHqmhaQ_OmXaXQ
                @Override // com.google.android.exoplayer2.video.VideoFrameReleaseHelper.DisplayHelper.Listener
                public final void onDefaultDisplayChanged(Display display) {
                    VideoFrameReleaseHelper.this.a(display);
                }
            });
        }
    }

    public void onStarted() {
        this.d = true;
        a();
        a(false);
    }

    public void onSurfaceChanged(@Nullable Surface surface) {
        if (surface instanceof DummySurface) {
            surface = null;
        }
        if (this.e != surface) {
            c();
            this.e = surface;
            a(true);
        }
    }

    public void onPositionReset() {
        a();
    }

    public void onPlaybackSpeed(float f) {
        this.i = f;
        a();
        a(false);
    }

    public void onFormatChanged(float f) {
        this.f = f;
        this.a.a();
        b();
    }

    public void onNextFrame(long j) {
        long j2 = this.m;
        if (j2 != -1) {
            this.o = j2;
            this.p = this.n;
        }
        this.l++;
        this.a.a(j * 1000);
        b();
    }

    public void onStopped() {
        this.d = false;
        c();
    }

    public void onDisabled() {
        DisplayHelper displayHelper = this.b;
        if (displayHelper != null) {
            displayHelper.a();
            ((c) Assertions.checkNotNull(this.c)).c();
        }
    }

    public long adjustReleaseTime(long j) {
        long j2;
        c cVar;
        if (this.o != -1 && this.a.b()) {
            long e = (((float) (this.a.e() * (this.l - this.o))) / this.i) + this.p;
            if (a(j, e)) {
                j2 = e;
                this.m = this.l;
                this.n = j2;
                cVar = this.c;
                if (cVar != null || this.j == C.TIME_UNSET) {
                    return j2;
                }
                long j3 = cVar.a;
                return j3 == C.TIME_UNSET ? j2 : a(j2, j3, this.j) - this.k;
            }
            a();
        }
        j2 = j;
        this.m = this.l;
        this.n = j2;
        cVar = this.c;
        if (cVar != null) {
        }
        return j2;
    }

    private void a() {
        this.l = 0L;
        this.o = -1L;
        this.m = -1L;
    }

    private static boolean a(long j, long j2) {
        return Math.abs(j - j2) <= 20000000;
    }

    private void b() {
        if (Util.SDK_INT >= 30 && this.e != null) {
            float f = this.a.b() ? this.a.f() : this.f;
            float f2 = this.g;
            if (f != f2) {
                int i = (f > (-1.0f) ? 1 : (f == (-1.0f) ? 0 : -1));
                boolean z = true;
                if (i != 0 && f2 != -1.0f) {
                    if (Math.abs(f - this.g) < (this.a.b() && (this.a.d() > 5000000000L ? 1 : (this.a.d() == 5000000000L ? 0 : -1)) >= 0 ? 0.02f : 1.0f)) {
                        z = false;
                    }
                } else if (i == 0 && this.a.c() < 30) {
                    z = false;
                }
                if (z) {
                    this.g = f;
                    a(false);
                }
            }
        }
    }

    private void a(boolean z) {
        if (Util.SDK_INT >= 30 && this.e != null) {
            float f = 0.0f;
            if (this.d) {
                float f2 = this.g;
                if (f2 != -1.0f) {
                    f = this.i * f2;
                }
            }
            if (z || this.h != f) {
                this.h = f;
                a(this.e, f);
            }
        }
    }

    private void c() {
        Surface surface;
        if (Util.SDK_INT >= 30 && (surface = this.e) != null && this.h != 0.0f) {
            this.h = 0.0f;
            a(surface, 0.0f);
        }
    }

    @RequiresApi(30)
    private static void a(Surface surface, float f) {
        try {
            surface.setFrameRate(f, f == 0.0f ? 0 : 1);
        } catch (IllegalStateException e) {
            Log.e("VideoFrameReleaseHelper", "Failed to call Surface.setFrameRate", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(@Nullable Display display) {
        if (display != null) {
            this.j = (long) (1.0E9d / display.getRefreshRate());
            this.k = (this.j * 80) / 100;
            return;
        }
        Log.w("VideoFrameReleaseHelper", "Unable to query display refresh rate");
        this.j = C.TIME_UNSET;
        this.k = C.TIME_UNSET;
    }

    private static long a(long j, long j2, long j3) {
        long j4;
        long j5 = j2 + (((j - j2) / j3) * j3);
        if (j <= j5) {
            j4 = j5 - j3;
        } else {
            j5 = j3 + j5;
            j4 = j5;
        }
        return j5 - j < j - j4 ? j5 : j4;
    }

    @Nullable
    private static DisplayHelper a(@Nullable Context context) {
        DisplayHelper displayHelper = null;
        if (context == null) {
            return null;
        }
        Context applicationContext = context.getApplicationContext();
        if (Util.SDK_INT >= 17) {
            displayHelper = b.a(applicationContext);
        }
        return displayHelper == null ? a.a(applicationContext) : displayHelper;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class a implements DisplayHelper {
        private final WindowManager a;

        @Override // com.google.android.exoplayer2.video.VideoFrameReleaseHelper.DisplayHelper
        public void a() {
        }

        @Nullable
        public static DisplayHelper a(Context context) {
            WindowManager windowManager = (WindowManager) context.getSystemService("window");
            if (windowManager != null) {
                return new a(windowManager);
            }
            return null;
        }

        private a(WindowManager windowManager) {
            this.a = windowManager;
        }

        @Override // com.google.android.exoplayer2.video.VideoFrameReleaseHelper.DisplayHelper
        public void a(DisplayHelper.Listener listener) {
            listener.onDefaultDisplayChanged(this.a.getDefaultDisplay());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @RequiresApi(17)
    /* loaded from: classes2.dex */
    public static final class b implements DisplayManager.DisplayListener, DisplayHelper {
        private final DisplayManager a;
        @Nullable
        private DisplayHelper.Listener b;

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayAdded(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayRemoved(int i) {
        }

        @Nullable
        public static DisplayHelper a(Context context) {
            DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
            if (displayManager != null) {
                return new b(displayManager);
            }
            return null;
        }

        private b(DisplayManager displayManager) {
            this.a = displayManager;
        }

        @Override // com.google.android.exoplayer2.video.VideoFrameReleaseHelper.DisplayHelper
        public void a(DisplayHelper.Listener listener) {
            this.b = listener;
            this.a.registerDisplayListener(this, Util.createHandlerForCurrentLooper());
            listener.onDefaultDisplayChanged(b());
        }

        @Override // com.google.android.exoplayer2.video.VideoFrameReleaseHelper.DisplayHelper
        public void a() {
            this.a.unregisterDisplayListener(this);
            this.b = null;
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayChanged(int i) {
            DisplayHelper.Listener listener = this.b;
            if (listener != null && i == 0) {
                listener.onDefaultDisplayChanged(b());
            }
        }

        private Display b() {
            return this.a.getDisplay(0);
        }
    }

    /* loaded from: classes2.dex */
    private static final class c implements Handler.Callback, Choreographer.FrameCallback {
        private static final c b = new c();
        private Choreographer e;
        private int f;
        public volatile long a = C.TIME_UNSET;
        private final HandlerThread d = new HandlerThread("ExoPlayer:FrameReleaseChoreographer");
        private final Handler c = Util.createHandler(this.d.getLooper(), this);

        public static c a() {
            return b;
        }

        private c() {
            this.d.start();
            this.c.sendEmptyMessage(0);
        }

        public void b() {
            this.c.sendEmptyMessage(1);
        }

        public void c() {
            this.c.sendEmptyMessage(2);
        }

        @Override // android.view.Choreographer.FrameCallback
        public void doFrame(long j) {
            this.a = j;
            ((Choreographer) Assertions.checkNotNull(this.e)).postFrameCallbackDelayed(this, 500L);
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    d();
                    return true;
                case 1:
                    e();
                    return true;
                case 2:
                    f();
                    return true;
                default:
                    return false;
            }
        }

        private void d() {
            this.e = Choreographer.getInstance();
        }

        private void e() {
            this.f++;
            if (this.f == 1) {
                ((Choreographer) Assertions.checkNotNull(this.e)).postFrameCallback(this);
            }
        }

        private void f() {
            this.f--;
            if (this.f == 0) {
                ((Choreographer) Assertions.checkNotNull(this.e)).removeFrameCallback(this);
                this.a = C.TIME_UNSET;
            }
        }
    }
}
