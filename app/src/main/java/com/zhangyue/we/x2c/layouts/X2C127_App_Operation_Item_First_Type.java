package com.zhangyue.we.x2c.layouts;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.xiaomi.micolauncher.R;
import com.zhangyue.we.x2c.IViewCreator;

/* loaded from: classes4.dex */
public class X2C127_App_Operation_Item_First_Type implements IViewCreator {
    @Override // com.zhangyue.we.x2c.IViewCreator
    public View createView(Context context) {
        Resources resources = context.getResources();
        ConstraintLayout constraintLayout = new ConstraintLayout(context);
        constraintLayout.setLayoutParams(new ConstraintLayout.LayoutParams(-1, (int) resources.getDimension(R.dimen.app_operate_item_height)));
        ImageView imageView = new ImageView(context);
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams((int) resources.getDimension(R.dimen.home_skill_app_operation_item_app_icon_size), (int) resources.getDimension(R.dimen.home_skill_app_operation_item_app_icon_size));
        imageView.setId(R.id.app_icon);
        layoutParams.bottomToBottom = 0;
        layoutParams.dimensionRatio = "1:1";
        layoutParams.startToStart = 0;
        layoutParams.topToTop = 0;
        layoutParams.validate();
        imageView.setLayoutParams(layoutParams);
        constraintLayout.addView(imageView);
        TextView textView = new TextView(context);
        ConstraintLayout.LayoutParams layoutParams2 = new ConstraintLayout.LayoutParams(-2, -2);
        textView.setId(R.id.app_name_tv);
        layoutParams2.leftMargin = (int) resources.getDimension(R.dimen.home_skill_app_operation_item_title_margin);
        layoutParams2.topMargin = (int) TypedValue.applyDimension(1, 5.0f, resources.getDisplayMetrics());
        textView.setTextColor(resources.getColor(R.color.app_name_text_color));
        textView.setTextSize(0, resources.getDimension(R.dimen.operate_app_name_size));
        layoutParams2.startToEnd = R.id.app_icon;
        layoutParams2.topToTop = R.id.app_icon;
        layoutParams2.matchConstraintPercentWidth = 0.5f;
        layoutParams2.validate();
        textView.setLayoutParams(layoutParams2);
        constraintLayout.addView(textView);
        TextView textView2 = new TextView(context);
        ConstraintLayout.LayoutParams layoutParams3 = new ConstraintLayout.LayoutParams(-2, -2);
        textView2.setId(R.id.app_introduction);
        textView2.setEllipsize(TextUtils.TruncateAt.END);
        textView2.setGravity(16);
        textView2.setMaxLines(1);
        textView2.setTextColor(resources.getColor(R.color.app_desc_text_color));
        textView2.setTextSize(0, resources.getDimension(R.dimen.operate_app_introduction_size));
        layoutParams3.bottomToBottom = R.id.app_icon;
        layoutParams3.startToStart = R.id.app_name_tv;
        layoutParams3.topToBottom = R.id.app_name_tv;
        layoutParams3.matchConstraintPercentWidth = 0.5f;
        layoutParams3.validate();
        textView2.setLayoutParams(layoutParams3);
        constraintLayout.addView(textView2);
        View view = new View(context);
        ConstraintLayout.LayoutParams layoutParams4 = new ConstraintLayout.LayoutParams((int) TypedValue.applyDimension(1, 0.0f, resources.getDisplayMetrics()), (int) resources.getDimension(R.dimen.app_operation_item_divider_height));
        view.setBackgroundColor(resources.getColor(R.color.app_divider_color));
        layoutParams4.bottomToBottom = 0;
        layoutParams4.startToStart = R.id.app_name_tv;
        layoutParams4.validate();
        view.setLayoutParams(layoutParams4);
        constraintLayout.addView(view);
        return constraintLayout;
    }
}
