package com.uc.crashsdk;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import com.uc.crashsdk.a.a;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ProGuard */
/* loaded from: classes2.dex */
public final class c implements Application.ActivityLifecycleCallbacks {
    private boolean a = false;
    private boolean b = false;

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityCreated(Activity activity, Bundle bundle) {
        a(activity, 2);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStarted(Activity activity) {
        a(activity, 1);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityResumed(Activity activity) {
        a(activity, 1);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityPaused(Activity activity) {
        a(activity, 2);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStopped(Activity activity) {
        a(activity, 2);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityDestroyed(Activity activity) {
        WeakHashMap weakHashMap;
        WeakHashMap weakHashMap2;
        if (g.J()) {
            boolean unused = b.ab = true;
            weakHashMap = b.aa;
            synchronized (weakHashMap) {
                weakHashMap2 = b.aa;
                weakHashMap2.remove(activity);
                a(2);
            }
        }
    }

    private void a(Activity activity, int i) {
        WeakHashMap weakHashMap;
        WeakHashMap weakHashMap2;
        if (1 == i) {
            String unused = b.ac = activity.getComponentName().flattenToShortString();
        } else {
            String unused2 = b.ac = "";
        }
        b.A();
        if (g.J()) {
            boolean unused3 = b.ab = true;
            weakHashMap = b.aa;
            synchronized (weakHashMap) {
                weakHashMap2 = b.aa;
                weakHashMap2.put(activity, Integer.valueOf(i));
                a(i);
            }
        }
    }

    private void a(int i) {
        WeakHashMap weakHashMap;
        WeakHashMap weakHashMap2;
        if (e.t()) {
            a.a("crashsdk", "[LifeCycle] ignore state change while crashing");
            return;
        }
        boolean z = 1 == i;
        if (!z) {
            weakHashMap2 = b.aa;
            Iterator it = weakHashMap2.entrySet().iterator();
            while (true) {
                if (it.hasNext()) {
                    Object value = ((Map.Entry) it.next()).getValue();
                    if (value != null && ((Integer) value).intValue() == 1) {
                        z = true;
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        if (this.a != z) {
            b.b(z);
            this.a = z;
        }
        weakHashMap = b.aa;
        boolean isEmpty = weakHashMap.isEmpty();
        if (this.b != isEmpty) {
            if (isEmpty) {
                b.t();
            }
            this.b = isEmpty;
        }
    }
}
