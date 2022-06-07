package com.xiaomi.micolauncher.module.homepage.viewholder.music;

import android.view.View;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.module.homepage.view.ChildHotSongView;

/* loaded from: classes3.dex */
public class ChildHotSongViewHolder extends BaseMusicViewHolder {
    private final ChildHotSongView c;

    @Override // com.xiaomi.micolauncher.common.view.BaseViewHolder
    protected void initViewInMain() {
    }

    public ChildHotSongViewHolder(int i, View view) {
        super(view);
        this.c = (ChildHotSongView) view;
        this.c.setTitle(i == 3 ? R.string.hot_chinese_song_title : R.string.hot_english_song_title);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.music.BaseMusicViewHolder
    public void setData(PatchWall.Block block) {
        this.c.setPatchBlockData(block);
    }
}
