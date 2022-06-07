package com.zhangyue.we.x2c.layouts;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.LinearLayout;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.homepage.view.AppView;
import com.zhangyue.we.x2c.IViewCreator;

/* loaded from: classes4.dex */
public class X2C127_Child_App_All_Item implements IViewCreator {
    @Override // com.zhangyue.we.x2c.IViewCreator
    public View createView(Context context) {
        Resources resources = context.getResources();
        LinearLayout linearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) resources.getDimension(R.dimen.child_app_item_width), -1);
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(layoutParams);
        AppView appView = new AppView(context);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, -2);
        appView.setId(R.id.first_app);
        appView.setLayoutParams(layoutParams2);
        linearLayout.addView(appView);
        AppView appView2 = new AppView(context);
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-1, -2);
        appView2.setId(R.id.second_app);
        appView2.setLayoutParams(layoutParams3);
        linearLayout.addView(appView2);
        AppView appView3 = new AppView(context);
        LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(-1, -2);
        appView3.setId(R.id.third_app);
        appView3.setLayoutParams(layoutParams4);
        linearLayout.addView(appView3);
        return linearLayout;
    }
}
