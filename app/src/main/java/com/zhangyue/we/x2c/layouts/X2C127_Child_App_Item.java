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
public class X2C127_Child_App_Item implements IViewCreator {
    @Override // com.zhangyue.we.x2c.IViewCreator
    public View createView(Context context) {
        Resources resources = context.getResources();
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.child_list_app_item_width), (int) resources.getDimension(R.dimen.child_app_item_height)));
        frameLayout.setPadding(0, 0, 0, (int) resources.getDimension(R.dimen.child_list_app_item_padding_bottom));
        ImageView imageView = new ImageView(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.child_app_icon_size), (int) resources.getDimension(R.dimen.child_app_icon_size));
        imageView.setId(R.id.app_icon);
        layoutParams.gravity = 1;
        imageView.setLayoutParams(layoutParams);
        frameLayout.addView(imageView);
        TextView textView = new TextView(context);
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, -2);
        textView.setId(R.id.app_title);
        layoutParams2.gravity = 80;
        textView.setGravity(1);
        textView.setTextColor(resources.getColor(R.color.color_272727));
        textView.setTextSize(0, resources.getDimension(R.dimen.child_app_name_text_size));
        textView.setLayoutParams(layoutParams2);
        frameLayout.addView(textView);
        ImageView imageView2 = new ImageView(context);
        FrameLayout.LayoutParams layoutParams3 = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.delete_icon_size), (int) resources.getDimension(R.dimen.delete_icon_size));
        imageView2.setId(R.id.delete_iv);
        layoutParams3.leftMargin = (int) resources.getDimension(R.dimen.list_app_item_padding_horizontal);
        layoutParams3.gravity = BadgeDrawable.TOP_START;
        imageView2.setBackgroundResource(0);
        imageView2.setLayoutParams(layoutParams3);
        frameLayout.addView(imageView2);
        return frameLayout;
    }
}
