package com.zhangyue.we.x2c.layouts;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.FrameLayout;
import com.google.android.material.badge.BadgeDrawable;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.homepage.view.AppSelectedView;
import com.zhangyue.we.x2c.IViewCreator;

/* loaded from: classes4.dex */
public class X2C127_Child_App_Selected_Layout implements IViewCreator {
    @Override // com.zhangyue.we.x2c.IViewCreator
    public View createView(Context context) {
        Resources resources = context.getResources();
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.child_app_selected_layout_width), -1));
        frameLayout.setPadding((int) resources.getDimension(R.dimen.child_operate_layout_padding), (int) resources.getDimension(R.dimen.child_operate_layout_padding), (int) resources.getDimension(R.dimen.child_operate_layout_padding), (int) resources.getDimension(R.dimen.child_operate_layout_padding));
        AppSelectedView appSelectedView = new AppSelectedView(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.child_app_item_width), -2);
        appSelectedView.setId(R.id.selected_app_first);
        appSelectedView.setLayoutParams(layoutParams);
        frameLayout.addView(appSelectedView);
        AppSelectedView appSelectedView2 = new AppSelectedView(context);
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.child_app_item_width), -2);
        appSelectedView2.setId(R.id.selected_app_second);
        layoutParams2.gravity = 49;
        appSelectedView2.setLayoutParams(layoutParams2);
        frameLayout.addView(appSelectedView2);
        AppSelectedView appSelectedView3 = new AppSelectedView(context);
        FrameLayout.LayoutParams layoutParams3 = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.child_app_item_width), -2);
        appSelectedView3.setId(R.id.selected_app_third);
        layoutParams3.gravity = BadgeDrawable.TOP_END;
        appSelectedView3.setLayoutParams(layoutParams3);
        frameLayout.addView(appSelectedView3);
        AppSelectedView appSelectedView4 = new AppSelectedView(context);
        FrameLayout.LayoutParams layoutParams4 = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.child_app_item_width), -2);
        appSelectedView4.setId(R.id.selected_app_fourth);
        layoutParams4.gravity = 8388627;
        appSelectedView4.setLayoutParams(layoutParams4);
        frameLayout.addView(appSelectedView4);
        AppSelectedView appSelectedView5 = new AppSelectedView(context);
        FrameLayout.LayoutParams layoutParams5 = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.child_app_item_width), -2);
        appSelectedView5.setId(R.id.selected_app_fifth);
        layoutParams5.gravity = 17;
        appSelectedView5.setLayoutParams(layoutParams5);
        frameLayout.addView(appSelectedView5);
        AppSelectedView appSelectedView6 = new AppSelectedView(context);
        FrameLayout.LayoutParams layoutParams6 = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.child_app_item_width), -2);
        appSelectedView6.setId(R.id.selected_app_sixth);
        layoutParams6.gravity = 8388629;
        appSelectedView6.setLayoutParams(layoutParams6);
        frameLayout.addView(appSelectedView6);
        AppSelectedView appSelectedView7 = new AppSelectedView(context);
        FrameLayout.LayoutParams layoutParams7 = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.child_app_item_width), -2);
        appSelectedView7.setId(R.id.selected_app_seventh);
        layoutParams7.gravity = BadgeDrawable.BOTTOM_START;
        appSelectedView7.setLayoutParams(layoutParams7);
        frameLayout.addView(appSelectedView7);
        AppSelectedView appSelectedView8 = new AppSelectedView(context);
        FrameLayout.LayoutParams layoutParams8 = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.child_app_item_width), -2);
        appSelectedView8.setId(R.id.selected_app_eight);
        layoutParams8.gravity = 81;
        appSelectedView8.setLayoutParams(layoutParams8);
        frameLayout.addView(appSelectedView8);
        AppSelectedView appSelectedView9 = new AppSelectedView(context);
        FrameLayout.LayoutParams layoutParams9 = new FrameLayout.LayoutParams((int) resources.getDimension(R.dimen.child_app_item_width), -2);
        appSelectedView9.setId(R.id.selected_app_ninth);
        layoutParams9.gravity = BadgeDrawable.BOTTOM_END;
        appSelectedView9.setLayoutParams(layoutParams9);
        frameLayout.addView(appSelectedView9);
        return frameLayout;
    }
}
