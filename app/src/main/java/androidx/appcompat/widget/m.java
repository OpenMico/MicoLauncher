package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.R;
import androidx.appcompat.graphics.drawable.DrawableWrapper;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.widget.ListViewAutoScrollHelper;
import java.lang.reflect.Field;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: DropDownListView.java */
/* loaded from: classes.dex */
public class m extends ListView {
    public static final int INVALID_POSITION = -1;
    public static final int NO_POSITION = -1;
    b a;
    private final Rect b = new Rect();
    private int c = 0;
    private int d = 0;
    private int e = 0;
    private int f = 0;
    private int g;
    private Field h;
    private a i;
    private boolean j;
    private boolean k;
    private boolean l;
    private ViewPropertyAnimatorCompat m;
    private ListViewAutoScrollHelper n;

    /* JADX INFO: Access modifiers changed from: package-private */
    public m(@NonNull Context context, boolean z) {
        super(context, null, R.attr.dropDownListViewStyle);
        this.k = z;
        setCacheColorHint(0);
        try {
            this.h = AbsListView.class.getDeclaredField("mIsChildViewEnabled");
            this.h.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override // android.view.View
    public boolean isInTouchMode() {
        return (this.k && this.j) || super.isInTouchMode();
    }

    @Override // android.view.View
    public boolean hasWindowFocus() {
        return this.k || super.hasWindowFocus();
    }

    @Override // android.view.View
    public boolean isFocused() {
        return this.k || super.isFocused();
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean hasFocus() {
        return this.k || super.hasFocus();
    }

    @Override // android.widget.AbsListView
    public void setSelector(Drawable drawable) {
        this.i = drawable != null ? new a(drawable) : null;
        super.setSelector(this.i);
        Rect rect = new Rect();
        if (drawable != null) {
            drawable.getPadding(rect);
        }
        this.c = rect.left;
        this.d = rect.top;
        this.e = rect.right;
        this.f = rect.bottom;
    }

    @Override // android.widget.AbsListView, android.view.ViewGroup, android.view.View
    protected void drawableStateChanged() {
        if (this.a == null) {
            super.drawableStateChanged();
            setSelectorEnabled(true);
            a();
        }
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        a(canvas);
        super.dispatchDraw(canvas);
    }

    @Override // android.widget.AbsListView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            this.g = pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY());
        }
        b bVar = this.a;
        if (bVar != null) {
            bVar.a();
        }
        return super.onTouchEvent(motionEvent);
    }

    public int lookForSelectablePosition(int i, boolean z) {
        int i2;
        ListAdapter adapter = getAdapter();
        if (adapter == null || isInTouchMode()) {
            return -1;
        }
        int count = adapter.getCount();
        if (!getAdapter().areAllItemsEnabled()) {
            if (z) {
                i2 = Math.max(0, i);
                while (i2 < count && !adapter.isEnabled(i2)) {
                    i2++;
                }
            } else {
                i2 = Math.min(i, count - 1);
                while (i2 >= 0 && !adapter.isEnabled(i2)) {
                    i2--;
                }
            }
            if (i2 < 0 || i2 >= count) {
                return -1;
            }
            return i2;
        } else if (i < 0 || i >= count) {
            return -1;
        } else {
            return i;
        }
    }

    public int measureHeightOfChildrenCompat(int i, int i2, int i3, int i4, int i5) {
        int i6;
        int listPaddingTop = getListPaddingTop();
        int listPaddingBottom = getListPaddingBottom();
        int dividerHeight = getDividerHeight();
        Drawable divider = getDivider();
        ListAdapter adapter = getAdapter();
        if (adapter == null) {
            return listPaddingTop + listPaddingBottom;
        }
        int i7 = listPaddingTop + listPaddingBottom;
        if (dividerHeight <= 0 || divider == null) {
            dividerHeight = 0;
        }
        int count = adapter.getCount();
        int i8 = 0;
        int i9 = 0;
        int i10 = 0;
        View view = null;
        while (i8 < count) {
            int itemViewType = adapter.getItemViewType(i8);
            if (itemViewType != i9) {
                view = null;
                i9 = itemViewType;
            }
            view = adapter.getView(i8, view, this);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = generateDefaultLayoutParams();
                view.setLayoutParams(layoutParams);
            }
            if (layoutParams.height > 0) {
                i6 = View.MeasureSpec.makeMeasureSpec(layoutParams.height, 1073741824);
            } else {
                i6 = View.MeasureSpec.makeMeasureSpec(0, 0);
            }
            view.measure(i, i6);
            view.forceLayout();
            if (i8 > 0) {
                i7 += dividerHeight;
            }
            i7 += view.getMeasuredHeight();
            if (i7 >= i4) {
                return (i5 < 0 || i8 <= i5 || i10 <= 0 || i7 == i4) ? i4 : i10;
            }
            if (i5 >= 0 && i8 >= i5) {
                i10 = i7;
            }
            i8++;
        }
        return i7;
    }

    private void setSelectorEnabled(boolean z) {
        a aVar = this.i;
        if (aVar != null) {
            aVar.a(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: DropDownListView.java */
    /* loaded from: classes.dex */
    public static class a extends DrawableWrapper {
        private boolean a = true;

        a(Drawable drawable) {
            super(drawable);
        }

        void a(boolean z) {
            this.a = z;
        }

        @Override // androidx.appcompat.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public boolean setState(int[] iArr) {
            if (this.a) {
                return super.setState(iArr);
            }
            return false;
        }

        @Override // androidx.appcompat.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            if (this.a) {
                super.draw(canvas);
            }
        }

        @Override // androidx.appcompat.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public void setHotspot(float f, float f2) {
            if (this.a) {
                super.setHotspot(f, f2);
            }
        }

        @Override // androidx.appcompat.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public void setHotspotBounds(int i, int i2, int i3, int i4) {
            if (this.a) {
                super.setHotspotBounds(i, i2, i3, i4);
            }
        }

        @Override // androidx.appcompat.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public boolean setVisible(boolean z, boolean z2) {
            if (this.a) {
                return super.setVisible(z, z2);
            }
            return false;
        }
    }

    @Override // android.view.View
    public boolean onHoverEvent(@NonNull MotionEvent motionEvent) {
        if (Build.VERSION.SDK_INT < 26) {
            return super.onHoverEvent(motionEvent);
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 10 && this.a == null) {
            this.a = new b();
            this.a.b();
        }
        boolean onHoverEvent = super.onHoverEvent(motionEvent);
        if (actionMasked == 9 || actionMasked == 7) {
            int pointToPosition = pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY());
            if (!(pointToPosition == -1 || pointToPosition == getSelectedItemPosition())) {
                View childAt = getChildAt(pointToPosition - getFirstVisiblePosition());
                if (childAt.isEnabled()) {
                    setSelectionFromTop(pointToPosition, childAt.getTop() - getTop());
                }
                a();
            }
        } else {
            setSelection(-1);
        }
        return onHoverEvent;
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        this.a = null;
        super.onDetachedFromWindow();
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0018  */
    /* JADX WARN: Removed duplicated region for block: B:11:0x001b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onForwardedEvent(android.view.MotionEvent r8, int r9) {
        /*
            r7 = this;
            int r0 = r8.getActionMasked()
            r1 = 0
            r2 = 1
            switch(r0) {
                case 1: goto L_0x0011;
                case 2: goto L_0x000f;
                case 3: goto L_0x000c;
                default: goto L_0x0009;
            }
        L_0x0009:
            r9 = r1
            r3 = r2
            goto L_0x0044
        L_0x000c:
            r9 = r1
            r3 = r9
            goto L_0x0044
        L_0x000f:
            r3 = r2
            goto L_0x0012
        L_0x0011:
            r3 = r1
        L_0x0012:
            int r9 = r8.findPointerIndex(r9)
            if (r9 >= 0) goto L_0x001b
            r9 = r1
            r3 = r9
            goto L_0x0044
        L_0x001b:
            float r4 = r8.getX(r9)
            int r4 = (int) r4
            float r9 = r8.getY(r9)
            int r9 = (int) r9
            int r5 = r7.pointToPosition(r4, r9)
            r6 = -1
            if (r5 != r6) goto L_0x002e
            r9 = r2
            goto L_0x0044
        L_0x002e:
            int r3 = r7.getFirstVisiblePosition()
            int r3 = r5 - r3
            android.view.View r3 = r7.getChildAt(r3)
            float r4 = (float) r4
            float r9 = (float) r9
            r7.a(r3, r5, r4, r9)
            if (r0 != r2) goto L_0x0042
            r7.a(r3, r5)
        L_0x0042:
            r9 = r1
            r3 = r2
        L_0x0044:
            if (r3 == 0) goto L_0x0048
            if (r9 == 0) goto L_0x004b
        L_0x0048:
            r7.b()
        L_0x004b:
            if (r3 == 0) goto L_0x0063
            androidx.core.widget.ListViewAutoScrollHelper r9 = r7.n
            if (r9 != 0) goto L_0x0058
            androidx.core.widget.ListViewAutoScrollHelper r9 = new androidx.core.widget.ListViewAutoScrollHelper
            r9.<init>(r7)
            r7.n = r9
        L_0x0058:
            androidx.core.widget.ListViewAutoScrollHelper r9 = r7.n
            r9.setEnabled(r2)
            androidx.core.widget.ListViewAutoScrollHelper r9 = r7.n
            r9.onTouch(r7, r8)
            goto L_0x006a
        L_0x0063:
            androidx.core.widget.ListViewAutoScrollHelper r8 = r7.n
            if (r8 == 0) goto L_0x006a
            r8.setEnabled(r1)
        L_0x006a:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.m.onForwardedEvent(android.view.MotionEvent, int):boolean");
    }

    private void a(View view, int i) {
        performItemClick(view, i, getItemIdAtPosition(i));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setListSelectionHidden(boolean z) {
        this.j = z;
    }

    private void a() {
        Drawable selector = getSelector();
        if (selector != null && c() && isPressed()) {
            selector.setState(getDrawableState());
        }
    }

    private void a(Canvas canvas) {
        Drawable selector;
        if (!this.b.isEmpty() && (selector = getSelector()) != null) {
            selector.setBounds(this.b);
            selector.draw(canvas);
        }
    }

    private void a(int i, View view, float f, float f2) {
        a(i, view);
        Drawable selector = getSelector();
        if (selector != null && i != -1) {
            DrawableCompat.setHotspot(selector, f, f2);
        }
    }

    private void a(int i, View view) {
        Drawable selector = getSelector();
        boolean z = true;
        boolean z2 = (selector == null || i == -1) ? false : true;
        if (z2) {
            selector.setVisible(false, false);
        }
        b(i, view);
        if (z2) {
            Rect rect = this.b;
            float exactCenterX = rect.exactCenterX();
            float exactCenterY = rect.exactCenterY();
            if (getVisibility() != 0) {
                z = false;
            }
            selector.setVisible(z, false);
            DrawableCompat.setHotspot(selector, exactCenterX, exactCenterY);
        }
    }

    private void b(int i, View view) {
        Rect rect = this.b;
        rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        rect.left -= this.c;
        rect.top -= this.d;
        rect.right += this.e;
        rect.bottom += this.f;
        try {
            boolean z = this.h.getBoolean(this);
            if (view.isEnabled() != z) {
                this.h.set(this, Boolean.valueOf(!z));
                if (i != -1) {
                    refreshDrawableState();
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void b() {
        this.l = false;
        setPressed(false);
        drawableStateChanged();
        View childAt = getChildAt(this.g - getFirstVisiblePosition());
        if (childAt != null) {
            childAt.setPressed(false);
        }
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = this.m;
        if (viewPropertyAnimatorCompat != null) {
            viewPropertyAnimatorCompat.cancel();
            this.m = null;
        }
    }

    private void a(View view, int i, float f, float f2) {
        View childAt;
        this.l = true;
        if (Build.VERSION.SDK_INT >= 21) {
            drawableHotspotChanged(f, f2);
        }
        if (!isPressed()) {
            setPressed(true);
        }
        layoutChildren();
        int i2 = this.g;
        if (!(i2 == -1 || (childAt = getChildAt(i2 - getFirstVisiblePosition())) == null || childAt == view || !childAt.isPressed())) {
            childAt.setPressed(false);
        }
        this.g = i;
        float left = f - view.getLeft();
        float top = f2 - view.getTop();
        if (Build.VERSION.SDK_INT >= 21) {
            view.drawableHotspotChanged(left, top);
        }
        if (!view.isPressed()) {
            view.setPressed(true);
        }
        a(i, view, f, f2);
        setSelectorEnabled(false);
        refreshDrawableState();
    }

    private boolean c() {
        return this.l;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: DropDownListView.java */
    /* loaded from: classes.dex */
    public class b implements Runnable {
        b() {
        }

        @Override // java.lang.Runnable
        public void run() {
            m mVar = m.this;
            mVar.a = null;
            mVar.drawableStateChanged();
        }

        public void a() {
            m mVar = m.this;
            mVar.a = null;
            mVar.removeCallbacks(this);
        }

        public void b() {
            m.this.post(this);
        }
    }
}
