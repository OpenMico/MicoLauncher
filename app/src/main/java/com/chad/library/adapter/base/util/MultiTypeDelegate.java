package com.chad.library.adapter.base.util;

import android.util.SparseIntArray;
import androidx.annotation.LayoutRes;
import java.util.List;

/* loaded from: classes.dex */
public abstract class MultiTypeDelegate<T> {
    private static final int DEFAULT_VIEW_TYPE = -255;
    private boolean autoMode;
    private SparseIntArray layouts;
    private boolean selfMode;

    protected abstract int getItemType(T t);

    public MultiTypeDelegate(SparseIntArray sparseIntArray) {
        this.layouts = sparseIntArray;
    }

    public MultiTypeDelegate() {
    }

    public final int getDefItemViewType(List<T> list, int i) {
        T t = list.get(i);
        return t != null ? getItemType(t) : DEFAULT_VIEW_TYPE;
    }

    public final int getLayoutId(int i) {
        return this.layouts.get(i, -404);
    }

    private void addItemType(int i, @LayoutRes int i2) {
        if (this.layouts == null) {
            this.layouts = new SparseIntArray();
        }
        this.layouts.put(i, i2);
    }

    public MultiTypeDelegate registerItemTypeAutoIncrease(@LayoutRes int... iArr) {
        this.autoMode = true;
        checkMode(this.selfMode);
        for (int i = 0; i < iArr.length; i++) {
            addItemType(i, iArr[i]);
        }
        return this;
    }

    public MultiTypeDelegate registerItemType(int i, @LayoutRes int i2) {
        this.selfMode = true;
        checkMode(this.autoMode);
        addItemType(i, i2);
        return this;
    }

    private void checkMode(boolean z) {
        if (z) {
            throw new IllegalArgumentException("Don't mess two register mode");
        }
    }
}
