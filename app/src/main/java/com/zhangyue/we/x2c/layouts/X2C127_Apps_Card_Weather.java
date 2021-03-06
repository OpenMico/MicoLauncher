package com.zhangyue.we.x2c.layouts;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.view.RoundImageView;
import com.zhangyue.we.x2c.IViewCreator;

/* loaded from: classes4.dex */
public class X2C127_Apps_Card_Weather implements IViewCreator {
    @Override // com.zhangyue.we.x2c.IViewCreator
    public View createView(Context context) {
        Resources resources = context.getResources();
        RelativeLayout relativeLayout = new RelativeLayout(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) resources.getDimension(R.dimen.app_card_weather_width), -1);
        layoutParams.topMargin = (int) resources.getDimension(R.dimen.app_card_weather_margin_top);
        relativeLayout.setLayoutParams(layoutParams);
        RoundImageView roundImageView = new RoundImageView(context);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams((int) resources.getDimension(R.dimen.app_card_weather_width), (int) resources.getDimension(R.dimen.app_card_weather_height));
        roundImageView.setId(R.id.ivBackground);
        roundImageView.setBackgroundResource(R.drawable.bg_app_card_weather);
        roundImageView.setLayoutParams(layoutParams2);
        relativeLayout.addView(roundImageView);
        TextView textView = new TextView(context);
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-2, -2);
        textView.setId(R.id.tvWeatherTemperature);
        layoutParams3.addRule(20, -1);
        layoutParams3.addRule(10, -1);
        layoutParams3.leftMargin = (int) resources.getDimension(R.dimen.app_card_weather_temperature_margin);
        layoutParams3.topMargin = (int) resources.getDimension(R.dimen.app_card_weather_temperature_margin);
        textView.setTextColor(resources.getColor(R.color.white));
        textView.setTextSize(0, resources.getDimension(R.dimen.app_weather_temperature_size));
        textView.setLayoutParams(layoutParams3);
        relativeLayout.addView(textView);
        ImageView imageView = new ImageView(context);
        RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams((int) resources.getDimension(R.dimen.app_card_weather_unit_size), (int) resources.getDimension(R.dimen.app_card_weather_unit_size));
        imageView.setId(R.id.ivWeatherUnit);
        layoutParams4.addRule(6, R.id.tvWeatherTemperature);
        layoutParams4.topMargin = (int) TypedValue.applyDimension(1, 25.0f, resources.getDisplayMetrics());
        layoutParams4.addRule(17, R.id.tvWeatherTemperature);
        imageView.setImageResource(R.drawable.sh_c_degree);
        imageView.setVisibility(4);
        imageView.setLayoutParams(layoutParams4);
        relativeLayout.addView(imageView);
        ImageView imageView2 = new ImageView(context);
        RelativeLayout.LayoutParams layoutParams5 = new RelativeLayout.LayoutParams((int) resources.getDimension(R.dimen.app_card_weather_icon_big_size), (int) resources.getDimension(R.dimen.app_card_weather_icon_big_size));
        imageView2.setId(R.id.ivWeather);
        layoutParams5.addRule(10, -1);
        layoutParams5.addRule(21, -1);
        layoutParams5.topMargin = (int) resources.getDimension(R.dimen.app_card_weather_icon_margin_top);
        layoutParams5.rightMargin = (int) resources.getDimension(R.dimen.app_card_weather_icon_margin_right);
        imageView2.setLayoutParams(layoutParams5);
        relativeLayout.addView(imageView2);
        TextView textView2 = new TextView(context);
        RelativeLayout.LayoutParams layoutParams6 = new RelativeLayout.LayoutParams(-2, -2);
        textView2.setId(R.id.tvWeatherDesc);
        layoutParams6.addRule(3, R.id.tvWeatherTemperature);
        layoutParams6.addRule(18, R.id.tvWeatherTemperature);
        layoutParams6.topMargin = (int) resources.getDimension(R.dimen.app_card_weather_des_margin_top);
        textView2.setTextSize(0, resources.getDimension(R.dimen.app_weather_info_text_size));
        textView2.setLineSpacing((int) resources.getDimension(R.dimen.mc10dp), 1.0f);
        textView2.setTextColor(resources.getColor(R.color.white));
        textView2.setLayoutParams(layoutParams6);
        relativeLayout.addView(textView2);
        LinearLayout linearLayout = new LinearLayout(context);
        RelativeLayout.LayoutParams layoutParams7 = new RelativeLayout.LayoutParams(-1, -2);
        linearLayout.setId(R.id.llFutureWeathers);
        layoutParams7.addRule(3, R.id.tvWeatherDesc);
        layoutParams7.topMargin = (int) resources.getDimension(R.dimen.app_card_weather_future_margin_top);
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(layoutParams7);
        relativeLayout.addView(linearLayout);
        linearLayout.setPadding((int) resources.getDimension(R.dimen.app_card_weather_temperature_margin), 0, (int) resources.getDimension(R.dimen.app_card_weather_icon_margin_right), 0);
        TextView textView3 = new TextView(context);
        RelativeLayout.LayoutParams layoutParams8 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams8.addRule(3, R.id.ivBackground);
        layoutParams8.topMargin = (int) resources.getDimension(R.dimen.app_card_weather_bottom_des_margin_top);
        layoutParams8.addRule(14, -1);
        textView3.setText(R.string.label_weather);
        textView3.setTextColor(resources.getColor(R.color.app_name_color));
        textView3.setTextSize(0, resources.getDimension(R.dimen.app_name_text_size));
        textView3.setLayoutParams(layoutParams8);
        relativeLayout.addView(textView3);
        return relativeLayout;
    }
}
