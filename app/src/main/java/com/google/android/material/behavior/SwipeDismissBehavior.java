package com.google.android.material.behavior;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.customview.widget.ViewDragHelper;

/* loaded from: classes2.dex */
public class SwipeDismissBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_SETTLING = 2;
    public static final int SWIPE_DIRECTION_ANY = 2;
    public static final int SWIPE_DIRECTION_END_TO_START = 1;
    public static final int SWIPE_DIRECTION_START_TO_END = 0;
    ViewDragHelper a;
    OnDismissListener b;
    private boolean g;
    private boolean i;
    private float h = 0.0f;
    int c = 2;
    float d = 0.5f;
    float e = 0.0f;
    float f = 0.5f;
    private final ViewDragHelper.Callback j = new ViewDragHelper.Callback() { // from class: com.google.android.material.behavior.SwipeDismissBehavior.1
        private int b;
        private int c = -1;

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public boolean tryCaptureView(View view, int i) {
            int i2 = this.c;
            return (i2 == -1 || i2 == i) && SwipeDismissBehavior.this.canSwipeDismissView(view);
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public void onViewCaptured(@NonNull View view, int i) {
            this.c = i;
            this.b = view.getLeft();
            ViewParent parent = view.getParent();
            if (parent != null) {
                parent.requestDisallowInterceptTouchEvent(true);
            }
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public void onViewDragStateChanged(int i) {
            if (SwipeDismissBehavior.this.b != null) {
                SwipeDismissBehavior.this.b.onDragStateChanged(i);
            }
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public void onViewReleased(@NonNull View view, float f, float f2) {
            boolean z;
            int i;
            this.c = -1;
            int width = view.getWidth();
            if (a(view, f)) {
                int left = view.getLeft();
                int i2 = this.b;
                i = left < i2 ? i2 - width : i2 + width;
                z = true;
            } else {
                i = this.b;
                z = false;
            }
            if (SwipeDismissBehavior.this.a.settleCapturedViewAt(i, view.getTop())) {
                ViewCompat.postOnAnimation(view, new a(view, z));
            } else if (z && SwipeDismissBehavior.this.b != null) {
                SwipeDismissBehavior.this.b.onDismiss(view);
            }
        }

        private boolean a(@NonNull View view, float f) {
            int i = (f > 0.0f ? 1 : (f == 0.0f ? 0 : -1));
            if (i != 0) {
                boolean z = ViewCompat.getLayoutDirection(view) == 1;
                if (SwipeDismissBehavior.this.c == 2) {
                    return true;
                }
                if (SwipeDismissBehavior.this.c == 0) {
                    if (z) {
                        if (f >= 0.0f) {
                            return false;
                        }
                    } else if (i <= 0) {
                        return false;
                    }
                    return true;
                } else if (SwipeDismissBehavior.this.c != 1) {
                    return false;
                } else {
                    if (z) {
                        if (i <= 0) {
                            return false;
                        }
                    } else if (f >= 0.0f) {
                        return false;
                    }
                    return true;
                }
            } else {
                return Math.abs(view.getLeft() - this.b) >= Math.round(((float) view.getWidth()) * SwipeDismissBehavior.this.d);
            }
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public int getViewHorizontalDragRange(@NonNull View view) {
            return view.getWidth();
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public int clampViewPositionHorizontal(@NonNull View view, int i, int i2) {
            int i3;
            int i4;
            boolean z = ViewCompat.getLayoutDirection(view) == 1;
            if (SwipeDismissBehavior.this.c == 0) {
                if (z) {
                    i3 = this.b - view.getWidth();
                    i4 = this.b;
                } else {
                    i3 = this.b;
                    i4 = view.getWidth() + i3;
                }
            } else if (SwipeDismissBehavior.this.c != 1) {
                i3 = this.b - view.getWidth();
                i4 = view.getWidth() + this.b;
            } else if (z) {
                i3 = this.b;
                i4 = view.getWidth() + i3;
            } else {
                i3 = this.b - view.getWidth();
                i4 = this.b;
            }
            return SwipeDismissBehavior.a(i3, i, i4);
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public int clampViewPositionVertical(@NonNull View view, int i, int i2) {
            return view.getTop();
        }

        @Override // androidx.customview.widget.ViewDragHelper.Callback
        public void onViewPositionChanged(@NonNull View view, int i, int i2, int i3, int i4) {
            float width = this.b + (view.getWidth() * SwipeDismissBehavior.this.e);
            float width2 = this.b + (view.getWidth() * SwipeDismissBehavior.this.f);
            float f = i;
            if (f <= width) {
                view.setAlpha(1.0f);
            } else if (f >= width2) {
                view.setAlpha(0.0f);
            } else {
                view.setAlpha(SwipeDismissBehavior.a(0.0f, 1.0f - SwipeDismissBehavior.b(width, width2, f), 1.0f));
            }
        }
    };

    /* loaded from: classes2.dex */
    public interface OnDismissListener {
        void onDismiss(View view);

        void onDragStateChanged(int i);
    }

    static float b(float f, float f2, float f3) {
        return (f3 - f) / (f2 - f);
    }

    public boolean canSwipeDismissView(@NonNull View view) {
        return true;
    }

    public void setListener(@Nullable OnDismissListener onDismissListener) {
        this.b = onDismissListener;
    }

    @Nullable
    @VisibleForTesting
    public OnDismissListener getListener() {
        return this.b;
    }

    public void setSwipeDirection(int i) {
        this.c = i;
    }

    public void setDragDismissDistance(float f) {
        this.d = a(0.0f, f, 1.0f);
    }

    public void setStartAlphaSwipeDistance(float f) {
        this.e = a(0.0f, f, 1.0f);
    }

    public void setEndAlphaSwipeDistance(float f) {
        this.f = a(0.0f, f, 1.0f);
    }

    public void setSensitivity(float f) {
        this.h = f;
        this.i = true;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onLayoutChild(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V v, int i) {
        boolean onLayoutChild = super.onLayoutChild(coordinatorLayout, v, i);
        if (ViewCompat.getImportantForAccessibility(v) == 0) {
            ViewCompat.setImportantForAccessibility(v, 1);
            a(v);
        }
        return onLayoutChild;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x002f A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onInterceptTouchEvent(@androidx.annotation.NonNull androidx.coordinatorlayout.widget.CoordinatorLayout r5, @androidx.annotation.NonNull V r6, @androidx.annotation.NonNull android.view.MotionEvent r7) {
        /*
            r4 = this;
            boolean r0 = r4.g
            int r1 = r7.getActionMasked()
            r2 = 3
            r3 = 0
            if (r1 == r2) goto L_0x0021
            switch(r1) {
                case 0: goto L_0x000e;
                case 1: goto L_0x0021;
                default: goto L_0x000d;
            }
        L_0x000d:
            goto L_0x0023
        L_0x000e:
            float r0 = r7.getX()
            int r0 = (int) r0
            float r1 = r7.getY()
            int r1 = (int) r1
            boolean r6 = r5.isPointInChildBounds(r6, r0, r1)
            r4.g = r6
            boolean r0 = r4.g
            goto L_0x0023
        L_0x0021:
            r4.g = r3
        L_0x0023:
            if (r0 == 0) goto L_0x002f
            r4.a(r5)
            androidx.customview.widget.ViewDragHelper r5 = r4.a
            boolean r5 = r5.shouldInterceptTouchEvent(r7)
            return r5
        L_0x002f:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.behavior.SwipeDismissBehavior.onInterceptTouchEvent(androidx.coordinatorlayout.widget.CoordinatorLayout, android.view.View, android.view.MotionEvent):boolean");
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        ViewDragHelper viewDragHelper = this.a;
        if (viewDragHelper == null) {
            return false;
        }
        viewDragHelper.processTouchEvent(motionEvent);
        return true;
    }

    private void a(ViewGroup viewGroup) {
        ViewDragHelper viewDragHelper;
        if (this.a == null) {
            if (this.i) {
                viewDragHelper = ViewDragHelper.create(viewGroup, this.h, this.j);
            } else {
                viewDragHelper = ViewDragHelper.create(viewGroup, this.j);
            }
            this.a = viewDragHelper;
        }
    }

    /* loaded from: classes2.dex */
    private class a implements Runnable {
        private final View b;
        private final boolean c;

        a(View view, boolean z) {
            this.b = view;
            this.c = z;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (SwipeDismissBehavior.this.a != null && SwipeDismissBehavior.this.a.continueSettling(true)) {
                ViewCompat.postOnAnimation(this.b, this);
            } else if (this.c && SwipeDismissBehavior.this.b != null) {
                SwipeDismissBehavior.this.b.onDismiss(this.b);
            }
        }
    }

    private void a(View view) {
        ViewCompat.removeAccessibilityAction(view, 1048576);
        if (canSwipeDismissView(view)) {
            ViewCompat.replaceAccessibilityAction(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_DISMISS, null, new AccessibilityViewCommand() { // from class: com.google.android.material.behavior.SwipeDismissBehavior.2
                @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                public boolean perform(@NonNull View view2, @Nullable AccessibilityViewCommand.CommandArguments commandArguments) {
                    boolean z = false;
                    if (!SwipeDismissBehavior.this.canSwipeDismissView(view2)) {
                        return false;
                    }
                    boolean z2 = ViewCompat.getLayoutDirection(view2) == 1;
                    if ((SwipeDismissBehavior.this.c == 0 && z2) || (SwipeDismissBehavior.this.c == 1 && !z2)) {
                        z = true;
                    }
                    ViewCompat.offsetLeftAndRight(view2, z ? -view2.getWidth() : view2.getWidth());
                    view2.setAlpha(0.0f);
                    if (SwipeDismissBehavior.this.b != null) {
                        SwipeDismissBehavior.this.b.onDismiss(view2);
                    }
                    return true;
                }
            });
        }
    }

    static float a(float f, float f2, float f3) {
        return Math.min(Math.max(f, f2), f3);
    }

    static int a(int i, int i2, int i3) {
        return Math.min(Math.max(i, i2), i3);
    }

    public int getDragState() {
        ViewDragHelper viewDragHelper = this.a;
        if (viewDragHelper != null) {
            return viewDragHelper.getViewDragState();
        }
        return 0;
    }
}
