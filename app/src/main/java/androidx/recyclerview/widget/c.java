package androidx.recyclerview.widget;

import android.util.Log;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Preconditions;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StableIdStorage;
import androidx.recyclerview.widget.ViewTypeStorage;
import androidx.recyclerview.widget.i;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;

/* compiled from: ConcatAdapterController.java */
/* loaded from: classes.dex */
class c implements i.a {
    private final ConcatAdapter a;
    private final ViewTypeStorage b;
    private List<WeakReference<RecyclerView>> c = new ArrayList();
    private final IdentityHashMap<RecyclerView.ViewHolder, i> d = new IdentityHashMap<>();
    private List<i> e = new ArrayList();
    private a f = new a();
    @NonNull
    private final ConcatAdapter.Config.StableIdMode g;
    private final StableIdStorage h;

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(ConcatAdapter concatAdapter, ConcatAdapter.Config config) {
        this.a = concatAdapter;
        if (config.isolateViewTypes) {
            this.b = new ViewTypeStorage.IsolatedViewTypeStorage();
        } else {
            this.b = new ViewTypeStorage.SharedIdRangeViewTypeStorage();
        }
        this.g = config.stableIdMode;
        if (config.stableIdMode == ConcatAdapter.Config.StableIdMode.NO_STABLE_IDS) {
            this.h = new StableIdStorage.NoStableIdStorage();
        } else if (config.stableIdMode == ConcatAdapter.Config.StableIdMode.ISOLATED_STABLE_IDS) {
            this.h = new StableIdStorage.IsolatedStableIdStorage();
        } else if (config.stableIdMode == ConcatAdapter.Config.StableIdMode.SHARED_STABLE_IDS) {
            this.h = new StableIdStorage.SharedPoolStableIdStorage();
        } else {
            throw new IllegalArgumentException("unknown stable id mode");
        }
    }

    @Nullable
    private i c(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        int d = d(adapter);
        if (d == -1) {
            return null;
        }
        return this.e.get(d);
    }

    private int d(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        int size = this.e.size();
        for (int i = 0; i < size; i++) {
            if (this.e.get(i).a == adapter) {
                return i;
            }
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        return a(this.e.size(), adapter);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean a(int i, RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        if (i < 0 || i > this.e.size()) {
            throw new IndexOutOfBoundsException("Index must be between 0 and " + this.e.size() + ". Given:" + i);
        }
        if (c()) {
            Preconditions.checkArgument(adapter.hasStableIds(), "All sub adapters must have stable ids when stable id mode is ISOLATED_STABLE_IDS or SHARED_STABLE_IDS");
        } else if (adapter.hasStableIds()) {
            Log.w("ConcatAdapter", "Stable ids in the adapter will be ignored as the ConcatAdapter is configured not to have stable ids");
        }
        if (c(adapter) != null) {
            return false;
        }
        i iVar = new i(adapter, this, this.b, this.h.createStableIdLookup());
        this.e.add(i, iVar);
        for (WeakReference<RecyclerView> weakReference : this.c) {
            RecyclerView recyclerView = weakReference.get();
            if (recyclerView != null) {
                adapter.onAttachedToRecyclerView(recyclerView);
            }
        }
        if (iVar.b() > 0) {
            this.a.notifyItemRangeInserted(c(iVar), iVar.b());
        }
        d();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean b(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        int d = d(adapter);
        if (d == -1) {
            return false;
        }
        i iVar = this.e.get(d);
        int c = c(iVar);
        this.e.remove(d);
        this.a.notifyItemRangeRemoved(c, iVar.b());
        for (WeakReference<RecyclerView> weakReference : this.c) {
            RecyclerView recyclerView = weakReference.get();
            if (recyclerView != null) {
                adapter.onDetachedFromRecyclerView(recyclerView);
            }
        }
        iVar.a();
        d();
        return true;
    }

    private int c(i iVar) {
        i next;
        Iterator<i> it = this.e.iterator();
        int i = 0;
        while (it.hasNext() && (next = it.next()) != iVar) {
            i += next.b();
        }
        return i;
    }

    public long a(int i) {
        a c = c(i);
        long b = c.a.b(c.b);
        a(c);
        return b;
    }

    @Override // androidx.recyclerview.widget.i.a
    public void a(@NonNull i iVar) {
        this.a.notifyDataSetChanged();
        d();
    }

    @Override // androidx.recyclerview.widget.i.a
    public void a(@NonNull i iVar, int i, int i2, @Nullable Object obj) {
        this.a.notifyItemRangeChanged(i + c(iVar), i2, obj);
    }

    @Override // androidx.recyclerview.widget.i.a
    public void a(@NonNull i iVar, int i, int i2) {
        this.a.notifyItemRangeInserted(i + c(iVar), i2);
    }

    @Override // androidx.recyclerview.widget.i.a
    public void b(@NonNull i iVar, int i, int i2) {
        this.a.notifyItemRangeRemoved(i + c(iVar), i2);
    }

    @Override // androidx.recyclerview.widget.i.a
    public void c(@NonNull i iVar, int i, int i2) {
        int c = c(iVar);
        this.a.notifyItemMoved(i + c, i2 + c);
    }

    @Override // androidx.recyclerview.widget.i.a
    public void b(i iVar) {
        d();
    }

    private void d() {
        RecyclerView.Adapter.StateRestorationPolicy e = e();
        if (e != this.a.getStateRestorationPolicy()) {
            this.a.a(e);
        }
    }

    private RecyclerView.Adapter.StateRestorationPolicy e() {
        for (i iVar : this.e) {
            RecyclerView.Adapter.StateRestorationPolicy stateRestorationPolicy = iVar.a.getStateRestorationPolicy();
            if (stateRestorationPolicy == RecyclerView.Adapter.StateRestorationPolicy.PREVENT) {
                return RecyclerView.Adapter.StateRestorationPolicy.PREVENT;
            }
            if (stateRestorationPolicy == RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY && iVar.b() == 0) {
                return RecyclerView.Adapter.StateRestorationPolicy.PREVENT;
            }
        }
        return RecyclerView.Adapter.StateRestorationPolicy.ALLOW;
    }

    public int a() {
        int i = 0;
        for (i iVar : this.e) {
            i += iVar.b();
        }
        return i;
    }

    public int b(int i) {
        a c = c(i);
        int a2 = c.a.a(c.b);
        a(c);
        return a2;
    }

    public RecyclerView.ViewHolder a(ViewGroup viewGroup, int i) {
        return this.b.getWrapperForGlobalType(i).a(viewGroup, i);
    }

    @NonNull
    private a c(int i) {
        a aVar;
        if (this.f.c) {
            aVar = new a();
        } else {
            aVar = this.f;
            aVar.c = true;
        }
        Iterator<i> it = this.e.iterator();
        int i2 = i;
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            i next = it.next();
            if (next.b() > i2) {
                aVar.a = next;
                aVar.b = i2;
                break;
            }
            i2 -= next.b();
        }
        if (aVar.a != null) {
            return aVar;
        }
        throw new IllegalArgumentException("Cannot find wrapper for " + i);
    }

    private void a(a aVar) {
        aVar.c = false;
        aVar.a = null;
        aVar.b = -1;
        this.f = aVar;
    }

    public void a(RecyclerView.ViewHolder viewHolder, int i) {
        a c = c(i);
        this.d.put(viewHolder, c.a);
        c.a.a(viewHolder, c.b);
        a(c);
    }

    public void a(RecyclerView.ViewHolder viewHolder) {
        e(viewHolder).a.onViewAttachedToWindow(viewHolder);
    }

    public void b(RecyclerView.ViewHolder viewHolder) {
        e(viewHolder).a.onViewDetachedFromWindow(viewHolder);
    }

    public void c(RecyclerView.ViewHolder viewHolder) {
        i iVar = this.d.get(viewHolder);
        if (iVar != null) {
            iVar.a.onViewRecycled(viewHolder);
            this.d.remove(viewHolder);
            return;
        }
        throw new IllegalStateException("Cannot find wrapper for " + viewHolder + ", seems like it is not bound by this adapter: " + this);
    }

    public boolean d(RecyclerView.ViewHolder viewHolder) {
        i iVar = this.d.get(viewHolder);
        if (iVar != null) {
            boolean onFailedToRecycleView = iVar.a.onFailedToRecycleView(viewHolder);
            this.d.remove(viewHolder);
            return onFailedToRecycleView;
        }
        throw new IllegalStateException("Cannot find wrapper for " + viewHolder + ", seems like it is not bound by this adapter: " + this);
    }

    @NonNull
    private i e(RecyclerView.ViewHolder viewHolder) {
        i iVar = this.d.get(viewHolder);
        if (iVar != null) {
            return iVar;
        }
        throw new IllegalStateException("Cannot find wrapper for " + viewHolder + ", seems like it is not bound by this adapter: " + this);
    }

    private boolean c(RecyclerView recyclerView) {
        for (WeakReference<RecyclerView> weakReference : this.c) {
            if (weakReference.get() == recyclerView) {
                return true;
            }
        }
        return false;
    }

    public void a(RecyclerView recyclerView) {
        if (!c(recyclerView)) {
            this.c.add(new WeakReference<>(recyclerView));
            for (i iVar : this.e) {
                iVar.a.onAttachedToRecyclerView(recyclerView);
            }
        }
    }

    public void b(RecyclerView recyclerView) {
        int size = this.c.size() - 1;
        while (true) {
            if (size < 0) {
                break;
            }
            WeakReference<RecyclerView> weakReference = this.c.get(size);
            if (weakReference.get() == null) {
                this.c.remove(size);
            } else if (weakReference.get() == recyclerView) {
                this.c.remove(size);
                break;
            }
            size--;
        }
        for (i iVar : this.e) {
            iVar.a.onDetachedFromRecyclerView(recyclerView);
        }
    }

    public int a(RecyclerView.Adapter<? extends RecyclerView.ViewHolder> adapter, RecyclerView.ViewHolder viewHolder, int i) {
        i iVar = this.d.get(viewHolder);
        if (iVar == null) {
            return -1;
        }
        int c = i - c(iVar);
        int itemCount = iVar.a.getItemCount();
        if (c >= 0 && c < itemCount) {
            return iVar.a.findRelativeAdapterPositionIn(adapter, viewHolder, c);
        }
        throw new IllegalStateException("Detected inconsistent adapter updates. The local position of the view holder maps to " + c + " which is out of bounds for the adapter with size " + itemCount + ".Make sure to immediately call notify methods in your adapter when you change the backing dataviewHolder:" + viewHolder + "adapter:" + adapter);
    }

    public List<RecyclerView.Adapter<? extends RecyclerView.ViewHolder>> b() {
        if (this.e.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(this.e.size());
        for (i iVar : this.e) {
            arrayList.add(iVar.a);
        }
        return arrayList;
    }

    public boolean c() {
        return this.g != ConcatAdapter.Config.StableIdMode.NO_STABLE_IDS;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: ConcatAdapterController.java */
    /* loaded from: classes.dex */
    public static class a {
        i a;
        int b;
        boolean c;

        a() {
        }
    }
}
