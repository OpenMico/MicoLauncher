package com.zhangyue.we.x2c.layouts;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.view.GravityCompat;
import com.xiaomi.micolauncher.R;
import com.zhangyue.we.x2c.IViewCreator;

/* loaded from: classes4.dex */
public class X2C127_Music_Category_Item implements IViewCreator {
    @Override // com.zhangyue.we.x2c.IViewCreator
    public View createView(Context context) {
        Resources resources = context.getResources();
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.recommend_width), (int) resources.getDimension(R.dimen.recommend_width)));
        ImageView imageView = new ImageView(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.recommend_width), (int) resources.getDimension(R.dimen.recommend_width));
        imageView.setId(R.id.cover_img);
        imageView.setImageResource(R.drawable.rectangle_loading);
        imageView.setLayoutParams(layoutParams);
        frameLayout.addView(imageView);
        TextView textView = new TextView(context);
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, -2);
        textView.setId(R.id.title_tv);
        textView.setGravity(GravityCompat.START);
        layoutParams2.leftMargin = (int) resources.getDimension(R.dimen.category_title_margin_left);
        layoutParams2.topMargin = (int) resources.getDimension(R.dimen.category_title_margin_top);
        textView.setTextColor(resources.getColor(R.color.white));
        textView.setTextSize(0, resources.getDimension(R.dimen.category_title_size));
        textView.setLayoutParams(layoutParams2);
        frameLayout.addView(textView);
        LinearLayout linearLayout = new LinearLayout(context);
        FrameLayout.LayoutParams layoutParams3 = new FrameLayout.LayoutParams(-2, -2);
        linearLayout.setOrientation(0);
        layoutParams3.gravity = 80;
        layoutParams3.bottomMargin = (int) resources.getDimension(R.dimen.category_mark_margin_bottom);
        layoutParams3.leftMargin = (int) resources.getDimension(R.dimen.category_title_margin_left);
        linearLayout.setLayoutParams(layoutParams3);
        frameLayout.addView(linearLayout);
        TextView textView2 = new TextView(context);
        LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(-2, (int) resources.getDimension(R.dimen.category_mark_height));
        textView2.setId(R.id.tip_first);
        textView2.setBackgroundResource(R.drawable.mark_bg);
        textView2.setGravity(17);
        textView2.setMaxWidth((int) resources.getDimension(R.dimen.category_mark_max_width));
        textView2.setTextColor(resources.getColor(R.color.color_262626));
        textView2.setTextSize(0, resources.getDimension(R.dimen.category_mark_size));
        textView2.setVisibility(4);
        textView2.setLayoutParams(layoutParams4);
        linearLayout.addView(textView2);
        textView2.setPadding((int) resources.getDimension(R.dimen.category_mark_padding_horizontal), 0, (int) resources.getDimension(R.dimen.category_mark_padding_horizontal), 0);
        TextView textView3 = new TextView(context);
        LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(-2, (int) resources.getDimension(R.dimen.category_mark_height));
        textView3.setId(R.id.tip_second);
        layoutParams5.leftMargin = (int) resources.getDimension(R.dimen.category_mark_margin_right);
        textView3.setBackgroundResource(R.drawable.mark_bg);
        textView3.setGravity(17);
        textView3.setMaxWidth((int) resources.getDimension(R.dimen.category_mark_max_width));
        textView3.setTextColor(resources.getColor(R.color.color_262626));
        textView3.setTextSize(0, resources.getDimension(R.dimen.category_mark_size));
        textView3.setVisibility(4);
        textView3.setLayoutParams(layoutParams5);
        linearLayout.addView(textView3);
        textView3.setPadding((int) resources.getDimension(R.dimen.category_mark_padding_horizontal), 0, (int) resources.getDimension(R.dimen.category_mark_padding_horizontal), 0);
        return frameLayout;
    }
}
