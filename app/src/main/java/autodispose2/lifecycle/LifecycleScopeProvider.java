package autodispose2.lifecycle;

import autodispose2.ScopeProvider;
import autodispose2.internal.DoNotMock;
import io.reactivex.rxjava3.annotations.CheckReturnValue;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Observable;

@DoNotMock("Use TestLifecycleScopeProvider instead")
/* loaded from: classes.dex */
public interface LifecycleScopeProvider<E> extends ScopeProvider {
    @CheckReturnValue
    CorrespondingEventsFunction<E> correspondingEvents();

    @CheckReturnValue
    Observable<E> lifecycle();

    @Nullable
    E peekLifecycle();

    @Override // autodispose2.ScopeProvider
    default CompletableSource requestScope() {
        return LifecycleScopes.resolveScopeFromLifecycle(this);
    }
}
