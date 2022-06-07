package androidx.fragment.app;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.R;
import java.util.ArrayList;

/* loaded from: classes.dex */
public final class FragmentContainerView extends FrameLayout {
    private ArrayList<View> a;
    private ArrayList<View> b;
    private View.OnApplyWindowInsetsListener c;
    private boolean d;

    @Override // android.view.View
    @NonNull
    @RequiresApi(20)
    public WindowInsets onApplyWindowInsets(@NonNull WindowInsets windowInsets) {
        return windowInsets;
    }

    public FragmentContainerView(@NonNull Context context) {
        super(context);
        this.d = true;
    }

    public FragmentContainerView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FragmentContainerView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.d = true;
        if (attributeSet != null) {
            String classAttribute = attributeSet.getClassAttribute();
            String str = "class";
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.FragmentContainerView);
            if (classAttribute == null) {
                classAttribute = obtainStyledAttributes.getString(R.styleable.FragmentContainerView_android_name);
                str = "android:name";
            }
            obtainStyledAttributes.recycle();
            if (classAttribute != null && !isInEditMode()) {
                throw new UnsupportedOperationException("FragmentContainerView must be within a FragmentActivity to use " + str + "=\"" + classAttribute + "\"");
            }
        }
    }

    public FragmentContainerView(@NonNull Context context, @NonNull AttributeSet attributeSet, @NonNull FragmentManager fragmentManager) {
        super(context, attributeSet);
        String str;
        this.d = true;
        String classAttribute = attributeSet.getClassAttribute();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.FragmentContainerView);
        classAttribute = classAttribute == null ? obtainStyledAttributes.getString(R.styleable.FragmentContainerView_android_name) : classAttribute;
        String string = obtainStyledAttributes.getString(R.styleable.FragmentContainerView_android_tag);
        obtainStyledAttributes.recycle();
        int id = getId();
        Fragment findFragmentById = fragmentManager.findFragmentById(id);
        if (classAttribute != null && findFragmentById == null) {
            if (id <= 0) {
                if (string != null) {
                    str = " with tag " + string;
                } else {
                    str = "";
                }
                throw new IllegalStateException("FragmentContainerView must have an android:id to add Fragment " + classAttribute + str);
            }
            Fragment instantiate = fragmentManager.getFragmentFactory().instantiate(context.getClassLoader(), classAttribute);
            instantiate.onInflate(context, attributeSet, (Bundle) null);
            fragmentManager.beginTransaction().setReorderingAllowed(true).a(this, instantiate, string).commitNowAllowingStateLoss();
        }
        fragmentManager.a(this);
    }

    @Override // android.view.ViewGroup
    public void setLayoutTransition(@Nullable LayoutTransition layoutTransition) {
        if (Build.VERSION.SDK_INT < 18) {
            super.setLayoutTransition(layoutTransition);
            return;
        }
        throw new UnsupportedOperationException("FragmentContainerView does not support Layout Transitions or animateLayoutChanges=\"true\".");
    }

    @Override // android.view.View
    public void setOnApplyWindowInsetsListener(@NonNull View.OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
        this.c = onApplyWindowInsetsListener;
    }

    @Override // android.view.ViewGroup, android.view.View
    @NonNull
    @RequiresApi(20)
    public WindowInsets dispatchApplyWindowInsets(@NonNull WindowInsets windowInsets) {
        WindowInsetsCompat windowInsetsCompat;
        WindowInsetsCompat windowInsetsCompat2 = WindowInsetsCompat.toWindowInsetsCompat(windowInsets);
        View.OnApplyWindowInsetsListener onApplyWindowInsetsListener = this.c;
        if (onApplyWindowInsetsListener != null) {
            windowInsetsCompat = WindowInsetsCompat.toWindowInsetsCompat(onApplyWindowInsetsListener.onApplyWindowInsets(this, windowInsets));
        } else {
            windowInsetsCompat = ViewCompat.onApplyWindowInsets(this, windowInsetsCompat2);
        }
        if (!windowInsetsCompat.isConsumed()) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                ViewCompat.dispatchApplyWindowInsets(getChildAt(i), windowInsetsCompat);
            }
        }
        return windowInsets;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(@NonNull Canvas canvas) {
        if (this.d && this.a != null) {
            for (int i = 0; i < this.a.size(); i++) {
                super.drawChild(canvas, this.a.get(i), getDrawingTime());
            }
        }
        super.dispatchDraw(canvas);
    }

    @Override // android.view.ViewGroup
    protected boolean drawChild(@NonNull Canvas canvas, @NonNull View view, long j) {
        ArrayList<View> arrayList;
        if (!this.d || (arrayList = this.a) == null || arrayList.size() <= 0 || !this.a.contains(view)) {
            return super.drawChild(canvas, view, j);
        }
        return false;
    }

    @Override // android.view.ViewGroup
    public void startViewTransition(@NonNull View view) {
        if (view.getParent() == this) {
            if (this.b == null) {
                this.b = new ArrayList<>();
            }
            this.b.add(view);
        }
        super.startViewTransition(view);
    }

    @Override // android.view.ViewGroup
    public void endViewTransition(@NonNull View view) {
        ArrayList<View> arrayList = this.b;
        if (arrayList != null) {
            arrayList.remove(view);
            ArrayList<View> arrayList2 = this.a;
            if (arrayList2 != null && arrayList2.remove(view)) {
                this.d = true;
            }
        }
        super.endViewTransition(view);
    }

    public void setDrawDisappearingViewsLast(boolean z) {
        this.d = z;
    }

    @Override // android.view.ViewGroup
    public void addView(@NonNull View view, int i, @Nullable ViewGroup.LayoutParams layoutParams) {
        if (FragmentManager.a(view) != null) {
            super.addView(view, i, layoutParams);
            return;
        }
        throw new IllegalStateException("Views added to a FragmentContainerView must be associated with a Fragment. View " + view + " is not associated with a Fragment.");
    }

    @Override // android.view.ViewGroup
    protected boolean addViewInLayout(@NonNull View view, int i, @Nullable ViewGroup.LayoutParams layoutParams, boolean z) {
        if (FragmentManager.a(view) != null) {
            return super.addViewInLayout(view, i, layoutParams, z);
        }
        throw new IllegalStateException("Views added to a FragmentContainerView must be associated with a Fragment. View " + view + " is not associated with a Fragment.");
    }

    @Override // android.view.ViewGroup
    public void removeViewAt(int i) {
        a(getChildAt(i));
        super.removeViewAt(i);
    }

    @Override // android.view.ViewGroup
    public void removeViewInLayout(@NonNull View view) {
        a(view);
        super.removeViewInLayout(view);
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public void removeView(@NonNull View view) {
        a(view);
        super.removeView(view);
    }

    @Override // android.view.ViewGroup
    public void removeViews(int i, int i2) {
        for (int i3 = i; i3 < i + i2; i3++) {
            a(getChildAt(i3));
        }
        super.removeViews(i, i2);
    }

    @Override // android.view.ViewGroup
    public void removeViewsInLayout(int i, int i2) {
        for (int i3 = i; i3 < i + i2; i3++) {
            a(getChildAt(i3));
        }
        super.removeViewsInLayout(i, i2);
    }

    @Override // android.view.ViewGroup
    public void removeAllViewsInLayout() {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            a(getChildAt(childCount));
        }
        super.removeAllViewsInLayout();
    }

    @Override // android.view.ViewGroup
    protected void removeDetachedView(@NonNull View view, boolean z) {
        if (z) {
            a(view);
        }
        super.removeDetachedView(view, z);
    }

    private void a(@NonNull View view) {
        ArrayList<View> arrayList = this.b;
        if (arrayList != null && arrayList.contains(view)) {
            if (this.a == null) {
                this.a = new ArrayList<>();
            }
            this.a.add(view);
        }
    }
}
