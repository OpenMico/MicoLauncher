package com.google.android.exoplayer2.util;

import android.graphics.SurfaceTexture;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@RequiresApi(17)
/* loaded from: classes2.dex */
public final class EGLSurfaceTexture implements SurfaceTexture.OnFrameAvailableListener, Runnable {
    public static final int SECURE_MODE_NONE = 0;
    public static final int SECURE_MODE_PROTECTED_PBUFFER = 2;
    public static final int SECURE_MODE_SURFACELESS_CONTEXT = 1;
    private static final int[] a = {12352, 4, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12325, 0, 12327, 12344, 12339, 4, 12344};
    private final Handler b;
    private final int[] c;
    @Nullable
    private final TextureImageListener d;
    @Nullable
    private EGLDisplay e;
    @Nullable
    private EGLContext f;
    @Nullable
    private EGLSurface g;
    @Nullable
    private SurfaceTexture h;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface SecureMode {
    }

    /* loaded from: classes2.dex */
    public interface TextureImageListener {
        void onFrameAvailable();
    }

    /* loaded from: classes2.dex */
    public static final class GlException extends RuntimeException {
        private GlException(String str) {
            super(str);
        }
    }

    public EGLSurfaceTexture(Handler handler) {
        this(handler, null);
    }

    public EGLSurfaceTexture(Handler handler, @Nullable TextureImageListener textureImageListener) {
        this.b = handler;
        this.d = textureImageListener;
        this.c = new int[1];
    }

    public void init(int i) {
        this.e = b();
        EGLConfig a2 = a(this.e);
        this.f = a(this.e, a2, i);
        this.g = a(this.e, a2, this.f, i);
        a(this.c);
        this.h = new SurfaceTexture(this.c[0]);
        this.h.setOnFrameAvailableListener(this);
    }

    /* JADX WARN: Finally extract failed */
    public void release() {
        this.b.removeCallbacks(this);
        try {
            if (this.h != null) {
                this.h.release();
                GLES20.glDeleteTextures(1, this.c, 0);
            }
            EGLDisplay eGLDisplay = this.e;
            if (eGLDisplay != null && !eGLDisplay.equals(EGL14.EGL_NO_DISPLAY)) {
                EGL14.eglMakeCurrent(this.e, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT);
            }
            EGLSurface eGLSurface = this.g;
            if (eGLSurface != null && !eGLSurface.equals(EGL14.EGL_NO_SURFACE)) {
                EGL14.eglDestroySurface(this.e, this.g);
            }
            EGLContext eGLContext = this.f;
            if (eGLContext != null) {
                EGL14.eglDestroyContext(this.e, eGLContext);
            }
            if (Util.SDK_INT >= 19) {
                EGL14.eglReleaseThread();
            }
            EGLDisplay eGLDisplay2 = this.e;
            if (eGLDisplay2 != null && !eGLDisplay2.equals(EGL14.EGL_NO_DISPLAY)) {
                EGL14.eglTerminate(this.e);
            }
            this.e = null;
            this.f = null;
            this.g = null;
            this.h = null;
        } catch (Throwable th) {
            EGLDisplay eGLDisplay3 = this.e;
            if (eGLDisplay3 != null && !eGLDisplay3.equals(EGL14.EGL_NO_DISPLAY)) {
                EGL14.eglMakeCurrent(this.e, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT);
            }
            EGLSurface eGLSurface2 = this.g;
            if (eGLSurface2 != null && !eGLSurface2.equals(EGL14.EGL_NO_SURFACE)) {
                EGL14.eglDestroySurface(this.e, this.g);
            }
            EGLContext eGLContext2 = this.f;
            if (eGLContext2 != null) {
                EGL14.eglDestroyContext(this.e, eGLContext2);
            }
            if (Util.SDK_INT >= 19) {
                EGL14.eglReleaseThread();
            }
            EGLDisplay eGLDisplay4 = this.e;
            if (eGLDisplay4 != null && !eGLDisplay4.equals(EGL14.EGL_NO_DISPLAY)) {
                EGL14.eglTerminate(this.e);
            }
            this.e = null;
            this.f = null;
            this.g = null;
            this.h = null;
            throw th;
        }
    }

    public SurfaceTexture getSurfaceTexture() {
        return (SurfaceTexture) Assertions.checkNotNull(this.h);
    }

    @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        this.b.post(this);
    }

    @Override // java.lang.Runnable
    public void run() {
        a();
        SurfaceTexture surfaceTexture = this.h;
        if (surfaceTexture != null) {
            try {
                surfaceTexture.updateTexImage();
            } catch (RuntimeException unused) {
            }
        }
    }

    private void a() {
        TextureImageListener textureImageListener = this.d;
        if (textureImageListener != null) {
            textureImageListener.onFrameAvailable();
        }
    }

    private static EGLDisplay b() {
        EGLDisplay eglGetDisplay = EGL14.eglGetDisplay(0);
        if (eglGetDisplay != null) {
            int[] iArr = new int[2];
            if (EGL14.eglInitialize(eglGetDisplay, iArr, 0, iArr, 1)) {
                return eglGetDisplay;
            }
            throw new GlException("eglInitialize failed");
        }
        throw new GlException("eglGetDisplay failed");
    }

    private static EGLConfig a(EGLDisplay eGLDisplay) {
        EGLConfig[] eGLConfigArr = new EGLConfig[1];
        int[] iArr = new int[1];
        boolean eglChooseConfig = EGL14.eglChooseConfig(eGLDisplay, a, 0, eGLConfigArr, 0, 1, iArr, 0);
        if (eglChooseConfig && iArr[0] > 0 && eGLConfigArr[0] != null) {
            return eGLConfigArr[0];
        }
        throw new GlException(Util.formatInvariant("eglChooseConfig failed: success=%b, numConfigs[0]=%d, configs[0]=%s", Boolean.valueOf(eglChooseConfig), Integer.valueOf(iArr[0]), eGLConfigArr[0]));
    }

    private static EGLContext a(EGLDisplay eGLDisplay, EGLConfig eGLConfig, int i) {
        int[] iArr;
        if (i == 0) {
            iArr = new int[]{12440, 2, 12344};
        } else {
            iArr = new int[]{12440, 2, 12992, 1, 12344};
        }
        EGLContext eglCreateContext = EGL14.eglCreateContext(eGLDisplay, eGLConfig, EGL14.EGL_NO_CONTEXT, iArr, 0);
        if (eglCreateContext != null) {
            return eglCreateContext;
        }
        throw new GlException("eglCreateContext failed");
    }

    private static EGLSurface a(EGLDisplay eGLDisplay, EGLConfig eGLConfig, EGLContext eGLContext, int i) {
        EGLSurface eGLSurface;
        int[] iArr;
        if (i == 1) {
            eGLSurface = EGL14.EGL_NO_SURFACE;
        } else {
            if (i == 2) {
                iArr = new int[]{12375, 1, 12374, 1, 12992, 1, 12344};
            } else {
                iArr = new int[]{12375, 1, 12374, 1, 12344};
            }
            eGLSurface = EGL14.eglCreatePbufferSurface(eGLDisplay, eGLConfig, iArr, 0);
            if (eGLSurface == null) {
                throw new GlException("eglCreatePbufferSurface failed");
            }
        }
        if (EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, eGLContext)) {
            return eGLSurface;
        }
        throw new GlException("eglMakeCurrent failed");
    }

    private static void a(int[] iArr) {
        GLES20.glGenTextures(1, iArr, 0);
        GlUtil.checkGlError();
    }
}
