package com.xiaomi.micolauncher.module.audiobooks.diffutil;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import com.xiaomi.micolauncher.api.model.AudioDiscoveryPage;
import java.util.List;

/* loaded from: classes3.dex */
public class AudioBookDiffUtilCallback extends DiffUtil.Callback {
    private List<AudioDiscoveryPage.Flow.BlocksBean.ItemsBean> a;
    private List<AudioDiscoveryPage.Flow.BlocksBean.ItemsBean> b;

    public AudioBookDiffUtilCallback(@NonNull List<AudioDiscoveryPage.Flow.BlocksBean.ItemsBean> list, @NonNull List<AudioDiscoveryPage.Flow.BlocksBean.ItemsBean> list2) {
        this.a = list;
        this.b = list2;
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public int getOldListSize() {
        return this.a.size();
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public int getNewListSize() {
        return this.b.size();
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public boolean areItemsTheSame(int i, int i2) {
        AudioDiscoveryPage.Flow.BlocksBean.ItemsBean itemsBean = this.a.get(i);
        AudioDiscoveryPage.Flow.BlocksBean.ItemsBean itemsBean2 = this.b.get(i2);
        if (!TextUtils.isEmpty(itemsBean.getStationId()) && !TextUtils.isEmpty(itemsBean2.getStationId())) {
            return itemsBean.getStationId().equals(itemsBean2.getStationId());
        }
        if (TextUtils.isEmpty(itemsBean.getId()) || TextUtils.isEmpty(itemsBean2.getId())) {
            return false;
        }
        return itemsBean.getId().equals(itemsBean2.getId());
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public boolean areContentsTheSame(int i, int i2) {
        return this.a.get(i).equals(this.b.get(i2));
    }
}
