package com.xiaomi.micolauncher.module.homepage.viewholder.music;

import android.view.View;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.module.homepage.view.MusicPatchWallHotView;

/* loaded from: classes3.dex */
public class MusicHotViewHolder extends BaseMusicViewHolder {
    private MusicPatchWallHotView c;

    public MusicHotViewHolder(View view) {
        super(view);
        this.c = (MusicPatchWallHotView) view.findViewById(R.id.music_find_hot);
    }

    @Override // com.xiaomi.micolauncher.common.view.BaseViewHolder
    protected void initViewInMain() {
        MusicPatchWallHotView musicPatchWallHotView = this.c;
        if (musicPatchWallHotView != null) {
            musicPatchWallHotView.initInMain();
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.music.BaseMusicViewHolder
    public void setData(PatchWall.Block block) {
        MusicPatchWallHotView musicPatchWallHotView = this.c;
        if (musicPatchWallHotView != null) {
            musicPatchWallHotView.setPatchBlockData(block);
        }
    }
}
