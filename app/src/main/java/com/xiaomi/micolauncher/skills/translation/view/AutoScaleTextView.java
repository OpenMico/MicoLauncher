package com.xiaomi.micolauncher.skills.translation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.utils.UiUtils;

/* loaded from: classes3.dex */
public class AutoScaleTextView extends TextView {
    public AutoScaleTextView(Context context) {
        this(context, null);
    }

    public AutoScaleTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AutoScaleTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    private void a(String str) {
        if (str != null && str.length() != 0) {
            if (getPaint().measureText(str) >= UiUtils.getSize(getContext(), R.dimen.translation_text_max_width)) {
                setTextSize(UiUtils.getSize(getContext(), R.dimen.translation_text_scale_size));
            } else {
                setTextSize(UiUtils.getSize(getContext(), R.dimen.translation_text_size_big));
            }
        }
    }

    @Override // android.widget.TextView
    protected void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        a(charSequence.toString());
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        if (i != i3) {
            a(getText().toString());
        }
    }
}
