package xcrash;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import java.util.Iterator;
import java.util.LinkedList;

/* compiled from: ActivityMonitor.java */
/* loaded from: classes6.dex */
class a {
    private static final a a = new a();
    private LinkedList<Activity> b = null;
    private boolean c = false;

    private a() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static a a() {
        return a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(Application application) {
        this.b = new LinkedList<>();
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() { // from class: xcrash.a.1
            private int b = 0;
            private boolean c = false;

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
            public void onActivityCreated(Activity activity, Bundle bundle) {
                a.this.b.addFirst(activity);
                if (a.this.b.size() > 100) {
                    a.this.b.removeLast();
                }
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityStarted(Activity activity) {
                int i = this.b + 1;
                this.b = i;
                if (i == 1 && !this.c) {
                    a.this.c = true;
                }
            }

            /* JADX WARN: Type inference failed for: r2v1, types: [void, boolean] */
            /* JADX WARN: Unknown variable types count: 1 */
            @Override // android.app.Application.ActivityLifecycleCallbacks
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void onActivityStopped(java.lang.Object r2) {
                /*
                    r1 = this;
                    void r2 = r2.<init>()
                    r1.c = r2
                    int r2 = r1.b
                    int r2 = r2 + (-1)
                    r1.b = r2
                    if (r2 != 0) goto L_0x0018
                    boolean r2 = r1.c
                    if (r2 != 0) goto L_0x0018
                    xcrash.a r2 = xcrash.a.this
                    r0 = 0
                    xcrash.a.a(r2, r0)
                L_0x0018:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: xcrash.a.AnonymousClass1.onActivityStopped(android.app.Activity):void");
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityDestroyed(Activity activity) {
                a.this.b.remove(activity);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b() {
        LinkedList<Activity> linkedList = this.b;
        if (linkedList != null) {
            Iterator<Activity> it = linkedList.iterator();
            while (it.hasNext()) {
                it.next().finish();
            }
            this.b.clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean c() {
        return this.c;
    }
}
