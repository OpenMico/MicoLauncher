package com.xiaomi.micolauncher.common.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;

/* loaded from: classes3.dex */
public class MarqueeTextView extends AppCompatTextView {
    @Override // android.view.View
    public boolean isFocused() {
        return true;
    }

    public MarqueeTextView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        setSingleLine();
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
    }

    public MarqueeTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView();
    }

    public MarqueeTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView();
    }
}
