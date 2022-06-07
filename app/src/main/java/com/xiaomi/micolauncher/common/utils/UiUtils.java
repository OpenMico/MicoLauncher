package com.xiaomi.micolauncher.common.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;

/* loaded from: classes3.dex */
public class UiUtils {
    public static final long PERIOD_1SECOND = 1000;
    public static final long PERIOD_DEFAULT = 500;
    public static final long WAIT_LAYOUT_DELAY_MILLIS = 200;
    private static long a;
    private static int b;
    private static int c;

    /* loaded from: classes3.dex */
    public interface OnLayoutCompleteListener {
        void onLayoutComplete(boolean z);
    }

    public static void waitLayoutComplete(OnLayoutCompleteListener onLayoutCompleteListener, View view) {
        if (!b(view)) {
            view.getViewTreeObserver().addOnGlobalLayoutListener(new b(onLayoutCompleteListener, view));
        } else if (onLayoutCompleteListener != null) {
            onLayoutCompleteListener.onLayoutComplete(true);
        }
    }

    public static void waitLayoutComplete(OnLayoutCompleteListener onLayoutCompleteListener, View... viewArr) {
        if (ContainerUtil.getSize(viewArr) <= 0) {
            throw new IllegalArgumentException("waitLayoutComplete : should not be empty views");
        } else if (!b(viewArr)) {
            a aVar = new a(onLayoutCompleteListener, viewArr);
            for (View view : viewArr) {
                view.getViewTreeObserver().addOnGlobalLayoutListener(aVar);
            }
        } else if (onLayoutCompleteListener != null) {
            onLayoutCompleteListener.onLayoutComplete(true);
        }
    }

    public static boolean b(View... viewArr) {
        for (View view : viewArr) {
            if (!b(view)) {
                return false;
            }
        }
        return true;
    }

    public static boolean b(View view) {
        return (view.getWidth() == 0 || view.getHeight() == 0) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class a implements ViewTreeObserver.OnGlobalLayoutListener {
        private final OnLayoutCompleteListener a;
        private View[] b;
        private int c;
        private boolean d = false;

        a(OnLayoutCompleteListener onLayoutCompleteListener, View... viewArr) {
            this.a = onLayoutCompleteListener;
            this.b = viewArr;
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            this.c++;
            View[] viewArr = this.b;
            if (viewArr == null || !UiUtils.b(viewArr)) {
                if (this.c == ContainerUtil.getSize(this.b) && this.a != null) {
                    c();
                }
            } else if (this.a != null) {
                c();
            }
        }

        private void b() {
            for (View view : this.b) {
                try {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } catch (Exception e) {
                    L.base.e("removeOnGlobalLayoutListener", e);
                }
            }
        }

        private void c() {
            if (!a()) {
                this.a.onLayoutComplete(false);
                b();
                this.d = true;
                this.b = null;
            }
        }

        public boolean a() {
            return this.d;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class b implements ViewTreeObserver.OnGlobalLayoutListener {
        private final OnLayoutCompleteListener a;
        private View b;
        private boolean c = false;

        b(OnLayoutCompleteListener onLayoutCompleteListener, View view) {
            this.a = onLayoutCompleteListener;
            this.b = view;
            ThreadUtil.postDelayedInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.common.utils.-$$Lambda$UiUtils$b$uk1ZZ2E5Qwloyk78T8fZeOCOV0E
                @Override // java.lang.Runnable
                public final void run() {
                    UiUtils.b.this.d();
                }
            }, 200L);
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            View view = this.b;
            if (view != null && UiUtils.b(view) && this.a != null && !a()) {
                d();
            }
        }

        private void b() {
            try {
                this.b.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            } catch (Exception e) {
                L.base.e("removeOnGlobalLayoutListener", e);
            }
        }

        /* renamed from: c */
        public void d() {
            if (!a()) {
                this.a.onLayoutComplete(false);
                b();
                this.c = true;
                this.b = null;
            }
        }

        public boolean a() {
            return this.c;
        }
    }

    public static void setTextSizeInPx(TextView textView, float f) {
        textView.setTextSize(0, f);
    }

    public static int getCornerRadius() {
        if (c == 0) {
            c = MicoApplication.getGlobalContext().getResources().getDimensionPixelSize(R.dimen.corner_radius);
        }
        return c;
    }

    public static int getEntertainmentCornerRadius(Context context) {
        if (b == 0) {
            b = context.getResources().getDimensionPixelSize(R.dimen.corner_radius);
        }
        return b;
    }

    public static void setAppTextStyle(TextView textView, String str, int i) {
        if (ChildModeManager.getManager().isChildMode()) {
            textView.setTypeface(textView.getContext().getResources().getFont(R.font.founder));
        } else {
            textView.setTypeface(Typeface.create(str, i));
        }
    }

    public static void setAppTextStyle(TextView textView, Typeface typeface) {
        if (ChildModeManager.getManager().isChildMode()) {
            textView.setTypeface(textView.getContext().getResources().getFont(R.font.founder));
        } else {
            textView.setTypeface(typeface);
        }
    }

    public static int getSize(Context context, int i) {
        return context.getResources().getDimensionPixelSize(i);
    }

    public static boolean isFragmentInvalid(Fragment fragment) {
        return fragment == null || fragment.getActivity() == null || fragment.getContext() == null || fragment.isDetached() || fragment.isRemoving();
    }

    public static boolean isFragmentVisible(Fragment fragment) {
        return !isFragmentInvalid(fragment) && fragment.isVisible();
    }

    @UiThread
    public static boolean isFastClick() {
        return isFastClick(500L);
    }

    @UiThread
    public static boolean isFastClick(long j) {
        long currentTimeMillis = System.currentTimeMillis();
        a = currentTimeMillis;
        return currentTimeMillis - a < j;
    }
}
