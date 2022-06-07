package com.zhangyue.we.x2c.layouts;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.homepage.view.AppOperateView;
import com.zhangyue.we.x2c.IViewCreator;

/* loaded from: classes4.dex */
public class X2C127_App_Operation_List_Layout implements IViewCreator {
    @Override // com.zhangyue.we.x2c.IViewCreator
    public View createView(Context context) {
        Resources resources = context.getResources();
        LinearLayout linearLayout = new LinearLayout(context);
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) resources.getDimension(R.dimen.operate_layout_width), (int) resources.getDimension(R.dimen.app_operate_list_height));
        linearLayout.setBackgroundResource(R.drawable.operate_layout_bg);
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setPadding((int) resources.getDimension(R.dimen.operate_app_padding_start), (int) resources.getDimension(R.dimen.operate_app_padding_top), (int) resources.getDimension(R.dimen.operate_app_padding_start), 0);
        TextView textView = new TextView(context);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, (int) resources.getDimension(R.dimen.wake_guide_item_bg_radius));
        textView.setId(R.id.tvTitle);
        textView.setText(R.string.title_apps_recommendation);
        textView.setTextColor(resources.getColor(R.color.app_card_title_text_color));
        textView.setTextSize(0, resources.getDimension(R.dimen.app_card_title_text_size));
        textView.setLayoutParams(layoutParams2);
        linearLayout.addView(textView);
        AppOperateView appOperateView = new AppOperateView(context);
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-2, -2);
        appOperateView.setId(R.id.app_list_first);
        appOperateView.setLayoutParams(layoutParams3);
        linearLayout.addView(appOperateView);
        AppOperateView appOperateView2 = new AppOperateView(context);
        LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(-2, -2);
        appOperateView2.setId(R.id.app_list_second);
        appOperateView2.setLayoutParams(layoutParams4);
        linearLayout.addView(appOperateView2);
        AppOperateView appOperateView3 = new AppOperateView(context);
        LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(-2, -2);
        appOperateView3.setId(R.id.app_list_third);
        appOperateView3.setLayoutParams(layoutParams5);
        linearLayout.addView(appOperateView3);
        AppOperateView appOperateView4 = new AppOperateView(context);
        LinearLayout.LayoutParams layoutParams6 = new LinearLayout.LayoutParams(-2, -2);
        appOperateView4.setId(R.id.app_list_fourth);
        appOperateView4.setLayoutParams(layoutParams6);
        linearLayout.addView(appOperateView4);
        TextView textView2 = new TextView(context);
        LinearLayout.LayoutParams layoutParams7 = new LinearLayout.LayoutParams(-1, -2);
        textView2.setId(R.id.special_title);
        layoutParams7.leftMargin = (int) resources.getDimension(R.dimen.operate_text_margin_start);
        layoutParams7.topMargin = (int) resources.getDimension(R.dimen.operate_app_title_margin_top);
        textView2.setText("新品尝鲜");
        textView2.setTextColor(resources.getColor(R.color.color_272727));
        textView2.setTextSize(0, resources.getDimension(R.dimen.operate_bottom_app_name_size));
        textView2.setVisibility(4);
        textView2.setLayoutParams(layoutParams7);
        linearLayout.addView(textView2);
        TextView textView3 = new TextView(context);
        LinearLayout.LayoutParams layoutParams8 = new LinearLayout.LayoutParams(-2, -2);
        textView3.setId(R.id.description_tv);
        layoutParams8.leftMargin = (int) resources.getDimension(R.dimen.operate_text_margin_start);
        textView3.setTextColor(resources.getColor(R.color.color_898F9A));
        textView3.setTextSize(0, resources.getDimension(R.dimen.operate_desc_size));
        textView3.setVisibility(4);
        textView3.setLayoutParams(layoutParams8);
        linearLayout.addView(textView3);
        return linearLayout;
    }
}
