package io.realm.internal;

import io.realm.OrderedCollectionChangeSet;
import io.realm.internal.sync.OsSubscription;
import javax.annotation.Nullable;

/* loaded from: classes5.dex */
public class EmptyLoadChangeSet extends OsCollectionChangeSet {
    private static final int[] a = new int[0];
    private static final OrderedCollectionChangeSet.Range[] b = new OrderedCollectionChangeSet.Range[0];

    public EmptyLoadChangeSet(@Nullable OsSubscription osSubscription, boolean z, boolean z2) {
        super(0L, z, osSubscription, z2);
    }

    public EmptyLoadChangeSet(@Nullable OsSubscription osSubscription, boolean z) {
        super(0L, true, osSubscription, z);
    }

    @Override // io.realm.internal.OsCollectionChangeSet, io.realm.OrderedCollectionChangeSet
    public OrderedCollectionChangeSet.State getState() {
        return OrderedCollectionChangeSet.State.INITIAL;
    }

    @Override // io.realm.internal.OsCollectionChangeSet, io.realm.OrderedCollectionChangeSet
    public int[] getDeletions() {
        return a;
    }

    @Override // io.realm.internal.OsCollectionChangeSet, io.realm.OrderedCollectionChangeSet
    public int[] getInsertions() {
        return a;
    }

    @Override // io.realm.internal.OsCollectionChangeSet, io.realm.OrderedCollectionChangeSet
    public int[] getChanges() {
        return a;
    }

    @Override // io.realm.internal.OsCollectionChangeSet, io.realm.OrderedCollectionChangeSet
    public OrderedCollectionChangeSet.Range[] getDeletionRanges() {
        return b;
    }

    @Override // io.realm.internal.OsCollectionChangeSet, io.realm.OrderedCollectionChangeSet
    public OrderedCollectionChangeSet.Range[] getInsertionRanges() {
        return b;
    }

    @Override // io.realm.internal.OsCollectionChangeSet, io.realm.OrderedCollectionChangeSet
    public OrderedCollectionChangeSet.Range[] getChangeRanges() {
        return b;
    }

    @Override // io.realm.internal.OsCollectionChangeSet, io.realm.OrderedCollectionChangeSet
    public Throwable getError() {
        if (this.subscription == null || this.subscription.getState() != OsSubscription.SubscriptionState.ERROR) {
            return null;
        }
        return this.subscription.getError();
    }

    @Override // io.realm.internal.OsCollectionChangeSet
    public boolean isRemoteDataLoaded() {
        return super.isRemoteDataLoaded();
    }

    @Override // io.realm.internal.OsCollectionChangeSet, io.realm.OrderedCollectionChangeSet
    public boolean isCompleteResult() {
        return isRemoteDataLoaded();
    }

    @Override // io.realm.internal.OsCollectionChangeSet
    public boolean isFirstAsyncCallback() {
        return super.isFirstAsyncCallback();
    }

    @Override // io.realm.internal.OsCollectionChangeSet
    public boolean isEmpty() {
        return this.subscription == null;
    }

    @Override // io.realm.internal.OsCollectionChangeSet
    public String toString() {
        return super.toString();
    }

    @Override // io.realm.internal.OsCollectionChangeSet, io.realm.internal.NativeObject
    public long getNativePtr() {
        return super.getNativePtr();
    }

    @Override // io.realm.internal.OsCollectionChangeSet, io.realm.internal.NativeObject
    public long getNativeFinalizerPtr() {
        return super.getNativeFinalizerPtr();
    }
}
