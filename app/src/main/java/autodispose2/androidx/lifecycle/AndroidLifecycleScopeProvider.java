package autodispose2.androidx.lifecycle;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import autodispose2.OutsideScopeException;
import autodispose2.lifecycle.CorrespondingEventsFunction;
import autodispose2.lifecycle.LifecycleEndedException;
import autodispose2.lifecycle.LifecycleScopeProvider;
import autodispose2.lifecycle.LifecycleScopes;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Observable;

/* loaded from: classes.dex */
public final class AndroidLifecycleScopeProvider implements LifecycleScopeProvider<Lifecycle.Event> {
    private static final CorrespondingEventsFunction<Lifecycle.Event> a = $$Lambda$AndroidLifecycleScopeProvider$yL5a24_jPCPcOByvUo3BF1e80k.INSTANCE;
    private final CorrespondingEventsFunction<Lifecycle.Event> b;
    private final LifecycleEventsObservable c;

    public static /* synthetic */ Lifecycle.Event a(Lifecycle.Event event) throws OutsideScopeException {
        switch (event) {
            case ON_CREATE:
                return Lifecycle.Event.ON_DESTROY;
            case ON_START:
                return Lifecycle.Event.ON_STOP;
            case ON_RESUME:
                return Lifecycle.Event.ON_PAUSE;
            case ON_PAUSE:
                return Lifecycle.Event.ON_STOP;
            default:
                throw new LifecycleEndedException("Lifecycle has ended! Last event was " + event);
        }
    }

    public static AndroidLifecycleScopeProvider from(LifecycleOwner lifecycleOwner) {
        return from(lifecycleOwner.getLifecycle());
    }

    public static AndroidLifecycleScopeProvider from(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        return from(lifecycleOwner.getLifecycle(), event);
    }

    public static AndroidLifecycleScopeProvider from(Lifecycle lifecycle) {
        return from(lifecycle, a);
    }

    public static AndroidLifecycleScopeProvider from(Lifecycle lifecycle, Lifecycle.Event event) {
        return from(lifecycle, new a(event));
    }

    public static AndroidLifecycleScopeProvider from(LifecycleOwner lifecycleOwner, CorrespondingEventsFunction<Lifecycle.Event> correspondingEventsFunction) {
        return from(lifecycleOwner.getLifecycle(), correspondingEventsFunction);
    }

    public static AndroidLifecycleScopeProvider from(Lifecycle lifecycle, CorrespondingEventsFunction<Lifecycle.Event> correspondingEventsFunction) {
        return new AndroidLifecycleScopeProvider(lifecycle, correspondingEventsFunction);
    }

    private AndroidLifecycleScopeProvider(Lifecycle lifecycle, CorrespondingEventsFunction<Lifecycle.Event> correspondingEventsFunction) {
        this.c = new LifecycleEventsObservable(lifecycle);
        this.b = correspondingEventsFunction;
    }

    @Override // autodispose2.lifecycle.LifecycleScopeProvider
    public Observable<Lifecycle.Event> lifecycle() {
        return this.c;
    }

    @Override // autodispose2.lifecycle.LifecycleScopeProvider
    public CorrespondingEventsFunction<Lifecycle.Event> correspondingEvents() {
        return this.b;
    }

    @Override // autodispose2.lifecycle.LifecycleScopeProvider
    public Lifecycle.Event peekLifecycle() {
        this.c.b();
        return this.c.a();
    }

    @Override // autodispose2.lifecycle.LifecycleScopeProvider, autodispose2.ScopeProvider
    public CompletableSource requestScope() {
        return LifecycleScopes.resolveScopeFromLifecycle(this);
    }

    /* loaded from: classes.dex */
    public static class a implements CorrespondingEventsFunction<Lifecycle.Event> {
        private final Lifecycle.Event a;

        a(Lifecycle.Event event) {
            this.a = event;
        }

        /* renamed from: a */
        public Lifecycle.Event apply(Lifecycle.Event event) throws OutsideScopeException {
            return this.a;
        }
    }
}
