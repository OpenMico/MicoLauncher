package com.zhangyue.we.x2c.layouts;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.homepage.view.AppOperateView;
import com.zhangyue.we.x2c.IViewCreator;

/* loaded from: classes4.dex */
public class X2C127_Child_App_Operation_List_Layout implements IViewCreator {
    @Override // com.zhangyue.we.x2c.IViewCreator
    public View createView(Context context) {
        Resources resources = context.getResources();
        LinearLayout linearLayout = new LinearLayout(context);
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) resources.getDimension(R.dimen.child_operate_layout_width), -1);
        linearLayout.setBackgroundResource(R.drawable.child_operate_layout_bg);
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setPadding((int) resources.getDimension(R.dimen.operate_app_padding_start), (int) resources.getDimension(R.dimen.child_operate_app_padding_top), 0, 0);
        AppOperateView appOperateView = new AppOperateView(context);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
        appOperateView.setId(R.id.app_list_first);
        appOperateView.setLayoutParams(layoutParams2);
        linearLayout.addView(appOperateView);
        AppOperateView appOperateView2 = new AppOperateView(context);
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-2, -2);
        appOperateView2.setId(R.id.app_list_second);
        appOperateView2.setLayoutParams(layoutParams3);
        linearLayout.addView(appOperateView2);
        AppOperateView appOperateView3 = new AppOperateView(context);
        LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(-2, -2);
        appOperateView3.setId(R.id.app_list_third);
        appOperateView3.setLayoutParams(layoutParams4);
        linearLayout.addView(appOperateView3);
        TextView textView = new TextView(context);
        LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(-1, -2);
        textView.setId(R.id.special_title);
        layoutParams5.topMargin = (int) resources.getDimension(R.dimen.child_operate_app_title_margin_top);
        layoutParams5.rightMargin = (int) resources.getDimension(R.dimen.child_operate_text_margin_start);
        textView.setText("新品尝鲜");
        textView.setTextColor(resources.getColor(R.color.color_1F1F1F));
        textView.setTextSize(0, resources.getDimension(R.dimen.child_operate_bottom_app_name_size));
        textView.setLayoutParams(layoutParams5);
        linearLayout.addView(textView);
        TextView textView2 = new TextView(context);
        LinearLayout.LayoutParams layoutParams6 = new LinearLayout.LayoutParams(-2, -2);
        textView2.setId(R.id.description_tv);
        layoutParams6.rightMargin = (int) resources.getDimension(R.dimen.child_operate_text_margin_start);
        textView2.setEllipsize(TextUtils.TruncateAt.END);
        textView2.setMaxLines(2);
        textView2.setTextColor(resources.getColor(R.color.color_898F9A));
        textView2.setTextSize(0, resources.getDimension(R.dimen.child_operate_desc_size));
        textView2.setLayoutParams(layoutParams6);
        linearLayout.addView(textView2);
        return linearLayout;
    }
}
