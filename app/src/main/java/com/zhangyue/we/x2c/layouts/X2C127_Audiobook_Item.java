package com.zhangyue.we.x2c.layouts;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.FrameLayout;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.homepage.view.AudioBookContentView;
import com.zhangyue.we.x2c.IViewCreator;

/* loaded from: classes4.dex */
public class X2C127_Audiobook_Item implements IViewCreator {
    @Override // com.zhangyue.we.x2c.IViewCreator
    public View createView(Context context) {
        Resources resources = context.getResources();
        AudioBookContentView audioBookContentView = new AudioBookContentView(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.audiobook_content_width), (int) resources.getDimension(R.dimen.audiobook_content_height));
        audioBookContentView.setId(R.id.contentview);
        layoutParams.leftMargin = (int) resources.getDimension(R.dimen.audiobook_content_margin_right);
        audioBookContentView.setLayoutParams(layoutParams);
        return audioBookContentView;
    }
}
