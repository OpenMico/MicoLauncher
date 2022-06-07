package com.xiaomi.micolauncher.skills.video.view;

import android.text.TextUtils;
import androidx.recyclerview.widget.DiffUtil;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import java.util.List;

/* loaded from: classes3.dex */
public class SelectionsDiffUtilCallback extends DiffUtil.Callback {
    private final List<VideoItem> a;
    private final List<VideoItem> b;

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public boolean areContentsTheSame(int i, int i2) {
        return false;
    }

    public SelectionsDiffUtilCallback(List<VideoItem> list, List<VideoItem> list2) {
        this.a = list;
        this.b = list2;
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public int getOldListSize() {
        if (ContainerUtil.hasData(this.b)) {
            return this.b.size();
        }
        return 0;
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public int getNewListSize() {
        if (ContainerUtil.hasData(this.a)) {
            return this.a.size();
        }
        return 0;
    }

    private VideoItem a(int i) {
        if (!ContainerUtil.hasData(this.b) || i < 0 || i >= this.b.size()) {
            return null;
        }
        return this.b.get(i);
    }

    private VideoItem b(int i) {
        if (!ContainerUtil.hasData(this.a) || i < 0 || i >= this.a.size()) {
            return null;
        }
        return this.a.get(i);
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public boolean areItemsTheSame(int i, int i2) {
        VideoItem b = b(i2);
        VideoItem a = a(i);
        if (b == null || a == null) {
            return false;
        }
        return TextUtils.equals(b.getMediaId(), a.getMediaId());
    }
}
