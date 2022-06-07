package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.appcompat.R;
import androidx.appcompat.view.menu.ShowableListMenu;
import androidx.core.view.ViewCompat;
import androidx.core.widget.PopupWindowCompat;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class ListPopupWindow implements ShowableListMenu {
    public static final int INPUT_METHOD_FROM_FOCUSABLE = 0;
    public static final int INPUT_METHOD_NEEDED = 1;
    public static final int INPUT_METHOD_NOT_NEEDED = 2;
    public static final int MATCH_PARENT = -1;
    public static final int POSITION_PROMPT_ABOVE = 0;
    public static final int POSITION_PROMPT_BELOW = 1;
    public static final int WRAP_CONTENT = -2;
    private static Method a;
    private static Method b;
    private static Method h;
    private AdapterView.OnItemClickListener A;
    private AdapterView.OnItemSelectedListener B;
    private final d C;
    private final c D;
    private final a E;
    private Runnable F;
    private final Rect G;
    private Rect H;
    private boolean I;
    m c;
    int d;
    final e e;
    final Handler f;
    PopupWindow g;
    private Context i;
    private ListAdapter j;
    private int k;
    private int l;
    private int m;
    private int n;
    private int o;
    private boolean p;
    private boolean q;
    private boolean r;
    private int s;
    private boolean t;
    private boolean u;
    private View v;
    private int w;
    private DataSetObserver x;
    private View y;
    private Drawable z;

    private static boolean a(int i) {
        return i == 66 || i == 23;
    }

    static {
        if (Build.VERSION.SDK_INT <= 28) {
            try {
                a = PopupWindow.class.getDeclaredMethod("setClipToScreenEnabled", Boolean.TYPE);
            } catch (NoSuchMethodException unused) {
                Log.i("ListPopupWindow", "Could not find method setClipToScreenEnabled() on PopupWindow. Oh well.");
            }
            try {
                h = PopupWindow.class.getDeclaredMethod("setEpicenterBounds", Rect.class);
            } catch (NoSuchMethodException unused2) {
                Log.i("ListPopupWindow", "Could not find method setEpicenterBounds(Rect) on PopupWindow. Oh well.");
            }
        }
        if (Build.VERSION.SDK_INT <= 23) {
            try {
                b = PopupWindow.class.getDeclaredMethod("getMaxAvailableHeight", View.class, Integer.TYPE, Boolean.TYPE);
            } catch (NoSuchMethodException unused3) {
                Log.i("ListPopupWindow", "Could not find method getMaxAvailableHeight(View, int, boolean) on PopupWindow. Oh well.");
            }
        }
    }

    public ListPopupWindow(@NonNull Context context) {
        this(context, null, R.attr.listPopupWindowStyle);
    }

    public ListPopupWindow(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.listPopupWindowStyle);
    }

    public ListPopupWindow(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int i) {
        this(context, attributeSet, i, 0);
    }

    public ListPopupWindow(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int i, @StyleRes int i2) {
        this.k = -2;
        this.l = -2;
        this.o = 1002;
        this.s = 0;
        this.t = false;
        this.u = false;
        this.d = Integer.MAX_VALUE;
        this.w = 0;
        this.e = new e();
        this.C = new d();
        this.D = new c();
        this.E = new a();
        this.G = new Rect();
        this.i = context;
        this.f = new Handler(context.getMainLooper());
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ListPopupWindow, i, i2);
        this.m = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.ListPopupWindow_android_dropDownHorizontalOffset, 0);
        this.n = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.ListPopupWindow_android_dropDownVerticalOffset, 0);
        if (this.n != 0) {
            this.p = true;
        }
        obtainStyledAttributes.recycle();
        this.g = new f(context, attributeSet, i, i2);
        this.g.setInputMethodMode(1);
    }

    public void setAdapter(@Nullable ListAdapter listAdapter) {
        DataSetObserver dataSetObserver = this.x;
        if (dataSetObserver == null) {
            this.x = new b();
        } else {
            ListAdapter listAdapter2 = this.j;
            if (listAdapter2 != null) {
                listAdapter2.unregisterDataSetObserver(dataSetObserver);
            }
        }
        this.j = listAdapter;
        if (listAdapter != null) {
            listAdapter.registerDataSetObserver(this.x);
        }
        m mVar = this.c;
        if (mVar != null) {
            mVar.setAdapter(this.j);
        }
    }

    public void setPromptPosition(int i) {
        this.w = i;
    }

    public int getPromptPosition() {
        return this.w;
    }

    public void setModal(boolean z) {
        this.I = z;
        this.g.setFocusable(z);
    }

    public boolean isModal() {
        return this.I;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setForceIgnoreOutsideTouch(boolean z) {
        this.u = z;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setDropDownAlwaysVisible(boolean z) {
        this.t = z;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public boolean isDropDownAlwaysVisible() {
        return this.t;
    }

    public void setSoftInputMode(int i) {
        this.g.setSoftInputMode(i);
    }

    public int getSoftInputMode() {
        return this.g.getSoftInputMode();
    }

    public void setListSelector(Drawable drawable) {
        this.z = drawable;
    }

    @Nullable
    public Drawable getBackground() {
        return this.g.getBackground();
    }

    public void setBackgroundDrawable(@Nullable Drawable drawable) {
        this.g.setBackgroundDrawable(drawable);
    }

    public void setAnimationStyle(@StyleRes int i) {
        this.g.setAnimationStyle(i);
    }

    @StyleRes
    public int getAnimationStyle() {
        return this.g.getAnimationStyle();
    }

    @Nullable
    public View getAnchorView() {
        return this.y;
    }

    public void setAnchorView(@Nullable View view) {
        this.y = view;
    }

    public int getHorizontalOffset() {
        return this.m;
    }

    public void setHorizontalOffset(int i) {
        this.m = i;
    }

    public int getVerticalOffset() {
        if (!this.p) {
            return 0;
        }
        return this.n;
    }

    public void setVerticalOffset(int i) {
        this.n = i;
        this.p = true;
    }

    public void setEpicenterBounds(@Nullable Rect rect) {
        this.H = rect != null ? new Rect(rect) : null;
    }

    @Nullable
    public Rect getEpicenterBounds() {
        Rect rect = this.H;
        if (rect != null) {
            return new Rect(rect);
        }
        return null;
    }

    public void setDropDownGravity(int i) {
        this.s = i;
    }

    public int getWidth() {
        return this.l;
    }

    public void setWidth(int i) {
        this.l = i;
    }

    public void setContentWidth(int i) {
        Drawable background = this.g.getBackground();
        if (background != null) {
            background.getPadding(this.G);
            this.l = this.G.left + this.G.right + i;
            return;
        }
        setWidth(i);
    }

    public int getHeight() {
        return this.k;
    }

    public void setHeight(int i) {
        if (i >= 0 || -2 == i || -1 == i) {
            this.k = i;
            return;
        }
        throw new IllegalArgumentException("Invalid height. Must be a positive value, MATCH_PARENT, or WRAP_CONTENT.");
    }

    public void setWindowLayoutType(int i) {
        this.o = i;
    }

    public void setOnItemClickListener(@Nullable AdapterView.OnItemClickListener onItemClickListener) {
        this.A = onItemClickListener;
    }

    public void setOnItemSelectedListener(@Nullable AdapterView.OnItemSelectedListener onItemSelectedListener) {
        this.B = onItemSelectedListener;
    }

    public void setPromptView(@Nullable View view) {
        boolean isShowing = isShowing();
        if (isShowing) {
            a();
        }
        this.v = view;
        if (isShowing) {
            show();
        }
    }

    public void postShow() {
        this.f.post(this.F);
    }

    @Override // androidx.appcompat.view.menu.ShowableListMenu
    public void show() {
        int b2 = b();
        boolean isInputMethodNotNeeded = isInputMethodNotNeeded();
        PopupWindowCompat.setWindowLayoutType(this.g, this.o);
        boolean z = true;
        if (!this.g.isShowing()) {
            int i = this.l;
            if (i == -1) {
                i = -1;
            } else if (i == -2) {
                i = getAnchorView().getWidth();
            }
            int i2 = this.k;
            if (i2 == -1) {
                b2 = -1;
            } else if (i2 != -2) {
                b2 = i2;
            }
            this.g.setWidth(i);
            this.g.setHeight(b2);
            a(true);
            this.g.setOutsideTouchable(!this.u && !this.t);
            this.g.setTouchInterceptor(this.C);
            if (this.r) {
                PopupWindowCompat.setOverlapAnchor(this.g, this.q);
            }
            if (Build.VERSION.SDK_INT <= 28) {
                Method method = h;
                if (method != null) {
                    try {
                        method.invoke(this.g, this.H);
                    } catch (Exception e2) {
                        Log.e("ListPopupWindow", "Could not invoke setEpicenterBounds on PopupWindow", e2);
                    }
                }
            } else {
                this.g.setEpicenterBounds(this.H);
            }
            PopupWindowCompat.showAsDropDown(this.g, getAnchorView(), this.m, this.n, this.s);
            this.c.setSelection(-1);
            if (!this.I || this.c.isInTouchMode()) {
                clearListSelection();
            }
            if (!this.I) {
                this.f.post(this.E);
            }
        } else if (ViewCompat.isAttachedToWindow(getAnchorView())) {
            int i3 = this.l;
            if (i3 == -1) {
                i3 = -1;
            } else if (i3 == -2) {
                i3 = getAnchorView().getWidth();
            }
            int i4 = this.k;
            if (i4 == -1) {
                if (!isInputMethodNotNeeded) {
                    b2 = -1;
                }
                if (isInputMethodNotNeeded) {
                    this.g.setWidth(this.l == -1 ? -1 : 0);
                    this.g.setHeight(0);
                } else {
                    this.g.setWidth(this.l == -1 ? -1 : 0);
                    this.g.setHeight(-1);
                }
            } else if (i4 != -2) {
                b2 = i4;
            }
            PopupWindow popupWindow = this.g;
            if (this.u || this.t) {
                z = false;
            }
            popupWindow.setOutsideTouchable(z);
            this.g.update(getAnchorView(), this.m, this.n, i3 < 0 ? -1 : i3, b2 < 0 ? -1 : b2);
        }
    }

    @Override // androidx.appcompat.view.menu.ShowableListMenu
    public void dismiss() {
        this.g.dismiss();
        a();
        this.g.setContentView(null);
        this.c = null;
        this.f.removeCallbacks(this.e);
    }

    public void setOnDismissListener(@Nullable PopupWindow.OnDismissListener onDismissListener) {
        this.g.setOnDismissListener(onDismissListener);
    }

    private void a() {
        View view = this.v;
        if (view != null) {
            ViewParent parent = view.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(this.v);
            }
        }
    }

    public void setInputMethodMode(int i) {
        this.g.setInputMethodMode(i);
    }

    public int getInputMethodMode() {
        return this.g.getInputMethodMode();
    }

    public void setSelection(int i) {
        m mVar = this.c;
        if (isShowing() && mVar != null) {
            mVar.setListSelectionHidden(false);
            mVar.setSelection(i);
            if (mVar.getChoiceMode() != 0) {
                mVar.setItemChecked(i, true);
            }
        }
    }

    public void clearListSelection() {
        m mVar = this.c;
        if (mVar != null) {
            mVar.setListSelectionHidden(true);
            mVar.requestLayout();
        }
    }

    @Override // androidx.appcompat.view.menu.ShowableListMenu
    public boolean isShowing() {
        return this.g.isShowing();
    }

    public boolean isInputMethodNotNeeded() {
        return this.g.getInputMethodMode() == 2;
    }

    public boolean performItemClick(int i) {
        if (!isShowing()) {
            return false;
        }
        if (this.A == null) {
            return true;
        }
        m mVar = this.c;
        this.A.onItemClick(mVar, mVar.getChildAt(i - mVar.getFirstVisiblePosition()), i, mVar.getAdapter().getItemId(i));
        return true;
    }

    @Nullable
    public Object getSelectedItem() {
        if (!isShowing()) {
            return null;
        }
        return this.c.getSelectedItem();
    }

    public int getSelectedItemPosition() {
        if (!isShowing()) {
            return -1;
        }
        return this.c.getSelectedItemPosition();
    }

    public long getSelectedItemId() {
        if (!isShowing()) {
            return Long.MIN_VALUE;
        }
        return this.c.getSelectedItemId();
    }

    @Nullable
    public View getSelectedView() {
        if (!isShowing()) {
            return null;
        }
        return this.c.getSelectedView();
    }

    @Override // androidx.appcompat.view.menu.ShowableListMenu
    @Nullable
    public ListView getListView() {
        return this.c;
    }

    @NonNull
    m a(Context context, boolean z) {
        return new m(context, z);
    }

    public boolean onKeyDown(int i, @NonNull KeyEvent keyEvent) {
        if (isShowing() && i != 62 && (this.c.getSelectedItemPosition() >= 0 || !a(i))) {
            int selectedItemPosition = this.c.getSelectedItemPosition();
            boolean z = !this.g.isAboveAnchor();
            ListAdapter listAdapter = this.j;
            int i2 = Integer.MAX_VALUE;
            int i3 = Integer.MIN_VALUE;
            if (listAdapter != null) {
                boolean areAllItemsEnabled = listAdapter.areAllItemsEnabled();
                i2 = areAllItemsEnabled ? 0 : this.c.lookForSelectablePosition(0, true);
                if (areAllItemsEnabled) {
                    i3 = listAdapter.getCount() - 1;
                } else {
                    i3 = this.c.lookForSelectablePosition(listAdapter.getCount() - 1, false);
                }
            }
            if ((!z || i != 19 || selectedItemPosition > i2) && (z || i != 20 || selectedItemPosition < i3)) {
                this.c.setListSelectionHidden(false);
                if (this.c.onKeyDown(i, keyEvent)) {
                    this.g.setInputMethodMode(2);
                    this.c.requestFocusFromTouch();
                    show();
                    if (!(i == 23 || i == 66)) {
                        switch (i) {
                        }
                    }
                    return true;
                } else if (!z || i != 20) {
                    if (!z && i == 19 && selectedItemPosition == i2) {
                        return true;
                    }
                } else if (selectedItemPosition == i3) {
                    return true;
                }
            } else {
                clearListSelection();
                this.g.setInputMethodMode(1);
                show();
                return true;
            }
        }
        return false;
    }

    public boolean onKeyUp(int i, @NonNull KeyEvent keyEvent) {
        if (!isShowing() || this.c.getSelectedItemPosition() < 0) {
            return false;
        }
        boolean onKeyUp = this.c.onKeyUp(i, keyEvent);
        if (onKeyUp && a(i)) {
            dismiss();
        }
        return onKeyUp;
    }

    public boolean onKeyPreIme(int i, @NonNull KeyEvent keyEvent) {
        if (i != 4 || !isShowing()) {
            return false;
        }
        View view = this.y;
        if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
            KeyEvent.DispatcherState keyDispatcherState = view.getKeyDispatcherState();
            if (keyDispatcherState != null) {
                keyDispatcherState.startTracking(keyEvent, this);
            }
            return true;
        } else if (keyEvent.getAction() != 1) {
            return false;
        } else {
            KeyEvent.DispatcherState keyDispatcherState2 = view.getKeyDispatcherState();
            if (keyDispatcherState2 != null) {
                keyDispatcherState2.handleUpEvent(keyEvent);
            }
            if (!keyEvent.isTracking() || keyEvent.isCanceled()) {
                return false;
            }
            dismiss();
            return true;
        }
    }

    public View.OnTouchListener createDragToOpenListener(View view) {
        return new ForwardingListener(view) { // from class: androidx.appcompat.widget.ListPopupWindow.1
            /* renamed from: b */
            public ListPopupWindow getPopup() {
                return ListPopupWindow.this;
            }
        };
    }

    /* JADX WARN: Multi-variable type inference failed */
    private int b() {
        int i;
        int i2;
        int i3;
        int i4;
        boolean z = true;
        if (this.c == null) {
            Context context = this.i;
            this.F = new Runnable() { // from class: androidx.appcompat.widget.ListPopupWindow.2
                @Override // java.lang.Runnable
                public void run() {
                    View anchorView = ListPopupWindow.this.getAnchorView();
                    if (anchorView != null && anchorView.getWindowToken() != null) {
                        ListPopupWindow.this.show();
                    }
                }
            };
            this.c = a(context, !this.I);
            Drawable drawable = this.z;
            if (drawable != null) {
                this.c.setSelector(drawable);
            }
            this.c.setAdapter(this.j);
            this.c.setOnItemClickListener(this.A);
            this.c.setFocusable(true);
            this.c.setFocusableInTouchMode(true);
            this.c.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: androidx.appcompat.widget.ListPopupWindow.3
                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onNothingSelected(AdapterView<?> adapterView) {
                }

                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onItemSelected(AdapterView<?> adapterView, View view, int i5, long j) {
                    m mVar;
                    if (i5 != -1 && (mVar = ListPopupWindow.this.c) != null) {
                        mVar.setListSelectionHidden(false);
                    }
                }
            });
            this.c.setOnScrollListener(this.D);
            AdapterView.OnItemSelectedListener onItemSelectedListener = this.B;
            if (onItemSelectedListener != null) {
                this.c.setOnItemSelectedListener(onItemSelectedListener);
            }
            m mVar = this.c;
            View view = this.v;
            if (view != null) {
                LinearLayout linearLayout = new LinearLayout(context);
                linearLayout.setOrientation(1);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, 0, 1.0f);
                switch (this.w) {
                    case 0:
                        linearLayout.addView(view);
                        linearLayout.addView(mVar, layoutParams);
                        break;
                    case 1:
                        linearLayout.addView(mVar, layoutParams);
                        linearLayout.addView(view);
                        break;
                    default:
                        Log.e("ListPopupWindow", "Invalid hint position " + this.w);
                        break;
                }
                int i5 = this.l;
                if (i5 >= 0) {
                    i4 = Integer.MIN_VALUE;
                } else {
                    i5 = 0;
                    i4 = 0;
                }
                view.measure(View.MeasureSpec.makeMeasureSpec(i5, i4), 0);
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) view.getLayoutParams();
                i = view.getMeasuredHeight() + layoutParams2.topMargin + layoutParams2.bottomMargin;
                mVar = linearLayout;
            } else {
                i = 0;
            }
            this.g.setContentView(mVar);
        } else {
            ViewGroup viewGroup = (ViewGroup) this.g.getContentView();
            View view2 = this.v;
            if (view2 != null) {
                LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) view2.getLayoutParams();
                i = view2.getMeasuredHeight() + layoutParams3.topMargin + layoutParams3.bottomMargin;
            } else {
                i = 0;
            }
        }
        Drawable background = this.g.getBackground();
        if (background != null) {
            background.getPadding(this.G);
            i2 = this.G.top + this.G.bottom;
            if (!this.p) {
                this.n = -this.G.top;
            }
        } else {
            this.G.setEmpty();
            i2 = 0;
        }
        if (this.g.getInputMethodMode() != 2) {
            z = false;
        }
        int a2 = a(getAnchorView(), this.n, z);
        if (this.t || this.k == -1) {
            return a2 + i2;
        }
        int i6 = this.l;
        switch (i6) {
            case -2:
                i3 = View.MeasureSpec.makeMeasureSpec(this.i.getResources().getDisplayMetrics().widthPixels - (this.G.left + this.G.right), Integer.MIN_VALUE);
                break;
            case -1:
                i3 = View.MeasureSpec.makeMeasureSpec(this.i.getResources().getDisplayMetrics().widthPixels - (this.G.left + this.G.right), 1073741824);
                break;
            default:
                i3 = View.MeasureSpec.makeMeasureSpec(i6, 1073741824);
                break;
        }
        int measureHeightOfChildrenCompat = this.c.measureHeightOfChildrenCompat(i3, 0, -1, a2 - i, -1);
        if (measureHeightOfChildrenCompat > 0) {
            i += i2 + this.c.getPaddingTop() + this.c.getPaddingBottom();
        }
        return measureHeightOfChildrenCompat + i;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setOverlapAnchor(boolean z) {
        this.r = true;
        this.q = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class b extends DataSetObserver {
        b() {
        }

        @Override // android.database.DataSetObserver
        public void onChanged() {
            if (ListPopupWindow.this.isShowing()) {
                ListPopupWindow.this.show();
            }
        }

        @Override // android.database.DataSetObserver
        public void onInvalidated() {
            ListPopupWindow.this.dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class a implements Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            ListPopupWindow.this.clearListSelection();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class e implements Runnable {
        e() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (ListPopupWindow.this.c != null && ViewCompat.isAttachedToWindow(ListPopupWindow.this.c) && ListPopupWindow.this.c.getCount() > ListPopupWindow.this.c.getChildCount() && ListPopupWindow.this.c.getChildCount() <= ListPopupWindow.this.d) {
                ListPopupWindow.this.g.setInputMethodMode(2);
                ListPopupWindow.this.show();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class d implements View.OnTouchListener {
        d() {
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int action = motionEvent.getAction();
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            if (action == 0 && ListPopupWindow.this.g != null && ListPopupWindow.this.g.isShowing() && x >= 0 && x < ListPopupWindow.this.g.getWidth() && y >= 0 && y < ListPopupWindow.this.g.getHeight()) {
                ListPopupWindow.this.f.postDelayed(ListPopupWindow.this.e, 250L);
                return false;
            } else if (action != 1) {
                return false;
            } else {
                ListPopupWindow.this.f.removeCallbacks(ListPopupWindow.this.e);
                return false;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class c implements AbsListView.OnScrollListener {
        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView absListView, int i, int i2, int i3) {
        }

        c() {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView absListView, int i) {
            if (i == 1 && !ListPopupWindow.this.isInputMethodNotNeeded() && ListPopupWindow.this.g.getContentView() != null) {
                ListPopupWindow.this.f.removeCallbacks(ListPopupWindow.this.e);
                ListPopupWindow.this.e.run();
            }
        }
    }

    private void a(boolean z) {
        if (Build.VERSION.SDK_INT <= 28) {
            Method method = a;
            if (method != null) {
                try {
                    method.invoke(this.g, Boolean.valueOf(z));
                } catch (Exception unused) {
                    Log.i("ListPopupWindow", "Could not call setClipToScreenEnabled() on PopupWindow. Oh well.");
                }
            }
        } else {
            this.g.setIsClippedToScreen(z);
        }
    }

    private int a(View view, int i, boolean z) {
        if (Build.VERSION.SDK_INT > 23) {
            return this.g.getMaxAvailableHeight(view, i, z);
        }
        Method method = b;
        if (method != null) {
            try {
                return ((Integer) method.invoke(this.g, view, Integer.valueOf(i), Boolean.valueOf(z))).intValue();
            } catch (Exception unused) {
                Log.i("ListPopupWindow", "Could not call getMaxAvailableHeightMethod(View, int, boolean) on PopupWindow. Using the public version.");
            }
        }
        return this.g.getMaxAvailableHeight(view, i);
    }
}
