package androidx.recyclerview.widget;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

/* loaded from: classes.dex */
public abstract class SortedListAdapterCallback<T2> extends SortedList.Callback<T2> {
    final RecyclerView.Adapter a;

    public SortedListAdapterCallback(RecyclerView.Adapter adapter) {
        this.a = adapter;
    }

    @Override // androidx.recyclerview.widget.ListUpdateCallback
    public void onInserted(int i, int i2) {
        this.a.notifyItemRangeInserted(i, i2);
    }

    @Override // androidx.recyclerview.widget.ListUpdateCallback
    public void onRemoved(int i, int i2) {
        this.a.notifyItemRangeRemoved(i, i2);
    }

    @Override // androidx.recyclerview.widget.ListUpdateCallback
    public void onMoved(int i, int i2) {
        this.a.notifyItemMoved(i, i2);
    }

    @Override // androidx.recyclerview.widget.SortedList.Callback
    public void onChanged(int i, int i2) {
        this.a.notifyItemRangeChanged(i, i2);
    }

    @Override // androidx.recyclerview.widget.SortedList.Callback, androidx.recyclerview.widget.ListUpdateCallback
    public void onChanged(int i, int i2, Object obj) {
        this.a.notifyItemRangeChanged(i, i2, obj);
    }
}
