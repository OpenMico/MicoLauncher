package com.zhangyue.we.x2c.layouts;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.RelativeLayout;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.homepage.view.MusicPatchWallLikeView;
import com.zhangyue.we.x2c.IViewCreator;

/* loaded from: classes4.dex */
public class X2C127_Music_Find_Like implements IViewCreator {
    @Override // com.zhangyue.we.x2c.IViewCreator
    public View createView(Context context) {
        Resources resources = context.getResources();
        MusicPatchWallLikeView musicPatchWallLikeView = new MusicPatchWallLikeView(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) resources.getDimension(R.dimen.recommend_layout_width), (int) resources.getDimension(R.dimen.recommend_layout_height));
        musicPatchWallLikeView.setId(R.id.music_find_like);
        layoutParams.rightMargin = (int) resources.getDimension(R.dimen.music_find_item_margin_right);
        musicPatchWallLikeView.setLayoutParams(layoutParams);
        return musicPatchWallLikeView;
    }
}
