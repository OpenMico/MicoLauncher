package com.google.android.material.navigationrail;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationBarMenuView;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes2.dex */
public class NavigationRailMenuView extends NavigationBarMenuView {
    private final FrameLayout.LayoutParams a = new FrameLayout.LayoutParams(-1, -2);

    public NavigationRailMenuView(@NonNull Context context) {
        super(context);
        FrameLayout.LayoutParams layoutParams = this.a;
        layoutParams.gravity = 49;
        setLayoutParams(layoutParams);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int i3;
        int size = View.MeasureSpec.getSize(i2);
        int size2 = getMenu().getVisibleItems().size();
        if (size2 <= 1 || !isShifting(getLabelVisibilityMode(), size2)) {
            i3 = a(i, size, size2, null);
        } else {
            i3 = b(i, size, size2);
        }
        setMeasuredDimension(View.resolveSizeAndState(View.MeasureSpec.getSize(i), i, 0), View.resolveSizeAndState(i3, i2, 0));
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        int i5 = i3 - i;
        int i6 = 0;
        for (int i7 = 0; i7 < childCount; i7++) {
            View childAt = getChildAt(i7);
            if (childAt.getVisibility() != 8) {
                int measuredHeight = childAt.getMeasuredHeight() + i6;
                childAt.layout(0, i6, i5, measuredHeight);
                i6 = measuredHeight;
            }
        }
    }

    @Override // com.google.android.material.navigation.NavigationBarMenuView
    @NonNull
    protected NavigationBarItemView createNavigationBarItemView(@NonNull Context context) {
        return new a(context);
    }

    private int a(int i, int i2, int i3) {
        return View.MeasureSpec.makeMeasureSpec(Math.min(View.MeasureSpec.getSize(i), i2 / Math.max(1, i3)), 0);
    }

    private int b(int i, int i2, int i3) {
        int i4;
        View childAt = getChildAt(getSelectedItemPosition());
        if (childAt != null) {
            i4 = a(childAt, i, a(i, i2, i3));
            i2 -= i4;
            i3--;
        } else {
            i4 = 0;
        }
        return i4 + a(i, i2, i3, childAt);
    }

    private int a(int i, int i2, int i3, View view) {
        int i4;
        a(i, i2, i3);
        if (view == null) {
            i4 = a(i, i2, i3);
        } else {
            i4 = View.MeasureSpec.makeMeasureSpec(view.getMeasuredHeight(), 0);
        }
        int childCount = getChildCount();
        int i5 = 0;
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            if (childAt != view) {
                i5 += a(childAt, i, i4);
            }
        }
        return i5;
    }

    private int a(View view, int i, int i2) {
        if (view.getVisibility() == 8) {
            return 0;
        }
        view.measure(i, i2);
        return view.getMeasuredHeight();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setMenuGravity(int i) {
        if (this.a.gravity != i) {
            FrameLayout.LayoutParams layoutParams = this.a;
            layoutParams.gravity = i;
            setLayoutParams(layoutParams);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getMenuGravity() {
        return this.a.gravity;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a() {
        return (this.a.gravity & 112) == 48;
    }
}
