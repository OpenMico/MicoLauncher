package com.google.android.exoplayer2.video.spherical;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.SurfaceTexture;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.WindowManager;
import androidx.annotation.AnyThread;
import androidx.annotation.BinderThread;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoFrameMetadataListener;
import com.google.android.exoplayer2.video.spherical.OrientationListener;
import com.google.android.exoplayer2.video.spherical.TouchTracker;
import com.umeng.analytics.pro.ai;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/* loaded from: classes2.dex */
public final class SphericalGLSurfaceView extends GLSurfaceView {
    private final CopyOnWriteArrayList<VideoSurfaceListener> a;
    private final SensorManager b;
    @Nullable
    private final Sensor c;
    private final OrientationListener d;
    private final Handler e;
    private final TouchTracker f;
    private final d g;
    @Nullable
    private SurfaceTexture h;
    @Nullable
    private Surface i;
    private boolean j;
    private boolean k;
    private boolean l;

    /* loaded from: classes2.dex */
    public interface VideoSurfaceListener {
        void onVideoSurfaceCreated(Surface surface);

        void onVideoSurfaceDestroyed(Surface surface);
    }

    public SphericalGLSurfaceView(Context context) {
        this(context, null);
    }

    public SphericalGLSurfaceView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a = new CopyOnWriteArrayList<>();
        this.e = new Handler(Looper.getMainLooper());
        this.b = (SensorManager) Assertions.checkNotNull(context.getSystemService(ai.ac));
        Sensor defaultSensor = Util.SDK_INT >= 18 ? this.b.getDefaultSensor(15) : null;
        this.c = defaultSensor == null ? this.b.getDefaultSensor(11) : defaultSensor;
        this.g = new d();
        a aVar = new a(this.g);
        this.f = new TouchTracker(context, aVar, 25.0f);
        this.d = new OrientationListener(((WindowManager) Assertions.checkNotNull((WindowManager) context.getSystemService("window"))).getDefaultDisplay(), this.f, aVar);
        this.j = true;
        setEGLContextClientVersion(2);
        setRenderer(aVar);
        setOnTouchListener(this.f);
    }

    public void addVideoSurfaceListener(VideoSurfaceListener videoSurfaceListener) {
        this.a.add(videoSurfaceListener);
    }

    public void removeVideoSurfaceListener(VideoSurfaceListener videoSurfaceListener) {
        this.a.remove(videoSurfaceListener);
    }

    @Nullable
    public Surface getVideoSurface() {
        return this.i;
    }

    public VideoFrameMetadataListener getVideoFrameMetadataListener() {
        return this.g;
    }

    public CameraMotionListener getCameraMotionListener() {
        return this.g;
    }

    public void setDefaultStereoMode(int i) {
        this.g.a(i);
    }

    public void setUseSensorRotation(boolean z) {
        this.j = z;
        a();
    }

    @Override // android.opengl.GLSurfaceView
    public void onResume() {
        super.onResume();
        this.k = true;
        a();
    }

    @Override // android.opengl.GLSurfaceView
    public void onPause() {
        this.k = false;
        a();
        super.onPause();
    }

    @Override // android.opengl.GLSurfaceView, android.view.SurfaceView, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.e.post(new Runnable() { // from class: com.google.android.exoplayer2.video.spherical.-$$Lambda$SphericalGLSurfaceView$zF2u3W_aN5EXE_b0jURYZOe-sDU
            @Override // java.lang.Runnable
            public final void run() {
                SphericalGLSurfaceView.this.b();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b() {
        Surface surface = this.i;
        if (surface != null) {
            Iterator<VideoSurfaceListener> it = this.a.iterator();
            while (it.hasNext()) {
                it.next().onVideoSurfaceDestroyed(surface);
            }
        }
        a(this.h, surface);
        this.h = null;
        this.i = null;
    }

    private void a() {
        boolean z = this.j && this.k;
        Sensor sensor = this.c;
        if (sensor != null && z != this.l) {
            if (z) {
                this.b.registerListener(this.d, sensor, 0);
            } else {
                this.b.unregisterListener(this.d);
            }
            this.l = z;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final SurfaceTexture surfaceTexture) {
        this.e.post(new Runnable() { // from class: com.google.android.exoplayer2.video.spherical.-$$Lambda$SphericalGLSurfaceView$cBASUNN-tl6MWtd_UQf0nPJjFlA
            @Override // java.lang.Runnable
            public final void run() {
                SphericalGLSurfaceView.this.b(surfaceTexture);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(SurfaceTexture surfaceTexture) {
        SurfaceTexture surfaceTexture2 = this.h;
        Surface surface = this.i;
        Surface surface2 = new Surface(surfaceTexture);
        this.h = surfaceTexture;
        this.i = surface2;
        Iterator<VideoSurfaceListener> it = this.a.iterator();
        while (it.hasNext()) {
            it.next().onVideoSurfaceCreated(surface2);
        }
        a(surfaceTexture2, surface);
    }

    private static void a(@Nullable SurfaceTexture surfaceTexture, @Nullable Surface surface) {
        if (surfaceTexture != null) {
            surfaceTexture.release();
        }
        if (surface != null) {
            surface.release();
        }
    }

    @VisibleForTesting
    /* loaded from: classes2.dex */
    final class a implements GLSurfaceView.Renderer, OrientationListener.Listener, TouchTracker.Listener {
        private final d b;
        private float h;
        private final float[] c = new float[16];
        private final float[] d = new float[16];
        private final float[] e = new float[16];
        private final float[] f = new float[16];
        private final float[] g = new float[16];
        private final float[] j = new float[16];
        private final float[] k = new float[16];
        private float i = 3.1415927f;

        public a(d dVar) {
            this.b = dVar;
            Matrix.setIdentityM(this.e, 0);
            Matrix.setIdentityM(this.f, 0);
            Matrix.setIdentityM(this.g, 0);
        }

        @Override // android.opengl.GLSurfaceView.Renderer
        public synchronized void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
            SphericalGLSurfaceView.this.a(this.b.a());
        }

        @Override // android.opengl.GLSurfaceView.Renderer
        public void onSurfaceChanged(GL10 gl10, int i, int i2) {
            GLES20.glViewport(0, 0, i, i2);
            float f = i / i2;
            Matrix.perspectiveM(this.c, 0, a(f), f, 0.1f, 100.0f);
        }

        @Override // android.opengl.GLSurfaceView.Renderer
        public void onDrawFrame(GL10 gl10) {
            synchronized (this) {
                Matrix.multiplyMM(this.k, 0, this.e, 0, this.g, 0);
                Matrix.multiplyMM(this.j, 0, this.f, 0, this.k, 0);
            }
            Matrix.multiplyMM(this.d, 0, this.c, 0, this.j, 0);
            this.b.a(this.d, false);
        }

        @Override // com.google.android.exoplayer2.video.spherical.OrientationListener.Listener
        @BinderThread
        public synchronized void onOrientationChange(float[] fArr, float f) {
            System.arraycopy(fArr, 0, this.e, 0, this.e.length);
            this.i = -f;
            a();
        }

        @AnyThread
        private void a() {
            Matrix.setRotateM(this.f, 0, -this.h, (float) Math.cos(this.i), (float) Math.sin(this.i), 0.0f);
        }

        @Override // com.google.android.exoplayer2.video.spherical.TouchTracker.Listener
        @UiThread
        public synchronized void onScrollChange(PointF pointF) {
            this.h = pointF.y;
            a();
            Matrix.setRotateM(this.g, 0, -pointF.x, 0.0f, 1.0f, 0.0f);
        }

        @Override // com.google.android.exoplayer2.video.spherical.TouchTracker.Listener
        @UiThread
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return SphericalGLSurfaceView.this.performClick();
        }

        private float a(float f) {
            if (f > 1.0f) {
                return (float) (Math.toDegrees(Math.atan(Math.tan(Math.toRadians(45.0d)) / f)) * 2.0d);
            }
            return 90.0f;
        }
    }
}
