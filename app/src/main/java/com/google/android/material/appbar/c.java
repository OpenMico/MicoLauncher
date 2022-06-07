package com.google.android.material.appbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

/* compiled from: ViewOffsetBehavior.java */
/* loaded from: classes2.dex */
class c<V extends View> extends CoordinatorLayout.Behavior<V> {
    private d a;
    private int b = 0;
    private int c = 0;

    public c() {
    }

    public c(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onLayoutChild(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, int i) {
        layoutChild(coordinatorLayout, v, i);
        if (this.a == null) {
            this.a = new d(v);
        }
        this.a.a();
        this.a.b();
        int i2 = this.b;
        if (i2 != 0) {
            this.a.a(i2);
            this.b = 0;
        }
        int i3 = this.c;
        if (i3 == 0) {
            return true;
        }
        this.a.b(i3);
        this.c = 0;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void layoutChild(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, int i) {
        coordinatorLayout.onLayoutChild(v, i);
    }

    public boolean setTopAndBottomOffset(int i) {
        d dVar = this.a;
        if (dVar != null) {
            return dVar.a(i);
        }
        this.b = i;
        return false;
    }

    public boolean setLeftAndRightOffset(int i) {
        d dVar = this.a;
        if (dVar != null) {
            return dVar.b(i);
        }
        this.c = i;
        return false;
    }

    public int getTopAndBottomOffset() {
        d dVar = this.a;
        if (dVar != null) {
            return dVar.c();
        }
        return 0;
    }

    public int getLeftAndRightOffset() {
        d dVar = this.a;
        if (dVar != null) {
            return dVar.d();
        }
        return 0;
    }

    public void setVerticalOffsetEnabled(boolean z) {
        d dVar = this.a;
        if (dVar != null) {
            dVar.a(z);
        }
    }

    public boolean isVerticalOffsetEnabled() {
        d dVar = this.a;
        return dVar != null && dVar.f();
    }

    public void setHorizontalOffsetEnabled(boolean z) {
        d dVar = this.a;
        if (dVar != null) {
            dVar.b(z);
        }
    }

    public boolean isHorizontalOffsetEnabled() {
        d dVar = this.a;
        return dVar != null && dVar.g();
    }
}
