package io.realm.rx;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollection;
import javax.annotation.Nullable;

/* loaded from: classes5.dex */
public class CollectionChange<E extends OrderedRealmCollection> {
    private final E a;
    private final OrderedCollectionChangeSet b;

    public CollectionChange(E e, @Nullable OrderedCollectionChangeSet orderedCollectionChangeSet) {
        this.a = e;
        this.b = orderedCollectionChangeSet;
    }

    public E getCollection() {
        return this.a;
    }

    @Nullable
    public OrderedCollectionChangeSet getChangeset() {
        return this.b;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CollectionChange collectionChange = (CollectionChange) obj;
        if (!this.a.equals(collectionChange.a)) {
            return false;
        }
        OrderedCollectionChangeSet orderedCollectionChangeSet = this.b;
        return orderedCollectionChangeSet != null ? orderedCollectionChangeSet.equals(collectionChange.b) : collectionChange.b == null;
    }

    public int hashCode() {
        int hashCode = this.a.hashCode() * 31;
        OrderedCollectionChangeSet orderedCollectionChangeSet = this.b;
        return hashCode + (orderedCollectionChangeSet != null ? orderedCollectionChangeSet.hashCode() : 0);
    }
}
