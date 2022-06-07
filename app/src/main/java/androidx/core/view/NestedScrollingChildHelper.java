package androidx.core.view;

import android.view.View;
import android.view.ViewParent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes.dex */
public class NestedScrollingChildHelper {
    private ViewParent a;
    private ViewParent b;
    private final View c;
    private boolean d;
    private int[] e;

    public NestedScrollingChildHelper(@NonNull View view) {
        this.c = view;
    }

    public void setNestedScrollingEnabled(boolean z) {
        if (this.d) {
            ViewCompat.stopNestedScroll(this.c);
        }
        this.d = z;
    }

    public boolean isNestedScrollingEnabled() {
        return this.d;
    }

    public boolean hasNestedScrollingParent() {
        return hasNestedScrollingParent(0);
    }

    public boolean hasNestedScrollingParent(int i) {
        return a(i) != null;
    }

    public boolean startNestedScroll(int i) {
        return startNestedScroll(i, 0);
    }

    public boolean startNestedScroll(int i, int i2) {
        if (hasNestedScrollingParent(i2)) {
            return true;
        }
        if (!isNestedScrollingEnabled()) {
            return false;
        }
        View view = this.c;
        for (ViewParent parent = this.c.getParent(); parent != null; parent = parent.getParent()) {
            if (ViewParentCompat.onStartNestedScroll(parent, view, this.c, i, i2)) {
                a(i2, parent);
                ViewParentCompat.onNestedScrollAccepted(parent, view, this.c, i, i2);
                return true;
            }
            if (parent instanceof View) {
                view = (View) parent;
            }
        }
        return false;
    }

    public void stopNestedScroll() {
        stopNestedScroll(0);
    }

    public void stopNestedScroll(int i) {
        ViewParent a = a(i);
        if (a != null) {
            ViewParentCompat.onStopNestedScroll(a, this.c, i);
            a(i, null);
        }
    }

    public boolean dispatchNestedScroll(int i, int i2, int i3, int i4, @Nullable int[] iArr) {
        return a(i, i2, i3, i4, iArr, 0, null);
    }

    public boolean dispatchNestedScroll(int i, int i2, int i3, int i4, @Nullable int[] iArr, int i5) {
        return a(i, i2, i3, i4, iArr, i5, null);
    }

    public void dispatchNestedScroll(int i, int i2, int i3, int i4, @Nullable int[] iArr, int i5, @Nullable int[] iArr2) {
        a(i, i2, i3, i4, iArr, i5, iArr2);
    }

    private boolean a(int i, int i2, int i3, int i4, @Nullable int[] iArr, int i5, @Nullable int[] iArr2) {
        ViewParent a;
        int i6;
        int i7;
        int[] iArr3;
        if (!isNestedScrollingEnabled() || (a = a(i5)) == null) {
            return false;
        }
        if (i == 0 && i2 == 0 && i3 == 0 && i4 == 0) {
            if (iArr != null) {
                iArr[0] = 0;
                iArr[1] = 0;
            }
            return false;
        }
        if (iArr != null) {
            this.c.getLocationInWindow(iArr);
            i7 = iArr[0];
            i6 = iArr[1];
        } else {
            i7 = 0;
            i6 = 0;
        }
        if (iArr2 == null) {
            int[] a2 = a();
            a2[0] = 0;
            a2[1] = 0;
            iArr3 = a2;
        } else {
            iArr3 = iArr2;
        }
        ViewParentCompat.onNestedScroll(a, this.c, i, i2, i3, i4, i5, iArr3);
        if (iArr != null) {
            this.c.getLocationInWindow(iArr);
            iArr[0] = iArr[0] - i7;
            iArr[1] = iArr[1] - i6;
        }
        return true;
    }

    public boolean dispatchNestedPreScroll(int i, int i2, @Nullable int[] iArr, @Nullable int[] iArr2) {
        return dispatchNestedPreScroll(i, i2, iArr, iArr2, 0);
    }

    public boolean dispatchNestedPreScroll(int i, int i2, @Nullable int[] iArr, @Nullable int[] iArr2, int i3) {
        ViewParent a;
        int i4;
        int i5;
        if (!isNestedScrollingEnabled() || (a = a(i3)) == null) {
            return false;
        }
        if (i == 0 && i2 == 0) {
            if (iArr2 != null) {
                iArr2[0] = 0;
                iArr2[1] = 0;
            }
            return false;
        }
        if (iArr2 != null) {
            this.c.getLocationInWindow(iArr2);
            i5 = iArr2[0];
            i4 = iArr2[1];
        } else {
            i5 = 0;
            i4 = 0;
        }
        if (iArr == null) {
            iArr = a();
        }
        iArr[0] = 0;
        iArr[1] = 0;
        ViewParentCompat.onNestedPreScroll(a, this.c, i, i2, iArr, i3);
        if (iArr2 != null) {
            this.c.getLocationInWindow(iArr2);
            iArr2[0] = iArr2[0] - i5;
            iArr2[1] = iArr2[1] - i4;
        }
        return (iArr[0] == 0 && iArr[1] == 0) ? false : true;
    }

    public boolean dispatchNestedFling(float f, float f2, boolean z) {
        ViewParent a;
        if (!isNestedScrollingEnabled() || (a = a(0)) == null) {
            return false;
        }
        return ViewParentCompat.onNestedFling(a, this.c, f, f2, z);
    }

    public boolean dispatchNestedPreFling(float f, float f2) {
        ViewParent a;
        if (!isNestedScrollingEnabled() || (a = a(0)) == null) {
            return false;
        }
        return ViewParentCompat.onNestedPreFling(a, this.c, f, f2);
    }

    public void onDetachedFromWindow() {
        ViewCompat.stopNestedScroll(this.c);
    }

    public void onStopNestedScroll(@NonNull View view) {
        ViewCompat.stopNestedScroll(this.c);
    }

    private ViewParent a(int i) {
        switch (i) {
            case 0:
                return this.a;
            case 1:
                return this.b;
            default:
                return null;
        }
    }

    private void a(int i, ViewParent viewParent) {
        switch (i) {
            case 0:
                this.a = viewParent;
                return;
            case 1:
                this.b = viewParent;
                return;
            default:
                return;
        }
    }

    private int[] a() {
        if (this.e == null) {
            this.e = new int[2];
        }
        return this.e;
    }
}
