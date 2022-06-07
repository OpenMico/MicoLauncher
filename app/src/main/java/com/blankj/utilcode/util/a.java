package com.blankj.utilcode.util;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import com.blankj.utilcode.util.Utils;
import com.xiaomi.idm.api.IDMServer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: UtilsActivityLifecycleImpl.java */
/* loaded from: classes.dex */
public final class a implements Application.ActivityLifecycleCallbacks {
    static final a a = new a();
    private static final Activity e = new Activity();
    private final LinkedList<Activity> b = new LinkedList<>();
    private final List<Utils.OnAppStatusChangedListener> c = new CopyOnWriteArrayList();
    private final Map<Activity, List<Utils.ActivityLifecycleCallbacks>> d = new ConcurrentHashMap();
    private int f = 0;
    private int g = 0;
    private boolean h = false;

    a() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(Application application) {
        application.registerActivityLifecycleCallbacks(this);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(Application application) {
        this.b.clear();
        application.unregisterActivityLifecycleCallbacks(this);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Activity a() {
        for (Activity activity : b()) {
            if (b.b(activity)) {
                return activity;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public List<Activity> b() {
        if (!this.b.isEmpty()) {
            return new LinkedList(this.b);
        }
        this.b.addAll(e());
        return new LinkedList(this.b);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(Utils.OnAppStatusChangedListener onAppStatusChangedListener) {
        this.c.add(onAppStatusChangedListener);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(Utils.OnAppStatusChangedListener onAppStatusChangedListener) {
        this.c.remove(onAppStatusChangedListener);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(Utils.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        a(e, activityLifecycleCallbacks);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(final Activity activity, final Utils.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        if (activity != null && activityLifecycleCallbacks != null) {
            b.a(new Runnable() { // from class: com.blankj.utilcode.util.a.1
                @Override // java.lang.Runnable
                public void run() {
                    a.this.c(activity, activityLifecycleCallbacks);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean c() {
        return !this.h;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(Activity activity, Utils.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        List<Utils.ActivityLifecycleCallbacks> list = this.d.get(activity);
        if (list == null) {
            list = new CopyOnWriteArrayList<>();
            this.d.put(activity, list);
        } else if (list.contains(activityLifecycleCallbacks)) {
            return;
        }
        list.add(activityLifecycleCallbacks);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(Utils.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        b(e, activityLifecycleCallbacks);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(final Activity activity) {
        if (activity != null) {
            b.a(new Runnable() { // from class: com.blankj.utilcode.util.a.2
                @Override // java.lang.Runnable
                public void run() {
                    a.this.d.remove(activity);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(final Activity activity, final Utils.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        if (activity != null && activityLifecycleCallbacks != null) {
            b.a(new Runnable() { // from class: com.blankj.utilcode.util.a.3
                @Override // java.lang.Runnable
                public void run() {
                    a.this.d(activity, activityLifecycleCallbacks);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(Activity activity, Utils.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        List<Utils.ActivityLifecycleCallbacks> list = this.d.get(activity);
        if (list != null && !list.isEmpty()) {
            list.remove(activityLifecycleCallbacks);
        }
    }

    private void a(Activity activity, Lifecycle.Event event) {
        a(activity, event, this.d.get(activity));
        a(activity, event, this.d.get(e));
    }

    private void a(Activity activity, Lifecycle.Event event, List<Utils.ActivityLifecycleCallbacks> list) {
        if (list != null) {
            for (Utils.ActivityLifecycleCallbacks activityLifecycleCallbacks : list) {
                activityLifecycleCallbacks.onLifecycleChanged(activity, event);
                if (event.equals(Lifecycle.Event.ON_CREATE)) {
                    activityLifecycleCallbacks.onActivityCreated(activity);
                } else if (event.equals(Lifecycle.Event.ON_START)) {
                    activityLifecycleCallbacks.onActivityStarted(activity);
                } else if (event.equals(Lifecycle.Event.ON_RESUME)) {
                    activityLifecycleCallbacks.onActivityResumed(activity);
                } else if (event.equals(Lifecycle.Event.ON_PAUSE)) {
                    activityLifecycleCallbacks.onActivityPaused(activity);
                } else if (event.equals(Lifecycle.Event.ON_STOP)) {
                    activityLifecycleCallbacks.onActivityStopped(activity);
                } else if (event.equals(Lifecycle.Event.ON_DESTROY)) {
                    activityLifecycleCallbacks.onActivityDestroyed(activity);
                }
            }
            if (event.equals(Lifecycle.Event.ON_DESTROY)) {
                this.d.remove(activity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Application d() {
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            Object invoke = cls.getMethod("getApplication", new Class[0]).invoke(f(), new Object[0]);
            if (invoke == null) {
                return null;
            }
            return (Application) invoke;
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
            return null;
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
            return null;
        } catch (NoSuchMethodException e4) {
            e4.printStackTrace();
            return null;
        } catch (InvocationTargetException e5) {
            e5.printStackTrace();
            return null;
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(@NonNull Activity activity, Bundle bundle) {
        if (activity != null) {
            if (this.b.size() == 0) {
                b(activity, true);
            }
            LanguageUtils.a(activity);
            i();
            b(activity);
            a(activity, Lifecycle.Event.ON_CREATE);
            return;
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(@NonNull Activity activity) {
        if (activity != null) {
            if (!this.h) {
                b(activity);
            }
            int i = this.g;
            if (i < 0) {
                this.g = i + 1;
            } else {
                this.f++;
            }
            a(activity, Lifecycle.Event.ON_START);
            return;
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(@NonNull Activity activity) {
        if (activity != null) {
            b(activity);
            if (this.h) {
                this.h = false;
                b(activity, true);
            }
            a(activity, false);
            a(activity, Lifecycle.Event.ON_RESUME);
            return;
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(@NonNull Activity activity) {
        if (activity != null) {
            a(activity, Lifecycle.Event.ON_PAUSE);
            return;
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        if (activity.isChangingConfigurations()) {
            this.g--;
        } else {
            this.f--;
            if (this.f <= 0) {
                this.h = true;
                b(activity, false);
            }
        }
        a(activity, true);
        a(activity, Lifecycle.Event.ON_STOP);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {
        if (activity == null) {
            throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (bundle == null) {
            throw new NullPointerException("Argument 'outState' of type Bundle (#1 out of 2, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(@NonNull Activity activity) {
        if (activity != null) {
            this.b.remove(activity);
            b.c(activity);
            a(activity, Lifecycle.Event.ON_DESTROY);
            return;
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    private void a(final Activity activity, boolean z) {
        try {
            if (z) {
                Window window = activity.getWindow();
                window.getDecorView().setTag(-123, Integer.valueOf(window.getAttributes().softInputMode));
                window.setSoftInputMode(3);
            } else {
                final Object tag = activity.getWindow().getDecorView().getTag(-123);
                if (tag instanceof Integer) {
                    b.a(new Runnable() { // from class: com.blankj.utilcode.util.a.4
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                Window window2 = activity.getWindow();
                                if (window2 != null) {
                                    window2.setSoftInputMode(((Integer) tag).intValue());
                                }
                            } catch (Exception unused) {
                            }
                        }
                    }, 100L);
                }
            }
        } catch (Exception unused) {
        }
    }

    private void b(Activity activity, boolean z) {
        if (!this.c.isEmpty()) {
            for (Utils.OnAppStatusChangedListener onAppStatusChangedListener : this.c) {
                if (z) {
                    onAppStatusChangedListener.onForeground(activity);
                } else {
                    onAppStatusChangedListener.onBackground(activity);
                }
            }
        }
    }

    private void b(Activity activity) {
        if (!this.b.contains(activity)) {
            this.b.addFirst(activity);
        } else if (!this.b.getFirst().equals(activity)) {
            this.b.remove(activity);
            this.b.addFirst(activity);
        }
    }

    private List<Activity> e() {
        Object obj;
        LinkedList linkedList = new LinkedList();
        Activity activity = null;
        try {
            Object f = f();
            Field declaredField = f.getClass().getDeclaredField("mActivities");
            declaredField.setAccessible(true);
            obj = declaredField.get(f);
        } catch (Exception e2) {
            Log.e("UtilsActivityLifecycle", "getActivitiesByReflect: " + e2.getMessage());
        }
        if (!(obj instanceof Map)) {
            return linkedList;
        }
        for (Object obj2 : ((Map) obj).values()) {
            Class<?> cls = obj2.getClass();
            Field declaredField2 = cls.getDeclaredField(IDMServer.PERSIST_TYPE_ACTIVITY);
            declaredField2.setAccessible(true);
            Activity activity2 = (Activity) declaredField2.get(obj2);
            if (activity == null) {
                Field declaredField3 = cls.getDeclaredField("paused");
                declaredField3.setAccessible(true);
                if (!declaredField3.getBoolean(obj2)) {
                    activity = activity2;
                } else {
                    linkedList.add(activity2);
                }
            } else {
                linkedList.add(activity2);
            }
        }
        if (activity != null) {
            linkedList.addFirst(activity);
        }
        return linkedList;
    }

    private Object f() {
        Object g = g();
        return g != null ? g : h();
    }

    private Object g() {
        try {
            Field declaredField = Class.forName("android.app.ActivityThread").getDeclaredField("sCurrentActivityThread");
            declaredField.setAccessible(true);
            return declaredField.get(null);
        } catch (Exception e2) {
            Log.e("UtilsActivityLifecycle", "getActivityThreadInActivityThreadStaticField: " + e2.getMessage());
            return null;
        }
    }

    private Object h() {
        try {
            return Class.forName("android.app.ActivityThread").getMethod("currentActivityThread", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception e2) {
            Log.e("UtilsActivityLifecycle", "getActivityThreadInActivityThreadStaticMethod: " + e2.getMessage());
            return null;
        }
    }

    private static void i() {
        if (Build.VERSION.SDK_INT < 26 || !ValueAnimator.areAnimatorsEnabled()) {
            try {
                Field declaredField = ValueAnimator.class.getDeclaredField("sDurationScale");
                declaredField.setAccessible(true);
                if (((Float) declaredField.get(null)).floatValue() == 0.0f) {
                    declaredField.set(null, Float.valueOf(1.0f));
                    Log.i("UtilsActivityLifecycle", "setAnimatorsEnabled: Animators are enabled now!");
                }
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            } catch (NoSuchFieldException e3) {
                e3.printStackTrace();
            }
        }
    }
}
