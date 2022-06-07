package io.realm.internal;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.RealmChangeListener;
import io.realm.internal.ObserverPairList;
import javax.annotation.Nullable;

@Keep
/* loaded from: classes5.dex */
interface ObservableCollection {
    void notifyChangeListeners(long j);

    /* loaded from: classes5.dex */
    public static class CollectionObserverPair<T> extends ObserverPairList.ObserverPair<T, Object> {
        public CollectionObserverPair(T t, Object obj) {
            super(t, obj);
        }

        public void onChange(T t, OsCollectionChangeSet osCollectionChangeSet) {
            if (this.listener instanceof OrderedRealmCollectionChangeListener) {
                ((OrderedRealmCollectionChangeListener) this.listener).onChange(t, new StatefulCollectionChangeSet(osCollectionChangeSet));
            } else if (this.listener instanceof RealmChangeListener) {
                ((RealmChangeListener) this.listener).onChange(t);
            } else {
                throw new RuntimeException("Unsupported listener type: " + this.listener);
            }
        }
    }

    /* loaded from: classes5.dex */
    public static class RealmChangeListenerWrapper<T> implements OrderedRealmCollectionChangeListener<T> {
        private final RealmChangeListener<T> a;

        public RealmChangeListenerWrapper(RealmChangeListener<T> realmChangeListener) {
            this.a = realmChangeListener;
        }

        @Override // io.realm.OrderedRealmCollectionChangeListener
        public void onChange(T t, @Nullable OrderedCollectionChangeSet orderedCollectionChangeSet) {
            this.a.onChange(t);
        }

        public boolean equals(Object obj) {
            return (obj instanceof RealmChangeListenerWrapper) && this.a == ((RealmChangeListenerWrapper) obj).a;
        }

        public int hashCode() {
            return this.a.hashCode();
        }
    }

    /* loaded from: classes5.dex */
    public static class Callback implements ObserverPairList.Callback<CollectionObserverPair> {
        private final OsCollectionChangeSet a;

        public Callback(OsCollectionChangeSet osCollectionChangeSet) {
            this.a = osCollectionChangeSet;
        }

        public void onCalled(CollectionObserverPair collectionObserverPair, Object obj) {
            collectionObserverPair.onChange(obj, this.a);
        }
    }
}
