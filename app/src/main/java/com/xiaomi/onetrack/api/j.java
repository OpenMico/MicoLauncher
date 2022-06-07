package com.xiaomi.onetrack.api;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.SystemClock;
import com.xiaomi.onetrack.util.DeviceUtil;
import com.xiaomi.onetrack.util.p;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class j implements Application.ActivityLifecycleCallbacks {
    final /* synthetic */ f a;
    private int b = 0;
    private int c;
    private long d;
    private boolean e;
    private boolean f;

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public j(f fVar) {
        this.a = fVar;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
        d dVar;
        if (this.b == 0) {
            dVar = this.a.b;
            dVar.a(1);
            this.e = true;
            this.f = false;
            DeviceUtil.a();
        } else {
            this.e = false;
        }
        this.b++;
        p.a("OneTrackImp", "onActivityStarted: " + activity.getLocalClassName());
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        this.a.c(this.f);
        this.c = System.identityHashCode(activity);
        this.d = SystemClock.elapsedRealtime();
        this.a.a(activity.getClass().getName(), this.e);
        if (p.a) {
            p.a("OneTrackImp", "onActivityResumed:" + activity.getLocalClassName() + " isAppStart:" + this.e);
        }
        this.e = false;
        this.a.d();
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        this.a.a(activity.getClass().getName(), this.c == System.identityHashCode(activity) ? SystemClock.elapsedRealtime() - this.d : 0L);
        if (p.a) {
            p.a("OneTrackImp", "onActivityPaused:" + activity.getLocalClassName());
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        d dVar;
        this.b--;
        if (this.b == 0) {
            dVar = this.a.b;
            dVar.a(2);
            this.a.i();
            this.f = true;
            this.e = false;
        } else {
            this.f = false;
        }
        this.a.c(this.f);
        p.a("OneTrackImp", "onActivityStopped: " + activity.getLocalClassName());
    }
}
