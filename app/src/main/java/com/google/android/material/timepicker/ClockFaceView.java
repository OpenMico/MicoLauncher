package com.google.android.material.timepicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.timepicker.ClockHandView;
import java.util.Arrays;

/* loaded from: classes2.dex */
class ClockFaceView extends c implements ClockHandView.OnRotateListener {
    private final ClockHandView a;
    private final Rect b;
    private final RectF c;
    private final SparseArray<TextView> d;
    private final AccessibilityDelegateCompat e;
    private final int[] f;
    private final float[] g;
    private final int h;
    private final int i;
    private final int j;
    private final int k;
    private String[] l;
    private float m;
    private final ColorStateList n;

    public ClockFaceView(@NonNull Context context) {
        this(context, null);
    }

    public ClockFaceView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.materialClockStyle);
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public ClockFaceView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.b = new Rect();
        this.c = new RectF();
        this.d = new SparseArray<>();
        this.g = new float[]{0.0f, 0.9f, 1.0f};
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ClockFaceView, i, R.style.Widget_MaterialComponents_TimePicker_Clock);
        Resources resources = getResources();
        this.n = MaterialResources.getColorStateList(context, obtainStyledAttributes, R.styleable.ClockFaceView_clockNumberTextColor);
        LayoutInflater.from(context).inflate(R.layout.material_clockface_view, (ViewGroup) this, true);
        this.a = (ClockHandView) findViewById(R.id.material_clock_hand);
        this.h = resources.getDimensionPixelSize(R.dimen.material_clock_hand_padding);
        ColorStateList colorStateList = this.n;
        int colorForState = colorStateList.getColorForState(new int[]{16842913}, colorStateList.getDefaultColor());
        this.f = new int[]{colorForState, colorForState, this.n.getDefaultColor()};
        this.a.a(this);
        int defaultColor = AppCompatResources.getColorStateList(context, R.color.material_timepicker_clockface).getDefaultColor();
        ColorStateList colorStateList2 = MaterialResources.getColorStateList(context, obtainStyledAttributes, R.styleable.ClockFaceView_clockFaceBackgroundColor);
        setBackgroundColor(colorStateList2 != null ? colorStateList2.getDefaultColor() : defaultColor);
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.google.android.material.timepicker.ClockFaceView.1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                if (!ClockFaceView.this.isShown()) {
                    return true;
                }
                ClockFaceView.this.getViewTreeObserver().removeOnPreDrawListener(this);
                ClockFaceView.this.a(((ClockFaceView.this.getHeight() / 2) - ClockFaceView.this.a.c()) - ClockFaceView.this.h);
                return true;
            }
        });
        setFocusable(true);
        obtainStyledAttributes.recycle();
        this.e = new AccessibilityDelegateCompat() { // from class: com.google.android.material.timepicker.ClockFaceView.2
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public void onInitializeAccessibilityNodeInfo(View view, @NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                int intValue = ((Integer) view.getTag(R.id.material_value_index)).intValue();
                if (intValue > 0) {
                    accessibilityNodeInfoCompat.setTraversalAfter((View) ClockFaceView.this.d.get(intValue - 1));
                }
                accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(0, 1, intValue, 1, false, view.isSelected()));
            }
        };
        String[] strArr = new String[12];
        Arrays.fill(strArr, "");
        a(strArr, 0);
        this.i = resources.getDimensionPixelSize(R.dimen.material_time_picker_minimum_screen_height);
        this.j = resources.getDimensionPixelSize(R.dimen.material_time_picker_minimum_screen_width);
        this.k = resources.getDimensionPixelSize(R.dimen.material_clock_size);
    }

    public void a(String[] strArr, @StringRes int i) {
        this.l = strArr;
        b(i);
    }

    private void b(@StringRes int i) {
        LayoutInflater from = LayoutInflater.from(getContext());
        int size = this.d.size();
        for (int i2 = 0; i2 < Math.max(this.l.length, size); i2++) {
            TextView textView = this.d.get(i2);
            if (i2 >= this.l.length) {
                removeView(textView);
                this.d.remove(i2);
            } else {
                if (textView == null) {
                    textView = (TextView) from.inflate(R.layout.material_clockface_textview, (ViewGroup) this, false);
                    this.d.put(i2, textView);
                    addView(textView);
                }
                textView.setVisibility(0);
                textView.setText(this.l[i2]);
                textView.setTag(R.id.material_value_index, Integer.valueOf(i2));
                ViewCompat.setAccessibilityDelegate(textView, this.e);
                textView.setTextColor(this.n);
                if (i != 0) {
                    textView.setContentDescription(getResources().getString(i, this.l[i2]));
                }
            }
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(@NonNull AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo).setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(1, this.l.length, false, 1));
    }

    @Override // com.google.android.material.timepicker.c
    public void a(int i) {
        if (i != b()) {
            super.a(i);
            this.a.a(b());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        c();
    }

    private void c() {
        RectF b = this.a.b();
        for (int i = 0; i < this.d.size(); i++) {
            TextView textView = this.d.get(i);
            if (textView != null) {
                textView.getDrawingRect(this.b);
                this.b.offset(textView.getPaddingLeft(), textView.getPaddingTop());
                offsetDescendantRectToMyCoords(textView, this.b);
                this.c.set(this.b);
                textView.getPaint().setShader(a(b, this.c));
                textView.invalidate();
            }
        }
    }

    private RadialGradient a(RectF rectF, RectF rectF2) {
        if (!RectF.intersects(rectF, rectF2)) {
            return null;
        }
        return new RadialGradient(rectF.centerX() - this.c.left, rectF.centerY() - this.c.top, rectF.width() * 0.5f, this.f, this.g, Shader.TileMode.CLAMP);
    }

    @Override // com.google.android.material.timepicker.ClockHandView.OnRotateListener
    public void onRotate(float f, boolean z) {
        if (Math.abs(this.m - f) > 0.001f) {
            this.m = f;
            c();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.View
    public void onMeasure(int i, int i2) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int a = (int) (this.k / a(this.i / displayMetrics.heightPixels, this.j / displayMetrics.widthPixels, 1.0f));
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(a, 1073741824);
        setMeasuredDimension(a, a);
        super.onMeasure(makeMeasureSpec, makeMeasureSpec);
    }

    private static float a(float f, float f2, float f3) {
        return Math.max(Math.max(f, f2), f3);
    }
}
