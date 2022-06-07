package com.xiaomi.micolauncher.module.homepage.viewholder.music;

import android.view.View;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.module.homepage.view.MusicCategoryItemView;

/* loaded from: classes3.dex */
public class MusicCategoryViewHolder extends BaseMusicViewHolder {
    MusicCategoryItemView[] c = new MusicCategoryItemView[2];

    public MusicCategoryViewHolder(View view) {
        super(view);
        this.c[0] = (MusicCategoryItemView) view.findViewById(R.id.category_first);
        this.c[1] = (MusicCategoryItemView) view.findViewById(R.id.category_second);
    }

    @Override // com.xiaomi.micolauncher.common.view.BaseViewHolder
    protected void initViewInMain() {
        for (MusicCategoryItemView musicCategoryItemView : this.c) {
            musicCategoryItemView.initInMain();
        }
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.music.BaseMusicViewHolder
    public void setData(PatchWall.Block block) {
        for (int i = 0; i < this.c.length; i++) {
            if (i < block.items.size()) {
                this.c[i].setData(block.title, block.items.get(i));
            }
        }
    }
}
