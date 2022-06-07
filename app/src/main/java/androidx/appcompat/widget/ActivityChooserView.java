package androidx.appcompat.widget;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.appcompat.R;
import androidx.appcompat.view.menu.ShowableListMenu;
import androidx.appcompat.widget.ActivityChooserModel;
import androidx.core.view.ActionProvider;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public class ActivityChooserView extends ViewGroup implements ActivityChooserModel.ActivityChooserModelClient {
    final a a;
    final FrameLayout b;
    final FrameLayout c;
    ActionProvider d;
    final DataSetObserver e;
    PopupWindow.OnDismissListener f;
    boolean g;
    int h;
    private final b i;
    private final View j;
    private final Drawable k;
    private final ImageView l;
    private final ImageView m;
    private final int n;
    private final ViewTreeObserver.OnGlobalLayoutListener o;
    private ListPopupWindow p;
    private boolean q;
    private int r;

    public ActivityChooserView(@NonNull Context context) {
        this(context, null);
    }

    public ActivityChooserView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ActivityChooserView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.e = new DataSetObserver() { // from class: androidx.appcompat.widget.ActivityChooserView.1
            @Override // android.database.DataSetObserver
            public void onChanged() {
                super.onChanged();
                ActivityChooserView.this.a.notifyDataSetChanged();
            }

            @Override // android.database.DataSetObserver
            public void onInvalidated() {
                super.onInvalidated();
                ActivityChooserView.this.a.notifyDataSetInvalidated();
            }
        };
        this.o = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: androidx.appcompat.widget.ActivityChooserView.2
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                if (!ActivityChooserView.this.isShowingPopup()) {
                    return;
                }
                if (!ActivityChooserView.this.isShown()) {
                    ActivityChooserView.this.getListPopupWindow().dismiss();
                    return;
                }
                ActivityChooserView.this.getListPopupWindow().show();
                if (ActivityChooserView.this.d != null) {
                    ActivityChooserView.this.d.subUiVisibilityChanged(true);
                }
            }
        };
        this.h = 4;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ActivityChooserView, i, 0);
        ViewCompat.saveAttributeDataForStyleable(this, context, R.styleable.ActivityChooserView, attributeSet, obtainStyledAttributes, i, 0);
        this.h = obtainStyledAttributes.getInt(R.styleable.ActivityChooserView_initialActivityCount, 4);
        Drawable drawable = obtainStyledAttributes.getDrawable(R.styleable.ActivityChooserView_expandActivityOverflowButtonDrawable);
        obtainStyledAttributes.recycle();
        LayoutInflater.from(getContext()).inflate(R.layout.abc_activity_chooser_view, (ViewGroup) this, true);
        this.i = new b();
        this.j = findViewById(R.id.activity_chooser_view_content);
        this.k = this.j.getBackground();
        this.c = (FrameLayout) findViewById(R.id.default_activity_button);
        this.c.setOnClickListener(this.i);
        this.c.setOnLongClickListener(this.i);
        this.m = (ImageView) this.c.findViewById(R.id.image);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.expand_activities_button);
        frameLayout.setOnClickListener(this.i);
        frameLayout.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: androidx.appcompat.widget.ActivityChooserView.3
            @Override // android.view.View.AccessibilityDelegate
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo).setCanOpenPopup(true);
            }
        });
        frameLayout.setOnTouchListener(new ForwardingListener(frameLayout) { // from class: androidx.appcompat.widget.ActivityChooserView.4
            @Override // androidx.appcompat.widget.ForwardingListener
            public ShowableListMenu getPopup() {
                return ActivityChooserView.this.getListPopupWindow();
            }

            @Override // androidx.appcompat.widget.ForwardingListener
            protected boolean onForwardingStarted() {
                ActivityChooserView.this.showPopup();
                return true;
            }

            @Override // androidx.appcompat.widget.ForwardingListener
            protected boolean onForwardingStopped() {
                ActivityChooserView.this.dismissPopup();
                return true;
            }
        });
        this.b = frameLayout;
        this.l = (ImageView) frameLayout.findViewById(R.id.image);
        this.l.setImageDrawable(drawable);
        this.a = new a();
        this.a.registerDataSetObserver(new DataSetObserver() { // from class: androidx.appcompat.widget.ActivityChooserView.5
            @Override // android.database.DataSetObserver
            public void onChanged() {
                super.onChanged();
                ActivityChooserView.this.a();
            }
        });
        Resources resources = context.getResources();
        this.n = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(R.dimen.abc_config_prefDialogWidth));
    }

    @Override // androidx.appcompat.widget.ActivityChooserModel.ActivityChooserModelClient
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void setActivityChooserModel(ActivityChooserModel activityChooserModel) {
        this.a.a(activityChooserModel);
        if (isShowingPopup()) {
            dismissPopup();
            showPopup();
        }
    }

    public void setExpandActivityOverflowButtonDrawable(Drawable drawable) {
        this.l.setImageDrawable(drawable);
    }

    public void setExpandActivityOverflowButtonContentDescription(int i) {
        this.l.setContentDescription(getContext().getString(i));
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setProvider(ActionProvider actionProvider) {
        this.d = actionProvider;
    }

    public boolean showPopup() {
        if (isShowingPopup() || !this.q) {
            return false;
        }
        this.g = false;
        a(this.h);
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:28:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    void a(int r6) {
        /*
            r5 = this;
            androidx.appcompat.widget.ActivityChooserView$a r0 = r5.a
            androidx.appcompat.widget.ActivityChooserModel r0 = r0.e()
            if (r0 == 0) goto L_0x0096
            android.view.ViewTreeObserver r0 = r5.getViewTreeObserver()
            android.view.ViewTreeObserver$OnGlobalLayoutListener r1 = r5.o
            r0.addOnGlobalLayoutListener(r1)
            android.widget.FrameLayout r0 = r5.c
            int r0 = r0.getVisibility()
            r1 = 1
            r2 = 0
            if (r0 != 0) goto L_0x001d
            r0 = r1
            goto L_0x001e
        L_0x001d:
            r0 = r2
        L_0x001e:
            androidx.appcompat.widget.ActivityChooserView$a r3 = r5.a
            int r3 = r3.c()
            r4 = 2147483647(0x7fffffff, float:NaN)
            if (r6 == r4) goto L_0x0039
            int r4 = r6 + r0
            if (r3 <= r4) goto L_0x0039
            androidx.appcompat.widget.ActivityChooserView$a r3 = r5.a
            r3.a(r1)
            androidx.appcompat.widget.ActivityChooserView$a r3 = r5.a
            int r6 = r6 - r1
            r3.a(r6)
            goto L_0x0043
        L_0x0039:
            androidx.appcompat.widget.ActivityChooserView$a r3 = r5.a
            r3.a(r2)
            androidx.appcompat.widget.ActivityChooserView$a r3 = r5.a
            r3.a(r6)
        L_0x0043:
            androidx.appcompat.widget.ListPopupWindow r6 = r5.getListPopupWindow()
            boolean r3 = r6.isShowing()
            if (r3 != 0) goto L_0x0095
            boolean r3 = r5.g
            if (r3 != 0) goto L_0x005a
            if (r0 != 0) goto L_0x0054
            goto L_0x005a
        L_0x0054:
            androidx.appcompat.widget.ActivityChooserView$a r0 = r5.a
            r0.a(r2, r2)
            goto L_0x005f
        L_0x005a:
            androidx.appcompat.widget.ActivityChooserView$a r3 = r5.a
            r3.a(r1, r0)
        L_0x005f:
            androidx.appcompat.widget.ActivityChooserView$a r0 = r5.a
            int r0 = r0.a()
            int r3 = r5.n
            int r0 = java.lang.Math.min(r0, r3)
            r6.setContentWidth(r0)
            r6.show()
            androidx.core.view.ActionProvider r0 = r5.d
            if (r0 == 0) goto L_0x0078
            r0.subUiVisibilityChanged(r1)
        L_0x0078:
            android.widget.ListView r0 = r6.getListView()
            android.content.Context r1 = r5.getContext()
            int r3 = androidx.appcompat.R.string.abc_activitychooserview_choose_application
            java.lang.String r1 = r1.getString(r3)
            r0.setContentDescription(r1)
            android.widget.ListView r6 = r6.getListView()
            android.graphics.drawable.ColorDrawable r0 = new android.graphics.drawable.ColorDrawable
            r0.<init>(r2)
            r6.setSelector(r0)
        L_0x0095:
            return
        L_0x0096:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r0 = "No data model. Did you call #setDataModel?"
            r6.<init>(r0)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.ActivityChooserView.a(int):void");
    }

    public boolean dismissPopup() {
        if (!isShowingPopup()) {
            return true;
        }
        getListPopupWindow().dismiss();
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (!viewTreeObserver.isAlive()) {
            return true;
        }
        viewTreeObserver.removeGlobalOnLayoutListener(this.o);
        return true;
    }

    public boolean isShowingPopup() {
        return getListPopupWindow().isShowing();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ActivityChooserModel e = this.a.e();
        if (e != null) {
            e.registerObserver(this.e);
        }
        this.q = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ActivityChooserModel e = this.a.e();
        if (e != null) {
            e.unregisterObserver(this.e);
        }
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.removeGlobalOnLayoutListener(this.o);
        }
        if (isShowingPopup()) {
            dismissPopup();
        }
        this.q = false;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        View view = this.j;
        if (this.c.getVisibility() != 0) {
            i2 = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i2), 1073741824);
        }
        measureChild(view, i, i2);
        setMeasuredDimension(view.getMeasuredWidth(), view.getMeasuredHeight());
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.j.layout(0, 0, i3 - i, i4 - i2);
        if (!isShowingPopup()) {
            dismissPopup();
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public ActivityChooserModel getDataModel() {
        return this.a.e();
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.f = onDismissListener;
    }

    public void setInitialActivityCount(int i) {
        this.h = i;
    }

    public void setDefaultActionButtonContentDescription(int i) {
        this.r = i;
    }

    ListPopupWindow getListPopupWindow() {
        if (this.p == null) {
            this.p = new ListPopupWindow(getContext());
            this.p.setAdapter(this.a);
            this.p.setAnchorView(this);
            this.p.setModal(true);
            this.p.setOnItemClickListener(this.i);
            this.p.setOnDismissListener(this.i);
        }
        return this.p;
    }

    void a() {
        if (this.a.getCount() > 0) {
            this.b.setEnabled(true);
        } else {
            this.b.setEnabled(false);
        }
        int c = this.a.c();
        int d = this.a.d();
        if (c == 1 || (c > 1 && d > 0)) {
            this.c.setVisibility(0);
            ResolveInfo b2 = this.a.b();
            PackageManager packageManager = getContext().getPackageManager();
            this.m.setImageDrawable(b2.loadIcon(packageManager));
            if (this.r != 0) {
                this.c.setContentDescription(getContext().getString(this.r, b2.loadLabel(packageManager)));
            }
        } else {
            this.c.setVisibility(8);
        }
        if (this.c.getVisibility() == 0) {
            this.j.setBackgroundDrawable(this.k);
        } else {
            this.j.setBackgroundDrawable(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class b implements View.OnClickListener, View.OnLongClickListener, AdapterView.OnItemClickListener, PopupWindow.OnDismissListener {
        b() {
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            switch (((a) adapterView.getAdapter()).getItemViewType(i)) {
                case 0:
                    ActivityChooserView.this.dismissPopup();
                    if (!ActivityChooserView.this.g) {
                        if (!ActivityChooserView.this.a.f()) {
                            i++;
                        }
                        Intent b = ActivityChooserView.this.a.e().b(i);
                        if (b != null) {
                            b.addFlags(524288);
                            ActivityChooserView.this.getContext().startActivity(b);
                            return;
                        }
                        return;
                    } else if (i > 0) {
                        ActivityChooserView.this.a.e().c(i);
                        return;
                    } else {
                        return;
                    }
                case 1:
                    ActivityChooserView.this.a(Integer.MAX_VALUE);
                    return;
                default:
                    throw new IllegalArgumentException();
            }
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (view == ActivityChooserView.this.c) {
                ActivityChooserView.this.dismissPopup();
                Intent b = ActivityChooserView.this.a.e().b(ActivityChooserView.this.a.e().a(ActivityChooserView.this.a.b()));
                if (b != null) {
                    b.addFlags(524288);
                    ActivityChooserView.this.getContext().startActivity(b);
                }
            } else if (view == ActivityChooserView.this.b) {
                ActivityChooserView activityChooserView = ActivityChooserView.this;
                activityChooserView.g = false;
                activityChooserView.a(activityChooserView.h);
            } else {
                throw new IllegalArgumentException();
            }
        }

        @Override // android.view.View.OnLongClickListener
        public boolean onLongClick(View view) {
            if (view == ActivityChooserView.this.c) {
                if (ActivityChooserView.this.a.getCount() > 0) {
                    ActivityChooserView activityChooserView = ActivityChooserView.this;
                    activityChooserView.g = true;
                    activityChooserView.a(activityChooserView.h);
                }
                return true;
            }
            throw new IllegalArgumentException();
        }

        @Override // android.widget.PopupWindow.OnDismissListener
        public void onDismiss() {
            a();
            if (ActivityChooserView.this.d != null) {
                ActivityChooserView.this.d.subUiVisibilityChanged(false);
            }
        }

        private void a() {
            if (ActivityChooserView.this.f != null) {
                ActivityChooserView.this.f.onDismiss();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class a extends BaseAdapter {
        private ActivityChooserModel b;
        private int c = 4;
        private boolean d;
        private boolean e;
        private boolean f;

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public int getViewTypeCount() {
            return 3;
        }

        a() {
        }

        public void a(ActivityChooserModel activityChooserModel) {
            ActivityChooserModel e = ActivityChooserView.this.a.e();
            if (e != null && ActivityChooserView.this.isShown()) {
                e.unregisterObserver(ActivityChooserView.this.e);
            }
            this.b = activityChooserModel;
            if (activityChooserModel != null && ActivityChooserView.this.isShown()) {
                activityChooserModel.registerObserver(ActivityChooserView.this.e);
            }
            notifyDataSetChanged();
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public int getItemViewType(int i) {
            return (!this.f || i != getCount() - 1) ? 0 : 1;
        }

        @Override // android.widget.Adapter
        public int getCount() {
            int a = this.b.a();
            if (!this.d && this.b.b() != null) {
                a--;
            }
            int min = Math.min(a, this.c);
            return this.f ? min + 1 : min;
        }

        @Override // android.widget.Adapter
        public Object getItem(int i) {
            switch (getItemViewType(i)) {
                case 0:
                    if (!this.d && this.b.b() != null) {
                        i++;
                    }
                    return this.b.a(i);
                case 1:
                    return null;
                default:
                    throw new IllegalArgumentException();
            }
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            switch (getItemViewType(i)) {
                case 0:
                    if (view == null || view.getId() != R.id.list_item) {
                        view = LayoutInflater.from(ActivityChooserView.this.getContext()).inflate(R.layout.abc_activity_chooser_view_list_item, viewGroup, false);
                    }
                    PackageManager packageManager = ActivityChooserView.this.getContext().getPackageManager();
                    ResolveInfo resolveInfo = (ResolveInfo) getItem(i);
                    ((ImageView) view.findViewById(R.id.icon)).setImageDrawable(resolveInfo.loadIcon(packageManager));
                    ((TextView) view.findViewById(R.id.title)).setText(resolveInfo.loadLabel(packageManager));
                    if (!this.d || i != 0 || !this.e) {
                        view.setActivated(false);
                    } else {
                        view.setActivated(true);
                    }
                    return view;
                case 1:
                    if (view != null && view.getId() == 1) {
                        return view;
                    }
                    View inflate = LayoutInflater.from(ActivityChooserView.this.getContext()).inflate(R.layout.abc_activity_chooser_view_list_item, viewGroup, false);
                    inflate.setId(1);
                    ((TextView) inflate.findViewById(R.id.title)).setText(ActivityChooserView.this.getContext().getString(R.string.abc_activity_chooser_view_see_all));
                    return inflate;
                default:
                    throw new IllegalArgumentException();
            }
        }

        public int a() {
            int i = this.c;
            this.c = Integer.MAX_VALUE;
            int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
            int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
            int count = getCount();
            int i2 = 0;
            View view = null;
            for (int i3 = 0; i3 < count; i3++) {
                view = getView(i3, view, null);
                view.measure(makeMeasureSpec, makeMeasureSpec2);
                i2 = Math.max(i2, view.getMeasuredWidth());
            }
            this.c = i;
            return i2;
        }

        public void a(int i) {
            if (this.c != i) {
                this.c = i;
                notifyDataSetChanged();
            }
        }

        public ResolveInfo b() {
            return this.b.b();
        }

        public void a(boolean z) {
            if (this.f != z) {
                this.f = z;
                notifyDataSetChanged();
            }
        }

        public int c() {
            return this.b.a();
        }

        public int d() {
            return this.b.c();
        }

        public ActivityChooserModel e() {
            return this.b;
        }

        public void a(boolean z, boolean z2) {
            if (this.d != z || this.e != z2) {
                this.d = z;
                this.e = z2;
                notifyDataSetChanged();
            }
        }

        public boolean f() {
            return this.d;
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    /* loaded from: classes.dex */
    public static class InnerLayout extends LinearLayout {
        private static final int[] a = {16842964};

        public InnerLayout(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, a);
            setBackgroundDrawable(obtainStyledAttributes.getDrawable(0));
            obtainStyledAttributes.recycle();
        }
    }
}
