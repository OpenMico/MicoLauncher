package com.google.android.material.card;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shape.CornerTreatment;
import com.google.android.material.shape.CutCornerTreatment;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.RoundedCornerTreatment;
import com.google.android.material.shape.ShapeAppearanceModel;

/* compiled from: MaterialCardViewHelper.java */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes2.dex */
class a {
    private static final int[] a = {16842912};
    private static final double b = Math.cos(Math.toRadians(45.0d));
    @NonNull
    private final MaterialCardView c;
    @NonNull
    private final MaterialShapeDrawable e;
    @NonNull
    private final MaterialShapeDrawable f;
    @Dimension
    private int g;
    @Dimension
    private int h;
    @Dimension
    private int i;
    @Nullable
    private Drawable j;
    @Nullable
    private Drawable k;
    @Nullable
    private ColorStateList l;
    @Nullable
    private ColorStateList m;
    @Nullable
    private ShapeAppearanceModel n;
    @Nullable
    private ColorStateList o;
    @Nullable
    private Drawable p;
    @Nullable
    private LayerDrawable q;
    @Nullable
    private MaterialShapeDrawable r;
    @Nullable
    private MaterialShapeDrawable s;
    private boolean u;
    @NonNull
    private final Rect d = new Rect();
    private boolean t = false;

    public a(@NonNull MaterialCardView materialCardView, AttributeSet attributeSet, int i, @StyleRes int i2) {
        this.c = materialCardView;
        this.e = new MaterialShapeDrawable(materialCardView.getContext(), attributeSet, i, i2);
        this.e.initializeElevationOverlay(materialCardView.getContext());
        this.e.setShadowColor(-12303292);
        ShapeAppearanceModel.Builder builder = this.e.getShapeAppearanceModel().toBuilder();
        TypedArray obtainStyledAttributes = materialCardView.getContext().obtainStyledAttributes(attributeSet, R.styleable.CardView, i, R.style.CardView);
        if (obtainStyledAttributes.hasValue(R.styleable.CardView_cardCornerRadius)) {
            builder.setAllCornerSizes(obtainStyledAttributes.getDimension(R.styleable.CardView_cardCornerRadius, 0.0f));
        }
        this.f = new MaterialShapeDrawable();
        a(builder.build());
        obtainStyledAttributes.recycle();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(@NonNull TypedArray typedArray) {
        this.o = MaterialResources.getColorStateList(this.c.getContext(), typedArray, R.styleable.MaterialCardView_strokeColor);
        if (this.o == null) {
            this.o = ColorStateList.valueOf(-1);
        }
        this.i = typedArray.getDimensionPixelSize(R.styleable.MaterialCardView_strokeWidth, 0);
        this.u = typedArray.getBoolean(R.styleable.MaterialCardView_android_checkable, false);
        this.c.setLongClickable(this.u);
        this.m = MaterialResources.getColorStateList(this.c.getContext(), typedArray, R.styleable.MaterialCardView_checkedIconTint);
        a(MaterialResources.getDrawable(this.c.getContext(), typedArray, R.styleable.MaterialCardView_checkedIcon));
        b(typedArray.getDimensionPixelSize(R.styleable.MaterialCardView_checkedIconSize, 0));
        c(typedArray.getDimensionPixelSize(R.styleable.MaterialCardView_checkedIconMargin, 0));
        this.l = MaterialResources.getColorStateList(this.c.getContext(), typedArray, R.styleable.MaterialCardView_rippleColor);
        if (this.l == null) {
            this.l = ColorStateList.valueOf(MaterialColors.getColor(this.c, R.attr.colorControlHighlight));
        }
        c(MaterialResources.getColorStateList(this.c.getContext(), typedArray, R.styleable.MaterialCardView_cardForegroundColor));
        H();
        l();
        n();
        this.c.setBackgroundInternal(c(this.e));
        this.j = this.c.isClickable() ? E() : this.f;
        this.c.setForeground(c(this.j));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a() {
        return this.t;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(boolean z) {
        this.t = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(ColorStateList colorStateList) {
        if (this.o != colorStateList) {
            this.o = colorStateList;
            n();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @ColorInt
    public int b() {
        ColorStateList colorStateList = this.o;
        if (colorStateList == null) {
            return -1;
        }
        return colorStateList.getDefaultColor();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public ColorStateList c() {
        return this.o;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(@Dimension int i) {
        if (i != this.i) {
            this.i = i;
            n();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Dimension
    public int d() {
        return this.i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public MaterialShapeDrawable e() {
        return this.e;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(ColorStateList colorStateList) {
        this.e.setFillColor(colorStateList);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ColorStateList f() {
        return this.e.getFillColor();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void c(@Nullable ColorStateList colorStateList) {
        MaterialShapeDrawable materialShapeDrawable = this.f;
        if (colorStateList == null) {
            colorStateList = ColorStateList.valueOf(0);
        }
        materialShapeDrawable.setFillColor(colorStateList);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ColorStateList g() {
        return this.f.getFillColor();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(int i, int i2, int i3, int i4) {
        this.d.set(i, i2, i3, i4);
        o();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public Rect h() {
        return this.d;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void i() {
        Drawable drawable = this.j;
        this.j = this.c.isClickable() ? E() : this.f;
        Drawable drawable2 = this.j;
        if (drawable != drawable2) {
            b(drawable2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(float f) {
        a(this.n.withCornerSize(f));
        this.j.invalidateSelf();
        if (C() || B()) {
            o();
        }
        if (C()) {
            m();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float j() {
        return this.e.getTopLeftCornerResolvedSize();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(@FloatRange(from = 0.0d, to = 1.0d) float f) {
        this.e.setInterpolation(f);
        MaterialShapeDrawable materialShapeDrawable = this.f;
        if (materialShapeDrawable != null) {
            materialShapeDrawable.setInterpolation(f);
        }
        MaterialShapeDrawable materialShapeDrawable2 = this.s;
        if (materialShapeDrawable2 != null) {
            materialShapeDrawable2.setInterpolation(f);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @FloatRange(from = 0.0d, to = 1.0d)
    public float k() {
        return this.e.getInterpolation();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void l() {
        this.e.setElevation(this.c.getCardElevation());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void m() {
        if (!a()) {
            this.c.setBackgroundInternal(c(this.e));
        }
        this.c.setForeground(c(this.j));
    }

    void n() {
        this.f.setStroke(this.i, this.o);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void o() {
        int D = (int) ((B() || C() ? D() : 0.0f) - A());
        this.c.a(this.d.left + D, this.d.top + D, this.d.right + D, this.d.bottom + D);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(boolean z) {
        this.u = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean p() {
        return this.u;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void d(@Nullable ColorStateList colorStateList) {
        this.l = colorStateList;
        H();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void e(@Nullable ColorStateList colorStateList) {
        this.m = colorStateList;
        Drawable drawable = this.k;
        if (drawable != null) {
            DrawableCompat.setTintList(drawable, colorStateList);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public ColorStateList q() {
        return this.m;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public ColorStateList r() {
        return this.l;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public Drawable s() {
        return this.k;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(@Nullable Drawable drawable) {
        this.k = drawable;
        if (drawable != null) {
            this.k = DrawableCompat.wrap(drawable.mutate());
            DrawableCompat.setTintList(this.k, this.m);
        }
        if (this.q != null) {
            this.q.setDrawableByLayerId(R.id.mtrl_card_checked_layer_id, I());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Dimension
    public int t() {
        return this.h;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(@Dimension int i) {
        this.h = i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Dimension
    public int u() {
        return this.g;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void c(@Dimension int i) {
        this.g = i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(int i, int i2) {
        int i3;
        int i4;
        int i5;
        if (this.q != null) {
            int i6 = this.g;
            int i7 = this.h;
            int i8 = (i - i6) - i7;
            int i9 = (i2 - i6) - i7;
            if ((Build.VERSION.SDK_INT < 21) || this.c.getUseCompatPadding()) {
                i3 = i9 - ((int) Math.ceil(x() * 2.0f));
                i8 -= (int) Math.ceil(y() * 2.0f);
            } else {
                i3 = i9;
            }
            int i10 = this.g;
            if (ViewCompat.getLayoutDirection(this.c) == 1) {
                i4 = i8;
                i5 = i10;
            } else {
                i5 = i8;
                i4 = i10;
            }
            this.q.setLayerInset(2, i5, this.g, i4, i3);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @RequiresApi(api = 23)
    public void v() {
        Drawable drawable = this.p;
        if (drawable != null) {
            Rect bounds = drawable.getBounds();
            int i = bounds.bottom;
            this.p.setBounds(bounds.left, bounds.top, bounds.right, i - 1);
            this.p.setBounds(bounds.left, bounds.top, bounds.right, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(@NonNull ShapeAppearanceModel shapeAppearanceModel) {
        this.n = shapeAppearanceModel;
        this.e.setShapeAppearanceModel(shapeAppearanceModel);
        MaterialShapeDrawable materialShapeDrawable = this.e;
        materialShapeDrawable.setShadowBitmapDrawingEnable(!materialShapeDrawable.isRoundRect());
        MaterialShapeDrawable materialShapeDrawable2 = this.f;
        if (materialShapeDrawable2 != null) {
            materialShapeDrawable2.setShapeAppearanceModel(shapeAppearanceModel);
        }
        MaterialShapeDrawable materialShapeDrawable3 = this.s;
        if (materialShapeDrawable3 != null) {
            materialShapeDrawable3.setShapeAppearanceModel(shapeAppearanceModel);
        }
        MaterialShapeDrawable materialShapeDrawable4 = this.r;
        if (materialShapeDrawable4 != null) {
            materialShapeDrawable4.setShapeAppearanceModel(shapeAppearanceModel);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ShapeAppearanceModel w() {
        return this.n;
    }

    private void b(Drawable drawable) {
        if (Build.VERSION.SDK_INT < 23 || !(this.c.getForeground() instanceof InsetDrawable)) {
            this.c.setForeground(c(drawable));
        } else {
            ((InsetDrawable) this.c.getForeground()).setDrawable(drawable);
        }
    }

    @NonNull
    private Drawable c(Drawable drawable) {
        int i;
        int i2;
        if ((Build.VERSION.SDK_INT < 21) || this.c.getUseCompatPadding()) {
            i = (int) Math.ceil(x());
            i2 = (int) Math.ceil(y());
        } else {
            i2 = 0;
            i = 0;
        }
        return new InsetDrawable(drawable, i2, i, i2, i) { // from class: com.google.android.material.card.a.1
            @Override // android.graphics.drawable.Drawable
            public int getMinimumHeight() {
                return -1;
            }

            @Override // android.graphics.drawable.Drawable
            public int getMinimumWidth() {
                return -1;
            }

            @Override // android.graphics.drawable.InsetDrawable, android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
            public boolean getPadding(Rect rect) {
                return false;
            }
        };
    }

    private float x() {
        return (this.c.getMaxCardElevation() * 1.5f) + (C() ? D() : 0.0f);
    }

    private float y() {
        return this.c.getMaxCardElevation() + (C() ? D() : 0.0f);
    }

    private boolean z() {
        return Build.VERSION.SDK_INT >= 21 && this.e.isRoundRect();
    }

    private float A() {
        if (!this.c.getPreventCornerOverlap()) {
            return 0.0f;
        }
        if (Build.VERSION.SDK_INT < 21 || this.c.getUseCompatPadding()) {
            return (float) ((1.0d - b) * this.c.getCardViewRadius());
        }
        return 0.0f;
    }

    private boolean B() {
        return this.c.getPreventCornerOverlap() && !z();
    }

    private boolean C() {
        return this.c.getPreventCornerOverlap() && z() && this.c.getUseCompatPadding();
    }

    private float D() {
        return Math.max(Math.max(a(this.n.getTopLeftCorner(), this.e.getTopLeftCornerResolvedSize()), a(this.n.getTopRightCorner(), this.e.getTopRightCornerResolvedSize())), Math.max(a(this.n.getBottomRightCorner(), this.e.getBottomRightCornerResolvedSize()), a(this.n.getBottomLeftCorner(), this.e.getBottomLeftCornerResolvedSize())));
    }

    private float a(CornerTreatment cornerTreatment, float f) {
        if (cornerTreatment instanceof RoundedCornerTreatment) {
            return (float) ((1.0d - b) * f);
        }
        if (cornerTreatment instanceof CutCornerTreatment) {
            return f / 2.0f;
        }
        return 0.0f;
    }

    @NonNull
    private Drawable E() {
        if (this.p == null) {
            this.p = F();
        }
        if (this.q == null) {
            this.q = new LayerDrawable(new Drawable[]{this.p, this.f, I()});
            this.q.setId(2, R.id.mtrl_card_checked_layer_id);
        }
        return this.q;
    }

    @NonNull
    private Drawable F() {
        if (!RippleUtils.USE_FRAMEWORK_RIPPLE) {
            return G();
        }
        this.s = J();
        return new RippleDrawable(this.l, null, this.s);
    }

    @NonNull
    private Drawable G() {
        StateListDrawable stateListDrawable = new StateListDrawable();
        this.r = J();
        this.r.setFillColor(this.l);
        stateListDrawable.addState(new int[]{16842919}, this.r);
        return stateListDrawable;
    }

    private void H() {
        Drawable drawable;
        if (!RippleUtils.USE_FRAMEWORK_RIPPLE || (drawable = this.p) == null) {
            MaterialShapeDrawable materialShapeDrawable = this.r;
            if (materialShapeDrawable != null) {
                materialShapeDrawable.setFillColor(this.l);
                return;
            }
            return;
        }
        ((RippleDrawable) drawable).setColor(this.l);
    }

    @NonNull
    private Drawable I() {
        StateListDrawable stateListDrawable = new StateListDrawable();
        Drawable drawable = this.k;
        if (drawable != null) {
            stateListDrawable.addState(a, drawable);
        }
        return stateListDrawable;
    }

    @NonNull
    private MaterialShapeDrawable J() {
        return new MaterialShapeDrawable(this.n);
    }
}
