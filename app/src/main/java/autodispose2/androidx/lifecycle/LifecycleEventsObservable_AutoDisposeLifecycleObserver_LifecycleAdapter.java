package autodispose2.androidx.lifecycle;

import androidx.lifecycle.GeneratedAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MethodCallsLogger;
import autodispose2.androidx.lifecycle.LifecycleEventsObservable;

/* loaded from: classes.dex */
public class LifecycleEventsObservable_AutoDisposeLifecycleObserver_LifecycleAdapter implements GeneratedAdapter {
    final LifecycleEventsObservable.AutoDisposeLifecycleObserver a;

    LifecycleEventsObservable_AutoDisposeLifecycleObserver_LifecycleAdapter(LifecycleEventsObservable.AutoDisposeLifecycleObserver autoDisposeLifecycleObserver) {
        this.a = autoDisposeLifecycleObserver;
    }

    @Override // androidx.lifecycle.GeneratedAdapter
    public void callMethods(LifecycleOwner lifecycleOwner, Lifecycle.Event event, boolean z, MethodCallsLogger methodCallsLogger) {
        boolean z2 = methodCallsLogger != null;
        if (!z) {
            return;
        }
        if (!z2 || methodCallsLogger.approveCall("onStateChange", 4)) {
            this.a.onStateChange(lifecycleOwner, event);
        }
    }
}
