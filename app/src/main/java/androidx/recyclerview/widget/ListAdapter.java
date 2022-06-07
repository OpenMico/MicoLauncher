package androidx.recyclerview.widget;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import java.util.List;

/* loaded from: classes.dex */
public abstract class ListAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    final AsyncListDiffer<T> a;
    private final AsyncListDiffer.ListListener<T> b = new AsyncListDiffer.ListListener<T>() { // from class: androidx.recyclerview.widget.ListAdapter.1
        @Override // androidx.recyclerview.widget.AsyncListDiffer.ListListener
        public void onCurrentListChanged(@NonNull List<T> list, @NonNull List<T> list2) {
            ListAdapter.this.onCurrentListChanged(list, list2);
        }
    };

    public void onCurrentListChanged(@NonNull List<T> list, @NonNull List<T> list2) {
    }

    protected ListAdapter(@NonNull DiffUtil.ItemCallback<T> itemCallback) {
        this.a = new AsyncListDiffer<>(new AdapterListUpdateCallback(this), new AsyncDifferConfig.Builder(itemCallback).build());
        this.a.addListListener(this.b);
    }

    protected ListAdapter(@NonNull AsyncDifferConfig<T> asyncDifferConfig) {
        this.a = new AsyncListDiffer<>(new AdapterListUpdateCallback(this), asyncDifferConfig);
        this.a.addListListener(this.b);
    }

    public void submitList(@Nullable List<T> list) {
        this.a.submitList(list);
    }

    public void submitList(@Nullable List<T> list, @Nullable Runnable runnable) {
        this.a.submitList(list, runnable);
    }

    protected T getItem(int i) {
        return this.a.getCurrentList().get(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.a.getCurrentList().size();
    }

    @NonNull
    public List<T> getCurrentList() {
        return this.a.getCurrentList();
    }
}
