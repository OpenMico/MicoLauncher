package miuix.animation;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import java.lang.ref.WeakReference;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.CommonUtils;

/* loaded from: classes5.dex */
public class ViewTarget extends IAnimTarget<View> {
    public static final ITargetCreator<View> sCreator = new ITargetCreator<View>() { // from class: miuix.animation.ViewTarget.1
        /* renamed from: a */
        public IAnimTarget createTarget(View view) {
            return new ViewTarget(view);
        }
    };
    private WeakReference<View> h;
    private LifecycleCallbacks i;
    private ViewLifecyclerObserver j;
    private WeakReference<Context> k;

    private ViewTarget(View view) {
        this.h = new WeakReference<>(view);
        a(view.getContext());
    }

    private boolean a(Context context) {
        while (context != null) {
            if (context instanceof LifecycleOwner) {
                this.k = new WeakReference<>(context);
                if (this.j == null) {
                    this.j = new ViewLifecyclerObserver();
                }
                ((LifecycleOwner) context).getLifecycle().addObserver(this.j);
                return true;
            } else if (!(context instanceof Activity)) {
                context = context instanceof ContextWrapper ? ((ContextWrapper) context).getBaseContext() : null;
            } else if (Build.VERSION.SDK_INT < 29) {
                return false;
            } else {
                this.k = new WeakReference<>(context);
                if (this.i == null) {
                    this.i = new LifecycleCallbacks();
                }
                ((Activity) context).registerActivityLifecycleCallbacks(this.i);
                return true;
            }
        }
        return false;
    }

    private boolean b(Context context) {
        LifecycleCallbacks lifecycleCallbacks;
        if (context == null) {
            return false;
        }
        if (context instanceof LifecycleOwner) {
            if (this.j != null) {
                ((LifecycleOwner) context).getLifecycle().removeObserver(this.j);
            }
            this.j = null;
            return true;
        } else if (Build.VERSION.SDK_INT < 29 || !(context instanceof Activity) || (lifecycleCallbacks = this.i) == null) {
            return false;
        } else {
            ((Activity) context).unregisterActivityLifecycleCallbacks(lifecycleCallbacks);
            this.i = null;
            return true;
        }
    }

    @Override // miuix.animation.IAnimTarget
    public View getTargetObject() {
        return this.h.get();
    }

    @Override // miuix.animation.IAnimTarget
    public void clean() {
        WeakReference<Context> weakReference = this.k;
        if (weakReference != null) {
            b(weakReference.get());
        }
    }

    @Override // miuix.animation.IAnimTarget
    public boolean isValid() {
        return this.h.get() != null;
    }

    @Override // miuix.animation.IAnimTarget
    public void getLocationOnScreen(int[] iArr) {
        View view = this.h.get();
        if (view == null) {
            iArr[1] = Integer.MAX_VALUE;
            iArr[0] = Integer.MAX_VALUE;
            return;
        }
        view.getLocationOnScreen(iArr);
    }

    @Override // miuix.animation.IAnimTarget
    public void onFrameEnd(boolean z) {
        View view = this.h.get();
        if (z && view != null) {
            view.setTag(R.id.miuix_animation_tag_set_height, null);
            view.setTag(R.id.miuix_animation_tag_set_width, null);
            view.setTag(R.id.miuix_animation_tag_view_corner, Float.valueOf(0.0f));
        }
    }

    @Override // miuix.animation.IAnimTarget
    public void executeOnInitialized(final Runnable runnable) {
        final View view = this.h.get();
        if (view == null) {
            return;
        }
        if (view.getVisibility() == 8 && !view.isLaidOut() && (view.getWidth() == 0 || view.getHeight() == 0)) {
            post(new Runnable() { // from class: miuix.animation.ViewTarget.2
                @Override // java.lang.Runnable
                public void run() {
                    ViewTarget.this.a(view, runnable);
                }
            });
        } else {
            post(runnable);
        }
    }

    public void a(View view, Runnable runnable) {
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            view.setTag(R.id.miuix_animation_tag_init_layout, true);
            ViewGroup viewGroup = (ViewGroup) parent;
            int left = viewGroup.getLeft();
            int top = viewGroup.getTop();
            int visibility = view.getVisibility();
            if (visibility == 8) {
                view.setVisibility(4);
            }
            viewGroup.measure(viewGroup.getWidth(), viewGroup.getHeight());
            viewGroup.layout(left, top, viewGroup.getWidth() + left, viewGroup.getHeight() + top);
            view.setVisibility(visibility);
            runnable.run();
            view.setTag(R.id.miuix_animation_tag_init_layout, null);
        }
    }

    @Override // miuix.animation.IAnimTarget
    public boolean shouldUseIntValue(FloatProperty floatProperty) {
        if (floatProperty == ViewProperty.WIDTH || floatProperty == ViewProperty.HEIGHT || floatProperty == ViewProperty.SCROLL_X || floatProperty == ViewProperty.SCROLL_Y) {
            return true;
        }
        return super.shouldUseIntValue(floatProperty);
    }

    @Override // miuix.animation.IAnimTarget
    public boolean allowAnimRun() {
        View targetObject = getTargetObject();
        return targetObject != null && !Folme.isInDraggingState(targetObject);
    }

    @Override // miuix.animation.IAnimTarget
    public void post(Runnable runnable) {
        View targetObject = getTargetObject();
        if (targetObject != null) {
            if (this.handler.isInTargetThread() || !targetObject.isAttachedToWindow()) {
                a(runnable);
            } else {
                targetObject.post(runnable);
            }
        }
    }

    private void a(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            Log.w(CommonUtils.TAG, "ViewTarget.executeTask failed, " + getTargetObject(), e);
        }
    }

    /* loaded from: classes5.dex */
    public class ViewLifecyclerObserver implements LifecycleObserver {
        protected ViewLifecyclerObserver() {
            ViewTarget.this = r1;
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        void onDestroy() {
            ViewTarget.this.a();
        }
    }

    /* loaded from: classes5.dex */
    public class LifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(@NonNull Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(@NonNull Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(@NonNull Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(@NonNull Activity activity) {
        }

        protected LifecycleCallbacks() {
            ViewTarget.this = r1;
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(@NonNull Activity activity) {
            ViewTarget.this.a();
        }
    }

    public void a() {
        WeakReference<Context> weakReference = this.k;
        if (weakReference != null) {
            b(weakReference.get());
        }
        a(0.0f);
        Folme.clean(this);
    }

    private void a(float f) {
        View view = this.h.get();
        if (view != null) {
            view.setTag(R.id.miuix_animation_tag_view_corner, Float.valueOf(f));
        }
    }
}
