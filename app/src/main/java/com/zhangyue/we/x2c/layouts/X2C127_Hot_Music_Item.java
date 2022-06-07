package com.zhangyue.we.x2c.layouts;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.badge.BadgeDrawable;
import com.xiaomi.micolauncher.R;
import com.zhangyue.we.x2c.IViewCreator;

/* loaded from: classes4.dex */
public class X2C127_Hot_Music_Item implements IViewCreator {
    @Override // com.zhangyue.we.x2c.IViewCreator
    public View createView(Context context) {
        Resources resources = context.getResources();
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.music_list_width), (int) resources.getDimension(R.dimen.music_list_height)));
        TextView textView = new TextView(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, (int) resources.getDimension(R.dimen.music_list_title_height));
        textView.setId(R.id.first_music);
        layoutParams.topMargin = (int) resources.getDimension(R.dimen.music_list_title_margin_top);
        layoutParams.leftMargin = (int) resources.getDimension(R.dimen.music_list_title_margin_left);
        layoutParams.rightMargin = (int) resources.getDimension(R.dimen.music_list_title_margin_right);
        textView.setMaxLines(1);
        textView.setGravity(16);
        textView.setTextSize(0, resources.getDimension(R.dimen.music_list_title_text_size));
        textView.setLayoutParams(layoutParams);
        frameLayout.addView(textView);
        textView.setPadding((int) resources.getDimension(R.dimen.music_list_title_margin_title), 0, 0, 0);
        TextView textView2 = new TextView(context);
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, (int) resources.getDimension(R.dimen.music_list_title_height));
        textView2.setId(R.id.first_music_artist);
        layoutParams2.topMargin = (int) resources.getDimension(R.dimen.music_list_first_artist_margin_top);
        layoutParams2.leftMargin = (int) resources.getDimension(R.dimen.music_list_title_margin_left);
        layoutParams2.rightMargin = (int) resources.getDimension(R.dimen.music_list_title_margin_right);
        textView2.setMaxLines(1);
        textView2.setGravity(16);
        textView2.setTextSize(0, resources.getDimension(R.dimen.music_list_title_artist_size));
        textView2.setLayoutParams(layoutParams2);
        frameLayout.addView(textView2);
        textView2.setPadding((int) resources.getDimension(R.dimen.music_list_title_margin_title), 0, 0, 0);
        TextView textView3 = new TextView(context);
        FrameLayout.LayoutParams layoutParams3 = new FrameLayout.LayoutParams(-1, (int) resources.getDimension(R.dimen.music_list_title_height));
        textView3.setId(R.id.second_music);
        layoutParams3.topMargin = (int) resources.getDimension(R.dimen.music_list_third_artist_margin_top);
        layoutParams3.leftMargin = (int) resources.getDimension(R.dimen.music_list_title_margin_left);
        layoutParams3.rightMargin = (int) resources.getDimension(R.dimen.music_list_title_margin_right);
        textView3.setMaxLines(1);
        textView3.setGravity(16);
        textView3.setTextSize(0, resources.getDimension(R.dimen.music_list_tip_text_size));
        textView3.setLayoutParams(layoutParams3);
        frameLayout.addView(textView3);
        textView3.setPadding((int) resources.getDimension(R.dimen.music_list_title_margin_title), 0, 0, 0);
        TextView textView4 = new TextView(context);
        FrameLayout.LayoutParams layoutParams4 = new FrameLayout.LayoutParams(-1, (int) resources.getDimension(R.dimen.music_list_title_height));
        textView4.setId(R.id.third_music);
        layoutParams4.topMargin = (int) TypedValue.applyDimension(1, 206.0f, resources.getDisplayMetrics());
        layoutParams4.leftMargin = (int) resources.getDimension(R.dimen.music_list_title_margin_left);
        layoutParams4.rightMargin = (int) resources.getDimension(R.dimen.music_list_title_margin_right);
        textView4.setMaxLines(1);
        textView4.setGravity(16);
        textView4.setTextSize(0, resources.getDimension(R.dimen.music_list_tip_text_size));
        textView4.setLayoutParams(layoutParams4);
        frameLayout.addView(textView4);
        textView4.setPadding((int) resources.getDimension(R.dimen.music_list_title_margin_title), 0, 0, 0);
        ImageView imageView = new ImageView(context);
        FrameLayout.LayoutParams layoutParams5 = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.hot_music_item_mark_iv_width), (int) resources.getDimension(R.dimen.hot_music_item_mark_iv_height));
        imageView.setId(R.id.markIv);
        layoutParams5.gravity = BadgeDrawable.TOP_END;
        layoutParams5.topMargin = (int) resources.getDimension(R.dimen.music_list_mark_margin_top);
        imageView.setLayoutParams(layoutParams5);
        frameLayout.addView(imageView);
        return frameLayout;
    }
}
