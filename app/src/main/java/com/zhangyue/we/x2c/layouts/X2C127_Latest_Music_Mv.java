package com.zhangyue.we.x2c.layouts;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.FrameLayout;
import com.google.android.material.badge.BadgeDrawable;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.homepage.view.MusicPatchWallLatestMvItem;
import com.xiaomi.micolauncher.module.music.view.MusicPatchWallHeader;
import com.zhangyue.we.x2c.IViewCreator;

/* loaded from: classes4.dex */
public class X2C127_Latest_Music_Mv implements IViewCreator {
    @Override // com.zhangyue.we.x2c.IViewCreator
    public View createView(Context context) {
        Resources resources = context.getResources();
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        MusicPatchWallLatestMvItem musicPatchWallLatestMvItem = new MusicPatchWallLatestMvItem(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.mv_layout_width), (int) resources.getDimension(R.dimen.mv_layout_height));
        musicPatchWallLatestMvItem.setId(R.id.latest_music_mv1);
        layoutParams.gravity = BadgeDrawable.TOP_START;
        musicPatchWallLatestMvItem.setLayoutParams(layoutParams);
        frameLayout.addView(musicPatchWallLatestMvItem);
        MusicPatchWallHeader musicPatchWallHeader = new MusicPatchWallHeader(context);
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.mv_layout_width), (int) resources.getDimension(R.dimen.mv_layout_height));
        musicPatchWallHeader.setId(R.id.music_select);
        layoutParams2.gravity = BadgeDrawable.BOTTOM_START;
        musicPatchWallHeader.setLayoutParams(layoutParams2);
        frameLayout.addView(musicPatchWallHeader);
        MusicPatchWallLatestMvItem musicPatchWallLatestMvItem2 = new MusicPatchWallLatestMvItem(context);
        FrameLayout.LayoutParams layoutParams3 = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.mv_layout_width), (int) resources.getDimension(R.dimen.mv_layout_height));
        musicPatchWallLatestMvItem2.setId(R.id.latest_music_mv2);
        layoutParams3.gravity = BadgeDrawable.TOP_END;
        musicPatchWallLatestMvItem2.setLayoutParams(layoutParams3);
        frameLayout.addView(musicPatchWallLatestMvItem2);
        MusicPatchWallLatestMvItem musicPatchWallLatestMvItem3 = new MusicPatchWallLatestMvItem(context);
        FrameLayout.LayoutParams layoutParams4 = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.mv_layout_width), (int) resources.getDimension(R.dimen.mv_layout_height));
        musicPatchWallLatestMvItem3.setId(R.id.latest_music_mv3);
        layoutParams4.gravity = BadgeDrawable.BOTTOM_END;
        musicPatchWallLatestMvItem3.setLayoutParams(layoutParams4);
        frameLayout.addView(musicPatchWallLatestMvItem3);
        return frameLayout;
    }
}
