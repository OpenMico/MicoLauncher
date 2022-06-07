package com.xiaomi.micolauncher.module.music.swiperefresh;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewStub;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes3.dex */
public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {
    private final SparseArray<View> a = new SparseArray<>();
    private int b;

    public BaseRecyclerViewHolder(View view) {
        super(view);
    }

    public void setViewHolderType(int i) {
        this.b = i;
    }

    public int getViewHolderType() {
        return this.b;
    }

    public <T extends View> T obtainView(int i) {
        T t = (T) this.a.get(i);
        if (t != null) {
            return t;
        }
        T t2 = (T) this.itemView.findViewById(i);
        if (t2 == null) {
            return null;
        }
        if (!(t2 instanceof ViewStub)) {
            this.a.put(i, t2);
        }
        return t2;
    }

    public <T> T obtainView(int i, Class<T> cls) {
        T t = (T) obtainView(i);
        if (t == null) {
            return null;
        }
        return t;
    }
}
