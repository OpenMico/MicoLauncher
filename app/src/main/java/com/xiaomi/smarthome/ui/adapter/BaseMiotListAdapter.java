package com.xiaomi.smarthome.ui.adapter;

import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class BaseMiotListAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    protected List<T> dataList = new ArrayList();

    public List<T> getDataList() {
        return this.dataList;
    }

    public void updateDataList(List<T> list) {
        this.dataList.clear();
        if (list != null) {
            this.dataList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<T> list = this.dataList;
        if (list == null) {
            return 0;
        }
        return list.size();
    }
}
