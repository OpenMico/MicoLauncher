package com.zhangyue.we.x2c.layouts;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.FrameLayout;
import com.google.android.material.badge.BadgeDrawable;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.homepage.view.AudioBookContentMainView;
import com.zhangyue.we.x2c.IViewCreator;

/* loaded from: classes4.dex */
public class X2C127_No_Banner_Layout implements IViewCreator {
    @Override // com.zhangyue.we.x2c.IViewCreator
    public View createView(Context context) {
        Resources resources = context.getResources();
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.audiobook_banner_layout_width), (int) resources.getDimension(R.dimen.audiobook_height)));
        AudioBookContentMainView audioBookContentMainView = new AudioBookContentMainView(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.audiobook_content_width), (int) resources.getDimension(R.dimen.audiobook_content_height));
        audioBookContentMainView.setId(R.id.content_one);
        audioBookContentMainView.setBackgroundResource(R.drawable.audiobook_bg_0);
        audioBookContentMainView.setLayoutParams(layoutParams);
        frameLayout.addView(audioBookContentMainView);
        AudioBookContentMainView audioBookContentMainView2 = new AudioBookContentMainView(context);
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.audiobook_content_width), (int) resources.getDimension(R.dimen.audiobook_content_height));
        audioBookContentMainView2.setId(R.id.content_two);
        layoutParams2.gravity = BadgeDrawable.TOP_END;
        audioBookContentMainView2.setBackgroundResource(R.drawable.audiobook_bg_1);
        audioBookContentMainView2.setLayoutParams(layoutParams2);
        frameLayout.addView(audioBookContentMainView2);
        AudioBookContentMainView audioBookContentMainView3 = new AudioBookContentMainView(context);
        FrameLayout.LayoutParams layoutParams3 = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.audiobook_content_width), (int) resources.getDimension(R.dimen.audiobook_content_height));
        audioBookContentMainView3.setId(R.id.content_three);
        layoutParams3.gravity = BadgeDrawable.BOTTOM_END;
        audioBookContentMainView3.setBackgroundResource(R.drawable.audiobook_bg_2);
        audioBookContentMainView3.setLayoutParams(layoutParams3);
        frameLayout.addView(audioBookContentMainView3);
        return frameLayout;
    }
}
