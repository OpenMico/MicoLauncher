package com.google.android.material.imageview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatImageView;
import com.google.android.material.R;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearancePathProvider;
import com.google.android.material.shape.Shapeable;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

/* loaded from: classes2.dex */
public class ShapeableImageView extends AppCompatImageView implements Shapeable {
    private static final int a = R.style.Widget_MaterialComponents_ShapeableImageView;
    private final ShapeAppearancePathProvider b;
    private final RectF c;
    private final RectF d;
    private final Paint e;
    private final Paint f;
    private final Path g;
    @Nullable
    private ColorStateList h;
    @Nullable
    private MaterialShapeDrawable i;
    private ShapeAppearanceModel j;
    @Dimension
    private float k;
    private Path l;
    @Dimension
    private int m;
    @Dimension
    private int n;
    @Dimension
    private int o;
    @Dimension
    private int p;
    @Dimension
    private int q;
    @Dimension
    private int r;
    private boolean s;

    public ShapeableImageView(Context context) {
        this(context, null, 0);
    }

    public ShapeableImageView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ShapeableImageView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, a), attributeSet, i);
        this.b = ShapeAppearancePathProvider.getInstance();
        this.g = new Path();
        this.s = false;
        Context context2 = getContext();
        this.f = new Paint();
        this.f.setAntiAlias(true);
        this.f.setColor(-1);
        this.f.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        this.c = new RectF();
        this.d = new RectF();
        this.l = new Path();
        TypedArray obtainStyledAttributes = context2.obtainStyledAttributes(attributeSet, R.styleable.ShapeableImageView, i, a);
        this.h = MaterialResources.getColorStateList(context2, obtainStyledAttributes, R.styleable.ShapeableImageView_strokeColor);
        this.k = obtainStyledAttributes.getDimensionPixelSize(R.styleable.ShapeableImageView_strokeWidth, 0);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.ShapeableImageView_contentPadding, 0);
        this.m = dimensionPixelSize;
        this.n = dimensionPixelSize;
        this.o = dimensionPixelSize;
        this.p = dimensionPixelSize;
        this.m = obtainStyledAttributes.getDimensionPixelSize(R.styleable.ShapeableImageView_contentPaddingLeft, dimensionPixelSize);
        this.n = obtainStyledAttributes.getDimensionPixelSize(R.styleable.ShapeableImageView_contentPaddingTop, dimensionPixelSize);
        this.o = obtainStyledAttributes.getDimensionPixelSize(R.styleable.ShapeableImageView_contentPaddingRight, dimensionPixelSize);
        this.p = obtainStyledAttributes.getDimensionPixelSize(R.styleable.ShapeableImageView_contentPaddingBottom, dimensionPixelSize);
        this.q = obtainStyledAttributes.getDimensionPixelSize(R.styleable.ShapeableImageView_contentPaddingStart, Integer.MIN_VALUE);
        this.r = obtainStyledAttributes.getDimensionPixelSize(R.styleable.ShapeableImageView_contentPaddingEnd, Integer.MIN_VALUE);
        obtainStyledAttributes.recycle();
        this.e = new Paint();
        this.e.setStyle(Paint.Style.STROKE);
        this.e.setAntiAlias(true);
        this.j = ShapeAppearanceModel.builder(context2, attributeSet, i, a).build();
        if (Build.VERSION.SDK_INT >= 21) {
            setOutlineProvider(new a());
        }
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDetachedFromWindow() {
        setLayerType(0, null);
        super.onDetachedFromWindow();
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setLayerType(2, null);
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (!this.s) {
            if (Build.VERSION.SDK_INT <= 19 || isLayoutDirectionResolved()) {
                this.s = true;
                if (Build.VERSION.SDK_INT < 21 || (!isPaddingRelative() && !a())) {
                    setPadding(super.getPaddingLeft(), super.getPaddingTop(), super.getPaddingRight(), super.getPaddingBottom());
                } else {
                    setPaddingRelative(super.getPaddingStart(), super.getPaddingTop(), super.getPaddingEnd(), super.getPaddingBottom());
                }
            }
        }
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(this.l, this.f);
        a(canvas);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        a(i, i2);
    }

    public void setContentPadding(@Dimension int i, @Dimension int i2, @Dimension int i3, @Dimension int i4) {
        this.q = Integer.MIN_VALUE;
        this.r = Integer.MIN_VALUE;
        super.setPadding((super.getPaddingLeft() - this.m) + i, (super.getPaddingTop() - this.n) + i2, (super.getPaddingRight() - this.o) + i3, (super.getPaddingBottom() - this.p) + i4);
        this.m = i;
        this.n = i2;
        this.o = i3;
        this.p = i4;
    }

    @RequiresApi(17)
    public void setContentPaddingRelative(@Dimension int i, @Dimension int i2, @Dimension int i3, @Dimension int i4) {
        super.setPaddingRelative((super.getPaddingStart() - getContentPaddingStart()) + i, (super.getPaddingTop() - this.n) + i2, (super.getPaddingEnd() - getContentPaddingEnd()) + i3, (super.getPaddingBottom() - this.p) + i4);
        this.m = b() ? i3 : i;
        this.n = i2;
        if (!b()) {
            i = i3;
        }
        this.o = i;
        this.p = i4;
    }

    private boolean a() {
        return (this.q == Integer.MIN_VALUE && this.r == Integer.MIN_VALUE) ? false : true;
    }

    @Dimension
    public int getContentPaddingBottom() {
        return this.p;
    }

    @Dimension
    public final int getContentPaddingEnd() {
        int i = this.r;
        return i != Integer.MIN_VALUE ? i : b() ? this.m : this.o;
    }

    @Dimension
    public int getContentPaddingLeft() {
        int i;
        int i2;
        if (a()) {
            if (b() && (i2 = this.r) != Integer.MIN_VALUE) {
                return i2;
            }
            if (!b() && (i = this.q) != Integer.MIN_VALUE) {
                return i;
            }
        }
        return this.m;
    }

    @Dimension
    public int getContentPaddingRight() {
        int i;
        int i2;
        if (a()) {
            if (b() && (i2 = this.q) != Integer.MIN_VALUE) {
                return i2;
            }
            if (!b() && (i = this.r) != Integer.MIN_VALUE) {
                return i;
            }
        }
        return this.o;
    }

    @Dimension
    public final int getContentPaddingStart() {
        int i = this.q;
        return i != Integer.MIN_VALUE ? i : b() ? this.o : this.m;
    }

    @Dimension
    public int getContentPaddingTop() {
        return this.n;
    }

    private boolean b() {
        return Build.VERSION.SDK_INT >= 17 && getLayoutDirection() == 1;
    }

    @Override // android.view.View
    public void setPadding(@Dimension int i, @Dimension int i2, @Dimension int i3, @Dimension int i4) {
        super.setPadding(i + getContentPaddingLeft(), i2 + getContentPaddingTop(), i3 + getContentPaddingRight(), i4 + getContentPaddingBottom());
    }

    @Override // android.view.View
    public void setPaddingRelative(@Dimension int i, @Dimension int i2, @Dimension int i3, @Dimension int i4) {
        super.setPaddingRelative(i + getContentPaddingStart(), i2 + getContentPaddingTop(), i3 + getContentPaddingEnd(), i4 + getContentPaddingBottom());
    }

    @Override // android.view.View
    @Dimension
    public int getPaddingBottom() {
        return super.getPaddingBottom() - getContentPaddingBottom();
    }

    @Override // android.view.View
    @Dimension
    public int getPaddingEnd() {
        return super.getPaddingEnd() - getContentPaddingEnd();
    }

    @Override // android.view.View
    @Dimension
    public int getPaddingLeft() {
        return super.getPaddingLeft() - getContentPaddingLeft();
    }

    @Override // android.view.View
    @Dimension
    public int getPaddingRight() {
        return super.getPaddingRight() - getContentPaddingRight();
    }

    @Override // android.view.View
    @Dimension
    public int getPaddingStart() {
        return super.getPaddingStart() - getContentPaddingStart();
    }

    @Override // android.view.View
    @Dimension
    public int getPaddingTop() {
        return super.getPaddingTop() - getContentPaddingTop();
    }

    @Override // com.google.android.material.shape.Shapeable
    public void setShapeAppearanceModel(@NonNull ShapeAppearanceModel shapeAppearanceModel) {
        this.j = shapeAppearanceModel;
        MaterialShapeDrawable materialShapeDrawable = this.i;
        if (materialShapeDrawable != null) {
            materialShapeDrawable.setShapeAppearanceModel(shapeAppearanceModel);
        }
        a(getWidth(), getHeight());
        invalidate();
        if (Build.VERSION.SDK_INT >= 21) {
            invalidateOutline();
        }
    }

    @Override // com.google.android.material.shape.Shapeable
    @NonNull
    public ShapeAppearanceModel getShapeAppearanceModel() {
        return this.j;
    }

    private void a(int i, int i2) {
        this.c.set(getPaddingLeft(), getPaddingTop(), i - getPaddingRight(), i2 - getPaddingBottom());
        this.b.calculatePath(this.j, 1.0f, this.c, this.g);
        this.l.rewind();
        this.l.addPath(this.g);
        this.d.set(0.0f, 0.0f, i, i2);
        this.l.addRect(this.d, Path.Direction.CCW);
    }

    private void a(Canvas canvas) {
        if (this.h != null) {
            this.e.setStrokeWidth(this.k);
            int colorForState = this.h.getColorForState(getDrawableState(), this.h.getDefaultColor());
            if (this.k > 0.0f && colorForState != 0) {
                this.e.setColor(colorForState);
                canvas.drawPath(this.g, this.e);
            }
        }
    }

    public void setStrokeColorResource(@ColorRes int i) {
        setStrokeColor(AppCompatResources.getColorStateList(getContext(), i));
    }

    @Nullable
    public ColorStateList getStrokeColor() {
        return this.h;
    }

    public void setStrokeWidth(@Dimension float f) {
        if (this.k != f) {
            this.k = f;
            invalidate();
        }
    }

    public void setStrokeWidthResource(@DimenRes int i) {
        setStrokeWidth(getResources().getDimensionPixelSize(i));
    }

    @Dimension
    public float getStrokeWidth() {
        return this.k;
    }

    public void setStrokeColor(@Nullable ColorStateList colorStateList) {
        this.h = colorStateList;
        invalidate();
    }

    @TargetApi(21)
    /* loaded from: classes2.dex */
    class a extends ViewOutlineProvider {
        private final Rect b = new Rect();

        a() {
        }

        @Override // android.view.ViewOutlineProvider
        public void getOutline(View view, Outline outline) {
            if (ShapeableImageView.this.j != null) {
                if (ShapeableImageView.this.i == null) {
                    ShapeableImageView shapeableImageView = ShapeableImageView.this;
                    shapeableImageView.i = new MaterialShapeDrawable(shapeableImageView.j);
                }
                ShapeableImageView.this.c.round(this.b);
                ShapeableImageView.this.i.setBounds(this.b);
                ShapeableImageView.this.i.getOutline(outline);
            }
        }
    }
}
