package com.xiaomi.micolauncher.common.player.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.xiaomi.micolauncher.common.player.opengl.ShaderRenderer;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;

/* loaded from: classes3.dex */
public class ShaderView extends GLSurfaceView {
    private ShaderRenderer a;
    private volatile boolean b = false;

    public ShaderView(Context context, int i) {
        super(context);
        a(context, i);
    }

    public ShaderView(Context context) {
        super(context);
        a(context, 1);
    }

    public ShaderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context, 1);
    }

    @Override // android.opengl.GLSurfaceView
    public void onPause() {
        if (this.b) {
            super.onPause();
            this.b = false;
        }
    }

    @Override // android.opengl.GLSurfaceView
    public void onResume() {
        if (!this.b) {
            super.onResume();
            this.b = true;
        }
    }

    @Override // android.view.View
    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.a.touchAt(motionEvent);
        return true;
    }

    public void setFragmentShader(String str, float f) {
        onPause();
        this.a.setFragmentShader(str, f);
        onResume();
    }

    public ShaderRenderer getRenderer() {
        return this.a;
    }

    private void a(Context context, int i) {
        this.a = new ShaderRenderer(context);
        setEGLContextClientVersion(2);
        setEGLContextFactory(new a(this.a));
        setRenderer(this.a);
        setRenderMode(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class a implements GLSurfaceView.EGLContextFactory {
        private static int a = 12440;
        private ShaderRenderer b;

        private a(ShaderRenderer shaderRenderer) {
            this.b = shaderRenderer;
        }

        @Override // android.opengl.GLSurfaceView.EGLContextFactory
        public EGLContext createContext(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig) {
            EGLContext eglCreateContext = egl10.eglCreateContext(eGLDisplay, eGLConfig, EGL10.EGL_NO_CONTEXT, new int[]{a, 3, 12344});
            if (eglCreateContext == null || eglCreateContext == EGL10.EGL_NO_CONTEXT || eglCreateContext.getGL() == null) {
                return egl10.eglCreateContext(eGLDisplay, eGLConfig, EGL10.EGL_NO_CONTEXT, new int[]{a, 2, 12344});
            }
            this.b.setVersion(3);
            return eglCreateContext;
        }

        @Override // android.opengl.GLSurfaceView.EGLContextFactory
        public void destroyContext(EGL10 egl10, EGLDisplay eGLDisplay, EGLContext eGLContext) {
            egl10.eglDestroyContext(eGLDisplay, eGLContext);
        }
    }
}
