package com.zhangyue.we.x2c.layouts;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.FrameLayout;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.homepage.view.HotMusicItem;
import com.zhangyue.we.x2c.IViewCreator;

/* loaded from: classes4.dex */
public class X2C127_Hot_Music_List implements IViewCreator {
    @Override // com.zhangyue.we.x2c.IViewCreator
    public View createView(Context context) {
        Resources resources = context.getResources();
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.music_list_width), -1));
        HotMusicItem hotMusicItem = new HotMusicItem(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.music_list_width), (int) resources.getDimension(R.dimen.music_list_height));
        hotMusicItem.setId(R.id.hottest_item);
        hotMusicItem.setItemTag(R.drawable.music_rank_hottag);
        hotMusicItem.setItemTitleTextColor(resources.getColor(R.color.music_hottest_text_color));
        hotMusicItem.setTipTextColor(resources.getColor(R.color.white));
        hotMusicItem.setSecondIndexSrc(R.drawable.music_rank_hotsecond);
        hotMusicItem.setThirdIndexSrc(R.drawable.music_rank_hotthird);
        hotMusicItem.setBackgroundResource(R.drawable.music_rank_hotcard_bg);
        hotMusicItem.setLayoutParams(layoutParams);
        frameLayout.addView(hotMusicItem);
        HotMusicItem hotMusicItem2 = new HotMusicItem(context);
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.music_list_width), (int) resources.getDimension(R.dimen.music_list_height));
        hotMusicItem2.setId(R.id.newest_item);
        layoutParams2.topMargin = (int) resources.getDimension(R.dimen.music_list_item_margin_top);
        hotMusicItem2.setItemTag(R.drawable.music_rank_newtag);
        hotMusicItem2.setItemTitleTextColor(resources.getColor(R.color.music_newest_text_color));
        hotMusicItem2.setTipTextColor(resources.getColor(R.color.black));
        hotMusicItem2.setSecondIndexSrc(R.drawable.music_rank_newsecond);
        hotMusicItem2.setThirdIndexSrc(R.drawable.music_rank_newthird);
        hotMusicItem2.setBackgroundResource(R.drawable.music_rank_newcard_bg);
        hotMusicItem2.setLayoutParams(layoutParams2);
        frameLayout.addView(hotMusicItem2);
        return frameLayout;
    }
}
