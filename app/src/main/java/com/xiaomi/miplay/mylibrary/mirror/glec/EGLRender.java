package com.xiaomi.miplay.mylibrary.mirror.glec;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLExt;
import android.opengl.EGLSurface;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.Surface;
import com.xiaomi.mi_soundbox_command_sdk.Commands;
import com.xiaomi.miplay.mylibrary.mirror.Device;
import com.xiaomi.miplay.mylibrary.mirror.glec.CopySurfaceData;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes4.dex */
public class EGLRender implements SurfaceTexture.OnFrameAvailableListener {
    private boolean A;
    private int B;
    private int C;
    private HandlerThread D;
    private a E;
    private ReentrantLock F;
    private long G;
    private Context H;
    private Device I;
    private int J;
    private boolean K;
    private CopySurfaceData L;
    private int M;
    private boolean N;
    private BlackCheck O;
    private byte[] P;
    int a;
    int[] b;
    int[] c;
    int[] d;
    private STextureRender e;
    private SurfaceTexture f;
    private EGLDisplay g;
    private EGLContext h;
    private EGLContext i;
    private EGLSurface j;
    private EGLSurface k;
    private Surface l;
    private int m;
    private int n;
    private int o;
    private int p;
    private int q;
    private int r;
    private boolean s;
    private onFrameCallBack t;
    private volatile AtomicBoolean u;
    private volatile AtomicBoolean v;
    private final Object w;
    private long x;
    private long y;
    private long z;

    /* loaded from: classes4.dex */
    public interface onFrameCallBack {
        void onError();

        void onStop();
    }

    static /* synthetic */ int d(EGLRender eGLRender) {
        int i = eGLRender.M;
        eGLRender.M = i + 1;
        return i;
    }

    static /* synthetic */ int s(EGLRender eGLRender) {
        int i = eGLRender.C;
        eGLRender.C = i + 1;
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(Context context) {
        return (context == null || context.getResources().getConfiguration().orientation == 1) ? false : true;
    }

    public void setStartTimeNs(long j) {
        this.y = j;
    }

    public void setCallBack(onFrameCallBack onframecallback) {
        this.t = onframecallback;
    }

    public EGLRender(Surface surface, int i, int i2, int i3, long j) {
        this.g = EGL14.EGL_NO_DISPLAY;
        this.h = EGL14.EGL_NO_CONTEXT;
        this.i = EGL14.EGL_NO_CONTEXT;
        this.j = EGL14.EGL_NO_SURFACE;
        this.k = EGL14.EGL_NO_SURFACE;
        this.s = false;
        this.u = new AtomicBoolean(false);
        this.v = new AtomicBoolean(false);
        this.w = new Object();
        this.x = 0L;
        this.y = 0L;
        this.z = 0L;
        this.A = true;
        this.B = 0;
        this.C = 0;
        this.F = new ReentrantLock();
        this.H = null;
        this.I = null;
        this.K = true;
        this.M = 0;
        this.N = false;
        this.O = null;
        this.P = null;
        this.a = 0;
        this.m = i;
        this.n = i2;
        this.o = i;
        this.p = i2;
        this.G = j;
        a(i3);
        a(surface);
        makeCurrent();
        b();
    }

    public EGLRender(Surface surface, int i, int i2, int i3, int i4, int i5, long j, Context context, Device device) {
        this.g = EGL14.EGL_NO_DISPLAY;
        this.h = EGL14.EGL_NO_CONTEXT;
        this.i = EGL14.EGL_NO_CONTEXT;
        this.j = EGL14.EGL_NO_SURFACE;
        this.k = EGL14.EGL_NO_SURFACE;
        this.s = false;
        this.u = new AtomicBoolean(false);
        this.v = new AtomicBoolean(false);
        this.w = new Object();
        this.x = 0L;
        this.y = 0L;
        this.z = 0L;
        this.A = true;
        this.B = 0;
        this.C = 0;
        this.F = new ReentrantLock();
        this.H = null;
        this.I = null;
        this.K = true;
        this.M = 0;
        this.N = false;
        this.O = null;
        this.P = null;
        this.a = 0;
        this.m = i;
        this.n = i2;
        this.o = i3;
        this.p = i4;
        this.G = j;
        this.H = context;
        this.I = device;
        a(i5);
        a(surface);
        makeCurrent();
        b();
        Context context2 = this.H;
        if (context2 != null) {
            this.J = context2.getResources().getConfiguration().orientation;
        } else {
            this.J = 1;
        }
        Log.i("MiplayQuickEglRender", "mSrcWidth:" + this.m + " mSrcHeight:" + this.n + " mTextureWidth:" + this.o + " mTextureHeight:" + this.p);
    }

    private void a(int i) {
        Log.d("MiplayQuickEglRender", "initFPs :" + i);
        this.q = i;
        this.r = 1000 / i;
    }

    private void a(Surface surface) {
        this.g = EGL14.eglGetDisplay(0);
        if (this.g != EGL14.EGL_NO_DISPLAY) {
            int[] iArr = new int[2];
            if (EGL14.eglInitialize(this.g, iArr, 0, iArr, 1)) {
                EGLConfig[] eGLConfigArr = new EGLConfig[1];
                if (EGL14.eglChooseConfig(this.g, new int[]{12324, 8, 12323, 8, 12322, 8, 12321, 8, 12352, 4, 12339, 1, 12344}, 0, eGLConfigArr, 0, eGLConfigArr.length, new int[1], 0)) {
                    EGLConfig b = b(2);
                    int[] iArr2 = {12440, 2, 12344};
                    this.h = EGL14.eglCreateContext(this.g, eGLConfigArr[0], EGL14.EGL_NO_CONTEXT, iArr2, 0);
                    a("eglCreateContext");
                    EGLContext eGLContext = this.h;
                    if (eGLContext != null) {
                        this.i = EGL14.eglCreateContext(this.g, b, eGLContext, iArr2, 0);
                        a("eglCreateContext");
                        if (this.i != null) {
                            int i = this.m;
                            this.j = EGL14.eglCreatePbufferSurface(this.g, eGLConfigArr[0], new int[]{12375, i, 12374, i, 12344}, 0);
                            a("eglCreatePbufferSurface");
                            if (this.j != null) {
                                this.k = EGL14.eglCreateWindowSurface(this.g, b, surface, new int[]{12344}, 0);
                                a("eglCreateWindowSurface");
                                if (this.k == null) {
                                    throw new RuntimeException("surface was null");
                                }
                                return;
                            }
                            throw new RuntimeException("surface was null");
                        }
                        throw new RuntimeException("null context2");
                    }
                    throw new RuntimeException("null context");
                }
                throw new RuntimeException("unable to find RGB888+recordable ES2 EGL config");
            }
            this.g = null;
            throw new RuntimeException("unable to initialize EGL14");
        }
        throw new RuntimeException("unable to get EGL14 display");
    }

    private void a() {
        EGL14.eglDestroyContext(this.g, this.h);
        EGL14.eglDestroyContext(this.g, this.i);
        EGL14.eglDestroySurface(this.g, this.j);
        EGL14.eglDestroySurface(this.g, this.k);
        EGL14.eglTerminate(this.g);
        this.h = EGL14.EGL_NO_CONTEXT;
        this.i = EGL14.EGL_NO_CONTEXT;
        this.g = EGL14.EGL_NO_DISPLAY;
        this.j = EGL14.EGL_NO_SURFACE;
        this.k = EGL14.EGL_NO_SURFACE;
    }

    public void setVideoHandleMode(int i) {
        if (this.e != null) {
            if (i <= 0 || i >= 3) {
                i = 3;
            }
            this.e.setVideoHandleMode(i);
        }
    }

    public void makeCurrent() {
        EGLDisplay eGLDisplay = this.g;
        EGLSurface eGLSurface = this.j;
        if (!EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, this.h)) {
            throw new RuntimeException("eglMakeCurrent failed");
        }
    }

    private void b() {
        this.e = new STextureRender(this.m, this.n, this.o, this.p, this.H);
        this.e.surfaceCreated();
        this.f = new SurfaceTexture(this.e.getTextureId());
        this.f.setDefaultBufferSize(this.m, this.n);
        this.f.setOnFrameAvailableListener(this);
        this.l = new Surface(this.f);
        if (this.K) {
            this.O = new BlackCheck(this.m, this.n, this.o, this.p, this.H);
            this.L = new CopySurfaceData(this.H);
            this.a = BlackCheck.getStatusBarHeight(this.H);
            this.b = new int[4];
            this.c = new int[4];
            this.d = new int[3];
            this.L.setMsurface(this.l, this.m, this.n, new CopySurfaceData.OnConvertBitmapListener() { // from class: com.xiaomi.miplay.mylibrary.mirror.glec.EGLRender.1
                @Override // com.xiaomi.miplay.mylibrary.mirror.glec.CopySurfaceData.OnConvertBitmapListener
                public void onSuccess(Bitmap bitmap) {
                    EGLRender.this.b[0] = 0;
                    EGLRender.this.b[1] = 0;
                    EGLRender.this.b[2] = 0;
                    EGLRender.this.b[3] = 0;
                    EGLRender.this.c[0] = 0;
                    EGLRender.this.c[1] = 0;
                    EGLRender.this.c[2] = 0;
                    EGLRender.this.c[3] = 0;
                    EGLRender.this.d[0] = 0;
                    EGLRender.this.d[1] = 0;
                    EGLRender.this.d[2] = 0;
                    EGLRender eGLRender = EGLRender.this;
                    if (eGLRender.a(eGLRender.H) || EGLRender.this.e == null) {
                        if (EGLRender.this.O != null && EGLRender.this.O.CheckBlackEdge(bitmap, EGLRender.this.b, EGLRender.this.d) && EGLRender.this.N) {
                            Log.i("MiplayQuickEglRender", "### left:" + EGLRender.this.b[0] + " right:" + EGLRender.this.b[1] + " top:" + EGLRender.this.b[2] + " bottom:" + EGLRender.this.b[3]);
                            StringBuilder sb = new StringBuilder();
                            sb.append("@@@ scale:");
                            sb.append(EGLRender.this.d[0]);
                            sb.append(" trans:");
                            sb.append(EGLRender.this.d[1]);
                            sb.append(" crop:");
                            sb.append(EGLRender.this.d[2]);
                            Log.i("MiplayQuickEglRender", sb.toString());
                        }
                        if (EGLRender.this.e != null) {
                            EGLRender.this.e.setTextureCropAttr(EGLRender.this.b[0], EGLRender.this.b[1], EGLRender.this.b[2], EGLRender.this.b[3], EGLRender.this.d[0], EGLRender.this.d[1], EGLRender.this.d[2]);
                        }
                        EGLRender.d(EGLRender.this);
                        return;
                    }
                    EGLRender.this.O.CheckBlackEdge(bitmap, EGLRender.this.c, EGLRender.this.d);
                    if (EGLRender.this.e != null) {
                        EGLRender.this.e.setTextureCropAttr(EGLRender.this.c[0], EGLRender.this.c[1], EGLRender.this.c[2], EGLRender.this.c[3], EGLRender.this.d[0], EGLRender.this.d[1], EGLRender.this.d[2]);
                    }
                    EGLRender.d(EGLRender.this);
                }

                @Override // com.xiaomi.miplay.mylibrary.mirror.glec.CopySurfaceData.OnConvertBitmapListener
                public void onFailure() {
                    Log.i("MiplayQuickEglRender", "@@@@@@@@@@@@@@ onFailure");
                }
            });
        }
    }

    public Surface getDecodeSurface() {
        return this.l;
    }

    private EGLConfig b(int i) {
        EGLConfig[] eGLConfigArr = new EGLConfig[1];
        if (EGL14.eglChooseConfig(this.g, new int[]{12324, 8, 12323, 8, 12322, 8, 12321, 8, 12352, i >= 3 ? 68 : 4, 12344, 0, 12344}, 0, eGLConfigArr, 0, eGLConfigArr.length, new int[1], 0)) {
            return eGLConfigArr[0];
        }
        Log.w("MiplayQuickEglRender", "unable to find RGB8888 / " + i + " EGLConfig");
        return null;
    }

    private void a(String str) {
        int eglGetError = EGL14.eglGetError();
        if (eglGetError != 12288) {
            throw new RuntimeException(str + ": EGL error: 0x" + Integer.toHexString(eglGetError));
        }
    }

    public void makeCurrent(int i) {
        if (i == 0) {
            EGLDisplay eGLDisplay = this.g;
            EGLSurface eGLSurface = this.j;
            if (!EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, this.h)) {
                throw new RuntimeException("eglMakeCurrent failed");
            }
            return;
        }
        EGLDisplay eGLDisplay2 = this.g;
        EGLSurface eGLSurface2 = this.k;
        if (!EGL14.eglMakeCurrent(eGLDisplay2, eGLSurface2, eGLSurface2, this.i)) {
            throw new RuntimeException("eglMakeCurrent failed");
        }
    }

    public void setPresentationTime(long j) {
        EGLExt.eglPresentationTimeANDROID(this.g, this.k, j + this.y);
        a("eglPresentationTimeANDROID");
    }

    public boolean swapBuffers() {
        boolean eglSwapBuffers = EGL14.eglSwapBuffers(this.g, this.k);
        a("eglSwapBuffers");
        return eglSwapBuffers;
    }

    @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        if (this.E == null || !this.u.get()) {
            Log.i("MiplayQuickEglRender", "onFrameAvailable error");
        } else {
            this.E.sendEmptyMessage(1);
        }
        this.s = true;
    }

    public void drawImage() {
        this.e.drawFrame();
    }

    public void releaseResource() {
        a();
        this.f.release();
        this.f = null;
        this.e = null;
        this.l.release();
        this.l = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(long j, long j2) {
        long j3 = (j2 - this.z) / this.r;
        if (((j3 - j) * 100) / j3 > 10) {
            Log.w("MiplayQuickEglRender", "frame underrun more than 10% !!!");
            if (this.A) {
                this.t.onError();
                this.A = false;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public class a extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what == 1) {
                try {
                    EGLRender.this.F.lock();
                    try {
                        if (EGLRender.this.v.get()) {
                            synchronized (EGLRender.this.w) {
                                Log.i("MiplayQuickEglRender", "mPauseControl start!");
                                EGLRender.this.w.wait();
                                Log.i("MiplayQuickEglRender", "mPauseControl resume!");
                            }
                        }
                    } catch (Exception e) {
                        Log.e("MiplayQuickEglRender", "error in draw image " + e);
                    }
                    if (EGLRender.this.u.get()) {
                        EGLRender.this.x = SystemClock.elapsedRealtime();
                        if (EGLRender.this.z <= 0) {
                            EGLRender.this.B += 200;
                            EGLRender.this.z = EGLRender.this.x;
                            EGLRender.this.makeCurrent(1);
                        }
                        EGLRender.this.f.updateTexImage();
                        if (EGLRender.this.K) {
                            EGLRender.this.L.convertLayoutToBitmap(EGLRender.this.m, EGLRender.this.n);
                        }
                        EGLRender.this.drawImage();
                        EGLRender.this.setPresentationTime(((EGLRender.this.x - EGLRender.this.z) + EGLRender.this.G) * 1000000);
                        EGLRender.this.swapBuffers();
                        EGLRender.s(EGLRender.this);
                        if (EGLRender.this.C > EGLRender.this.B) {
                            EGLRender.this.B += 200;
                            EGLRender.this.a(EGLRender.this.C, EGLRender.this.x);
                        }
                    }
                } finally {
                    EGLRender.this.F.unlock();
                }
            }
        }
    }

    public void start() {
        Log.d("MiplayQuickEglRender", "start");
        this.D = new HandlerThread("RenderThread");
        this.D.start();
        this.E = new a(this.D.getLooper());
        this.u.set(true);
    }

    public void stop() {
        Log.d("MiplayQuickEglRender", "stop");
        this.u.set(false);
        this.D.quitSafely();
        if (this.v.get()) {
            synchronized (this.w) {
                this.w.notify();
                Log.i("MiplayQuickEglRender", "mPauseControl notify");
            }
            this.v.set(false);
        }
        this.F.lock();
        this.F.unlock();
        this.t.onStop();
    }

    public void pause() {
        Log.d("MiplayQuickEglRender", "pause");
        this.v.set(true);
    }

    public void resume() {
        Log.d("MiplayQuickEglRender", Commands.RESUME);
        if (this.v.get()) {
            synchronized (this.w) {
                this.w.notify();
                Log.i("MiplayQuickEglRender", "mPauseControl notify");
            }
            this.v.set(false);
        }
    }
}
