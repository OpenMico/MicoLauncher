package androidx.appcompat.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewStub;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.CursorAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.R;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import java.lang.ref.WeakReference;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class AlertController {
    private int A;
    private CharSequence C;
    private Drawable D;
    private CharSequence E;
    private Drawable F;
    private CharSequence G;
    private Drawable H;
    private Drawable J;
    private ImageView K;
    private TextView L;
    private TextView M;
    private View N;
    private int O;
    private int P;
    private boolean Q;
    final AppCompatDialog a;
    ListView b;
    Button c;
    Message d;
    Button e;
    Message f;
    Button g;
    Message h;
    NestedScrollView i;
    ListAdapter j;
    int l;
    int m;
    int n;
    int o;
    Handler p;
    private final Context q;
    private final Window r;
    private final int s;
    private CharSequence t;
    private CharSequence u;
    private View v;
    private int w;
    private int x;
    private int y;
    private int z;
    private boolean B = false;
    private int I = 0;
    int k = -1;
    private int R = 0;
    private final View.OnClickListener S = new View.OnClickListener() { // from class: androidx.appcompat.app.AlertController.1
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            Message message;
            if (view == AlertController.this.c && AlertController.this.d != null) {
                message = Message.obtain(AlertController.this.d);
            } else if (view != AlertController.this.e || AlertController.this.f == null) {
                message = (view != AlertController.this.g || AlertController.this.h == null) ? null : Message.obtain(AlertController.this.h);
            } else {
                message = Message.obtain(AlertController.this.f);
            }
            if (message != null) {
                message.sendToTarget();
            }
            AlertController.this.p.obtainMessage(1, AlertController.this.a).sendToTarget();
        }
    };

    /* loaded from: classes.dex */
    private static final class a extends Handler {
        private WeakReference<DialogInterface> a;

        public a(DialogInterface dialogInterface) {
            this.a = new WeakReference<>(dialogInterface);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i != 1) {
                switch (i) {
                    case -3:
                    case -2:
                    case -1:
                        ((DialogInterface.OnClickListener) message.obj).onClick(this.a.get(), message.what);
                        return;
                    default:
                        return;
                }
            } else {
                ((DialogInterface) message.obj).dismiss();
            }
        }
    }

    private static boolean a(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.alertDialogCenterButtons, typedValue, true);
        return typedValue.data != 0;
    }

    public AlertController(Context context, AppCompatDialog appCompatDialog, Window window) {
        this.q = context;
        this.a = appCompatDialog;
        this.r = window;
        this.p = new a(appCompatDialog);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(null, R.styleable.AlertDialog, R.attr.alertDialogStyle, 0);
        this.O = obtainStyledAttributes.getResourceId(R.styleable.AlertDialog_android_layout, 0);
        this.P = obtainStyledAttributes.getResourceId(R.styleable.AlertDialog_buttonPanelSideLayout, 0);
        this.l = obtainStyledAttributes.getResourceId(R.styleable.AlertDialog_listLayout, 0);
        this.m = obtainStyledAttributes.getResourceId(R.styleable.AlertDialog_multiChoiceItemLayout, 0);
        this.n = obtainStyledAttributes.getResourceId(R.styleable.AlertDialog_singleChoiceItemLayout, 0);
        this.o = obtainStyledAttributes.getResourceId(R.styleable.AlertDialog_listItemLayout, 0);
        this.Q = obtainStyledAttributes.getBoolean(R.styleable.AlertDialog_showTitle, true);
        this.s = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AlertDialog_buttonIconDimen, 0);
        obtainStyledAttributes.recycle();
        appCompatDialog.supportRequestWindowFeature(1);
    }

    static boolean a(View view) {
        if (view.onCheckIsTextEditor()) {
            return true;
        }
        if (!(view instanceof ViewGroup)) {
            return false;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        while (childCount > 0) {
            childCount--;
            if (a(viewGroup.getChildAt(childCount))) {
                return true;
            }
        }
        return false;
    }

    public void a() {
        this.a.setContentView(c());
        d();
    }

    private int c() {
        int i = this.P;
        if (i == 0) {
            return this.O;
        }
        return this.R == 1 ? i : this.O;
    }

    public void a(CharSequence charSequence) {
        this.t = charSequence;
        TextView textView = this.L;
        if (textView != null) {
            textView.setText(charSequence);
        }
    }

    public void b(View view) {
        this.N = view;
    }

    public void b(CharSequence charSequence) {
        this.u = charSequence;
        TextView textView = this.M;
        if (textView != null) {
            textView.setText(charSequence);
        }
    }

    public void a(int i) {
        this.v = null;
        this.w = i;
        this.B = false;
    }

    public void c(View view) {
        this.v = view;
        this.w = 0;
        this.B = false;
    }

    public void a(View view, int i, int i2, int i3, int i4) {
        this.v = view;
        this.w = 0;
        this.B = true;
        this.x = i;
        this.y = i2;
        this.z = i3;
        this.A = i4;
    }

    public void a(int i, CharSequence charSequence, DialogInterface.OnClickListener onClickListener, Message message, Drawable drawable) {
        if (message == null && onClickListener != null) {
            message = this.p.obtainMessage(i, onClickListener);
        }
        switch (i) {
            case -3:
                this.G = charSequence;
                this.h = message;
                this.H = drawable;
                return;
            case -2:
                this.E = charSequence;
                this.f = message;
                this.F = drawable;
                return;
            case -1:
                this.C = charSequence;
                this.d = message;
                this.D = drawable;
                return;
            default:
                throw new IllegalArgumentException("Button does not exist");
        }
    }

    public void b(int i) {
        this.J = null;
        this.I = i;
        ImageView imageView = this.K;
        if (imageView == null) {
            return;
        }
        if (i != 0) {
            imageView.setVisibility(0);
            this.K.setImageResource(this.I);
            return;
        }
        imageView.setVisibility(8);
    }

    public void a(Drawable drawable) {
        this.J = drawable;
        this.I = 0;
        ImageView imageView = this.K;
        if (imageView == null) {
            return;
        }
        if (drawable != null) {
            imageView.setVisibility(0);
            this.K.setImageDrawable(drawable);
            return;
        }
        imageView.setVisibility(8);
    }

    public int c(int i) {
        TypedValue typedValue = new TypedValue();
        this.q.getTheme().resolveAttribute(i, typedValue, true);
        return typedValue.resourceId;
    }

    public ListView b() {
        return this.b;
    }

    public Button d(int i) {
        switch (i) {
            case -3:
                return this.g;
            case -2:
                return this.e;
            case -1:
                return this.c;
            default:
                return null;
        }
    }

    public boolean a(int i, KeyEvent keyEvent) {
        NestedScrollView nestedScrollView = this.i;
        return nestedScrollView != null && nestedScrollView.executeKeyEvent(keyEvent);
    }

    public boolean b(int i, KeyEvent keyEvent) {
        NestedScrollView nestedScrollView = this.i;
        return nestedScrollView != null && nestedScrollView.executeKeyEvent(keyEvent);
    }

    @Nullable
    private ViewGroup a(@Nullable View view, @Nullable View view2) {
        if (view == null) {
            if (view2 instanceof ViewStub) {
                view2 = ((ViewStub) view2).inflate();
            }
            return (ViewGroup) view2;
        }
        if (view2 != null) {
            ViewParent parent = view2.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(view2);
            }
        }
        if (view instanceof ViewStub) {
            view = ((ViewStub) view).inflate();
        }
        return (ViewGroup) view;
    }

    private void d() {
        ListAdapter listAdapter;
        View findViewById;
        View findViewById2;
        View findViewById3 = this.r.findViewById(R.id.parentPanel);
        View findViewById4 = findViewById3.findViewById(R.id.topPanel);
        View findViewById5 = findViewById3.findViewById(R.id.contentPanel);
        View findViewById6 = findViewById3.findViewById(R.id.buttonPanel);
        ViewGroup viewGroup = (ViewGroup) findViewById3.findViewById(R.id.customPanel);
        a(viewGroup);
        View findViewById7 = viewGroup.findViewById(R.id.topPanel);
        View findViewById8 = viewGroup.findViewById(R.id.contentPanel);
        View findViewById9 = viewGroup.findViewById(R.id.buttonPanel);
        ViewGroup a2 = a(findViewById7, findViewById4);
        ViewGroup a3 = a(findViewById8, findViewById5);
        ViewGroup a4 = a(findViewById9, findViewById6);
        c(a3);
        d(a4);
        b(a2);
        int i = 0;
        Object[] objArr = (viewGroup == null || viewGroup.getVisibility() == 8) ? null : 1;
        boolean z = (a2 == null || a2.getVisibility() == 8) ? false : true;
        boolean z2 = (a4 == null || a4.getVisibility() == 8) ? false : true;
        if (!(z2 || a3 == null || (findViewById2 = a3.findViewById(R.id.textSpacerNoButtons)) == null)) {
            findViewById2.setVisibility(0);
        }
        if (z) {
            NestedScrollView nestedScrollView = this.i;
            if (nestedScrollView != null) {
                nestedScrollView.setClipToPadding(true);
            }
            View view = null;
            if (!(this.u == null && this.b == null)) {
                view = a2.findViewById(R.id.titleDividerNoCustom);
            }
            if (view != null) {
                view.setVisibility(0);
            }
        } else if (!(a3 == null || (findViewById = a3.findViewById(R.id.textSpacerNoTitle)) == null)) {
            findViewById.setVisibility(0);
        }
        ListView listView = this.b;
        if (listView instanceof RecycleListView) {
            ((RecycleListView) listView).setHasDecor(z, z2);
        }
        if (objArr == null) {
            View view2 = this.b;
            if (view2 == null) {
                view2 = this.i;
            }
            if (view2 != null) {
                if (z2) {
                    i = 2;
                }
                int i2 = z ? 1 : 0;
                char c = z ? 1 : 0;
                char c2 = z ? 1 : 0;
                a(a3, view2, i2 | i, 3);
            }
        }
        ListView listView2 = this.b;
        if (listView2 != null && (listAdapter = this.j) != null) {
            listView2.setAdapter(listAdapter);
            int i3 = this.k;
            if (i3 > -1) {
                listView2.setItemChecked(i3, true);
                listView2.setSelection(i3);
            }
        }
    }

    private void a(ViewGroup viewGroup, View view, int i, int i2) {
        final View findViewById = this.r.findViewById(R.id.scrollIndicatorUp);
        View findViewById2 = this.r.findViewById(R.id.scrollIndicatorDown);
        if (Build.VERSION.SDK_INT >= 23) {
            ViewCompat.setScrollIndicators(view, i, i2);
            if (findViewById != null) {
                viewGroup.removeView(findViewById);
            }
            if (findViewById2 != null) {
                viewGroup.removeView(findViewById2);
                return;
            }
            return;
        }
        final View view2 = null;
        if (findViewById != null && (i & 1) == 0) {
            viewGroup.removeView(findViewById);
            findViewById = null;
        }
        if (findViewById2 == null || (i & 2) != 0) {
            view2 = findViewById2;
        } else {
            viewGroup.removeView(findViewById2);
        }
        if (findViewById != null || view2 != null) {
            if (this.u != null) {
                this.i.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() { // from class: androidx.appcompat.app.AlertController.2
                    @Override // androidx.core.widget.NestedScrollView.OnScrollChangeListener
                    public void onScrollChange(NestedScrollView nestedScrollView, int i3, int i4, int i5, int i6) {
                        AlertController.a(nestedScrollView, findViewById, view2);
                    }
                });
                this.i.post(new Runnable() { // from class: androidx.appcompat.app.AlertController.3
                    @Override // java.lang.Runnable
                    public void run() {
                        AlertController.a(AlertController.this.i, findViewById, view2);
                    }
                });
                return;
            }
            ListView listView = this.b;
            if (listView != null) {
                listView.setOnScrollListener(new AbsListView.OnScrollListener() { // from class: androidx.appcompat.app.AlertController.4
                    @Override // android.widget.AbsListView.OnScrollListener
                    public void onScrollStateChanged(AbsListView absListView, int i3) {
                    }

                    @Override // android.widget.AbsListView.OnScrollListener
                    public void onScroll(AbsListView absListView, int i3, int i4, int i5) {
                        AlertController.a(absListView, findViewById, view2);
                    }
                });
                this.b.post(new Runnable() { // from class: androidx.appcompat.app.AlertController.5
                    @Override // java.lang.Runnable
                    public void run() {
                        AlertController.a(AlertController.this.b, findViewById, view2);
                    }
                });
                return;
            }
            if (findViewById != null) {
                viewGroup.removeView(findViewById);
            }
            if (view2 != null) {
                viewGroup.removeView(view2);
            }
        }
    }

    private void a(ViewGroup viewGroup) {
        View view = this.v;
        boolean z = false;
        if (view == null) {
            view = this.w != 0 ? LayoutInflater.from(this.q).inflate(this.w, viewGroup, false) : null;
        }
        if (view != null) {
            z = true;
        }
        if (!z || !a(view)) {
            this.r.setFlags(131072, 131072);
        }
        if (z) {
            FrameLayout frameLayout = (FrameLayout) this.r.findViewById(R.id.custom);
            frameLayout.addView(view, new ViewGroup.LayoutParams(-1, -1));
            if (this.B) {
                frameLayout.setPadding(this.x, this.y, this.z, this.A);
            }
            if (this.b != null) {
                ((LinearLayoutCompat.LayoutParams) viewGroup.getLayoutParams()).weight = 0.0f;
                return;
            }
            return;
        }
        viewGroup.setVisibility(8);
    }

    private void b(ViewGroup viewGroup) {
        if (this.N != null) {
            viewGroup.addView(this.N, 0, new ViewGroup.LayoutParams(-1, -2));
            this.r.findViewById(R.id.title_template).setVisibility(8);
            return;
        }
        this.K = (ImageView) this.r.findViewById(16908294);
        if (!(!TextUtils.isEmpty(this.t)) || !this.Q) {
            this.r.findViewById(R.id.title_template).setVisibility(8);
            this.K.setVisibility(8);
            viewGroup.setVisibility(8);
            return;
        }
        this.L = (TextView) this.r.findViewById(R.id.alertTitle);
        this.L.setText(this.t);
        int i = this.I;
        if (i != 0) {
            this.K.setImageResource(i);
            return;
        }
        Drawable drawable = this.J;
        if (drawable != null) {
            this.K.setImageDrawable(drawable);
            return;
        }
        this.L.setPadding(this.K.getPaddingLeft(), this.K.getPaddingTop(), this.K.getPaddingRight(), this.K.getPaddingBottom());
        this.K.setVisibility(8);
    }

    private void c(ViewGroup viewGroup) {
        this.i = (NestedScrollView) this.r.findViewById(R.id.scrollView);
        this.i.setFocusable(false);
        this.i.setNestedScrollingEnabled(false);
        this.M = (TextView) viewGroup.findViewById(16908299);
        TextView textView = this.M;
        if (textView != null) {
            CharSequence charSequence = this.u;
            if (charSequence != null) {
                textView.setText(charSequence);
                return;
            }
            textView.setVisibility(8);
            this.i.removeView(this.M);
            if (this.b != null) {
                ViewGroup viewGroup2 = (ViewGroup) this.i.getParent();
                int indexOfChild = viewGroup2.indexOfChild(this.i);
                viewGroup2.removeViewAt(indexOfChild);
                viewGroup2.addView(this.b, indexOfChild, new ViewGroup.LayoutParams(-1, -1));
                return;
            }
            viewGroup.setVisibility(8);
        }
    }

    static void a(View view, View view2, View view3) {
        int i = 0;
        if (view2 != null) {
            view2.setVisibility(view.canScrollVertically(-1) ? 0 : 4);
        }
        if (view3 != null) {
            if (!view.canScrollVertically(1)) {
                i = 4;
            }
            view3.setVisibility(i);
        }
    }

    private void d(ViewGroup viewGroup) {
        boolean z;
        this.c = (Button) viewGroup.findViewById(16908313);
        this.c.setOnClickListener(this.S);
        boolean z2 = true;
        if (!TextUtils.isEmpty(this.C) || this.D != null) {
            this.c.setText(this.C);
            Drawable drawable = this.D;
            if (drawable != null) {
                int i = this.s;
                drawable.setBounds(0, 0, i, i);
                this.c.setCompoundDrawables(this.D, null, null, null);
            }
            this.c.setVisibility(0);
            z = true;
        } else {
            this.c.setVisibility(8);
            z = false;
        }
        this.e = (Button) viewGroup.findViewById(16908314);
        this.e.setOnClickListener(this.S);
        if (!TextUtils.isEmpty(this.E) || this.F != null) {
            this.e.setText(this.E);
            Drawable drawable2 = this.F;
            if (drawable2 != null) {
                int i2 = this.s;
                drawable2.setBounds(0, 0, i2, i2);
                this.e.setCompoundDrawables(this.F, null, null, null);
            }
            this.e.setVisibility(0);
            z |= true;
        } else {
            this.e.setVisibility(8);
        }
        this.g = (Button) viewGroup.findViewById(16908315);
        this.g.setOnClickListener(this.S);
        if (!TextUtils.isEmpty(this.G) || this.H != null) {
            this.g.setText(this.G);
            Drawable drawable3 = this.H;
            if (drawable3 != null) {
                int i3 = this.s;
                drawable3.setBounds(0, 0, i3, i3);
                this.g.setCompoundDrawables(this.H, null, null, null);
            }
            this.g.setVisibility(0);
            z |= true;
        } else {
            this.g.setVisibility(8);
        }
        if (a(this.q)) {
            if (z) {
                a(this.c);
            } else if (z) {
                a(this.e);
            } else if (z) {
                a(this.g);
            }
        }
        if (!z) {
            z2 = false;
        }
        if (!z2) {
            viewGroup.setVisibility(8);
        }
    }

    private void a(Button button) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) button.getLayoutParams();
        layoutParams.gravity = 1;
        layoutParams.weight = 0.5f;
        button.setLayoutParams(layoutParams);
    }

    /* loaded from: classes.dex */
    public static class RecycleListView extends ListView {
        private final int a;
        private final int b;

        public RecycleListView(Context context) {
            this(context, null);
        }

        public RecycleListView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RecycleListView);
            this.b = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.RecycleListView_paddingBottomNoButtons, -1);
            this.a = obtainStyledAttributes.getDimensionPixelOffset(R.styleable.RecycleListView_paddingTopNoTitle, -1);
        }

        public void setHasDecor(boolean z, boolean z2) {
            if (!z2 || !z) {
                setPadding(getPaddingLeft(), z ? getPaddingTop() : this.a, getPaddingRight(), z2 ? getPaddingBottom() : this.b);
            }
        }
    }

    /* loaded from: classes.dex */
    public static class AlertParams {
        public ListAdapter mAdapter;
        public boolean[] mCheckedItems;
        public final Context mContext;
        public Cursor mCursor;
        public View mCustomTitleView;
        public boolean mForceInverseBackground;
        public Drawable mIcon;
        public final LayoutInflater mInflater;
        public String mIsCheckedColumn;
        public boolean mIsMultiChoice;
        public boolean mIsSingleChoice;
        public CharSequence[] mItems;
        public String mLabelColumn;
        public CharSequence mMessage;
        public Drawable mNegativeButtonIcon;
        public DialogInterface.OnClickListener mNegativeButtonListener;
        public CharSequence mNegativeButtonText;
        public Drawable mNeutralButtonIcon;
        public DialogInterface.OnClickListener mNeutralButtonListener;
        public CharSequence mNeutralButtonText;
        public DialogInterface.OnCancelListener mOnCancelListener;
        public DialogInterface.OnMultiChoiceClickListener mOnCheckboxClickListener;
        public DialogInterface.OnClickListener mOnClickListener;
        public DialogInterface.OnDismissListener mOnDismissListener;
        public AdapterView.OnItemSelectedListener mOnItemSelectedListener;
        public DialogInterface.OnKeyListener mOnKeyListener;
        public OnPrepareListViewListener mOnPrepareListViewListener;
        public Drawable mPositiveButtonIcon;
        public DialogInterface.OnClickListener mPositiveButtonListener;
        public CharSequence mPositiveButtonText;
        public CharSequence mTitle;
        public View mView;
        public int mViewLayoutResId;
        public int mViewSpacingBottom;
        public int mViewSpacingLeft;
        public int mViewSpacingRight;
        public int mViewSpacingTop;
        public int mIconId = 0;
        public int mIconAttrId = 0;
        public boolean mViewSpacingSpecified = false;
        public int mCheckedItem = -1;
        public boolean mRecycleOnMeasure = true;
        public boolean mCancelable = true;

        /* loaded from: classes.dex */
        public interface OnPrepareListViewListener {
            void onPrepareListView(ListView listView);
        }

        public AlertParams(Context context) {
            this.mContext = context;
            this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        }

        public void apply(AlertController alertController) {
            View view = this.mCustomTitleView;
            if (view != null) {
                alertController.b(view);
            } else {
                CharSequence charSequence = this.mTitle;
                if (charSequence != null) {
                    alertController.a(charSequence);
                }
                Drawable drawable = this.mIcon;
                if (drawable != null) {
                    alertController.a(drawable);
                }
                int i = this.mIconId;
                if (i != 0) {
                    alertController.b(i);
                }
                int i2 = this.mIconAttrId;
                if (i2 != 0) {
                    alertController.b(alertController.c(i2));
                }
            }
            CharSequence charSequence2 = this.mMessage;
            if (charSequence2 != null) {
                alertController.b(charSequence2);
            }
            if (!(this.mPositiveButtonText == null && this.mPositiveButtonIcon == null)) {
                alertController.a(-1, this.mPositiveButtonText, this.mPositiveButtonListener, (Message) null, this.mPositiveButtonIcon);
            }
            if (!(this.mNegativeButtonText == null && this.mNegativeButtonIcon == null)) {
                alertController.a(-2, this.mNegativeButtonText, this.mNegativeButtonListener, (Message) null, this.mNegativeButtonIcon);
            }
            if (!(this.mNeutralButtonText == null && this.mNeutralButtonIcon == null)) {
                alertController.a(-3, this.mNeutralButtonText, this.mNeutralButtonListener, (Message) null, this.mNeutralButtonIcon);
            }
            if (!(this.mItems == null && this.mCursor == null && this.mAdapter == null)) {
                a(alertController);
            }
            View view2 = this.mView;
            if (view2 == null) {
                int i3 = this.mViewLayoutResId;
                if (i3 != 0) {
                    alertController.a(i3);
                }
            } else if (this.mViewSpacingSpecified) {
                alertController.a(view2, this.mViewSpacingLeft, this.mViewSpacingTop, this.mViewSpacingRight, this.mViewSpacingBottom);
            } else {
                alertController.c(view2);
            }
        }

        private void a(final AlertController alertController) {
            ListAdapter listAdapter;
            int i;
            final RecycleListView recycleListView = (RecycleListView) this.mInflater.inflate(alertController.l, (ViewGroup) null);
            if (this.mIsMultiChoice) {
                Cursor cursor = this.mCursor;
                if (cursor == null) {
                    listAdapter = new ArrayAdapter<CharSequence>(this.mContext, alertController.m, 16908308, this.mItems) { // from class: androidx.appcompat.app.AlertController.AlertParams.1
                        @Override // android.widget.ArrayAdapter, android.widget.Adapter
                        public View getView(int i2, View view, ViewGroup viewGroup) {
                            View view2 = super.getView(i2, view, viewGroup);
                            if (AlertParams.this.mCheckedItems != null && AlertParams.this.mCheckedItems[i2]) {
                                recycleListView.setItemChecked(i2, true);
                            }
                            return view2;
                        }
                    };
                } else {
                    listAdapter = new CursorAdapter(this.mContext, cursor, false) { // from class: androidx.appcompat.app.AlertController.AlertParams.2
                        private final int d;
                        private final int e;

                        {
                            Cursor cursor2 = getCursor();
                            this.d = cursor2.getColumnIndexOrThrow(AlertParams.this.mLabelColumn);
                            this.e = cursor2.getColumnIndexOrThrow(AlertParams.this.mIsCheckedColumn);
                        }

                        @Override // android.widget.CursorAdapter
                        public void bindView(View view, Context context, Cursor cursor2) {
                            ((CheckedTextView) view.findViewById(16908308)).setText(cursor2.getString(this.d));
                            RecycleListView recycleListView2 = recycleListView;
                            int position = cursor2.getPosition();
                            boolean z = true;
                            if (cursor2.getInt(this.e) != 1) {
                                z = false;
                            }
                            recycleListView2.setItemChecked(position, z);
                        }

                        @Override // android.widget.CursorAdapter
                        public View newView(Context context, Cursor cursor2, ViewGroup viewGroup) {
                            return AlertParams.this.mInflater.inflate(alertController.m, viewGroup, false);
                        }
                    };
                }
            } else {
                if (this.mIsSingleChoice) {
                    i = alertController.n;
                } else {
                    i = alertController.o;
                }
                Cursor cursor2 = this.mCursor;
                if (cursor2 != null) {
                    listAdapter = new SimpleCursorAdapter(this.mContext, i, cursor2, new String[]{this.mLabelColumn}, new int[]{16908308});
                } else {
                    listAdapter = this.mAdapter;
                    if (listAdapter == null) {
                        listAdapter = new b(this.mContext, i, 16908308, this.mItems);
                    }
                }
            }
            OnPrepareListViewListener onPrepareListViewListener = this.mOnPrepareListViewListener;
            if (onPrepareListViewListener != null) {
                onPrepareListViewListener.onPrepareListView(recycleListView);
            }
            alertController.j = listAdapter;
            alertController.k = this.mCheckedItem;
            if (this.mOnClickListener != null) {
                recycleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: androidx.appcompat.app.AlertController.AlertParams.3
                    @Override // android.widget.AdapterView.OnItemClickListener
                    public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j) {
                        AlertParams.this.mOnClickListener.onClick(alertController.a, i2);
                        if (!AlertParams.this.mIsSingleChoice) {
                            alertController.a.dismiss();
                        }
                    }
                });
            } else if (this.mOnCheckboxClickListener != null) {
                recycleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: androidx.appcompat.app.AlertController.AlertParams.4
                    @Override // android.widget.AdapterView.OnItemClickListener
                    public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j) {
                        if (AlertParams.this.mCheckedItems != null) {
                            AlertParams.this.mCheckedItems[i2] = recycleListView.isItemChecked(i2);
                        }
                        AlertParams.this.mOnCheckboxClickListener.onClick(alertController.a, i2, recycleListView.isItemChecked(i2));
                    }
                });
            }
            AdapterView.OnItemSelectedListener onItemSelectedListener = this.mOnItemSelectedListener;
            if (onItemSelectedListener != null) {
                recycleListView.setOnItemSelectedListener(onItemSelectedListener);
            }
            if (this.mIsSingleChoice) {
                recycleListView.setChoiceMode(1);
            } else if (this.mIsMultiChoice) {
                recycleListView.setChoiceMode(2);
            }
            alertController.b = recycleListView;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class b extends ArrayAdapter<CharSequence> {
        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public boolean hasStableIds() {
            return true;
        }

        public b(Context context, int i, int i2, CharSequence[] charSequenceArr) {
            super(context, i, i2, charSequenceArr);
        }
    }
}
