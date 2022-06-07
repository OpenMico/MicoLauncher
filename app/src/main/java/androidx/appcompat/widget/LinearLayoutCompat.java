package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.appcompat.R;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.badge.BadgeDrawable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public class LinearLayoutCompat extends ViewGroup {
    public static final int HORIZONTAL = 0;
    public static final int SHOW_DIVIDER_BEGINNING = 1;
    public static final int SHOW_DIVIDER_END = 4;
    public static final int SHOW_DIVIDER_MIDDLE = 2;
    public static final int SHOW_DIVIDER_NONE = 0;
    public static final int VERTICAL = 1;
    private boolean a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private float g;
    private boolean h;
    private int[] i;
    private int[] j;
    private Drawable k;
    private int l;
    private int m;
    private int n;
    private int o;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* loaded from: classes.dex */
    public @interface DividerMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* loaded from: classes.dex */
    public @interface OrientationMode {
    }

    int a(View view) {
        return 0;
    }

    int a(View view, int i) {
        return 0;
    }

    int b(int i) {
        return 0;
    }

    int b(View view) {
        return 0;
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public LinearLayoutCompat(@NonNull Context context) {
        this(context, null);
    }

    public LinearLayoutCompat(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LinearLayoutCompat(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = true;
        this.b = -1;
        this.c = 0;
        this.e = BadgeDrawable.TOP_START;
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, R.styleable.LinearLayoutCompat, i, 0);
        ViewCompat.saveAttributeDataForStyleable(this, context, R.styleable.LinearLayoutCompat, attributeSet, obtainStyledAttributes.getWrappedTypeArray(), i, 0);
        int i2 = obtainStyledAttributes.getInt(R.styleable.LinearLayoutCompat_android_orientation, -1);
        if (i2 >= 0) {
            setOrientation(i2);
        }
        int i3 = obtainStyledAttributes.getInt(R.styleable.LinearLayoutCompat_android_gravity, -1);
        if (i3 >= 0) {
            setGravity(i3);
        }
        boolean z = obtainStyledAttributes.getBoolean(R.styleable.LinearLayoutCompat_android_baselineAligned, true);
        if (!z) {
            setBaselineAligned(z);
        }
        this.g = obtainStyledAttributes.getFloat(R.styleable.LinearLayoutCompat_android_weightSum, -1.0f);
        this.b = obtainStyledAttributes.getInt(R.styleable.LinearLayoutCompat_android_baselineAlignedChildIndex, -1);
        this.h = obtainStyledAttributes.getBoolean(R.styleable.LinearLayoutCompat_measureWithLargestChild, false);
        setDividerDrawable(obtainStyledAttributes.getDrawable(R.styleable.LinearLayoutCompat_divider));
        this.n = obtainStyledAttributes.getInt(R.styleable.LinearLayoutCompat_showDividers, 0);
        this.o = obtainStyledAttributes.getDimensionPixelSize(R.styleable.LinearLayoutCompat_dividerPadding, 0);
        obtainStyledAttributes.recycle();
    }

    public void setShowDividers(int i) {
        if (i != this.n) {
            requestLayout();
        }
        this.n = i;
    }

    public int getShowDividers() {
        return this.n;
    }

    public Drawable getDividerDrawable() {
        return this.k;
    }

    public void setDividerDrawable(Drawable drawable) {
        if (drawable != this.k) {
            this.k = drawable;
            boolean z = false;
            if (drawable != null) {
                this.l = drawable.getIntrinsicWidth();
                this.m = drawable.getIntrinsicHeight();
            } else {
                this.l = 0;
                this.m = 0;
            }
            if (drawable == null) {
                z = true;
            }
            setWillNotDraw(z);
            requestLayout();
        }
    }

    public void setDividerPadding(int i) {
        this.o = i;
    }

    public int getDividerPadding() {
        return this.o;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public int getDividerWidth() {
        return this.l;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        if (this.k != null) {
            if (this.d == 1) {
                a(canvas);
            } else {
                b(canvas);
            }
        }
    }

    void a(Canvas canvas) {
        int i;
        int virtualChildCount = getVirtualChildCount();
        for (int i2 = 0; i2 < virtualChildCount; i2++) {
            View a = a(i2);
            if (!(a == null || a.getVisibility() == 8 || !hasDividerBeforeChildAt(i2))) {
                a(canvas, (a.getTop() - ((LayoutParams) a.getLayoutParams()).topMargin) - this.m);
            }
        }
        if (hasDividerBeforeChildAt(virtualChildCount)) {
            View a2 = a(virtualChildCount - 1);
            if (a2 == null) {
                i = (getHeight() - getPaddingBottom()) - this.m;
            } else {
                i = a2.getBottom() + ((LayoutParams) a2.getLayoutParams()).bottomMargin;
            }
            a(canvas, i);
        }
    }

    void b(Canvas canvas) {
        int i;
        int i2;
        int virtualChildCount = getVirtualChildCount();
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        for (int i3 = 0; i3 < virtualChildCount; i3++) {
            View a = a(i3);
            if (!(a == null || a.getVisibility() == 8 || !hasDividerBeforeChildAt(i3))) {
                LayoutParams layoutParams = (LayoutParams) a.getLayoutParams();
                if (isLayoutRtl) {
                    i2 = a.getRight() + layoutParams.rightMargin;
                } else {
                    i2 = (a.getLeft() - layoutParams.leftMargin) - this.l;
                }
                b(canvas, i2);
            }
        }
        if (hasDividerBeforeChildAt(virtualChildCount)) {
            View a2 = a(virtualChildCount - 1);
            if (a2 != null) {
                LayoutParams layoutParams2 = (LayoutParams) a2.getLayoutParams();
                if (isLayoutRtl) {
                    i = (a2.getLeft() - layoutParams2.leftMargin) - this.l;
                } else {
                    i = a2.getRight() + layoutParams2.rightMargin;
                }
            } else if (isLayoutRtl) {
                i = getPaddingLeft();
            } else {
                i = (getWidth() - getPaddingRight()) - this.l;
            }
            b(canvas, i);
        }
    }

    void a(Canvas canvas, int i) {
        this.k.setBounds(getPaddingLeft() + this.o, i, (getWidth() - getPaddingRight()) - this.o, this.m + i);
        this.k.draw(canvas);
    }

    void b(Canvas canvas, int i) {
        this.k.setBounds(i, getPaddingTop() + this.o, this.l + i, (getHeight() - getPaddingBottom()) - this.o);
        this.k.draw(canvas);
    }

    public boolean isBaselineAligned() {
        return this.a;
    }

    public void setBaselineAligned(boolean z) {
        this.a = z;
    }

    public boolean isMeasureWithLargestChildEnabled() {
        return this.h;
    }

    public void setMeasureWithLargestChildEnabled(boolean z) {
        this.h = z;
    }

    @Override // android.view.View
    public int getBaseline() {
        int i;
        if (this.b < 0) {
            return super.getBaseline();
        }
        int childCount = getChildCount();
        int i2 = this.b;
        if (childCount > i2) {
            View childAt = getChildAt(i2);
            int baseline = childAt.getBaseline();
            if (baseline != -1) {
                int i3 = this.c;
                if (this.d == 1 && (i = this.e & 112) != 48) {
                    if (i == 16) {
                        i3 += ((((getBottom() - getTop()) - getPaddingTop()) - getPaddingBottom()) - this.f) / 2;
                    } else if (i == 80) {
                        i3 = ((getBottom() - getTop()) - getPaddingBottom()) - this.f;
                    }
                }
                return i3 + ((LayoutParams) childAt.getLayoutParams()).topMargin + baseline;
            } else if (this.b == 0) {
                return -1;
            } else {
                throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
            }
        } else {
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
        }
    }

    public int getBaselineAlignedChildIndex() {
        return this.b;
    }

    public void setBaselineAlignedChildIndex(int i) {
        if (i < 0 || i >= getChildCount()) {
            throw new IllegalArgumentException("base aligned child index out of range (0, " + getChildCount() + ")");
        }
        this.b = i;
    }

    View a(int i) {
        return getChildAt(i);
    }

    int getVirtualChildCount() {
        return getChildCount();
    }

    public float getWeightSum() {
        return this.g;
    }

    public void setWeightSum(float f) {
        this.g = Math.max(0.0f, f);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        if (this.d == 1) {
            a(i, i2);
        } else {
            b(i, i2);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    protected boolean hasDividerBeforeChildAt(int i) {
        if (i == 0) {
            return (this.n & 1) != 0;
        }
        if (i == getChildCount()) {
            return (this.n & 4) != 0;
        }
        if ((this.n & 2) == 0) {
            return false;
        }
        for (int i2 = i - 1; i2 >= 0; i2--) {
            if (getChildAt(i2).getVisibility() != 8) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:142:0x0338  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void a(int r35, int r36) {
        /*
            Method dump skipped, instructions count: 932
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.LinearLayoutCompat.a(int, int):void");
    }

    private void c(int i, int i2) {
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
        for (int i3 = 0; i3 < i; i3++) {
            View a = a(i3);
            if (a.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) a.getLayoutParams();
                if (layoutParams.width == -1) {
                    int i4 = layoutParams.height;
                    layoutParams.height = a.getMeasuredHeight();
                    measureChildWithMargins(a, makeMeasureSpec, 0, i2, 0);
                    layoutParams.height = i4;
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:191:0x0479  */
    /* JADX WARN: Removed duplicated region for block: B:192:0x047c  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0174  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0195  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x01c7  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x01cf  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x01de  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x01eb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void b(int r39, int r40) {
        /*
            Method dump skipped, instructions count: 1345
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.LinearLayoutCompat.b(int, int):void");
    }

    private void d(int i, int i2) {
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824);
        for (int i3 = 0; i3 < i; i3++) {
            View a = a(i3);
            if (a.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) a.getLayoutParams();
                if (layoutParams.height == -1) {
                    int i4 = layoutParams.width;
                    layoutParams.width = a.getMeasuredWidth();
                    measureChildWithMargins(a, i2, 0, makeMeasureSpec, 0);
                    layoutParams.width = i4;
                }
            }
        }
    }

    void a(View view, int i, int i2, int i3, int i4, int i5) {
        measureChildWithMargins(view, i2, i3, i4, i5);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.d == 1) {
            a(i, i2, i3, i4);
        } else {
            b(i, i2, i3, i4);
        }
    }

    void a(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        int paddingLeft = getPaddingLeft();
        int i8 = i3 - i;
        int paddingRight = i8 - getPaddingRight();
        int paddingRight2 = (i8 - paddingLeft) - getPaddingRight();
        int virtualChildCount = getVirtualChildCount();
        int i9 = this.e;
        int i10 = i9 & 112;
        int i11 = i9 & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        if (i10 != 16) {
            i5 = i10 != 80 ? getPaddingTop() : ((getPaddingTop() + i4) - i2) - this.f;
        } else {
            i5 = getPaddingTop() + (((i4 - i2) - this.f) / 2);
        }
        int i12 = 0;
        while (i12 < virtualChildCount) {
            View a = a(i12);
            if (a == null) {
                i5 += b(i12);
                i6 = 1;
            } else if (a.getVisibility() != 8) {
                int measuredWidth = a.getMeasuredWidth();
                int measuredHeight = a.getMeasuredHeight();
                LayoutParams layoutParams = (LayoutParams) a.getLayoutParams();
                i11 = layoutParams.gravity;
                if (i11 < 0) {
                }
                int absoluteGravity = GravityCompat.getAbsoluteGravity(i11, ViewCompat.getLayoutDirection(this)) & 7;
                if (absoluteGravity == 1) {
                    i7 = ((((paddingRight2 - measuredWidth) / 2) + paddingLeft) + layoutParams.leftMargin) - layoutParams.rightMargin;
                } else if (absoluteGravity != 5) {
                    i7 = layoutParams.leftMargin + paddingLeft;
                } else {
                    i7 = (paddingRight - measuredWidth) - layoutParams.rightMargin;
                }
                if (hasDividerBeforeChildAt(i12)) {
                    i5 += this.m;
                }
                int i13 = i5 + layoutParams.topMargin;
                a(a, i7, i13 + a(a), measuredWidth, measuredHeight);
                i5 = i13 + measuredHeight + layoutParams.bottomMargin + b(a);
                i12 += a(a, i12);
                i6 = 1;
            } else {
                i6 = 1;
            }
            i12 += i6;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x010e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void b(int r25, int r26, int r27, int r28) {
        /*
            Method dump skipped, instructions count: 344
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.LinearLayoutCompat.b(int, int, int, int):void");
    }

    private void a(View view, int i, int i2, int i3, int i4) {
        view.layout(i, i2, i3 + i, i4 + i2);
    }

    public void setOrientation(int i) {
        if (this.d != i) {
            this.d = i;
            requestLayout();
        }
    }

    public int getOrientation() {
        return this.d;
    }

    public void setGravity(int i) {
        if (this.e != i) {
            if ((8388615 & i) == 0) {
                i |= GravityCompat.START;
            }
            if ((i & 112) == 0) {
                i |= 48;
            }
            this.e = i;
            requestLayout();
        }
    }

    public int getGravity() {
        return this.e;
    }

    public void setHorizontalGravity(int i) {
        int i2 = i & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        int i3 = this.e;
        if ((8388615 & i3) != i2) {
            this.e = i2 | ((-8388616) & i3);
            requestLayout();
        }
    }

    public void setVerticalGravity(int i) {
        int i2 = i & 112;
        int i3 = this.e;
        if ((i3 & 112) != i2) {
            this.e = i2 | (i3 & (-113));
            requestLayout();
        }
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public LayoutParams generateDefaultLayoutParams() {
        int i = this.d;
        if (i == 0) {
            return new LayoutParams(-2, -2);
        }
        if (i == 1) {
            return new LayoutParams(-1, -2);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName("androidx.appcompat.widget.LinearLayoutCompat");
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName("androidx.appcompat.widget.LinearLayoutCompat");
    }

    /* loaded from: classes.dex */
    public static class LayoutParams extends LinearLayout.LayoutParams {
        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(int i, int i2, float f) {
            super(i, i2, f);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }
    }
}
