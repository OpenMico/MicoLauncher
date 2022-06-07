package com.xiaomi.micolauncher.module.homepage.viewholder.music;

import android.view.View;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.module.homepage.view.MusicRecommendItemView;

/* loaded from: classes3.dex */
public class MusicRecommendViewHolder extends BaseMusicViewHolder {
    MusicRecommendItemView[] c = new MusicRecommendItemView[2];

    public MusicRecommendViewHolder(View view) {
        super(view);
        this.c[0] = (MusicRecommendItemView) view.findViewById(R.id.recommend_first);
        this.c[1] = (MusicRecommendItemView) view.findViewById(R.id.recommend_second);
    }

    @Override // com.xiaomi.micolauncher.common.view.BaseViewHolder
    protected void initViewInMain() {
        for (MusicRecommendItemView musicRecommendItemView : this.c) {
            musicRecommendItemView.initInMain();
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.music.BaseMusicViewHolder
    public void setData(PatchWall.Block block) {
        this.b = block;
        for (int i = 0; i < this.c.length; i++) {
            if (i < block.items.size()) {
                PatchWall.Item item = block.items.get(i);
                this.c[i].setData(item.title, item);
            }
        }
    }
}
