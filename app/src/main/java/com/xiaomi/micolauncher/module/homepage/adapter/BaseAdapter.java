package com.xiaomi.micolauncher.module.homepage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.common.utils.X2CWrapper;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import com.xiaomi.micolauncher.module.child.childvideo.holder.BaseChildVideoHolder;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class BaseAdapter<T extends BaseViewHolder> extends RecyclerView.Adapter<T> {
    protected List<BaseChildVideoHolder> holders = new ArrayList();
    protected boolean isScrolling;

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return i;
    }

    protected View inflateView(ViewGroup viewGroup, int i) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(i, viewGroup, false);
    }

    protected View inflateViewByX2C(ViewGroup viewGroup, int i) {
        return X2CWrapper.inflate(viewGroup.getContext(), i, viewGroup, false);
    }

    public void setScrolling(boolean z) {
        this.isScrolling = z;
    }
}
