package com.zhangyue.we.x2c.layouts;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.xiaomi.micolauncher.R;
import com.zhangyue.we.x2c.IViewCreator;

/* loaded from: classes4.dex */
public class X2C127_Guess_You_Like_Music implements IViewCreator {
    @Override // com.zhangyue.we.x2c.IViewCreator
    public View createView(Context context) {
        Resources resources = context.getResources();
        RelativeLayout relativeLayout = new RelativeLayout(context);
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams((int) resources.getDimension(R.dimen.recommend_layout_width), (int) resources.getDimension(R.dimen.recommend_layout_height)));
        ImageView imageView = new ImageView(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) resources.getDimension(R.dimen.recommend_layout_width), (int) resources.getDimension(R.dimen.recommend_layout_height));
        imageView.setId(R.id.like_bg);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(layoutParams);
        relativeLayout.addView(imageView);
        ImageView imageView2 = new ImageView(context);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams((int) resources.getDimension(R.dimen.recommend_layout_width), (int) resources.getDimension(R.dimen.recommend_layout_height));
        imageView2.setBackgroundResource(R.drawable.like_bg);
        imageView2.setLayoutParams(layoutParams2);
        relativeLayout.addView(imageView2);
        ImageView imageView3 = new ImageView(context);
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams((int) resources.getDimension(R.dimen.recommend_big_img_size), (int) resources.getDimension(R.dimen.recommend_big_img_size));
        imageView3.setId(R.id.like_img_1);
        imageView3.setBackgroundResource(R.drawable.app_icon_placeholder);
        layoutParams3.topMargin = (int) resources.getDimension(R.dimen.music_find_like_margin_top);
        layoutParams3.leftMargin = (int) resources.getDimension(R.dimen.music_find_like_margin_start);
        imageView3.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView3.setLayoutParams(layoutParams3);
        relativeLayout.addView(imageView3);
        ImageView imageView4 = new ImageView(context);
        RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams((int) resources.getDimension(R.dimen.recommend_small_img_width), (int) resources.getDimension(R.dimen.recommend_small_img_height));
        imageView4.setId(R.id.like_img_2);
        layoutParams4.addRule(18, R.id.like_img_1);
        layoutParams4.addRule(3, R.id.like_img_1);
        layoutParams4.topMargin = (int) resources.getDimension(R.dimen.recommend_small_img_margin);
        imageView4.setImageResource(R.drawable.app_icon_placeholder);
        imageView4.setLayoutParams(layoutParams4);
        relativeLayout.addView(imageView4);
        ImageView imageView5 = new ImageView(context);
        RelativeLayout.LayoutParams layoutParams5 = new RelativeLayout.LayoutParams((int) resources.getDimension(R.dimen.recommend_small_img_width), (int) resources.getDimension(R.dimen.recommend_small_img_height));
        imageView5.setId(R.id.like_img_3);
        layoutParams5.addRule(17, R.id.like_img_2);
        layoutParams5.addRule(6, R.id.like_img_2);
        layoutParams5.leftMargin = (int) resources.getDimension(R.dimen.recommend_small_img_margin);
        imageView5.setImageResource(R.drawable.app_icon_placeholder);
        imageView5.setLayoutParams(layoutParams5);
        relativeLayout.addView(imageView5);
        ImageView imageView6 = new ImageView(context);
        RelativeLayout.LayoutParams layoutParams6 = new RelativeLayout.LayoutParams((int) resources.getDimension(R.dimen.recommend_small_img_width), (int) resources.getDimension(R.dimen.recommend_small_img_height));
        imageView6.setId(R.id.like_img_4);
        layoutParams6.addRule(17, R.id.like_img_3);
        layoutParams6.addRule(6, R.id.like_img_3);
        layoutParams6.leftMargin = (int) resources.getDimension(R.dimen.recommend_small_img_margin);
        imageView6.setImageResource(R.drawable.app_icon_placeholder);
        imageView6.setLayoutParams(layoutParams6);
        relativeLayout.addView(imageView6);
        ImageView imageView7 = new ImageView(context);
        RelativeLayout.LayoutParams layoutParams7 = new RelativeLayout.LayoutParams((int) resources.getDimension(R.dimen.recommend_small_img_size), (int) resources.getDimension(R.dimen.recommend_small_img_size));
        imageView7.setId(R.id.like_img_5);
        layoutParams7.addRule(18, R.id.like_img_4);
        layoutParams7.addRule(2, R.id.like_img_4);
        layoutParams7.bottomMargin = (int) resources.getDimension(R.dimen.recommend_small_img_margin);
        imageView7.setImageResource(R.drawable.app_icon_placeholder);
        imageView7.setLayoutParams(layoutParams7);
        relativeLayout.addView(imageView7);
        ImageView imageView8 = new ImageView(context);
        RelativeLayout.LayoutParams layoutParams8 = new RelativeLayout.LayoutParams((int) resources.getDimension(R.dimen.recommend_small_img_size), (int) resources.getDimension(R.dimen.recommend_small_img_size));
        imageView8.setId(R.id.like_img_6);
        layoutParams8.addRule(18, R.id.like_img_5);
        layoutParams8.addRule(2, R.id.like_img_5);
        layoutParams8.bottomMargin = (int) resources.getDimension(R.dimen.recommend_small_img_margin);
        imageView8.setImageResource(R.drawable.app_icon_placeholder);
        imageView8.setLayoutParams(layoutParams8);
        relativeLayout.addView(imageView8);
        ImageView imageView9 = new ImageView(context);
        RelativeLayout.LayoutParams layoutParams9 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams9.addRule(21, -1);
        layoutParams9.addRule(12, -1);
        imageView9.setBackgroundResource(R.drawable.music_daily_lan);
        imageView9.setLayoutParams(layoutParams9);
        relativeLayout.addView(imageView9);
        ImageView imageView10 = new ImageView(context);
        RelativeLayout.LayoutParams layoutParams10 = new RelativeLayout.LayoutParams((int) resources.getDimension(R.dimen.recommend_audio_status_size), (int) resources.getDimension(R.dimen.recommend_audio_status_size));
        imageView10.setId(R.id.iv_play_state);
        layoutParams10.addRule(12, -1);
        layoutParams10.addRule(19, R.id.like_img_4);
        layoutParams10.bottomMargin = (int) resources.getDimension(R.dimen.music_find_play_state_margin_bottom);
        imageView10.setImageResource(R.drawable.music_daily_play);
        imageView10.setLayoutParams(layoutParams10);
        relativeLayout.addView(imageView10);
        ImageView imageView11 = new ImageView(context);
        RelativeLayout.LayoutParams layoutParams11 = new RelativeLayout.LayoutParams(-2, (int) resources.getDimension(R.dimen.recommend_audio_data_height));
        imageView11.setId(R.id.date_ten_month_iv);
        layoutParams11.addRule(3, R.id.like_img_2);
        layoutParams11.addRule(20, -1);
        layoutParams11.topMargin = (int) resources.getDimension(R.dimen.date_img_margin_top);
        layoutParams11.leftMargin = (int) resources.getDimension(R.dimen.date_img_margin_left);
        imageView11.setLayoutParams(layoutParams11);
        relativeLayout.addView(imageView11);
        ImageView imageView12 = new ImageView(context);
        RelativeLayout.LayoutParams layoutParams12 = new RelativeLayout.LayoutParams(-2, (int) resources.getDimension(R.dimen.recommend_audio_data_height));
        imageView12.setId(R.id.date_single_month_iv);
        layoutParams12.addRule(6, R.id.date_ten_month_iv);
        layoutParams12.addRule(8, R.id.date_ten_month_iv);
        layoutParams12.addRule(17, R.id.date_ten_month_iv);
        layoutParams12.leftMargin = (int) resources.getDimension(R.dimen.recommend_audio_data_margin);
        imageView12.setLayoutParams(layoutParams12);
        relativeLayout.addView(imageView12);
        ImageView imageView13 = new ImageView(context);
        RelativeLayout.LayoutParams layoutParams13 = new RelativeLayout.LayoutParams(-2, -2);
        imageView13.setId(R.id.date_pointer_iv);
        layoutParams13.addRule(8, R.id.date_single_month_iv);
        layoutParams13.addRule(17, R.id.date_single_month_iv);
        imageView13.setImageResource(R.drawable.music_like_dian);
        layoutParams13.leftMargin = (int) resources.getDimension(R.dimen.recommend_audio_data_pointer_margin);
        imageView13.setLayoutParams(layoutParams13);
        relativeLayout.addView(imageView13);
        ImageView imageView14 = new ImageView(context);
        RelativeLayout.LayoutParams layoutParams14 = new RelativeLayout.LayoutParams(-2, (int) resources.getDimension(R.dimen.recommend_audio_data_height));
        imageView14.setId(R.id.date_ten_day_iv);
        layoutParams14.addRule(6, R.id.date_single_month_iv);
        layoutParams14.addRule(8, R.id.date_single_month_iv);
        layoutParams14.addRule(17, R.id.date_pointer_iv);
        layoutParams14.leftMargin = (int) resources.getDimension(R.dimen.recommend_audio_data_pointer_margin);
        imageView14.setLayoutParams(layoutParams14);
        relativeLayout.addView(imageView14);
        ImageView imageView15 = new ImageView(context);
        RelativeLayout.LayoutParams layoutParams15 = new RelativeLayout.LayoutParams(-2, (int) resources.getDimension(R.dimen.recommend_audio_data_height));
        imageView15.setId(R.id.date_single_day_iv);
        layoutParams15.addRule(6, R.id.date_ten_day_iv);
        layoutParams15.addRule(8, R.id.date_ten_day_iv);
        layoutParams15.addRule(17, R.id.date_ten_day_iv);
        layoutParams15.leftMargin = (int) resources.getDimension(R.dimen.recommend_audio_data_margin);
        imageView15.setLayoutParams(layoutParams15);
        relativeLayout.addView(imageView15);
        TextView textView = new TextView(context);
        RelativeLayout.LayoutParams layoutParams16 = new RelativeLayout.LayoutParams(-2, -2);
        textView.setId(R.id.title_tv);
        layoutParams16.addRule(18, R.id.date_ten_month_iv);
        layoutParams16.addRule(3, R.id.date_ten_month_iv);
        layoutParams16.topMargin = (int) resources.getDimension(R.dimen.guess_like_margin_top);
        textView.setText(R.string.music_recommend_list_title);
        textView.setTextSize(0, resources.getDimension(R.dimen.recommend_title_text_size));
        textView.setTextColor(resources.getColor(R.color.white));
        textView.setLayoutParams(layoutParams16);
        relativeLayout.addView(textView);
        return relativeLayout;
    }
}