package com.zhangyue.we.x2c.layouts;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.badge.BadgeDrawable;
import com.xiaomi.micolauncher.R;
import com.zhangyue.we.x2c.IViewCreator;

/* loaded from: classes4.dex */
public class X2C127_Child_App_Operation_Single_Layout implements IViewCreator {
    @Override // com.zhangyue.we.x2c.IViewCreator
    public View createView(Context context) {
        Resources resources = context.getResources();
        FrameLayout frameLayout = new FrameLayout(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.child_operate_layout_width), -1);
        layoutParams.setMargins((int) resources.getDimension(R.dimen.child_operate_layout_padding), (int) resources.getDimension(R.dimen.child_operate_layout_padding), (int) resources.getDimension(R.dimen.child_operate_layout_padding), (int) resources.getDimension(R.dimen.child_operate_layout_padding));
        frameLayout.setBackgroundResource(R.drawable.child_operate_layout_bg);
        frameLayout.setLayoutParams(layoutParams);
        ImageView imageView = new ImageView(context);
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, (int) resources.getDimension(R.dimen.child_operate_single_cover_height));
        imageView.setId(R.id.operation_content_cover);
        imageView.setLayoutParams(layoutParams2);
        frameLayout.addView(imageView);
        ImageView imageView2 = new ImageView(context);
        FrameLayout.LayoutParams layoutParams3 = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.child_operate_bottom_app_icon_size), (int) resources.getDimension(R.dimen.child_operate_bottom_app_icon_size));
        imageView2.setId(R.id.operation_icon);
        layoutParams3.gravity = BadgeDrawable.BOTTOM_END;
        layoutParams3.rightMargin = (int) resources.getDimension(R.dimen.child_operate_bottom_app_icon_margin_end);
        layoutParams3.bottomMargin = (int) resources.getDimension(R.dimen.child_operate_bottom_app_icon_margin_bottom);
        imageView2.setLayoutParams(layoutParams3);
        frameLayout.addView(imageView2);
        TextView textView = new TextView(context);
        FrameLayout.LayoutParams layoutParams4 = new FrameLayout.LayoutParams(-1, -2);
        textView.setId(R.id.operation_title);
        layoutParams4.gravity = BadgeDrawable.BOTTOM_START;
        layoutParams4.leftMargin = (int) resources.getDimension(R.dimen.child_operate_bottom_app_name_margin_start);
        layoutParams4.rightMargin = (int) resources.getDimension(R.dimen.child_operate_bottom_app_margin_end);
        layoutParams4.bottomMargin = (int) resources.getDimension(R.dimen.child_operate_title_margin_bottom);
        textView.setTextColor(resources.getColor(R.color.color_1F1F1F));
        textView.setTextSize(0, resources.getDimension(R.dimen.child_operate_bottom_app_name_size));
        textView.setLayoutParams(layoutParams4);
        frameLayout.addView(textView);
        TextView textView2 = new TextView(context);
        FrameLayout.LayoutParams layoutParams5 = new FrameLayout.LayoutParams(-1, -2);
        textView2.setId(R.id.operation_sub);
        layoutParams5.gravity = BadgeDrawable.BOTTOM_START;
        layoutParams5.leftMargin = (int) resources.getDimension(R.dimen.child_operate_bottom_app_name_margin_start);
        layoutParams5.rightMargin = (int) resources.getDimension(R.dimen.child_operate_bottom_app_margin_end);
        layoutParams5.bottomMargin = (int) resources.getDimension(R.dimen.child_operate_bottom_app_introduction_margin_bottom);
        textView2.setTextColor(resources.getColor(R.color.color_898F9A));
        textView2.setTextSize(0, resources.getDimension(R.dimen.child_operate_app_introduction_size));
        textView2.setLayoutParams(layoutParams5);
        frameLayout.addView(textView2);
        return frameLayout;
    }
}
