package com.xiaomi.micolauncher.module.homepage.viewholder.music;

import android.view.View;
import android.view.ViewGroup;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.PatchWall;
import com.xiaomi.micolauncher.module.homepage.view.MusicCircleItemView;

/* loaded from: classes3.dex */
public class MusicCircleViewHolder extends BaseMusicViewHolder {
    MusicCircleItemView[] c = new MusicCircleItemView[2];
    private final int d;

    public MusicCircleViewHolder(View view, int i) {
        super(view);
        this.c[0] = (MusicCircleItemView) view.findViewById(R.id.circle_first);
        this.c[1] = (MusicCircleItemView) view.findViewById(R.id.circle_second);
        this.d = i;
    }

    public void setBackground(int i) {
        this.itemView.setBackgroundResource(i);
    }

    public void setRightMargin(int i) {
        ((ViewGroup.MarginLayoutParams) this.itemView.getLayoutParams()).rightMargin = i;
    }

    @Override // com.xiaomi.micolauncher.common.view.BaseViewHolder
    protected void initViewInMain() {
        for (MusicCircleItemView musicCircleItemView : this.c) {
            musicCircleItemView.initInMain();
        }
        switch (this.d) {
            case 6:
                this.itemView.setBackgroundResource(R.drawable.radio_bg_second);
                return;
            case 7:
                this.itemView.setBackgroundResource(R.drawable.radio_bg_third);
                return;
            case 8:
            case 9:
            default:
                return;
            case 10:
                this.itemView.setBackgroundResource(R.drawable.singer_bg_second);
                return;
            case 11:
                this.itemView.setBackgroundResource(R.drawable.singer_bg_third);
                ((ViewGroup.MarginLayoutParams) this.itemView.getLayoutParams()).rightMargin = this.itemView.getResources().getDimensionPixelSize(R.dimen.music_find_item_margin_right);
                return;
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
