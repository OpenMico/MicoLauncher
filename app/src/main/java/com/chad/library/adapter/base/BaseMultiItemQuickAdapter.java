package com.chad.library.adapter.base;

import android.util.SparseIntArray;
import android.view.ViewGroup;
import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.IExpandable;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import java.util.List;

/* loaded from: classes.dex */
public abstract class BaseMultiItemQuickAdapter<T extends MultiItemEntity, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {
    private static final int DEFAULT_VIEW_TYPE = -255;
    public static final int TYPE_NOT_FOUND = -404;
    private SparseIntArray layouts;

    public BaseMultiItemQuickAdapter(List<T> list) {
        super(list);
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    protected int getDefItemViewType(int i) {
        MultiItemEntity multiItemEntity = (MultiItemEntity) this.mData.get(i);
        return multiItemEntity != null ? multiItemEntity.getItemType() : DEFAULT_VIEW_TYPE;
    }

    protected void setDefaultViewTypeLayout(@LayoutRes int i) {
        addItemType(DEFAULT_VIEW_TYPE, i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public K onCreateDefViewHolder(ViewGroup viewGroup, int i) {
        return createBaseViewHolder(viewGroup, getLayoutId(i));
    }

    private int getLayoutId(int i) {
        return this.layouts.get(i, -404);
    }

    protected void addItemType(int i, @LayoutRes int i2) {
        if (this.layouts == null) {
            this.layouts = new SparseIntArray();
        }
        this.layouts.put(i, i2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void remove(@IntRange(from = 0) int i) {
        if (this.mData != null && i >= 0 && i < this.mData.size()) {
            MultiItemEntity multiItemEntity = (MultiItemEntity) this.mData.get(i);
            if (multiItemEntity instanceof IExpandable) {
                removeAllChild((IExpandable) multiItemEntity, i);
            }
            removeDataFromParent(multiItemEntity);
            super.remove(i);
        }
    }

    protected void removeAllChild(IExpandable iExpandable, int i) {
        List subItems;
        if (!(!iExpandable.isExpanded() || (subItems = iExpandable.getSubItems()) == null || subItems.size() == 0)) {
            int size = subItems.size();
            for (int i2 = 0; i2 < size; i2++) {
                remove(i + 1);
            }
        }
    }

    protected void removeDataFromParent(T t) {
        IExpandable iExpandable;
        int parentPosition = getParentPosition(t);
        if (parentPosition >= 0 && (iExpandable = (IExpandable) this.mData.get(parentPosition)) != t) {
            iExpandable.getSubItems().remove(t);
        }
    }

    public int getParentPositionInAll(int i) {
        List<T> data = getData();
        MultiItemEntity multiItemEntity = (MultiItemEntity) getItem(i);
        if (isExpandable(multiItemEntity)) {
            IExpandable iExpandable = (IExpandable) multiItemEntity;
            for (int i2 = i - 1; i2 >= 0; i2--) {
                MultiItemEntity multiItemEntity2 = (MultiItemEntity) data.get(i2);
                if (isExpandable(multiItemEntity2) && iExpandable.getLevel() > ((IExpandable) multiItemEntity2).getLevel()) {
                    return i2;
                }
            }
            return -1;
        }
        for (int i3 = i - 1; i3 >= 0; i3--) {
            if (isExpandable((MultiItemEntity) data.get(i3))) {
                return i3;
            }
        }
        return -1;
    }

    public boolean isExpandable(MultiItemEntity multiItemEntity) {
        return multiItemEntity != null && (multiItemEntity instanceof IExpandable);
    }
}
