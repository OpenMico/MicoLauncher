package io.reactivex.disposables;

import io.reactivex.annotations.NonNull;
import org.reactivestreams.Subscription;

/* compiled from: SubscriptionDisposable.java */
/* loaded from: classes4.dex */
final class e extends c<Subscription> {
    private static final long serialVersionUID = -707001650852963139L;

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(Subscription subscription) {
        super(subscription);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void a(@NonNull Subscription subscription) {
        subscription.cancel();
    }
}
