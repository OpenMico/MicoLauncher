package com.scwang.smart.refresh.layout.util;

import android.view.View;
import android.view.ViewGroup;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.scwang.smart.refresh.layout.api.RefreshKernel;
import com.scwang.smart.refresh.layout.listener.CoordinatorLayoutListener;

/* loaded from: classes2.dex */
public class DesignUtil {
    public static void checkCoordinatorLayout(View view, RefreshKernel refreshKernel, final CoordinatorLayoutListener coordinatorLayoutListener) {
        try {
            if (view instanceof CoordinatorLayout) {
                refreshKernel.getRefreshLayout().setEnableNestedScroll(false);
                ViewGroup viewGroup = (ViewGroup) view;
                for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                    View childAt = viewGroup.getChildAt(childCount);
                    if (childAt instanceof AppBarLayout) {
                        ((AppBarLayout) childAt).addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.scwang.smart.refresh.layout.util.DesignUtil.1
                            @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
                            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                                CoordinatorLayoutListener coordinatorLayoutListener2 = CoordinatorLayoutListener.this;
                                boolean z = true;
                                boolean z2 = i >= 0;
                                if (appBarLayout.getTotalScrollRange() + i > 0) {
                                    z = false;
                                }
                                coordinatorLayoutListener2.onCoordinatorUpdate(z2, z);
                            }
                        });
                    }
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
