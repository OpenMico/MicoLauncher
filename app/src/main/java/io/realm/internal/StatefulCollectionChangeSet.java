package io.realm.internal;

import io.realm.OrderedCollectionChangeSet;
import javax.annotation.Nullable;

/* loaded from: classes5.dex */
public class StatefulCollectionChangeSet implements OrderedCollectionChangeSet {
    private final OrderedCollectionChangeSet a;
    private final Throwable b;
    private final OrderedCollectionChangeSet.State c;
    private final boolean d;

    public StatefulCollectionChangeSet(OsCollectionChangeSet osCollectionChangeSet) {
        this.a = osCollectionChangeSet;
        boolean isFirstAsyncCallback = osCollectionChangeSet.isFirstAsyncCallback();
        this.d = osCollectionChangeSet.isRemoteDataLoaded();
        this.b = osCollectionChangeSet.getError();
        if (this.b != null) {
            this.c = OrderedCollectionChangeSet.State.ERROR;
        } else {
            this.c = isFirstAsyncCallback ? OrderedCollectionChangeSet.State.INITIAL : OrderedCollectionChangeSet.State.UPDATE;
        }
    }

    @Override // io.realm.OrderedCollectionChangeSet
    public OrderedCollectionChangeSet.State getState() {
        return this.c;
    }

    @Override // io.realm.OrderedCollectionChangeSet
    public int[] getDeletions() {
        return this.a.getDeletions();
    }

    @Override // io.realm.OrderedCollectionChangeSet
    public int[] getInsertions() {
        return this.a.getInsertions();
    }

    @Override // io.realm.OrderedCollectionChangeSet
    public int[] getChanges() {
        return this.a.getChanges();
    }

    @Override // io.realm.OrderedCollectionChangeSet
    public OrderedCollectionChangeSet.Range[] getDeletionRanges() {
        return this.a.getDeletionRanges();
    }

    @Override // io.realm.OrderedCollectionChangeSet
    public OrderedCollectionChangeSet.Range[] getInsertionRanges() {
        return this.a.getInsertionRanges();
    }

    @Override // io.realm.OrderedCollectionChangeSet
    public OrderedCollectionChangeSet.Range[] getChangeRanges() {
        return this.a.getChangeRanges();
    }

    @Override // io.realm.OrderedCollectionChangeSet
    @Nullable
    public Throwable getError() {
        return this.b;
    }

    @Override // io.realm.OrderedCollectionChangeSet
    public boolean isCompleteResult() {
        return this.d;
    }
}
