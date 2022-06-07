package com.zhangyue.we.x2c.layouts;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.xiaomi.micolauncher.R;
import com.zhangyue.we.x2c.IViewCreator;

/* loaded from: classes4.dex */
public class X2C127_Video_Category_Item implements IViewCreator {
    @Override // com.zhangyue.we.x2c.IViewCreator
    public View createView(Context context) {
        Resources resources = context.getResources();
        RelativeLayout relativeLayout = new RelativeLayout(context);
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams((int) resources.getDimension(R.dimen.video_item_width), (int) resources.getDimension(R.dimen.video_item_height_with_top_margin)));
        ImageView imageView = new ImageView(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) resources.getDimension(R.dimen.video_category_item_width), (int) resources.getDimension(R.dimen.video_category_item_height));
        imageView.setImageResource(R.drawable.video_category_img_loading);
        imageView.setId(R.id.category_image_1);
        layoutParams.topMargin = (int) resources.getDimension(R.dimen.recycler_padding_top);
        imageView.setLayoutParams(layoutParams);
        relativeLayout.addView(imageView);
        ImageView imageView2 = new ImageView(context);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams((int) resources.getDimension(R.dimen.video_category_item_width), (int) resources.getDimension(R.dimen.video_category_item_height));
        imageView2.setImageResource(R.drawable.video_category_img_loading);
        imageView2.setId(R.id.category_image_2);
        layoutParams2.addRule(17, R.id.category_image_1);
        layoutParams2.topMargin = (int) resources.getDimension(R.dimen.recycler_padding_top);
        layoutParams2.leftMargin = (int) resources.getDimension(R.dimen.video_item_padding);
        imageView2.setLayoutParams(layoutParams2);
        relativeLayout.addView(imageView2);
        ImageView imageView3 = new ImageView(context);
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams((int) resources.getDimension(R.dimen.video_category_item_width), (int) resources.getDimension(R.dimen.video_category_item_height));
        imageView3.setImageResource(R.drawable.video_category_img_loading);
        imageView3.setId(R.id.category_image_3);
        layoutParams3.topMargin = (int) resources.getDimension(R.dimen.video_item_padding);
        layoutParams3.addRule(3, R.id.category_image_1);
        imageView3.setLayoutParams(layoutParams3);
        relativeLayout.addView(imageView3);
        ImageView imageView4 = new ImageView(context);
        RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams((int) resources.getDimension(R.dimen.video_category_item_width), (int) resources.getDimension(R.dimen.video_category_item_height));
        imageView4.setImageResource(R.drawable.video_category_img_loading);
        imageView4.setId(R.id.category_image_4);
        layoutParams4.addRule(3, R.id.category_image_2);
        layoutParams4.addRule(17, R.id.category_image_3);
        layoutParams4.topMargin = (int) resources.getDimension(R.dimen.video_item_padding);
        layoutParams4.leftMargin = (int) resources.getDimension(R.dimen.video_item_padding);
        imageView4.setLayoutParams(layoutParams4);
        relativeLayout.addView(imageView4);
        return relativeLayout;
    }
}
