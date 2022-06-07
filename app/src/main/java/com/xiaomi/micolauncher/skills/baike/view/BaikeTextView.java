package com.xiaomi.micolauncher.skills.baike.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class BaikeTextView extends View {
    public static final int DELAY_MILLIS = 800;
    public static final String LINE_FEED = "\n";
    public static final int REFRESH_INTERVAL = 20;
    private boolean a;
    private int b;
    private Paint c;
    private String d;
    private ArrayList<String> e;
    private long f;
    private double g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private boolean m;
    private Handler n;
    private CompleteListener o;

    /* loaded from: classes3.dex */
    public interface CompleteListener {
        void onComplete();
    }

    public BaikeTextView(Context context) {
        this(context, null);
    }

    public BaikeTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.c = new Paint();
        this.a = true;
        this.c.setStyle(Paint.Style.FILL);
        this.c.setAntiAlias(true);
        this.d = "";
        this.e = new ArrayList<>();
        this.g = 20000.0d;
        this.j = 49;
        int i = this.j;
        this.k = i;
        this.l = 20;
        this.c.setTextSize(i);
        this.m = false;
        this.n = new Handler();
        this.o = null;
    }

    public void setFontSize(int i) {
        if (i >= 0) {
            this.j = i;
            this.c.setTextSize(i);
            this.k = i;
        }
    }

    public void setRowInterval(int i) {
        if (i >= 0) {
            this.l = i;
        }
    }

    public void setText(String str) {
        this.d = str;
        setInterval(str);
        this.f = System.currentTimeMillis();
        this.h = 0;
        this.i = 0;
        a();
    }

    public void updateText(String str) {
        this.d = str;
        setInterval(str);
        this.a = true;
        this.f = System.currentTimeMillis();
        this.h = 0;
        this.i = 0;
        a();
        onSizeChanged(getWidth(), getHeight(), getWidth(), getHeight());
    }

    public void stopUpdate() {
        this.a = false;
    }

    public void setCompleteListener(CompleteListener completeListener) {
        this.o = completeListener;
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i == 0 || i2 == 0) {
            L.skill.w("onSizeChanged size not ready");
            return;
        }
        this.b = i2;
        this.e.clear();
        int i5 = 0;
        int length = this.d.length();
        int i6 = 1;
        while (i6 < length) {
            int i7 = i6 - 1;
            if (this.d.substring(i7, i6).equals("\n")) {
                this.e.add(this.d.substring(i5, i7));
                i6++;
                i5 = i6;
            } else if (this.c.measureText(this.d, i5, i6) > i - 7) {
                this.e.add(this.d.substring(i5, i7));
                i5 = i7;
            } else {
                i6++;
            }
        }
        if (i5 != i6) {
            this.e.add(this.d.substring(i5));
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int size = this.e.size();
        if (size != 0) {
            int currentTimeMillis = (int) (((System.currentTimeMillis() - this.f) / this.g) * size);
            if (currentTimeMillis == 0) {
                this.h = 0;
                this.i = 0;
            }
            this.c.setTextSize(this.j);
            canvas.save();
            int i = this.h;
            int i2 = this.k + this.l;
            if (currentTimeMillis >= 2) {
                this.h = ((currentTimeMillis - 2) + 1) * i2;
                int i3 = i2 * size;
                int i4 = i3 - this.h;
                int i5 = this.b;
                if (i4 < i5) {
                    this.h = (i3 - i5) + 5;
                    if (this.h < 0) {
                        this.h = 0;
                    }
                }
            } else {
                this.h = 0;
            }
            if (i != this.h) {
                this.n.postDelayed(new Runnable() { // from class: com.xiaomi.micolauncher.skills.baike.view.-$$Lambda$BaikeTextView$6okQM7cG719BoJDtcNseeRoKFco
                    @Override // java.lang.Runnable
                    public final void run() {
                        BaikeTextView.this.d();
                    }
                }, 800L);
            }
            if (this.i != this.h && this.m) {
                c();
            }
            int i6 = 0;
            while (i6 < size) {
                this.c.setColor(getContext().getColor(currentTimeMillis >= i6 ? R.color.baike_content_title_1 : R.color.baike_content_title_2));
                canvas.drawText(this.e.get(i6), 0.0f, (((-this.i) + (i2 * i6)) + 60) - 5, this.c);
                i6++;
            }
            canvas.restore();
            if (currentTimeMillis == size - 1) {
                this.a = false;
                CompleteListener completeListener = this.o;
                if (completeListener != null) {
                    completeListener.onComplete();
                }
            }
        }
    }

    public /* synthetic */ void d() {
        this.m = true;
    }

    private void a() {
        ThreadUtil.postDelayedInMainThread(new $$Lambda$BaikeTextView$UU6g38GLsCLfDfjZAdUAG7u4Q(this), 20L);
    }

    public void b() {
        if (this.a) {
            invalidate();
            ThreadUtil.postDelayedInMainThread(new $$Lambda$BaikeTextView$UU6g38GLsCLfDfjZAdUAG7u4Q(this), 20L);
        }
    }

    private void c() {
        int i = this.i;
        int i2 = this.h;
        if (i > i2) {
            this.i = i - 3;
            if (this.i <= i2) {
                this.m = false;
            }
        } else if (i < i2) {
            this.i = i + 3;
            if (this.i >= i2) {
                this.m = false;
            }
        }
    }

    private void setInterval(String str) {
        this.g = str.length() * (ChildModeManager.getManager().isChildMode() ? 315 : 265);
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopUpdate();
    }
}
