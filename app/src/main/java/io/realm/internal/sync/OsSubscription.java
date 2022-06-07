package io.realm.internal.sync;

import io.realm.RealmChangeListener;
import io.realm.internal.KeepMember;
import io.realm.internal.NativeObject;
import io.realm.internal.ObserverPairList;
import io.realm.internal.OsResults;
import javax.annotation.Nullable;

@KeepMember
/* loaded from: classes5.dex */
public class OsSubscription implements NativeObject {
    private static final long a = nativeGetFinalizerPtr();
    private final long b;
    protected final ObserverPairList<b> observerPairs = new ObserverPairList<>();

    private static native long nativeCreateOrUpdate(long j, String str, long j2, boolean z);

    private static native Object nativeGetError(long j);

    private static native long nativeGetFinalizerPtr();

    private static native int nativeGetState(long j);

    private native void nativeStartListening(long j);

    private native void nativeStopListening(long j);

    /* loaded from: classes5.dex */
    public enum SubscriptionState {
        ERROR(-1),
        CREATING(2),
        PENDING(0),
        COMPLETE(1),
        INVALIDATED(3);
        
        private final int val;

        SubscriptionState(int i) {
            this.val = i;
        }

        public static SubscriptionState fromInternalValue(int i) {
            SubscriptionState[] values = values();
            for (SubscriptionState subscriptionState : values) {
                if (subscriptionState.val == i) {
                    return subscriptionState;
                }
            }
            throw new IllegalArgumentException("Unknown value: " + i);
        }
    }

    /* loaded from: classes5.dex */
    public static class b extends ObserverPairList.ObserverPair<OsSubscription, RealmChangeListener<OsSubscription>> {
        public b(OsSubscription osSubscription, RealmChangeListener<OsSubscription> realmChangeListener) {
            super(osSubscription, realmChangeListener);
        }

        public void a(OsSubscription osSubscription) {
            ((RealmChangeListener) this.listener).onChange(osSubscription);
        }
    }

    /* loaded from: classes5.dex */
    private static class a implements ObserverPairList.Callback<b> {
        private a() {
        }

        /* renamed from: a */
        public void onCalled(b bVar, Object obj) {
            bVar.a((OsSubscription) obj);
        }
    }

    public OsSubscription(OsResults osResults, SubscriptionAction subscriptionAction) {
        this.b = nativeCreateOrUpdate(osResults.getNativePtr(), subscriptionAction.getName(), subscriptionAction.getTimeToLiveMs(), subscriptionAction.isUpdate());
    }

    @Override // io.realm.internal.NativeObject
    public long getNativePtr() {
        return this.b;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativeFinalizerPtr() {
        return a;
    }

    public SubscriptionState getState() {
        return SubscriptionState.fromInternalValue(nativeGetState(this.b));
    }

    @Nullable
    public Throwable getError() {
        return (Throwable) nativeGetError(this.b);
    }

    public void addChangeListener(RealmChangeListener<OsSubscription> realmChangeListener) {
        if (this.observerPairs.isEmpty()) {
            nativeStartListening(this.b);
        }
        this.observerPairs.add(new b(this, realmChangeListener));
    }

    public void removeChangeListener(RealmChangeListener<OsSubscription> realmChangeListener) {
        this.observerPairs.remove(this, realmChangeListener);
        if (this.observerPairs.isEmpty()) {
            nativeStopListening(this.b);
        }
    }

    @KeepMember
    private void notifyChangeListeners() {
        this.observerPairs.foreach(new a());
    }
}
