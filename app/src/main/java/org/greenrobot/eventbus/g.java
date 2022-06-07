package org.greenrobot.eventbus;

/* compiled from: Subscription.java */
/* loaded from: classes5.dex */
public final class g {
    final Object a;
    final SubscriberMethod b;
    volatile boolean c = true;

    public g(Object obj, SubscriberMethod subscriberMethod) {
        this.a = obj;
        this.b = subscriberMethod;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof g)) {
            return false;
        }
        g gVar = (g) obj;
        return this.a == gVar.a && this.b.equals(gVar.b);
    }

    public int hashCode() {
        return this.a.hashCode() + this.b.f.hashCode();
    }
}
