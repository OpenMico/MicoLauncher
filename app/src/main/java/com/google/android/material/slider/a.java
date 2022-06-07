package com.google.android.material.slider;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.widget.SeekBar;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.Dimension;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewOverlayImpl;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.slider.BaseOnChangeListener;
import com.google.android.material.slider.BaseOnSliderTouchListener;
import com.google.android.material.slider.a;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import com.google.android.material.tooltip.TooltipDrawable;
import com.xiaomi.mipush.sdk.Constants;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: BaseSlider.java */
/* loaded from: classes2.dex */
public abstract class a<S extends a<S, L, T>, L extends BaseOnChangeListener<S>, T extends BaseOnSliderTouchListener<S>> extends View {
    static final int a = R.style.Widget_MaterialComponents_Slider;
    private static final String b = "a";
    private int A;
    private int B;
    private int C;
    private float D;
    private MotionEvent E;
    private LabelFormatter F;
    private boolean G;
    private float H;
    private float I;
    private ArrayList<Float> J;
    private int K;
    private int L;
    private float M;
    private float[] N;
    private boolean O;
    private int P;
    private boolean Q;
    private boolean R;
    private boolean S;
    @NonNull
    private ColorStateList T;
    @NonNull
    private ColorStateList U;
    @NonNull
    private ColorStateList V;
    @NonNull
    private ColorStateList W;
    @NonNull
    private ColorStateList aa;
    @NonNull
    private final MaterialShapeDrawable ab;
    private float ac;
    private int ad;
    @NonNull
    private final Paint c;
    @NonNull
    private final Paint d;
    @NonNull
    private final Paint e;
    @NonNull
    private final Paint f;
    @NonNull
    private final Paint g;
    @NonNull
    private final Paint h;
    @NonNull
    private final b i;
    private final AccessibilityManager j;
    private a<S, L, T>.RunnableC0078a k;
    @NonNull
    private final d l;
    @NonNull
    private final List<TooltipDrawable> m;
    @NonNull
    private final List<L> n;
    @NonNull
    private final List<T> o;
    private boolean p;
    private ValueAnimator q;
    private ValueAnimator r;
    private final int s;
    private int t;
    private int u;
    private int v;
    private int w;
    private int x;
    private int y;
    private int z;

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: BaseSlider.java */
    /* loaded from: classes2.dex */
    public interface d {
        TooltipDrawable a();
    }

    protected float getMinSeparation() {
        return 0.0f;
    }

    public a(@NonNull Context context) {
        this(context, null);
    }

    public a(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.sliderStyle);
    }

    public a(@NonNull Context context, @Nullable final AttributeSet attributeSet, final int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, a), attributeSet, i);
        this.m = new ArrayList();
        this.n = new ArrayList();
        this.o = new ArrayList();
        this.p = false;
        this.G = false;
        this.J = new ArrayList<>();
        this.K = -1;
        this.L = -1;
        this.M = 0.0f;
        this.O = true;
        this.R = false;
        this.ab = new MaterialShapeDrawable();
        this.ad = 0;
        Context context2 = getContext();
        this.c = new Paint();
        this.c.setStyle(Paint.Style.STROKE);
        this.c.setStrokeCap(Paint.Cap.ROUND);
        this.d = new Paint();
        this.d.setStyle(Paint.Style.STROKE);
        this.d.setStrokeCap(Paint.Cap.ROUND);
        this.e = new Paint(1);
        this.e.setStyle(Paint.Style.FILL);
        this.e.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        this.f = new Paint(1);
        this.f.setStyle(Paint.Style.FILL);
        this.g = new Paint();
        this.g.setStyle(Paint.Style.STROKE);
        this.g.setStrokeCap(Paint.Cap.ROUND);
        this.h = new Paint();
        this.h.setStyle(Paint.Style.STROKE);
        this.h.setStrokeCap(Paint.Cap.ROUND);
        a(context2.getResources());
        this.l = new d() { // from class: com.google.android.material.slider.a.1
            @Override // com.google.android.material.slider.a.d
            public TooltipDrawable a() {
                TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(a.this.getContext(), attributeSet, R.styleable.Slider, i, a.a, new int[0]);
                TooltipDrawable b2 = a.b(a.this.getContext(), obtainStyledAttributes);
                obtainStyledAttributes.recycle();
                return b2;
            }
        };
        a(context2, attributeSet, i);
        setFocusable(true);
        setClickable(true);
        this.ab.setShadowCompatibilityMode(2);
        this.s = ViewConfiguration.get(context2).getScaledTouchSlop();
        this.i = new b(this);
        ViewCompat.setAccessibilityDelegate(this, this.i);
        this.j = (AccessibilityManager) getContext().getSystemService("accessibility");
    }

    private void a(@NonNull Resources resources) {
        this.v = resources.getDimensionPixelSize(R.dimen.mtrl_slider_widget_height);
        this.t = resources.getDimensionPixelOffset(R.dimen.mtrl_slider_track_side_padding);
        this.y = this.t;
        this.u = resources.getDimensionPixelSize(R.dimen.mtrl_slider_thumb_radius);
        this.z = resources.getDimensionPixelOffset(R.dimen.mtrl_slider_track_top);
        this.C = resources.getDimensionPixelSize(R.dimen.mtrl_slider_label_padding);
    }

    private void a(Context context, AttributeSet attributeSet, int i) {
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context, attributeSet, R.styleable.Slider, i, a, new int[0]);
        this.H = obtainStyledAttributes.getFloat(R.styleable.Slider_android_valueFrom, 0.0f);
        this.I = obtainStyledAttributes.getFloat(R.styleable.Slider_android_valueTo, 1.0f);
        setValues(Float.valueOf(this.H));
        this.M = obtainStyledAttributes.getFloat(R.styleable.Slider_android_stepSize, 0.0f);
        boolean hasValue = obtainStyledAttributes.hasValue(R.styleable.Slider_trackColor);
        int i2 = hasValue ? R.styleable.Slider_trackColor : R.styleable.Slider_trackColorInactive;
        int i3 = hasValue ? R.styleable.Slider_trackColor : R.styleable.Slider_trackColorActive;
        ColorStateList colorStateList = MaterialResources.getColorStateList(context, obtainStyledAttributes, i2);
        if (colorStateList == null) {
            colorStateList = AppCompatResources.getColorStateList(context, R.color.material_slider_inactive_track_color);
        }
        setTrackInactiveTintList(colorStateList);
        ColorStateList colorStateList2 = MaterialResources.getColorStateList(context, obtainStyledAttributes, i3);
        if (colorStateList2 == null) {
            colorStateList2 = AppCompatResources.getColorStateList(context, R.color.material_slider_active_track_color);
        }
        setTrackActiveTintList(colorStateList2);
        this.ab.setFillColor(MaterialResources.getColorStateList(context, obtainStyledAttributes, R.styleable.Slider_thumbColor));
        if (obtainStyledAttributes.hasValue(R.styleable.Slider_thumbStrokeColor)) {
            setThumbStrokeColor(MaterialResources.getColorStateList(context, obtainStyledAttributes, R.styleable.Slider_thumbStrokeColor));
        }
        setThumbStrokeWidth(obtainStyledAttributes.getDimension(R.styleable.Slider_thumbStrokeWidth, 0.0f));
        ColorStateList colorStateList3 = MaterialResources.getColorStateList(context, obtainStyledAttributes, R.styleable.Slider_haloColor);
        if (colorStateList3 == null) {
            colorStateList3 = AppCompatResources.getColorStateList(context, R.color.material_slider_halo_color);
        }
        setHaloTintList(colorStateList3);
        this.O = obtainStyledAttributes.getBoolean(R.styleable.Slider_tickVisible, true);
        boolean hasValue2 = obtainStyledAttributes.hasValue(R.styleable.Slider_tickColor);
        int i4 = hasValue2 ? R.styleable.Slider_tickColor : R.styleable.Slider_tickColorInactive;
        int i5 = hasValue2 ? R.styleable.Slider_tickColor : R.styleable.Slider_tickColorActive;
        ColorStateList colorStateList4 = MaterialResources.getColorStateList(context, obtainStyledAttributes, i4);
        if (colorStateList4 == null) {
            colorStateList4 = AppCompatResources.getColorStateList(context, R.color.material_slider_inactive_tick_marks_color);
        }
        setTickInactiveTintList(colorStateList4);
        ColorStateList colorStateList5 = MaterialResources.getColorStateList(context, obtainStyledAttributes, i5);
        if (colorStateList5 == null) {
            colorStateList5 = AppCompatResources.getColorStateList(context, R.color.material_slider_active_tick_marks_color);
        }
        setTickActiveTintList(colorStateList5);
        setThumbRadius(obtainStyledAttributes.getDimensionPixelSize(R.styleable.Slider_thumbRadius, 0));
        setHaloRadius(obtainStyledAttributes.getDimensionPixelSize(R.styleable.Slider_haloRadius, 0));
        setThumbElevation(obtainStyledAttributes.getDimension(R.styleable.Slider_thumbElevation, 0.0f));
        setTrackHeight(obtainStyledAttributes.getDimensionPixelSize(R.styleable.Slider_trackHeight, 0));
        this.w = obtainStyledAttributes.getInt(R.styleable.Slider_labelBehavior, 0);
        if (!obtainStyledAttributes.getBoolean(R.styleable.Slider_android_enabled, true)) {
            setEnabled(false);
        }
        obtainStyledAttributes.recycle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @NonNull
    public static TooltipDrawable b(@NonNull Context context, @NonNull TypedArray typedArray) {
        return TooltipDrawable.createFromAttributes(context, null, 0, typedArray.getResourceId(R.styleable.Slider_labelStyle, R.style.Widget_MaterialComponents_Tooltip));
    }

    private void b() {
        this.y = this.t + Math.max(this.A - this.u, 0);
        if (ViewCompat.isLaidOut(this)) {
            a(getWidth());
        }
    }

    private void c() {
        float f = this.H;
        if (f >= this.I) {
            throw new IllegalStateException(String.format("valueFrom(%s) must be smaller than valueTo(%s)", Float.toString(f), Float.toString(this.I)));
        }
    }

    private void d() {
        float f = this.I;
        if (f <= this.H) {
            throw new IllegalStateException(String.format("valueTo(%s) must be greater than valueFrom(%s)", Float.toString(f), Float.toString(this.H)));
        }
    }

    private boolean a(float f) {
        double doubleValue = new BigDecimal(Float.toString(f)).subtract(new BigDecimal(Float.toString(this.H))).divide(new BigDecimal(Float.toString(this.M)), MathContext.DECIMAL64).doubleValue();
        return Math.abs(((double) Math.round(doubleValue)) - doubleValue) < 1.0E-4d;
    }

    private void e() {
        if (this.M > 0.0f && !a(this.I)) {
            throw new IllegalStateException(String.format("The stepSize(%s) must be 0, or a factor of the valueFrom(%s)-valueTo(%s) range", Float.toString(this.M), Float.toString(this.H), Float.toString(this.I)));
        }
    }

    private void f() {
        Iterator<Float> it = this.J.iterator();
        while (it.hasNext()) {
            Float next = it.next();
            if (next.floatValue() < this.H || next.floatValue() > this.I) {
                throw new IllegalStateException(String.format("Slider value(%s) must be greater or equal to valueFrom(%s), and lower or equal to valueTo(%s)", Float.toString(next.floatValue()), Float.toString(this.H), Float.toString(this.I)));
            } else if (this.M > 0.0f && !a(next.floatValue())) {
                throw new IllegalStateException(String.format("Value(%s) must be equal to valueFrom(%s) plus a multiple of stepSize(%s) when using stepSize(%s)", Float.toString(next.floatValue()), Float.toString(this.H), Float.toString(this.M), Float.toString(this.M)));
            }
        }
    }

    private void g() {
        float f = this.M;
        if (f != 0.0f) {
            if (((int) f) != f) {
                Log.w(b, String.format("Floating point value used for %s(%s). Using floats can have rounding errors which may result in incorrect values. Instead, consider using integers with a custom LabelFormatter to display the  value correctly.", "stepSize", Float.valueOf(f)));
            }
            float f2 = this.H;
            if (((int) f2) != f2) {
                Log.w(b, String.format("Floating point value used for %s(%s). Using floats can have rounding errors which may result in incorrect values. Instead, consider using integers with a custom LabelFormatter to display the  value correctly.", "valueFrom", Float.valueOf(f2)));
            }
            float f3 = this.I;
            if (((int) f3) != f3) {
                Log.w(b, String.format("Floating point value used for %s(%s). Using floats can have rounding errors which may result in incorrect values. Instead, consider using integers with a custom LabelFormatter to display the  value correctly.", "valueTo", Float.valueOf(f3)));
            }
        }
    }

    private void h() {
        if (this.S) {
            c();
            d();
            e();
            f();
            g();
            this.S = false;
        }
    }

    public float getValueFrom() {
        return this.H;
    }

    public void setValueFrom(float f) {
        this.H = f;
        this.S = true;
        postInvalidate();
    }

    public float getValueTo() {
        return this.I;
    }

    public void setValueTo(float f) {
        this.I = f;
        this.S = true;
        postInvalidate();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @NonNull
    public List<Float> getValues() {
        return new ArrayList(this.J);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setValues(@NonNull Float... fArr) {
        ArrayList<Float> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, fArr);
        setValuesInternal(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setValues(@NonNull List<Float> list) {
        setValuesInternal(new ArrayList<>(list));
    }

    private void setValuesInternal(@NonNull ArrayList<Float> arrayList) {
        if (!arrayList.isEmpty()) {
            Collections.sort(arrayList);
            if (this.J.size() != arrayList.size() || !this.J.equals(arrayList)) {
                this.J = arrayList;
                this.S = true;
                this.L = 0;
                k();
                i();
                s();
                postInvalidate();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("At least one value must be set");
    }

    private void i() {
        if (this.m.size() > this.J.size()) {
            List<TooltipDrawable> subList = this.m.subList(this.J.size(), this.m.size());
            for (TooltipDrawable tooltipDrawable : subList) {
                if (ViewCompat.isAttachedToWindow(this)) {
                    b(tooltipDrawable);
                }
            }
            subList.clear();
        }
        while (this.m.size() < this.J.size()) {
            TooltipDrawable a2 = this.l.a();
            this.m.add(a2);
            if (ViewCompat.isAttachedToWindow(this)) {
                a(a2);
            }
        }
        int i = 1;
        if (this.m.size() == 1) {
            i = 0;
        }
        for (TooltipDrawable tooltipDrawable2 : this.m) {
            tooltipDrawable2.setStrokeWidth(i);
        }
    }

    public float getStepSize() {
        return this.M;
    }

    public void setStepSize(float f) {
        if (f < 0.0f) {
            throw new IllegalArgumentException(String.format("The stepSize(%s) must be 0, or a factor of the valueFrom(%s)-valueTo(%s) range", Float.toString(f), Float.toString(this.H), Float.toString(this.I)));
        } else if (this.M != f) {
            this.M = f;
            this.S = true;
            postInvalidate();
        }
    }

    public int getFocusedThumbIndex() {
        return this.L;
    }

    public void setFocusedThumbIndex(int i) {
        if (i < 0 || i >= this.J.size()) {
            throw new IllegalArgumentException("index out of range");
        }
        this.L = i;
        this.i.requestKeyboardFocusForVirtualView(this.L);
        postInvalidate();
    }

    protected void setActiveThumbIndex(int i) {
        this.K = i;
    }

    public int getActiveThumbIndex() {
        return this.K;
    }

    public void addOnChangeListener(@Nullable L l) {
        this.n.add(l);
    }

    public void removeOnChangeListener(@NonNull L l) {
        this.n.remove(l);
    }

    public void clearOnChangeListeners() {
        this.n.clear();
    }

    public void addOnSliderTouchListener(@NonNull T t) {
        this.o.add(t);
    }

    public void removeOnSliderTouchListener(@NonNull T t) {
        this.o.remove(t);
    }

    public void clearOnSliderTouchListeners() {
        this.o.clear();
    }

    public boolean hasLabelFormatter() {
        return this.F != null;
    }

    public void setLabelFormatter(@Nullable LabelFormatter labelFormatter) {
        this.F = labelFormatter;
    }

    public float getThumbElevation() {
        return this.ab.getElevation();
    }

    public void setThumbElevation(float f) {
        this.ab.setElevation(f);
    }

    public void setThumbElevationResource(@DimenRes int i) {
        setThumbElevation(getResources().getDimension(i));
    }

    @Dimension
    public int getThumbRadius() {
        return this.A;
    }

    public void setThumbRadius(@IntRange(from = 0) @Dimension int i) {
        if (i != this.A) {
            this.A = i;
            b();
            this.ab.setShapeAppearanceModel(ShapeAppearanceModel.builder().setAllCorners(0, this.A).build());
            MaterialShapeDrawable materialShapeDrawable = this.ab;
            int i2 = this.A;
            materialShapeDrawable.setBounds(0, 0, i2 * 2, i2 * 2);
            postInvalidate();
        }
    }

    public void setThumbRadiusResource(@DimenRes int i) {
        setThumbRadius(getResources().getDimensionPixelSize(i));
    }

    public void setThumbStrokeColor(@Nullable ColorStateList colorStateList) {
        this.ab.setStrokeColor(colorStateList);
        postInvalidate();
    }

    public void setThumbStrokeColorResource(@ColorRes int i) {
        if (i != 0) {
            setThumbStrokeColor(AppCompatResources.getColorStateList(getContext(), i));
        }
    }

    public ColorStateList getThumbStrokeColor() {
        return this.ab.getStrokeColor();
    }

    public void setThumbStrokeWidth(float f) {
        this.ab.setStrokeWidth(f);
        postInvalidate();
    }

    public void setThumbStrokeWidthResource(@DimenRes int i) {
        if (i != 0) {
            setThumbStrokeWidth(getResources().getDimension(i));
        }
    }

    public float getThumbStrokeWidth() {
        return this.ab.getStrokeWidth();
    }

    @Dimension
    public int getHaloRadius() {
        return this.B;
    }

    public void setHaloRadius(@IntRange(from = 0) @Dimension int i) {
        if (i != this.B) {
            this.B = i;
            Drawable background = getBackground();
            if (m() || !(background instanceof RippleDrawable)) {
                postInvalidate();
            } else {
                DrawableUtils.setRippleDrawableRadius((RippleDrawable) background, this.B);
            }
        }
    }

    public void setHaloRadiusResource(@DimenRes int i) {
        setHaloRadius(getResources().getDimensionPixelSize(i));
    }

    public int getLabelBehavior() {
        return this.w;
    }

    public void setLabelBehavior(int i) {
        if (this.w != i) {
            this.w = i;
            requestLayout();
        }
    }

    @Dimension
    public int getTrackSidePadding() {
        return this.y;
    }

    @Dimension
    public int getTrackWidth() {
        return this.P;
    }

    @Dimension
    public int getTrackHeight() {
        return this.x;
    }

    public void setTrackHeight(@IntRange(from = 0) @Dimension int i) {
        if (this.x != i) {
            this.x = i;
            q();
            postInvalidate();
        }
    }

    @NonNull
    public ColorStateList getHaloTintList() {
        return this.T;
    }

    public void setHaloTintList(@NonNull ColorStateList colorStateList) {
        if (!colorStateList.equals(this.T)) {
            this.T = colorStateList;
            Drawable background = getBackground();
            if (m() || !(background instanceof RippleDrawable)) {
                this.f.setColor(a(colorStateList));
                this.f.setAlpha(63);
                invalidate();
                return;
            }
            ((RippleDrawable) background).setColor(colorStateList);
        }
    }

    @NonNull
    public ColorStateList getThumbTintList() {
        return this.ab.getFillColor();
    }

    public void setThumbTintList(@NonNull ColorStateList colorStateList) {
        if (!colorStateList.equals(this.ab.getFillColor())) {
            this.ab.setFillColor(colorStateList);
            invalidate();
        }
    }

    @NonNull
    public ColorStateList getTickTintList() {
        if (this.V.equals(this.U)) {
            return this.U;
        }
        throw new IllegalStateException("The inactive and active ticks are different colors. Use the getTickColorInactive() and getTickColorActive() methods instead.");
    }

    public void setTickTintList(@NonNull ColorStateList colorStateList) {
        setTickInactiveTintList(colorStateList);
        setTickActiveTintList(colorStateList);
    }

    @NonNull
    public ColorStateList getTickActiveTintList() {
        return this.U;
    }

    public void setTickActiveTintList(@NonNull ColorStateList colorStateList) {
        if (!colorStateList.equals(this.U)) {
            this.U = colorStateList;
            this.h.setColor(a(this.U));
            invalidate();
        }
    }

    @NonNull
    public ColorStateList getTickInactiveTintList() {
        return this.V;
    }

    public void setTickInactiveTintList(@NonNull ColorStateList colorStateList) {
        if (!colorStateList.equals(this.V)) {
            this.V = colorStateList;
            this.g.setColor(a(this.V));
            invalidate();
        }
    }

    public boolean isTickVisible() {
        return this.O;
    }

    public void setTickVisible(boolean z) {
        if (this.O != z) {
            this.O = z;
            postInvalidate();
        }
    }

    @NonNull
    public ColorStateList getTrackTintList() {
        if (this.aa.equals(this.W)) {
            return this.W;
        }
        throw new IllegalStateException("The inactive and active parts of the track are different colors. Use the getInactiveTrackColor() and getActiveTrackColor() methods instead.");
    }

    public void setTrackTintList(@NonNull ColorStateList colorStateList) {
        setTrackInactiveTintList(colorStateList);
        setTrackActiveTintList(colorStateList);
    }

    @NonNull
    public ColorStateList getTrackActiveTintList() {
        return this.W;
    }

    public void setTrackActiveTintList(@NonNull ColorStateList colorStateList) {
        if (!colorStateList.equals(this.W)) {
            this.W = colorStateList;
            this.d.setColor(a(this.W));
            invalidate();
        }
    }

    @NonNull
    public ColorStateList getTrackInactiveTintList() {
        return this.aa;
    }

    public void setTrackInactiveTintList(@NonNull ColorStateList colorStateList) {
        if (!colorStateList.equals(this.aa)) {
            this.aa = colorStateList;
            this.c.setColor(a(this.aa));
            invalidate();
        }
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        setLayerType(z ? 0 : 2, null);
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        for (TooltipDrawable tooltipDrawable : this.m) {
            a(tooltipDrawable);
        }
    }

    private void a(TooltipDrawable tooltipDrawable) {
        tooltipDrawable.setRelativeToView(ViewUtils.getContentView(this));
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        a<S, L, T>.RunnableC0078a aVar = this.k;
        if (aVar != null) {
            removeCallbacks(aVar);
        }
        this.p = false;
        for (TooltipDrawable tooltipDrawable : this.m) {
            b(tooltipDrawable);
        }
        super.onDetachedFromWindow();
    }

    private void b(TooltipDrawable tooltipDrawable) {
        ViewOverlayImpl contentViewOverlay = ViewUtils.getContentViewOverlay(this);
        if (contentViewOverlay != null) {
            contentViewOverlay.remove(tooltipDrawable);
            tooltipDrawable.detachView(ViewUtils.getContentView(this));
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int i3 = this.v;
        int i4 = 0;
        if (this.w == 1) {
            i4 = this.m.get(0).getIntrinsicHeight();
        }
        super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(i3 + i4, 1073741824));
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        a(i);
        k();
    }

    private void j() {
        if (this.M > 0.0f) {
            h();
            int min = Math.min((int) (((this.I - this.H) / this.M) + 1.0f), (this.P / (this.x * 2)) + 1);
            float[] fArr = this.N;
            if (fArr == null || fArr.length != min * 2) {
                this.N = new float[min * 2];
            }
            float f = this.P / (min - 1);
            for (int i = 0; i < min * 2; i += 2) {
                float[] fArr2 = this.N;
                fArr2[i] = this.y + ((i / 2) * f);
                fArr2[i + 1] = l();
            }
        }
    }

    private void a(int i) {
        this.P = Math.max(i - (this.y * 2), 0);
        j();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void k() {
        if (!m() && getMeasuredWidth() > 0) {
            Drawable background = getBackground();
            if (background instanceof RippleDrawable) {
                int b2 = (int) ((b(this.J.get(this.L).floatValue()) * this.P) + this.y);
                int l = l();
                int i = this.B;
                DrawableCompat.setHotspotBounds(background, b2 - i, l - i, b2 + i, l + i);
            }
        }
    }

    private int l() {
        int i = this.z;
        int i2 = 0;
        if (this.w == 1) {
            i2 = this.m.get(0).getIntrinsicHeight();
        }
        return i + i2;
    }

    @Override // android.view.View
    protected void onDraw(@NonNull Canvas canvas) {
        if (this.S) {
            h();
            j();
        }
        super.onDraw(canvas);
        int l = l();
        a(canvas, this.P, l);
        if (((Float) Collections.max(getValues())).floatValue() > this.H) {
            b(canvas, this.P, l);
        }
        a(canvas);
        if ((this.G || isFocused()) && isEnabled()) {
            d(canvas, this.P, l);
            if (this.K != -1) {
                p();
            }
        }
        c(canvas, this.P, l);
    }

    private float[] getActiveRange() {
        float floatValue = ((Float) Collections.max(getValues())).floatValue();
        float floatValue2 = ((Float) Collections.min(getValues())).floatValue();
        if (this.J.size() == 1) {
            floatValue2 = this.H;
        }
        float b2 = b(floatValue2);
        float b3 = b(floatValue);
        return a() ? new float[]{b3, b2} : new float[]{b2, b3};
    }

    private void a(@NonNull Canvas canvas, int i, int i2) {
        float[] activeRange = getActiveRange();
        int i3 = this.y;
        float f = i;
        float f2 = i3 + (activeRange[1] * f);
        if (f2 < i3 + i) {
            float f3 = i2;
            canvas.drawLine(f2, f3, i3 + i, f3, this.c);
        }
        int i4 = this.y;
        float f4 = i4 + (activeRange[0] * f);
        if (f4 > i4) {
            float f5 = i2;
            canvas.drawLine(i4, f5, f4, f5, this.c);
        }
    }

    private float b(float f) {
        float f2 = this.H;
        float f3 = (f - f2) / (this.I - f2);
        return a() ? 1.0f - f3 : f3;
    }

    private void b(@NonNull Canvas canvas, int i, int i2) {
        float[] activeRange = getActiveRange();
        int i3 = this.y;
        float f = i;
        float f2 = i2;
        canvas.drawLine(i3 + (activeRange[0] * f), f2, i3 + (activeRange[1] * f), f2, this.d);
    }

    private void a(@NonNull Canvas canvas) {
        if (this.O && this.M > 0.0f) {
            float[] activeRange = getActiveRange();
            int a2 = a(this.N, activeRange[0]);
            int a3 = a(this.N, activeRange[1]);
            int i = a2 * 2;
            canvas.drawPoints(this.N, 0, i, this.g);
            int i2 = a3 * 2;
            canvas.drawPoints(this.N, i, i2 - i, this.h);
            float[] fArr = this.N;
            canvas.drawPoints(fArr, i2, fArr.length - i2, this.g);
        }
    }

    private void c(@NonNull Canvas canvas, int i, int i2) {
        if (!isEnabled()) {
            Iterator<Float> it = this.J.iterator();
            while (it.hasNext()) {
                canvas.drawCircle(this.y + (b(it.next().floatValue()) * i), i2, this.A, this.e);
            }
        }
        Iterator<Float> it2 = this.J.iterator();
        while (it2.hasNext()) {
            canvas.save();
            int b2 = this.y + ((int) (b(it2.next().floatValue()) * i));
            int i3 = this.A;
            canvas.translate(b2 - i3, i2 - i3);
            this.ab.draw(canvas);
            canvas.restore();
        }
    }

    private void d(@NonNull Canvas canvas, int i, int i2) {
        if (m()) {
            int b2 = (int) (this.y + (b(this.J.get(this.L).floatValue()) * i));
            if (Build.VERSION.SDK_INT < 28) {
                int i3 = this.B;
                canvas.clipRect(b2 - i3, i2 - i3, b2 + i3, i3 + i2, Region.Op.UNION);
            }
            canvas.drawCircle(b2, i2, this.B, this.f);
        }
    }

    private boolean m() {
        return this.Q || Build.VERSION.SDK_INT < 21 || !(getBackground() instanceof RippleDrawable);
    }

    @Override // android.view.View
    public boolean onTouchEvent(@NonNull MotionEvent motionEvent) {
        if (!isEnabled()) {
            return false;
        }
        float x = motionEvent.getX();
        this.ac = (x - this.y) / this.P;
        this.ac = Math.max(0.0f, this.ac);
        this.ac = Math.min(1.0f, this.ac);
        switch (motionEvent.getActionMasked()) {
            case 0:
                this.D = x;
                if (!r()) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    if (pickActiveThumb()) {
                        requestFocus();
                        this.G = true;
                        n();
                        k();
                        invalidate();
                        t();
                        break;
                    }
                }
                break;
            case 1:
                this.G = false;
                MotionEvent motionEvent2 = this.E;
                if (motionEvent2 != null && motionEvent2.getActionMasked() == 0 && Math.abs(this.E.getX() - motionEvent.getX()) <= this.s && Math.abs(this.E.getY() - motionEvent.getY()) <= this.s && pickActiveThumb()) {
                    t();
                }
                if (this.K != -1) {
                    n();
                    this.K = -1;
                    u();
                }
                o();
                invalidate();
                break;
            case 2:
                if (!this.G) {
                    if (r() && Math.abs(x - this.D) < this.s) {
                        return false;
                    }
                    getParent().requestDisallowInterceptTouchEvent(true);
                    t();
                }
                if (pickActiveThumb()) {
                    this.G = true;
                    n();
                    k();
                    invalidate();
                    break;
                }
                break;
        }
        setPressed(this.G);
        this.E = MotionEvent.obtain(motionEvent);
        return true;
    }

    private static int a(float[] fArr, float f) {
        return Math.round(f * ((fArr.length / 2) - 1));
    }

    private double c(float f) {
        float f2 = this.M;
        if (f2 <= 0.0f) {
            return f;
        }
        int i = (int) ((this.I - this.H) / f2);
        return Math.round(f * i) / i;
    }

    protected boolean pickActiveThumb() {
        if (this.K != -1) {
            return true;
        }
        float valueOfTouchPositionAbsolute = getValueOfTouchPositionAbsolute();
        float f = f(valueOfTouchPositionAbsolute);
        this.K = 0;
        float abs = Math.abs(this.J.get(this.K).floatValue() - valueOfTouchPositionAbsolute);
        for (int i = 1; i < this.J.size(); i++) {
            float abs2 = Math.abs(this.J.get(i).floatValue() - valueOfTouchPositionAbsolute);
            float f2 = f(this.J.get(i).floatValue());
            if (Float.compare(abs2, abs) > 1) {
                break;
            }
            boolean z = !a() ? f2 - f < 0.0f : f2 - f > 0.0f;
            if (Float.compare(abs2, abs) < 0) {
                this.K = i;
            } else {
                if (Float.compare(abs2, abs) != 0) {
                    continue;
                } else if (Math.abs(f2 - f) < this.s) {
                    this.K = -1;
                    return false;
                } else if (z) {
                    this.K = i;
                }
            }
            abs = abs2;
        }
        return this.K != -1;
    }

    private float getValueOfTouchPositionAbsolute() {
        float f = this.ac;
        if (a()) {
            f = 1.0f - f;
        }
        float f2 = this.I;
        float f3 = this.H;
        return (f * (f2 - f3)) + f3;
    }

    private boolean n() {
        return d(getValueOfTouchPosition());
    }

    private boolean d(float f) {
        return a(this.K, f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(int i, float f) {
        if (Math.abs(f - this.J.get(i).floatValue()) < 1.0E-4d) {
            return false;
        }
        this.J.set(i, Float.valueOf(b(i, f)));
        this.L = i;
        b(i);
        return true;
    }

    private float b(int i, float f) {
        float f2 = 0.0f;
        if (this.M == 0.0f) {
            f2 = getMinSeparation();
        }
        if (this.ad == 0) {
            f2 = e(f2);
        }
        if (a()) {
            f2 = -f2;
        }
        int i2 = i + 1;
        int i3 = i - 1;
        return MathUtils.clamp(f, i3 < 0 ? this.H : this.J.get(i3).floatValue() + f2, i2 >= this.J.size() ? this.I : this.J.get(i2).floatValue() - f2);
    }

    private float e(float f) {
        if (f == 0.0f) {
            return 0.0f;
        }
        float f2 = (f - this.y) / this.P;
        float f3 = this.H;
        return (f2 * (f3 - this.I)) + f3;
    }

    protected void setSeparationUnit(int i) {
        this.ad = i;
    }

    private float getValueOfTouchPosition() {
        double c2 = c(this.ac);
        if (a()) {
            c2 = 1.0d - c2;
        }
        float f = this.I;
        float f2 = this.H;
        return (float) ((c2 * (f - f2)) + f2);
    }

    private float f(float f) {
        return (b(f) * this.P) + this.y;
    }

    private static float a(ValueAnimator valueAnimator, float f) {
        if (valueAnimator == null || !valueAnimator.isRunning()) {
            return f;
        }
        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        valueAnimator.cancel();
        return floatValue;
    }

    private ValueAnimator a(boolean z) {
        float f = 0.0f;
        float a2 = a(z ? this.r : this.q, z ? 0.0f : 1.0f);
        if (z) {
            f = 1.0f;
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(a2, f);
        ofFloat.setDuration(z ? 83L : 117L);
        ofFloat.setInterpolator(z ? AnimationUtils.DECELERATE_INTERPOLATOR : AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.slider.a.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                for (TooltipDrawable tooltipDrawable : a.this.m) {
                    tooltipDrawable.setRevealFraction(floatValue);
                }
                ViewCompat.postInvalidateOnAnimation(a.this);
            }
        });
        return ofFloat;
    }

    private void o() {
        if (this.p) {
            this.p = false;
            this.r = a(false);
            this.q = null;
            this.r.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.slider.a.3
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    for (TooltipDrawable tooltipDrawable : a.this.m) {
                        ViewUtils.getContentViewOverlay(a.this).remove(tooltipDrawable);
                    }
                }
            });
            this.r.start();
        }
    }

    private void p() {
        if (this.w != 2) {
            if (!this.p) {
                this.p = true;
                this.q = a(true);
                this.r = null;
                this.q.start();
            }
            Iterator<TooltipDrawable> it = this.m.iterator();
            for (int i = 0; i < this.J.size() && it.hasNext(); i++) {
                if (i != this.L) {
                    a(it.next(), this.J.get(i).floatValue());
                }
            }
            if (it.hasNext()) {
                a(it.next(), this.J.get(this.L).floatValue());
                return;
            }
            throw new IllegalStateException(String.format("Not enough labels(%d) to display all the values(%d)", Integer.valueOf(this.m.size()), Integer.valueOf(this.J.size())));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String g(float f) {
        if (hasLabelFormatter()) {
            return this.F.getFormattedValue(f);
        }
        return String.format(((float) ((int) f)) == f ? "%.0f" : "%.2f", Float.valueOf(f));
    }

    private void a(TooltipDrawable tooltipDrawable, float f) {
        tooltipDrawable.setText(g(f));
        int b2 = (this.y + ((int) (b(f) * this.P))) - (tooltipDrawable.getIntrinsicWidth() / 2);
        int l = l() - (this.C + this.A);
        tooltipDrawable.setBounds(b2, l - tooltipDrawable.getIntrinsicHeight(), tooltipDrawable.getIntrinsicWidth() + b2, l);
        Rect rect = new Rect(tooltipDrawable.getBounds());
        DescendantOffsetUtils.offsetDescendantRect(ViewUtils.getContentView(this), this, rect);
        tooltipDrawable.setBounds(rect);
        ViewUtils.getContentViewOverlay(this).add(tooltipDrawable);
    }

    private void q() {
        this.c.setStrokeWidth(this.x);
        this.d.setStrokeWidth(this.x);
        this.g.setStrokeWidth(this.x / 2.0f);
        this.h.setStrokeWidth(this.x / 2.0f);
    }

    private boolean r() {
        ViewParent parent = getParent();
        while (true) {
            boolean z = false;
            if (!(parent instanceof ViewGroup)) {
                return false;
            }
            ViewGroup viewGroup = (ViewGroup) parent;
            if (viewGroup.canScrollVertically(1) || viewGroup.canScrollVertically(-1)) {
                z = true;
            }
            if (z && viewGroup.shouldDelayChildPressedState()) {
                return true;
            }
            parent = parent.getParent();
        }
    }

    private void s() {
        for (L l : this.n) {
            Iterator<Float> it = this.J.iterator();
            while (it.hasNext()) {
                l.onValueChange(this, it.next().floatValue(), false);
            }
        }
    }

    private void b(int i) {
        for (L l : this.n) {
            l.onValueChange(this, this.J.get(i).floatValue(), true);
        }
        AccessibilityManager accessibilityManager = this.j;
        if (accessibilityManager != null && accessibilityManager.isEnabled()) {
            h(i);
        }
    }

    private void t() {
        for (T t : this.o) {
            t.onStartTrackingTouch(this);
        }
    }

    private void u() {
        for (T t : this.o) {
            t.onStopTrackingTouch(this);
        }
    }

    @Override // android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        this.c.setColor(a(this.aa));
        this.d.setColor(a(this.W));
        this.g.setColor(a(this.V));
        this.h.setColor(a(this.U));
        for (TooltipDrawable tooltipDrawable : this.m) {
            if (tooltipDrawable.isStateful()) {
                tooltipDrawable.setState(getDrawableState());
            }
        }
        if (this.ab.isStateful()) {
            this.ab.setState(getDrawableState());
        }
        this.f.setColor(a(this.T));
        this.f.setAlpha(63);
    }

    @ColorInt
    private int a(@NonNull ColorStateList colorStateList) {
        return colorStateList.getColorForState(getDrawableState(), colorStateList.getDefaultColor());
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, @NonNull KeyEvent keyEvent) {
        if (!isEnabled()) {
            return super.onKeyDown(i, keyEvent);
        }
        if (this.J.size() == 1) {
            this.K = 0;
        }
        if (this.K == -1) {
            Boolean a2 = a(i, keyEvent);
            return a2 != null ? a2.booleanValue() : super.onKeyDown(i, keyEvent);
        }
        this.R |= keyEvent.isLongPress();
        Float e = e(i);
        if (e != null) {
            if (d(this.J.get(this.K).floatValue() + e.floatValue())) {
                k();
                postInvalidate();
            }
            return true;
        }
        if (i != 23) {
            if (i != 61) {
                if (i != 66) {
                    return super.onKeyDown(i, keyEvent);
                }
            } else if (keyEvent.hasNoModifiers()) {
                return c(1);
            } else {
                if (keyEvent.isShiftPressed()) {
                    return c(-1);
                }
                return false;
            }
        }
        this.K = -1;
        o();
        postInvalidate();
        return true;
    }

    private Boolean a(int i, @NonNull KeyEvent keyEvent) {
        if (i != 61) {
            if (i != 66) {
                if (i != 81) {
                    switch (i) {
                        case 21:
                            d(-1);
                            return true;
                        case 22:
                            d(1);
                            return true;
                        case 23:
                            break;
                        default:
                            switch (i) {
                                case 69:
                                    c(-1);
                                    return true;
                                case 70:
                                    break;
                                default:
                                    return null;
                            }
                    }
                }
                c(1);
                return true;
            }
            this.K = this.L;
            postInvalidate();
            return true;
        } else if (keyEvent.hasNoModifiers()) {
            return Boolean.valueOf(c(1));
        } else {
            if (keyEvent.isShiftPressed()) {
                return Boolean.valueOf(c(-1));
            }
            return false;
        }
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, @NonNull KeyEvent keyEvent) {
        this.R = false;
        return super.onKeyUp(i, keyEvent);
    }

    final boolean a() {
        return ViewCompat.getLayoutDirection(this) == 1;
    }

    private boolean c(int i) {
        int i2 = this.L;
        this.L = (int) MathUtils.clamp(i2 + i, 0L, this.J.size() - 1);
        int i3 = this.L;
        if (i3 == i2) {
            return false;
        }
        if (this.K != -1) {
            this.K = i3;
        }
        k();
        postInvalidate();
        return true;
    }

    private boolean d(int i) {
        if (a()) {
            i = i == Integer.MIN_VALUE ? Integer.MAX_VALUE : -i;
        }
        return c(i);
    }

    private Float e(int i) {
        float f = this.R ? f(20) : v();
        switch (i) {
            case 21:
                if (!a()) {
                    f = -f;
                }
                return Float.valueOf(f);
            case 22:
                if (a()) {
                    f = -f;
                }
                return Float.valueOf(f);
            case 69:
                return Float.valueOf(-f);
            case 70:
            case 81:
                return Float.valueOf(f);
            default:
                return null;
        }
    }

    private float v() {
        float f = this.M;
        if (f == 0.0f) {
            return 1.0f;
        }
        return f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float f(int i) {
        float v = v();
        float f = (this.I - this.H) / v;
        float f2 = i;
        return f <= f2 ? v : Math.round(f / f2) * v;
    }

    @Override // android.view.View
    protected void onFocusChanged(boolean z, int i, @Nullable Rect rect) {
        super.onFocusChanged(z, i, rect);
        if (!z) {
            this.K = -1;
            o();
            this.i.clearKeyboardFocusForVirtualView(this.L);
            return;
        }
        g(i);
        this.i.requestKeyboardFocusForVirtualView(this.L);
    }

    private void g(int i) {
        if (i == 17) {
            d(Integer.MAX_VALUE);
        } else if (i != 66) {
            switch (i) {
                case 1:
                    c(Integer.MAX_VALUE);
                    return;
                case 2:
                    c(Integer.MIN_VALUE);
                    return;
                default:
                    return;
            }
        } else {
            d(Integer.MIN_VALUE);
        }
    }

    @VisibleForTesting
    final int getAccessibilityFocusedVirtualViewId() {
        return this.i.getAccessibilityFocusedVirtualViewId();
    }

    @Override // android.view.View
    @NonNull
    public CharSequence getAccessibilityClassName() {
        return SeekBar.class.getName();
    }

    @Override // android.view.View
    public boolean dispatchHoverEvent(@NonNull MotionEvent motionEvent) {
        return this.i.dispatchHoverEvent(motionEvent) || super.dispatchHoverEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean dispatchKeyEvent(@NonNull KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent);
    }

    private void h(int i) {
        a<S, L, T>.RunnableC0078a aVar = this.k;
        if (aVar == null) {
            this.k = new RunnableC0078a();
        } else {
            removeCallbacks(aVar);
        }
        this.k.a(i);
        postDelayed(this.k, 200L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: BaseSlider.java */
    /* renamed from: com.google.android.material.slider.a$a  reason: collision with other inner class name */
    /* loaded from: classes2.dex */
    public class RunnableC0078a implements Runnable {
        int a;

        private RunnableC0078a() {
            this.a = -1;
        }

        void a(int i) {
            this.a = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            a.this.i.sendEventForVirtualView(this.a, 4);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        c cVar = new c(super.onSaveInstanceState());
        cVar.a = this.H;
        cVar.b = this.I;
        cVar.c = new ArrayList<>(this.J);
        cVar.d = this.M;
        cVar.e = hasFocus();
        return cVar;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        c cVar = (c) parcelable;
        super.onRestoreInstanceState(cVar.getSuperState());
        this.H = cVar.a;
        this.I = cVar.b;
        setValuesInternal(cVar.c);
        this.M = cVar.d;
        if (cVar.e) {
            requestFocus();
        }
        s();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: BaseSlider.java */
    /* loaded from: classes2.dex */
    public static class c extends View.BaseSavedState {
        public static final Parcelable.Creator<c> CREATOR = new Parcelable.Creator<c>() { // from class: com.google.android.material.slider.a.c.1
            @NonNull
            /* renamed from: a */
            public c createFromParcel(@NonNull Parcel parcel) {
                return new c(parcel);
            }

            @NonNull
            /* renamed from: a */
            public c[] newArray(int i) {
                return new c[i];
            }
        };
        float a;
        float b;
        ArrayList<Float> c;
        float d;
        boolean e;

        c(Parcelable parcelable) {
            super(parcelable);
        }

        private c(@NonNull Parcel parcel) {
            super(parcel);
            this.a = parcel.readFloat();
            this.b = parcel.readFloat();
            this.c = new ArrayList<>();
            parcel.readList(this.c, Float.class.getClassLoader());
            this.d = parcel.readFloat();
            this.e = parcel.createBooleanArray()[0];
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(@NonNull Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeFloat(this.a);
            parcel.writeFloat(this.b);
            parcel.writeList(this.c);
            parcel.writeFloat(this.d);
            parcel.writeBooleanArray(new boolean[]{this.e});
        }
    }

    void a(int i, Rect rect) {
        int b2 = this.y + ((int) (b(getValues().get(i).floatValue()) * this.P));
        int l = l();
        int i2 = this.A;
        rect.set(b2 - i2, l - i2, b2 + i2, l + i2);
    }

    /* compiled from: BaseSlider.java */
    /* loaded from: classes2.dex */
    private static class b extends ExploreByTouchHelper {
        Rect c = new Rect();
        private final a<?, ?, ?> d;

        b(a<?, ?, ?> aVar) {
            super(aVar);
            this.d = aVar;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected int getVirtualViewAt(float f, float f2) {
            for (int i = 0; i < this.d.getValues().size(); i++) {
                this.d.a(i, this.c);
                if (this.c.contains((int) f, (int) f2)) {
                    return i;
                }
            }
            return -1;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected void getVisibleVirtualViews(List<Integer> list) {
            for (int i = 0; i < this.d.getValues().size(); i++) {
                list.add(Integer.valueOf(i));
            }
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected void onPopulateNodeForVirtualView(int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SET_PROGRESS);
            List<Float> values = this.d.getValues();
            float floatValue = values.get(i).floatValue();
            float valueFrom = this.d.getValueFrom();
            float valueTo = this.d.getValueTo();
            if (this.d.isEnabled()) {
                if (floatValue > valueFrom) {
                    accessibilityNodeInfoCompat.addAction(8192);
                }
                if (floatValue < valueTo) {
                    accessibilityNodeInfoCompat.addAction(4096);
                }
            }
            accessibilityNodeInfoCompat.setRangeInfo(AccessibilityNodeInfoCompat.RangeInfoCompat.obtain(1, valueFrom, valueTo, floatValue));
            accessibilityNodeInfoCompat.setClassName(SeekBar.class.getName());
            StringBuilder sb = new StringBuilder();
            if (this.d.getContentDescription() != null) {
                sb.append(this.d.getContentDescription());
                sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
            }
            if (values.size() > 1) {
                sb.append(b(i));
                sb.append(this.d.g(floatValue));
            }
            accessibilityNodeInfoCompat.setContentDescription(sb.toString());
            this.d.a(i, this.c);
            accessibilityNodeInfoCompat.setBoundsInParent(this.c);
        }

        @NonNull
        private String b(int i) {
            if (i == this.d.getValues().size() - 1) {
                return this.d.getContext().getString(R.string.material_slider_range_end);
            }
            return i == 0 ? this.d.getContext().getString(R.string.material_slider_range_start) : "";
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        protected boolean onPerformActionForVirtualView(int i, int i2, Bundle bundle) {
            if (!this.d.isEnabled()) {
                return false;
            }
            if (i2 == 4096 || i2 == 8192) {
                float f = this.d.f(20);
                if (i2 == 8192) {
                    f = -f;
                }
                if (this.d.a()) {
                    f = -f;
                }
                if (!this.d.a(i, MathUtils.clamp(this.d.getValues().get(i).floatValue() + f, this.d.getValueFrom(), this.d.getValueTo()))) {
                    return false;
                }
                this.d.k();
                this.d.postInvalidate();
                invalidateVirtualView(i);
                return true;
            } else if (i2 != 16908349 || bundle == null || !bundle.containsKey(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_PROGRESS_VALUE)) {
                return false;
            } else {
                if (!this.d.a(i, bundle.getFloat(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_PROGRESS_VALUE))) {
                    return false;
                }
                this.d.k();
                this.d.postInvalidate();
                invalidateVirtualView(i);
                return true;
            }
        }
    }
}
