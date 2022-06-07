package com.google.android.material.chip;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.annotation.AnimatorRes;
import androidx.annotation.BoolRes;
import androidx.annotation.CallSuper;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import com.google.android.material.R;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.resources.TextAppearanceFontCallback;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import com.umeng.analytics.pro.ai;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/* loaded from: classes2.dex */
public class Chip extends AppCompatCheckBox implements ChipDrawable.Delegate, Shapeable {
    private static final int a = R.style.Widget_MaterialComponents_Chip_Action;
    private static final Rect b = new Rect();
    private static final int[] c = {16842913};
    private static final int[] d = {16842911};
    @Nullable
    private ChipDrawable e;
    @Nullable
    private InsetDrawable f;
    @Nullable
    private RippleDrawable g;
    @Nullable
    private View.OnClickListener h;
    @Nullable
    private CompoundButton.OnCheckedChangeListener i;
    private boolean j;
    private boolean k;
    private boolean l;
    private boolean m;
    private boolean n;
    private int o;
    @Dimension(unit = 1)
    private int p;
    @NonNull
    private final a q;
    private final Rect r;
    private final RectF s;
    private final TextAppearanceFontCallback t;

    public Chip(Context context) {
        this(context, null);
    }

    public Chip(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.chipStyle);
    }

    public Chip(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, a), attributeSet, i);
        this.r = new Rect();
        this.s = new RectF();
        this.t = new TextAppearanceFontCallback() { // from class: com.google.android.material.chip.Chip.1
            @Override // com.google.android.material.resources.TextAppearanceFontCallback
            public void onFontRetrievalFailed(int i2) {
            }

            @Override // com.google.android.material.resources.TextAppearanceFontCallback
            public void onFontRetrieved(@NonNull Typeface typeface, boolean z) {
                Chip chip = Chip.this;
                chip.setText(chip.e.c() ? Chip.this.e.getText() : Chip.this.getText());
                Chip.this.requestLayout();
                Chip.this.invalidate();
            }
        };
        Context context2 = getContext();
        a(attributeSet);
        ChipDrawable createFromAttributes = ChipDrawable.createFromAttributes(context2, attributeSet, i, a);
        a(context2, attributeSet, i);
        setChipDrawable(createFromAttributes);
        createFromAttributes.setElevation(ViewCompat.getElevation(this));
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R.styleable.Chip, i, a, new int[0]);
        if (Build.VERSION.SDK_INT < 23) {
            setTextColor(MaterialResources.getColorStateList(context2, obtainStyledAttributes, R.styleable.Chip_android_textColor));
        }
        boolean hasValue = obtainStyledAttributes.hasValue(R.styleable.Chip_shapeAppearance);
        obtainStyledAttributes.recycle();
        this.q = new a(this);
        b();
        if (!hasValue) {
            d();
        }
        setChecked(this.j);
        setText(createFromAttributes.getText());
        setEllipsize(createFromAttributes.getEllipsize());
        j();
        if (!this.e.c()) {
            setLines(1);
            setHorizontallyScrolling(true);
        }
        setGravity(8388627);
        c();
        if (shouldEnsureMinTouchTargetSize()) {
            setMinHeight(this.p);
        }
        this.o = ViewCompat.getLayoutDirection(this);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this, this.e);
    }

    @Override // android.view.View
    @RequiresApi(21)
    public void setElevation(float f) {
        super.setElevation(f);
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setElevation(f);
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(@NonNull AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (isCheckable() || isClickable()) {
            accessibilityNodeInfo.setClassName(isCheckable() ? "android.widget.CompoundButton" : "android.widget.Button");
        } else {
            accessibilityNodeInfo.setClassName("android.view.View");
        }
        accessibilityNodeInfo.setCheckable(isCheckable());
        accessibilityNodeInfo.setClickable(isClickable());
        if (getParent() instanceof ChipGroup) {
            ChipGroup chipGroup = (ChipGroup) getParent();
            AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo).setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(chipGroup.getRowIndex(this), 1, chipGroup.isSingleLine() ? chipGroup.a(this) : -1, 1, false, isChecked()));
        }
    }

    private void b() {
        if (!i() || !isCloseIconVisible() || this.h == null) {
            ViewCompat.setAccessibilityDelegate(this, null);
        } else {
            ViewCompat.setAccessibilityDelegate(this, this.q);
        }
    }

    private void a(Context context, @Nullable AttributeSet attributeSet, int i) {
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context, attributeSet, R.styleable.Chip, i, a, new int[0]);
        this.n = obtainStyledAttributes.getBoolean(R.styleable.Chip_ensureMinTouchTargetSize, false);
        this.p = (int) Math.ceil(obtainStyledAttributes.getDimension(R.styleable.Chip_chipMinTouchTargetSize, (float) Math.ceil(ViewUtils.dpToPx(getContext(), 48))));
        obtainStyledAttributes.recycle();
    }

    private void c() {
        ChipDrawable chipDrawable;
        if (!TextUtils.isEmpty(getText()) && (chipDrawable = this.e) != null) {
            int chipEndPadding = (int) (chipDrawable.getChipEndPadding() + this.e.getTextEndPadding() + this.e.b());
            int chipStartPadding = (int) (this.e.getChipStartPadding() + this.e.getTextStartPadding() + this.e.a());
            if (this.f != null) {
                Rect rect = new Rect();
                this.f.getPadding(rect);
                chipStartPadding += rect.left;
                chipEndPadding += rect.right;
            }
            ViewCompat.setPaddingRelative(this, chipStartPadding, getPaddingTop(), chipEndPadding, getPaddingBottom());
        }
    }

    @Override // android.widget.TextView, android.view.View
    @TargetApi(17)
    public void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        if (this.o != i) {
            this.o = i;
            c();
        }
    }

    private void a(@Nullable AttributeSet attributeSet) {
        if (attributeSet != null) {
            if (attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "background") != null) {
                Log.w("Chip", "Do not set the background; Chip manages its own background drawable.");
            }
            if (attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "drawableLeft") != null) {
                throw new UnsupportedOperationException("Please set left drawable using R.attr#chipIcon.");
            } else if (attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "drawableStart") != null) {
                throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
            } else if (attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "drawableEnd") != null) {
                throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
            } else if (attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "drawableRight") != null) {
                throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
            } else if (!attributeSet.getAttributeBooleanValue("http://schemas.android.com/apk/res/android", "singleLine", true) || attributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "lines", 1) != 1 || attributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "minLines", 1) != 1 || attributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "maxLines", 1) != 1) {
                throw new UnsupportedOperationException("Chip does not support multi-line text");
            } else if (attributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "gravity", 8388627) != 8388627) {
                Log.w("Chip", "Chip text must be vertically center and start aligned");
            }
        }
    }

    private void d() {
        if (Build.VERSION.SDK_INT >= 21) {
            setOutlineProvider(new ViewOutlineProvider() { // from class: com.google.android.material.chip.Chip.2
                @Override // android.view.ViewOutlineProvider
                @TargetApi(21)
                public void getOutline(View view, @NonNull Outline outline) {
                    if (Chip.this.e != null) {
                        Chip.this.e.getOutline(outline);
                    } else {
                        outline.setAlpha(0.0f);
                    }
                }
            });
        }
    }

    public Drawable getChipDrawable() {
        return this.e;
    }

    public void setChipDrawable(@NonNull ChipDrawable chipDrawable) {
        ChipDrawable chipDrawable2 = this.e;
        if (chipDrawable2 != chipDrawable) {
            a(chipDrawable2);
            this.e = chipDrawable;
            this.e.a(false);
            b(this.e);
            ensureAccessibleTouchTarget(this.p);
        }
    }

    private void e() {
        if (RippleUtils.USE_FRAMEWORK_RIPPLE) {
            g();
            return;
        }
        this.e.setUseCompatRipple(true);
        ViewCompat.setBackground(this, getBackgroundDrawable());
        c();
        f();
    }

    private void f() {
        if (getBackgroundDrawable() == this.f && this.e.getCallback() == null) {
            this.e.setCallback(this.f);
        }
    }

    @Nullable
    public Drawable getBackgroundDrawable() {
        InsetDrawable insetDrawable = this.f;
        return insetDrawable == null ? this.e : insetDrawable;
    }

    private void g() {
        this.g = new RippleDrawable(RippleUtils.sanitizeRippleDrawableColor(this.e.getRippleColor()), getBackgroundDrawable(), null);
        this.e.setUseCompatRipple(false);
        ViewCompat.setBackground(this, this.g);
        c();
    }

    private void a(@Nullable ChipDrawable chipDrawable) {
        if (chipDrawable != null) {
            chipDrawable.setDelegate(null);
        }
    }

    private void b(@NonNull ChipDrawable chipDrawable) {
        chipDrawable.setDelegate(this);
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    protected int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 2);
        if (isChecked()) {
            mergeDrawableStates(onCreateDrawableState, c);
        }
        if (isCheckable()) {
            mergeDrawableStates(onCreateDrawableState, d);
        }
        return onCreateDrawableState;
    }

    @Override // android.widget.TextView
    public void setGravity(int i) {
        if (i != 8388627) {
            Log.w("Chip", "Chip text must be vertically center and start aligned");
        } else {
            super.setGravity(i);
        }
    }

    @Override // android.view.View
    public void setBackgroundTintList(@Nullable ColorStateList colorStateList) {
        Log.w("Chip", "Do not set the background tint list; Chip manages its own background drawable.");
    }

    @Override // android.view.View
    public void setBackgroundTintMode(@Nullable PorterDuff.Mode mode) {
        Log.w("Chip", "Do not set the background tint mode; Chip manages its own background drawable.");
    }

    @Override // android.view.View
    public void setBackgroundColor(int i) {
        Log.w("Chip", "Do not set the background color; Chip manages its own background drawable.");
    }

    @Override // androidx.appcompat.widget.AppCompatCheckBox, android.view.View
    public void setBackgroundResource(int i) {
        Log.w("Chip", "Do not set the background resource; Chip manages its own background drawable.");
    }

    @Override // android.view.View
    public void setBackground(Drawable drawable) {
        if (drawable == getBackgroundDrawable() || drawable == this.g) {
            super.setBackground(drawable);
        } else {
            Log.w("Chip", "Do not set the background; Chip manages its own background drawable.");
        }
    }

    @Override // androidx.appcompat.widget.AppCompatCheckBox, android.view.View
    public void setBackgroundDrawable(Drawable drawable) {
        if (drawable == getBackgroundDrawable() || drawable == this.g) {
            super.setBackgroundDrawable(drawable);
        } else {
            Log.w("Chip", "Do not set the background drawable; Chip manages its own background drawable.");
        }
    }

    @Override // android.widget.TextView
    public void setCompoundDrawables(@Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
        if (drawable != null) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        } else if (drawable3 == null) {
            super.setCompoundDrawables(drawable, drawable2, drawable3, drawable4);
        } else {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
    }

    @Override // android.widget.TextView
    public void setCompoundDrawablesWithIntrinsicBounds(int i, int i2, int i3, int i4) {
        if (i != 0) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        } else if (i3 == 0) {
            super.setCompoundDrawablesWithIntrinsicBounds(i, i2, i3, i4);
        } else {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
    }

    @Override // android.widget.TextView
    public void setCompoundDrawablesWithIntrinsicBounds(@Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
        if (drawable != null) {
            throw new UnsupportedOperationException("Please set left drawable using R.attr#chipIcon.");
        } else if (drawable3 == null) {
            super.setCompoundDrawablesWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
        } else {
            throw new UnsupportedOperationException("Please set right drawable using R.attr#closeIcon.");
        }
    }

    @Override // android.widget.TextView
    public void setCompoundDrawablesRelative(@Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
        if (drawable != null) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        } else if (drawable3 == null) {
            super.setCompoundDrawablesRelative(drawable, drawable2, drawable3, drawable4);
        } else {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
    }

    @Override // android.widget.TextView
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(int i, int i2, int i3, int i4) {
        if (i != 0) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        } else if (i3 == 0) {
            super.setCompoundDrawablesRelativeWithIntrinsicBounds(i, i2, i3, i4);
        } else {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
    }

    @Override // android.widget.TextView
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(@Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
        if (drawable != null) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        } else if (drawable3 == null) {
            super.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
        } else {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
    }

    @Override // android.widget.TextView
    @Nullable
    public TextUtils.TruncateAt getEllipsize() {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            return chipDrawable.getEllipsize();
        }
        return null;
    }

    @Override // android.widget.TextView
    public void setEllipsize(TextUtils.TruncateAt truncateAt) {
        if (this.e != null) {
            if (truncateAt != TextUtils.TruncateAt.MARQUEE) {
                super.setEllipsize(truncateAt);
                ChipDrawable chipDrawable = this.e;
                if (chipDrawable != null) {
                    chipDrawable.setEllipsize(truncateAt);
                    return;
                }
                return;
            }
            throw new UnsupportedOperationException("Text within a chip are not allowed to scroll.");
        }
    }

    @Override // android.widget.TextView
    public void setSingleLine(boolean z) {
        if (z) {
            super.setSingleLine(z);
            return;
        }
        throw new UnsupportedOperationException("Chip does not support multi-line text");
    }

    @Override // android.widget.TextView
    public void setLines(int i) {
        if (i <= 1) {
            super.setLines(i);
            return;
        }
        throw new UnsupportedOperationException("Chip does not support multi-line text");
    }

    @Override // android.widget.TextView
    public void setMinLines(int i) {
        if (i <= 1) {
            super.setMinLines(i);
            return;
        }
        throw new UnsupportedOperationException("Chip does not support multi-line text");
    }

    @Override // android.widget.TextView
    public void setMaxLines(int i) {
        if (i <= 1) {
            super.setMaxLines(i);
            return;
        }
        throw new UnsupportedOperationException("Chip does not support multi-line text");
    }

    @Override // android.widget.TextView
    public void setMaxWidth(@Px int i) {
        super.setMaxWidth(i);
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setMaxWidth(i);
        }
    }

    @Override // com.google.android.material.chip.ChipDrawable.Delegate
    public void onChipDrawableSizeChange() {
        ensureAccessibleTouchTarget(this.p);
        requestLayout();
        if (Build.VERSION.SDK_INT >= 21) {
            invalidateOutline();
        }
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public void setChecked(boolean z) {
        CompoundButton.OnCheckedChangeListener onCheckedChangeListener;
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable == null) {
            this.j = z;
        } else if (chipDrawable.isCheckable()) {
            boolean isChecked = isChecked();
            super.setChecked(z);
            if (isChecked != z && (onCheckedChangeListener = this.i) != null) {
                onCheckedChangeListener.onCheckedChanged(this, z);
            }
        }
    }

    public void setOnCheckedChangeListenerInternal(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.i = onCheckedChangeListener;
    }

    public void setOnCloseIconClickListener(View.OnClickListener onClickListener) {
        this.h = onClickListener;
        b();
    }

    @CallSuper
    public boolean performCloseIconClick() {
        boolean z = false;
        playSoundEffect(0);
        View.OnClickListener onClickListener = this.h;
        if (onClickListener != null) {
            onClickListener.onClick(this);
            z = true;
        }
        this.q.sendEventForVirtualView(1, 1);
        return z;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.widget.TextView, android.view.View
    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(@NonNull MotionEvent motionEvent) {
        boolean z;
        int actionMasked = motionEvent.getActionMasked();
        boolean contains = getCloseIconTouchBounds().contains(motionEvent.getX(), motionEvent.getY());
        switch (actionMasked) {
            case 0:
                if (contains) {
                    setCloseIconPressed(true);
                    z = true;
                    break;
                }
                z = false;
                break;
            case 1:
                if (this.k) {
                    performCloseIconClick();
                    z = true;
                    setCloseIconPressed(false);
                    break;
                }
                z = false;
                setCloseIconPressed(false);
            case 2:
                if (this.k) {
                    if (!contains) {
                        setCloseIconPressed(false);
                    }
                    z = true;
                    break;
                }
                z = false;
                break;
            case 3:
                z = false;
                setCloseIconPressed(false);
                break;
            default:
                z = false;
                break;
        }
        return z || super.onTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onHoverEvent(@NonNull MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 7) {
            setCloseIconHovered(getCloseIconTouchBounds().contains(motionEvent.getX(), motionEvent.getY()));
        } else if (actionMasked == 10) {
            setCloseIconHovered(false);
        }
        return super.onHoverEvent(motionEvent);
    }

    @SuppressLint({"PrivateApi"})
    private boolean a(@NonNull MotionEvent motionEvent) {
        if (motionEvent.getAction() == 10) {
            try {
                Field declaredField = ExploreByTouchHelper.class.getDeclaredField("k");
                declaredField.setAccessible(true);
                if (((Integer) declaredField.get(this.q)).intValue() != Integer.MIN_VALUE) {
                    Method declaredMethod = ExploreByTouchHelper.class.getDeclaredMethod(ai.aD, Integer.TYPE);
                    declaredMethod.setAccessible(true);
                    declaredMethod.invoke(this.q, Integer.MIN_VALUE);
                    return true;
                }
            } catch (IllegalAccessException e) {
                Log.e("Chip", "Unable to send Accessibility Exit event", e);
            } catch (NoSuchFieldException e2) {
                Log.e("Chip", "Unable to send Accessibility Exit event", e2);
            } catch (NoSuchMethodException e3) {
                Log.e("Chip", "Unable to send Accessibility Exit event", e3);
            } catch (InvocationTargetException e4) {
                Log.e("Chip", "Unable to send Accessibility Exit event", e4);
            }
        }
        return false;
    }

    @Override // android.view.View
    protected boolean dispatchHoverEvent(@NonNull MotionEvent motionEvent) {
        return a(motionEvent) || this.q.dispatchHoverEvent(motionEvent) || super.dispatchHoverEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (!this.q.dispatchKeyEvent(keyEvent) || this.q.getKeyboardFocusedVirtualViewId() == Integer.MIN_VALUE) {
            return super.dispatchKeyEvent(keyEvent);
        }
        return true;
    }

    @Override // android.widget.TextView, android.view.View
    protected void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
        this.q.onFocusChanged(z, i, rect);
    }

    @Override // android.widget.TextView, android.view.View
    public void getFocusedRect(@NonNull Rect rect) {
        if (this.q.getKeyboardFocusedVirtualViewId() == 1 || this.q.getAccessibilityFocusedVirtualViewId() == 1) {
            rect.set(getCloseIconTouchBoundsInt());
        } else {
            super.getFocusedRect(rect);
        }
    }

    private void setCloseIconPressed(boolean z) {
        if (this.k != z) {
            this.k = z;
            refreshDrawableState();
        }
    }

    private void setCloseIconHovered(boolean z) {
        if (this.l != z) {
            this.l = z;
            refreshDrawableState();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.widget.AppCompatCheckBox, android.widget.CompoundButton, android.widget.TextView, android.view.View
    public void drawableStateChanged() {
        super.drawableStateChanged();
        ChipDrawable chipDrawable = this.e;
        if ((chipDrawable == null || !chipDrawable.isCloseIconStateful()) ? false : this.e.setCloseIconState(h())) {
            invalidate();
        }
    }

    @NonNull
    private int[] h() {
        int i = 0;
        int i2 = isEnabled() ? 1 : 0;
        if (this.m) {
            i2++;
        }
        if (this.l) {
            i2++;
        }
        if (this.k) {
            i2++;
        }
        if (isChecked()) {
            i2++;
        }
        int[] iArr = new int[i2];
        if (isEnabled()) {
            iArr[0] = 16842910;
            i = 1;
        }
        if (this.m) {
            iArr[i] = 16842908;
            i++;
        }
        if (this.l) {
            iArr[i] = 16843623;
            i++;
        }
        if (this.k) {
            iArr[i] = 16842919;
            i++;
        }
        if (isChecked()) {
            iArr[i] = 16842913;
        }
        return iArr;
    }

    public boolean i() {
        ChipDrawable chipDrawable = this.e;
        return (chipDrawable == null || chipDrawable.getCloseIcon() == null) ? false : true;
    }

    @NonNull
    public RectF getCloseIconTouchBounds() {
        this.s.setEmpty();
        if (i() && this.h != null) {
            this.e.getCloseIconTouchBounds(this.s);
        }
        return this.s;
    }

    @NonNull
    public Rect getCloseIconTouchBoundsInt() {
        RectF closeIconTouchBounds = getCloseIconTouchBounds();
        this.r.set((int) closeIconTouchBounds.left, (int) closeIconTouchBounds.top, (int) closeIconTouchBounds.right, (int) closeIconTouchBounds.bottom);
        return this.r;
    }

    @Override // android.widget.Button, android.widget.TextView, android.view.View
    @Nullable
    @TargetApi(24)
    public PointerIcon onResolvePointerIcon(@NonNull MotionEvent motionEvent, int i) {
        if (!getCloseIconTouchBounds().contains(motionEvent.getX(), motionEvent.getY()) || !isEnabled()) {
            return null;
        }
        return PointerIcon.getSystemIcon(getContext(), 1002);
    }

    /* loaded from: classes2.dex */
    public class a extends ExploreByTouchHelper {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a(Chip chip) {
            super(chip);
            Chip.this = r1;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected int getVirtualViewAt(float f, float f2) {
            return (!Chip.this.i() || !Chip.this.getCloseIconTouchBounds().contains(f, f2)) ? 0 : 1;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected void getVisibleVirtualViews(@NonNull List<Integer> list) {
            list.add(0);
            if (Chip.this.i() && Chip.this.isCloseIconVisible() && Chip.this.h != null) {
                list.add(1);
            }
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected void onVirtualViewKeyboardFocusChanged(int i, boolean z) {
            if (i == 1) {
                Chip.this.m = z;
                Chip.this.refreshDrawableState();
            }
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected void onPopulateNodeForVirtualView(int i, @NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            if (i == 1) {
                CharSequence closeIconContentDescription = Chip.this.getCloseIconContentDescription();
                if (closeIconContentDescription != null) {
                    accessibilityNodeInfoCompat.setContentDescription(closeIconContentDescription);
                } else {
                    CharSequence text = Chip.this.getText();
                    Context context = Chip.this.getContext();
                    int i2 = R.string.mtrl_chip_close_icon_content_description;
                    Object[] objArr = new Object[1];
                    if (TextUtils.isEmpty(text)) {
                        text = "";
                    }
                    objArr[0] = text;
                    accessibilityNodeInfoCompat.setContentDescription(context.getString(i2, objArr).trim());
                }
                accessibilityNodeInfoCompat.setBoundsInParent(Chip.this.getCloseIconTouchBoundsInt());
                accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
                accessibilityNodeInfoCompat.setEnabled(Chip.this.isEnabled());
                return;
            }
            accessibilityNodeInfoCompat.setContentDescription("");
            accessibilityNodeInfoCompat.setBoundsInParent(Chip.b);
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected void onPopulateNodeForHost(@NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            accessibilityNodeInfoCompat.setCheckable(Chip.this.isCheckable());
            accessibilityNodeInfoCompat.setClickable(Chip.this.isClickable());
            if (Chip.this.isCheckable() || Chip.this.isClickable()) {
                accessibilityNodeInfoCompat.setClassName(Chip.this.isCheckable() ? "android.widget.CompoundButton" : "android.widget.Button");
            } else {
                accessibilityNodeInfoCompat.setClassName("android.view.View");
            }
            CharSequence text = Chip.this.getText();
            if (Build.VERSION.SDK_INT >= 23) {
                accessibilityNodeInfoCompat.setText(text);
            } else {
                accessibilityNodeInfoCompat.setContentDescription(text);
            }
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected boolean onPerformActionForVirtualView(int i, int i2, Bundle bundle) {
            if (i2 != 16) {
                return false;
            }
            if (i == 0) {
                return Chip.this.performClick();
            }
            if (i == 1) {
                return Chip.this.performCloseIconClick();
            }
            return false;
        }
    }

    @Nullable
    public ColorStateList getChipBackgroundColor() {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            return chipDrawable.getChipBackgroundColor();
        }
        return null;
    }

    public void setChipBackgroundColorResource(@ColorRes int i) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setChipBackgroundColorResource(i);
        }
    }

    public void setChipBackgroundColor(@Nullable ColorStateList colorStateList) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setChipBackgroundColor(colorStateList);
        }
    }

    public float getChipMinHeight() {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            return chipDrawable.getChipMinHeight();
        }
        return 0.0f;
    }

    public void setChipMinHeightResource(@DimenRes int i) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setChipMinHeightResource(i);
        }
    }

    public void setChipMinHeight(float f) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setChipMinHeight(f);
        }
    }

    public float getChipCornerRadius() {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            return Math.max(0.0f, chipDrawable.getChipCornerRadius());
        }
        return 0.0f;
    }

    @Deprecated
    public void setChipCornerRadiusResource(@DimenRes int i) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setChipCornerRadiusResource(i);
        }
    }

    @Override // com.google.android.material.shape.Shapeable
    public void setShapeAppearanceModel(@NonNull ShapeAppearanceModel shapeAppearanceModel) {
        this.e.setShapeAppearanceModel(shapeAppearanceModel);
    }

    @Override // com.google.android.material.shape.Shapeable
    @NonNull
    public ShapeAppearanceModel getShapeAppearanceModel() {
        return this.e.getShapeAppearanceModel();
    }

    @Deprecated
    public void setChipCornerRadius(float f) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setChipCornerRadius(f);
        }
    }

    @Nullable
    public ColorStateList getChipStrokeColor() {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            return chipDrawable.getChipStrokeColor();
        }
        return null;
    }

    public void setChipStrokeColorResource(@ColorRes int i) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setChipStrokeColorResource(i);
        }
    }

    public void setChipStrokeColor(@Nullable ColorStateList colorStateList) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setChipStrokeColor(colorStateList);
        }
    }

    public float getChipStrokeWidth() {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            return chipDrawable.getChipStrokeWidth();
        }
        return 0.0f;
    }

    public void setChipStrokeWidthResource(@DimenRes int i) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setChipStrokeWidthResource(i);
        }
    }

    public void setChipStrokeWidth(float f) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setChipStrokeWidth(f);
        }
    }

    @Nullable
    public ColorStateList getRippleColor() {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            return chipDrawable.getRippleColor();
        }
        return null;
    }

    public void setRippleColorResource(@ColorRes int i) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setRippleColorResource(i);
            if (!this.e.getUseCompatRipple()) {
                g();
            }
        }
    }

    public void setRippleColor(@Nullable ColorStateList colorStateList) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setRippleColor(colorStateList);
        }
        if (!this.e.getUseCompatRipple()) {
            g();
        }
    }

    @Deprecated
    public CharSequence getChipText() {
        return getText();
    }

    @Override // android.view.View
    public void setLayoutDirection(int i) {
        if (this.e != null && Build.VERSION.SDK_INT >= 17) {
            super.setLayoutDirection(i);
        }
    }

    @Override // android.widget.TextView
    public void setText(CharSequence charSequence, TextView.BufferType bufferType) {
        if (this.e != null) {
            if (charSequence == null) {
                charSequence = "";
            }
            super.setText(this.e.c() ? null : charSequence, bufferType);
            ChipDrawable chipDrawable = this.e;
            if (chipDrawable != null) {
                chipDrawable.setText(charSequence);
            }
        }
    }

    @Deprecated
    public void setChipTextResource(@StringRes int i) {
        setText(getResources().getString(i));
    }

    @Deprecated
    public void setChipText(@Nullable CharSequence charSequence) {
        setText(charSequence);
    }

    public void setTextAppearanceResource(@StyleRes int i) {
        setTextAppearance(getContext(), i);
    }

    public void setTextAppearance(@Nullable TextAppearance textAppearance) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setTextAppearance(textAppearance);
        }
        j();
    }

    @Override // android.widget.TextView
    public void setTextAppearance(Context context, int i) {
        super.setTextAppearance(context, i);
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setTextAppearanceResource(i);
        }
        j();
    }

    @Override // android.widget.TextView
    public void setTextAppearance(int i) {
        super.setTextAppearance(i);
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setTextAppearanceResource(i);
        }
        j();
    }

    private void j() {
        TextPaint paint = getPaint();
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            paint.drawableState = chipDrawable.getState();
        }
        TextAppearance textAppearance = getTextAppearance();
        if (textAppearance != null) {
            textAppearance.updateDrawState(getContext(), paint, this.t);
        }
    }

    @Nullable
    private TextAppearance getTextAppearance() {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            return chipDrawable.getTextAppearance();
        }
        return null;
    }

    public boolean isChipIconVisible() {
        ChipDrawable chipDrawable = this.e;
        return chipDrawable != null && chipDrawable.isChipIconVisible();
    }

    @Deprecated
    public boolean isChipIconEnabled() {
        return isChipIconVisible();
    }

    public void setChipIconVisible(@BoolRes int i) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setChipIconVisible(i);
        }
    }

    public void setChipIconVisible(boolean z) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setChipIconVisible(z);
        }
    }

    @Deprecated
    public void setChipIconEnabledResource(@BoolRes int i) {
        setChipIconVisible(i);
    }

    @Deprecated
    public void setChipIconEnabled(boolean z) {
        setChipIconVisible(z);
    }

    @Nullable
    public Drawable getChipIcon() {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            return chipDrawable.getChipIcon();
        }
        return null;
    }

    public void setChipIconResource(@DrawableRes int i) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setChipIconResource(i);
        }
    }

    public void setChipIcon(@Nullable Drawable drawable) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setChipIcon(drawable);
        }
    }

    @Nullable
    public ColorStateList getChipIconTint() {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            return chipDrawable.getChipIconTint();
        }
        return null;
    }

    public void setChipIconTintResource(@ColorRes int i) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setChipIconTintResource(i);
        }
    }

    public void setChipIconTint(@Nullable ColorStateList colorStateList) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setChipIconTint(colorStateList);
        }
    }

    public float getChipIconSize() {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            return chipDrawable.getChipIconSize();
        }
        return 0.0f;
    }

    public void setChipIconSizeResource(@DimenRes int i) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setChipIconSizeResource(i);
        }
    }

    public void setChipIconSize(float f) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setChipIconSize(f);
        }
    }

    public boolean isCloseIconVisible() {
        ChipDrawable chipDrawable = this.e;
        return chipDrawable != null && chipDrawable.isCloseIconVisible();
    }

    @Deprecated
    public boolean isCloseIconEnabled() {
        return isCloseIconVisible();
    }

    public void setCloseIconVisible(@BoolRes int i) {
        setCloseIconVisible(getResources().getBoolean(i));
    }

    public void setCloseIconVisible(boolean z) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setCloseIconVisible(z);
        }
        b();
    }

    @Deprecated
    public void setCloseIconEnabledResource(@BoolRes int i) {
        setCloseIconVisible(i);
    }

    @Deprecated
    public void setCloseIconEnabled(boolean z) {
        setCloseIconVisible(z);
    }

    @Nullable
    public Drawable getCloseIcon() {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            return chipDrawable.getCloseIcon();
        }
        return null;
    }

    public void setCloseIconResource(@DrawableRes int i) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setCloseIconResource(i);
        }
        b();
    }

    public void setCloseIcon(@Nullable Drawable drawable) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setCloseIcon(drawable);
        }
        b();
    }

    @Nullable
    public ColorStateList getCloseIconTint() {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            return chipDrawable.getCloseIconTint();
        }
        return null;
    }

    public void setCloseIconTintResource(@ColorRes int i) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setCloseIconTintResource(i);
        }
    }

    public void setCloseIconTint(@Nullable ColorStateList colorStateList) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setCloseIconTint(colorStateList);
        }
    }

    public float getCloseIconSize() {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            return chipDrawable.getCloseIconSize();
        }
        return 0.0f;
    }

    public void setCloseIconSizeResource(@DimenRes int i) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setCloseIconSizeResource(i);
        }
    }

    public void setCloseIconSize(float f) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setCloseIconSize(f);
        }
    }

    public void setCloseIconContentDescription(@Nullable CharSequence charSequence) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setCloseIconContentDescription(charSequence);
        }
    }

    @Nullable
    public CharSequence getCloseIconContentDescription() {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            return chipDrawable.getCloseIconContentDescription();
        }
        return null;
    }

    public boolean isCheckable() {
        ChipDrawable chipDrawable = this.e;
        return chipDrawable != null && chipDrawable.isCheckable();
    }

    public void setCheckableResource(@BoolRes int i) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setCheckableResource(i);
        }
    }

    public void setCheckable(boolean z) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setCheckable(z);
        }
    }

    public boolean isCheckedIconVisible() {
        ChipDrawable chipDrawable = this.e;
        return chipDrawable != null && chipDrawable.isCheckedIconVisible();
    }

    @Deprecated
    public boolean isCheckedIconEnabled() {
        return isCheckedIconVisible();
    }

    public void setCheckedIconVisible(@BoolRes int i) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setCheckedIconVisible(i);
        }
    }

    public void setCheckedIconVisible(boolean z) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setCheckedIconVisible(z);
        }
    }

    @Deprecated
    public void setCheckedIconEnabledResource(@BoolRes int i) {
        setCheckedIconVisible(i);
    }

    @Deprecated
    public void setCheckedIconEnabled(boolean z) {
        setCheckedIconVisible(z);
    }

    @Nullable
    public Drawable getCheckedIcon() {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            return chipDrawable.getCheckedIcon();
        }
        return null;
    }

    public void setCheckedIconResource(@DrawableRes int i) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setCheckedIconResource(i);
        }
    }

    public void setCheckedIcon(@Nullable Drawable drawable) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setCheckedIcon(drawable);
        }
    }

    @Nullable
    public ColorStateList getCheckedIconTint() {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            return chipDrawable.getCheckedIconTint();
        }
        return null;
    }

    public void setCheckedIconTintResource(@ColorRes int i) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setCheckedIconTintResource(i);
        }
    }

    public void setCheckedIconTint(@Nullable ColorStateList colorStateList) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setCheckedIconTint(colorStateList);
        }
    }

    @Nullable
    public MotionSpec getShowMotionSpec() {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            return chipDrawable.getShowMotionSpec();
        }
        return null;
    }

    public void setShowMotionSpecResource(@AnimatorRes int i) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setShowMotionSpecResource(i);
        }
    }

    public void setShowMotionSpec(@Nullable MotionSpec motionSpec) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setShowMotionSpec(motionSpec);
        }
    }

    @Nullable
    public MotionSpec getHideMotionSpec() {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            return chipDrawable.getHideMotionSpec();
        }
        return null;
    }

    public void setHideMotionSpecResource(@AnimatorRes int i) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setHideMotionSpecResource(i);
        }
    }

    public void setHideMotionSpec(@Nullable MotionSpec motionSpec) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setHideMotionSpec(motionSpec);
        }
    }

    public float getChipStartPadding() {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            return chipDrawable.getChipStartPadding();
        }
        return 0.0f;
    }

    public void setChipStartPaddingResource(@DimenRes int i) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setChipStartPaddingResource(i);
        }
    }

    public void setChipStartPadding(float f) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setChipStartPadding(f);
        }
    }

    public float getIconStartPadding() {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            return chipDrawable.getIconStartPadding();
        }
        return 0.0f;
    }

    public void setIconStartPaddingResource(@DimenRes int i) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setIconStartPaddingResource(i);
        }
    }

    public void setIconStartPadding(float f) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setIconStartPadding(f);
        }
    }

    public float getIconEndPadding() {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            return chipDrawable.getIconEndPadding();
        }
        return 0.0f;
    }

    public void setIconEndPaddingResource(@DimenRes int i) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setIconEndPaddingResource(i);
        }
    }

    public void setIconEndPadding(float f) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setIconEndPadding(f);
        }
    }

    public float getTextStartPadding() {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            return chipDrawable.getTextStartPadding();
        }
        return 0.0f;
    }

    public void setTextStartPaddingResource(@DimenRes int i) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setTextStartPaddingResource(i);
        }
    }

    public void setTextStartPadding(float f) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setTextStartPadding(f);
        }
    }

    public float getTextEndPadding() {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            return chipDrawable.getTextEndPadding();
        }
        return 0.0f;
    }

    public void setTextEndPaddingResource(@DimenRes int i) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setTextEndPaddingResource(i);
        }
    }

    public void setTextEndPadding(float f) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setTextEndPadding(f);
        }
    }

    public float getCloseIconStartPadding() {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            return chipDrawable.getCloseIconStartPadding();
        }
        return 0.0f;
    }

    public void setCloseIconStartPaddingResource(@DimenRes int i) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setCloseIconStartPaddingResource(i);
        }
    }

    public void setCloseIconStartPadding(float f) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setCloseIconStartPadding(f);
        }
    }

    public float getCloseIconEndPadding() {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            return chipDrawable.getCloseIconEndPadding();
        }
        return 0.0f;
    }

    public void setCloseIconEndPaddingResource(@DimenRes int i) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setCloseIconEndPaddingResource(i);
        }
    }

    public void setCloseIconEndPadding(float f) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setCloseIconEndPadding(f);
        }
    }

    public float getChipEndPadding() {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            return chipDrawable.getChipEndPadding();
        }
        return 0.0f;
    }

    public void setChipEndPaddingResource(@DimenRes int i) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setChipEndPaddingResource(i);
        }
    }

    public void setChipEndPadding(float f) {
        ChipDrawable chipDrawable = this.e;
        if (chipDrawable != null) {
            chipDrawable.setChipEndPadding(f);
        }
    }

    public boolean shouldEnsureMinTouchTargetSize() {
        return this.n;
    }

    public void setEnsureMinTouchTargetSize(boolean z) {
        this.n = z;
        ensureAccessibleTouchTarget(this.p);
    }

    public boolean ensureAccessibleTouchTarget(@Dimension int i) {
        this.p = i;
        int i2 = 0;
        if (!shouldEnsureMinTouchTargetSize()) {
            if (this.f != null) {
                k();
            } else {
                e();
            }
            return false;
        }
        int max = Math.max(0, i - this.e.getIntrinsicHeight());
        int max2 = Math.max(0, i - this.e.getIntrinsicWidth());
        if (max2 > 0 || max > 0) {
            int i3 = max2 > 0 ? max2 / 2 : 0;
            if (max > 0) {
                i2 = max / 2;
            }
            if (this.f != null) {
                Rect rect = new Rect();
                this.f.getPadding(rect);
                if (rect.top == i2 && rect.bottom == i2 && rect.left == i3 && rect.right == i3) {
                    e();
                    return true;
                }
            }
            if (Build.VERSION.SDK_INT >= 16) {
                if (getMinHeight() != i) {
                    setMinHeight(i);
                }
                if (getMinWidth() != i) {
                    setMinWidth(i);
                }
            } else {
                setMinHeight(i);
                setMinWidth(i);
            }
            a(i3, i2, i3, i2);
            e();
            return true;
        }
        if (this.f != null) {
            k();
        } else {
            e();
        }
        return false;
    }

    private void k() {
        if (this.f != null) {
            this.f = null;
            setMinWidth(0);
            setMinHeight((int) getChipMinHeight());
            e();
        }
    }

    private void a(int i, int i2, int i3, int i4) {
        this.f = new InsetDrawable((Drawable) this.e, i, i2, i3, i4);
    }
}
