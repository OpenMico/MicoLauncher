package autodispose2;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Supplier;

/* loaded from: classes.dex */
public final class Scopes {
    private Scopes() {
    }

    public static Completable completableOf(final ScopeProvider scopeProvider) {
        return Completable.defer(new Supplier() { // from class: autodispose2.-$$Lambda$Scopes$b9hzHoAwhLUl9ARSzH78OyWQ_vo
            @Override // io.reactivex.rxjava3.functions.Supplier
            public final Object get() {
                CompletableSource a;
                a = Scopes.a(ScopeProvider.this);
                return a;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ CompletableSource a(ScopeProvider scopeProvider) throws Throwable {
        try {
            return scopeProvider.requestScope();
        } catch (OutsideScopeException e) {
            Consumer<? super OutsideScopeException> outsideScopeHandler = AutoDisposePlugins.getOutsideScopeHandler();
            if (outsideScopeHandler == null) {
                return Completable.error(e);
            }
            outsideScopeHandler.accept(e);
            return Completable.complete();
        }
    }
}
