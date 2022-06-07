package androidx.appcompat.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.Property;
import android.view.ActionMode;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CompoundButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.R;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.text.AllCapsTransformationMethod;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.TextViewCompat;

/* loaded from: classes.dex */
public class SwitchCompat extends CompoundButton {
    private int A;
    private int B;
    private int C;
    private int D;
    private int E;
    private int F;
    private int G;
    private final TextPaint H;
    private ColorStateList I;
    private Layout J;
    private Layout K;
    private TransformationMethod L;
    private final k M;
    private final Rect N;
    float a;
    ObjectAnimator b;
    private Drawable d;
    private ColorStateList e;
    private PorterDuff.Mode f;
    private boolean g;
    private boolean h;
    private Drawable i;
    private ColorStateList j;
    private PorterDuff.Mode k;
    private boolean l;
    private boolean m;
    private int n;
    private int o;
    private int p;
    private boolean q;
    private CharSequence r;
    private CharSequence s;
    private boolean t;
    private int u;
    private int v;
    private float w;
    private float x;
    private VelocityTracker y;
    private int z;
    private static final Property<SwitchCompat, Float> c = new Property<SwitchCompat, Float>(Float.class, "thumbPos") { // from class: androidx.appcompat.widget.SwitchCompat.1
        /* renamed from: a */
        public Float get(SwitchCompat switchCompat) {
            return Float.valueOf(switchCompat.a);
        }

        /* renamed from: a */
        public void set(SwitchCompat switchCompat, Float f) {
            switchCompat.setThumbPosition(f.floatValue());
        }
    };
    private static final int[] O = {16842912};

    private static float a(float f, float f2, float f3) {
        return f < f2 ? f2 : f > f3 ? f3 : f;
    }

    public SwitchCompat(@NonNull Context context) {
        this(context, null);
    }

    public SwitchCompat(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.switchStyle);
    }

    public SwitchCompat(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.e = null;
        this.f = null;
        this.g = false;
        this.h = false;
        this.j = null;
        this.k = null;
        this.l = false;
        this.m = false;
        this.y = VelocityTracker.obtain();
        this.N = new Rect();
        ThemeUtils.checkAppCompatTheme(this, getContext());
        this.H = new TextPaint(1);
        Resources resources = getResources();
        this.H.density = resources.getDisplayMetrics().density;
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, R.styleable.SwitchCompat, i, 0);
        ViewCompat.saveAttributeDataForStyleable(this, context, R.styleable.SwitchCompat, attributeSet, obtainStyledAttributes.getWrappedTypeArray(), i, 0);
        this.d = obtainStyledAttributes.getDrawable(R.styleable.SwitchCompat_android_thumb);
        Drawable drawable = this.d;
        if (drawable != null) {
            drawable.setCallback(this);
        }
        this.i = obtainStyledAttributes.getDrawable(R.styleable.SwitchCompat_track);
        Drawable drawable2 = this.i;
        if (drawable2 != null) {
            drawable2.setCallback(this);
        }
        this.r = obtainStyledAttributes.getText(R.styleable.SwitchCompat_android_textOn);
        this.s = obtainStyledAttributes.getText(R.styleable.SwitchCompat_android_textOff);
        this.t = obtainStyledAttributes.getBoolean(R.styleable.SwitchCompat_showText, true);
        this.n = obtainStyledAttributes.getDimensionPixelSize(R.styleable.SwitchCompat_thumbTextPadding, 0);
        this.o = obtainStyledAttributes.getDimensionPixelSize(R.styleable.SwitchCompat_switchMinWidth, 0);
        this.p = obtainStyledAttributes.getDimensionPixelSize(R.styleable.SwitchCompat_switchPadding, 0);
        this.q = obtainStyledAttributes.getBoolean(R.styleable.SwitchCompat_splitTrack, false);
        ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(R.styleable.SwitchCompat_thumbTint);
        if (colorStateList != null) {
            this.e = colorStateList;
            this.g = true;
        }
        PorterDuff.Mode parseTintMode = DrawableUtils.parseTintMode(obtainStyledAttributes.getInt(R.styleable.SwitchCompat_thumbTintMode, -1), null);
        if (this.f != parseTintMode) {
            this.f = parseTintMode;
            this.h = true;
        }
        if (this.g || this.h) {
            b();
        }
        ColorStateList colorStateList2 = obtainStyledAttributes.getColorStateList(R.styleable.SwitchCompat_trackTint);
        if (colorStateList2 != null) {
            this.j = colorStateList2;
            this.l = true;
        }
        PorterDuff.Mode parseTintMode2 = DrawableUtils.parseTintMode(obtainStyledAttributes.getInt(R.styleable.SwitchCompat_trackTintMode, -1), null);
        if (this.k != parseTintMode2) {
            this.k = parseTintMode2;
            this.m = true;
        }
        if (this.l || this.m) {
            a();
        }
        int resourceId = obtainStyledAttributes.getResourceId(R.styleable.SwitchCompat_switchTextAppearance, 0);
        if (resourceId != 0) {
            setSwitchTextAppearance(context, resourceId);
        }
        this.M = new k(this);
        this.M.a(attributeSet, i);
        obtainStyledAttributes.recycle();
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.v = viewConfiguration.getScaledTouchSlop();
        this.z = viewConfiguration.getScaledMinimumFlingVelocity();
        refreshDrawableState();
        setChecked(isChecked());
    }

    public void setSwitchTextAppearance(Context context, int i) {
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, i, R.styleable.TextAppearance);
        ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(R.styleable.TextAppearance_android_textColor);
        if (colorStateList != null) {
            this.I = colorStateList;
        } else {
            this.I = getTextColors();
        }
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, 0);
        if (dimensionPixelSize != 0) {
            float f = dimensionPixelSize;
            if (f != this.H.getTextSize()) {
                this.H.setTextSize(f);
                requestLayout();
            }
        }
        a(obtainStyledAttributes.getInt(R.styleable.TextAppearance_android_typeface, -1), obtainStyledAttributes.getInt(R.styleable.TextAppearance_android_textStyle, -1));
        if (obtainStyledAttributes.getBoolean(R.styleable.TextAppearance_textAllCaps, false)) {
            this.L = new AllCapsTransformationMethod(getContext());
        } else {
            this.L = null;
        }
        obtainStyledAttributes.recycle();
    }

    private void a(int i, int i2) {
        Typeface typeface;
        switch (i) {
            case 1:
                typeface = Typeface.SANS_SERIF;
                break;
            case 2:
                typeface = Typeface.SERIF;
                break;
            case 3:
                typeface = Typeface.MONOSPACE;
                break;
            default:
                typeface = null;
                break;
        }
        setSwitchTypeface(typeface, i2);
    }

    public void setSwitchTypeface(Typeface typeface, int i) {
        Typeface typeface2;
        float f = 0.0f;
        boolean z = false;
        if (i > 0) {
            if (typeface == null) {
                typeface2 = Typeface.defaultFromStyle(i);
            } else {
                typeface2 = Typeface.create(typeface, i);
            }
            setSwitchTypeface(typeface2);
            int i2 = (~(typeface2 != null ? typeface2.getStyle() : 0)) & i;
            TextPaint textPaint = this.H;
            if ((i2 & 1) != 0) {
                z = true;
            }
            textPaint.setFakeBoldText(z);
            TextPaint textPaint2 = this.H;
            if ((i2 & 2) != 0) {
                f = -0.25f;
            }
            textPaint2.setTextSkewX(f);
            return;
        }
        this.H.setFakeBoldText(false);
        this.H.setTextSkewX(0.0f);
        setSwitchTypeface(typeface);
    }

    public void setSwitchTypeface(Typeface typeface) {
        if ((this.H.getTypeface() != null && !this.H.getTypeface().equals(typeface)) || (this.H.getTypeface() == null && typeface != null)) {
            this.H.setTypeface(typeface);
            requestLayout();
            invalidate();
        }
    }

    public void setSwitchPadding(int i) {
        this.p = i;
        requestLayout();
    }

    public int getSwitchPadding() {
        return this.p;
    }

    public void setSwitchMinWidth(int i) {
        this.o = i;
        requestLayout();
    }

    public int getSwitchMinWidth() {
        return this.o;
    }

    public void setThumbTextPadding(int i) {
        this.n = i;
        requestLayout();
    }

    public int getThumbTextPadding() {
        return this.n;
    }

    public void setTrackDrawable(Drawable drawable) {
        Drawable drawable2 = this.i;
        if (drawable2 != null) {
            drawable2.setCallback(null);
        }
        this.i = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
        }
        requestLayout();
    }

    public void setTrackResource(int i) {
        setTrackDrawable(AppCompatResources.getDrawable(getContext(), i));
    }

    public Drawable getTrackDrawable() {
        return this.i;
    }

    public void setTrackTintList(@Nullable ColorStateList colorStateList) {
        this.j = colorStateList;
        this.l = true;
        a();
    }

    @Nullable
    public ColorStateList getTrackTintList() {
        return this.j;
    }

    public void setTrackTintMode(@Nullable PorterDuff.Mode mode) {
        this.k = mode;
        this.m = true;
        a();
    }

    @Nullable
    public PorterDuff.Mode getTrackTintMode() {
        return this.k;
    }

    private void a() {
        if (this.i == null) {
            return;
        }
        if (this.l || this.m) {
            this.i = DrawableCompat.wrap(this.i).mutate();
            if (this.l) {
                DrawableCompat.setTintList(this.i, this.j);
            }
            if (this.m) {
                DrawableCompat.setTintMode(this.i, this.k);
            }
            if (this.i.isStateful()) {
                this.i.setState(getDrawableState());
            }
        }
    }

    public void setThumbDrawable(Drawable drawable) {
        Drawable drawable2 = this.d;
        if (drawable2 != null) {
            drawable2.setCallback(null);
        }
        this.d = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
        }
        requestLayout();
    }

    public void setThumbResource(int i) {
        setThumbDrawable(AppCompatResources.getDrawable(getContext(), i));
    }

    public Drawable getThumbDrawable() {
        return this.d;
    }

    public void setThumbTintList(@Nullable ColorStateList colorStateList) {
        this.e = colorStateList;
        this.g = true;
        b();
    }

    @Nullable
    public ColorStateList getThumbTintList() {
        return this.e;
    }

    public void setThumbTintMode(@Nullable PorterDuff.Mode mode) {
        this.f = mode;
        this.h = true;
        b();
    }

    @Nullable
    public PorterDuff.Mode getThumbTintMode() {
        return this.f;
    }

    private void b() {
        if (this.d == null) {
            return;
        }
        if (this.g || this.h) {
            this.d = DrawableCompat.wrap(this.d).mutate();
            if (this.g) {
                DrawableCompat.setTintList(this.d, this.e);
            }
            if (this.h) {
                DrawableCompat.setTintMode(this.d, this.f);
            }
            if (this.d.isStateful()) {
                this.d.setState(getDrawableState());
            }
        }
    }

    public void setSplitTrack(boolean z) {
        this.q = z;
        invalidate();
    }

    public boolean getSplitTrack() {
        return this.q;
    }

    public CharSequence getTextOn() {
        return this.r;
    }

    public void setTextOn(CharSequence charSequence) {
        this.r = charSequence;
        requestLayout();
        if (isChecked()) {
            d();
        }
    }

    public CharSequence getTextOff() {
        return this.s;
    }

    public void setTextOff(CharSequence charSequence) {
        this.s = charSequence;
        requestLayout();
        if (!isChecked()) {
            e();
        }
    }

    public void setShowText(boolean z) {
        if (this.t != z) {
            this.t = z;
            requestLayout();
        }
    }

    public boolean getShowText() {
        return this.t;
    }

    @Override // android.widget.TextView, android.view.View
    public void onMeasure(int i, int i2) {
        int i3;
        int i4;
        if (this.t) {
            if (this.J == null) {
                this.J = a(this.r);
            }
            if (this.K == null) {
                this.K = a(this.s);
            }
        }
        Rect rect = this.N;
        Drawable drawable = this.d;
        int i5 = 0;
        if (drawable != null) {
            drawable.getPadding(rect);
            i4 = (this.d.getIntrinsicWidth() - rect.left) - rect.right;
            i3 = this.d.getIntrinsicHeight();
        } else {
            i4 = 0;
            i3 = 0;
        }
        this.C = Math.max(this.t ? Math.max(this.J.getWidth(), this.K.getWidth()) + (this.n * 2) : 0, i4);
        Drawable drawable2 = this.i;
        if (drawable2 != null) {
            drawable2.getPadding(rect);
            i5 = this.i.getIntrinsicHeight();
        } else {
            rect.setEmpty();
        }
        int i6 = rect.left;
        int i7 = rect.right;
        Drawable drawable3 = this.d;
        if (drawable3 != null) {
            Rect opticalBounds = DrawableUtils.getOpticalBounds(drawable3);
            i6 = Math.max(i6, opticalBounds.left);
            i7 = Math.max(i7, opticalBounds.right);
        }
        int max = Math.max(this.o, (this.C * 2) + i6 + i7);
        int max2 = Math.max(i5, i3);
        this.A = max;
        this.B = max2;
        super.onMeasure(i, i2);
        if (getMeasuredHeight() < max2) {
            setMeasuredDimension(getMeasuredWidthAndState(), max2);
        }
    }

    @Override // android.view.View
    public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEvent(accessibilityEvent);
        CharSequence charSequence = isChecked() ? this.r : this.s;
        if (charSequence != null) {
            accessibilityEvent.getText().add(charSequence);
        }
    }

    private Layout a(CharSequence charSequence) {
        TransformationMethod transformationMethod = this.L;
        CharSequence transformation = transformationMethod != null ? transformationMethod.getTransformation(charSequence, this) : charSequence;
        TextPaint textPaint = this.H;
        return new StaticLayout(transformation, textPaint, transformation != null ? (int) Math.ceil(Layout.getDesiredWidth(transformation, textPaint)) : 0, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
    }

    private boolean a(float f, float f2) {
        if (this.d == null) {
            return false;
        }
        int thumbOffset = getThumbOffset();
        this.d.getPadding(this.N);
        int i = this.E;
        int i2 = this.v;
        int i3 = i - i2;
        int i4 = (this.D + thumbOffset) - i2;
        int i5 = this.C + i4 + this.N.left + this.N.right;
        int i6 = this.v;
        return f > ((float) i4) && f < ((float) (i5 + i6)) && f2 > ((float) i3) && f2 < ((float) (this.G + i6));
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.y.addMovement(motionEvent);
        switch (motionEvent.getActionMasked()) {
            case 0:
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                if (isEnabled() && a(x, y)) {
                    this.u = 1;
                    this.w = x;
                    this.x = y;
                    break;
                }
                break;
            case 1:
            case 3:
                if (this.u != 2) {
                    this.u = 0;
                    this.y.clear();
                    break;
                } else {
                    b(motionEvent);
                    super.onTouchEvent(motionEvent);
                    return true;
                }
            case 2:
                switch (this.u) {
                    case 2:
                        float x2 = motionEvent.getX();
                        int thumbScrollRange = getThumbScrollRange();
                        float f = x2 - this.w;
                        float f2 = thumbScrollRange != 0 ? f / thumbScrollRange : f > 0.0f ? 1.0f : -1.0f;
                        if (ViewUtils.isLayoutRtl(this)) {
                            f2 = -f2;
                        }
                        float a = a(this.a + f2, 0.0f, 1.0f);
                        if (a != this.a) {
                            this.w = x2;
                            setThumbPosition(a);
                        }
                        return true;
                    case 1:
                        float x3 = motionEvent.getX();
                        float y2 = motionEvent.getY();
                        if (Math.abs(x3 - this.w) > this.v || Math.abs(y2 - this.x) > this.v) {
                            this.u = 2;
                            getParent().requestDisallowInterceptTouchEvent(true);
                            this.w = x3;
                            this.x = y2;
                            return true;
                        }
                        break;
                }
        }
        return super.onTouchEvent(motionEvent);
    }

    private void a(MotionEvent motionEvent) {
        MotionEvent obtain = MotionEvent.obtain(motionEvent);
        obtain.setAction(3);
        super.onTouchEvent(obtain);
        obtain.recycle();
    }

    private void b(MotionEvent motionEvent) {
        this.u = 0;
        boolean z = true;
        boolean z2 = motionEvent.getAction() == 1 && isEnabled();
        boolean isChecked = isChecked();
        if (z2) {
            this.y.computeCurrentVelocity(1000);
            float xVelocity = this.y.getXVelocity();
            if (Math.abs(xVelocity) <= this.z) {
                z = getTargetCheckedState();
            } else if (!ViewUtils.isLayoutRtl(this) ? xVelocity <= 0.0f : xVelocity >= 0.0f) {
                z = false;
            }
        } else {
            z = isChecked;
        }
        if (z != isChecked) {
            playSoundEffect(0);
        }
        setChecked(z);
        a(motionEvent);
    }

    private void a(boolean z) {
        this.b = ObjectAnimator.ofFloat(this, c, z ? 1.0f : 0.0f);
        this.b.setDuration(250L);
        if (Build.VERSION.SDK_INT >= 18) {
            this.b.setAutoCancel(true);
        }
        this.b.start();
    }

    private void c() {
        ObjectAnimator objectAnimator = this.b;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
    }

    private boolean getTargetCheckedState() {
        return this.a > 0.5f;
    }

    void setThumbPosition(float f) {
        this.a = f;
        invalidate();
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public void toggle() {
        setChecked(!isChecked());
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public void setChecked(boolean z) {
        super.setChecked(z);
        boolean isChecked = isChecked();
        if (isChecked) {
            d();
        } else {
            e();
        }
        if (getWindowToken() == null || !ViewCompat.isLaidOut(this)) {
            c();
            setThumbPosition(isChecked ? 1.0f : 0.0f);
            return;
        }
        a(isChecked);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        super.onLayout(z, i, i2, i3, i4);
        int i10 = 0;
        if (this.d != null) {
            Rect rect = this.N;
            Drawable drawable = this.i;
            if (drawable != null) {
                drawable.getPadding(rect);
            } else {
                rect.setEmpty();
            }
            Rect opticalBounds = DrawableUtils.getOpticalBounds(this.d);
            i5 = Math.max(0, opticalBounds.left - rect.left);
            i10 = Math.max(0, opticalBounds.right - rect.right);
        } else {
            i5 = 0;
        }
        if (ViewUtils.isLayoutRtl(this)) {
            i7 = getPaddingLeft() + i5;
            i6 = ((this.A + i7) - i5) - i10;
        } else {
            i6 = (getWidth() - getPaddingRight()) - i10;
            i7 = (i6 - this.A) + i5 + i10;
        }
        int gravity = getGravity() & 112;
        if (gravity == 16) {
            int i11 = this.B;
            i9 = (((getPaddingTop() + getHeight()) - getPaddingBottom()) / 2) - (i11 / 2);
            i8 = i11 + i9;
        } else if (gravity != 80) {
            i9 = getPaddingTop();
            i8 = this.B + i9;
        } else {
            i8 = getHeight() - getPaddingBottom();
            i9 = i8 - this.B;
        }
        this.D = i7;
        this.E = i9;
        this.G = i8;
        this.F = i6;
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        Rect rect;
        int i;
        int i2;
        Rect rect2 = this.N;
        int i3 = this.D;
        int i4 = this.E;
        int i5 = this.F;
        int i6 = this.G;
        int thumbOffset = getThumbOffset() + i3;
        Drawable drawable = this.d;
        if (drawable != null) {
            rect = DrawableUtils.getOpticalBounds(drawable);
        } else {
            rect = DrawableUtils.INSETS_NONE;
        }
        Drawable drawable2 = this.i;
        if (drawable2 != null) {
            drawable2.getPadding(rect2);
            thumbOffset += rect2.left;
            if (rect != null) {
                if (rect.left > rect2.left) {
                    i3 += rect.left - rect2.left;
                }
                i = rect.top > rect2.top ? (rect.top - rect2.top) + i4 : i4;
                if (rect.right > rect2.right) {
                    i5 -= rect.right - rect2.right;
                }
                i2 = rect.bottom > rect2.bottom ? i6 - (rect.bottom - rect2.bottom) : i6;
            } else {
                i = i4;
                i2 = i6;
            }
            this.i.setBounds(i3, i, i5, i2);
        }
        Drawable drawable3 = this.d;
        if (drawable3 != null) {
            drawable3.getPadding(rect2);
            int i7 = thumbOffset - rect2.left;
            int i8 = thumbOffset + this.C + rect2.right;
            this.d.setBounds(i7, i4, i8, i6);
            Drawable background = getBackground();
            if (background != null) {
                DrawableCompat.setHotspotBounds(background, i7, i4, i8, i6);
            }
        }
        super.draw(canvas);
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    protected void onDraw(Canvas canvas) {
        int i;
        super.onDraw(canvas);
        Rect rect = this.N;
        Drawable drawable = this.i;
        if (drawable != null) {
            drawable.getPadding(rect);
        } else {
            rect.setEmpty();
        }
        int i2 = this.E;
        int i3 = this.G;
        int i4 = i2 + rect.top;
        int i5 = i3 - rect.bottom;
        Drawable drawable2 = this.d;
        if (drawable != null) {
            if (!this.q || drawable2 == null) {
                drawable.draw(canvas);
            } else {
                Rect opticalBounds = DrawableUtils.getOpticalBounds(drawable2);
                drawable2.copyBounds(rect);
                rect.left += opticalBounds.left;
                rect.right -= opticalBounds.right;
                int save = canvas.save();
                canvas.clipRect(rect, Region.Op.DIFFERENCE);
                drawable.draw(canvas);
                canvas.restoreToCount(save);
            }
        }
        int save2 = canvas.save();
        if (drawable2 != null) {
            drawable2.draw(canvas);
        }
        Layout layout = getTargetCheckedState() ? this.J : this.K;
        if (layout != null) {
            int[] drawableState = getDrawableState();
            ColorStateList colorStateList = this.I;
            if (colorStateList != null) {
                this.H.setColor(colorStateList.getColorForState(drawableState, 0));
            }
            this.H.drawableState = drawableState;
            if (drawable2 != null) {
                Rect bounds = drawable2.getBounds();
                i = bounds.left + bounds.right;
            } else {
                i = getWidth();
            }
            canvas.translate((i / 2) - (layout.getWidth() / 2), ((i4 + i5) / 2) - (layout.getHeight() / 2));
            layout.draw(canvas);
        }
        canvas.restoreToCount(save2);
    }

    @Override // android.widget.CompoundButton, android.widget.TextView
    public int getCompoundPaddingLeft() {
        if (!ViewUtils.isLayoutRtl(this)) {
            return super.getCompoundPaddingLeft();
        }
        int compoundPaddingLeft = super.getCompoundPaddingLeft() + this.A;
        return !TextUtils.isEmpty(getText()) ? compoundPaddingLeft + this.p : compoundPaddingLeft;
    }

    @Override // android.widget.CompoundButton, android.widget.TextView
    public int getCompoundPaddingRight() {
        if (ViewUtils.isLayoutRtl(this)) {
            return super.getCompoundPaddingRight();
        }
        int compoundPaddingRight = super.getCompoundPaddingRight() + this.A;
        return !TextUtils.isEmpty(getText()) ? compoundPaddingRight + this.p : compoundPaddingRight;
    }

    private int getThumbOffset() {
        float f;
        if (ViewUtils.isLayoutRtl(this)) {
            f = 1.0f - this.a;
        } else {
            f = this.a;
        }
        return (int) ((f * getThumbScrollRange()) + 0.5f);
    }

    private int getThumbScrollRange() {
        Rect rect;
        Drawable drawable = this.i;
        if (drawable == null) {
            return 0;
        }
        Rect rect2 = this.N;
        drawable.getPadding(rect2);
        Drawable drawable2 = this.d;
        if (drawable2 != null) {
            rect = DrawableUtils.getOpticalBounds(drawable2);
        } else {
            rect = DrawableUtils.INSETS_NONE;
        }
        return ((((this.A - this.C) - rect2.left) - rect2.right) - rect.left) - rect.right;
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    protected int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 1);
        if (isChecked()) {
            mergeDrawableStates(onCreateDrawableState, O);
        }
        return onCreateDrawableState;
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        Drawable drawable = this.d;
        boolean z = false;
        if (drawable != null && drawable.isStateful()) {
            z = false | drawable.setState(drawableState);
        }
        Drawable drawable2 = this.i;
        if (drawable2 != null && drawable2.isStateful()) {
            z |= drawable2.setState(drawableState);
        }
        if (z) {
            invalidate();
        }
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public void drawableHotspotChanged(float f, float f2) {
        if (Build.VERSION.SDK_INT >= 21) {
            super.drawableHotspotChanged(f, f2);
        }
        Drawable drawable = this.d;
        if (drawable != null) {
            DrawableCompat.setHotspot(drawable, f, f2);
        }
        Drawable drawable2 = this.i;
        if (drawable2 != null) {
            DrawableCompat.setHotspot(drawable2, f, f2);
        }
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    protected boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.d || drawable == this.i;
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.d;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
        Drawable drawable2 = this.i;
        if (drawable2 != null) {
            drawable2.jumpToCurrentState();
        }
        ObjectAnimator objectAnimator = this.b;
        if (objectAnimator != null && objectAnimator.isStarted()) {
            this.b.end();
            this.b = null;
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName("android.widget.Switch");
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName("android.widget.Switch");
        if (Build.VERSION.SDK_INT < 30) {
            CharSequence charSequence = isChecked() ? this.r : this.s;
            if (!TextUtils.isEmpty(charSequence)) {
                CharSequence text = accessibilityNodeInfo.getText();
                if (TextUtils.isEmpty(text)) {
                    accessibilityNodeInfo.setText(charSequence);
                    return;
                }
                StringBuilder sb = new StringBuilder();
                sb.append(text);
                sb.append(' ');
                sb.append(charSequence);
                accessibilityNodeInfo.setText(sb);
            }
        }
    }

    @Override // android.widget.TextView
    public void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(TextViewCompat.wrapCustomSelectionActionModeCallback(this, callback));
    }

    private void d() {
        if (Build.VERSION.SDK_INT >= 30) {
            CharSequence charSequence = this.r;
            if (charSequence == null) {
                charSequence = getResources().getString(R.string.abc_capital_on);
            }
            ViewCompat.setStateDescription(this, charSequence);
        }
    }

    private void e() {
        if (Build.VERSION.SDK_INT >= 30) {
            CharSequence charSequence = this.s;
            if (charSequence == null) {
                charSequence = getResources().getString(R.string.abc_capital_off);
            }
            ViewCompat.setStateDescription(this, charSequence);
        }
    }
}
