package com.zhangyue.we.x2c.layouts;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.xiaomi.micolauncher.R;
import com.zhangyue.we.x2c.IViewCreator;

/* loaded from: classes4.dex */
public class X2C127_App_Item implements IViewCreator {
    @Override // com.zhangyue.we.x2c.IViewCreator
    public View createView(Context context) {
        Resources resources = context.getResources();
        RelativeLayout relativeLayout = new RelativeLayout(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) resources.getDimension(R.dimen.list_app_item_width), (int) resources.getDimension(R.dimen.app_item_height));
        layoutParams.topMargin = (int) resources.getDimension(R.dimen.app_card_weather_margin_top);
        relativeLayout.setLayoutParams(layoutParams);
        ImageView imageView = new ImageView(context);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams((int) resources.getDimension(R.dimen.app_icon_size), (int) resources.getDimension(R.dimen.app_icon_size));
        imageView.setId(R.id.app_icon);
        layoutParams2.addRule(14, -1);
        imageView.setLayoutParams(layoutParams2);
        relativeLayout.addView(imageView);
        TextView textView = new TextView(context);
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-2, -2);
        textView.setId(R.id.app_title);
        layoutParams3.addRule(3, R.id.app_icon);
        layoutParams3.topMargin = (int) resources.getDimension(R.dimen.app_icon_title_margin);
        textView.setGravity(1);
        textView.setTextColor(resources.getColor(R.color.app_name_color));
        textView.setTextSize(0, resources.getDimension(R.dimen.app_name_text_size));
        layoutParams3.addRule(14, -1);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setMaxWidth((int) resources.getDimension(R.dimen.app_icon_size));
        textView.setLayoutParams(layoutParams3);
        relativeLayout.addView(textView);
        ImageView imageView2 = new ImageView(context);
        RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams((int) resources.getDimension(R.dimen.delete_icon_size), (int) resources.getDimension(R.dimen.delete_icon_size));
        imageView2.setId(R.id.delete_iv);
        imageView2.setBackgroundResource(0);
        imageView2.setLayoutParams(layoutParams4);
        relativeLayout.addView(imageView2);
        return relativeLayout;
    }
}
