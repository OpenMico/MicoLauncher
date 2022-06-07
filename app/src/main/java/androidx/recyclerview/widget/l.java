package androidx.recyclerview.widget;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.collection.LongSparseArray;
import androidx.collection.SimpleArrayMap;
import androidx.core.util.Pools;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: ViewInfoStore.java */
/* loaded from: classes.dex */
class l {
    @VisibleForTesting
    final SimpleArrayMap<RecyclerView.ViewHolder, a> a = new SimpleArrayMap<>();
    @VisibleForTesting
    final LongSparseArray<RecyclerView.ViewHolder> b = new LongSparseArray<>();

    /* compiled from: ViewInfoStore.java */
    /* loaded from: classes.dex */
    interface b {
        void a(RecyclerView.ViewHolder viewHolder);

        void a(RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo, @Nullable RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo2);

        void b(RecyclerView.ViewHolder viewHolder, @Nullable RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo, RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo2);

        void c(RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo, @NonNull RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a() {
        this.a.clear();
        this.b.clear();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(RecyclerView.ViewHolder viewHolder, RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo) {
        a aVar = this.a.get(viewHolder);
        if (aVar == null) {
            aVar = a.a();
            this.a.put(viewHolder, aVar);
        }
        aVar.b = itemHolderInfo;
        aVar.a |= 4;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a(RecyclerView.ViewHolder viewHolder) {
        a aVar = this.a.get(viewHolder);
        return (aVar == null || (aVar.a & 1) == 0) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public RecyclerView.ItemAnimator.ItemHolderInfo b(RecyclerView.ViewHolder viewHolder) {
        return a(viewHolder, 4);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public RecyclerView.ItemAnimator.ItemHolderInfo c(RecyclerView.ViewHolder viewHolder) {
        return a(viewHolder, 8);
    }

    private RecyclerView.ItemAnimator.ItemHolderInfo a(RecyclerView.ViewHolder viewHolder, int i) {
        a valueAt;
        RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo;
        int indexOfKey = this.a.indexOfKey(viewHolder);
        if (indexOfKey < 0 || (valueAt = this.a.valueAt(indexOfKey)) == null || (valueAt.a & i) == 0) {
            return null;
        }
        valueAt.a &= ~i;
        if (i == 4) {
            itemHolderInfo = valueAt.b;
        } else if (i == 8) {
            itemHolderInfo = valueAt.c;
        } else {
            throw new IllegalArgumentException("Must provide flag PRE or POST");
        }
        if ((valueAt.a & 12) == 0) {
            this.a.removeAt(indexOfKey);
            a.a(valueAt);
        }
        return itemHolderInfo;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(long j, RecyclerView.ViewHolder viewHolder) {
        this.b.put(j, viewHolder);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(RecyclerView.ViewHolder viewHolder, RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo) {
        a aVar = this.a.get(viewHolder);
        if (aVar == null) {
            aVar = a.a();
            this.a.put(viewHolder, aVar);
        }
        aVar.a |= 2;
        aVar.b = itemHolderInfo;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean d(RecyclerView.ViewHolder viewHolder) {
        a aVar = this.a.get(viewHolder);
        return (aVar == null || (aVar.a & 4) == 0) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public RecyclerView.ViewHolder a(long j) {
        return this.b.get(j);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void c(RecyclerView.ViewHolder viewHolder, RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo) {
        a aVar = this.a.get(viewHolder);
        if (aVar == null) {
            aVar = a.a();
            this.a.put(viewHolder, aVar);
        }
        aVar.c = itemHolderInfo;
        aVar.a |= 8;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void e(RecyclerView.ViewHolder viewHolder) {
        a aVar = this.a.get(viewHolder);
        if (aVar == null) {
            aVar = a.a();
            this.a.put(viewHolder, aVar);
        }
        aVar.a |= 1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void f(RecyclerView.ViewHolder viewHolder) {
        a aVar = this.a.get(viewHolder);
        if (aVar != null) {
            aVar.a &= -2;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(b bVar) {
        for (int size = this.a.size() - 1; size >= 0; size--) {
            RecyclerView.ViewHolder keyAt = this.a.keyAt(size);
            a removeAt = this.a.removeAt(size);
            if ((removeAt.a & 3) == 3) {
                bVar.a(keyAt);
            } else if ((removeAt.a & 1) != 0) {
                if (removeAt.b == null) {
                    bVar.a(keyAt);
                } else {
                    bVar.a(keyAt, removeAt.b, removeAt.c);
                }
            } else if ((removeAt.a & 14) == 14) {
                bVar.b(keyAt, removeAt.b, removeAt.c);
            } else if ((removeAt.a & 12) == 12) {
                bVar.c(keyAt, removeAt.b, removeAt.c);
            } else if ((removeAt.a & 4) != 0) {
                bVar.a(keyAt, removeAt.b, null);
            } else if ((removeAt.a & 8) != 0) {
                bVar.b(keyAt, removeAt.b, removeAt.c);
            } else {
                int i = removeAt.a;
            }
            a.a(removeAt);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void g(RecyclerView.ViewHolder viewHolder) {
        int size = this.b.size() - 1;
        while (true) {
            if (size < 0) {
                break;
            } else if (viewHolder == this.b.valueAt(size)) {
                this.b.removeAt(size);
                break;
            } else {
                size--;
            }
        }
        a remove = this.a.remove(viewHolder);
        if (remove != null) {
            a.a(remove);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b() {
        a.b();
    }

    public void h(RecyclerView.ViewHolder viewHolder) {
        f(viewHolder);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: ViewInfoStore.java */
    /* loaded from: classes.dex */
    public static class a {
        static Pools.Pool<a> d = new Pools.SimplePool(20);
        int a;
        @Nullable
        RecyclerView.ItemAnimator.ItemHolderInfo b;
        @Nullable
        RecyclerView.ItemAnimator.ItemHolderInfo c;

        private a() {
        }

        static a a() {
            a acquire = d.acquire();
            return acquire == null ? new a() : acquire;
        }

        static void a(a aVar) {
            aVar.a = 0;
            aVar.b = null;
            aVar.c = null;
            d.release(aVar);
        }

        static void b() {
            do {
            } while (d.acquire() != null);
        }
    }
}
