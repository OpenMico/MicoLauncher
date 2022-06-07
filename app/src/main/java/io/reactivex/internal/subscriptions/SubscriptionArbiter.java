package io.reactivex.internal.subscriptions;

import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

/* loaded from: classes4.dex */
public class SubscriptionArbiter extends AtomicInteger implements Subscription {
    private static final long serialVersionUID = -2189523197179400958L;
    Subscription actual;
    final boolean cancelOnReplace;
    volatile boolean cancelled;
    long requested;
    protected boolean unbounded;
    final AtomicReference<Subscription> missedSubscription = new AtomicReference<>();
    final AtomicLong missedRequested = new AtomicLong();
    final AtomicLong missedProduced = new AtomicLong();

    public SubscriptionArbiter(boolean z) {
        this.cancelOnReplace = z;
    }

    public final void setSubscription(Subscription subscription) {
        if (this.cancelled) {
            subscription.cancel();
            return;
        }
        ObjectHelper.requireNonNull(subscription, "s is null");
        if (get() != 0 || !compareAndSet(0, 1)) {
            Subscription andSet = this.missedSubscription.getAndSet(subscription);
            if (andSet != null && this.cancelOnReplace) {
                andSet.cancel();
            }
            a();
            return;
        }
        Subscription subscription2 = this.actual;
        if (subscription2 != null && this.cancelOnReplace) {
            subscription2.cancel();
        }
        this.actual = subscription;
        long j = this.requested;
        if (decrementAndGet() != 0) {
            b();
        }
        if (j != 0) {
            subscription.request(j);
        }
    }

    @Override // org.reactivestreams.Subscription
    public final void request(long j) {
        if (SubscriptionHelper.validate(j) && !this.unbounded) {
            if (get() != 0 || !compareAndSet(0, 1)) {
                BackpressureHelper.add(this.missedRequested, j);
                a();
                return;
            }
            long j2 = this.requested;
            if (j2 != Long.MAX_VALUE) {
                long addCap = BackpressureHelper.addCap(j2, j);
                this.requested = addCap;
                if (addCap == Long.MAX_VALUE) {
                    this.unbounded = true;
                }
            }
            Subscription subscription = this.actual;
            if (decrementAndGet() != 0) {
                b();
            }
            if (subscription != null) {
                subscription.request(j);
            }
        }
    }

    public final void produced(long j) {
        if (!this.unbounded) {
            if (get() != 0 || !compareAndSet(0, 1)) {
                BackpressureHelper.add(this.missedProduced, j);
                a();
                return;
            }
            long j2 = this.requested;
            if (j2 != Long.MAX_VALUE) {
                long j3 = j2 - j;
                long j4 = 0;
                if (j3 < 0) {
                    SubscriptionHelper.reportMoreProduced(j3);
                } else {
                    j4 = j3;
                }
                this.requested = j4;
            }
            if (decrementAndGet() != 0) {
                b();
            }
        }
    }

    public void cancel() {
        if (!this.cancelled) {
            this.cancelled = true;
            a();
        }
    }

    final void a() {
        if (getAndIncrement() == 0) {
            b();
        }
    }

    final void b() {
        int i = 1;
        Subscription subscription = null;
        long j = 0;
        do {
            Subscription subscription2 = this.missedSubscription.get();
            if (subscription2 != null) {
                subscription2 = this.missedSubscription.getAndSet(null);
            }
            long j2 = this.missedRequested.get();
            if (j2 != 0) {
                j2 = this.missedRequested.getAndSet(0L);
            }
            long j3 = this.missedProduced.get();
            if (j3 != 0) {
                j3 = this.missedProduced.getAndSet(0L);
            }
            Subscription subscription3 = this.actual;
            if (this.cancelled) {
                if (subscription3 != null) {
                    subscription3.cancel();
                    this.actual = null;
                }
                if (subscription2 != null) {
                    subscription2.cancel();
                }
            } else {
                long j4 = this.requested;
                if (j4 != Long.MAX_VALUE) {
                    j4 = BackpressureHelper.addCap(j4, j2);
                    if (j4 != Long.MAX_VALUE) {
                        j4 -= j3;
                        if (j4 < 0) {
                            SubscriptionHelper.reportMoreProduced(j4);
                            j4 = 0;
                        }
                    }
                    this.requested = j4;
                }
                if (subscription2 != null) {
                    if (subscription3 != null && this.cancelOnReplace) {
                        subscription3.cancel();
                    }
                    this.actual = subscription2;
                    if (j4 != 0) {
                        j = BackpressureHelper.addCap(j, j4);
                        subscription = subscription2;
                    }
                } else if (!(subscription3 == null || j2 == 0)) {
                    j = BackpressureHelper.addCap(j, j2);
                    subscription = subscription3;
                }
            }
            i = addAndGet(-i);
        } while (i != 0);
        if (j != 0) {
            subscription.request(j);
        }
    }

    public final boolean isUnbounded() {
        return this.unbounded;
    }

    public final boolean isCancelled() {
        return this.cancelled;
    }
}
