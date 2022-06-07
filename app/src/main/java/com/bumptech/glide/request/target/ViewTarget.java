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
import androidx.annotation.CallSuper;
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

@Deprecated
/* loaded from: classes.dex */
public abstract class ViewTarget<T extends View, Z> extends BaseTarget<Z> {
    private static boolean a;
    private static int b = R.id.glide_custom_view_target_tag;
    private final a c;
    @Nullable
    private View.OnAttachStateChangeListener d;
    private boolean e;
    private boolean f;
    protected final T view;

    public ViewTarget(@NonNull T t) {
        this.view = (T) ((View) Preconditions.checkNotNull(t));
        this.c = new a(t);
    }

    @Deprecated
    public ViewTarget(@NonNull T t, boolean z) {
        this(t);
        if (z) {
            waitForLayout();
        }
    }

    @NonNull
    public final ViewTarget<T, Z> clearOnDetach() {
        if (this.d != null) {
            return this;
        }
        this.d = new View.OnAttachStateChangeListener() { // from class: com.bumptech.glide.request.target.ViewTarget.1
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view) {
                ViewTarget.this.a();
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view) {
                ViewTarget.this.b();
            }
        };
        c();
        return this;
    }

    void a() {
        Request request = getRequest();
        if (request != null && request.isCleared()) {
            request.begin();
        }
    }

    void b() {
        Request request = getRequest();
        if (request != null) {
            this.e = true;
            request.clear();
            this.e = false;
        }
    }

    @NonNull
    public final ViewTarget<T, Z> waitForLayout() {
        this.c.b = true;
        return this;
    }

    @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
    @CallSuper
    public void onLoadStarted(@Nullable Drawable drawable) {
        super.onLoadStarted(drawable);
        c();
    }

    private void c() {
        View.OnAttachStateChangeListener onAttachStateChangeListener = this.d;
        if (onAttachStateChangeListener != null && !this.f) {
            this.view.addOnAttachStateChangeListener(onAttachStateChangeListener);
            this.f = true;
        }
    }

    private void d() {
        View.OnAttachStateChangeListener onAttachStateChangeListener = this.d;
        if (onAttachStateChangeListener != null && this.f) {
            this.view.removeOnAttachStateChangeListener(onAttachStateChangeListener);
            this.f = false;
        }
    }

    @NonNull
    public T getView() {
        return this.view;
    }

    @Override // com.bumptech.glide.request.target.Target
    @CallSuper
    public void getSize(@NonNull SizeReadyCallback sizeReadyCallback) {
        this.c.a(sizeReadyCallback);
    }

    @Override // com.bumptech.glide.request.target.Target
    @CallSuper
    public void removeCallback(@NonNull SizeReadyCallback sizeReadyCallback) {
        this.c.b(sizeReadyCallback);
    }

    @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
    @CallSuper
    public void onLoadCleared(@Nullable Drawable drawable) {
        super.onLoadCleared(drawable);
        this.c.b();
        if (!this.e) {
            d();
        }
    }

    @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
    public void setRequest(@Nullable Request request) {
        a(request);
    }

    @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
    @Nullable
    public Request getRequest() {
        Object e = e();
        if (e == null) {
            return null;
        }
        if (e instanceof Request) {
            return (Request) e;
        }
        throw new IllegalArgumentException("You must not call setTag() on a view Glide is targeting");
    }

    public String toString() {
        return "Target for: " + this.view;
    }

    private void a(@Nullable Object obj) {
        a = true;
        this.view.setTag(b, obj);
    }

    @Nullable
    private Object e() {
        return this.view.getTag(b);
    }

    @Deprecated
    public static void setTagId(int i) {
        if (!a) {
            b = i;
            return;
        }
        throw new IllegalArgumentException("You cannot set the tag id more than once or change the tag id after the first request has been made");
    }

    @VisibleForTesting
    /* loaded from: classes.dex */
    public static final class a {
        @Nullable
        @VisibleForTesting
        static Integer a;
        boolean b;
        private final View c;
        private final List<SizeReadyCallback> d = new ArrayList();
        @Nullable
        private ViewTreeObserver$OnPreDrawListenerC0047a e;

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
                this.e = new ViewTreeObserver$OnPreDrawListenerC0047a(this);
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
            if (Log.isLoggable("ViewTarget", 4)) {
                Log.i("ViewTarget", "Glide treats LayoutParams.WRAP_CONTENT as a request for an image the size of this device's screen dimensions. If you want to load the original image and are ok with the corresponding memory cost and OOMs (depending on the input size), use override(Target.SIZE_ORIGINAL). Otherwise, use LayoutParams.MATCH_PARENT, set layout_width and layout_height to fixed dimension, or use .override() with fixed dimensions.");
            }
            return a(this.c.getContext());
        }

        /* renamed from: com.bumptech.glide.request.target.ViewTarget$a$a */
        /* loaded from: classes.dex */
        public static final class ViewTreeObserver$OnPreDrawListenerC0047a implements ViewTreeObserver.OnPreDrawListener {
            private final WeakReference<a> a;

            ViewTreeObserver$OnPreDrawListenerC0047a(@NonNull a aVar) {
                this.a = new WeakReference<>(aVar);
            }

            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                if (Log.isLoggable("ViewTarget", 2)) {
                    Log.v("ViewTarget", "OnGlobalLayoutListener called attachStateListener=" + this);
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
