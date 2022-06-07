package com.xiaomi.micolauncher.module.homepage.viewholder.music;

import android.view.View;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.module.homepage.view.ChildSongRecentAndFavView;

/* loaded from: classes3.dex */
public class ChildSongRecentAndFavHolder extends BaseMusicViewHolder {
    private final ChildSongRecentAndFavView c;

    public ChildSongRecentAndFavHolder(View view) {
        super(view);
        this.c = (ChildSongRecentAndFavView) view;
    }

    @Override // com.xiaomi.micolauncher.common.view.BaseViewHolder
    protected void initViewInMain() {
        this.c.initInMain();
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.music.BaseMusicViewHolder
    public void setData(PatchWall.Block block) {
        this.c.setPatchBlockData();
    }
}
