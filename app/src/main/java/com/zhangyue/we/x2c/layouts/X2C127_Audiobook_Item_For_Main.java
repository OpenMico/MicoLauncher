package com.zhangyue.we.x2c.layouts;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.FrameLayout;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.homepage.view.AudioBookContentMainView;
import com.zhangyue.we.x2c.IViewCreator;

/* loaded from: classes4.dex */
public class X2C127_Audiobook_Item_For_Main implements IViewCreator {
    @Override // com.zhangyue.we.x2c.IViewCreator
    public View createView(Context context) {
        Resources resources = context.getResources();
        AudioBookContentMainView audioBookContentMainView = new AudioBookContentMainView(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.audiobook_content_width), (int) resources.getDimension(R.dimen.audiobook_content_height_for_main));
        audioBookContentMainView.setId(R.id.contentview);
        layoutParams.rightMargin = (int) resources.getDimension(R.dimen.audiobook_content_margin_right_for_main);
        audioBookContentMainView.setLayoutParams(layoutParams);
        return audioBookContentMainView;
    }
}
