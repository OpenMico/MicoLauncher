package com.zhangyue.we.x2c.layouts;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.LinearLayout;
import com.xiaomi.micolauncher.R;
import com.zhangyue.we.x2c.IViewCreator;

/* loaded from: classes4.dex */
public class X2C127_Child_App_All_Layout implements IViewCreator {
    @Override // com.zhangyue.we.x2c.IViewCreator
    public View createView(Context context) {
        Resources resources = context.getResources();
        LinearLayout linearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -1);
        linearLayout.setOrientation(0);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setPadding((int) resources.getDimension(R.dimen.child_operate_layout_padding), (int) resources.getDimension(R.dimen.child_operate_layout_padding), (int) resources.getDimension(R.dimen.child_operate_layout_padding), (int) resources.getDimension(R.dimen.child_operate_layout_padding));
        return linearLayout;
    }
}
