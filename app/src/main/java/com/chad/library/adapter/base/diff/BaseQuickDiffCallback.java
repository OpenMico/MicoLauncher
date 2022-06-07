package com.chad.library.adapter.base.diff;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public abstract class BaseQuickDiffCallback<T> extends DiffUtil.Callback {
    private List<T> newList;
    private List<T> oldList;

    protected abstract boolean areContentsTheSame(@NonNull T t, @NonNull T t2);

    protected abstract boolean areItemsTheSame(@NonNull T t, @NonNull T t2);

    @Nullable
    protected Object getChangePayload(@NonNull T t, @NonNull T t2) {
        return null;
    }

    public BaseQuickDiffCallback(@Nullable List<T> list) {
        this.newList = list == null ? new ArrayList<>() : list;
    }

    public List<T> getNewList() {
        return this.newList;
    }

    public List<T> getOldList() {
        return this.oldList;
    }

    public void setOldList(@Nullable List<T> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        this.oldList = list;
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public int getOldListSize() {
        return this.oldList.size();
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public int getNewListSize() {
        return this.newList.size();
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public boolean areItemsTheSame(int i, int i2) {
        return areItemsTheSame(this.oldList.get(i), this.newList.get(i2));
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public boolean areContentsTheSame(int i, int i2) {
        return areContentsTheSame(this.oldList.get(i), this.newList.get(i2));
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    @Nullable
    public Object getChangePayload(int i, int i2) {
        return getChangePayload(this.oldList.get(i), this.newList.get(i2));
    }
}
