package com.zhangyue.we.x2c.layouts;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.FrameLayout;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.homepage.view.MusicPatchWallHotView;
import com.zhangyue.we.x2c.IViewCreator;

/* loaded from: classes4.dex */
public class X2C127_Music_Find_Hot implements IViewCreator {
    @Override // com.zhangyue.we.x2c.IViewCreator
    public View createView(Context context) {
        Resources resources = context.getResources();
        MusicPatchWallHotView musicPatchWallHotView = new MusicPatchWallHotView(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.music_list_width), -1);
        musicPatchWallHotView.setId(R.id.music_find_hot);
        layoutParams.rightMargin = (int) resources.getDimension(R.dimen.music_find_item_margin_right);
        layoutParams.leftMargin = (int) resources.getDimension(R.dimen.music_find_item_margin_right);
        musicPatchWallHotView.setLayoutParams(layoutParams);
        return musicPatchWallHotView;
    }
}
