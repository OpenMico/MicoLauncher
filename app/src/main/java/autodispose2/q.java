package autodispose2;

import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

/* compiled from: AutoSubscriptionHelper.java */
/* loaded from: classes.dex */
enum q implements Subscription {
    CANCELLED;

    @Override // org.reactivestreams.Subscription
    public void cancel() {
    }

    @Override // org.reactivestreams.Subscription
    public void request(long j) {
    }

    static void a() {
        RxJavaPlugins.onError(new IllegalStateException("Subscription already set!"));
    }

    static boolean a(long j) {
        if (j > 0) {
            return true;
        }
        RxJavaPlugins.onError(new IllegalArgumentException("n > 0 required but it was " + j));
        return false;
    }

    static boolean a(AtomicReference<Subscription> atomicReference, Subscription subscription) {
        k.a(subscription, "s is null");
        if (atomicReference.compareAndSet(null, subscription)) {
            return true;
        }
        subscription.cancel();
        if (atomicReference.get() == CANCELLED) {
            return false;
        }
        a();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(AtomicReference<Subscription> atomicReference) {
        Subscription andSet;
        Subscription subscription = atomicReference.get();
        q qVar = CANCELLED;
        if (subscription == qVar || (andSet = atomicReference.getAndSet(qVar)) == CANCELLED) {
            return false;
        }
        if (andSet == null) {
            return true;
        }
        andSet.cancel();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(AtomicReference<Subscription> atomicReference, AtomicLong atomicLong, Subscription subscription) {
        if (!a(atomicReference, subscription)) {
            return false;
        }
        long andSet = atomicLong.getAndSet(0L);
        if (andSet == 0) {
            return true;
        }
        subscription.request(andSet);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(AtomicReference<Subscription> atomicReference, AtomicLong atomicLong, long j) {
        Subscription subscription = atomicReference.get();
        if (subscription != null) {
            subscription.request(j);
        } else if (a(j)) {
            c.a(atomicLong, j);
            Subscription subscription2 = atomicReference.get();
            if (subscription2 != null) {
                long andSet = atomicLong.getAndSet(0L);
                if (andSet != 0) {
                    subscription2.request(andSet);
                }
            }
        }
    }
}
