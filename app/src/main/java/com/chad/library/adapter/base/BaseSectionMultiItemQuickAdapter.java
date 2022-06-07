package com.chad.library.adapter.base;

import android.util.SparseIntArray;
import android.view.ViewGroup;
import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.IExpandable;
import com.chad.library.adapter.base.entity.SectionMultiEntity;
import java.util.List;

/* loaded from: classes.dex */
public abstract class BaseSectionMultiItemQuickAdapter<T extends SectionMultiEntity, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {
    private static final int DEFAULT_VIEW_TYPE = -255;
    protected static final int SECTION_HEADER_VIEW = 1092;
    public static final int TYPE_NOT_FOUND = -404;
    private SparseIntArray layouts;
    protected int mSectionHeadResId;

    protected abstract void convertHead(K k, T t);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public /* bridge */ /* synthetic */ void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        onBindViewHolder((BaseSectionMultiItemQuickAdapter<T, K>) ((BaseViewHolder) viewHolder), i);
    }

    public BaseSectionMultiItemQuickAdapter(int i, List<T> list) {
        super(list);
        this.mSectionHeadResId = i;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    protected int getDefItemViewType(int i) {
        SectionMultiEntity sectionMultiEntity = (SectionMultiEntity) this.mData.get(i);
        return sectionMultiEntity != null ? sectionMultiEntity.isHeader ? SECTION_HEADER_VIEW : sectionMultiEntity.getItemType() : DEFAULT_VIEW_TYPE;
    }

    protected void setDefaultViewTypeLayout(@LayoutRes int i) {
        addItemType(DEFAULT_VIEW_TYPE, i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public K onCreateDefViewHolder(ViewGroup viewGroup, int i) {
        if (i == SECTION_HEADER_VIEW) {
            return createBaseViewHolder(getItemView(this.mSectionHeadResId, viewGroup));
        }
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

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public boolean isFixedViewType(int i) {
        return super.isFixedViewType(i) || i == SECTION_HEADER_VIEW;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void onBindViewHolder(@NonNull K k, int i) {
        if (k.getItemViewType() != SECTION_HEADER_VIEW) {
            super.onBindViewHolder((BaseSectionMultiItemQuickAdapter<T, K>) k, i);
            return;
        }
        setFullSpan(k);
        convertHead(k, (SectionMultiEntity) getItem(i - getHeaderLayoutCount()));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void remove(@IntRange(from = 0) int i) {
        if (this.mData != null && i >= 0 && i < this.mData.size()) {
            SectionMultiEntity sectionMultiEntity = (SectionMultiEntity) this.mData.get(i);
            if (sectionMultiEntity instanceof IExpandable) {
                removeAllChild((IExpandable) sectionMultiEntity, i);
            }
            removeDataFromParent(sectionMultiEntity);
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
        int parentPosition = getParentPosition(t);
        if (parentPosition >= 0) {
            ((IExpandable) this.mData.get(parentPosition)).getSubItems().remove(t);
        }
    }
}
