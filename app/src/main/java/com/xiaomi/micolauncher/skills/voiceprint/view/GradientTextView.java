package com.xiaomi.micolauncher.skills.voiceprint.view;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.common.L;

/* loaded from: classes3.dex */
public class GradientTextView extends TextView {
    public static final int TYPE_LEFT_RIGHT = 0;
    public static final int TYPE_RIGHT_LEFT = 1;

    public GradientTextView(Context context) {
        this(context, null);
    }

    public GradientTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public GradientTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setGradientText(String str, String[] strArr, int i) {
        if (!TextUtils.isEmpty(str)) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            spannableStringBuilder.append((CharSequence) str);
            if (ContainerUtil.hasData(strArr)) {
                for (String str2 : strArr) {
                    int indexOf = str.indexOf(str2);
                    int length = str2.length();
                    L.wakeup.i("text  %s  word  %s  index   %d", str, str2, Integer.valueOf(indexOf));
                    if (indexOf >= 0) {
                        spannableStringBuilder.setSpan(new GradientSpan(getContext(), getPaint().measureText(str2), i), indexOf, length + indexOf, 18);
                    }
                }
            }
            setText(spannableStringBuilder);
        }
    }
}
