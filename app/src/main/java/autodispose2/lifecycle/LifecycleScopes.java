package autodispose2.lifecycle;

import autodispose2.AutoDisposePlugins;
import autodispose2.OutsideScopeException;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Predicate;
import java.util.Comparator;

/* loaded from: classes.dex */
public final class LifecycleScopes {
    private static final Comparator<Comparable<Object>> a = $$Lambda$rF8UpyPO5WNLy_kM5ijr_r552c.INSTANCE;

    private LifecycleScopes() {
        throw new InstantiationError();
    }

    public static <E> CompletableSource resolveScopeFromLifecycle(LifecycleScopeProvider<E> lifecycleScopeProvider) throws OutsideScopeException {
        return resolveScopeFromLifecycle((LifecycleScopeProvider) lifecycleScopeProvider, true);
    }

    public static <E> CompletableSource resolveScopeFromLifecycle(LifecycleScopeProvider<E> lifecycleScopeProvider, boolean z) throws OutsideScopeException {
        E peekLifecycle = lifecycleScopeProvider.peekLifecycle();
        CorrespondingEventsFunction<E> correspondingEvents = lifecycleScopeProvider.correspondingEvents();
        if (peekLifecycle != null) {
            try {
                return resolveScopeFromLifecycle(lifecycleScopeProvider.lifecycle(), correspondingEvents.apply(peekLifecycle));
            } catch (Exception e) {
                if (!z || !(e instanceof LifecycleEndedException)) {
                    return Completable.error(e);
                }
                Consumer<? super OutsideScopeException> outsideScopeHandler = AutoDisposePlugins.getOutsideScopeHandler();
                if (outsideScopeHandler != null) {
                    try {
                        outsideScopeHandler.accept((LifecycleEndedException) e);
                        return Completable.complete();
                    } catch (Throwable th) {
                        return Completable.error(th);
                    }
                } else {
                    throw e;
                }
            }
        } else {
            throw new LifecycleNotStartedException();
        }
    }

    public static <E> CompletableSource resolveScopeFromLifecycle(Observable<E> observable, E e) {
        return resolveScopeFromLifecycle(observable, e, e instanceof Comparable ? a : null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Comparator comparator, Object obj, Object obj2) throws Throwable {
        return comparator.compare(obj2, obj) >= 0;
    }

    public static <E> CompletableSource resolveScopeFromLifecycle(Observable<E> observable, final E e, @Nullable final Comparator<E> comparator) {
        Predicate<? super E> predicate;
        if (comparator != null) {
            predicate = new Predicate() { // from class: autodispose2.lifecycle.-$$Lambda$LifecycleScopes$ldC_gyoa_RTB469VFLZJjgLla2k
                @Override // io.reactivex.rxjava3.functions.Predicate
                public final boolean test(Object obj) {
                    boolean a2;
                    a2 = LifecycleScopes.a(comparator, e, obj);
                    return a2;
                }
            };
        } else {
            predicate = new Predicate() { // from class: autodispose2.lifecycle.-$$Lambda$LifecycleScopes$ALlYadOXa8qyvefGmh9T1nG--tQ
                @Override // io.reactivex.rxjava3.functions.Predicate
                public final boolean test(Object obj) {
                    boolean equals;
                    equals = obj.equals(e);
                    return equals;
                }
            };
        }
        return observable.skip(1L).takeUntil(predicate).ignoreElements();
    }
}
