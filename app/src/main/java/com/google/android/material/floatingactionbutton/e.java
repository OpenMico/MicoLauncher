package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.util.Preconditions;
import com.google.android.material.R;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shadow.ShadowViewDelegate;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import java.util.ArrayList;

/* compiled from: FloatingActionButtonImplLollipop.java */
@RequiresApi(21)
/* loaded from: classes2.dex */
class e extends d {
    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.material.floatingactionbutton.d
    public void j() {
    }

    @Override // com.google.android.material.floatingactionbutton.d
    boolean s() {
        return false;
    }

    @Override // com.google.android.material.floatingactionbutton.d
    void x() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(FloatingActionButton floatingActionButton, ShadowViewDelegate shadowViewDelegate) {
        super(floatingActionButton, shadowViewDelegate);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.material.floatingactionbutton.d
    public void a(ColorStateList colorStateList, @Nullable PorterDuff.Mode mode, ColorStateList colorStateList2, int i) {
        Drawable drawable;
        this.c = u();
        this.c.setTintList(colorStateList);
        if (mode != null) {
            this.c.setTintMode(mode);
        }
        this.c.initializeElevationOverlay(this.s.getContext());
        if (i > 0) {
            this.e = a(i, colorStateList);
            drawable = new LayerDrawable(new Drawable[]{(Drawable) Preconditions.checkNotNull(this.e), (Drawable) Preconditions.checkNotNull(this.c)});
        } else {
            this.e = null;
            drawable = this.c;
        }
        this.d = new RippleDrawable(RippleUtils.sanitizeRippleDrawableColor(colorStateList2), drawable, null);
        this.f = this.d;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.material.floatingactionbutton.d
    public void b(@Nullable ColorStateList colorStateList) {
        if (this.d instanceof RippleDrawable) {
            ((RippleDrawable) this.d).setColor(RippleUtils.sanitizeRippleDrawableColor(colorStateList));
        } else {
            super.b(colorStateList);
        }
    }

    @Override // com.google.android.material.floatingactionbutton.d
    void a(float f, float f2, float f3) {
        if (Build.VERSION.SDK_INT == 21) {
            this.s.refreshDrawableState();
        } else {
            StateListAnimator stateListAnimator = new StateListAnimator();
            stateListAnimator.addState(m, a(f, f3));
            stateListAnimator.addState(n, a(f, f2));
            stateListAnimator.addState(o, a(f, f2));
            stateListAnimator.addState(p, a(f, f2));
            AnimatorSet animatorSet = new AnimatorSet();
            ArrayList arrayList = new ArrayList();
            arrayList.add(ObjectAnimator.ofFloat(this.s, "elevation", f).setDuration(0L));
            if (Build.VERSION.SDK_INT >= 22 && Build.VERSION.SDK_INT <= 24) {
                arrayList.add(ObjectAnimator.ofFloat(this.s, View.TRANSLATION_Z, this.s.getTranslationZ()).setDuration(100L));
            }
            arrayList.add(ObjectAnimator.ofFloat(this.s, View.TRANSLATION_Z, 0.0f).setDuration(100L));
            animatorSet.playSequentially((Animator[]) arrayList.toArray(new Animator[0]));
            animatorSet.setInterpolator(a);
            stateListAnimator.addState(q, animatorSet);
            stateListAnimator.addState(r, a(0.0f, 0.0f));
            this.s.setStateListAnimator(stateListAnimator);
        }
        if (p()) {
            o();
        }
    }

    @NonNull
    private Animator a(float f, float f2) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(ObjectAnimator.ofFloat(this.s, "elevation", f).setDuration(0L)).with(ObjectAnimator.ofFloat(this.s, View.TRANSLATION_Z, f2).setDuration(100L));
        animatorSet.setInterpolator(a);
        return animatorSet;
    }

    @Override // com.google.android.material.floatingactionbutton.d
    public float a() {
        return this.s.getElevation();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.material.floatingactionbutton.d
    public void n() {
        o();
    }

    @Override // com.google.android.material.floatingactionbutton.d
    boolean p() {
        return this.t.isCompatPaddingEnabled() || !h();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.material.floatingactionbutton.d
    public void a(int[] iArr) {
        if (Build.VERSION.SDK_INT != 21) {
            return;
        }
        if (this.s.isEnabled()) {
            this.s.setElevation(this.i);
            if (this.s.isPressed()) {
                this.s.setTranslationZ(this.k);
            } else if (this.s.isFocused() || this.s.isHovered()) {
                this.s.setTranslationZ(this.j);
            } else {
                this.s.setTranslationZ(0.0f);
            }
        } else {
            this.s.setElevation(0.0f);
            this.s.setTranslationZ(0.0f);
        }
    }

    @NonNull
    c a(int i, ColorStateList colorStateList) {
        Context context = this.s.getContext();
        c cVar = new c((ShapeAppearanceModel) Preconditions.checkNotNull(this.b));
        cVar.a(ContextCompat.getColor(context, R.color.design_fab_stroke_top_outer_color), ContextCompat.getColor(context, R.color.design_fab_stroke_top_inner_color), ContextCompat.getColor(context, R.color.design_fab_stroke_end_inner_color), ContextCompat.getColor(context, R.color.design_fab_stroke_end_outer_color));
        cVar.a(i);
        cVar.a(colorStateList);
        return cVar;
    }

    @Override // com.google.android.material.floatingactionbutton.d
    @NonNull
    MaterialShapeDrawable u() {
        return new a((ShapeAppearanceModel) Preconditions.checkNotNull(this.b));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.android.material.floatingactionbutton.d
    public void a(@NonNull Rect rect) {
        if (this.t.isCompatPaddingEnabled()) {
            super.a(rect);
        } else if (!h()) {
            int sizeDimension = (this.l - this.s.getSizeDimension()) / 2;
            rect.set(sizeDimension, sizeDimension, sizeDimension, sizeDimension);
        } else {
            rect.set(0, 0, 0, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: FloatingActionButtonImplLollipop.java */
    /* loaded from: classes2.dex */
    public static class a extends MaterialShapeDrawable {
        @Override // com.google.android.material.shape.MaterialShapeDrawable, android.graphics.drawable.Drawable
        public boolean isStateful() {
            return true;
        }

        a(ShapeAppearanceModel shapeAppearanceModel) {
            super(shapeAppearanceModel);
        }
    }
}
