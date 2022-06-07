package io.realm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import io.realm.OrderedCollectionChangeSet;
import io.realm.RealmModel;

/* loaded from: classes5.dex */
public abstract class RealmRecyclerViewAdapter<T extends RealmModel, S extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<S> {
    private final boolean a;
    private final boolean b;
    private final OrderedRealmCollectionChangeListener c;
    @Nullable
    private OrderedRealmCollection<T> d;

    private OrderedRealmCollectionChangeListener a() {
        return new OrderedRealmCollectionChangeListener() { // from class: io.realm.RealmRecyclerViewAdapter.1
            @Override // io.realm.OrderedRealmCollectionChangeListener
            public void onChange(Object obj, OrderedCollectionChangeSet orderedCollectionChangeSet) {
                if (orderedCollectionChangeSet.getState() == OrderedCollectionChangeSet.State.INITIAL) {
                    RealmRecyclerViewAdapter.this.notifyDataSetChanged();
                    return;
                }
                OrderedCollectionChangeSet.Range[] deletionRanges = orderedCollectionChangeSet.getDeletionRanges();
                for (int length = deletionRanges.length - 1; length >= 0; length--) {
                    OrderedCollectionChangeSet.Range range = deletionRanges[length];
                    RealmRecyclerViewAdapter.this.notifyItemRangeRemoved(range.startIndex, range.length);
                }
                OrderedCollectionChangeSet.Range[] insertionRanges = orderedCollectionChangeSet.getInsertionRanges();
                for (OrderedCollectionChangeSet.Range range2 : insertionRanges) {
                    RealmRecyclerViewAdapter.this.notifyItemRangeInserted(range2.startIndex, range2.length);
                }
                if (RealmRecyclerViewAdapter.this.b) {
                    OrderedCollectionChangeSet.Range[] changeRanges = orderedCollectionChangeSet.getChangeRanges();
                    for (OrderedCollectionChangeSet.Range range3 : changeRanges) {
                        RealmRecyclerViewAdapter.this.notifyItemRangeChanged(range3.startIndex, range3.length);
                    }
                }
            }
        };
    }

    public RealmRecyclerViewAdapter(@Nullable OrderedRealmCollection<T> orderedRealmCollection, boolean z) {
        this(orderedRealmCollection, z, true);
    }

    public RealmRecyclerViewAdapter(@Nullable OrderedRealmCollection<T> orderedRealmCollection, boolean z, boolean z2) {
        if (orderedRealmCollection == null || orderedRealmCollection.isManaged()) {
            this.d = orderedRealmCollection;
            this.a = z;
            this.c = this.a ? a() : null;
            this.b = z2;
            return;
        }
        throw new IllegalStateException("Only use this adapter with managed RealmCollection, for un-managed lists you can just use the BaseRecyclerViewAdapter");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (this.a && b()) {
            a(this.d);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        if (this.a && b()) {
            b(this.d);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        if (b()) {
            return this.d.size();
        }
        return 0;
    }

    @Nullable
    public T getItem(int i) {
        if (i >= 0) {
            OrderedRealmCollection<T> orderedRealmCollection = this.d;
            if ((orderedRealmCollection == null || i < orderedRealmCollection.size()) && b()) {
                return this.d.get(i);
            }
            return null;
        }
        throw new IllegalArgumentException("Only indexes >= 0 are allowed. Input was: " + i);
    }

    @Nullable
    public OrderedRealmCollection<T> getData() {
        return this.d;
    }

    public void updateData(@Nullable OrderedRealmCollection<T> orderedRealmCollection) {
        if (this.a) {
            if (b()) {
                b(this.d);
            }
            if (orderedRealmCollection != null) {
                a(orderedRealmCollection);
            }
        }
        this.d = orderedRealmCollection;
        notifyDataSetChanged();
    }

    private void a(@NonNull OrderedRealmCollection<T> orderedRealmCollection) {
        if (orderedRealmCollection instanceof RealmResults) {
            ((RealmResults) orderedRealmCollection).addChangeListener(this.c);
        } else if (orderedRealmCollection instanceof RealmList) {
            ((RealmList) orderedRealmCollection).addChangeListener(this.c);
        } else {
            throw new IllegalArgumentException("RealmCollection not supported: " + orderedRealmCollection.getClass());
        }
    }

    private void b(@NonNull OrderedRealmCollection<T> orderedRealmCollection) {
        if (orderedRealmCollection instanceof RealmResults) {
            ((RealmResults) orderedRealmCollection).removeChangeListener(this.c);
        } else if (orderedRealmCollection instanceof RealmList) {
            ((RealmList) orderedRealmCollection).removeChangeListener(this.c);
        } else {
            throw new IllegalArgumentException("RealmCollection not supported: " + orderedRealmCollection.getClass());
        }
    }

    private boolean b() {
        OrderedRealmCollection<T> orderedRealmCollection = this.d;
        return orderedRealmCollection != null && orderedRealmCollection.isValid();
    }
}
