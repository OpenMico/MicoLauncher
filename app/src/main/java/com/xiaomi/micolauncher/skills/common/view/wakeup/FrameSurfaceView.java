package com.xiaomi.micolauncher.skills.common.view.wakeup;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.common.DisplayUtils;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.R;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class FrameSurfaceView extends BaseSurfaceView {
    public static final int INFINITE = -1;
    public static final int INVALID_INDEX = Integer.MAX_VALUE;
    private Bitmap b;
    private int d;
    private BitmapFactory.Options f;
    private Rect h;
    private int j;
    private int k;
    private boolean l;
    private int a = 0;
    private List<String> c = new ArrayList();
    private int e = Integer.MAX_VALUE;
    private Paint g = new Paint();
    private Rect i = new Rect();

    @Override // com.xiaomi.micolauncher.skills.common.view.wakeup.BaseSurfaceView
    protected void onFrameDrawFinish() {
    }

    public FrameSurfaceView(Context context) {
        super(context);
    }

    public FrameSurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public FrameSurfaceView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setRepeatTimes(int i) {
        this.a = i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.skills.common.view.wakeup.BaseSurfaceView
    public void init() {
        super.init();
        this.f = new BitmapFactory.Options();
        this.f.inMutable = true;
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.i.set(0, 0, getWidth(), getHeight());
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.wakeup.BaseSurfaceView
    protected int getDefaultWidth() {
        return this.j;
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.wakeup.BaseSurfaceView
    protected int getDefaultHeight() {
        return this.k;
    }

    public void setDuration(int i) {
        setFrameDuration(i / this.c.size());
    }

    public void setbitmapNames(List<String> list) {
        this.d = 0;
        this.f = new BitmapFactory.Options();
        this.f.inMutable = true;
        if (list != null && list.size() != 0) {
            this.c = list;
            if (!this.l) {
                getBitmapDimension();
            }
            this.l = true;
        }
    }

    private void getBitmapDimension() {
        this.j = DisplayUtils.dip2px(getContext(), getResources().getDimension(R.dimen.wakeup_anim_width));
        this.k = DisplayUtils.dip2px(getContext(), getResources().getDimension(R.dimen.wakeup_height));
        this.h = new Rect(0, 0, this.j, this.k);
        requestLayout();
    }

    public void destroy() {
        this.l = false;
        stopDrawThread();
        this.d = 0;
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.wakeup.BaseSurfaceView
    protected void onFrameDraw(Canvas canvas) {
        b(canvas);
        if (d()) {
            if (!this.isAlive || (c() && this.a != -1)) {
                a();
            } else {
                a(canvas);
            }
        }
    }

    private void a(Canvas canvas) {
        if (ContainerUtil.isOutOfBound(this.d, this.c) && this.a == -1) {
            this.d = 0;
        }
        this.b = a(this.c.get(this.d), this.f);
        canvas.drawBitmap(this.b, this.h, this.i, this.g);
        this.d++;
        this.e++;
    }

    private void a() {
        b();
    }

    private void b() {
        this.e = Integer.MAX_VALUE;
    }

    private boolean c() {
        return this.e > this.c.size() - 1;
    }

    private boolean d() {
        return this.e != Integer.MAX_VALUE;
    }

    public void start() {
        e();
    }

    private void e() {
        this.isAlive = true;
        this.e = 0;
    }

    private void b(Canvas canvas) {
        if (canvas != null) {
            this.g.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            canvas.drawPaint(this.g);
            this.g.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        }
    }

    private Bitmap a(String str, BitmapFactory.Options options) {
        InputStream inputStream;
        options.inScaled = false;
        try {
            inputStream = getResources().getAssets().open(str);
        } catch (IOException e) {
            e.printStackTrace();
            inputStream = null;
        }
        Bitmap decodeStream = BitmapFactory.decodeStream(inputStream, null, options);
        if (!Hardware.isX6A()) {
            return decodeStream;
        }
        int i = options.outWidth;
        int i2 = options.outHeight;
        float dip2px = DisplayUtils.dip2px(getContext(), getResources().getDimension(R.dimen.wakeup_height)) / i2;
        Matrix matrix = new Matrix();
        matrix.postScale(dip2px, dip2px);
        return Bitmap.createBitmap(decodeStream, 0, 0, i, i2, matrix, true);
    }
}
