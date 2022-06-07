package com.xiaomi.micolauncher.module.homepage.viewholder.music;

import android.view.View;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.module.homepage.view.HomeChildSongFindRecommendView;

/* loaded from: classes3.dex */
public class ChildSongRecommendViewHolder extends BaseMusicViewHolder {
    HomeChildSongFindRecommendView c;

    @Override // com.xiaomi.micolauncher.common.view.BaseViewHolder
    protected void initViewInMain() {
    }

    public ChildSongRecommendViewHolder(View view) {
        super(view);
        this.c = (HomeChildSongFindRecommendView) view.findViewById(R.id.music_find_like_child_mode);
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.music.BaseMusicViewHolder
    public void setData(PatchWall.Block block) {
        this.c.setPatchBlockData(block);
    }
}
