package com.blankj.utilcode.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.ThreadUtils;

/* loaded from: classes.dex */
public final class Utils {
    @SuppressLint({"StaticFieldLeak"})
    private static Application a;

    /* loaded from: classes.dex */
    public interface Consumer<T> {
        void accept(T t);
    }

    /* loaded from: classes.dex */
    public interface Func1<Ret, Par> {
        Ret call(Par par);
    }

    /* loaded from: classes.dex */
    public interface OnAppStatusChangedListener {
        void onBackground(Activity activity);

        void onForeground(Activity activity);
    }

    /* loaded from: classes.dex */
    public interface Supplier<T> {
        T get();
    }

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void init(Application application) {
        if (application == null) {
            Log.e("Utils", "app is null.");
            return;
        }
        Application application2 = a;
        if (application2 == null) {
            a = application;
            b.a(a);
            b.a();
        } else if (!application2.equals(application)) {
            b.b(a);
            a = application;
            b.a(a);
        }
    }

    public static Application getApp() {
        Application application = a;
        if (application != null) {
            return application;
        }
        init(b.d());
        if (a != null) {
            Log.i("Utils", b.p() + " reflect app success.");
            return a;
        }
        throw new NullPointerException("reflect failed.");
    }

    /* loaded from: classes.dex */
    public static abstract class Task<Result> extends ThreadUtils.SimpleTask<Result> {
        private Consumer<Result> a;

        public Task(Consumer<Result> consumer) {
            this.a = consumer;
        }

        @Override // com.blankj.utilcode.util.ThreadUtils.Task
        public void onSuccess(Result result) {
            Consumer<Result> consumer = this.a;
            if (consumer != null) {
                consumer.accept(result);
            }
        }
    }

    /* loaded from: classes.dex */
    public static class ActivityLifecycleCallbacks {
        public void onActivityCreated(@NonNull Activity activity) {
            if (activity == null) {
                throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            }
        }

        public void onActivityStarted(@NonNull Activity activity) {
            if (activity == null) {
                throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            }
        }

        public void onActivityResumed(@NonNull Activity activity) {
            if (activity == null) {
                throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            }
        }

        public void onActivityPaused(@NonNull Activity activity) {
            if (activity == null) {
                throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            }
        }

        public void onActivityStopped(@NonNull Activity activity) {
            if (activity == null) {
                throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            }
        }

        public void onActivityDestroyed(@NonNull Activity activity) {
            if (activity == null) {
                throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            }
        }

        public void onLifecycleChanged(@NonNull Activity activity, Lifecycle.Event event) {
            if (activity == null) {
                throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
            }
        }
    }
}
