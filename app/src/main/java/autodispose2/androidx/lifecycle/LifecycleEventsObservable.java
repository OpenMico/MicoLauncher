package autodispose2.androidx.lifecycle;

import androidx.annotation.RestrictTo;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import autodispose2.android.internal.AutoDisposeAndroidUtil;
import autodispose2.android.internal.MainThreadDisposable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

/* JADX INFO: Access modifiers changed from: package-private */
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class LifecycleEventsObservable extends Observable<Lifecycle.Event> {
    private final Lifecycle a;
    private final BehaviorSubject<Lifecycle.Event> b = BehaviorSubject.create();

    /* JADX INFO: Access modifiers changed from: package-private */
    public LifecycleEventsObservable(Lifecycle lifecycle) {
        this.a = lifecycle;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Lifecycle.Event a() {
        return this.b.getValue();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b() {
        Lifecycle.Event event;
        switch (this.a.getCurrentState()) {
            case INITIALIZED:
                event = Lifecycle.Event.ON_CREATE;
                break;
            case CREATED:
                event = Lifecycle.Event.ON_START;
                break;
            case STARTED:
            case RESUMED:
                event = Lifecycle.Event.ON_RESUME;
                break;
            default:
                event = Lifecycle.Event.ON_DESTROY;
                break;
        }
        this.b.onNext(event);
    }

    @Override // io.reactivex.rxjava3.core.Observable
    protected void subscribeActual(Observer<? super Lifecycle.Event> observer) {
        AutoDisposeLifecycleObserver autoDisposeLifecycleObserver = new AutoDisposeLifecycleObserver(this.a, observer, this.b);
        observer.onSubscribe(autoDisposeLifecycleObserver);
        if (!AutoDisposeAndroidUtil.isMainThread()) {
            observer.onError(new IllegalStateException("Lifecycles can only be bound to on the main thread!"));
            return;
        }
        this.a.addObserver(autoDisposeLifecycleObserver);
        if (autoDisposeLifecycleObserver.isDisposed()) {
            this.a.removeObserver(autoDisposeLifecycleObserver);
        }
    }

    /* loaded from: classes.dex */
    static final class AutoDisposeLifecycleObserver extends MainThreadDisposable implements LifecycleObserver {
        private final Lifecycle a;
        private final Observer<? super Lifecycle.Event> b;
        private final BehaviorSubject<Lifecycle.Event> c;

        AutoDisposeLifecycleObserver(Lifecycle lifecycle, Observer<? super Lifecycle.Event> observer, BehaviorSubject<Lifecycle.Event> behaviorSubject) {
            this.a = lifecycle;
            this.b = observer;
            this.c = behaviorSubject;
        }

        @Override // autodispose2.android.internal.MainThreadDisposable
        protected void onDispose() {
            this.a.removeObserver(this);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
        public void onStateChange(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            if (!isDisposed()) {
                if (!(event == Lifecycle.Event.ON_CREATE && this.c.getValue() == event)) {
                    this.c.onNext(event);
                }
                this.b.onNext(event);
            }
        }
    }
}
