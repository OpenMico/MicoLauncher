package com.xiaomi.micolauncher.module.homepage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.homepage.viewholder.CategoryViewHolder;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import java.util.List;

/* loaded from: classes3.dex */
public class CategoryTabAdapter<T> extends RecyclerView.Adapter<CategoryViewHolder> {
    private List<T> a;
    private int b = 0;
    private final CategoryViewHolder.TabClickListener c;
    private final LayoutInflater d;

    public CategoryTabAdapter(Context context, List<T> list, CategoryViewHolder.TabClickListener tabClickListener) {
        this.a = list;
        this.c = tabClickListener;
        this.d = LayoutInflater.from(context);
    }

    public List<T> getItems() {
        return this.a;
    }

    public T getSelectItem() {
        return this.a.get(this.b);
    }

    public int getHighlightPosition() {
        return this.b;
    }

    public void updateTabList(List<T> list) {
        this.a = list;
        notifyDataSetChanged();
    }

    public boolean setHighlightPosition(int i) {
        int i2 = this.b;
        if (i2 == i) {
            return false;
        }
        this.b = i;
        notifyItemChanged(i2);
        notifyItemChanged(i);
        return true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CategoryViewHolder categoryViewHolder = new CategoryViewHolder(this.d.inflate(ChildModeManager.getManager().isChildMode() ? R.layout.home_category_item_child : R.layout.home_category_item, viewGroup, false));
        categoryViewHolder.setTabClickListener(this.c);
        return categoryViewHolder;
    }

    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int i) {
        categoryViewHolder.bindData(i, this.b == i, this.a.get(i));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return ContainerUtil.getSize(this.a);
    }
}
