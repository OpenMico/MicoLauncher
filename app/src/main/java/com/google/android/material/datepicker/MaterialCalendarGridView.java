package com.google.android.material.datepicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.ListAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R;
import com.google.android.material.internal.ViewUtils;
import java.util.Calendar;

/* loaded from: classes2.dex */
public final class MaterialCalendarGridView extends GridView {
    private final Calendar a;
    private final boolean b;

    public MaterialCalendarGridView(Context context) {
        this(context, null);
    }

    public MaterialCalendarGridView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MaterialCalendarGridView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = k.c();
        if (MaterialDatePicker.a(getContext())) {
            setNextFocusLeftId(R.id.cancel_button);
            setNextFocusRightId(R.id.confirm_button);
        }
        this.b = MaterialDatePicker.b(getContext());
        ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegateCompat() { // from class: com.google.android.material.datepicker.MaterialCalendarGridView.1
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public void onInitializeAccessibilityNodeInfo(View view, @NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                accessibilityNodeInfoCompat.setCollectionInfo(null);
            }
        });
    }

    @Override // android.widget.AbsListView, android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getAdapter2().notifyDataSetChanged();
    }

    @Override // android.widget.GridView, android.widget.AdapterView
    public void setSelection(int i) {
        if (i < getAdapter2().a()) {
            super.setSelection(getAdapter2().a());
        } else {
            super.setSelection(i);
        }
    }

    @Override // android.widget.GridView, android.widget.AbsListView, android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (!super.onKeyDown(i, keyEvent)) {
            return false;
        }
        if (getSelectedItemPosition() == -1 || getSelectedItemPosition() >= getAdapter2().a()) {
            return true;
        }
        if (19 != i) {
            return false;
        }
        setSelection(getAdapter2().a());
        return true;
    }

    @NonNull
    /* renamed from: a */
    public g getAdapter2() {
        return (g) super.getAdapter();
    }

    @Override // android.widget.GridView, android.widget.AbsListView
    public final void setAdapter(ListAdapter listAdapter) {
        if (listAdapter instanceof g) {
            super.setAdapter(listAdapter);
            return;
        }
        throw new IllegalArgumentException(String.format("%1$s must have its Adapter set to a %2$s", MaterialCalendarGridView.class.getCanonicalName(), g.class.getCanonicalName()));
    }

    @Override // android.view.View
    protected final void onDraw(@NonNull Canvas canvas) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        MaterialCalendarGridView materialCalendarGridView = this;
        super.onDraw(canvas);
        g a = getAdapter2();
        DateSelector<?> dateSelector = a.c;
        b bVar = a.d;
        Long a2 = a.getItem(a.a());
        Long a3 = a.getItem(a.b());
        for (Pair<Long, Long> pair : dateSelector.getSelectedRanges()) {
            if (pair.first == null) {
                materialCalendarGridView = this;
            } else if (pair.second != null) {
                long longValue = pair.first.longValue();
                long longValue2 = pair.second.longValue();
                if (!a(a2, a3, Long.valueOf(longValue), Long.valueOf(longValue2))) {
                    boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
                    if (longValue < a2.longValue()) {
                        i2 = a.a();
                        if (a.e(i2)) {
                            i = 0;
                        } else if (!isLayoutRtl) {
                            i = materialCalendarGridView.getChildAt(i2 - 1).getRight();
                        } else {
                            i = materialCalendarGridView.getChildAt(i2 - 1).getLeft();
                        }
                    } else {
                        materialCalendarGridView.a.setTimeInMillis(longValue);
                        i2 = a.c(materialCalendarGridView.a.get(5));
                        i = a(materialCalendarGridView.getChildAt(i2));
                    }
                    if (longValue2 > a3.longValue()) {
                        i4 = Math.min(a.b(), getChildCount() - 1);
                        if (a.f(i4)) {
                            i3 = getWidth();
                        } else if (!isLayoutRtl) {
                            i3 = materialCalendarGridView.getChildAt(i4).getRight();
                        } else {
                            i3 = materialCalendarGridView.getChildAt(i4).getLeft();
                        }
                    } else {
                        materialCalendarGridView.a.setTimeInMillis(longValue2);
                        i4 = a.c(materialCalendarGridView.a.get(5));
                        i3 = a(materialCalendarGridView.getChildAt(i4));
                    }
                    int itemId = (int) a.getItemId(i2);
                    int itemId2 = (int) a.getItemId(i4);
                    while (itemId <= itemId2) {
                        int numColumns = getNumColumns() * itemId;
                        int numColumns2 = (getNumColumns() + numColumns) - 1;
                        View childAt = materialCalendarGridView.getChildAt(numColumns);
                        int top = childAt.getTop() + bVar.a.a();
                        int bottom = childAt.getBottom() - bVar.a.b();
                        if (!isLayoutRtl) {
                            i6 = numColumns > i2 ? 0 : i;
                            i5 = i4 > numColumns2 ? getWidth() : i3;
                        } else {
                            i6 = i4 > numColumns2 ? 0 : i3;
                            i5 = numColumns > i2 ? getWidth() : i;
                        }
                        canvas.drawRect(i6, top, i5, bottom, bVar.h);
                        itemId++;
                        a = a;
                        materialCalendarGridView = this;
                    }
                    materialCalendarGridView = this;
                }
            }
        }
    }

    @Override // android.widget.GridView, android.widget.AbsListView, android.view.View
    public void onMeasure(int i, int i2) {
        if (this.b) {
            super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(16777215, Integer.MIN_VALUE));
            getLayoutParams().height = getMeasuredHeight();
            return;
        }
        super.onMeasure(i, i2);
    }

    @Override // android.widget.GridView, android.widget.AbsListView, android.view.View
    protected void onFocusChanged(boolean z, int i, Rect rect) {
        if (z) {
            a(i, rect);
        } else {
            super.onFocusChanged(false, i, rect);
        }
    }

    private void a(int i, Rect rect) {
        if (i == 33) {
            setSelection(getAdapter2().b());
        } else if (i == 130) {
            setSelection(getAdapter2().a());
        } else {
            super.onFocusChanged(true, i, rect);
        }
    }

    private static boolean a(@Nullable Long l, @Nullable Long l2, @Nullable Long l3, @Nullable Long l4) {
        return l == null || l2 == null || l3 == null || l4 == null || l3.longValue() > l2.longValue() || l4.longValue() < l.longValue();
    }

    private static int a(@NonNull View view) {
        return view.getLeft() + (view.getWidth() / 2);
    }
}
