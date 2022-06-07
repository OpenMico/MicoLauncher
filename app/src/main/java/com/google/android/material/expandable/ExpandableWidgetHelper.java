package com.google.android.material.expandable;

import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

/* loaded from: classes2.dex */
public final class ExpandableWidgetHelper {
    @NonNull
    private final View a;
    private boolean b = false;
    @IdRes
    private int c = 0;

    public ExpandableWidgetHelper(ExpandableWidget expandableWidget) {
        this.a = (View) expandableWidget;
    }

    public boolean setExpanded(boolean z) {
        if (this.b == z) {
            return false;
        }
        this.b = z;
        a();
        return true;
    }

    public boolean isExpanded() {
        return this.b;
    }

    @NonNull
    public Bundle onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("expanded", this.b);
        bundle.putInt("expandedComponentIdHint", this.c);
        return bundle;
    }

    public void onRestoreInstanceState(@NonNull Bundle bundle) {
        this.b = bundle.getBoolean("expanded", false);
        this.c = bundle.getInt("expandedComponentIdHint", 0);
        if (this.b) {
            a();
        }
    }

    public void setExpandedComponentIdHint(@IdRes int i) {
        this.c = i;
    }

    @IdRes
    public int getExpandedComponentIdHint() {
        return this.c;
    }

    private void a() {
        ViewParent parent = this.a.getParent();
        if (parent instanceof CoordinatorLayout) {
            ((CoordinatorLayout) parent).dispatchDependentViewsChanged(this.a);
        }
    }
}
