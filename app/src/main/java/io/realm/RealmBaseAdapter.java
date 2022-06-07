package io.realm;

import android.widget.BaseAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.realm.RealmModel;

/* loaded from: classes5.dex */
public abstract class RealmBaseAdapter<T extends RealmModel> extends BaseAdapter {
    private final RealmChangeListener<OrderedRealmCollection<T>> a;
    @Nullable
    protected OrderedRealmCollection<T> adapterData;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public RealmBaseAdapter(@Nullable OrderedRealmCollection<T> orderedRealmCollection) {
        if (orderedRealmCollection == null || orderedRealmCollection.isManaged()) {
            this.adapterData = orderedRealmCollection;
            this.a = (RealmChangeListener<OrderedRealmCollection<T>>) new RealmChangeListener<OrderedRealmCollection<T>>() { // from class: io.realm.RealmBaseAdapter.1
                /* renamed from: a */
                public void onChange(OrderedRealmCollection<T> orderedRealmCollection2) {
                    RealmBaseAdapter.this.notifyDataSetChanged();
                }
            };
            if (a()) {
                a(orderedRealmCollection);
                return;
            }
            return;
        }
        throw new IllegalStateException("Only use this adapter with managed list, for un-managed lists you can just use the BaseAdapter");
    }

    private void a(@NonNull OrderedRealmCollection<T> orderedRealmCollection) {
        if (orderedRealmCollection instanceof RealmResults) {
            ((RealmResults) orderedRealmCollection).addChangeListener(this.a);
        } else if (orderedRealmCollection instanceof RealmList) {
            ((RealmList) orderedRealmCollection).addChangeListener(this.a);
        } else {
            throw new IllegalArgumentException("RealmCollection not supported: " + orderedRealmCollection.getClass());
        }
    }

    private void b(@NonNull OrderedRealmCollection<T> orderedRealmCollection) {
        if (orderedRealmCollection instanceof RealmResults) {
            ((RealmResults) orderedRealmCollection).removeChangeListener(this.a);
        } else if (orderedRealmCollection instanceof RealmList) {
            ((RealmList) orderedRealmCollection).removeChangeListener(this.a);
        } else {
            throw new IllegalArgumentException("RealmCollection not supported: " + orderedRealmCollection.getClass());
        }
    }

    @Override // android.widget.Adapter
    public int getCount() {
        if (a()) {
            return this.adapterData.size();
        }
        return 0;
    }

    @Override // android.widget.Adapter
    @Nullable
    public T getItem(int i) {
        if (a()) {
            return this.adapterData.get(i);
        }
        return null;
    }

    public void updateData(@Nullable OrderedRealmCollection<T> orderedRealmCollection) {
        if (this.a != null) {
            if (a()) {
                b(this.adapterData);
            }
            if (orderedRealmCollection != null && orderedRealmCollection.isValid()) {
                a(orderedRealmCollection);
            }
        }
        this.adapterData = orderedRealmCollection;
        notifyDataSetChanged();
    }

    private boolean a() {
        OrderedRealmCollection<T> orderedRealmCollection = this.adapterData;
        return orderedRealmCollection != null && orderedRealmCollection.isValid();
    }
}
