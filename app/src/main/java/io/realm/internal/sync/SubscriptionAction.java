package io.realm.internal.sync;

/* loaded from: classes5.dex */
public class SubscriptionAction {
    private final String a;
    private final long b;
    private final boolean c;
    public static final SubscriptionAction NO_SUBSCRIPTION = new SubscriptionAction(null, 0, false);
    public static final SubscriptionAction ANONYMOUS_SUBSCRIPTION = new SubscriptionAction("", Long.MAX_VALUE, false);

    public static SubscriptionAction create(String str, long j) {
        return new SubscriptionAction(str, j, false);
    }

    public static SubscriptionAction update(String str, long j) {
        return new SubscriptionAction(str, j, true);
    }

    public SubscriptionAction(String str, long j, boolean z) {
        this.a = str;
        this.b = j;
        this.c = z;
    }

    public boolean shouldCreateSubscriptions() {
        return this.a != null;
    }

    public String getName() {
        return this.a;
    }

    public long getTimeToLiveMs() {
        return this.b;
    }

    public boolean isUpdate() {
        return this.c;
    }
}
