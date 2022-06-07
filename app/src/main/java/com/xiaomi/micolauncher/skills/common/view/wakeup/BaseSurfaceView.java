package com.xiaomi.micolauncher.skills.common.view.wakeup;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import com.xiaomi.micolauncher.common.L;

/* loaded from: classes3.dex */
public abstract class BaseSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    public static final int DEFAULT_FRAME_DURATION_MILLISECOND = 43;
    private HandlerThread a;
    private Canvas b;
    private a c;
    protected Handler drawHandler;
    protected int frameDuration = 43;
    protected volatile boolean isAlive;

    protected abstract int getDefaultHeight();

    protected abstract int getDefaultWidth();

    protected abstract void onFrameDraw(Canvas canvas);

    protected abstract void onFrameDrawFinish();

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
    }

    public BaseSurfaceView(Context context) {
        super(context);
        init();
    }

    public BaseSurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public BaseSurfaceView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    protected int getFrameDuration() {
        return this.frameDuration;
    }

    protected void setFrameDuration(int i) {
        this.frameDuration = i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void init() {
        getHolder().addCallback(this);
        a();
    }

    private void a() {
        getHolder().setFormat(-3);
        setZOrderOnTop(true);
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        L.wakeup.d("surfaceCreated");
        this.isAlive = true;
        b();
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        L.wakeup.d("surfaceDestroyed");
        stopDrawThread();
    }

    protected void stopDrawThread() {
        this.isAlive = false;
        Handler handler = this.drawHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            this.drawHandler.removeCallbacks(this.c);
        }
    }

    private void b() {
        if (this.a == null) {
            this.a = new HandlerThread("SurfaceViewThread");
            this.a.start();
        }
        if (this.drawHandler == null) {
            this.drawHandler = new Handler(this.a.getLooper());
        }
        if (this.c == null) {
            this.c = new a();
        }
        this.drawHandler.post(this.c);
    }

    @Override // android.view.SurfaceView, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        if (mode == Integer.MIN_VALUE) {
            measuredWidth = getDefaultWidth();
        }
        if (mode2 == Integer.MIN_VALUE) {
            measuredHeight = getDefaultHeight();
        }
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class a implements Runnable {
        private a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (BaseSurfaceView.this.isAlive && BaseSurfaceView.this.drawHandler != null) {
                try {
                    try {
                        try {
                            BaseSurfaceView.this.b = BaseSurfaceView.this.getHolder().lockCanvas();
                            BaseSurfaceView.this.onFrameDraw(BaseSurfaceView.this.b);
                            BaseSurfaceView.this.getHolder().unlockCanvasAndPost(BaseSurfaceView.this.b);
                            BaseSurfaceView.this.onFrameDrawFinish();
                        } catch (Exception e) {
                            e.printStackTrace();
                            BaseSurfaceView.this.getHolder().unlockCanvasAndPost(BaseSurfaceView.this.b);
                            BaseSurfaceView.this.onFrameDrawFinish();
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    if (BaseSurfaceView.this.a != null && BaseSurfaceView.this.a.isAlive()) {
                        BaseSurfaceView.this.drawHandler.postDelayed(this, BaseSurfaceView.this.frameDuration);
                    }
                } catch (Throwable th) {
                    try {
                        BaseSurfaceView.this.getHolder().unlockCanvasAndPost(BaseSurfaceView.this.b);
                        BaseSurfaceView.this.onFrameDrawFinish();
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                    throw th;
                }
            }
        }
    }
}
