package com.google.android.material.card;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Checkable;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.cardview.widget.CardView;
import com.google.android.material.R;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

/* loaded from: classes2.dex */
public class MaterialCardView extends CardView implements Checkable, Shapeable {
    private static final int[] e = {16842911};
    private static final int[] f = {16842912};
    private static final int[] g = {R.attr.state_dragged};
    private static final int h = R.style.Widget_MaterialComponents_CardView;
    @NonNull
    private final a i;
    private boolean j;
    private boolean k;
    private boolean l;
    private OnCheckedChangeListener m;

    /* loaded from: classes2.dex */
    public interface OnCheckedChangeListener {
        void onCheckedChanged(MaterialCardView materialCardView, boolean z);
    }

    public MaterialCardView(Context context) {
        this(context, null);
    }

    public MaterialCardView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.materialCardViewStyle);
    }

    public MaterialCardView(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, h), attributeSet, i);
        this.k = false;
        this.l = false;
        this.j = true;
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(getContext(), attributeSet, R.styleable.MaterialCardView, i, h, new int[0]);
        this.i = new a(this, attributeSet, i, h);
        this.i.b(super.getCardBackgroundColor());
        this.i.a(super.getContentPaddingLeft(), super.getContentPaddingTop(), super.getContentPaddingRight(), super.getContentPaddingBottom());
        this.i.a(obtainStyledAttributes);
        obtainStyledAttributes.recycle();
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(@NonNull AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName("androidx.cardview.widget.CardView");
        accessibilityNodeInfo.setCheckable(isCheckable());
        accessibilityNodeInfo.setClickable(isClickable());
        accessibilityNodeInfo.setChecked(isChecked());
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(@NonNull AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName("androidx.cardview.widget.CardView");
        accessibilityEvent.setChecked(isChecked());
    }

    @Override // androidx.cardview.widget.CardView, android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        this.i.a(getMeasuredWidth(), getMeasuredHeight());
    }

    public void setStrokeColor(@ColorInt int i) {
        this.i.a(ColorStateList.valueOf(i));
    }

    public void setStrokeColor(ColorStateList colorStateList) {
        this.i.a(colorStateList);
    }

    @ColorInt
    @Deprecated
    public int getStrokeColor() {
        return this.i.b();
    }

    @Nullable
    public ColorStateList getStrokeColorStateList() {
        return this.i.c();
    }

    public void setStrokeWidth(@Dimension int i) {
        this.i.a(i);
    }

    @Dimension
    public int getStrokeWidth() {
        return this.i.d();
    }

    @Override // androidx.cardview.widget.CardView
    public void setRadius(float f2) {
        super.setRadius(f2);
        this.i.a(f2);
    }

    @Override // androidx.cardview.widget.CardView
    public float getRadius() {
        return this.i.j();
    }

    public float getCardViewRadius() {
        return super.getRadius();
    }

    public void setProgress(@FloatRange(from = 0.0d, to = 1.0d) float f2) {
        this.i.b(f2);
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public float getProgress() {
        return this.i.k();
    }

    @Override // androidx.cardview.widget.CardView
    public void setContentPadding(int i, int i2, int i3, int i4) {
        this.i.a(i, i2, i3, i4);
    }

    public void a(int i, int i2, int i3, int i4) {
        super.setContentPadding(i, i2, i3, i4);
    }

    @Override // androidx.cardview.widget.CardView
    public int getContentPaddingLeft() {
        return this.i.h().left;
    }

    @Override // androidx.cardview.widget.CardView
    public int getContentPaddingTop() {
        return this.i.h().top;
    }

    @Override // androidx.cardview.widget.CardView
    public int getContentPaddingRight() {
        return this.i.h().right;
    }

    @Override // androidx.cardview.widget.CardView
    public int getContentPaddingBottom() {
        return this.i.h().bottom;
    }

    @Override // androidx.cardview.widget.CardView
    public void setCardBackgroundColor(@ColorInt int i) {
        this.i.b(ColorStateList.valueOf(i));
    }

    @Override // androidx.cardview.widget.CardView
    public void setCardBackgroundColor(@Nullable ColorStateList colorStateList) {
        this.i.b(colorStateList);
    }

    @Override // androidx.cardview.widget.CardView
    @NonNull
    public ColorStateList getCardBackgroundColor() {
        return this.i.f();
    }

    public void setCardForegroundColor(@Nullable ColorStateList colorStateList) {
        this.i.c(colorStateList);
    }

    @NonNull
    public ColorStateList getCardForegroundColor() {
        return this.i.g();
    }

    @Override // android.view.View
    public void setClickable(boolean z) {
        super.setClickable(z);
        a aVar = this.i;
        if (aVar != null) {
            aVar.i();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this, this.i.e());
    }

    @Override // androidx.cardview.widget.CardView
    public void setCardElevation(float f2) {
        super.setCardElevation(f2);
        this.i.l();
    }

    @Override // androidx.cardview.widget.CardView
    public void setMaxCardElevation(float f2) {
        super.setMaxCardElevation(f2);
        this.i.m();
    }

    @Override // androidx.cardview.widget.CardView
    public void setUseCompatPadding(boolean z) {
        super.setUseCompatPadding(z);
        this.i.m();
        this.i.o();
    }

    @Override // androidx.cardview.widget.CardView
    public void setPreventCornerOverlap(boolean z) {
        super.setPreventCornerOverlap(z);
        this.i.m();
        this.i.o();
    }

    @Override // android.view.View
    public void setBackground(Drawable drawable) {
        setBackgroundDrawable(drawable);
    }

    @Override // android.view.View
    public void setBackgroundDrawable(Drawable drawable) {
        if (this.j) {
            if (!this.i.a()) {
                Log.i("MaterialCardView", "Setting a custom background is not supported.");
                this.i.a(true);
            }
            super.setBackgroundDrawable(drawable);
        }
    }

    public void setBackgroundInternal(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
    }

    @Override // android.widget.Checkable
    public boolean isChecked() {
        return this.k;
    }

    @Override // android.widget.Checkable
    public void setChecked(boolean z) {
        if (this.k != z) {
            toggle();
        }
    }

    public void setDragged(boolean z) {
        if (this.l != z) {
            this.l = z;
            refreshDrawableState();
            a();
            invalidate();
        }
    }

    public boolean isDragged() {
        return this.l;
    }

    public boolean isCheckable() {
        a aVar = this.i;
        return aVar != null && aVar.p();
    }

    public void setCheckable(boolean z) {
        this.i.b(z);
    }

    @Override // android.widget.Checkable
    public void toggle() {
        if (isCheckable() && isEnabled()) {
            this.k = !this.k;
            refreshDrawableState();
            a();
            OnCheckedChangeListener onCheckedChangeListener = this.m;
            if (onCheckedChangeListener != null) {
                onCheckedChangeListener.onCheckedChanged(this, this.k);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 3);
        if (isCheckable()) {
            mergeDrawableStates(onCreateDrawableState, e);
        }
        if (isChecked()) {
            mergeDrawableStates(onCreateDrawableState, f);
        }
        if (isDragged()) {
            mergeDrawableStates(onCreateDrawableState, g);
        }
        return onCreateDrawableState;
    }

    public void setOnCheckedChangeListener(@Nullable OnCheckedChangeListener onCheckedChangeListener) {
        this.m = onCheckedChangeListener;
    }

    public void setRippleColor(@Nullable ColorStateList colorStateList) {
        this.i.d(colorStateList);
    }

    public void setRippleColorResource(@ColorRes int i) {
        this.i.d(AppCompatResources.getColorStateList(getContext(), i));
    }

    public ColorStateList getRippleColor() {
        return this.i.r();
    }

    @Nullable
    public Drawable getCheckedIcon() {
        return this.i.s();
    }

    public void setCheckedIconResource(@DrawableRes int i) {
        this.i.a(AppCompatResources.getDrawable(getContext(), i));
    }

    public void setCheckedIcon(@Nullable Drawable drawable) {
        this.i.a(drawable);
    }

    @Nullable
    public ColorStateList getCheckedIconTint() {
        return this.i.q();
    }

    public void setCheckedIconTint(@Nullable ColorStateList colorStateList) {
        this.i.e(colorStateList);
    }

    @Dimension
    public int getCheckedIconSize() {
        return this.i.t();
    }

    public void setCheckedIconSize(@Dimension int i) {
        this.i.b(i);
    }

    public void setCheckedIconSizeResource(@DimenRes int i) {
        if (i != 0) {
            this.i.b(getResources().getDimensionPixelSize(i));
        }
    }

    @Dimension
    public int getCheckedIconMargin() {
        return this.i.u();
    }

    public void setCheckedIconMargin(@Dimension int i) {
        this.i.c(i);
    }

    public void setCheckedIconMarginResource(@DimenRes int i) {
        if (i != -1) {
            this.i.c(getResources().getDimensionPixelSize(i));
        }
    }

    @NonNull
    private RectF getBoundsAsRectF() {
        RectF rectF = new RectF();
        rectF.set(this.i.e().getBounds());
        return rectF;
    }

    @Override // com.google.android.material.shape.Shapeable
    public void setShapeAppearanceModel(@NonNull ShapeAppearanceModel shapeAppearanceModel) {
        if (Build.VERSION.SDK_INT >= 21) {
            setClipToOutline(shapeAppearanceModel.isRoundRect(getBoundsAsRectF()));
        }
        this.i.a(shapeAppearanceModel);
    }

    @Override // com.google.android.material.shape.Shapeable
    @NonNull
    public ShapeAppearanceModel getShapeAppearanceModel() {
        return this.i.w();
    }

    private void a() {
        if (Build.VERSION.SDK_INT > 26) {
            this.i.v();
        }
    }
}
