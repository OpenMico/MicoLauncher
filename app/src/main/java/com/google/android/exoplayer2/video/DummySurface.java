package com.google.android.exoplayer2.video;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.Surface;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.EGLSurfaceTexture;
import com.google.android.exoplayer2.util.GlUtil;
import com.google.android.exoplayer2.util.Log;

@RequiresApi(17)
/* loaded from: classes2.dex */
public final class DummySurface extends Surface {
    private static int a;
    private static boolean b;
    private final a c;
    private boolean d;
    public final boolean secure;

    public static synchronized boolean isSecureSupported(Context context) {
        boolean z;
        synchronized (DummySurface.class) {
            z = true;
            if (!b) {
                a = a(context);
                b = true;
            }
            if (a == 0) {
                z = false;
            }
        }
        return z;
    }

    public static DummySurface newInstanceV17(Context context, boolean z) {
        int i = 0;
        Assertions.checkState(!z || isSecureSupported(context));
        a aVar = new a();
        if (z) {
            i = a;
        }
        return aVar.a(i);
    }

    private DummySurface(a aVar, SurfaceTexture surfaceTexture, boolean z) {
        super(surfaceTexture);
        this.c = aVar;
        this.secure = z;
    }

    @Override // android.view.Surface
    public void release() {
        super.release();
        synchronized (this.c) {
            if (!this.d) {
                this.c.a();
                this.d = true;
            }
        }
    }

    private static int a(Context context) {
        if (GlUtil.isProtectedContentExtensionSupported(context)) {
            return GlUtil.isSurfacelessContextExtensionSupported() ? 1 : 2;
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class a extends HandlerThread implements Handler.Callback {
        private EGLSurfaceTexture a;
        private Handler b;
        @Nullable
        private Error c;
        @Nullable
        private RuntimeException d;
        @Nullable
        private DummySurface e;

        public a() {
            super("ExoPlayer:DummySurface");
        }

        public DummySurface a(int i) {
            boolean z;
            start();
            this.b = new Handler(getLooper(), this);
            this.a = new EGLSurfaceTexture(this.b);
            synchronized (this) {
                z = false;
                this.b.obtainMessage(1, i, 0).sendToTarget();
                while (this.e == null && this.d == null && this.c == null) {
                    try {
                        wait();
                    } catch (InterruptedException unused) {
                        z = true;
                    }
                }
            }
            if (z) {
                Thread.currentThread().interrupt();
            }
            RuntimeException runtimeException = this.d;
            if (runtimeException == null) {
                Error error = this.c;
                if (error == null) {
                    return (DummySurface) Assertions.checkNotNull(this.e);
                }
                throw error;
            }
            throw runtimeException;
        }

        public void a() {
            Assertions.checkNotNull(this.b);
            this.b.sendEmptyMessage(2);
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            try {
                switch (message.what) {
                    case 1:
                        try {
                            b(message.arg1);
                            synchronized (this) {
                                notify();
                            }
                        } catch (Error e) {
                            Log.e("DummySurface", "Failed to initialize dummy surface", e);
                            this.c = e;
                            synchronized (this) {
                                notify();
                            }
                        } catch (RuntimeException e2) {
                            Log.e("DummySurface", "Failed to initialize dummy surface", e2);
                            this.d = e2;
                            synchronized (this) {
                                notify();
                            }
                        }
                        return true;
                    case 2:
                        try {
                            b();
                            return true;
                        } finally {
                            quit();
                        }
                    default:
                        return true;
                }
            } catch (Throwable th) {
                synchronized (this) {
                    notify();
                    throw th;
                }
            }
        }

        private void b(int i) {
            Assertions.checkNotNull(this.a);
            this.a.init(i);
            this.e = new DummySurface(this, this.a.getSurfaceTexture(), i != 0);
        }

        private void b() {
            Assertions.checkNotNull(this.a);
            this.a.release();
        }
    }
}
