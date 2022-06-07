package androidx.appcompat.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewPropertyAnimator;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.appcompat.R;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.view.ActionBarPolicy;
import androidx.appcompat.widget.LinearLayoutCompat;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public class ScrollingTabContainerView extends HorizontalScrollView implements AdapterView.OnItemSelectedListener {
    private static final Interpolator j = new DecelerateInterpolator();
    Runnable a;
    int c;
    int d;
    private b e;
    private Spinner f;
    private boolean g;
    private int h;
    private int i;
    protected ViewPropertyAnimator mVisibilityAnim;
    protected final VisibilityAnimListener mVisAnimListener = new VisibilityAnimListener();
    LinearLayoutCompat b = d();

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public ScrollingTabContainerView(@NonNull Context context) {
        super(context);
        setHorizontalScrollBarEnabled(false);
        ActionBarPolicy actionBarPolicy = ActionBarPolicy.get(context);
        setContentHeight(actionBarPolicy.getTabContainerHeight());
        this.d = actionBarPolicy.getStackedTabMaxWidth();
        addView(this.b, new ViewGroup.LayoutParams(-2, -1));
    }

    @Override // android.widget.HorizontalScrollView, android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i);
        boolean z = true;
        boolean z2 = mode == 1073741824;
        setFillViewport(z2);
        int childCount = this.b.getChildCount();
        if (childCount <= 1 || !(mode == 1073741824 || mode == Integer.MIN_VALUE)) {
            this.c = -1;
        } else {
            if (childCount > 2) {
                this.c = (int) (View.MeasureSpec.getSize(i) * 0.4f);
            } else {
                this.c = View.MeasureSpec.getSize(i) / 2;
            }
            this.c = Math.min(this.c, this.d);
        }
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.h, 1073741824);
        if (z2 || !this.g) {
            z = false;
        }
        if (z) {
            this.b.measure(0, makeMeasureSpec);
            if (this.b.getMeasuredWidth() > View.MeasureSpec.getSize(i)) {
                b();
            } else {
                c();
            }
        } else {
            c();
        }
        int measuredWidth = getMeasuredWidth();
        super.onMeasure(i, makeMeasureSpec);
        int measuredWidth2 = getMeasuredWidth();
        if (z2 && measuredWidth != measuredWidth2) {
            setTabSelected(this.i);
        }
    }

    private boolean a() {
        Spinner spinner = this.f;
        return spinner != null && spinner.getParent() == this;
    }

    public void setAllowCollapse(boolean z) {
        this.g = z;
    }

    private void b() {
        if (!a()) {
            if (this.f == null) {
                this.f = e();
            }
            removeView(this.b);
            addView(this.f, new ViewGroup.LayoutParams(-2, -1));
            if (this.f.getAdapter() == null) {
                this.f.setAdapter((SpinnerAdapter) new a());
            }
            Runnable runnable = this.a;
            if (runnable != null) {
                removeCallbacks(runnable);
                this.a = null;
            }
            this.f.setSelection(this.i);
        }
    }

    private boolean c() {
        if (!a()) {
            return false;
        }
        removeView(this.f);
        addView(this.b, new ViewGroup.LayoutParams(-2, -1));
        setTabSelected(this.f.getSelectedItemPosition());
        return false;
    }

    public void setTabSelected(int i) {
        this.i = i;
        int childCount = this.b.getChildCount();
        int i2 = 0;
        while (i2 < childCount) {
            View childAt = this.b.getChildAt(i2);
            boolean z = i2 == i;
            childAt.setSelected(z);
            if (z) {
                animateToTab(i);
            }
            i2++;
        }
        Spinner spinner = this.f;
        if (spinner != null && i >= 0) {
            spinner.setSelection(i);
        }
    }

    public void setContentHeight(int i) {
        this.h = i;
        requestLayout();
    }

    private LinearLayoutCompat d() {
        LinearLayoutCompat linearLayoutCompat = new LinearLayoutCompat(getContext(), null, R.attr.actionBarTabBarStyle);
        linearLayoutCompat.setMeasureWithLargestChildEnabled(true);
        linearLayoutCompat.setGravity(17);
        linearLayoutCompat.setLayoutParams(new LinearLayoutCompat.LayoutParams(-2, -1));
        return linearLayoutCompat;
    }

    private Spinner e() {
        AppCompatSpinner appCompatSpinner = new AppCompatSpinner(getContext(), null, R.attr.actionDropDownStyle);
        appCompatSpinner.setLayoutParams(new LinearLayoutCompat.LayoutParams(-2, -1));
        appCompatSpinner.setOnItemSelectedListener(this);
        return appCompatSpinner;
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ActionBarPolicy actionBarPolicy = ActionBarPolicy.get(getContext());
        setContentHeight(actionBarPolicy.getTabContainerHeight());
        this.d = actionBarPolicy.getStackedTabMaxWidth();
    }

    public void animateToVisibility(int i) {
        ViewPropertyAnimator viewPropertyAnimator = this.mVisibilityAnim;
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.cancel();
        }
        if (i == 0) {
            if (getVisibility() != 0) {
                setAlpha(0.0f);
            }
            ViewPropertyAnimator alpha = animate().alpha(1.0f);
            alpha.setDuration(200L);
            alpha.setInterpolator(j);
            alpha.setListener(this.mVisAnimListener.withFinalVisibility(alpha, i));
            alpha.start();
            return;
        }
        ViewPropertyAnimator alpha2 = animate().alpha(0.0f);
        alpha2.setDuration(200L);
        alpha2.setInterpolator(j);
        alpha2.setListener(this.mVisAnimListener.withFinalVisibility(alpha2, i));
        alpha2.start();
    }

    public void animateToTab(int i) {
        final View childAt = this.b.getChildAt(i);
        Runnable runnable = this.a;
        if (runnable != null) {
            removeCallbacks(runnable);
        }
        this.a = new Runnable() { // from class: androidx.appcompat.widget.ScrollingTabContainerView.1
            @Override // java.lang.Runnable
            public void run() {
                ScrollingTabContainerView.this.smoothScrollTo(childAt.getLeft() - ((ScrollingTabContainerView.this.getWidth() - childAt.getWidth()) / 2), 0);
                ScrollingTabContainerView.this.a = null;
            }
        };
        post(this.a);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Runnable runnable = this.a;
        if (runnable != null) {
            post(runnable);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Runnable runnable = this.a;
        if (runnable != null) {
            removeCallbacks(runnable);
        }
    }

    c a(ActionBar.Tab tab, boolean z) {
        c cVar = new c(getContext(), tab, z);
        if (z) {
            cVar.setBackgroundDrawable(null);
            cVar.setLayoutParams(new AbsListView.LayoutParams(-1, this.h));
        } else {
            cVar.setFocusable(true);
            if (this.e == null) {
                this.e = new b();
            }
            cVar.setOnClickListener(this.e);
        }
        return cVar;
    }

    public void addTab(ActionBar.Tab tab, boolean z) {
        c a2 = a(tab, false);
        this.b.addView(a2, new LinearLayoutCompat.LayoutParams(0, -1, 1.0f));
        Spinner spinner = this.f;
        if (spinner != null) {
            ((a) spinner.getAdapter()).notifyDataSetChanged();
        }
        if (z) {
            a2.setSelected(true);
        }
        if (this.g) {
            requestLayout();
        }
    }

    public void addTab(ActionBar.Tab tab, int i, boolean z) {
        c a2 = a(tab, false);
        this.b.addView(a2, i, new LinearLayoutCompat.LayoutParams(0, -1, 1.0f));
        Spinner spinner = this.f;
        if (spinner != null) {
            ((a) spinner.getAdapter()).notifyDataSetChanged();
        }
        if (z) {
            a2.setSelected(true);
        }
        if (this.g) {
            requestLayout();
        }
    }

    public void updateTab(int i) {
        ((c) this.b.getChildAt(i)).a();
        Spinner spinner = this.f;
        if (spinner != null) {
            ((a) spinner.getAdapter()).notifyDataSetChanged();
        }
        if (this.g) {
            requestLayout();
        }
    }

    public void removeTabAt(int i) {
        this.b.removeViewAt(i);
        Spinner spinner = this.f;
        if (spinner != null) {
            ((a) spinner.getAdapter()).notifyDataSetChanged();
        }
        if (this.g) {
            requestLayout();
        }
    }

    public void removeAllTabs() {
        this.b.removeAllViews();
        Spinner spinner = this.f;
        if (spinner != null) {
            ((a) spinner.getAdapter()).notifyDataSetChanged();
        }
        if (this.g) {
            requestLayout();
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j2) {
        ((c) view).b().select();
    }

    /* loaded from: classes.dex */
    public class c extends LinearLayout {
        private final int[] b = {16842964};
        private ActionBar.Tab c;
        private TextView d;
        private ImageView e;
        private View f;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public c(Context context, ActionBar.Tab tab, boolean z) {
            super(context, null, R.attr.actionBarTabStyle);
            ScrollingTabContainerView.this = r4;
            this.c = tab;
            TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, null, this.b, R.attr.actionBarTabStyle, 0);
            if (obtainStyledAttributes.hasValue(0)) {
                setBackgroundDrawable(obtainStyledAttributes.getDrawable(0));
            }
            obtainStyledAttributes.recycle();
            if (z) {
                setGravity(8388627);
            }
            a();
        }

        public void a(ActionBar.Tab tab) {
            this.c = tab;
            a();
        }

        @Override // android.view.View
        public void setSelected(boolean z) {
            boolean z2 = isSelected() != z;
            super.setSelected(z);
            if (z2 && z) {
                sendAccessibilityEvent(4);
            }
        }

        @Override // android.view.View
        public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(accessibilityEvent);
            accessibilityEvent.setClassName("androidx.appcompat.app.ActionBar$Tab");
        }

        @Override // android.view.View
        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            accessibilityNodeInfo.setClassName("androidx.appcompat.app.ActionBar$Tab");
        }

        @Override // android.widget.LinearLayout, android.view.View
        public void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
            if (ScrollingTabContainerView.this.c > 0 && getMeasuredWidth() > ScrollingTabContainerView.this.c) {
                super.onMeasure(View.MeasureSpec.makeMeasureSpec(ScrollingTabContainerView.this.c, 1073741824), i2);
            }
        }

        public void a() {
            ActionBar.Tab tab = this.c;
            View customView = tab.getCustomView();
            CharSequence charSequence = null;
            if (customView != null) {
                ViewParent parent = customView.getParent();
                if (parent != this) {
                    if (parent != null) {
                        ((ViewGroup) parent).removeView(customView);
                    }
                    addView(customView);
                }
                this.f = customView;
                TextView textView = this.d;
                if (textView != null) {
                    textView.setVisibility(8);
                }
                ImageView imageView = this.e;
                if (imageView != null) {
                    imageView.setVisibility(8);
                    this.e.setImageDrawable(null);
                    return;
                }
                return;
            }
            View view = this.f;
            if (view != null) {
                removeView(view);
                this.f = null;
            }
            Drawable icon = tab.getIcon();
            CharSequence text = tab.getText();
            if (icon != null) {
                if (this.e == null) {
                    AppCompatImageView appCompatImageView = new AppCompatImageView(getContext());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                    layoutParams.gravity = 16;
                    appCompatImageView.setLayoutParams(layoutParams);
                    addView(appCompatImageView, 0);
                    this.e = appCompatImageView;
                }
                this.e.setImageDrawable(icon);
                this.e.setVisibility(0);
            } else {
                ImageView imageView2 = this.e;
                if (imageView2 != null) {
                    imageView2.setVisibility(8);
                    this.e.setImageDrawable(null);
                }
            }
            boolean z = !TextUtils.isEmpty(text);
            if (z) {
                if (this.d == null) {
                    AppCompatTextView appCompatTextView = new AppCompatTextView(getContext(), null, R.attr.actionBarTabTextStyle);
                    appCompatTextView.setEllipsize(TextUtils.TruncateAt.END);
                    LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
                    layoutParams2.gravity = 16;
                    appCompatTextView.setLayoutParams(layoutParams2);
                    addView(appCompatTextView);
                    this.d = appCompatTextView;
                }
                this.d.setText(text);
                this.d.setVisibility(0);
            } else {
                TextView textView2 = this.d;
                if (textView2 != null) {
                    textView2.setVisibility(8);
                    this.d.setText((CharSequence) null);
                }
            }
            ImageView imageView3 = this.e;
            if (imageView3 != null) {
                imageView3.setContentDescription(tab.getContentDescription());
            }
            if (!z) {
                charSequence = tab.getContentDescription();
            }
            TooltipCompat.setTooltipText(this, charSequence);
        }

        public ActionBar.Tab b() {
            return this.c;
        }
    }

    /* loaded from: classes.dex */
    public class a extends BaseAdapter {
        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        a() {
            ScrollingTabContainerView.this = r1;
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return ScrollingTabContainerView.this.b.getChildCount();
        }

        @Override // android.widget.Adapter
        public Object getItem(int i) {
            return ((c) ScrollingTabContainerView.this.b.getChildAt(i)).b();
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                return ScrollingTabContainerView.this.a((ActionBar.Tab) getItem(i), true);
            }
            ((c) view).a((ActionBar.Tab) getItem(i));
            return view;
        }
    }

    /* loaded from: classes.dex */
    public class b implements View.OnClickListener {
        b() {
            ScrollingTabContainerView.this = r1;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            ((c) view).b().select();
            int childCount = ScrollingTabContainerView.this.b.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = ScrollingTabContainerView.this.b.getChildAt(i);
                childAt.setSelected(childAt == view);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes.dex */
    public class VisibilityAnimListener extends AnimatorListenerAdapter {
        private boolean b = false;
        private int c;

        protected VisibilityAnimListener() {
            ScrollingTabContainerView.this = r1;
        }

        public VisibilityAnimListener withFinalVisibility(ViewPropertyAnimator viewPropertyAnimator, int i) {
            this.c = i;
            ScrollingTabContainerView.this.mVisibilityAnim = viewPropertyAnimator;
            return this;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
            ScrollingTabContainerView.this.setVisibility(0);
            this.b = false;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            if (!this.b) {
                ScrollingTabContainerView scrollingTabContainerView = ScrollingTabContainerView.this;
                scrollingTabContainerView.mVisibilityAnim = null;
                scrollingTabContainerView.setVisibility(this.c);
            }
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            this.b = true;
        }
    }
}
