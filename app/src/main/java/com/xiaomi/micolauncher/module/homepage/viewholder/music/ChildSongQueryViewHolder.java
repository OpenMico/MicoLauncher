package com.xiaomi.micolauncher.module.homepage.viewholder.music;

import android.view.View;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.module.homepage.view.ChildSongQueryView;

/* loaded from: classes3.dex */
public class ChildSongQueryViewHolder extends BaseMusicViewHolder {
    @Override // com.xiaomi.micolauncher.common.view.BaseViewHolder
    protected void initViewInMain() {
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.music.BaseMusicViewHolder
    public void setData(PatchWall.Block block) {
    }

    public ChildSongQueryViewHolder(int i, View view) {
        super(view);
        ((ChildSongQueryView) view).updateItems(i);
    }
}
