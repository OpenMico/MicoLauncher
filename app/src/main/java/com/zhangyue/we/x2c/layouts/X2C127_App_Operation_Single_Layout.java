package com.zhangyue.we.x2c.layouts;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import com.google.android.material.badge.BadgeDrawable;
import com.xiaomi.micolauncher.R;
import com.zhangyue.we.x2c.IViewCreator;

/* loaded from: classes4.dex */
public class X2C127_App_Operation_Single_Layout implements IViewCreator {
    @Override // com.zhangyue.we.x2c.IViewCreator
    public View createView(Context context) {
        Resources resources = context.getResources();
        CardView cardView = new CardView(context);
        ViewGroup.LayoutParams layoutParams = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.operate_layout_width), (int) resources.getDimension(R.dimen.operate_single_app_height));
        cardView.setId(R.id.card_view);
        cardView.setLayoutParams(layoutParams);
        ImageView imageView = new ImageView(context);
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, -1);
        imageView.setId(R.id.operation_content_cover);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(layoutParams2);
        cardView.addView(imageView);
        ImageView imageView2 = new ImageView(context);
        FrameLayout.LayoutParams layoutParams3 = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.operate_bottom_app_icon_size), (int) resources.getDimension(R.dimen.operate_bottom_app_icon_size));
        imageView2.setId(R.id.operation_icon);
        layoutParams3.gravity = BadgeDrawable.BOTTOM_START;
        layoutParams3.leftMargin = (int) resources.getDimension(R.dimen.operate_bottom_app_icon_margin_start);
        layoutParams3.bottomMargin = (int) TypedValue.applyDimension(1, 13.0f, resources.getDisplayMetrics());
        imageView2.setLayoutParams(layoutParams3);
        cardView.addView(imageView2);
        TextView textView = new TextView(context);
        FrameLayout.LayoutParams layoutParams4 = new FrameLayout.LayoutParams(-1, -2);
        textView.setId(R.id.operation_title);
        layoutParams4.gravity = BadgeDrawable.BOTTOM_START;
        layoutParams4.leftMargin = (int) resources.getDimension(R.dimen.operate_bottom_app_name_margin_start);
        layoutParams4.bottomMargin = (int) resources.getDimension(R.dimen.operate_title_margin_bottom);
        textView.setTextColor(resources.getColor(R.color.app_operation_title_color));
        textView.setTextSize(0, resources.getDimension(R.dimen.operate_bottom_single_app_name_size));
        textView.setLayoutParams(layoutParams4);
        cardView.addView(textView);
        TextView textView2 = new TextView(context);
        FrameLayout.LayoutParams layoutParams5 = new FrameLayout.LayoutParams(-1, -2);
        textView2.setId(R.id.operation_sub);
        layoutParams5.gravity = BadgeDrawable.BOTTOM_START;
        layoutParams5.leftMargin = (int) resources.getDimension(R.dimen.operate_bottom_app_name_margin_start);
        layoutParams5.bottomMargin = (int) resources.getDimension(R.dimen.operate_bottom_app_introduction_margin_bottom);
        textView2.setTextColor(resources.getColor(R.color.app_operation_sub_color));
        textView2.setTextSize(0, resources.getDimension(R.dimen.operate_single_app_introduction_size));
        textView2.setLayoutParams(layoutParams5);
        cardView.addView(textView2);
        return cardView;
    }
}
