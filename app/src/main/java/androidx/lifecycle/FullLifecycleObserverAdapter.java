package androidx.lifecycle;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class FullLifecycleObserverAdapter implements LifecycleEventObserver {
    private final FullLifecycleObserver mFullLifecycleObserver;
    private final LifecycleEventObserver mLifecycleEventObserver;

    /* JADX INFO: Access modifiers changed from: package-private */
    public FullLifecycleObserverAdapter(FullLifecycleObserver fullLifecycleObserver, LifecycleEventObserver lifecycleEventObserver) {
        this.mFullLifecycleObserver = fullLifecycleObserver;
        this.mLifecycleEventObserver = lifecycleEventObserver;
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner, @NonNull Lifecycle.Event event) {
        switch (event) {
            case ON_CREATE:
                this.mFullLifecycleObserver.onCreate(lifecycleOwner);
                break;
            case ON_START:
                this.mFullLifecycleObserver.onStart(lifecycleOwner);
                break;
            case ON_RESUME:
                this.mFullLifecycleObserver.onResume(lifecycleOwner);
                break;
            case ON_PAUSE:
                this.mFullLifecycleObserver.onPause(lifecycleOwner);
                break;
            case ON_STOP:
                this.mFullLifecycleObserver.onStop(lifecycleOwner);
                break;
            case ON_DESTROY:
                this.mFullLifecycleObserver.onDestroy(lifecycleOwner);
                break;
            case ON_ANY:
                throw new IllegalArgumentException("ON_ANY must not been send by anybody");
        }
        LifecycleEventObserver lifecycleEventObserver = this.mLifecycleEventObserver;
        if (lifecycleEventObserver != null) {
            lifecycleEventObserver.onStateChanged(lifecycleOwner, event);
        }
    }
}
