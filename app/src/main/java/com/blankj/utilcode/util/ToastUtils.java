package com.blankj.utilcode.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.CallSuper;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import com.blankj.utilcode.R;
import com.blankj.utilcode.util.Utils;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.SimpleExoPlayer;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

/* loaded from: classes.dex */
public final class ToastUtils {
    private static final ToastUtils a = make();
    private static WeakReference<c> b;
    private String c;
    private int d = -1;
    private int e = -1;
    private int f = -1;
    private int g = -16777217;
    private int h = -1;
    private int i = -16777217;
    private int j = -1;
    private boolean k = false;
    private Drawable[] l = new Drawable[4];
    private boolean m = false;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface MODE {
        public static final String DARK = "dark";
        public static final String LIGHT = "light";
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public interface c {
        void a();

        void a(View view);

        void a(CharSequence charSequence);

        void b(int i);
    }

    @NonNull
    public static ToastUtils make() {
        return new ToastUtils();
    }

    @NonNull
    public final ToastUtils setMode(String str) {
        this.c = str;
        return this;
    }

    @NonNull
    public final ToastUtils setGravity(int i, int i2, int i3) {
        this.d = i;
        this.e = i2;
        this.f = i3;
        return this;
    }

    @NonNull
    public final ToastUtils setBgColor(@ColorInt int i) {
        this.g = i;
        return this;
    }

    @NonNull
    public final ToastUtils setBgResource(@DrawableRes int i) {
        this.h = i;
        return this;
    }

    @NonNull
    public final ToastUtils setTextColor(@ColorInt int i) {
        this.i = i;
        return this;
    }

    @NonNull
    public final ToastUtils setTextSize(int i) {
        this.j = i;
        return this;
    }

    @NonNull
    public final ToastUtils setDurationIsLong(boolean z) {
        this.k = z;
        return this;
    }

    @NonNull
    public final ToastUtils setLeftIcon(@DrawableRes int i) {
        ToastUtils leftIcon = setLeftIcon(ContextCompat.getDrawable(Utils.getApp(), i));
        if (leftIcon != null) {
            return leftIcon;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ToastUtils.setLeftIcon() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public final ToastUtils setLeftIcon(@Nullable Drawable drawable) {
        this.l[0] = drawable;
        return this;
    }

    @NonNull
    public final ToastUtils setTopIcon(@DrawableRes int i) {
        ToastUtils topIcon = setTopIcon(ContextCompat.getDrawable(Utils.getApp(), i));
        if (topIcon != null) {
            return topIcon;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ToastUtils.setTopIcon() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public final ToastUtils setTopIcon(@Nullable Drawable drawable) {
        this.l[1] = drawable;
        return this;
    }

    @NonNull
    public final ToastUtils setRightIcon(@DrawableRes int i) {
        ToastUtils rightIcon = setRightIcon(ContextCompat.getDrawable(Utils.getApp(), i));
        if (rightIcon != null) {
            return rightIcon;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ToastUtils.setRightIcon() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public final ToastUtils setRightIcon(@Nullable Drawable drawable) {
        this.l[2] = drawable;
        return this;
    }

    @NonNull
    public final ToastUtils setBottomIcon(int i) {
        ToastUtils bottomIcon = setBottomIcon(ContextCompat.getDrawable(Utils.getApp(), i));
        if (bottomIcon != null) {
            return bottomIcon;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ToastUtils.setBottomIcon() marked by @androidx.annotation.NonNull");
    }

    @NonNull
    public final ToastUtils setBottomIcon(@Nullable Drawable drawable) {
        this.l[3] = drawable;
        return this;
    }

    @NonNull
    public final ToastUtils setNotUseSystemToast() {
        this.m = true;
        return this;
    }

    @NonNull
    public static ToastUtils getDefaultMaker() {
        ToastUtils toastUtils = a;
        if (toastUtils != null) {
            return toastUtils;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.ToastUtils.getDefaultMaker() marked by @androidx.annotation.NonNull");
    }

    public final void show(@Nullable CharSequence charSequence) {
        a(charSequence, b(), this);
    }

    public final void show(@StringRes int i) {
        a(b.a(i), b(), this);
    }

    public final void show(@StringRes int i, Object... objArr) {
        a(b.a(i, objArr), b(), this);
    }

    public final void show(@Nullable String str, Object... objArr) {
        a(b.a(str, objArr), b(), this);
    }

    public final void show(@NonNull View view) {
        if (view != null) {
            a(view, b(), this);
            return;
        }
        throw new NullPointerException("Argument 'view' of type View (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    private int b() {
        return this.k ? 1 : 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public View a(CharSequence charSequence) {
        if (!MODE.DARK.equals(this.c) && !"light".equals(this.c)) {
            Drawable[] drawableArr = this.l;
            if (drawableArr[0] == null && drawableArr[1] == null && drawableArr[2] == null && drawableArr[3] == null) {
                return null;
            }
        }
        View b2 = b.b(R.layout.utils_toast_view);
        TextView textView = (TextView) b2.findViewById(16908299);
        if (MODE.DARK.equals(this.c)) {
            ((GradientDrawable) b2.getBackground().mutate()).setColor(Color.parseColor("#BB000000"));
            textView.setTextColor(-1);
        }
        textView.setText(charSequence);
        if (this.l[0] != null) {
            View findViewById = b2.findViewById(R.id.utvLeftIconView);
            ViewCompat.setBackground(findViewById, this.l[0]);
            findViewById.setVisibility(0);
        }
        if (this.l[1] != null) {
            View findViewById2 = b2.findViewById(R.id.utvTopIconView);
            ViewCompat.setBackground(findViewById2, this.l[1]);
            findViewById2.setVisibility(0);
        }
        if (this.l[2] != null) {
            View findViewById3 = b2.findViewById(R.id.utvRightIconView);
            ViewCompat.setBackground(findViewById3, this.l[2]);
            findViewById3.setVisibility(0);
        }
        if (this.l[3] != null) {
            View findViewById4 = b2.findViewById(R.id.utvBottomIconView);
            ViewCompat.setBackground(findViewById4, this.l[3]);
            findViewById4.setVisibility(0);
        }
        return b2;
    }

    public static void showShort(@Nullable CharSequence charSequence) {
        a(charSequence, 0, a);
    }

    public static void showShort(@StringRes int i) {
        a(b.a(i), 0, a);
    }

    public static void showShort(@StringRes int i, Object... objArr) {
        a(b.a(i, objArr), 0, a);
    }

    public static void showShort(@Nullable String str, Object... objArr) {
        a(b.a(str, objArr), 0, a);
    }

    public static void showLong(@Nullable CharSequence charSequence) {
        a(charSequence, 1, a);
    }

    public static void showLong(@StringRes int i) {
        a(b.a(i), 1, a);
    }

    public static void showLong(@StringRes int i, Object... objArr) {
        a(b.a(i, objArr), 1, a);
    }

    public static void showLong(@Nullable String str, Object... objArr) {
        a(b.a(str, objArr), 1, a);
    }

    public static void cancel() {
        b.a(new Runnable() { // from class: com.blankj.utilcode.util.ToastUtils.1
            @Override // java.lang.Runnable
            public void run() {
                if (ToastUtils.b != null) {
                    c cVar = (c) ToastUtils.b.get();
                    if (cVar != null) {
                        cVar.a();
                    }
                    WeakReference unused = ToastUtils.b = null;
                }
            }
        });
    }

    private static void a(@Nullable CharSequence charSequence, int i, ToastUtils toastUtils) {
        a(null, b(charSequence), i, toastUtils);
    }

    private static void a(@NonNull View view, int i, ToastUtils toastUtils) {
        if (view != null) {
            a(view, null, i, toastUtils);
            return;
        }
        throw new NullPointerException("Argument 'view' of type View (#0 out of 3, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    private static void a(@Nullable final View view, @Nullable final CharSequence charSequence, final int i, @NonNull ToastUtils toastUtils) {
        if (toastUtils != null) {
            b.a(new Runnable() { // from class: com.blankj.utilcode.util.ToastUtils.2
                @Override // java.lang.Runnable
                public void run() {
                    ToastUtils.cancel();
                    c i2 = ToastUtils.i(ToastUtils.this);
                    WeakReference unused = ToastUtils.b = new WeakReference(i2);
                    View view2 = view;
                    if (view2 != null) {
                        i2.a(view2);
                    } else {
                        i2.a(charSequence);
                    }
                    i2.b(i);
                }
            });
            return;
        }
        throw new NullPointerException("Argument 'utils' of type ToastUtils (#3 out of 4, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    private static CharSequence b(CharSequence charSequence) {
        return charSequence == null ? "toast null" : charSequence.length() == 0 ? "toast nothing" : charSequence;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static c i(ToastUtils toastUtils) {
        if (!toastUtils.m && NotificationManagerCompat.from(Utils.getApp()).areNotificationsEnabled()) {
            if (Build.VERSION.SDK_INT < 23) {
                return new d(toastUtils);
            }
            if (!b.m()) {
                return new d(toastUtils);
            }
        }
        if (Build.VERSION.SDK_INT < 25) {
            return new e(toastUtils, 2005);
        }
        if (!b.m()) {
            return new b(toastUtils);
        }
        if (Build.VERSION.SDK_INT >= 26) {
            return new e(toastUtils, 2038);
        }
        return new e(toastUtils, PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_TIMEOUT);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class d extends a {
        d(ToastUtils toastUtils) {
            super(toastUtils);
            if (Build.VERSION.SDK_INT == 25) {
                try {
                    Field declaredField = Toast.class.getDeclaredField("mTN");
                    declaredField.setAccessible(true);
                    Object obj = declaredField.get(this.a);
                    Field declaredField2 = declaredField.getType().getDeclaredField("mHandler");
                    declaredField2.setAccessible(true);
                    declaredField2.set(obj, new a((Handler) declaredField2.get(obj)));
                } catch (Exception unused) {
                }
            }
        }

        @Override // com.blankj.utilcode.util.ToastUtils.c
        public void b(int i) {
            if (this.a != null) {
                this.a.setDuration(i);
                this.a.show();
            }
        }

        /* loaded from: classes.dex */
        static class a extends Handler {
            private Handler a;

            a(Handler handler) {
                this.a = handler;
            }

            @Override // android.os.Handler
            public void handleMessage(@NonNull Message message) {
                if (message != null) {
                    this.a.handleMessage(message);
                    return;
                }
                throw new NullPointerException("Argument 'msg' of type Message (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            }

            @Override // android.os.Handler
            public void dispatchMessage(@NonNull Message message) {
                if (message != null) {
                    try {
                        this.a.dispatchMessage(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    throw new NullPointerException("Argument 'msg' of type Message (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class e extends a {
        private WindowManager d;
        private WindowManager.LayoutParams e;

        e(ToastUtils toastUtils, int i) {
            super(toastUtils);
            this.e = new WindowManager.LayoutParams();
            this.d = (WindowManager) Utils.getApp().getSystemService("window");
            this.e.type = i;
        }

        e(ToastUtils toastUtils, WindowManager windowManager, int i) {
            super(toastUtils);
            this.e = new WindowManager.LayoutParams();
            this.d = windowManager;
            this.e.type = i;
        }

        @Override // com.blankj.utilcode.util.ToastUtils.c
        public void b(int i) {
            if (this.a != null) {
                WindowManager.LayoutParams layoutParams = this.e;
                layoutParams.height = -2;
                layoutParams.width = -2;
                layoutParams.format = -3;
                layoutParams.windowAnimations = 16973828;
                layoutParams.setTitle("ToastWithoutNotification");
                WindowManager.LayoutParams layoutParams2 = this.e;
                layoutParams2.flags = 152;
                layoutParams2.packageName = Utils.getApp().getPackageName();
                this.e.gravity = this.a.getGravity();
                if ((this.e.gravity & 7) == 7) {
                    this.e.horizontalWeight = 1.0f;
                }
                if ((this.e.gravity & 112) == 112) {
                    this.e.verticalWeight = 1.0f;
                }
                this.e.x = this.a.getXOffset();
                this.e.y = this.a.getYOffset();
                this.e.horizontalMargin = this.a.getHorizontalMargin();
                this.e.verticalMargin = this.a.getVerticalMargin();
                try {
                    if (this.d != null) {
                        this.d.addView(this.c, this.e);
                    }
                } catch (Exception unused) {
                }
                b.a(new Runnable() { // from class: com.blankj.utilcode.util.ToastUtils.e.1
                    @Override // java.lang.Runnable
                    public void run() {
                        e.this.a();
                    }
                }, i == 0 ? SimpleExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS : 3500L);
            }
        }

        @Override // com.blankj.utilcode.util.ToastUtils.a, com.blankj.utilcode.util.ToastUtils.c
        public void a() {
            try {
                if (this.d != null) {
                    this.d.removeViewImmediate(this.c);
                    this.d = null;
                }
            } catch (Exception unused) {
            }
            super.a();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class b extends a {
        private static int d;
        private Utils.ActivityLifecycleCallbacks e;
        private c f;

        b(ToastUtils toastUtils) {
            super(toastUtils);
        }

        @Override // com.blankj.utilcode.util.ToastUtils.c
        public void b(int i) {
            if (this.a != null) {
                if (!b.e()) {
                    this.f = c(i);
                    return;
                }
                boolean z = false;
                for (Activity activity : b.c()) {
                    if (b.b(activity)) {
                        if (!z) {
                            this.f = a(activity, i);
                            z = true;
                        } else {
                            a(activity, d, true);
                        }
                    }
                }
                if (z) {
                    b();
                    b.a(new Runnable() { // from class: com.blankj.utilcode.util.ToastUtils.b.1
                        @Override // java.lang.Runnable
                        public void run() {
                            b.this.a();
                        }
                    }, i == 0 ? SimpleExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS : 3500L);
                    d++;
                    return;
                }
                this.f = c(i);
            }
        }

        @Override // com.blankj.utilcode.util.ToastUtils.a, com.blankj.utilcode.util.ToastUtils.c
        public void a() {
            Window window;
            if (d()) {
                c();
                for (Activity activity : b.c()) {
                    if (b.b(activity) && (window = activity.getWindow()) != null) {
                        ViewGroup viewGroup = (ViewGroup) window.getDecorView();
                        StringBuilder sb = new StringBuilder();
                        sb.append("TAG_TOAST");
                        sb.append(d - 1);
                        View findViewWithTag = viewGroup.findViewWithTag(sb.toString());
                        if (findViewWithTag != null) {
                            try {
                                viewGroup.removeView(findViewWithTag);
                            } catch (Exception unused) {
                            }
                        }
                    }
                }
            }
            c cVar = this.f;
            if (cVar != null) {
                cVar.a();
                this.f = null;
            }
            super.a();
        }

        private c c(int i) {
            d dVar = new d(this.b);
            dVar.a = this.a;
            dVar.b(i);
            return dVar;
        }

        private c a(Activity activity, int i) {
            e eVar = new e(this.b, activity.getWindowManager(), 99);
            eVar.c = a(-1);
            eVar.a = this.a;
            eVar.b(i);
            return eVar;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(Activity activity, int i, boolean z) {
            Window window = activity.getWindow();
            if (window != null) {
                ViewGroup viewGroup = (ViewGroup) window.getDecorView();
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
                layoutParams.gravity = this.a.getGravity();
                layoutParams.bottomMargin = this.a.getYOffset() + b.k();
                layoutParams.topMargin = this.a.getYOffset() + b.j();
                layoutParams.leftMargin = this.a.getXOffset();
                View a = a(i);
                if (z) {
                    a.setAlpha(0.0f);
                    a.animate().alpha(1.0f).setDuration(200L).start();
                }
                viewGroup.addView(a, layoutParams);
            }
        }

        private void b() {
            final int i = d;
            this.e = new Utils.ActivityLifecycleCallbacks() { // from class: com.blankj.utilcode.util.ToastUtils.b.2
                @Override // com.blankj.utilcode.util.Utils.ActivityLifecycleCallbacks
                public void onActivityCreated(@NonNull Activity activity) {
                    if (activity == null) {
                        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
                    } else if (b.this.d()) {
                        b.this.a(activity, i, false);
                    }
                }
            };
            b.a(this.e);
        }

        private void c() {
            b.b(this.e);
            this.e = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean d() {
            return this.e != null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static abstract class a implements c {
        protected Toast a = new Toast(Utils.getApp());
        protected ToastUtils b;
        protected View c;

        a(ToastUtils toastUtils) {
            this.b = toastUtils;
            if (this.b.d != -1 || this.b.e != -1 || this.b.f != -1) {
                this.a.setGravity(this.b.d, this.b.e, this.b.f);
            }
        }

        @Override // com.blankj.utilcode.util.ToastUtils.c
        public void a(View view) {
            this.c = view;
            this.a.setView(this.c);
        }

        @Override // com.blankj.utilcode.util.ToastUtils.c
        public void a(CharSequence charSequence) {
            View a = this.b.a(charSequence);
            if (a != null) {
                a(a);
                b();
                return;
            }
            this.c = this.a.getView();
            View view = this.c;
            if (view == null || view.findViewById(16908299) == null) {
                a(b.b(R.layout.utils_toast_view));
            }
            TextView textView = (TextView) this.c.findViewById(16908299);
            textView.setText(charSequence);
            if (this.b.i != -16777217) {
                textView.setTextColor(this.b.i);
            }
            if (this.b.j != -1) {
                textView.setTextSize(this.b.j);
            }
            a(textView);
            b();
        }

        private void b() {
            if (b.v()) {
                a(a(-1));
            }
        }

        private void a(TextView textView) {
            if (this.b.h != -1) {
                this.c.setBackgroundResource(this.b.h);
                textView.setBackgroundColor(0);
            } else if (this.b.g != -16777217) {
                Drawable background = this.c.getBackground();
                Drawable background2 = textView.getBackground();
                if (background != null && background2 != null) {
                    background.mutate().setColorFilter(new PorterDuffColorFilter(this.b.g, PorterDuff.Mode.SRC_IN));
                    textView.setBackgroundColor(0);
                } else if (background != null) {
                    background.mutate().setColorFilter(new PorterDuffColorFilter(this.b.g, PorterDuff.Mode.SRC_IN));
                } else if (background2 != null) {
                    background2.mutate().setColorFilter(new PorterDuffColorFilter(this.b.g, PorterDuff.Mode.SRC_IN));
                } else {
                    this.c.setBackgroundColor(this.b.g);
                }
            }
        }

        @Override // com.blankj.utilcode.util.ToastUtils.c
        @CallSuper
        public void a() {
            Toast toast = this.a;
            if (toast != null) {
                toast.cancel();
            }
            this.a = null;
            this.c = null;
        }

        View a(int i) {
            Bitmap a = b.a(this.c);
            ImageView imageView = new ImageView(Utils.getApp());
            imageView.setTag("TAG_TOAST" + i);
            imageView.setImageBitmap(a);
            return imageView;
        }
    }

    /* loaded from: classes.dex */
    public static final class UtilsMaxWidthRelativeLayout extends RelativeLayout {
        private static final int a = b.a(80.0f);

        public UtilsMaxWidthRelativeLayout(Context context) {
            super(context);
        }

        public UtilsMaxWidthRelativeLayout(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public UtilsMaxWidthRelativeLayout(Context context, AttributeSet attributeSet, int i) {
            super(context, attributeSet, i);
        }

        @Override // android.widget.RelativeLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(b.r() - a, Integer.MIN_VALUE), i2);
        }
    }
}
