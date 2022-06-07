package com.zhangyue.we.x2c.layouts;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.google.android.material.badge.BadgeDrawable;
import com.xiaomi.micolauncher.R;
import com.zhangyue.we.x2c.IViewCreator;

/* loaded from: classes4.dex */
public class X2C127_Audiobooks_Banner_Item implements IViewCreator {
    @Override // com.zhangyue.we.x2c.IViewCreator
    public View createView(Context context) {
        Resources resources = context.getResources();
        FrameLayout frameLayout = new FrameLayout(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.audiobook_banner_layout_width), (int) resources.getDimension(R.dimen.audiobook_height));
        layoutParams.leftMargin = (int) resources.getDimension(R.dimen.holder_margin);
        layoutParams.rightMargin = (int) resources.getDimension(R.dimen.holder_margin);
        frameLayout.setLayoutParams(layoutParams);
        ImageView imageView = new ImageView(context);
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.audiobook_content_width), (int) resources.getDimension(R.dimen.audiobook_content_height));
        imageView.setId(R.id.audiobook_recommend_one);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(layoutParams2);
        frameLayout.addView(imageView);
        ImageView imageView2 = new ImageView(context);
        FrameLayout.LayoutParams layoutParams3 = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.audiobook_content_width), (int) resources.getDimension(R.dimen.audiobook_content_height));
        imageView2.setId(R.id.audiobook_recommend_two);
        layoutParams3.gravity = BadgeDrawable.TOP_END;
        imageView2.setLayoutParams(layoutParams3);
        frameLayout.addView(imageView2);
        ImageView imageView3 = new ImageView(context);
        FrameLayout.LayoutParams layoutParams4 = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.audiobook_content_width), (int) resources.getDimension(R.dimen.audiobook_content_height));
        imageView3.setId(R.id.audiobook_recommend_three);
        layoutParams4.gravity = BadgeDrawable.BOTTOM_END;
        imageView3.setLayoutParams(layoutParams4);
        frameLayout.addView(imageView3);
        ImageView imageView4 = new ImageView(context);
        FrameLayout.LayoutParams layoutParams5 = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.audiobook_option_width), (int) resources.getDimension(R.dimen.audiobook_option_height));
        imageView4.setId(R.id.audiobook_category);
        layoutParams5.gravity = 80;
        imageView4.setImageResource(R.drawable.book_classify_all);
        imageView4.setLayoutParams(layoutParams5);
        frameLayout.addView(imageView4);
        ImageView imageView5 = new ImageView(context);
        FrameLayout.LayoutParams layoutParams6 = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.audiobook_option_width), (int) resources.getDimension(R.dimen.audiobook_option_height));
        imageView5.setId(R.id.audiobook_favorite);
        layoutParams6.gravity = 80;
        layoutParams6.leftMargin = (int) resources.getDimension(R.dimen.audiobook_option_margin_horizontal);
        layoutParams6.bottomMargin = (int) resources.getDimension(R.dimen.audiobook_option_margin_vertical);
        imageView5.setImageResource(R.drawable.book_classify_collect);
        imageView5.setLayoutParams(layoutParams6);
        frameLayout.addView(imageView5);
        ImageView imageView6 = new ImageView(context);
        FrameLayout.LayoutParams layoutParams7 = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.audiobook_option_width), (int) resources.getDimension(R.dimen.audiobook_option_height));
        imageView6.setId(R.id.audiobook_play_recent);
        layoutParams7.gravity = 80;
        layoutParams7.bottomMargin = (int) resources.getDimension(R.dimen.audiobook_option_margin_vertical);
        imageView6.setImageResource(R.drawable.book_classify_history);
        imageView6.setLayoutParams(layoutParams7);
        frameLayout.addView(imageView6);
        ImageView imageView7 = new ImageView(context);
        FrameLayout.LayoutParams layoutParams8 = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.audiobook_option_width), (int) resources.getDimension(R.dimen.audiobook_option_height));
        imageView7.setId(R.id.audiobook_buy);
        layoutParams8.gravity = 80;
        layoutParams8.leftMargin = (int) resources.getDimension(R.dimen.audiobook_option_margin_horizontal);
        imageView7.setImageResource(R.drawable.book_classify_buy);
        imageView7.setLayoutParams(layoutParams8);
        frameLayout.addView(imageView7);
        View createView = new X2C127_No_Banner_Layout().createView(context);
        ViewGroup.LayoutParams layoutParams9 = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.audiobook_banner_layout_width), (int) resources.getDimension(R.dimen.audiobook_height));
        createView.setId(R.id.view_stub);
        createView.setLayoutParams(layoutParams9);
        frameLayout.addView(createView);
        return frameLayout;
    }
}
