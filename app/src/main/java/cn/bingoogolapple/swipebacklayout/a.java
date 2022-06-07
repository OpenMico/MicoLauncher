package cn.bingoogolapple.swipebacklayout;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.webkit.WebView;
import androidx.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/* compiled from: BGASwipeBackManager.java */
/* loaded from: classes.dex */
class a implements Application.ActivityLifecycleCallbacks {
    private static final a a = new a();
    private Stack<Activity> b = new Stack<>();
    private Set<Class<? extends View>> c = new HashSet();

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
    }

    public static a a() {
        return a;
    }

    private a() {
    }

    public void a(Application application, List<Class<? extends View>> list) {
        application.registerActivityLifecycleCallbacks(this);
        this.c.add(WebView.class);
        this.c.add(SurfaceView.class);
        if (list != null) {
            this.c.addAll(list);
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
        this.b.add(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
        this.b.remove(activity);
    }

    @Nullable
    public Activity a(Activity activity) {
        Activity activity2;
        try {
            if (this.b.size() <= 1) {
                return null;
            }
            Activity activity3 = this.b.get(this.b.size() - 2);
            try {
                if (activity.equals(activity3)) {
                    int indexOf = this.b.indexOf(activity);
                    if (indexOf > 0) {
                        activity2 = this.b.get(indexOf - 1);
                    } else if (this.b.size() == 2) {
                        activity2 = this.b.lastElement();
                    }
                    return activity2;
                }
                activity2 = activity3;
                return activity2;
            } catch (Exception unused) {
                return activity3;
            }
        } catch (Exception unused2) {
            return null;
        }
    }

    public boolean b() {
        return this.b.size() > 1;
    }

    public boolean a(View view) {
        return this.c.contains(view.getClass());
    }
}
