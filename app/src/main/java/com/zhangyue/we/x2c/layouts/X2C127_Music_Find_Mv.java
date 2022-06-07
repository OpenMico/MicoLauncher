package com.zhangyue.we.x2c.layouts;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.FrameLayout;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.homepage.view.MusicPatchWallLatestMv;
import com.zhangyue.we.x2c.IViewCreator;

/* loaded from: classes4.dex */
public class X2C127_Music_Find_Mv implements IViewCreator {
    @Override // com.zhangyue.we.x2c.IViewCreator
    public View createView(Context context) {
        Resources resources = context.getResources();
        MusicPatchWallLatestMv musicPatchWallLatestMv = new MusicPatchWallLatestMv(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.recommend_mv_layout_width), (int) resources.getDimension(R.dimen.recommend_layout_height));
        musicPatchWallLatestMv.setId(R.id.music_find_mv);
        layoutParams.rightMargin = (int) resources.getDimension(R.dimen.music_find_item_margin_right);
        musicPatchWallLatestMv.setLayoutParams(layoutParams);
        return musicPatchWallLatestMv;
    }
}
