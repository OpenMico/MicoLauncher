package com.xiaomi.micolauncher.module.lockscreen.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.xiaomi.mico.common.DisplayUtils;
import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public class DateCalendarFortuneView extends LinearLayout {
    private String a;

    public DateCalendarFortuneView(Context context) {
        super(context);
    }

    public DateCalendarFortuneView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public DateCalendarFortuneView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension(a(), b());
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        for (int i5 = 0; i5 < getChildCount(); i5++) {
            View childAt = getChildAt(i5);
            int measuredWidth = childAt.getMeasuredWidth();
            int dip2px = (DisplayUtils.dip2px(getContext(), 18.0f) + measuredWidth) * i5;
            childAt.layout(dip2px, 0, dip2px + measuredWidth, measuredWidth);
        }
    }

    private int a() {
        int dip2px = DisplayUtils.dip2px(getContext(), 82.0f);
        int dip2px2 = DisplayUtils.dip2px(getContext(), 18.0f);
        int length = this.a.length();
        return (dip2px * length) + ((length - 1) * dip2px2);
    }

    private int b() {
        return DisplayUtils.dip2px(getContext(), 82.0f);
    }

    public void setText(String str) {
        this.a = str;
        int i = 0;
        while (i < str.length()) {
            int i2 = i + 1;
            addView(a(str.substring(i, i2)));
            i = i2;
        }
        requestLayout();
    }

    private View a(String str) {
        TextView textView = (TextView) LinearLayout.inflate(getContext(), R.layout.view_date_calendar_fortune_tv, null);
        textView.setText(str);
        return textView;
    }
}
