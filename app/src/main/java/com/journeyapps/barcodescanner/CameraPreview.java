package com.journeyapps.barcodescanner;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.ViewGroup;
import android.view.WindowManager;
import androidx.core.view.ViewCompat;
import com.google.zxing.client.android.R;
import com.journeyapps.barcodescanner.camera.CameraInstance;
import com.journeyapps.barcodescanner.camera.CameraParametersCallback;
import com.journeyapps.barcodescanner.camera.CameraSettings;
import com.journeyapps.barcodescanner.camera.CameraSurface;
import com.journeyapps.barcodescanner.camera.CenterCropStrategy;
import com.journeyapps.barcodescanner.camera.DisplayConfiguration;
import com.journeyapps.barcodescanner.camera.FitCenterStrategy;
import com.journeyapps.barcodescanner.camera.FitXYStrategy;
import com.journeyapps.barcodescanner.camera.PreviewScalingStrategy;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class CameraPreview extends ViewGroup {
    private static final String a = "CameraPreview";
    private CameraInstance b;
    private WindowManager c;
    private Handler d;
    private SurfaceView f;
    private TextureView g;
    private RotationListener i;
    private DisplayConfiguration l;
    private Size n;
    private Size o;
    private Rect p;
    private Size q;
    private boolean e = false;
    private boolean h = false;
    private int j = -1;
    private List<StateListener> k = new ArrayList();
    private CameraSettings m = new CameraSettings();
    private Rect r = null;
    private Rect s = null;
    private Size t = null;
    private double u = 0.1d;
    private PreviewScalingStrategy v = null;
    private boolean w = false;
    private final SurfaceHolder.Callback x = new SurfaceHolder.Callback() { // from class: com.journeyapps.barcodescanner.CameraPreview.2
        @Override // android.view.SurfaceHolder.Callback
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            CameraPreview.this.q = null;
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
            if (surfaceHolder == null) {
                Log.e(CameraPreview.a, "*** WARNING *** surfaceChanged() gave us a null surface!");
                return;
            }
            CameraPreview.this.q = new Size(i2, i3);
            CameraPreview.this.f();
        }
    };
    private final Handler.Callback y = new Handler.Callback() { // from class: com.journeyapps.barcodescanner.CameraPreview.3
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what == R.id.zxing_prewiew_size_ready) {
                CameraPreview.this.b((Size) message.obj);
                return true;
            } else if (message.what == R.id.zxing_camera_error) {
                Exception exc = (Exception) message.obj;
                if (!CameraPreview.this.isActive()) {
                    return false;
                }
                CameraPreview.this.pause();
                CameraPreview.this.A.cameraError(exc);
                return false;
            } else if (message.what != R.id.zxing_camera_closed) {
                return false;
            } else {
                CameraPreview.this.A.cameraClosed();
                return false;
            }
        }
    };
    private RotationCallback z = new RotationCallback() { // from class: com.journeyapps.barcodescanner.CameraPreview.4
        @Override // com.journeyapps.barcodescanner.RotationCallback
        public void onRotationChanged(int i) {
            CameraPreview.this.d.postDelayed(new Runnable() { // from class: com.journeyapps.barcodescanner.CameraPreview.4.1
                @Override // java.lang.Runnable
                public void run() {
                    CameraPreview.this.c();
                }
            }, 250L);
        }
    };
    private final StateListener A = new StateListener() { // from class: com.journeyapps.barcodescanner.CameraPreview.5
        @Override // com.journeyapps.barcodescanner.CameraPreview.StateListener
        public void previewSized() {
            for (StateListener stateListener : CameraPreview.this.k) {
                stateListener.previewSized();
            }
        }

        @Override // com.journeyapps.barcodescanner.CameraPreview.StateListener
        public void previewStarted() {
            for (StateListener stateListener : CameraPreview.this.k) {
                stateListener.previewStarted();
            }
        }

        @Override // com.journeyapps.barcodescanner.CameraPreview.StateListener
        public void previewStopped() {
            for (StateListener stateListener : CameraPreview.this.k) {
                stateListener.previewStopped();
            }
        }

        @Override // com.journeyapps.barcodescanner.CameraPreview.StateListener
        public void cameraError(Exception exc) {
            for (StateListener stateListener : CameraPreview.this.k) {
                stateListener.cameraError(exc);
            }
        }

        @Override // com.journeyapps.barcodescanner.CameraPreview.StateListener
        public void cameraClosed() {
            for (StateListener stateListener : CameraPreview.this.k) {
                stateListener.cameraClosed();
            }
        }
    };

    /* loaded from: classes2.dex */
    public interface StateListener {
        void cameraClosed();

        void cameraError(Exception exc);

        void previewSized();

        void previewStarted();

        void previewStopped();
    }

    public void previewStarted() {
    }

    @TargetApi(14)
    private TextureView.SurfaceTextureListener b() {
        return new TextureView.SurfaceTextureListener() { // from class: com.journeyapps.barcodescanner.CameraPreview.1
            @Override // android.view.TextureView.SurfaceTextureListener
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                return false;
            }

            @Override // android.view.TextureView.SurfaceTextureListener
            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
            }

            @Override // android.view.TextureView.SurfaceTextureListener
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
                onSurfaceTextureSizeChanged(surfaceTexture, i, i2);
            }

            @Override // android.view.TextureView.SurfaceTextureListener
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
                CameraPreview.this.q = new Size(i, i2);
                CameraPreview.this.f();
            }
        };
    }

    public CameraPreview(Context context) {
        super(context);
        a(context, null, 0, 0);
    }

    public CameraPreview(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context, attributeSet, 0, 0);
    }

    public CameraPreview(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context, attributeSet, i, 0);
    }

    private void a(Context context, AttributeSet attributeSet, int i, int i2) {
        if (getBackground() == null) {
            setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        }
        initializeAttributes(attributeSet);
        this.c = (WindowManager) context.getSystemService("window");
        this.d = new Handler(this.y);
        this.i = new RotationListener();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        d();
    }

    protected void initializeAttributes(AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.zxing_camera_preview);
        int dimension = (int) obtainStyledAttributes.getDimension(R.styleable.zxing_camera_preview_zxing_framing_rect_width, -1.0f);
        int dimension2 = (int) obtainStyledAttributes.getDimension(R.styleable.zxing_camera_preview_zxing_framing_rect_height, -1.0f);
        if (dimension > 0 && dimension2 > 0) {
            this.t = new Size(dimension, dimension2);
        }
        this.e = obtainStyledAttributes.getBoolean(R.styleable.zxing_camera_preview_zxing_use_texture_view, true);
        int integer = obtainStyledAttributes.getInteger(R.styleable.zxing_camera_preview_zxing_preview_scaling_strategy, -1);
        if (integer == 1) {
            this.v = new CenterCropStrategy();
        } else if (integer == 2) {
            this.v = new FitCenterStrategy();
        } else if (integer == 3) {
            this.v = new FitXYStrategy();
        }
        obtainStyledAttributes.recycle();
    }

    public void c() {
        if (isActive() && getDisplayRotation() != this.j) {
            pause();
            resume();
        }
    }

    private void d() {
        if (this.e) {
            this.g = new TextureView(getContext());
            this.g.setSurfaceTextureListener(b());
            addView(this.g);
            return;
        }
        this.f = new SurfaceView(getContext());
        this.f.getHolder().addCallback(this.x);
        addView(this.f);
    }

    public void addStateListener(StateListener stateListener) {
        this.k.add(stateListener);
    }

    private void e() {
        Size size;
        if (this.n == null || (size = this.o) == null || this.l == null) {
            this.s = null;
            this.r = null;
            this.p = null;
            throw new IllegalStateException("containerSize or previewSize is not set yet");
        }
        int i = size.width;
        int i2 = this.o.height;
        int i3 = this.n.width;
        int i4 = this.n.height;
        this.p = this.l.scalePreview(this.o);
        this.r = calculateFramingRect(new Rect(0, 0, i3, i4), this.p);
        Rect rect = new Rect(this.r);
        rect.offset(-this.p.left, -this.p.top);
        this.s = new Rect((rect.left * i) / this.p.width(), (rect.top * i2) / this.p.height(), (rect.right * i) / this.p.width(), (rect.bottom * i2) / this.p.height());
        if (this.s.width() <= 0 || this.s.height() <= 0) {
            this.s = null;
            this.r = null;
            Log.w(a, "Preview frame is too small");
            return;
        }
        this.A.previewSized();
    }

    public void setTorch(boolean z) {
        this.w = z;
        CameraInstance cameraInstance = this.b;
        if (cameraInstance != null) {
            cameraInstance.setTorch(z);
        }
    }

    public void changeCameraParameters(CameraParametersCallback cameraParametersCallback) {
        CameraInstance cameraInstance = this.b;
        if (cameraInstance != null) {
            cameraInstance.changeCameraParameters(cameraParametersCallback);
        }
    }

    private void a(Size size) {
        this.n = size;
        CameraInstance cameraInstance = this.b;
        if (cameraInstance != null && cameraInstance.getDisplayConfiguration() == null) {
            this.l = new DisplayConfiguration(getDisplayRotation(), size);
            this.l.setPreviewScalingStrategy(getPreviewScalingStrategy());
            this.b.setDisplayConfiguration(this.l);
            this.b.configureCamera();
            boolean z = this.w;
            if (z) {
                this.b.setTorch(z);
            }
        }
    }

    public void setPreviewScalingStrategy(PreviewScalingStrategy previewScalingStrategy) {
        this.v = previewScalingStrategy;
    }

    public PreviewScalingStrategy getPreviewScalingStrategy() {
        PreviewScalingStrategy previewScalingStrategy = this.v;
        if (previewScalingStrategy != null) {
            return previewScalingStrategy;
        }
        if (this.g != null) {
            return new CenterCropStrategy();
        }
        return new FitCenterStrategy();
    }

    public void b(Size size) {
        this.o = size;
        if (this.n != null) {
            e();
            requestLayout();
            f();
        }
    }

    protected Matrix calculateTextureTransform(Size size, Size size2) {
        float f;
        float f2 = size.width / size.height;
        float f3 = size2.width / size2.height;
        float f4 = 1.0f;
        if (f2 < f3) {
            f = f3 / f2;
        } else {
            f4 = f2 / f3;
            f = 1.0f;
        }
        Matrix matrix = new Matrix();
        matrix.setScale(f, f4);
        matrix.postTranslate((size.width - (size.width * f)) / 2.0f, (size.height - (size.height * f4)) / 2.0f);
        return matrix;
    }

    public void f() {
        Rect rect;
        Size size = this.q;
        if (size != null && this.o != null && (rect = this.p) != null) {
            if (this.f == null || !size.equals(new Size(rect.width(), this.p.height()))) {
                TextureView textureView = this.g;
                if (textureView != null && textureView.getSurfaceTexture() != null) {
                    if (this.o != null) {
                        this.g.setTransform(calculateTextureTransform(new Size(this.g.getWidth(), this.g.getHeight()), this.o));
                    }
                    a(new CameraSurface(this.g.getSurfaceTexture()));
                    return;
                }
                return;
            }
            a(new CameraSurface(this.f.getHolder()));
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    @SuppressLint({"DrawAllocation"})
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        a(new Size(i3 - i, i4 - i2));
        SurfaceView surfaceView = this.f;
        if (surfaceView != null) {
            Rect rect = this.p;
            if (rect == null) {
                surfaceView.layout(0, 0, getWidth(), getHeight());
            } else {
                surfaceView.layout(rect.left, this.p.top, this.p.right, this.p.bottom);
            }
        } else {
            TextureView textureView = this.g;
            if (textureView != null) {
                textureView.layout(0, 0, getWidth(), getHeight());
            }
        }
    }

    public Rect getFramingRect() {
        return this.r;
    }

    public Rect getPreviewFramingRect() {
        return this.s;
    }

    public CameraSettings getCameraSettings() {
        return this.m;
    }

    public void setCameraSettings(CameraSettings cameraSettings) {
        this.m = cameraSettings;
    }

    public void resume() {
        Util.validateMainThread();
        Log.d(a, "resume()");
        g();
        if (this.q != null) {
            f();
        } else {
            SurfaceView surfaceView = this.f;
            if (surfaceView != null) {
                surfaceView.getHolder().addCallback(this.x);
            } else {
                TextureView textureView = this.g;
                if (textureView != null) {
                    if (textureView.isAvailable()) {
                        b().onSurfaceTextureAvailable(this.g.getSurfaceTexture(), this.g.getWidth(), this.g.getHeight());
                    } else {
                        this.g.setSurfaceTextureListener(b());
                    }
                }
            }
        }
        requestLayout();
        this.i.listen(getContext(), this.z);
    }

    public void pause() {
        TextureView textureView;
        SurfaceView surfaceView;
        Util.validateMainThread();
        Log.d(a, "pause()");
        this.j = -1;
        CameraInstance cameraInstance = this.b;
        if (cameraInstance != null) {
            cameraInstance.close();
            this.b = null;
            this.h = false;
        } else {
            this.d.sendEmptyMessage(R.id.zxing_camera_closed);
        }
        if (this.q == null && (surfaceView = this.f) != null) {
            surfaceView.getHolder().removeCallback(this.x);
        }
        if (this.q == null && (textureView = this.g) != null) {
            textureView.setSurfaceTextureListener(null);
        }
        this.n = null;
        this.o = null;
        this.s = null;
        this.i.stop();
        this.A.previewStopped();
    }

    public void pauseAndWait() {
        CameraInstance cameraInstance = getCameraInstance();
        pause();
        long nanoTime = System.nanoTime();
        while (cameraInstance != null && !cameraInstance.isCameraClosed() && System.nanoTime() - nanoTime <= 2000000000) {
            try {
                Thread.sleep(1L);
            } catch (InterruptedException unused) {
                return;
            }
        }
    }

    public Size getFramingRectSize() {
        return this.t;
    }

    public void setFramingRectSize(Size size) {
        this.t = size;
    }

    public double getMarginFraction() {
        return this.u;
    }

    public void setMarginFraction(double d) {
        if (d < 0.5d) {
            this.u = d;
            return;
        }
        throw new IllegalArgumentException("The margin fraction must be less than 0.5");
    }

    public boolean isUseTextureView() {
        return this.e;
    }

    public void setUseTextureView(boolean z) {
        this.e = z;
    }

    protected boolean isActive() {
        return this.b != null;
    }

    private int getDisplayRotation() {
        return this.c.getDefaultDisplay().getRotation();
    }

    private void g() {
        if (this.b != null) {
            Log.w(a, "initCamera called twice");
            return;
        }
        this.b = createCameraInstance();
        this.b.setReadyHandler(this.d);
        this.b.open();
        this.j = getDisplayRotation();
    }

    protected CameraInstance createCameraInstance() {
        CameraInstance cameraInstance = new CameraInstance(getContext());
        cameraInstance.setCameraSettings(this.m);
        return cameraInstance;
    }

    private void a(CameraSurface cameraSurface) {
        if (!this.h && this.b != null) {
            Log.i(a, "Starting preview");
            this.b.setSurface(cameraSurface);
            this.b.startPreview();
            this.h = true;
            previewStarted();
            this.A.previewStarted();
        }
    }

    public CameraInstance getCameraInstance() {
        return this.b;
    }

    public boolean isPreviewActive() {
        return this.h;
    }

    protected Rect calculateFramingRect(Rect rect, Rect rect2) {
        Rect rect3 = new Rect(rect);
        rect3.intersect(rect2);
        if (this.t != null) {
            rect3.inset(Math.max(0, (rect3.width() - this.t.width) / 2), Math.max(0, (rect3.height() - this.t.height) / 2));
            return rect3;
        }
        int min = (int) Math.min(rect3.width() * this.u, rect3.height() * this.u);
        rect3.inset(min, min);
        if (rect3.height() > rect3.width()) {
            rect3.inset(0, (rect3.height() - rect3.width()) / 2);
        }
        return rect3;
    }

    @Override // android.view.View
    protected Parcelable onSaveInstanceState() {
        Parcelable onSaveInstanceState = super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        bundle.putParcelable("super", onSaveInstanceState);
        bundle.putBoolean("torch", this.w);
        return bundle;
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof Bundle)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        Bundle bundle = (Bundle) parcelable;
        super.onRestoreInstanceState(bundle.getParcelable("super"));
        setTorch(bundle.getBoolean("torch"));
    }

    public boolean isCameraClosed() {
        CameraInstance cameraInstance = this.b;
        return cameraInstance == null || cameraInstance.isCameraClosed();
    }
}
