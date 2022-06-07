package com.blankj.utilcode.util;

import android.os.Build;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public final class SnackbarUtils {
    public static final int LENGTH_INDEFINITE = -2;
    public static final int LENGTH_LONG = 0;
    public static final int LENGTH_SHORT = -1;
    private static WeakReference<Snackbar> a;
    private View b;
    private CharSequence c;
    private int d;
    private int e;
    private int f;
    private int g;
    private CharSequence h;
    private int i;
    private View.OnClickListener j;
    private int k;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface Duration {
    }

    private SnackbarUtils(View view) {
        a();
        this.b = view;
    }

    private void a() {
        this.c = "";
        this.d = -16777217;
        this.e = -16777217;
        this.f = -1;
        this.g = -1;
        this.h = "";
        this.i = -16777217;
        this.k = 0;
    }

    public static SnackbarUtils with(@NonNull View view) {
        if (view != null) {
            return new SnackbarUtils(view);
        }
        throw new NullPointerException("Argument 'view' of type View (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public SnackbarUtils setMessage(@NonNull CharSequence charSequence) {
        if (charSequence != null) {
            this.c = charSequence;
            return this;
        }
        throw new NullPointerException("Argument 'msg' of type CharSequence (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public SnackbarUtils setMessageColor(@ColorInt int i) {
        this.d = i;
        return this;
    }

    public SnackbarUtils setBgColor(@ColorInt int i) {
        this.e = i;
        return this;
    }

    public SnackbarUtils setBgResource(@DrawableRes int i) {
        this.f = i;
        return this;
    }

    public SnackbarUtils setDuration(int i) {
        this.g = i;
        return this;
    }

    public SnackbarUtils setAction(@NonNull CharSequence charSequence, @NonNull View.OnClickListener onClickListener) {
        if (charSequence == null) {
            throw new NullPointerException("Argument 'text' of type CharSequence (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (onClickListener != null) {
            return setAction(charSequence, -16777217, onClickListener);
        } else {
            throw new NullPointerException("Argument 'listener' of type View.OnClickListener (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public SnackbarUtils setAction(@NonNull CharSequence charSequence, @ColorInt int i, @NonNull View.OnClickListener onClickListener) {
        if (charSequence == null) {
            throw new NullPointerException("Argument 'text' of type CharSequence (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (onClickListener != null) {
            this.h = charSequence;
            this.i = i;
            this.j = onClickListener;
            return this;
        } else {
            throw new NullPointerException("Argument 'listener' of type View.OnClickListener (#2 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    public SnackbarUtils setBottomMargin(@IntRange(from = 1) int i) {
        this.k = i;
        return this;
    }

    public Snackbar show() {
        return show(false);
    }

    public Snackbar show(boolean z) {
        View view = this.b;
        if (view == null) {
            return null;
        }
        if (z) {
            ViewGroup a2 = a(view);
            View findViewWithTag = a2.findViewWithTag("topSnackBarCoordinatorLayout");
            if (findViewWithTag == null) {
                CoordinatorLayout coordinatorLayout = new CoordinatorLayout(view.getContext());
                coordinatorLayout.setTag("topSnackBarCoordinatorLayout");
                coordinatorLayout.setRotation(180.0f);
                if (Build.VERSION.SDK_INT >= 21) {
                    coordinatorLayout.setElevation(100.0f);
                }
                a2.addView(coordinatorLayout, -1, -1);
                view = coordinatorLayout;
            } else {
                view = findViewWithTag;
            }
        }
        if (this.d != -16777217) {
            SpannableString spannableString = new SpannableString(this.c);
            spannableString.setSpan(new ForegroundColorSpan(this.d), 0, spannableString.length(), 33);
            a = new WeakReference<>(Snackbar.make(view, spannableString, this.g));
        } else {
            a = new WeakReference<>(Snackbar.make(view, this.c, this.g));
        }
        Snackbar snackbar = a.get();
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        if (z) {
            for (int i = 0; i < snackbarLayout.getChildCount(); i++) {
                snackbarLayout.getChildAt(i).setRotation(180.0f);
            }
        }
        int i2 = this.f;
        if (i2 != -1) {
            snackbarLayout.setBackgroundResource(i2);
        } else {
            int i3 = this.e;
            if (i3 != -16777217) {
                snackbarLayout.setBackgroundColor(i3);
            }
        }
        if (this.k != 0) {
            ((ViewGroup.MarginLayoutParams) snackbarLayout.getLayoutParams()).bottomMargin = this.k;
        }
        if (this.h.length() > 0 && this.j != null) {
            int i4 = this.i;
            if (i4 != -16777217) {
                snackbar.setActionTextColor(i4);
            }
            snackbar.setAction(this.h, this.j);
        }
        snackbar.show();
        return snackbar;
    }

    public void showSuccess() {
        showSuccess(false);
    }

    public void showSuccess(boolean z) {
        this.e = -13912576;
        this.d = -1;
        this.i = -1;
        show(z);
    }

    public void showWarning() {
        showWarning(false);
    }

    public void showWarning(boolean z) {
        this.e = -16128;
        this.d = -1;
        this.i = -1;
        show(z);
    }

    public void showError() {
        showError(false);
    }

    public void showError(boolean z) {
        this.e = -65536;
        this.d = -1;
        this.i = -1;
        show(z);
    }

    public static void dismiss() {
        WeakReference<Snackbar> weakReference = a;
        if (weakReference != null && weakReference.get() != null) {
            a.get().dismiss();
            a = null;
        }
    }

    public static View getView() {
        Snackbar snackbar = a.get();
        if (snackbar == null) {
            return null;
        }
        return snackbar.getView();
    }

    public static void addView(@LayoutRes int i, @NonNull ViewGroup.LayoutParams layoutParams) {
        if (layoutParams != null) {
            View view = getView();
            if (view != null) {
                view.setPadding(0, 0, 0, 0);
                ((Snackbar.SnackbarLayout) view).addView(LayoutInflater.from(view.getContext()).inflate(i, (ViewGroup) null), -1, layoutParams);
                return;
            }
            return;
        }
        throw new NullPointerException("Argument 'params' of type ViewGroup.LayoutParams (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static void addView(@NonNull View view, @NonNull ViewGroup.LayoutParams layoutParams) {
        if (view == null) {
            throw new NullPointerException("Argument 'child' of type View (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (layoutParams != null) {
            View view2 = getView();
            if (view2 != null) {
                view2.setPadding(0, 0, 0, 0);
                ((Snackbar.SnackbarLayout) view2).addView(view, layoutParams);
            }
        } else {
            throw new NullPointerException("Argument 'params' of type ViewGroup.LayoutParams (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    private static ViewGroup a(View view) {
        ViewGroup viewGroup = null;
        while (!(view instanceof CoordinatorLayout)) {
            if (view instanceof FrameLayout) {
                if (view.getId() == 16908290) {
                    return (ViewGroup) view;
                }
                viewGroup = (ViewGroup) view;
            }
            if (view != null) {
                ViewParent parent = view.getParent();
                if (parent instanceof View) {
                    view = (View) parent;
                    continue;
                } else {
                    view = null;
                    continue;
                }
            }
            if (view == null) {
                return viewGroup;
            }
        }
        return (ViewGroup) view;
    }
}
