package com.zhangyue.we.x2c.layouts;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.LinearLayout;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.homepage.view.AppView;
import com.zhangyue.we.x2c.IViewCreator;

/* loaded from: classes4.dex */
public class X2C127_App_All_Item_Layout implements IViewCreator {
    @Override // com.zhangyue.we.x2c.IViewCreator
    public View createView(Context context) {
        Resources resources = context.getResources();
        AppView appView = new AppView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.leftMargin = (int) resources.getDimension(R.dimen.rec_app_item_margin);
        appView.setLayoutParams(layoutParams);
        return appView;
    }
}
