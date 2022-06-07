package com.chad.library.adapter.base;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.chad.library.adapter.base.util.ProviderDelegate;
import java.util.List;

/* loaded from: classes.dex */
public abstract class MultipleItemRvAdapter<T, V extends BaseViewHolder> extends BaseQuickAdapter<T, V> {
    private SparseArray<BaseItemProvider> mItemProviders;
    private MultiTypeDelegate<T> mMultiTypeDelegate;
    protected ProviderDelegate mProviderDelegate;

    protected abstract int getViewType(T t);

    public abstract void registerItemProvider();

    public MultipleItemRvAdapter(@Nullable List<T> list) {
        super(list);
    }

    public void finishInitialize() {
        this.mProviderDelegate = new ProviderDelegate();
        setMultiTypeDelegate(new MultiTypeDelegate<T>() { // from class: com.chad.library.adapter.base.MultipleItemRvAdapter.1
            @Override // com.chad.library.adapter.base.util.MultiTypeDelegate
            protected int getItemType(T t) {
                return MultipleItemRvAdapter.this.getViewType(t);
            }
        });
        registerItemProvider();
        this.mItemProviders = this.mProviderDelegate.getItemProviders();
        for (int i = 0; i < this.mItemProviders.size(); i++) {
            int keyAt = this.mItemProviders.keyAt(i);
            BaseItemProvider baseItemProvider = this.mItemProviders.get(keyAt);
            baseItemProvider.mData = this.mData;
            getMultiTypeDelegate().registerItemType(keyAt, baseItemProvider.layout());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void bindViewClickListener(V v) {
        if (v != null) {
            bindClick(v);
            super.bindViewClickListener(v);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public V onCreateDefViewHolder(ViewGroup viewGroup, int i) {
        if (getMultiTypeDelegate() != null) {
            return (V) createBaseViewHolder(viewGroup, getMultiTypeDelegate().getLayoutId(i));
        }
        throw new IllegalStateException("please use setMultiTypeDelegate first!");
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    protected int getDefItemViewType(int i) {
        if (getMultiTypeDelegate() != null) {
            return getMultiTypeDelegate().getDefItemViewType(this.mData, i);
        }
        throw new IllegalStateException("please use setMultiTypeDelegate first!");
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    protected void convert(@NonNull V v, T t) {
        BaseItemProvider baseItemProvider = this.mItemProviders.get(v.getItemViewType());
        baseItemProvider.mContext = ((BaseViewHolder) v).itemView.getContext();
        baseItemProvider.convert(v, t, v.getLayoutPosition() - getHeaderLayoutCount());
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    protected void convertPayloads(@NonNull V v, T t, @NonNull List<Object> list) {
        this.mItemProviders.get(v.getItemViewType()).convertPayloads(v, t, v.getLayoutPosition() - getHeaderLayoutCount(), list);
    }

    private void bindClick(final V v) {
        BaseQuickAdapter.OnItemClickListener onItemClickListener = getOnItemClickListener();
        BaseQuickAdapter.OnItemLongClickListener onItemLongClickListener = getOnItemLongClickListener();
        if (onItemClickListener == null || onItemLongClickListener == null) {
            if (onItemClickListener == null) {
                ((BaseViewHolder) v).itemView.setOnClickListener(new View.OnClickListener() { // from class: com.chad.library.adapter.base.MultipleItemRvAdapter.2
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        int adapterPosition = v.getAdapterPosition();
                        if (adapterPosition != -1) {
                            int headerLayoutCount = adapterPosition - MultipleItemRvAdapter.this.getHeaderLayoutCount();
                            ((BaseItemProvider) MultipleItemRvAdapter.this.mItemProviders.get(v.getItemViewType())).onClick(v, MultipleItemRvAdapter.this.mData.get(headerLayoutCount), headerLayoutCount);
                        }
                    }
                });
            }
            if (onItemLongClickListener == null) {
                ((BaseViewHolder) v).itemView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.chad.library.adapter.base.MultipleItemRvAdapter.3
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // android.view.View.OnLongClickListener
                    public boolean onLongClick(View view) {
                        int adapterPosition = v.getAdapterPosition();
                        if (adapterPosition == -1) {
                            return false;
                        }
                        int headerLayoutCount = adapterPosition - MultipleItemRvAdapter.this.getHeaderLayoutCount();
                        return ((BaseItemProvider) MultipleItemRvAdapter.this.mItemProviders.get(v.getItemViewType())).onLongClick(v, MultipleItemRvAdapter.this.mData.get(headerLayoutCount), headerLayoutCount);
                    }
                });
            }
        }
    }

    public void setMultiTypeDelegate(MultiTypeDelegate<T> multiTypeDelegate) {
        this.mMultiTypeDelegate = multiTypeDelegate;
    }

    public MultiTypeDelegate<T> getMultiTypeDelegate() {
        return this.mMultiTypeDelegate;
    }
}
