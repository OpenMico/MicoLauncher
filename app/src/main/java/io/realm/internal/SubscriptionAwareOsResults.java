package io.realm.internal;

import io.realm.RealmChangeListener;
import io.realm.internal.ObservableCollection;
import io.realm.internal.core.DescriptorOrdering;
import io.realm.internal.sync.OsSubscription;
import io.realm.internal.sync.SubscriptionAction;

/* loaded from: classes5.dex */
public class SubscriptionAwareOsResults extends OsResults {
    private boolean b;
    private OsSubscription c;
    private long a = 0;
    private boolean d = false;
    private boolean e = true;

    public static SubscriptionAwareOsResults createFromQuery(OsSharedRealm osSharedRealm, TableQuery tableQuery, DescriptorOrdering descriptorOrdering, SubscriptionAction subscriptionAction) {
        tableQuery.a();
        return new SubscriptionAwareOsResults(osSharedRealm, tableQuery.getTable(), nativeCreateResults(osSharedRealm.getNativePtr(), tableQuery.getNativePtr(), descriptorOrdering.getNativePtr()), subscriptionAction);
    }

    SubscriptionAwareOsResults(OsSharedRealm osSharedRealm, Table table, long j, SubscriptionAction subscriptionAction) {
        super(osSharedRealm, table, j);
        this.c = null;
        this.c = new OsSubscription(this, subscriptionAction);
        this.c.addChangeListener(new RealmChangeListener<OsSubscription>() { // from class: io.realm.internal.SubscriptionAwareOsResults.1
            /* renamed from: a */
            public void onChange(OsSubscription osSubscription) {
                SubscriptionAwareOsResults.this.b = true;
            }
        });
        RealmNotifier realmNotifier = osSharedRealm.realmNotifier;
        realmNotifier.addBeginSendingNotificationsCallback(new Runnable() { // from class: io.realm.internal.SubscriptionAwareOsResults.2
            @Override // java.lang.Runnable
            public void run() {
                SubscriptionAwareOsResults.this.b = false;
                SubscriptionAwareOsResults.this.d = false;
                SubscriptionAwareOsResults.this.a = 0L;
            }
        });
        realmNotifier.addFinishedSendingNotificationsCallback(new Runnable() { // from class: io.realm.internal.SubscriptionAwareOsResults.3
            @Override // java.lang.Runnable
            public void run() {
                if (SubscriptionAwareOsResults.this.d || SubscriptionAwareOsResults.this.b) {
                    SubscriptionAwareOsResults.this.a();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        OsCollectionChangeSet osCollectionChangeSet;
        OsSubscription osSubscription = this.b ? this.c : null;
        if (this.a != 0 || osSubscription == null || this.e || osSubscription.getState() == OsSubscription.SubscriptionState.ERROR || osSubscription.getState() == OsSubscription.SubscriptionState.COMPLETE) {
            long j = this.a;
            if (j == 0) {
                osCollectionChangeSet = new EmptyLoadChangeSet(osSubscription, this.e, true);
            } else {
                osCollectionChangeSet = new OsCollectionChangeSet(j, this.e, osSubscription, true);
            }
            if (!osCollectionChangeSet.isEmpty() || !isLoaded()) {
                this.loaded = true;
                this.e = false;
                this.observerPairs.foreach(new ObservableCollection.Callback(osCollectionChangeSet));
            }
        }
    }

    @Override // io.realm.internal.OsResults, io.realm.internal.ObservableCollection
    public void notifyChangeListeners(long j) {
        this.d = true;
        this.a = j;
    }
}
