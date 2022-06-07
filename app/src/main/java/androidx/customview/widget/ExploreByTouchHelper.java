package androidx.customview.widget;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.SparseArrayCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeProviderCompat;
import androidx.core.view.accessibility.AccessibilityRecordCompat;
import androidx.customview.widget.FocusStrategy;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public abstract class ExploreByTouchHelper extends AccessibilityDelegateCompat {
    public static final int HOST_ID = -1;
    public static final int INVALID_ID = Integer.MIN_VALUE;
    private static final Rect c = new Rect(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    private static final FocusStrategy.BoundsAdapter<AccessibilityNodeInfoCompat> l = new FocusStrategy.BoundsAdapter<AccessibilityNodeInfoCompat>() { // from class: androidx.customview.widget.ExploreByTouchHelper.1
        /* renamed from: a */
        public void obtainBounds(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, Rect rect) {
            accessibilityNodeInfoCompat.getBoundsInParent(rect);
        }
    };
    private static final FocusStrategy.CollectionAdapter<SparseArrayCompat<AccessibilityNodeInfoCompat>, AccessibilityNodeInfoCompat> m = new FocusStrategy.CollectionAdapter<SparseArrayCompat<AccessibilityNodeInfoCompat>, AccessibilityNodeInfoCompat>() { // from class: androidx.customview.widget.ExploreByTouchHelper.2
        /* renamed from: a */
        public AccessibilityNodeInfoCompat get(SparseArrayCompat<AccessibilityNodeInfoCompat> sparseArrayCompat, int i) {
            return sparseArrayCompat.valueAt(i);
        }

        /* renamed from: a */
        public int size(SparseArrayCompat<AccessibilityNodeInfoCompat> sparseArrayCompat) {
            return sparseArrayCompat.size();
        }
    };
    private final AccessibilityManager h;
    private final View i;
    private a j;
    private final Rect d = new Rect();
    private final Rect e = new Rect();
    private final Rect f = new Rect();
    private final int[] g = new int[2];
    int a = Integer.MIN_VALUE;
    int b = Integer.MIN_VALUE;
    private int k = Integer.MIN_VALUE;

    private static int b(int i) {
        switch (i) {
            case 19:
                return 33;
            case 20:
            default:
                return 130;
            case 21:
                return 17;
            case 22:
                return 66;
        }
    }

    protected abstract int getVirtualViewAt(float f, float f2);

    protected abstract void getVisibleVirtualViews(List<Integer> list);

    protected abstract boolean onPerformActionForVirtualView(int i, int i2, @Nullable Bundle bundle);

    protected void onPopulateEventForHost(@NonNull AccessibilityEvent accessibilityEvent) {
    }

    protected void onPopulateEventForVirtualView(int i, @NonNull AccessibilityEvent accessibilityEvent) {
    }

    protected void onPopulateNodeForHost(@NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
    }

    protected abstract void onPopulateNodeForVirtualView(int i, @NonNull AccessibilityNodeInfoCompat accessibilityNodeInfoCompat);

    protected void onVirtualViewKeyboardFocusChanged(int i, boolean z) {
    }

    public ExploreByTouchHelper(@NonNull View view) {
        if (view != null) {
            this.i = view;
            this.h = (AccessibilityManager) view.getContext().getSystemService("accessibility");
            view.setFocusable(true);
            if (ViewCompat.getImportantForAccessibility(view) == 0) {
                ViewCompat.setImportantForAccessibility(view, 1);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("View may not be null");
    }

    @Override // androidx.core.view.AccessibilityDelegateCompat
    public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) {
        if (this.j == null) {
            this.j = new a();
        }
        return this.j;
    }

    public final boolean dispatchHoverEvent(@NonNull MotionEvent motionEvent) {
        if (!this.h.isEnabled() || !this.h.isTouchExplorationEnabled()) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action != 7) {
            switch (action) {
                case 9:
                    break;
                case 10:
                    if (this.k == Integer.MIN_VALUE) {
                        return false;
                    }
                    c(Integer.MIN_VALUE);
                    return true;
                default:
                    return false;
            }
        }
        int virtualViewAt = getVirtualViewAt(motionEvent.getX(), motionEvent.getY());
        c(virtualViewAt);
        return virtualViewAt != Integer.MIN_VALUE;
    }

    public final boolean dispatchKeyEvent(@NonNull KeyEvent keyEvent) {
        int i = 0;
        if (keyEvent.getAction() == 1) {
            return false;
        }
        int keyCode = keyEvent.getKeyCode();
        if (keyCode != 61) {
            if (keyCode != 66) {
                switch (keyCode) {
                    case 19:
                    case 20:
                    case 21:
                    case 22:
                        if (!keyEvent.hasNoModifiers()) {
                            return false;
                        }
                        int b = b(keyCode);
                        int repeatCount = keyEvent.getRepeatCount() + 1;
                        boolean z = false;
                        while (i < repeatCount && b(b, (Rect) null)) {
                            i++;
                            z = true;
                        }
                        return z;
                    case 23:
                        break;
                    default:
                        return false;
                }
            }
            if (!keyEvent.hasNoModifiers() || keyEvent.getRepeatCount() != 0) {
                return false;
            }
            c();
            return true;
        } else if (keyEvent.hasNoModifiers()) {
            return b(2, (Rect) null);
        } else {
            if (keyEvent.hasModifiers(1)) {
                return b(1, (Rect) null);
            }
            return false;
        }
    }

    public final void onFocusChanged(boolean z, int i, @Nullable Rect rect) {
        int i2 = this.b;
        if (i2 != Integer.MIN_VALUE) {
            clearKeyboardFocusForVirtualView(i2);
        }
        if (z) {
            b(i, rect);
        }
    }

    public final int getAccessibilityFocusedVirtualViewId() {
        return this.a;
    }

    public final int getKeyboardFocusedVirtualViewId() {
        return this.b;
    }

    private void a(int i, Rect rect) {
        a(i).getBoundsInParent(rect);
    }

    private boolean b(int i, @Nullable Rect rect) {
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat;
        SparseArrayCompat<AccessibilityNodeInfoCompat> b = b();
        int i2 = this.b;
        int i3 = Integer.MIN_VALUE;
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = i2 == Integer.MIN_VALUE ? null : b.get(i2);
        if (i == 17 || i == 33 || i == 66 || i == 130) {
            Rect rect2 = new Rect();
            int i4 = this.b;
            if (i4 != Integer.MIN_VALUE) {
                a(i4, rect2);
            } else if (rect != null) {
                rect2.set(rect);
            } else {
                a(this.i, i, rect2);
            }
            accessibilityNodeInfoCompat = (AccessibilityNodeInfoCompat) FocusStrategy.a(b, m, l, accessibilityNodeInfoCompat2, rect2, i);
        } else {
            switch (i) {
                case 1:
                case 2:
                    accessibilityNodeInfoCompat = (AccessibilityNodeInfoCompat) FocusStrategy.a(b, m, l, accessibilityNodeInfoCompat2, i, ViewCompat.getLayoutDirection(this.i) == 1, false);
                    break;
                default:
                    throw new IllegalArgumentException("direction must be one of {FOCUS_FORWARD, FOCUS_BACKWARD, FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
            }
        }
        if (accessibilityNodeInfoCompat != null) {
            i3 = b.keyAt(b.indexOfValue(accessibilityNodeInfoCompat));
        }
        return requestKeyboardFocusForVirtualView(i3);
    }

    private SparseArrayCompat<AccessibilityNodeInfoCompat> b() {
        ArrayList arrayList = new ArrayList();
        getVisibleVirtualViews(arrayList);
        SparseArrayCompat<AccessibilityNodeInfoCompat> sparseArrayCompat = new SparseArrayCompat<>();
        for (int i = 0; i < arrayList.size(); i++) {
            sparseArrayCompat.put(arrayList.get(i).intValue(), e(arrayList.get(i).intValue()));
        }
        return sparseArrayCompat;
    }

    private static Rect a(@NonNull View view, int i, @NonNull Rect rect) {
        int width = view.getWidth();
        int height = view.getHeight();
        if (i == 17) {
            rect.set(width, 0, width, height);
        } else if (i == 33) {
            rect.set(0, height, width, height);
        } else if (i == 66) {
            rect.set(-1, 0, -1, height);
        } else if (i == 130) {
            rect.set(0, -1, width, -1);
        } else {
            throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
        return rect;
    }

    private boolean c() {
        int i = this.b;
        return i != Integer.MIN_VALUE && onPerformActionForVirtualView(i, 16, null);
    }

    public final boolean sendEventForVirtualView(int i, int i2) {
        ViewParent parent;
        if (i == Integer.MIN_VALUE || !this.h.isEnabled() || (parent = this.i.getParent()) == null) {
            return false;
        }
        return parent.requestSendAccessibilityEvent(this.i, a(i, i2));
    }

    public final void invalidateRoot() {
        invalidateVirtualView(-1, 1);
    }

    public final void invalidateVirtualView(int i) {
        invalidateVirtualView(i, 0);
    }

    public final void invalidateVirtualView(int i, int i2) {
        ViewParent parent;
        if (i != Integer.MIN_VALUE && this.h.isEnabled() && (parent = this.i.getParent()) != null) {
            AccessibilityEvent a2 = a(i, 2048);
            AccessibilityEventCompat.setContentChangeTypes(a2, i2);
            parent.requestSendAccessibilityEvent(this.i, a2);
        }
    }

    @Deprecated
    public int getFocusedVirtualView() {
        return getAccessibilityFocusedVirtualViewId();
    }

    private void c(int i) {
        int i2 = this.k;
        if (i2 != i) {
            this.k = i;
            sendEventForVirtualView(i, 128);
            sendEventForVirtualView(i2, 256);
        }
    }

    private AccessibilityEvent a(int i, int i2) {
        if (i != -1) {
            return b(i, i2);
        }
        return d(i2);
    }

    private AccessibilityEvent d(int i) {
        AccessibilityEvent obtain = AccessibilityEvent.obtain(i);
        this.i.onInitializeAccessibilityEvent(obtain);
        return obtain;
    }

    @Override // androidx.core.view.AccessibilityDelegateCompat
    public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(view, accessibilityEvent);
        onPopulateEventForHost(accessibilityEvent);
    }

    private AccessibilityEvent b(int i, int i2) {
        AccessibilityEvent obtain = AccessibilityEvent.obtain(i2);
        AccessibilityNodeInfoCompat a2 = a(i);
        obtain.getText().add(a2.getText());
        obtain.setContentDescription(a2.getContentDescription());
        obtain.setScrollable(a2.isScrollable());
        obtain.setPassword(a2.isPassword());
        obtain.setEnabled(a2.isEnabled());
        obtain.setChecked(a2.isChecked());
        onPopulateEventForVirtualView(i, obtain);
        if (!obtain.getText().isEmpty() || obtain.getContentDescription() != null) {
            obtain.setClassName(a2.getClassName());
            AccessibilityRecordCompat.setSource(obtain, this.i, i);
            obtain.setPackageName(this.i.getContext().getPackageName());
            return obtain;
        }
        throw new RuntimeException("Callbacks must add text or a content description in populateEventForVirtualViewId()");
    }

    @NonNull
    AccessibilityNodeInfoCompat a(int i) {
        if (i == -1) {
            return d();
        }
        return e(i);
    }

    @NonNull
    private AccessibilityNodeInfoCompat d() {
        AccessibilityNodeInfoCompat obtain = AccessibilityNodeInfoCompat.obtain(this.i);
        ViewCompat.onInitializeAccessibilityNodeInfo(this.i, obtain);
        ArrayList arrayList = new ArrayList();
        getVisibleVirtualViews(arrayList);
        if (obtain.getChildCount() <= 0 || arrayList.size() <= 0) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                obtain.addChild(this.i, ((Integer) arrayList.get(i)).intValue());
            }
            return obtain;
        }
        throw new RuntimeException("Views cannot have both real and virtual children");
    }

    @Override // androidx.core.view.AccessibilityDelegateCompat
    public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
        onPopulateNodeForHost(accessibilityNodeInfoCompat);
    }

    @NonNull
    private AccessibilityNodeInfoCompat e(int i) {
        AccessibilityNodeInfoCompat obtain = AccessibilityNodeInfoCompat.obtain();
        obtain.setEnabled(true);
        obtain.setFocusable(true);
        obtain.setClassName("android.view.View");
        obtain.setBoundsInParent(c);
        obtain.setBoundsInScreen(c);
        obtain.setParent(this.i);
        onPopulateNodeForVirtualView(i, obtain);
        if (obtain.getText() == null && obtain.getContentDescription() == null) {
            throw new RuntimeException("Callbacks must add text or a content description in populateNodeForVirtualViewId()");
        }
        obtain.getBoundsInParent(this.e);
        if (!this.e.equals(c)) {
            int actions = obtain.getActions();
            if ((actions & 64) != 0) {
                throw new RuntimeException("Callbacks must not add ACTION_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
            } else if ((actions & 128) == 0) {
                obtain.setPackageName(this.i.getContext().getPackageName());
                obtain.setSource(this.i, i);
                if (this.a == i) {
                    obtain.setAccessibilityFocused(true);
                    obtain.addAction(128);
                } else {
                    obtain.setAccessibilityFocused(false);
                    obtain.addAction(64);
                }
                boolean z = this.b == i;
                if (z) {
                    obtain.addAction(2);
                } else if (obtain.isFocusable()) {
                    obtain.addAction(1);
                }
                obtain.setFocused(z);
                this.i.getLocationOnScreen(this.g);
                obtain.getBoundsInScreen(this.d);
                if (this.d.equals(c)) {
                    obtain.getBoundsInParent(this.d);
                    if (obtain.mParentVirtualDescendantId != -1) {
                        AccessibilityNodeInfoCompat obtain2 = AccessibilityNodeInfoCompat.obtain();
                        for (int i2 = obtain.mParentVirtualDescendantId; i2 != -1; i2 = obtain2.mParentVirtualDescendantId) {
                            obtain2.setParent(this.i, -1);
                            obtain2.setBoundsInParent(c);
                            onPopulateNodeForVirtualView(i2, obtain2);
                            obtain2.getBoundsInParent(this.e);
                            this.d.offset(this.e.left, this.e.top);
                        }
                        obtain2.recycle();
                    }
                    this.d.offset(this.g[0] - this.i.getScrollX(), this.g[1] - this.i.getScrollY());
                }
                if (this.i.getLocalVisibleRect(this.f)) {
                    this.f.offset(this.g[0] - this.i.getScrollX(), this.g[1] - this.i.getScrollY());
                    if (this.d.intersect(this.f)) {
                        obtain.setBoundsInScreen(this.d);
                        if (a(this.d)) {
                            obtain.setVisibleToUser(true);
                        }
                    }
                }
                return obtain;
            } else {
                throw new RuntimeException("Callbacks must not add ACTION_CLEAR_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
            }
        } else {
            throw new RuntimeException("Callbacks must set parent bounds in populateNodeForVirtualViewId()");
        }
    }

    boolean a(int i, int i2, Bundle bundle) {
        if (i != -1) {
            return b(i, i2, bundle);
        }
        return a(i2, bundle);
    }

    private boolean a(int i, Bundle bundle) {
        return ViewCompat.performAccessibilityAction(this.i, i, bundle);
    }

    private boolean b(int i, int i2, Bundle bundle) {
        if (i2 == 64) {
            return f(i);
        }
        if (i2 == 128) {
            return g(i);
        }
        switch (i2) {
            case 1:
                return requestKeyboardFocusForVirtualView(i);
            case 2:
                return clearKeyboardFocusForVirtualView(i);
            default:
                return onPerformActionForVirtualView(i, i2, bundle);
        }
    }

    private boolean a(Rect rect) {
        if (rect == null || rect.isEmpty() || this.i.getWindowVisibility() != 0) {
            return false;
        }
        ViewParent parent = this.i.getParent();
        while (parent instanceof View) {
            View view = (View) parent;
            if (view.getAlpha() <= 0.0f || view.getVisibility() != 0) {
                return false;
            }
            parent = view.getParent();
        }
        return parent != null;
    }

    private boolean f(int i) {
        int i2;
        if (!this.h.isEnabled() || !this.h.isTouchExplorationEnabled() || (i2 = this.a) == i) {
            return false;
        }
        if (i2 != Integer.MIN_VALUE) {
            g(i2);
        }
        this.a = i;
        this.i.invalidate();
        sendEventForVirtualView(i, 32768);
        return true;
    }

    private boolean g(int i) {
        if (this.a != i) {
            return false;
        }
        this.a = Integer.MIN_VALUE;
        this.i.invalidate();
        sendEventForVirtualView(i, 65536);
        return true;
    }

    public final boolean requestKeyboardFocusForVirtualView(int i) {
        int i2;
        if ((!this.i.isFocused() && !this.i.requestFocus()) || (i2 = this.b) == i) {
            return false;
        }
        if (i2 != Integer.MIN_VALUE) {
            clearKeyboardFocusForVirtualView(i2);
        }
        if (i == Integer.MIN_VALUE) {
            return false;
        }
        this.b = i;
        onVirtualViewKeyboardFocusChanged(i, true);
        sendEventForVirtualView(i, 8);
        return true;
    }

    public final boolean clearKeyboardFocusForVirtualView(int i) {
        if (this.b != i) {
            return false;
        }
        this.b = Integer.MIN_VALUE;
        onVirtualViewKeyboardFocusChanged(i, false);
        sendEventForVirtualView(i, 8);
        return true;
    }

    /* loaded from: classes.dex */
    private class a extends AccessibilityNodeProviderCompat {
        a() {
        }

        @Override // androidx.core.view.accessibility.AccessibilityNodeProviderCompat
        public AccessibilityNodeInfoCompat createAccessibilityNodeInfo(int i) {
            return AccessibilityNodeInfoCompat.obtain(ExploreByTouchHelper.this.a(i));
        }

        @Override // androidx.core.view.accessibility.AccessibilityNodeProviderCompat
        public boolean performAction(int i, int i2, Bundle bundle) {
            return ExploreByTouchHelper.this.a(i, i2, bundle);
        }

        @Override // androidx.core.view.accessibility.AccessibilityNodeProviderCompat
        public AccessibilityNodeInfoCompat findFocus(int i) {
            int i2 = i == 2 ? ExploreByTouchHelper.this.a : ExploreByTouchHelper.this.b;
            if (i2 == Integer.MIN_VALUE) {
                return null;
            }
            return createAccessibilityNodeInfo(i2);
        }
    }
}
