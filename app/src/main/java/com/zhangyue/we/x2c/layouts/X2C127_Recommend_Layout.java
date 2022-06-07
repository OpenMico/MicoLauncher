package com.zhangyue.we.x2c.layouts;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.LinearLayout;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.homepage.view.MusicRecommendItemView;
import com.zhangyue.we.x2c.IViewCreator;

/* loaded from: classes4.dex */
public class X2C127_Recommend_Layout implements IViewCreator {
    @Override // com.zhangyue.we.x2c.IViewCreator
    public View createView(Context context) {
        Resources resources = context.getResources();
        LinearLayout linearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) resources.getDimension(R.dimen.recommend_width), (int) resources.getDimension(R.dimen.recommend_layout_height));
        linearLayout.setOrientation(1);
        layoutParams.rightMargin = (int) resources.getDimension(R.dimen.music_find_item_margin_right);
        linearLayout.setLayoutParams(layoutParams);
        MusicRecommendItemView musicRecommendItemView = new MusicRecommendItemView(context);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams((int) resources.getDimension(R.dimen.recommend_width), (int) resources.getDimension(R.dimen.recommend_height));
        musicRecommendItemView.setId(R.id.recommend_first);
        layoutParams2.gravity = 48;
        musicRecommendItemView.setLayoutParams(layoutParams2);
        linearLayout.addView(musicRecommendItemView);
        MusicRecommendItemView musicRecommendItemView2 = new MusicRecommendItemView(context);
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams((int) resources.getDimension(R.dimen.recommend_width), (int) resources.getDimension(R.dimen.recommend_height));
        musicRecommendItemView2.setId(R.id.recommend_second);
        layoutParams3.topMargin = (int) resources.getDimension(R.dimen.music_find_item_margin_right);
        layoutParams3.gravity = 80;
        musicRecommendItemView2.setLayoutParams(layoutParams3);
        linearLayout.addView(musicRecommendItemView2);
        return linearLayout;
    }
}
