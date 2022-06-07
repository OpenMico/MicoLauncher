package com.google.android.material.button;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.CompoundButton;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.TextViewCompat;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.R;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import java.util.LinkedHashSet;

/* loaded from: classes2.dex */
public class MaterialButton extends AppCompatButton implements Checkable, Shapeable {
    public static final int ICON_GRAVITY_END = 3;
    public static final int ICON_GRAVITY_START = 1;
    public static final int ICON_GRAVITY_TEXT_END = 4;
    public static final int ICON_GRAVITY_TEXT_START = 2;
    public static final int ICON_GRAVITY_TEXT_TOP = 32;
    public static final int ICON_GRAVITY_TOP = 16;
    private static final int[] a = {16842911};
    private static final int[] b = {16842912};
    private static final int c = R.style.Widget_MaterialComponents_Button;
    @NonNull
    private final a d;
    @NonNull
    private final LinkedHashSet<OnCheckedChangeListener> e;
    @Nullable
    private a f;
    @Nullable
    private PorterDuff.Mode g;
    @Nullable
    private ColorStateList h;
    @Nullable
    private Drawable i;
    @Px
    private int j;
    @Px
    private int k;
    @Px
    private int l;
    @Px
    private int m;
    private boolean n;
    private boolean o;
    private int p;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface IconGravity {
    }

    /* loaded from: classes2.dex */
    public interface OnCheckedChangeListener {
        void onCheckedChanged(MaterialButton materialButton, boolean z);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public interface a {
        void a(MaterialButton materialButton, boolean z);
    }

    public MaterialButton(@NonNull Context context) {
        this(context, null);
    }

    public MaterialButton(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.materialButtonStyle);
    }

    public MaterialButton(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, c), attributeSet, i);
        this.e = new LinkedHashSet<>();
        boolean z = false;
        this.n = false;
        this.o = false;
        Context context2 = getContext();
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R.styleable.MaterialButton, i, c, new int[0]);
        this.m = obtainStyledAttributes.getDimensionPixelSize(R.styleable.MaterialButton_iconPadding, 0);
        this.g = ViewUtils.parseTintMode(obtainStyledAttributes.getInt(R.styleable.MaterialButton_iconTintMode, -1), PorterDuff.Mode.SRC_IN);
        this.h = MaterialResources.getColorStateList(getContext(), obtainStyledAttributes, R.styleable.MaterialButton_iconTint);
        this.i = MaterialResources.getDrawable(getContext(), obtainStyledAttributes, R.styleable.MaterialButton_icon);
        this.p = obtainStyledAttributes.getInteger(R.styleable.MaterialButton_iconGravity, 1);
        this.j = obtainStyledAttributes.getDimensionPixelSize(R.styleable.MaterialButton_iconSize, 0);
        this.d = new a(this, ShapeAppearanceModel.builder(context2, attributeSet, i, c).build());
        this.d.a(obtainStyledAttributes);
        obtainStyledAttributes.recycle();
        setCompoundDrawablePadding(this.m);
        a(this.i != null ? true : z);
    }

    @NonNull
    private String getA11yClassName() {
        return (isCheckable() ? CompoundButton.class : Button.class).getName();
    }

    @Override // androidx.appcompat.widget.AppCompatButton, android.view.View
    public void onInitializeAccessibilityNodeInfo(@NonNull AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(getA11yClassName());
        accessibilityNodeInfo.setCheckable(isCheckable());
        accessibilityNodeInfo.setChecked(isChecked());
        accessibilityNodeInfo.setClickable(isClickable());
    }

    @Override // androidx.appcompat.widget.AppCompatButton, android.view.View
    public void onInitializeAccessibilityEvent(@NonNull AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(getA11yClassName());
        accessibilityEvent.setChecked(isChecked());
    }

    @Override // android.widget.TextView, android.view.View
    @NonNull
    public Parcelable onSaveInstanceState() {
        b bVar = new b(super.onSaveInstanceState());
        bVar.a = this.n;
        return bVar;
    }

    @Override // android.widget.TextView, android.view.View
    public void onRestoreInstanceState(@Nullable Parcelable parcelable) {
        if (!(parcelable instanceof b)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        b bVar = (b) parcelable;
        super.onRestoreInstanceState(bVar.getSuperState());
        setChecked(bVar.a);
    }

    @Override // androidx.appcompat.widget.AppCompatButton, androidx.core.view.TintableBackgroundView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setSupportBackgroundTintList(@Nullable ColorStateList colorStateList) {
        if (f()) {
            this.d.a(colorStateList);
        } else {
            super.setSupportBackgroundTintList(colorStateList);
        }
    }

    @Override // androidx.appcompat.widget.AppCompatButton, androidx.core.view.TintableBackgroundView
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public ColorStateList getSupportBackgroundTintList() {
        if (f()) {
            return this.d.c();
        }
        return super.getSupportBackgroundTintList();
    }

    @Override // androidx.appcompat.widget.AppCompatButton, androidx.core.view.TintableBackgroundView
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setSupportBackgroundTintMode(@Nullable PorterDuff.Mode mode) {
        if (f()) {
            this.d.a(mode);
        } else {
            super.setSupportBackgroundTintMode(mode);
        }
    }

    @Override // androidx.appcompat.widget.AppCompatButton, androidx.core.view.TintableBackgroundView
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        if (f()) {
            return this.d.d();
        }
        return super.getSupportBackgroundTintMode();
    }

    @Override // android.view.View
    public void setBackgroundTintList(@Nullable ColorStateList colorStateList) {
        setSupportBackgroundTintList(colorStateList);
    }

    @Override // android.view.View
    @Nullable
    public ColorStateList getBackgroundTintList() {
        return getSupportBackgroundTintList();
    }

    @Override // android.view.View
    public void setBackgroundTintMode(@Nullable PorterDuff.Mode mode) {
        setSupportBackgroundTintMode(mode);
    }

    @Override // android.view.View
    @Nullable
    public PorterDuff.Mode getBackgroundTintMode() {
        return getSupportBackgroundTintMode();
    }

    @Override // android.view.View
    public void setBackgroundColor(@ColorInt int i) {
        if (f()) {
            this.d.a(i);
        } else {
            super.setBackgroundColor(i);
        }
    }

    @Override // android.view.View
    public void setBackground(@NonNull Drawable drawable) {
        setBackgroundDrawable(drawable);
    }

    @Override // androidx.appcompat.widget.AppCompatButton, android.view.View
    public void setBackgroundResource(@DrawableRes int i) {
        setBackgroundDrawable(i != 0 ? AppCompatResources.getDrawable(getContext(), i) : null);
    }

    @Override // androidx.appcompat.widget.AppCompatButton, android.view.View
    public void setBackgroundDrawable(@NonNull Drawable drawable) {
        if (!f()) {
            super.setBackgroundDrawable(drawable);
        } else if (drawable != getBackground()) {
            Log.w("MaterialButton", "MaterialButton manages its own background to control elevation, shape, color and states. Consider using backgroundTint, shapeAppearance and other attributes where available. A custom background will ignore these attributes and you should consider handling interaction states such as pressed, focused and disabled");
            this.d.a();
            super.setBackgroundDrawable(drawable);
        } else {
            getBackground().setState(drawable.getState());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.widget.AppCompatButton, android.widget.TextView, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        a aVar;
        super.onLayout(z, i, i2, i3, i4);
        if (Build.VERSION.SDK_INT == 21 && (aVar = this.d) != null) {
            aVar.a(i4 - i2, i3 - i);
        }
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        a(i, i2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.widget.AppCompatButton, android.widget.TextView
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        super.onTextChanged(charSequence, i, i2, i3);
        a(getMeasuredWidth(), getMeasuredHeight());
    }

    @Override // android.widget.TextView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (f()) {
            MaterialShapeUtils.setParentAbsoluteElevation(this, this.d.i());
        }
    }

    @Override // android.view.View
    @RequiresApi(21)
    public void setElevation(float f) {
        super.setElevation(f);
        if (f()) {
            this.d.i().setElevation(f);
        }
    }

    private void a(int i, int i2) {
        if (this.i != null && getLayout() != null) {
            if (c() || d()) {
                this.l = 0;
                int i3 = this.p;
                boolean z = true;
                if (i3 == 1 || i3 == 3) {
                    this.k = 0;
                    a(false);
                    return;
                }
                int i4 = this.j;
                if (i4 == 0) {
                    i4 = this.i.getIntrinsicWidth();
                }
                int textWidth = (((((i - getTextWidth()) - ViewCompat.getPaddingEnd(this)) - i4) - this.m) - ViewCompat.getPaddingStart(this)) / 2;
                boolean a2 = a();
                if (this.p != 4) {
                    z = false;
                }
                if (a2 != z) {
                    textWidth = -textWidth;
                }
                if (this.k != textWidth) {
                    this.k = textWidth;
                    a(false);
                }
            } else if (e()) {
                this.k = 0;
                if (this.p == 16) {
                    this.l = 0;
                    a(false);
                    return;
                }
                int i5 = this.j;
                if (i5 == 0) {
                    i5 = this.i.getIntrinsicHeight();
                }
                int textHeight = (((((i2 - getTextHeight()) - getPaddingTop()) - i5) - this.m) - getPaddingBottom()) / 2;
                if (this.l != textHeight) {
                    this.l = textHeight;
                    a(false);
                }
            }
        }
    }

    private int getTextWidth() {
        TextPaint paint = getPaint();
        String charSequence = getText().toString();
        if (getTransformationMethod() != null) {
            charSequence = getTransformationMethod().getTransformation(charSequence, this).toString();
        }
        return Math.min((int) paint.measureText(charSequence), getLayout().getEllipsizedWidth());
    }

    private int getTextHeight() {
        TextPaint paint = getPaint();
        String charSequence = getText().toString();
        if (getTransformationMethod() != null) {
            charSequence = getTransformationMethod().getTransformation(charSequence, this).toString();
        }
        Rect rect = new Rect();
        paint.getTextBounds(charSequence, 0, charSequence.length(), rect);
        return Math.min(rect.height(), getLayout().getHeight());
    }

    private boolean a() {
        return ViewCompat.getLayoutDirection(this) == 1;
    }

    public void setInternalBackground(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
    }

    public void setIconPadding(@Px int i) {
        if (this.m != i) {
            this.m = i;
            setCompoundDrawablePadding(i);
        }
    }

    @Px
    public int getIconPadding() {
        return this.m;
    }

    public void setIconSize(@Px int i) {
        if (i < 0) {
            throw new IllegalArgumentException("iconSize cannot be less than 0");
        } else if (this.j != i) {
            this.j = i;
            a(true);
        }
    }

    @Px
    public int getIconSize() {
        return this.j;
    }

    public void setIcon(@Nullable Drawable drawable) {
        if (this.i != drawable) {
            this.i = drawable;
            a(true);
            a(getMeasuredWidth(), getMeasuredHeight());
        }
    }

    public void setIconResource(@DrawableRes int i) {
        setIcon(i != 0 ? AppCompatResources.getDrawable(getContext(), i) : null);
    }

    public Drawable getIcon() {
        return this.i;
    }

    public void setIconTint(@Nullable ColorStateList colorStateList) {
        if (this.h != colorStateList) {
            this.h = colorStateList;
            a(false);
        }
    }

    public void setIconTintResource(@ColorRes int i) {
        setIconTint(AppCompatResources.getColorStateList(getContext(), i));
    }

    public ColorStateList getIconTint() {
        return this.h;
    }

    public void setIconTintMode(PorterDuff.Mode mode) {
        if (this.g != mode) {
            this.g = mode;
            a(false);
        }
    }

    public PorterDuff.Mode getIconTintMode() {
        return this.g;
    }

    private void a(boolean z) {
        Drawable drawable = this.i;
        if (drawable != null) {
            this.i = DrawableCompat.wrap(drawable).mutate();
            DrawableCompat.setTintList(this.i, this.h);
            PorterDuff.Mode mode = this.g;
            if (mode != null) {
                DrawableCompat.setTintMode(this.i, mode);
            }
            int i = this.j;
            if (i == 0) {
                i = this.i.getIntrinsicWidth();
            }
            int i2 = this.j;
            if (i2 == 0) {
                i2 = this.i.getIntrinsicHeight();
            }
            Drawable drawable2 = this.i;
            int i3 = this.k;
            int i4 = this.l;
            drawable2.setBounds(i3, i4, i + i3, i2 + i4);
        }
        if (z) {
            b();
            return;
        }
        Drawable[] compoundDrawablesRelative = TextViewCompat.getCompoundDrawablesRelative(this);
        boolean z2 = false;
        Drawable drawable3 = compoundDrawablesRelative[0];
        Drawable drawable4 = compoundDrawablesRelative[1];
        Drawable drawable5 = compoundDrawablesRelative[2];
        if ((c() && drawable3 != this.i) || ((d() && drawable5 != this.i) || (e() && drawable4 != this.i))) {
            z2 = true;
        }
        if (z2) {
            b();
        }
    }

    private void b() {
        if (c()) {
            TextViewCompat.setCompoundDrawablesRelative(this, this.i, null, null, null);
        } else if (d()) {
            TextViewCompat.setCompoundDrawablesRelative(this, null, null, this.i, null);
        } else if (e()) {
            TextViewCompat.setCompoundDrawablesRelative(this, null, this.i, null, null);
        }
    }

    private boolean c() {
        int i = this.p;
        return i == 1 || i == 2;
    }

    private boolean d() {
        int i = this.p;
        return i == 3 || i == 4;
    }

    private boolean e() {
        int i = this.p;
        return i == 16 || i == 32;
    }

    public void setRippleColor(@Nullable ColorStateList colorStateList) {
        if (f()) {
            this.d.b(colorStateList);
        }
    }

    public void setRippleColorResource(@ColorRes int i) {
        if (f()) {
            setRippleColor(AppCompatResources.getColorStateList(getContext(), i));
        }
    }

    @Nullable
    public ColorStateList getRippleColor() {
        if (f()) {
            return this.d.e();
        }
        return null;
    }

    public void setStrokeColor(@Nullable ColorStateList colorStateList) {
        if (f()) {
            this.d.c(colorStateList);
        }
    }

    public void setStrokeColorResource(@ColorRes int i) {
        if (f()) {
            setStrokeColor(AppCompatResources.getColorStateList(getContext(), i));
        }
    }

    public ColorStateList getStrokeColor() {
        if (f()) {
            return this.d.f();
        }
        return null;
    }

    public void setStrokeWidth(@Px int i) {
        if (f()) {
            this.d.b(i);
        }
    }

    public void setStrokeWidthResource(@DimenRes int i) {
        if (f()) {
            setStrokeWidth(getResources().getDimensionPixelSize(i));
        }
    }

    @Px
    public int getStrokeWidth() {
        if (f()) {
            return this.d.g();
        }
        return 0;
    }

    public void setCornerRadius(@Px int i) {
        if (f()) {
            this.d.c(i);
        }
    }

    public void setCornerRadiusResource(@DimenRes int i) {
        if (f()) {
            setCornerRadius(getResources().getDimensionPixelSize(i));
        }
    }

    @Px
    public int getCornerRadius() {
        if (f()) {
            return this.d.h();
        }
        return 0;
    }

    public int getIconGravity() {
        return this.p;
    }

    public void setIconGravity(int i) {
        if (this.p != i) {
            this.p = i;
            a(getMeasuredWidth(), getMeasuredHeight());
        }
    }

    public void setInsetBottom(@Dimension int i) {
        this.d.d(i);
    }

    @Dimension
    public int getInsetBottom() {
        return this.d.m();
    }

    public void setInsetTop(@Dimension int i) {
        this.d.e(i);
    }

    @Dimension
    public int getInsetTop() {
        return this.d.n();
    }

    @Override // android.widget.TextView, android.view.View
    protected int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 2);
        if (isCheckable()) {
            mergeDrawableStates(onCreateDrawableState, a);
        }
        if (isChecked()) {
            mergeDrawableStates(onCreateDrawableState, b);
        }
        return onCreateDrawableState;
    }

    public void addOnCheckedChangeListener(@NonNull OnCheckedChangeListener onCheckedChangeListener) {
        this.e.add(onCheckedChangeListener);
    }

    public void removeOnCheckedChangeListener(@NonNull OnCheckedChangeListener onCheckedChangeListener) {
        this.e.remove(onCheckedChangeListener);
    }

    public void clearOnCheckedChangeListeners() {
        this.e.clear();
    }

    @Override // android.widget.Checkable
    public void setChecked(boolean z) {
        if (isCheckable() && isEnabled() && this.n != z) {
            this.n = z;
            refreshDrawableState();
            if (!this.o) {
                this.o = true;
                Iterator<OnCheckedChangeListener> it = this.e.iterator();
                while (it.hasNext()) {
                    it.next().onCheckedChanged(this, this.n);
                }
                this.o = false;
            }
        }
    }

    @Override // android.widget.Checkable
    public boolean isChecked() {
        return this.n;
    }

    @Override // android.widget.Checkable
    public void toggle() {
        setChecked(!this.n);
    }

    @Override // android.view.View
    public boolean performClick() {
        toggle();
        return super.performClick();
    }

    public boolean isCheckable() {
        a aVar = this.d;
        return aVar != null && aVar.j();
    }

    public void setCheckable(boolean z) {
        if (f()) {
            this.d.b(z);
        }
    }

    @Override // com.google.android.material.shape.Shapeable
    public void setShapeAppearanceModel(@NonNull ShapeAppearanceModel shapeAppearanceModel) {
        if (f()) {
            this.d.a(shapeAppearanceModel);
            return;
        }
        throw new IllegalStateException("Attempted to set ShapeAppearanceModel on a MaterialButton which has an overwritten background.");
    }

    @Override // com.google.android.material.shape.Shapeable
    @NonNull
    public ShapeAppearanceModel getShapeAppearanceModel() {
        if (f()) {
            return this.d.l();
        }
        throw new IllegalStateException("Attempted to get ShapeAppearanceModel from a MaterialButton which has an overwritten background.");
    }

    public void setOnPressedChangeListenerInternal(@Nullable a aVar) {
        this.f = aVar;
    }

    @Override // android.view.View
    public void setPressed(boolean z) {
        a aVar = this.f;
        if (aVar != null) {
            aVar.a(this, z);
        }
        super.setPressed(z);
    }

    private boolean f() {
        a aVar = this.d;
        return aVar != null && !aVar.b();
    }

    public void setShouldDrawSurfaceColorStroke(boolean z) {
        if (f()) {
            this.d.a(z);
        }
    }

    /* loaded from: classes2.dex */
    public static class b extends AbsSavedState {
        public static final Parcelable.Creator<b> CREATOR = new Parcelable.ClassLoaderCreator<b>() { // from class: com.google.android.material.button.MaterialButton.b.1
            @NonNull
            /* renamed from: a */
            public b createFromParcel(@NonNull Parcel parcel, ClassLoader classLoader) {
                return new b(parcel, classLoader);
            }

            @NonNull
            /* renamed from: a */
            public b createFromParcel(@NonNull Parcel parcel) {
                return new b(parcel, null);
            }

            @NonNull
            /* renamed from: a */
            public b[] newArray(int i) {
                return new b[i];
            }
        };
        boolean a;

        public b(Parcelable parcelable) {
            super(parcelable);
        }

        public b(@NonNull Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            if (classLoader == null) {
                getClass().getClassLoader();
            }
            a(parcel);
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(@NonNull Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.a ? 1 : 0);
        }

        private void a(@NonNull Parcel parcel) {
            boolean z = true;
            if (parcel.readInt() != 1) {
                z = false;
            }
            this.a = z;
        }
    }
}
