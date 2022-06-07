package com.zhangyue.we.x2c.layouts;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.xiaomi.micolauncher.R;
import com.zhangyue.we.x2c.IViewCreator;

/* loaded from: classes4.dex */
public class X2C127_Recent_Empty_Layout implements IViewCreator {
    @Override // com.zhangyue.we.x2c.IViewCreator
    public View createView(Context context) {
        Resources resources = context.getResources();
        ConstraintLayout constraintLayout = new ConstraintLayout(context);
        constraintLayout.setLayoutParams(new ConstraintLayout.LayoutParams(-1, -1));
        TextView textView = new TextView(context);
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams((int) resources.getDimension(R.dimen.no_history_width), -2);
        textView.setId(R.id.empty_view);
        textView.setText(R.string.no_video_history_text);
        textView.setTextSize(0, resources.getDimension(R.dimen.no_history_text_size));
        textView.setGravity(17);
        layoutParams.topToTop = 0;
        layoutParams.bottomToBottom = 0;
        layoutParams.leftToLeft = 0;
        layoutParams.rightToRight = 0;
        textView.setTextColor(resources.getColor(R.color.color_6F7382));
        layoutParams.validate();
        textView.setLayoutParams(layoutParams);
        constraintLayout.addView(textView);
        return constraintLayout;
    }
}
