package com.zhangyue.we.x2c.layouts;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.homepage.view.MusicCircleItemView;
import com.zhangyue.we.x2c.IViewCreator;

/* loaded from: classes4.dex */
public class X2C127_Music_Radio_Layout implements IViewCreator {
    @Override // com.zhangyue.we.x2c.IViewCreator
    public View createView(Context context) {
        Resources resources = context.getResources();
        FrameLayout frameLayout = new FrameLayout(context);
        ViewGroup.LayoutParams layoutParams = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.music_radio_width), (int) resources.getDimension(R.dimen.radio_layout_height));
        frameLayout.setId(R.id.circle_root);
        frameLayout.setBackgroundResource(R.drawable.radio_bg_first);
        frameLayout.setLayoutParams(layoutParams);
        frameLayout.setPadding(0, (int) resources.getDimension(R.dimen.music_circle_margin_bottom), 0, 0);
        MusicCircleItemView musicCircleItemView = new MusicCircleItemView(context);
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.radio_item_width), (int) resources.getDimension(R.dimen.radio_item_height));
        musicCircleItemView.setId(R.id.circle_first);
        layoutParams2.gravity = 49;
        musicCircleItemView.setLayoutParams(layoutParams2);
        frameLayout.addView(musicCircleItemView);
        MusicCircleItemView musicCircleItemView2 = new MusicCircleItemView(context);
        FrameLayout.LayoutParams layoutParams3 = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.radio_item_width), (int) resources.getDimension(R.dimen.radio_item_height));
        musicCircleItemView2.setId(R.id.circle_second);
        layoutParams3.bottomMargin = (int) resources.getDimension(R.dimen.music_circle_margin_bottom);
        layoutParams3.gravity = 81;
        musicCircleItemView2.setLayoutParams(layoutParams3);
        frameLayout.addView(musicCircleItemView2);
        return frameLayout;
    }
}
