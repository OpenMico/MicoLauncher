package androidx.core.app;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/* compiled from: ActivityRecreator.java */
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
final class a {
    private static final Handler g = new Handler(Looper.getMainLooper());
    protected static final Class<?> a = d();
    protected static final Field b = b();
    protected static final Field c = c();
    protected static final Method d = a(a);
    protected static final Method e = b(a);
    protected static final Method f = c(a);

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(@NonNull Activity activity) {
        Object obj;
        if (Build.VERSION.SDK_INT >= 28) {
            activity.recreate();
            return true;
        } else if (a() && f == null) {
            return false;
        } else {
            if (e == null && d == null) {
                return false;
            }
            try {
                final Object obj2 = c.get(activity);
                if (obj2 == null || (obj = b.get(activity)) == null) {
                    return false;
                }
                final Application application = activity.getApplication();
                final C0007a aVar = new C0007a(activity);
                application.registerActivityLifecycleCallbacks(aVar);
                g.post(new Runnable() { // from class: androidx.core.app.a.1
                    @Override // java.lang.Runnable
                    public void run() {
                        C0007a.this.a = obj2;
                    }
                });
                if (a()) {
                    f.invoke(obj, obj2, null, null, 0, false, null, null, false, false);
                } else {
                    activity.recreate();
                }
                g.post(new Runnable() { // from class: androidx.core.app.a.2
                    @Override // java.lang.Runnable
                    public void run() {
                        application.unregisterActivityLifecycleCallbacks(aVar);
                    }
                });
                return true;
            } catch (Throwable unused) {
                return false;
            }
        }
    }

    /* compiled from: ActivityRecreator.java */
    /* renamed from: androidx.core.app.a$a  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    private static final class C0007a implements Application.ActivityLifecycleCallbacks {
        Object a;
        private Activity b;
        private final int c;
        private boolean d = false;
        private boolean e = false;
        private boolean f = false;

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(Activity activity) {
        }

        C0007a(@NonNull Activity activity) {
            this.b = activity;
            this.c = this.b.hashCode();
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity) {
            if (this.b == activity) {
                this.d = true;
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
            if (this.e && !this.f && !this.d && a.a(this.a, this.c, activity)) {
                this.f = true;
                this.a = null;
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity) {
            if (this.b == activity) {
                this.b = null;
                this.e = true;
            }
        }
    }

    protected static boolean a(Object obj, int i, Activity activity) {
        try {
            final Object obj2 = c.get(activity);
            if (obj2 == obj && activity.hashCode() == i) {
                final Object obj3 = b.get(activity);
                g.postAtFrontOfQueue(new Runnable() { // from class: androidx.core.app.a.3
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            if (a.d != null) {
                                a.d.invoke(obj3, obj2, false, "AppCompat recreation");
                            } else {
                                a.e.invoke(obj3, obj2, false);
                            }
                        } catch (RuntimeException e2) {
                            if (e2.getClass() == RuntimeException.class && e2.getMessage() != null && e2.getMessage().startsWith("Unable to stop")) {
                                throw e2;
                            }
                        } catch (Throwable th) {
                            Log.e("ActivityRecreator", "Exception while invoking performStopActivity", th);
                        }
                    }
                });
                return true;
            }
            return false;
        } catch (Throwable th) {
            Log.e("ActivityRecreator", "Exception while fetching field values", th);
            return false;
        }
    }

    private static Method a(Class<?> cls) {
        if (cls == null) {
            return null;
        }
        try {
            Method declaredMethod = cls.getDeclaredMethod("performStopActivity", IBinder.class, Boolean.TYPE, String.class);
            declaredMethod.setAccessible(true);
            return declaredMethod;
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Method b(Class<?> cls) {
        if (cls == null) {
            return null;
        }
        try {
            Method declaredMethod = cls.getDeclaredMethod("performStopActivity", IBinder.class, Boolean.TYPE);
            declaredMethod.setAccessible(true);
            return declaredMethod;
        } catch (Throwable unused) {
            return null;
        }
    }

    private static boolean a() {
        return Build.VERSION.SDK_INT == 26 || Build.VERSION.SDK_INT == 27;
    }

    private static Method c(Class<?> cls) {
        if (!a() || cls == null) {
            return null;
        }
        try {
            Method declaredMethod = cls.getDeclaredMethod("requestRelaunchActivity", IBinder.class, List.class, List.class, Integer.TYPE, Boolean.TYPE, Configuration.class, Configuration.class, Boolean.TYPE, Boolean.TYPE);
            declaredMethod.setAccessible(true);
            return declaredMethod;
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Field b() {
        try {
            Field declaredField = Activity.class.getDeclaredField("mMainThread");
            declaredField.setAccessible(true);
            return declaredField;
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Field c() {
        try {
            Field declaredField = Activity.class.getDeclaredField("mToken");
            declaredField.setAccessible(true);
            return declaredField;
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Class<?> d() {
        try {
            return Class.forName("android.app.ActivityThread");
        } catch (Throwable unused) {
            return null;
        }
    }
}
