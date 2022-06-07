package com.bumptech.glide.request.target;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.R;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.util.Preconditions;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public abstract class CustomViewTarget<T extends View, Z> implements Target<Z> {
    @IdRes
    private static final int a = R.id.glide_custom_view_target_tag;
    private final a b;
    @Nullable
    private View.OnAttachStateChangeListener c;
    private boolean d;
    private boolean e;
    protected final T view;

    @Override // com.bumptech.glide.manager.LifecycleListener
    public void onDestroy() {
    }

    protected abstract void onResourceCleared(@Nullable Drawable drawable);

    protected void onResourceLoading(@Nullable Drawable drawable) {
    }

    @Override // com.bumptech.glide.manager.LifecycleListener
    public void onStart() {
    }

    @Override // com.bumptech.glide.manager.LifecycleListener
    public void onStop() {
    }

    @Deprecated
    public final CustomViewTarget<T, Z> useTagId(@IdRes int i) {
        return this;
    }

    public CustomViewTarget(@NonNull T t) {
        this.view = (T) ((View) Preconditions.checkNotNull(t));
        this.b = new a(t);
    }

    @NonNull
    public final CustomViewTarget<T, Z> waitForLayout() {
        this.b.b = true;
        return this;
    }

    @NonNull
    public final CustomViewTarget<T, Z> clearOnDetach() {
        if (this.c != null) {
            return this;
        }
        this.c = new View.OnAttachStateChangeListener() { // from class: com.bumptech.glide.request.target.CustomViewTarget.1
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view) {
                CustomViewTarget.this.a();
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view) {
                CustomViewTarget.this.b();
            }
        };
        d();
        return this;
    }

    @NonNull
    public final T getView() {
        return this.view;
    }

    @Override // com.bumptech.glide.request.target.Target
    public final void getSize(@NonNull SizeReadyCallback sizeReadyCallback) {
        this.b.a(sizeReadyCallback);
    }

    @Override // com.bumptech.glide.request.target.Target
    public final void removeCallback(@NonNull SizeReadyCallback sizeReadyCallback) {
        this.b.b(sizeReadyCallback);
    }

    @Override // com.bumptech.glide.request.target.Target
    public final void onLoadStarted(@Nullable Drawable drawable) {
        d();
        onResourceLoading(drawable);
    }

    @Override // com.bumptech.glide.request.target.Target
    public final void onLoadCleared(@Nullable Drawable drawable) {
        this.b.b();
        onResourceCleared(drawable);
        if (!this.d) {
            e();
        }
    }

    @Override // com.bumptech.glide.request.target.Target
    public final void setRequest(@Nullable Request request) {
        a(request);
    }

    @Override // com.bumptech.glide.request.target.Target
    @Nullable
    public final Request getRequest() {
        Object c = c();
        if (c == null) {
            return null;
        }
        if (c instanceof Request) {
            return (Request) c;
        }
        throw new IllegalArgumentException("You must not pass non-R.id ids to setTag(id)");
    }

    public String toString() {
        return "Target for: " + this.view;
    }

    final void a() {
        Request request = getRequest();
        if (request != null && request.isCleared()) {
            request.begin();
        }
    }

    final void b() {
        Request request = getRequest();
        if (request != null) {
            this.d = true;
            request.clear();
            this.d = false;
        }
    }

    private void a(@Nullable Object obj) {
        this.view.setTag(a, obj);
    }

    @Nullable
    private Object c() {
        return this.view.getTag(a);
    }

    private void d() {
        View.OnAttachStateChangeListener onAttachStateChangeListener = this.c;
        if (onAttachStateChangeListener != null && !this.e) {
            this.view.addOnAttachStateChangeListener(onAttachStateChangeListener);
            this.e = true;
        }
    }

    private void e() {
        View.OnAttachStateChangeListener onAttachStateChangeListener = this.c;
        if (onAttachStateChangeListener != null && this.e) {
            this.view.removeOnAttachStateChangeListener(onAttachStateChangeListener);
            this.e = false;
        }
    }

    @VisibleForTesting
    /* loaded from: classes.dex */
    static final class a {
        @Nullable
        @VisibleForTesting
        static Integer a;
        boolean b;
        private final View c;
        private final List<SizeReadyCallback> d = new ArrayList();
        @Nullable
        private ViewTreeObserver$OnPreDrawListenerC0046a e;

        private boolean a(int i) {
            return i > 0 || i == Integer.MIN_VALUE;
        }

        a(@NonNull View view) {
            this.c = view;
        }

        private static int a(@NonNull Context context) {
            if (a == null) {
                Display defaultDisplay = ((WindowManager) Preconditions.checkNotNull((WindowManager) context.getSystemService("window"))).getDefaultDisplay();
                Point point = new Point();
                defaultDisplay.getSize(point);
                a = Integer.valueOf(Math.max(point.x, point.y));
            }
            return a.intValue();
        }

        private void a(int i, int i2) {
            Iterator it = new ArrayList(this.d).iterator();
            while (it.hasNext()) {
                ((SizeReadyCallback) it.next()).onSizeReady(i, i2);
            }
        }

        void a() {
            if (!this.d.isEmpty()) {
                int d = d();
                int c = c();
                if (b(d, c)) {
                    a(d, c);
                    b();
                }
            }
        }

        void a(@NonNull SizeReadyCallback sizeReadyCallback) {
            int d = d();
            int c = c();
            if (b(d, c)) {
                sizeReadyCallback.onSizeReady(d, c);
                return;
            }
            if (!this.d.contains(sizeReadyCallback)) {
                this.d.add(sizeReadyCallback);
            }
            if (this.e == null) {
                ViewTreeObserver viewTreeObserver = this.c.getViewTreeObserver();
                this.e = new ViewTreeObserver$OnPreDrawListenerC0046a(this);
                viewTreeObserver.addOnPreDrawListener(this.e);
            }
        }

        void b(@NonNull SizeReadyCallback sizeReadyCallback) {
            this.d.remove(sizeReadyCallback);
        }

        void b() {
            ViewTreeObserver viewTreeObserver = this.c.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.removeOnPreDrawListener(this.e);
            }
            this.e = null;
            this.d.clear();
        }

        private boolean b(int i, int i2) {
            return a(i) && a(i2);
        }

        private int c() {
            int paddingTop = this.c.getPaddingTop() + this.c.getPaddingBottom();
            ViewGroup.LayoutParams layoutParams = this.c.getLayoutParams();
            return a(this.c.getHeight(), layoutParams != null ? layoutParams.height : 0, paddingTop);
        }

        private int d() {
            int paddingLeft = this.c.getPaddingLeft() + this.c.getPaddingRight();
            ViewGroup.LayoutParams layoutParams = this.c.getLayoutParams();
            return a(this.c.getWidth(), layoutParams != null ? layoutParams.width : 0, paddingLeft);
        }

        private int a(int i, int i2, int i3) {
            int i4 = i2 - i3;
            if (i4 > 0) {
                return i4;
            }
            if (this.b && this.c.isLayoutRequested()) {
                return 0;
            }
            int i5 = i - i3;
            if (i5 > 0) {
                return i5;
            }
            if (this.c.isLayoutRequested() || i2 != -2) {
                return 0;
            }
            if (Log.isLoggable("CustomViewTarget", 4)) {
                Log.i("CustomViewTarget", "Glide treats LayoutParams.WRAP_CONTENT as a request for an image the size of this device's screen dimensions. If you want to load the original image and are ok with the corresponding memory cost and OOMs (depending on the input size), use .override(Target.SIZE_ORIGINAL). Otherwise, use LayoutParams.MATCH_PARENT, set layout_width and layout_height to fixed dimension, or use .override() with fixed dimensions.");
            }
            return a(this.c.getContext());
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: com.bumptech.glide.request.target.CustomViewTarget$a$a  reason: collision with other inner class name */
        /* loaded from: classes.dex */
        public static final class ViewTreeObserver$OnPreDrawListenerC0046a implements ViewTreeObserver.OnPreDrawListener {
            private final WeakReference<a> a;

            ViewTreeObserver$OnPreDrawListenerC0046a(@NonNull a aVar) {
                this.a = new WeakReference<>(aVar);
            }

            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                if (Log.isLoggable("CustomViewTarget", 2)) {
                    Log.v("CustomViewTarget", "OnGlobalLayoutListener called attachStateListener=" + this);
                }
                a aVar = this.a.get();
                if (aVar == null) {
                    return true;
                }
                aVar.a();
                return true;
            }
        }
    }
}
