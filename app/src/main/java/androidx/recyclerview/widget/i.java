package androidx.recyclerview.widget;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Preconditions;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StableIdStorage;
import androidx.recyclerview.widget.ViewTypeStorage;

/* compiled from: NestedAdapterWrapper.java */
/* loaded from: classes.dex */
class i {
    public final RecyclerView.Adapter<RecyclerView.ViewHolder> a;
    final a b;
    int c;
    @NonNull
    private final ViewTypeStorage.ViewTypeLookup d;
    @NonNull
    private final StableIdStorage.StableIdLookup e;
    private RecyclerView.AdapterDataObserver f = new RecyclerView.AdapterDataObserver() { // from class: androidx.recyclerview.widget.i.1
        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onChanged() {
            i iVar = i.this;
            iVar.c = iVar.a.getItemCount();
            i.this.b.a(i.this);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeChanged(int i, int i2) {
            i.this.b.a(i.this, i, i2, null);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeChanged(int i, int i2, @Nullable Object obj) {
            i.this.b.a(i.this, i, i2, obj);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeInserted(int i, int i2) {
            i.this.c += i2;
            i.this.b.a(i.this, i, i2);
            if (i.this.c > 0 && i.this.a.getStateRestorationPolicy() == RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY) {
                i.this.b.b(i.this);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeRemoved(int i, int i2) {
            i.this.c -= i2;
            i.this.b.b(i.this, i, i2);
            if (i.this.c < 1 && i.this.a.getStateRestorationPolicy() == RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY) {
                i.this.b.b(i.this);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeMoved(int i, int i2, int i3) {
            boolean z = true;
            if (i3 != 1) {
                z = false;
            }
            Preconditions.checkArgument(z, "moving more than 1 item is not supported in RecyclerView");
            i.this.b.c(i.this, i, i2);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onStateRestorationPolicyChanged() {
            i.this.b.b(i.this);
        }
    };

    /* compiled from: NestedAdapterWrapper.java */
    /* loaded from: classes.dex */
    interface a {
        void a(@NonNull i iVar);

        void a(@NonNull i iVar, int i, int i2);

        void a(@NonNull i iVar, int i, int i2, @Nullable Object obj);

        void b(i iVar);

        void b(@NonNull i iVar, int i, int i2);

        void c(@NonNull i iVar, int i, int i2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public i(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter, a aVar, ViewTypeStorage viewTypeStorage, StableIdStorage.StableIdLookup stableIdLookup) {
        this.a = adapter;
        this.b = aVar;
        this.d = viewTypeStorage.createViewTypeWrapper(this);
        this.e = stableIdLookup;
        this.c = this.a.getItemCount();
        this.a.registerAdapterDataObserver(this.f);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a() {
        this.a.unregisterAdapterDataObserver(this.f);
        this.d.dispose();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int b() {
        return this.c;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int a(int i) {
        return this.d.localToGlobal(this.a.getItemViewType(i));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public RecyclerView.ViewHolder a(ViewGroup viewGroup, int i) {
        return this.a.onCreateViewHolder(viewGroup, this.d.globalToLocal(i));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(RecyclerView.ViewHolder viewHolder, int i) {
        this.a.bindViewHolder(viewHolder, i);
    }

    public long b(int i) {
        return this.e.localToGlobal(this.a.getItemId(i));
    }
}
