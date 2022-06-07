package com.zhangyue.we.x2c.layouts;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.FrameLayout;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.homepage.view.MusicCategoryItemView;
import com.zhangyue.we.x2c.IViewCreator;

/* loaded from: classes4.dex */
public class X2C127_Music_Category_Layout implements IViewCreator {
    @Override // com.zhangyue.we.x2c.IViewCreator
    public View createView(Context context) {
        Resources resources = context.getResources();
        FrameLayout frameLayout = new FrameLayout(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.recommend_width), (int) resources.getDimension(R.dimen.recommend_layout_height));
        frameLayout.setId(R.id.root_layout);
        layoutParams.rightMargin = (int) resources.getDimension(R.dimen.music_find_item_margin_right);
        frameLayout.setLayoutParams(layoutParams);
        MusicCategoryItemView musicCategoryItemView = new MusicCategoryItemView(context);
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.recommend_width), (int) resources.getDimension(R.dimen.recommend_width));
        musicCategoryItemView.setId(R.id.category_first);
        layoutParams2.gravity = 48;
        musicCategoryItemView.setLayoutParams(layoutParams2);
        frameLayout.addView(musicCategoryItemView);
        MusicCategoryItemView musicCategoryItemView2 = new MusicCategoryItemView(context);
        FrameLayout.LayoutParams layoutParams3 = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.recommend_width), (int) resources.getDimension(R.dimen.recommend_width));
        musicCategoryItemView2.setId(R.id.category_second);
        layoutParams3.gravity = 80;
        musicCategoryItemView2.setLayoutParams(layoutParams3);
        frameLayout.addView(musicCategoryItemView2);
        return frameLayout;
    }
}
