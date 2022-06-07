package io.reactivex.rxjava3.disposables;

import io.reactivex.rxjava3.annotations.NonNull;
import org.reactivestreams.Subscription;

/* compiled from: SubscriptionDisposable.java */
/* loaded from: classes4.dex */
final class f extends d<Subscription> {
    private static final long serialVersionUID = -707001650852963139L;

    /* JADX INFO: Access modifiers changed from: package-private */
    public f(Subscription subscription) {
        super(subscription);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void a(@NonNull Subscription subscription) {
        subscription.cancel();
    }
}
