package autodispose2;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.subjects.CompletableSubject;

/* loaded from: classes.dex */
public final class TestScopeProvider implements ScopeProvider {
    private final CompletableSubject a = CompletableSubject.create();

    public static TestScopeProvider create() {
        return create(CompletableSubject.create());
    }

    public static TestScopeProvider create(Completable completable) {
        return new TestScopeProvider(completable);
    }

    private TestScopeProvider(Completable completable) {
        completable.subscribe(this.a);
    }

    @Override // autodispose2.ScopeProvider
    public CompletableSource requestScope() {
        return this.a;
    }

    public void emit() {
        this.a.onComplete();
    }
}
