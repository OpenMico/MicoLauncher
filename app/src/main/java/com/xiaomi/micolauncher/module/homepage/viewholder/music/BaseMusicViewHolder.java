package com.xiaomi.micolauncher.module.homepage.viewholder.music;

import android.view.View;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import com.xiaomi.micolauncher.module.homepage.view.BaseMusicView;

/* loaded from: classes3.dex */
public abstract class BaseMusicViewHolder extends BaseViewHolder {
    BaseMusicView[] a = initItemViews();
    PatchWall.Block b;

    protected BaseMusicView[] initItemViews() {
        return null;
    }

    public BaseMusicViewHolder(View view) {
        super(view);
    }

    public void setData(PatchWall.Block block) {
        this.b = block;
        for (int i = 0; i < this.a.length; i++) {
            if (i < block.items.size()) {
                this.a[i].setData(block.title, block.items.get(i));
            }
        }
    }
}
