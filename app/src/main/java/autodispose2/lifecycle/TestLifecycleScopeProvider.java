package autodispose2.lifecycle;

import autodispose2.OutsideScopeException;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

/* loaded from: classes.dex */
public final class TestLifecycleScopeProvider implements LifecycleScopeProvider<TestLifecycle> {
    private final BehaviorSubject<TestLifecycle> a;

    /* loaded from: classes.dex */
    public enum TestLifecycle {
        STARTED,
        STOPPED
    }

    private TestLifecycleScopeProvider(@Nullable TestLifecycle testLifecycle) {
        if (testLifecycle == null) {
            this.a = BehaviorSubject.create();
        } else {
            this.a = BehaviorSubject.createDefault(testLifecycle);
        }
    }

    public static TestLifecycleScopeProvider create() {
        return new TestLifecycleScopeProvider(null);
    }

    public static TestLifecycleScopeProvider createInitial(TestLifecycle testLifecycle) {
        return new TestLifecycleScopeProvider(testLifecycle);
    }

    @Override // autodispose2.lifecycle.LifecycleScopeProvider
    public Observable<TestLifecycle> lifecycle() {
        return this.a.hide();
    }

    @Override // autodispose2.lifecycle.LifecycleScopeProvider
    public CorrespondingEventsFunction<TestLifecycle> correspondingEvents() {
        return $$Lambda$TestLifecycleScopeProvider$X85VxIDdra34zjFKi_R5D4_Vvt0.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ TestLifecycle a(TestLifecycle testLifecycle) throws OutsideScopeException {
        switch (testLifecycle) {
            case STARTED:
                return TestLifecycle.STOPPED;
            case STOPPED:
                throw new LifecycleEndedException();
            default:
                throw new IllegalStateException("Unknown lifecycle event.");
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // autodispose2.lifecycle.LifecycleScopeProvider
    public TestLifecycle peekLifecycle() {
        return this.a.getValue();
    }

    @Override // autodispose2.lifecycle.LifecycleScopeProvider, autodispose2.ScopeProvider
    public CompletableSource requestScope() {
        return LifecycleScopes.resolveScopeFromLifecycle(this);
    }

    public void start() {
        this.a.onNext(TestLifecycle.STARTED);
    }

    public void stop() {
        if (this.a.getValue() == TestLifecycle.STARTED) {
            this.a.onNext(TestLifecycle.STOPPED);
            return;
        }
        throw new IllegalStateException("Attempting to stop lifecycle before starting it.");
    }
}
