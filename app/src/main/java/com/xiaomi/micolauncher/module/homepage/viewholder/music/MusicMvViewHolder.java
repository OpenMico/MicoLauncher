package com.xiaomi.micolauncher.module.homepage.viewholder.music;

import android.view.View;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.module.homepage.view.MusicPatchWallLatestMv;

/* loaded from: classes3.dex */
public class MusicMvViewHolder extends BaseMusicViewHolder {
    MusicPatchWallLatestMv c;

    public MusicMvViewHolder(View view) {
        super(view);
        this.c = (MusicPatchWallLatestMv) view.findViewById(R.id.music_find_mv);
    }

    @Override // com.xiaomi.micolauncher.common.view.BaseViewHolder
    protected void initViewInMain() {
        this.c.initInMain();
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.music.BaseMusicViewHolder
    public void setData(PatchWall.Block block) {
        this.c.setPatchBlockData(block);
    }
}
