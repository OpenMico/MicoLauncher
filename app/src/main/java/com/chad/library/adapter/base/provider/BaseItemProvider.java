package com.chad.library.adapter.base.provider;

import android.content.Context;
import androidx.annotation.NonNull;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;

/* loaded from: classes.dex */
public abstract class BaseItemProvider<T, V extends BaseViewHolder> {
    public Context mContext;
    public List<T> mData;

    public abstract void convert(@NonNull V v, T t, int i);

    public void convertPayloads(@NonNull V v, T t, int i, @NonNull List<Object> list) {
    }

    public abstract int layout();

    public void onClick(V v, T t, int i) {
    }

    public boolean onLongClick(V v, T t, int i) {
        return false;
    }

    public abstract int viewType();
}
