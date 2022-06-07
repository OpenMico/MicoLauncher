package com.google.android.material.bottomnavigation;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationBarMenuView;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes2.dex */
public class BottomNavigationMenuView extends NavigationBarMenuView {
    private final int a;
    private final int b;
    private final int c;
    private final int d;
    private final int e;
    private boolean f;
    private int[] g = new int[5];

    public BottomNavigationMenuView(@NonNull Context context) {
        super(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 17;
        setLayoutParams(layoutParams);
        Resources resources = getResources();
        this.a = resources.getDimensionPixelSize(R.dimen.design_bottom_navigation_item_max_width);
        this.b = resources.getDimensionPixelSize(R.dimen.design_bottom_navigation_item_min_width);
        this.c = resources.getDimensionPixelSize(R.dimen.design_bottom_navigation_active_item_max_width);
        this.d = resources.getDimensionPixelSize(R.dimen.design_bottom_navigation_active_item_min_width);
        this.e = resources.getDimensionPixelSize(R.dimen.design_bottom_navigation_height);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        MenuBuilder menu = getMenu();
        int size = View.MeasureSpec.getSize(i);
        int size2 = menu.getVisibleItems().size();
        int childCount = getChildCount();
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.e, 1073741824);
        if (!isShifting(getLabelVisibilityMode(), size2) || !isItemHorizontalTranslationEnabled()) {
            int min = Math.min(size / (size2 == 0 ? 1 : size2), this.c);
            int i3 = size - (size2 * min);
            for (int i4 = 0; i4 < childCount; i4++) {
                if (getChildAt(i4).getVisibility() != 8) {
                    int[] iArr = this.g;
                    iArr[i4] = min;
                    if (i3 > 0) {
                        iArr[i4] = iArr[i4] + 1;
                        i3--;
                    }
                } else {
                    this.g[i4] = 0;
                }
            }
        } else {
            View childAt = getChildAt(getSelectedItemPosition());
            int i5 = this.d;
            if (childAt.getVisibility() != 8) {
                childAt.measure(View.MeasureSpec.makeMeasureSpec(this.c, Integer.MIN_VALUE), makeMeasureSpec);
                i5 = Math.max(i5, childAt.getMeasuredWidth());
            }
            int i6 = size2 - (childAt.getVisibility() != 8 ? 1 : 0);
            int min2 = Math.min(size - (this.b * i6), Math.min(i5, this.c));
            int i7 = size - min2;
            int min3 = Math.min(i7 / (i6 == 0 ? 1 : i6), this.a);
            int i8 = i7 - (i6 * min3);
            int i9 = 0;
            while (i9 < childCount) {
                if (getChildAt(i9).getVisibility() != 8) {
                    this.g[i9] = i9 == getSelectedItemPosition() ? min2 : min3;
                    if (i8 > 0) {
                        int[] iArr2 = this.g;
                        iArr2[i9] = iArr2[i9] + 1;
                        i8--;
                    }
                } else {
                    this.g[i9] = 0;
                }
                i9++;
            }
        }
        int i10 = 0;
        for (int i11 = 0; i11 < childCount; i11++) {
            View childAt2 = getChildAt(i11);
            if (childAt2.getVisibility() != 8) {
                childAt2.measure(View.MeasureSpec.makeMeasureSpec(this.g[i11], 1073741824), makeMeasureSpec);
                childAt2.getLayoutParams().width = childAt2.getMeasuredWidth();
                i10 += childAt2.getMeasuredWidth();
            }
        }
        setMeasuredDimension(View.resolveSizeAndState(i10, View.MeasureSpec.makeMeasureSpec(i10, 1073741824), 0), View.resolveSizeAndState(this.e, makeMeasureSpec, 0));
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        int i5 = i3 - i;
        int i6 = i4 - i2;
        int i7 = 0;
        for (int i8 = 0; i8 < childCount; i8++) {
            View childAt = getChildAt(i8);
            if (childAt.getVisibility() != 8) {
                if (ViewCompat.getLayoutDirection(this) == 1) {
                    int i9 = i5 - i7;
                    childAt.layout(i9 - childAt.getMeasuredWidth(), 0, i9, i6);
                } else {
                    childAt.layout(i7, 0, childAt.getMeasuredWidth() + i7, i6);
                }
                i7 += childAt.getMeasuredWidth();
            }
        }
    }

    public void setItemHorizontalTranslationEnabled(boolean z) {
        this.f = z;
    }

    public boolean isItemHorizontalTranslationEnabled() {
        return this.f;
    }

    @Override // com.google.android.material.navigation.NavigationBarMenuView
    @NonNull
    protected NavigationBarItemView createNavigationBarItemView(@NonNull Context context) {
        return new BottomNavigationItemView(context);
    }
}
