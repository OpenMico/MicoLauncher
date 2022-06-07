package com.google.android.material.button;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.ripple.RippleDrawableCompat;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: MaterialButtonHelper.java */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes2.dex */
public class a {
    private static final boolean a;
    private final MaterialButton b;
    @NonNull
    private ShapeAppearanceModel c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    @Nullable
    private PorterDuff.Mode j;
    @Nullable
    private ColorStateList k;
    @Nullable
    private ColorStateList l;
    @Nullable
    private ColorStateList m;
    @Nullable
    private Drawable n;
    private boolean o = false;
    private boolean p = false;
    private boolean q = false;
    private boolean r;
    private LayerDrawable s;
    private int t;

    static {
        a = Build.VERSION.SDK_INT >= 21;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(MaterialButton materialButton, @NonNull ShapeAppearanceModel shapeAppearanceModel) {
        this.b = materialButton;
        this.c = shapeAppearanceModel;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(@NonNull TypedArray typedArray) {
        this.d = typedArray.getDimensionPixelOffset(R.styleable.MaterialButton_android_insetLeft, 0);
        this.e = typedArray.getDimensionPixelOffset(R.styleable.MaterialButton_android_insetRight, 0);
        this.f = typedArray.getDimensionPixelOffset(R.styleable.MaterialButton_android_insetTop, 0);
        this.g = typedArray.getDimensionPixelOffset(R.styleable.MaterialButton_android_insetBottom, 0);
        if (typedArray.hasValue(R.styleable.MaterialButton_cornerRadius)) {
            this.h = typedArray.getDimensionPixelSize(R.styleable.MaterialButton_cornerRadius, -1);
            a(this.c.withCornerSize(this.h));
            this.q = true;
        }
        this.i = typedArray.getDimensionPixelSize(R.styleable.MaterialButton_strokeWidth, 0);
        this.j = ViewUtils.parseTintMode(typedArray.getInt(R.styleable.MaterialButton_backgroundTintMode, -1), PorterDuff.Mode.SRC_IN);
        this.k = MaterialResources.getColorStateList(this.b.getContext(), typedArray, R.styleable.MaterialButton_backgroundTint);
        this.l = MaterialResources.getColorStateList(this.b.getContext(), typedArray, R.styleable.MaterialButton_strokeColor);
        this.m = MaterialResources.getColorStateList(this.b.getContext(), typedArray, R.styleable.MaterialButton_rippleColor);
        this.r = typedArray.getBoolean(R.styleable.MaterialButton_android_checkable, false);
        this.t = typedArray.getDimensionPixelSize(R.styleable.MaterialButton_elevation, 0);
        int paddingStart = ViewCompat.getPaddingStart(this.b);
        int paddingTop = this.b.getPaddingTop();
        int paddingEnd = ViewCompat.getPaddingEnd(this.b);
        int paddingBottom = this.b.getPaddingBottom();
        if (typedArray.hasValue(R.styleable.MaterialButton_android_background)) {
            a();
        } else {
            o();
        }
        ViewCompat.setPaddingRelative(this.b, paddingStart + this.d, paddingTop + this.f, paddingEnd + this.e, paddingBottom + this.g);
    }

    private void o() {
        this.b.setInternalBackground(p());
        MaterialShapeDrawable i = i();
        if (i != null) {
            i.setElevation(this.t);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a() {
        this.p = true;
        this.b.setSupportBackgroundTintList(this.k);
        this.b.setSupportBackgroundTintMode(this.j);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean b() {
        return this.p;
    }

    @NonNull
    private InsetDrawable a(Drawable drawable) {
        return new InsetDrawable(drawable, this.d, this.f, this.e, this.g);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(@Nullable ColorStateList colorStateList) {
        if (this.k != colorStateList) {
            this.k = colorStateList;
            if (i() != null) {
                DrawableCompat.setTintList(i(), this.k);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ColorStateList c() {
        return this.k;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(@Nullable PorterDuff.Mode mode) {
        if (this.j != mode) {
            this.j = mode;
            if (i() != null && this.j != null) {
                DrawableCompat.setTintMode(i(), this.j);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public PorterDuff.Mode d() {
        return this.j;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(boolean z) {
        this.o = z;
        q();
    }

    private Drawable p() {
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(this.c);
        materialShapeDrawable.initializeElevationOverlay(this.b.getContext());
        DrawableCompat.setTintList(materialShapeDrawable, this.k);
        PorterDuff.Mode mode = this.j;
        if (mode != null) {
            DrawableCompat.setTintMode(materialShapeDrawable, mode);
        }
        materialShapeDrawable.setStroke(this.i, this.l);
        MaterialShapeDrawable materialShapeDrawable2 = new MaterialShapeDrawable(this.c);
        materialShapeDrawable2.setTint(0);
        materialShapeDrawable2.setStroke(this.i, this.o ? MaterialColors.getColor(this.b, R.attr.colorSurface) : 0);
        if (a) {
            this.n = new MaterialShapeDrawable(this.c);
            DrawableCompat.setTint(this.n, -1);
            this.s = new RippleDrawable(RippleUtils.sanitizeRippleDrawableColor(this.m), a(new LayerDrawable(new Drawable[]{materialShapeDrawable2, materialShapeDrawable})), this.n);
            return this.s;
        }
        this.n = new RippleDrawableCompat(this.c);
        DrawableCompat.setTintList(this.n, RippleUtils.sanitizeRippleDrawableColor(this.m));
        this.s = new LayerDrawable(new Drawable[]{materialShapeDrawable2, materialShapeDrawable, this.n});
        return a(this.s);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(int i, int i2) {
        Drawable drawable = this.n;
        if (drawable != null) {
            drawable.setBounds(this.d, this.f, i2 - this.e, i - this.g);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(int i) {
        if (i() != null) {
            i().setTint(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(@Nullable ColorStateList colorStateList) {
        if (this.m != colorStateList) {
            this.m = colorStateList;
            if (a && (this.b.getBackground() instanceof RippleDrawable)) {
                ((RippleDrawable) this.b.getBackground()).setColor(RippleUtils.sanitizeRippleDrawableColor(colorStateList));
            } else if (!a && (this.b.getBackground() instanceof RippleDrawableCompat)) {
                ((RippleDrawableCompat) this.b.getBackground()).setTintList(RippleUtils.sanitizeRippleDrawableColor(colorStateList));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public ColorStateList e() {
        return this.m;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void c(@Nullable ColorStateList colorStateList) {
        if (this.l != colorStateList) {
            this.l = colorStateList;
            q();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public ColorStateList f() {
        return this.l;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(int i) {
        if (this.i != i) {
            this.i = i;
            q();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int g() {
        return this.i;
    }

    private void q() {
        MaterialShapeDrawable i = i();
        MaterialShapeDrawable r = r();
        if (i != null) {
            i.setStroke(this.i, this.l);
            if (r != null) {
                r.setStroke(this.i, this.o ? MaterialColors.getColor(this.b, R.attr.colorSurface) : 0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void c(int i) {
        if (!this.q || this.h != i) {
            this.h = i;
            this.q = true;
            a(this.c.withCornerSize(i));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int h() {
        return this.h;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Nullable
    private MaterialShapeDrawable c(boolean z) {
        LayerDrawable layerDrawable = this.s;
        if (layerDrawable == null || layerDrawable.getNumberOfLayers() <= 0) {
            return null;
        }
        if (a) {
            return (MaterialShapeDrawable) ((LayerDrawable) ((InsetDrawable) this.s.getDrawable(0)).getDrawable()).getDrawable(!z);
        }
        return (MaterialShapeDrawable) this.s.getDrawable(!z ? 1 : 0);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public MaterialShapeDrawable i() {
        return c(false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(boolean z) {
        this.r = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean j() {
        return this.r;
    }

    @Nullable
    private MaterialShapeDrawable r() {
        return c(true);
    }

    private void b(@NonNull ShapeAppearanceModel shapeAppearanceModel) {
        if (i() != null) {
            i().setShapeAppearanceModel(shapeAppearanceModel);
        }
        if (r() != null) {
            r().setShapeAppearanceModel(shapeAppearanceModel);
        }
        if (k() != null) {
            k().setShapeAppearanceModel(shapeAppearanceModel);
        }
    }

    @Nullable
    public Shapeable k() {
        LayerDrawable layerDrawable = this.s;
        if (layerDrawable == null || layerDrawable.getNumberOfLayers() <= 1) {
            return null;
        }
        if (this.s.getNumberOfLayers() > 2) {
            return (Shapeable) this.s.getDrawable(2);
        }
        return (Shapeable) this.s.getDrawable(1);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(@NonNull ShapeAppearanceModel shapeAppearanceModel) {
        this.c = shapeAppearanceModel;
        b(shapeAppearanceModel);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public ShapeAppearanceModel l() {
        return this.c;
    }

    public void d(@Dimension int i) {
        b(this.f, i);
    }

    public int m() {
        return this.g;
    }

    public void e(@Dimension int i) {
        b(i, this.g);
    }

    private void b(@Dimension int i, @Dimension int i2) {
        int paddingStart = ViewCompat.getPaddingStart(this.b);
        int paddingTop = this.b.getPaddingTop();
        int paddingEnd = ViewCompat.getPaddingEnd(this.b);
        int paddingBottom = this.b.getPaddingBottom();
        int i3 = this.f;
        int i4 = this.g;
        this.g = i2;
        this.f = i;
        if (!this.p) {
            o();
        }
        ViewCompat.setPaddingRelative(this.b, paddingStart, (paddingTop + i) - i3, paddingEnd, (paddingBottom + i2) - i4);
    }

    public int n() {
        return this.f;
    }
}
