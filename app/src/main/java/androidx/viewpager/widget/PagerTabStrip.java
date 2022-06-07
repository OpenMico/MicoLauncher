package androidx.viewpager.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

/* loaded from: classes.dex */
public class PagerTabStrip extends PagerTitleStrip {
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private final Paint m;
    private final Rect n;
    private int o;
    private boolean p;
    private boolean q;
    private int r;
    private boolean s;
    private float t;
    private float u;
    private int v;

    public PagerTabStrip(@NonNull Context context) {
        this(context, null);
    }

    public PagerTabStrip(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.m = new Paint();
        this.n = new Rect();
        this.o = 255;
        this.p = false;
        this.q = false;
        this.g = this.f;
        this.m.setColor(this.g);
        float f = context.getResources().getDisplayMetrics().density;
        this.h = (int) ((3.0f * f) + 0.5f);
        this.i = (int) ((6.0f * f) + 0.5f);
        this.j = (int) (64.0f * f);
        this.l = (int) ((16.0f * f) + 0.5f);
        this.r = (int) ((1.0f * f) + 0.5f);
        this.k = (int) ((f * 32.0f) + 0.5f);
        this.v = ViewConfiguration.get(context).getScaledTouchSlop();
        setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom());
        setTextSpacing(getTextSpacing());
        setWillNotDraw(false);
        this.b.setFocusable(true);
        this.b.setOnClickListener(new View.OnClickListener() { // from class: androidx.viewpager.widget.PagerTabStrip.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PagerTabStrip.this.a.setCurrentItem(PagerTabStrip.this.a.getCurrentItem() - 1);
            }
        });
        this.d.setFocusable(true);
        this.d.setOnClickListener(new View.OnClickListener() { // from class: androidx.viewpager.widget.PagerTabStrip.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PagerTabStrip.this.a.setCurrentItem(PagerTabStrip.this.a.getCurrentItem() + 1);
            }
        });
        if (getBackground() == null) {
            this.p = true;
        }
    }

    public void setTabIndicatorColor(@ColorInt int i) {
        this.g = i;
        this.m.setColor(this.g);
        invalidate();
    }

    public void setTabIndicatorColorResource(@ColorRes int i) {
        setTabIndicatorColor(ContextCompat.getColor(getContext(), i));
    }

    @ColorInt
    public int getTabIndicatorColor() {
        return this.g;
    }

    @Override // android.view.View
    public void setPadding(int i, int i2, int i3, int i4) {
        int i5 = this.i;
        if (i4 < i5) {
            i4 = i5;
        }
        super.setPadding(i, i2, i3, i4);
    }

    @Override // androidx.viewpager.widget.PagerTitleStrip
    public void setTextSpacing(int i) {
        int i2 = this.j;
        if (i < i2) {
            i = i2;
        }
        super.setTextSpacing(i);
    }

    @Override // android.view.View
    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        if (!this.q) {
            this.p = drawable == null;
        }
    }

    @Override // android.view.View
    public void setBackgroundColor(@ColorInt int i) {
        super.setBackgroundColor(i);
        if (!this.q) {
            this.p = (i & ViewCompat.MEASURED_STATE_MASK) == 0;
        }
    }

    @Override // android.view.View
    public void setBackgroundResource(@DrawableRes int i) {
        super.setBackgroundResource(i);
        if (!this.q) {
            this.p = i == 0;
        }
    }

    public void setDrawFullUnderline(boolean z) {
        this.p = z;
        this.q = true;
        invalidate();
    }

    public boolean getDrawFullUnderline() {
        return this.p;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.viewpager.widget.PagerTitleStrip
    public int getMinHeight() {
        return Math.max(super.getMinHeight(), this.k);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action != 0 && this.s) {
            return false;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        switch (action) {
            case 0:
                this.t = x;
                this.u = y;
                this.s = false;
                break;
            case 1:
                if (x >= this.c.getLeft() - this.l) {
                    if (x > this.c.getRight() + this.l) {
                        this.a.setCurrentItem(this.a.getCurrentItem() + 1);
                        break;
                    }
                } else {
                    this.a.setCurrentItem(this.a.getCurrentItem() - 1);
                    break;
                }
                break;
            case 2:
                if (Math.abs(x - this.t) > this.v || Math.abs(y - this.u) > this.v) {
                    this.s = true;
                    break;
                }
                break;
        }
        return true;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int left = this.c.getLeft() - this.l;
        int right = this.c.getRight() + this.l;
        this.m.setColor((this.o << 24) | (this.g & 16777215));
        float f = height;
        canvas.drawRect(left, height - this.h, right, f, this.m);
        if (this.p) {
            this.m.setColor((-16777216) | (this.g & 16777215));
            canvas.drawRect(getPaddingLeft(), height - this.r, getWidth() - getPaddingRight(), f, this.m);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.viewpager.widget.PagerTitleStrip
    public void a(int i, float f, boolean z) {
        Rect rect = this.n;
        int height = getHeight();
        int left = this.c.getLeft() - this.l;
        int right = this.c.getRight() + this.l;
        int i2 = height - this.h;
        rect.set(left, i2, right, height);
        super.a(i, f, z);
        this.o = (int) (Math.abs(f - 0.5f) * 2.0f * 255.0f);
        rect.union(this.c.getLeft() - this.l, i2, this.c.getRight() + this.l, height);
        invalidate(rect);
    }
}
