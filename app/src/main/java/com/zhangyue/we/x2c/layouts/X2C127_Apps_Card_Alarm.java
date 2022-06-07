package com.zhangyue.we.x2c.layouts;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.xiaomi.micolauncher.R;
import com.zhangyue.we.x2c.IViewCreator;

/* loaded from: classes4.dex */
public class X2C127_Apps_Card_Alarm implements IViewCreator {
    @Override // com.zhangyue.we.x2c.IViewCreator
    public View createView(Context context) {
        Resources resources = context.getResources();
        RelativeLayout relativeLayout = new RelativeLayout(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) resources.getDimension(R.dimen.app_card_alarm_width), (int) resources.getDimension(R.dimen.app_item_height));
        layoutParams.topMargin = (int) resources.getDimension(R.dimen.app_item_margin_top);
        relativeLayout.setLayoutParams(layoutParams);
        relativeLayout.setPadding(0, 0, 0, (int) resources.getDimension(R.dimen.list_app_item_padding_bottom));
        View view = new View(context);
        ViewGroup.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-1, (int) resources.getDimension(R.dimen.app_card_alarm_height));
        view.setId(R.id.vBg);
        view.setBackgroundResource(R.drawable.bg_app_card_alarm);
        view.setLayoutParams(layoutParams2);
        relativeLayout.addView(view);
        TextView textView = new TextView(context);
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-2, -2);
        textView.setId(R.id.tvTime);
        layoutParams3.addRule(18, R.id.vBg);
        layoutParams3.addRule(6, R.id.vBg);
        layoutParams3.leftMargin = (int) resources.getDimension(R.dimen.app_card_alarm_time_margin_left);
        layoutParams3.topMargin = (int) resources.getDimension(R.dimen.app_card_alarm_time_margin_top);
        textView.setTextColor(Color.parseColor("#000000"));
        textView.setTextSize(0, resources.getDimension(R.dimen.app_card_alarm_time_text_size));
        textView.setLayoutParams(layoutParams3);
        relativeLayout.addView(textView);
        TextView textView2 = new TextView(context);
        RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(-2, -2);
        textView2.setId(R.id.tvMsg);
        layoutParams4.addRule(18, R.id.vBg);
        layoutParams4.addRule(8, R.id.vBg);
        layoutParams4.leftMargin = (int) resources.getDimension(R.dimen.app_card_alarm_time_margin_left);
        layoutParams4.bottomMargin = (int) resources.getDimension(R.dimen.app_card_alarm_time_margin_bottom);
        textView2.setEllipsize(TextUtils.TruncateAt.END);
        textView2.setMaxWidth((int) resources.getDimension(R.dimen.app_card_alarm_msg_max_width));
        textView2.setMaxLines(1);
        textView2.setTextSize(0, resources.getDimension(R.dimen.app_card_alarm_msg_text_size));
        textView2.setLayoutParams(layoutParams4);
        relativeLayout.addView(textView2);
        ImageView imageView = new ImageView(context);
        RelativeLayout.LayoutParams layoutParams5 = new RelativeLayout.LayoutParams((int) resources.getDimension(R.dimen.app_card_alarm_time_icon_size), (int) resources.getDimension(R.dimen.app_card_alarm_time_icon_size));
        layoutParams5.addRule(6, R.id.vBg);
        layoutParams5.addRule(19, R.id.vBg);
        layoutParams5.addRule(8, R.id.vBg);
        layoutParams5.rightMargin = (int) resources.getDimension(R.dimen.app_card_alarm_time_icon_margin_end);
        imageView.setImageResource(R.drawable.ic_clock_background);
        imageView.setLayoutParams(layoutParams5);
        relativeLayout.addView(imageView);
        TextView textView3 = new TextView(context);
        RelativeLayout.LayoutParams layoutParams6 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams6.addRule(3, R.id.vBg);
        layoutParams6.addRule(14, -1);
        layoutParams6.topMargin = (int) resources.getDimension(R.dimen.app_card_item_title_margin_top);
        textView3.setText(R.string.label_alarm);
        textView3.setTextColor(resources.getColor(R.color.app_name_color));
        textView3.setTextSize(0, resources.getDimension(R.dimen.app_name_text_size));
        textView3.setLayoutParams(layoutParams6);
        relativeLayout.addView(textView3);
        return relativeLayout;
    }
}
