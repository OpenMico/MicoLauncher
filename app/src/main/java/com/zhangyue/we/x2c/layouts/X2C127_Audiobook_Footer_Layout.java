package com.zhangyue.we.x2c.layouts;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import com.xiaomi.micolauncher.R;
import com.zhangyue.we.x2c.IViewCreator;

/* loaded from: classes4.dex */
public class X2C127_Audiobook_Footer_Layout implements IViewCreator {
    @Override // com.zhangyue.we.x2c.IViewCreator
    public View createView(Context context) {
        Resources resources = context.getResources();
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.audiobook_refresh_layout_width), -1));
        Button button = new Button(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.audiobook_refresh_width), -2);
        button.setId(R.id.refresh);
        layoutParams.gravity = 16;
        button.setGravity(1);
        button.setTextSize(0, resources.getDimension(R.dimen.audiobook_refresh_text_size));
        layoutParams.leftMargin = (int) resources.getDimension(R.dimen.audiobook_refresh_img_margin_left);
        button.setTextColor(resources.getColor(R.color.color_B7BBC3));
        button.setBackgroundResource(0);
        button.setText(R.string.refresh_text);
        button.setLayoutParams(layoutParams);
        frameLayout.addView(button);
        return frameLayout;
    }
}
